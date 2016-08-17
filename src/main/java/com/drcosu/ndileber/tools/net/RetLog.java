package com.drcosu.ndileber.tools.net;

import com.drcosu.ndileber.app.SApplication;
import com.orhanobut.logger.Logger;

import okhttp3.Request;
import retrofit2.Call;

/**
 * Created by shidawei on 16/8/17.
 */
public class RetLog {

    public static<T> void log(Call<T> call){
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
