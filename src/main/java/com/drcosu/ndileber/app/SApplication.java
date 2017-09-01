package com.drcosu.ndileber.app;


import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.drcosu.ndileber.tools.AndroidCrash;
import com.drcosu.ndileber.tools.TKeybord;
import com.drcosu.ndileber.tools.UImagePipelineConfig;
import com.drcosu.ndileber.tools.annotation.SDefaultFont;
import com.drcosu.ndileber.tools.annotation.SFontdType;
import com.drcosu.ndileber.tools.log.ULog;;
import com.drcosu.ndileber.tools.net.RetCallback;
import com.drcosu.ndileber.tools.rx.RxBus;
import com.drcosu.ndileber.tools.storage.UStorage;
import com.facebook.drawee.backends.pipeline.Fresco;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by shidawei on 16/6/2.
 *
 * @SDefaultFont(true) //添加默认
 * @SFontdType("defaultIcomoon.ttf") //添加自定义
 */
public abstract class SApplication extends Application{

	//自定义字体图标
	public static Typeface icon_font;
	//默认字体图标
	public static Typeface default_icon_font;

	public static boolean netLog = true;
	private static SApplication instance;
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		/**
		 * 缓存文件初始化
		 */
		instance = this;
		context = instance.getApplicationContext();

		Initializer.init(this);
		/**
		 * 字体图标注解
		 */
		if (this.getClass().isAnnotationPresent(SFontdType.class)) {
			ULog.i("加载字体图标");
			icon_font = Typeface.createFromAsset(getAssets(), this.getClass().getAnnotation(SFontdType.class).value());
		}
		/**
		 * 默认字体图标注解
		 */
		if(this.getClass().isAnnotationPresent(SDefaultFont.class)){
			if(this.getClass().getAnnotation(SDefaultFont.class).value()){
				default_icon_font = Typeface.createFromAsset(getAssets(),"defaultIcomoon.ttf");
			}
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
	 * 退出整个app
	 */
	public void quit(){
		ULog.i("退出整个app");
		RxBus.removeAllStickyEvents();
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
