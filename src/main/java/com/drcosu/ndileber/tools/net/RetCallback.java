package com.drcosu.ndileber.tools.net;

import com.drcosu.ndileber.app.SApplication;
import com.orhanobut.logger.Logger;

import java.util.Set;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.internal.framed.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shidawei on 16/8/5.
 */
public abstract class RetCallback<T> implements Callback<T>{


    protected abstract void onSuccess(Call<T> call, Response<T> response);

    protected abstract void failure(Call<T> call, Throwable throwable);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        RetLog.log(call);
        onSuccess(call, response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        RetLog.log(call);
        failure(call,throwable);
    }

    public void log(Call<T> call){

        if(SApplication.netLog){

            StringBuilder sb = new StringBuilder("");
            Request request = call.request();
//            Headers headers = request.headers();
//            Set<String> name = headers.names();
//            for(String n : name){
//                sb.append("\t{").append(n).append(" = ").append(headers.get(n)).append("}");
//            }
            sb.append("请求URL:"+request.url()+" 请求METHOD:"+request.method()+" 请求是否是https:"+request.isHttps());
            Logger.d(sb.toString());
        }
    }



}
