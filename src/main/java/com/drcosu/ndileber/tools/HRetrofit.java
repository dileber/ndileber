package com.drcosu.ndileber.tools;

import com.drcosu.ndileber.tools.net.parser.form.FormConverterFactory;
import com.drcosu.ndileber.tools.okhttp.UOkHttp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by shidawei on 16/8/5.
 */
public class HRetrofit {

    public Retrofit retrofit = null;

    private HRetrofit(String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(FormConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(HJson.getGson()))
                .client(UOkHttp.getInstance().okHttpClient)
                .build();
    }

    private static volatile Map<String, HRetrofit> hRetrofitMap = new ConcurrentHashMap<>();

    public static HRetrofit getInstence(String baseUrl){
        if (baseUrl == null) {
            throw new NullPointerException("baseUrl 不能为空");
        }

        if(!hRetrofitMap.containsKey(baseUrl)){
            synchronized (HRetrofit.class){
                if(!hRetrofitMap.containsKey(baseUrl)){
                    hRetrofitMap.put(baseUrl,new HRetrofit(baseUrl));
                }
            }
        }
        return hRetrofitMap.get(baseUrl);
    }

//    //private static File cacheDirectory = new File(MyApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
//    //设缓存有效期为1天
//    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
//    //设缓存有效期为10秒
//    protected static final long CACHE_STALE_MIN = 10;
//    //查询缓存的Cache-Control设置，使用缓存
//    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
//    //查询网络的Cache-Control设置。不使用缓存
//    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";
//    //@Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
//    //@Headers("Cache-Control: public," + CACHE_CONTROL_NETWORK)
//    Cache cache = new Cache(new File(UStorage.getDirectoryByDirType(StorageType.TYPE_TEMP)), 10 * 1024 * 1024);
//
//    private OkHttpClient genericClient() {
//
//        //.addHeader("Accept-Encoding", "gzip, deflate")  问题代码(开启压缩的服务器可以使用)
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request()
//                                .newBuilder()
//                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "*/*")
//                                .addHeader("Cookie", TCookie.getCookie())
//                                .build();
//                        return chain.proceed(request);
//                    }
//
//                }).connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .cache(cache)
//                .build();
//
//        return httpClient;
//    }

}
