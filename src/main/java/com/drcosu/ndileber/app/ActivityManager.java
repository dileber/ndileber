package com.drcosu.ndileber.app;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.drcosu.ndileber.tools.log.ULog;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * activity管理,每个类里再也看不到context乱传值了
 * 可以控制多个activity 多例
 * Created by shidawei on 16/6/3.
 */
public class ActivityManager {

    private static final String TAG = "ActivityManager";

    /**
     * activity  用一个栈来管理activity的生命周期 */
    private volatile Stack<Activity> activityStack;

    //private static ActivityManager activityManager;

    public static ActivityManager getInstance(){
//        if(activityManager ==null){
//            synchronized (ActivityManager.class){
//                if(activityManager == null){
//                    activityManager = new ActivityManager();
//                }
//            }
//        }
//        return activityManager;
        return getInstance(BASE);
    }

    private static String BASE = "base";

    private static volatile Map<String, ActivityManager> activityManagerMap = new ConcurrentHashMap<>();

    private String name;

    public static ActivityManager getInstance(String name){
        if(!activityManagerMap.containsKey(name)){
            synchronized (ActivityManager.class){
                if(!activityManagerMap.containsKey(name)){
                    ActivityManager activityManager = new ActivityManager();
                    activityManager.setName(name);
                    activityManagerMap.put(name, activityManager);
                }
            }
        }
        return activityManagerMap.get(name);
    }


    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityManager(){
        activityStack = new Stack<Activity>();
    }
    static WeakReference<Activity> currentActivity;

    /**
     *
     * 通过getCurrentActivity 获得当前的activity
     *
     * 获取顶栈 的 activity
     * @return
     */
    @Deprecated
    public Activity peekTopActivity() {
        return activityStack.peek();
    }

    /**
     * 设置当前活动的activity
     * @param ac
     */
    public static synchronized  void setCurrentActivity(Activity ac) {
        if (ac == null) {
            currentActivity = null;
        } else {
            currentActivity = new WeakReference<Activity>(ac);
        }
    }

    /**
     * 清除当前活动的activity
     * @param ac
     */
    public static synchronized  void clearCurrentActivity(Activity ac) {
        Activity cur = currentActivity == null ? null : currentActivity.get();
        if (cur != null && cur == ac) {
            currentActivity = null;
        }
    }

    /**
     * 得到当前活动的activity
     * @return
     */
    public synchronized static Activity getCurrentActivity() {
        return currentActivity == null ? null : currentActivity.get();
    }

    /**
     * 将Activity入栈
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public void pushActivity(@NonNull Activity activity)
    {
        //if(!activity.isChangingConfigurations()){
            activityStack.add(activity);
            ULog.i("来自: "+getName()+" "+activity.getComponentName().getShortClassName()+" 入栈");
        //}
    }

    /**
     * 退出Activity
     * <功能详细描述>
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public void popActivity(@NonNull Activity activity)
    {
        if (activity != null)
        {
            activityStack.remove(activity);
            ULog.i("来自: "+getName()+" "+activity.getComponentName().getShortClassName()+" 出栈");
            if(!activity.isChangingConfigurations()){
                activity.finish();
                activity = null;
                if(getName().equals(BASE)){
                    if(activityInStack()==0){
                        SApplication.getInstance().quit();
                    }
                }

            }

        }
    }

    /**
     * 栈中有多少activity
     * @return
     */
    public int activityInStack() {
        return activityStack.size();
    }

    /**
     * 退出栈中所有Activity
     */
    public void finishAllActivity()
    {
        while (true){
            if(activityInStack()>0){
                Activity activity = activityStack.pop();
                activity.finish();
                activity = null;
            }else {
                if(getName().equals(BASE))
                    ULog.i("app 退出");
                break;
            }
        }
    }

    public void finishOtherActivity(Class now){
        while (true){
            if(activityInStack()>0){
                Activity activity = activityStack.pop();
                if(now!=null&&activity!=null&&now.getClass().getName().equals(activity.getClass().getName())){
                    continue;
                }
                activity.finish();
                activity = null;
            }else {
                break;
            }
        }
    }

    @Deprecated
    public void finishOtherActivity(NewActivityCallBack newActivityCallBack){
        while (true){
            if(activityInStack()>0){
                Activity activity = activityStack.pop();
                if(activityInStack()==0){
                    newActivityCallBack.createActivity(activity);
                }
                activity.finish();
                activity = null;
            }else {
                break;
            }
        }
    }

    @Deprecated
    public interface NewActivityCallBack{
        void createActivity(Activity activity);
    }

}
