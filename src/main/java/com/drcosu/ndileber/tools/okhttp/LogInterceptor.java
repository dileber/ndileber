package com.drcosu.ndileber.tools.okhttp;

import com.drcosu.ndileber.tools.log.ULog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shidawei on 2017/6/9.
 */

public class LogInterceptor  implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间
        ULog.im("发送请求 %s on %s%n%s",request.url(), chain.connection(), request.headers());

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //ResponseBody responseBody = response.peekBody(1024 * 1024);

        ULog.im("接收响应: [%s] %n请求时长: %.1fms%n%s",
                response.request().url(),
                (t2 - t1) / 1e6d,
                response.headers());

        return response;
    }
}
