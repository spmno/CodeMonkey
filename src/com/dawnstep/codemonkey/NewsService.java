package com.dawnstep.codemonkey;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class NewsService extends Service {
	
    private static final String TAG = "NewsService"; 
    private NewsBinder mNewBinder = new NewsBinder();
	public NewsService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		return mNewBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	

	
	public class NewsBinder extends Binder {
		public List<String> getNews() {
			List<String> data = new ArrayList<String>();
	        data.add("测试数据1");
	        data.add("测试数据2");
	        data.add("测试数据3");
	        data.add("测试数据4");
	         
	        return data;
		}
	}
}
