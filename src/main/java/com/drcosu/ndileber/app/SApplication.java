package com.drcosu.ndileber.app;


import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.drcosu.ndileber.tools.AndroidCrash;
import com.drcosu.ndileber.tools.TKeybord;
import com.drcosu.ndileber.tools.UImagePipelineConfig;
import com.drcosu.ndileber.tools.okhttp.UOkHttp;
import com.drcosu.ndileber.tools.annotation.SFontdType;
import com.drcosu.ndileber.tools.net.RetCallback;
import com.drcosu.ndileber.tools.storage.UStorage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by shidawei on 16/6/2.
 */
public abstract class SApplication extends Application{

	public static Typeface icon_font;

	public static Typeface default_icon_font;

	private static SApplication instance;
	private static Context context;
	public static boolean netLog = true;
	public static boolean crash = false;
	public static boolean loadDeaultFont = false;

	public abstract void start();

	@Override
	public void onCreate() {
		super.onCreate();
		/**
		 * 缓存文件初始化
		 */
		instance = this;
		context = instance.getApplicationContext();

		UStorage.init(this,null);
		start();
		buglog();

		/**
		 * android 崩溃记录
		 */
		if(crash){
			AndroidCrash.getInstance();
		}
		Fresco.initialize(context, UImagePipelineConfig.getOkHttpCacheConfig(this));
		/**
		 * 字体图标注解
		 */
		if (this.getClass().isAnnotationPresent(SFontdType.class)) {
			Logger.i("加载字体图标");
			icon_font = Typeface.createFromAsset(getAssets(), this.getClass().getAnnotation(SFontdType.class).value());
		}

		if(loadDeaultFont){
			default_icon_font = Typeface.createFromAsset(getAssets(),"defaultIcomoon.ttf");
		}


		init();
	}

	protected abstract void init();


	public static Context getAppContext() {
		return context;
	}

	public static SApplication getInstance() {
		return instance;
	}

	/**
	 * 输出log 日志
	 */
	protected void buglog(){
		Logger.init(BaseConfiger.BUG_NAME,BaseConfiger.BUG_STATIC);
	}

	/**
	 * 退出整个app
	 */
	public void quit(){
		Logger.i("退出整个app");
		ThreadExecutor.getInstance().stop();
		TKeybord.fixFocusedViewLeak(this);
	}


	/**
	 * 前提使用本框架带的网络请求,当网络请求被发现用户没有权限的时候调用该方法(在这里可以操作的是重新登录或者弹框)
	 * @param call
	 * @param response
     */
	public abstract void appForbidden(Call call, Response response, RetCallback retCallback);


}
