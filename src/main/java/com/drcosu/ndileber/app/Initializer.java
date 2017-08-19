package com.drcosu.ndileber.app;

import android.content.Context;

import com.drcosu.ndileber.tools.AndroidCrash;
import com.drcosu.ndileber.tools.UImagePipelineConfig;
import com.drcosu.ndileber.tools.log.ULog;;
import com.drcosu.ndileber.tools.storage.UStorage;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by WaTaNaBe on 2017/8/19.
 */

public class Initializer {

    public static void init(Context context) {
        //初始化存储系统
        UStorage.init(context,null);
        //日志
        ULog.init();
        //android 崩溃记录
        AndroidCrash.getInstance();
        //facebook 图片库加载
        Fresco.initialize(context, UImagePipelineConfig.getOkHttpCacheConfig(context));


    }

}
