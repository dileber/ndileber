package com.drcosu.ndileber.tools.log;

import com.drcosu.ndileber.app.BaseConfiger;
import com.drcosu.ndileber.app.ThreadExecutor;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 添加本地环境下socket 日志系统
 * Created by WaTaNaBe on 2017/11/9.
 */

public class SocketLog {

//    private static final String wsurl = "ws://192.168.1.94:8080/myHandler?userId=";

    private static String wsurl;
    private static WebSocketClient mWebSocketClient;
    private static final String TAG = "SocketLog";

    public static void init(String wsurl){
        SocketLog.wsurl = wsurl;
        if(BaseConfiger.BUG_STATIC){
            if(mWebSocketClient == null) {
                ULog.i(TAG,"注册Socket日志：",wsurl);
                try {
                    mWebSocketClient = new WebSocketClient(new URI(wsurl)) {
                        @Override
                        public void onOpen(ServerHandshake serverHandshake) {
                            //连接成功
                            ULog.i(TAG,"opened connection");
                        }
                        @Override
                        public void onMessage(String s) {
                            //服务端消息
                            ULog.i(TAG,"received:" + s);
                        }


                        @Override
                        public void onClose(int i, String s, boolean remote) {
                            //连接断开，remote判定是客户端断开还是服务端断开

                            ULog.i(TAG,"Connection closed by " + ( remote ? "remote peer" : "us" ) + ", info=" + s);
                            closeConnect();
                        }


                        @Override
                        public void onError(Exception e) {
                            ULog.i(TAG,"error:" + e);
                        }
                    };
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //连接
    public static void connect() {
        if(BaseConfiger.BUG_STATIC){
            ThreadExecutor.getInstance().submit(new Runnable() {
                @Override
                public void run() {
                    mWebSocketClient.connect();
                }
            });
        }
    }


    //断开连接
    private static void closeConnect() {
        try {
            mWebSocketClient.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            mWebSocketClient = null;
        }
    }


//发送消息
    /**
     *
     */
    public static void log(String msg) {
        if(BaseConfiger.BUG_STATIC){
            if(mWebSocketClient!=null&&mWebSocketClient.isOpen()){
                mWebSocketClient.send(msg);
            }
        }
    }
}
