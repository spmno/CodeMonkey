package com.dawnstep.codemonkey;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectManager {
	
	public enum ConnectStatus {
		PHONE_CONNECTED,
		WIFI_CONNECTED,
		PHONE_AND_WIFI_CONNECTED,
		NONE_CONNECTED,
	}
	
	public ConnectStatus getNetStatus(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) {
		     boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
	         boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
	         if (wifiConnected && mobileConnected) {
	        	 return ConnectStatus.PHONE_AND_WIFI_CONNECTED;
	         } else if (wifiConnected) {
	        	 return ConnectStatus.WIFI_CONNECTED;
	         } else {
	        	 return ConnectStatus.PHONE_CONNECTED;
	         }

		} else {
			return ConnectStatus.NONE_CONNECTED;
		}
	}
	
}
