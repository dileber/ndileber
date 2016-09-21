package com.drcosu.ndileber.tools.net;

import com.drcosu.ndileber.app.FrameContants;
import com.drcosu.ndileber.tools.HPref;

/**
 * Created by shidawei on 16/9/21.
 */
public class TCookie {

    public static void putCookies(String cookie){
        HPref.getInstance().put(FrameContants.SYSTEM_PREFERANCE,FrameContants.SYSTEM_PREFERANCE_SESSION,cookie);
    }

    public static String getCookie(){
        return HPref.getInstance().get(FrameContants.SYSTEM_PREFERANCE,FrameContants.SYSTEM_PREFERANCE_SESSION,"",String.class);
    }

    public static void clearCookie(){
        HPref.getInstance().remove(FrameContants.SYSTEM_PREFERANCE,FrameContants.SYSTEM_PREFERANCE_SESSION);
    }

}
