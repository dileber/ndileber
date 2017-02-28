/**
 * Copyright 2015 Anthony Restaino

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied. See the License for the specific language governing
 permissions and limitations under the License.
 */
package com.drcosu.ndileber.tools.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A class to help you manage your permissions simply.
 *
 * 权限管理类
 * 用法
 *
 * PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
 * @Override
 * public void onGranted() {
 * //				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
 * }
 *
 *  @Override
 * public void onDenied(String permission) {
 * //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
 * }
 * });
 *
 * @Override
 * public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
 *  @NonNull int[] grantResults) {
 *  PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
 }

 *
 */
public class PermissionsManager {

  private static final String TAG = PermissionsManager.class.getSimpleName();

  private final Set<String> mPendingRequests = new HashSet<String>(1);
  private final Set<String> mPermissions = new HashSet<String>(1);
  private final List<WeakReference<PermissionsResultAction>> mPendingActions = new ArrayList<WeakReference<PermissionsResultAction>>(1);

  private static PermissionsManager mInstance = null;

  public static PermissionsManager getInstance() {
    if (mInstance == null) {
      mInstance = new PermissionsManager();
    }
    return mInstance;
  }

  private PermissionsManager() {
    initializePermissionsMap();
  }

  /**
   * 此方法使用反射来读取清单类中的所有权限。
   *因为一些权限不存在于旧版本的安卓系统中，
   *因为他们不存在，他们将被拒绝时，你检查是否有权限
   *因为一个新的权限，往往是补充，那里没有以前的
   *所需的许可。我们初始化一组可用的权限，并检查组
   *检查是否有权限，当我们被拒绝时权限仍然不存在
   * This method uses reflection to read all the permissions in the Manifest class.
   * This is necessary because some permissions do not exist on older versions of Android,
   * since they do not exist, they will be denied when you check whether you have permission
   * which is problematic since a new permission is often added where there was no previous
   * permission required. We initialize a Set of available permissions and check the set
   * when checking if we have permission since we want to know when we are denied a permission
   * because it doesn't exist yet.
   */
  private synchronized void initializePermissionsMap() {
    Field[] fields = Manifest.permission.class.getFields();
    for (Field field : fields) {
      String name = null;
      try {
        name = (String) field.get("");
      } catch (IllegalAccessException e) {
        Log.e(TAG, "Could not access field", e);
      }
      mPermissions.add(name);
    }
  }

  /**
   *
   * 此方法检索在应用程序清单中声明的所有权限。
   * 它返回一个非空数组，可以声明的权限。
   * This method retrieves all the permissions declared in the application's manifest.
   * It returns a non null array of permisions that can be declared.
   *
   * @param activity the Activity necessary to check what permissions we have. 检查我们需要哪些权限
   * @return a non null array of permissions that are declared in the application manifest. 返回在应用程序清单中声明的非空数组
   */
  @NonNull
  private synchronized String[] getManifestPermissions(@NonNull final Activity activity) {
    PackageInfo packageInfo = null;
    List<String> list = new ArrayList<String>(1);
    try {
      Log.d(TAG, activity.getPackageName());
      packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
    } catch (PackageManager.NameNotFoundException e) {
      Log.e(TAG, "A problem occurred when retrieving permissions", e);
    }
    if (packageInfo != null) {
      String[] permissions = packageInfo.requestedPermissions;
      if (permissions != null) {
        for (String perm : permissions) {
          Log.d(TAG, "Manifest contained permission: " + perm);
          list.add(perm);
        }
      }
    }
    return list.toArray(new String[list.size()]);
  }

  /**
   * This method adds the {@link PermissionsResultAction} to the current list
   * of pending actions that will be completed when the permissions are
   * received. The list of permissions passed to this method are registered
   * in the PermissionsResultAction object so that it will be notified of changes
   * made to these permissions.
   * 在permissionsresultaction对象，它将变更通知这些权限
   *
   * @param permissions the required permissions for the action to be executed. 权限所需的操作的权限。
   * @param action      the action to add to the current list of pending actions. 将 动作添加到当前正在执行的动作列表中。
   */
  private synchronized void addPendingAction(@NonNull String[] permissions,
      @Nullable PermissionsResultAction action) {
    if (action == null) {
      return;
    }
    action.registerPermissions(permissions);
    mPendingActions.add(new WeakReference<PermissionsResultAction>(action));
  }

