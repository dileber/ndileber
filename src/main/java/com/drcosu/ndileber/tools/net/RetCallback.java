package com.drcosu.ndileber.tools.net;

import android.net.Network;

import com.drcosu.ndileber.app.SApplication;
import com.drcosu.ndileber.tools.UDialog;
import com.orhanobut.logger.Logger;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketTimeoutException;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shidawei on 16/8/5.
 */
public abstract class RetCallback<T> implements Callback<T>{

    //出错提示
    public static String networkMsg = "网络请求失败,请检查";
    public static String networkTimeOutMsg = "网络请求超时,请检查";
    public static String networkForbiddenMsg = "用户权限没有";
    public static String networkSuccessMsg = "网络请求成功";
    public static String parseMsg;
    public static String unknownMsg;

    //对应HTTP的状态码
    private static final int SUCCESS = 200;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    protected abstract void onSuccess(Call<T> call, Response<T> response);

    protected abstract void failure(Call<T> call, Throwable throwable);

    protected void setCookie(Response<T> response) {
        Headers headers = response.headers();
        //Set<String> name = headers.names();
//        for(String n : name){
//            sb.append("\t{").append(n).append(" = ").append(headers.get(n)).append("}");
//        }

        String cookie = headers.get("Set-Cookie");
        TCookie.putCookies(cookie);
        Logger.d("log:"+cookie);
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        RetLog.log(call);
        switch (response.code()){
            case SUCCESS:
                Logger.d(networkSuccessMsg);
                Logger.o(response.body());
                onSuccess(call, response);
                break;
            case NOT_FOUND:
                failure(call,new NetWorkException(NOT_FOUND,"没有找到该资源"));
                break;
            case REQUEST_TIMEOUT:
                failure(call,new NetWorkException(REQUEST_TIMEOUT,"请求超时"));
                break;
            case INTERNAL_SERVER_ERROR:
                failure(call,new NetWorkException(INTERNAL_SERVER_ERROR,"服务器内部错误"));
                break;
            case BAD_GATEWAY:
                failure(call,new NetWorkException(BAD_GATEWAY,"网关错误"));
                break;
            case SERVICE_UNAVAILABLE:
                failure(call,new NetWorkException(SERVICE_UNAVAILABLE,"服务不可用"));
                break;
            case GATEWAY_TIMEOUT:
                failure(call,new NetWorkException(GATEWAY_TIMEOUT,"网关超时"));
                break;
            case UNAUTHORIZED:
                failure(call,new NetWorkException(UNAUTHORIZED,"身份验证未验证"));
                break;
            case FORBIDDEN:
                Logger.d(networkForbiddenMsg);
                failure(call,new NetWorkException(FORBIDDEN,"服务器没有授权"));
                SApplication.getInstance().appForbidden(call,response,this);
                break;
            default:
                failure(call,new NetWorkException(response.code(),"服务器返回失败"));

        }

//        if(response.code()==SUCCESS){
//            Logger.d(networkSuccessMsg);
//        }
//        if(response.code()== FORBIDDEN){
//            Logger.d(networkForbiddenMsg);
//            SApplication.getInstance().appForbidden(call,response,this);
//        }else{
//            Logger.o(response.body());
//            onSuccess(call, response);
//        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable e) {
        RetLog.log(call);

        Throwable throwable = e;
        //获取最根源的异常
        while(throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }

        if (e instanceof ConnectException) {
                //UDialog.alert(UDialog.DIALOG_ERROR,networkMsg).show();
            Logger.d(networkMsg);
            failure(call,new NetWorkException(networkMsg));
            return;
        }
//        if (e instanceof HttpRetryException) {
//            Logger.d("错误代码"+((HttpRetryException)e).responseCode());
//        }
        if(e instanceof SocketTimeoutException){
            //UDialog.alert(UDialog.DIALOG_ERROR,networkTimeOutMsg).show();
            Logger.d(networkTimeOutMsg);
            failure(call,new NetWorkException(networkTimeOutMsg));
            return;
        }
        Logger.e(e,"网络错误");
        failure(call,e);

//        Throwable t = throwable;
//        while (t != null) {
//            if (t instanceof ConnectException) {
//                UDialog.alert(UDialog.DIALOG_ERROR,networkMsg).show();
//                Logger.d(networkMsg);
//            }
//            if (t instanceof HttpRetryException) {
//                Logger.d("错误代码"+((HttpRetryException)t).responseCode());
//            }
//            if(t instanceof SocketTimeoutException){
//                UDialog.alert(UDialog.DIALOG_ERROR,networkTimeOutMsg).show();
//                Logger.d(networkTimeOutMsg);
//            }
//            t = t.getCause();
//        }
//        Logger.e(throwable,"网络错误");
//        failure(call,throwable);
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
