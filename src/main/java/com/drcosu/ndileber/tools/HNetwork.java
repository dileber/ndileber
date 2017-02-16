package com.drcosu.ndileber.tools;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

import com.drcosu.ndileber.app.SApplication;


public class HNetwork {

	/**
	 * 获取可用的网络信息
	 *
	 * @param context
	 * @return
	 */
	private static NetworkInfo getActiveNetworkInfo(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm.getActiveNetworkInfo();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 */
	public static boolean checkNetwork() {
		// TODO Auto-generated method stub
		NetworkInfo networkInfo = getActiveNetworkInfo(SApplication.getAppContext());
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		} else {
			return false;
		}
	}

	/**
	 * 当前网络是否是wifi网络
	 *
	 * @return
	 */
	public static boolean isWifi() {
		try {
			ConnectivityManager cm = (ConnectivityManager) SApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null) {
				if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static String getNetworkInfo(Context context) {
		String info = "";
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo activeNetInfo = connectivity.getActiveNetworkInfo();
			if (activeNetInfo != null) {
				if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					info = activeNetInfo.getTypeName();
				} else {
					StringBuilder sb = new StringBuilder();
					TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
					sb.append(activeNetInfo.getTypeName());
					sb.append(" [");
					if (tm != null) {
						// Result may be unreliable on CDMA networks
						sb.append(tm.getNetworkOperatorName());
						sb.append("#");
					}
					sb.append(activeNetInfo.getSubtypeName());
					sb.append("]");
					info = sb.toString();
				}
			}
		}
		return info;
	}

}