  /**
   * 删除从队列中等待的动作和执行该动作
   * This method removes a pending action from the list of pending actions.
   * It is used for cases where the permission has already been granted, so
   * you immediately wish to remove the pending action from the queue and
   * execute the action.
   *
   * @param action the action to remove 移除动作
   */
  private synchronized void removePendingAction(@Nullable PermissionsResultAction action) {
    for (Iterator<WeakReference<PermissionsResultAction>> iterator = mPendingActions.iterator();
         iterator.hasNext(); ) {
      WeakReference<PermissionsResultAction> weakRef = iterator.next();
      if (weakRef.get() == action || weakRef.get() == null) {
        iterator.remove();
      }
    }
  }

  /**
   * 这个静态方法可以用来检查你是否有一个特定的权限。
   * This static method can be used to check whether or not you have a specific permission.
   * It is basically a less verbose method of using {@link ActivityCompat#checkSelfPermission(Context, String)}
   * and will simply return a boolean whether or not you have the permission. If you pass
   * in a null Context object, it will return false as otherwise it cannot check the permission.
   * However, the Activity parameter is nullable so that you can pass in a reference that you
   * are not always sure will be valid or not (e.g. getActivity() from Fragment).
   *
   * @param context    the Context necessary to check the permission 检查权限的上下文对象
   * @param permission the permission to check 要检查的权限
   * @return true if you have been granted the permission, false otherwise 返回是否授权了此权限
   */
  @SuppressWarnings("unused")
  public synchronized boolean hasPermission(@Nullable Context context, @NonNull String permission) {
    return context != null && (ActivityCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED || !mPermissions.contains(permission));
  }

  /**
   * 这个静态方法可以用来检查你是否有几个特定的权限。
   * 为每一个权限，并将简单地返回一个布尔值是否你有所有的权限
   * This static method can be used to check whether or not you have several specific permissions.
   * It is simpler than checking using {@link ActivityCompat#checkSelfPermission(Context, String)}
   * for each permission and will simply return a boolean whether or not you have all the permissions.
   * If you pass in a null Context object, it will return false as otherwise it cannot check the
   * permission. However, the Activity parameter is nullable so that you can pass in a reference
   * that you are not always sure will be valid or not (e.g. getActivity() from Fragment).
   *
   * @param context     the Context necessary to check the permission 需要检查 权限的上下文
   * @param permissions the permissions to check 权限 数组
   * @return true if you have been granted all the permissions, false otherwise 返回你是否有所有的权限
   */
  @SuppressWarnings("unused")
  public synchronized boolean hasAllPermissions(@Nullable Context context, @NonNull String[] permissions) {
    if (context == null) {
      return false;
    }
    boolean hasAllPermissions = true;
    for (String perm : permissions) {
      hasAllPermissions &= hasPermission(context, perm);
    }
    return hasAllPermissions;
  }

  /**
   * 这种方法的是获取清单里面的所有权限。permissionsresultaction  用于通知允许用户允许或拒绝每一个权限。
   * This method will request all the permissions declared in your application manifest
   * for the specified {@link PermissionsResultAction}. The purpose of this method is to enable
   * all permissions to be requested at one shot. The PermissionsResultAction is used to notify
   * you of the user allowing or denying each permission. The Activity and PermissionsResultAction
   * parameters are both annotated Nullable, but this method will not work if the Activity
   * is null. It is only annotated Nullable as a courtesy to prevent crashes in the case
   * that you call this from a Fragment where {@link Fragment#getActivity()} could yield
   * null. Additionally, you will not receive any notification of permissions being granted
   * if you provide a null PermissionsResultAction.
   *
   * @param activity the Activity necessary to request and check permissions. 需要检查权限的activity
   * @param action   the PermissionsResultAction used to notify you of permissions being accepted. permissionsresultaction用于权限接受通知你
   */
  @SuppressWarnings("unused")
  public synchronized void requestAllManifestPermissionsIfNecessary(final @Nullable Activity activity,
      final @Nullable PermissionsResultAction action) {
    if (activity == null) {
      return;
    }
    String[] perms = getManifestPermissions(activity);
    requestPermissionsIfNecessaryForResult(activity, perms, action);
  }

