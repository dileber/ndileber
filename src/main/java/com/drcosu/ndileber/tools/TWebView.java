package com.drcosu.ndileber.tools;

import android.webkit.WebView;

/**
 * Created by H2 on 2016/10/27.
 */
public class TWebView {

    public static void destroyWebView(WebView webView){
        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            //frag_trust_comm.freeMemory();
            webView.pauseTimers();
            webView.destroy();
            webView = null;
        }
    }



}
