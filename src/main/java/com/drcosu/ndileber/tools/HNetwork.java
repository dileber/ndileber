package com.drcosu.ndileber.tools;


import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.drcosu.ndileber.app.SApplication;


public class HNetwork {

	private ConnectivityManager con;
	private static volatile HNetwork instance;

	public static HNetwork getInstance() {
		if (instance == null) {
			synchronized (HNetwork.class) {
				if (instance == null)
					instance = new HNetwork();
			}
		}
		return instance;
	}

	public HNetwork() {
		// TODO Auto-generated constructor stub
		con=(ConnectivityManager) SApplication.getAppContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
	}
	
	/**
	 */
	public boolean checkNetwork() {
		// TODO Auto-generated method stub
		NetworkInfo info=con.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {  
            // 判断当前网络是否已经连接  
            if (info.getState() == State.CONNECTED) {
            	return true;
            }else{
            	return false;
            }
        } 
		return false;
	}

	public boolean isWifi() {
		// TODO Auto-generated method stub
		if(checkNetwork()){
			State wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
			//判断wifi已连接的条件
			if(wifi == State.CONNECTED||wifi==State.CONNECTING){
				return true;
			}
			return false;
		}
		return false;
	}


}
