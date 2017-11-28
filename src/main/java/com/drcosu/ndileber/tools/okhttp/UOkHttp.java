package com.drcosu.ndileber.tools.okhttp;

import com.drcosu.ndileber.tools.SSystem;
import com.drcosu.ndileber.tools.SysInfoUtil;
import com.drcosu.ndileber.tools.TVersion;
import com.drcosu.ndileber.tools.net.TCookie;
import com.drcosu.ndileber.tools.storage.StorageType;
import com.drcosu.ndileber.tools.storage.UStorage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shidawei on 2017/6/9.
 */

public class UOkHttp {

    private static volatile UOkHttp instance;
    public static UOkHttp getInstance() {
        if (instance == null) {
            synchronized (UOkHttp.class) {
                instance = new UOkHttp();
            }
        }
        return instance;
    }

    public UOkHttp(){
        okHttpClient = genericClient();
    }

    public OkHttpClient okHttpClient;

    //private static File cacheDirectory = new File(MyApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //设缓存有效期为10秒
    protected static final long CACHE_STALE_MIN = 10;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";
    //@Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
    //@Headers("Cache-Control: public," + CACHE_CONTROL_NETWORK)
    Cache cache = new Cache(new File(UStorage.getDirectoryByDirType(StorageType.TYPE_TEMP)), 10 * 1024 * 1024);


    private OkHttpClient genericClient() {

        //.addHeader("Accept-Encoding", "gzip, deflate")  问题代码(开启压缩的服务器可以使用)
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", TCookie.getCookie())
                                .addHeader("platform", "android")//平台
                                .addHeader("sysVersion", "0")//系统版本号
                                .addHeader("device", "device")//设备信息
                                .addHeader("screen", SSystem.getScreen().getScreenWidth()+"x"+ SSystem.getScreen().getScreenHeight())//屏幕大小
                                .addHeader("uuid", SSystem.getOnlyId())//设备唯一码
                                .addHeader("version", "0")//app版本
                                .addHeader("networkType", "0")//网络类型
                                .build();
                        return chain.proceed(request);
                    }

                }).addInterceptor(new LogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        return httpClient;
    }

}
