package com.drcosu.ndileber.tools.debug;

import android.content.Context;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.app.BaseConfiger;
import com.drcosu.ndileber.tools.debug.debugwindows.FloatWindowPermissionChecker;


/**
 *页面debug 工具
 * Created by WaTaNaBe on 2017/11/28.
 */

public class DebugViewHelper {

    private static final String TAG = "DebugViewHelper";

    private static DebugView debugView;

    public static void showFloatView(final Context context,DebugView.DebugViewClickLinsenter mDebugViewClickLinsenter){

        if(BaseConfiger.BUG_STATIC){
            if(!UDebug.isAppOnForeground(context)){
                return;
            }
            if(debugView == null){
                if (!FloatWindowPermissionChecker.checkFloatWindowPermission()) {
                    FloatWindowPermissionChecker.askForFloatWindowPermission(context);
                    return;
                }
                debugView = new DebugView(context.getApplicationContext());
                debugView.setmDebugViewClickLinsenter(mDebugViewClickLinsenter);
                debugView.setImageResource(R.mipmap.dileber_debug);
            }
            debugView.show();
        }

    }

    public static void removeFloatView(){
        if(BaseConfiger.BUG_STATIC){
            if(debugView ==null||debugView.getWindowToken()==null){
                return;
            }
            debugView.dismiss();
        }
    }

}