  /**
   * 该方法将请求的权限，如果
   *他们需要被要求（即我们没有许可），并会增加
   * permissionsresultaction到队列被通知的权限被授予或
   *否认。在预Android棉花糖的情况下，将立即授予权限。
   *活动变量为空，但如果它是无效的，不能执行的方法。
   *这是唯一可作为一种礼貌片段，getactivity()可能产量空
   *如果该片段没有添加到其父活动中
   * This method should be used to execute a {@link PermissionsResultAction} for the array
   * of permissions passed to this method. This method will request the permissions if
   * they need to be requested (i.e. we don't have permission yet) and will add the
   * PermissionsResultAction to the queue to be notified of permissions being granted or
   * denied. In the case of pre-Android Marshmallow, permissions will be granted immediately.
   * The Activity variable is nullable, but if it is null, the method will fail to execute.
   * This is only nullable as a courtesy for Fragments where getActivity() may yeild null
   * if the Fragment is not currently added to its parent Activity.
   *
   * @param activity    the activity necessary to request the permissions. 请求权限的活动
   * @param permissions the list of permissions to request for the {@link PermissionsResultAction}. 权限列表
   * @param action      the PermissionsResultAction to notify when the permissions are granted or denied. permissionsresultaction通知当权限授予或拒绝。
   */
  @SuppressWarnings("unused")
  public synchronized void requestPermissionsIfNecessaryForResult(@Nullable Activity activity,
      @NonNull String[] permissions,
      @Nullable PermissionsResultAction action) {
    if (activity == null) {
      return;
    }
    addPendingAction(permissions, action);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      doPermissionWorkBeforeAndroidM(activity, permissions, action);
    } else {
      List<String> permList = getPermissionsListToRequest(activity, permissions, action);
      if (permList.isEmpty()) {
        //if there is no permission to request, there is no reason to keep the action int the list
        removePendingAction(action);
      } else {
        String[] permsToRequest = permList.toArray(new String[permList.size()]);
        mPendingRequests.addAll(permList);
        ActivityCompat.requestPermissions(activity, permsToRequest, 1);
      }
    }
  }

  /**
   * 该方法将请求的权限，如果
   *他们需要被要求（即我们没有许可），并会增加
   * permissionsresultaction到队列被通知的权限被授予或
   *否认。在预Android棉花糖(6.0)的情况下，将立即授予权限。
   *但如果 getactivity()返回null，这方法
   *将无法工作作为活动引用，必须检查权限。
   * This method should be used to execute a {@link PermissionsResultAction} for the array
   * of permissions passed to this method. This method will request the permissions if
   * they need to be requested (i.e. we don't have permission yet) and will add the
   * PermissionsResultAction to the queue to be notified of permissions being granted or
   * denied. In the case of pre-Android Marshmallow, permissions will be granted immediately.
   * The Fragment variable is used, but if {@link Fragment#getActivity()} returns null, this method
   * will fail to work as the activity reference is necessary to check for permissions.
   *
   * @param fragment    the fragment necessary to request the permissions. 需要检查 权限的fragmnet
   * @param permissions the list of permissions to request for the {@link PermissionsResultAction}.需要请求的权限列表
   * @param action      the PermissionsResultAction to notify when the permissions are granted or denied. permissionsresultaction通知当权限授予或拒绝。
   */
  @SuppressWarnings("unused")
  public synchronized void requestPermissionsIfNecessaryForResult(@NonNull Fragment fragment,
      @NonNull String[] permissions,
      @Nullable PermissionsResultAction action) {
    Activity activity = fragment.getActivity();
    if (activity == null) {
      return;
    }
    addPendingAction(permissions, action);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      doPermissionWorkBeforeAndroidM(activity, permissions, action);
    } else {
      List<String> permList = getPermissionsListToRequest(activity, permissions, action);
      if (permList.isEmpty()) {
        //if there is no permission to request, there is no reason to keep the action int the list
        removePendingAction(action);
      } else {
        String[] permsToRequest = permList.toArray(new String[permList.size()]);
        mPendingRequests.addAll(permList);
        fragment.requestPermissions(permsToRequest, 1);
      }
    }
  }

  /**
   * 这个方法通知permissionsmanager，权限已经改变。如果你正在做
   *使用活动的权限请求，则应调用此方法,将通知所有悬而未决  的,permissionsresultaction当前对象
   *在队列中，并将从挂起的请求列表中删除权限请求。
   * This method notifies the PermissionsManager that the permissions have change. If you are making
   * the permissions requests using an Activity, then this method should be called from the
   * Activity callback onRequestPermissionsResult() with the variables passed to that method. If
   * you are passing a Fragment to make the permissions request, then you should call this in
   * the {@link Fragment#onRequestPermissionsResult(int, String[], int[])} method.
   * It will notify all the pending PermissionsResultAction objects currently
   * in the queue, and will remove the permissions request from the list of pending requests.
   *
   * @param permissions the permissions that have changed. 已更改的权限。
   * @param results     the values for each permission. 每个权限的值
   */
  @SuppressWarnings("unused")
  public synchronized void notifyPermissionsChange(@NonNull String[] permissions, @NonNull int[] results) {
    int size = permissions.length;
    if (results.length < size) {
      size = results.length;
    }
    Iterator<WeakReference<PermissionsResultAction>> iterator = mPendingActions.iterator();
    while (iterator.hasNext()) {
      PermissionsResultAction action = iterator.next().get();
      for (int n = 0; n < size; n++) {
        if (action == null || action.onResult(permissions[n], results[n])) {
          iterator.remove();
          break;
        }
      }
    }
    for (int n = 0; n < size; n++) {
      mPendingRequests.remove(permissions[n]);
    }
  }

  /**
   * 在安卓设备前要求权限（安卓6，api23）,根据权限状态，直接进行或拒绝工作
   * When request permissions on devices before Android M (Android 6.0, API Level 23)
   * Do the granted or denied work directly according to the permission status
   *
   * @param activity    the activity to check permissions 检测权限的activity
   * @param permissions the permissions names 权限数组
   * @param action      the callback work object, containing what we what to do after
   *                    permission check 我们执行某项操作后权限检查
   */
  private void doPermissionWorkBeforeAndroidM(@NonNull Activity activity,
      @NonNull String[] permissions,
      @Nullable PermissionsResultAction action) {
    for (String perm : permissions) {
      if (action != null) {
        if (!mPermissions.contains(perm)) {
          action.onResult(perm, Permissions.NOT_FOUND);
        } else if (ActivityCompat.checkSelfPermission(activity, perm)
            != PackageManager.PERMISSION_GRANTED) {
          action.onResult(perm, Permissions.DENIED);
        } else {
          action.onResult(perm, Permissions.GRANTED);
        }
      }
    }
  }

  /**
   * Filter the permissions list:
   * If a permission is not granted, add it to the result list
   * if a permission is granted, do the granted work, do not add it to the result list
   *
   * @param activity    the activity to check permissions 检查权限的activity
   * @param permissions all the permissions names 所有权限的名字
   * @param action      the callback work object, containing what we what to do after
   *                    permission check 我们执行某项操作后权限检查
   * @return a list of permissions names that are not granted yet 尚未授予的权限名称列表
   */
  @NonNull
  private List<String> getPermissionsListToRequest(@NonNull Activity activity,
                                                   @NonNull String[] permissions,
                                                   @Nullable PermissionsResultAction action) {
    List<String> permList = new ArrayList<String>(permissions.length);
    for (String perm : permissions) {
      if (!mPermissions.contains(perm)) {
        if (action != null) {
          action.onResult(perm, Permissions.NOT_FOUND);
        }
      } else if (ActivityCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
        if (!mPendingRequests.contains(perm)) {
          permList.add(perm);
        }
      } else {
        if (action != null) {
          action.onResult(perm, Permissions.GRANTED);
        }
      }
    }
    return permList;
  }

}