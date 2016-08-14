package com.drcosu.ndileber.app;


import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.drcosu.ndileber.tools.AndroidCrash;
import com.drcosu.ndileber.tools.annotation.SFontdType;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * Created by shidawei on 16/6/2.
 */
public class SApplication extends Application{

	public static Typeface icon_font;

	private static SApplication instance;
	private static Context context;
	public static boolean netLog = true;

	@Override
	public void onCreate() {
		super.onCreate();
		buglog();
		instance = this;
		context = instance.getApplicationContext();
		/**
		 * android 崩溃记录
		 */
		AndroidCrash.getInstance().init();


		Fresco.initialize(context);
		/**
		 * 字体图标注解
		 */
		if (this.getClass().isAnnotationPresent(SFontdType.class)) {
			Logger.i("加载字体图标");
			icon_font = Typeface.createFromAsset(getAssets(), this.getClass().getAnnotation(SFontdType.class).value());
		}

	}
	
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
	}

}
