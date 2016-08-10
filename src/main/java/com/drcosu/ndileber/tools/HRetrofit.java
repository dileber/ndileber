package com.drcosu.ndileber.tools;

import com.drcosu.ndileber.app.FrameContants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.framed.FramedConnection;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by shidawei on 16/8/5.
 */
public class HRetrofit {

    public Retrofit retrofit = null;

    private HRetrofit(String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())
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

    private OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", HPref.getInstance().get(FrameContants.SYSTEM_PREFERANCE,FrameContants.SYSTEM_PREFERANCE_SESSION,"",String.class))
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

}
