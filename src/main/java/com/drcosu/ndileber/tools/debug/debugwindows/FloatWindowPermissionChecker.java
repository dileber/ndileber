package com.drcosu.ndileber.tools.debug.debugwindows;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.drcosu.ndileber.app.SApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by feifan on 2017/8/21.
 * Contacts me:404619986@qq.com
 */

public class FloatWindowPermissionChecker {
    private static final String TAG = "FloatWindowPermissionChecker";
    //无法跳转的提示，应当根据不同的Rom给予不同的提示，比如Oppo应该提示去手机管家里开启，这里偷懒懒得写了
    private static final String TOAST_HINT = "无法跳转至权限设置页面，请手动设置或向我反馈";

    public static boolean checkFloatWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(SApplication.getAppContext());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //AppOpsManager添加于API 19
            return checkOps();
        } else {
            //4.4以下一般都可以直接添加悬浮窗
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean checkOps() {
        try {
            Object object = SApplication.getAppContext().getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = 24;
            arrayOfObject1[1] = Binder.getCallingUid();
            arrayOfObject1[2] = SApplication.getAppContext().getPackageName();
            int m = (Integer) method.invoke(object, arrayOfObject1);
            //4.4至6.0之间的非国产手机，例如samsung，sony一般都可以直接添加悬浮窗
            return m == AppOpsManager.MODE_ALLOWED || !RomUtils.isDomesticSpecialRom();
        } catch (Exception ignore) {
        }
        return false;
    }

    public static void askForFloatWindowPermission(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("需要在应用设置中开启悬浮窗权限，是否前往开启权限？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tryJumpToPermissonPage(context);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private static void tryJumpToPermissonPage(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            switch (RomUtils.getRomName()) {
                case RomUtils.ROM_MIUI:
                    applyMiuiPermission(context);
                    break;
                case RomUtils.ROM_EMUI:
                    applyHuaweiPermission(context);
                    break;
                case RomUtils.ROM_VIVO:
                    applyVivoPermission(context);
                    break;
                case RomUtils.ROM_OPPO:
                    applyOppoPermission(context);
                    break;
                case RomUtils.ROM_QIKU:
                    apply360Permission(context);
                    break;
                case RomUtils.ROM_SMARTISAN:
                    applySmartisanPermission(context);
                    break;
                case RomUtils.ROM_COOLPAD:
                    applyCoolpadPermission(context);
                    break;
                case RomUtils.ROM_ZTE:
                    applyZTEPermission(context);
                    break;
                case RomUtils.ROM_LENOVO:
                    applyLenovoPermission(context);
                    break;
                case RomUtils.ROM_LETV:
                    applyLetvPermission(context);
                    break;
                default:
                    Toast.makeText(context, TOAST_HINT, Toast.LENGTH_LONG).show();
            }
        } else {
            if (RomUtils.isMeizuRom()) {
                applyMeizuPermission(context);
            } else {
                applyCommonPermission(context);
            }
        }
    }

    private static boolean startActivitySafely(Intent intent, Context context) {
        if (isIntentAvailable(intent, context)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    private static boolean isIntentAvailable(Intent intent, Context context) {
        return intent != null && context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    private static void showAlertToast(Context context){
        Toast.makeText(context, TOAST_HINT, Toast.LENGTH_LONG).show();
    }

    private static void applyCommonPermission(Context context) {
        try {
            Class clazz = Settings.class;
            Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
            Intent intent = new Intent(field.get(null).toString());
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            startActivitySafely(intent, context);
        } catch (Exception e) {
            showAlertToast(context);
        }
    }

    private static void applyCoolpadPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.yulong.android.seccenter", "com.yulong.android.seccenter.dataprotection.ui.AppListActivity");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyLenovoPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.lenovo.safecenter", "com.lenovo.safecenter.MainTab.LeSafeMainActivity");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyZTEPermission(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.zte.heartyservice.intent.action.startActivity.PERMISSION_SCANNER");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyLetvPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AppActivity");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyVivoPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.FloatWindowManager");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyOppoPermission(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.setAction("com.oppo.safe");
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        if (!startActivitySafely(intent, context)) {
            intent.setAction("com.color.safecenter");
            intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.floatwindow.FloatWindowListActivity");
            if (!startActivitySafely(intent, context)) {
                intent.setAction("com.coloros.safecenter");
                intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
                if (!startActivitySafely(intent, context)) {
                    showAlertToast(context);
                }
            }
        }
    }

    private static void apply360Permission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        if (!startActivitySafely(intent, context)) {
            intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
            if (!startActivitySafely(intent, context)) {
                showAlertToast(context);
            }
        }
    }

    private static void applyMiuiPermission(Context context) {
        Intent intent = new Intent();
        intent.setAction("miui.intent.action.APP_PERM_EDITOR");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("extra_pkgname", context.getPackageName());
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        intent.putExtra("packageName", context.getPackageName());
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");
            intent.setComponent(comp);
            if (!startActivitySafely(intent, context)) {
                comp = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");
                intent.setComponent(comp);
                context.startActivity(intent);
            }
        } catch (SecurityException e) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            showAlertToast(context);
        }
    }

    private static void applySmartisanPermission(Context context) {
        Intent intent = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS_NEW");
        intent.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
        intent.putExtra("index", 17); //有版本差异,不一定定位正确
        if (!startActivitySafely(intent, context)) {
            intent = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS");
            intent.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
            intent.putExtra("permission", new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});
            if (!startActivitySafely(intent, context)) {
                showAlertToast(context);
            }
        }
    }
}
