package com.drcosu.ndileber.tools.net;

import android.app.Activity;
import android.util.Log;

import com.drcosu.ndileber.app.ActivityManager;
import com.drcosu.ndileber.tools.log.ULog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by shidawei on 2017/5/10.
 */

public class RetManager {

    private static Map<String,List<Call>> req ;

    public static volatile RetManager instance ;

    private RetManager(){
        req = new HashMap<>();
    }

    public static RetManager getInstance() {
        if (instance == null) {
            synchronized (RetManager.class) {
                instance = new RetManager();
            }
        }
        return instance;
    }


    public void clearRequest(Activity act){
        synchronized (req){
            if(act!=null){
                String name = act.getLocalClassName();
                if(req.containsKey(name)){
                    ULog.d(name,"停止网络请求");
                    List<Call> lo = req.get(name);
                    for(int i=0;i<lo.size();i++){
                        Call call =lo.get(i);
                        call.cancel();
                    }
                    lo.clear();
                }
            }


        }
    }

    public void addRequest(Call call){
        synchronized (req){
            Activity act = ActivityManager.getCurrentActivity();
            if(act!=null){
                String name = act.getLocalClassName();
                ULog.d(name,"添加一个网络请求");

                if(req.containsKey(name)){
                    req.get(name).add(call);
                }else{
                    List<Call> lo = new ArrayList<>();
                    lo.add(call);
                    req.put(name,lo);
                }
            }
        }
    }


}
