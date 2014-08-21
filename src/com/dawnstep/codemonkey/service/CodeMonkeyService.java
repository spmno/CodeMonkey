package com.dawnstep.codemonkey.service;


import com.dawnstep.codemonkey.ConnectManager;
import com.dawnstep.codemonkey.ConnectManager.ConnectStatus;
import com.dawnstep.codemonkey.service.data.CodeMonkeyDataProvider;
import com.dawnstep.codemonkey.service.data.CodeMonkeyDatabaseHelper;
import com.dawnstep.codemonkey.service.data.DataListener;
import com.dawnstep.codemonkey.service.data.NewsDataProviderFactory;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;



public class CodeMonkeyService extends Service {
	
    private static final String TAG = "CodeMonkeyService"; 
    private CodeMonkeyBinder newBinder = new CodeMonkeyBinder();
    private CodeMonkeyDataProvider currentDataProvider;
    private Context appContext;
	public CodeMonkeyService() {
	}

	private void updateProvider() {
		ConnectManager connectManager = new ConnectManager();
		if (connectManager.getNetStatus(appContext) == ConnectStatus.NONE_CONNECTED) {
			currentDataProvider = 
					NewsDataProviderFactory.getInstance().
					createNewsDataProvider(NewsDataProviderFactory.ProviderType.DatabaseProvider);
		} else {
			currentDataProvider = 
					NewsDataProviderFactory.getInstance().
					createNewsDataProvider(NewsDataProviderFactory.ProviderType.NetProvider);
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		Log.i(TAG, "onBind");
		return newBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = getApplicationContext();
		updateProvider();
		CodeMonkeyDatabaseHelper.setContext(appContext);

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	

	
	public class CodeMonkeyBinder extends Binder {

		public void addNewsDataArrivedListener(DataListener listener) {
			currentDataProvider.addDataListener(listener);
		}
		
			
		public void addNewSkillGetKindListener(DataListener listener) {
			currentDataProvider.addDataListener(listener);
		}
		
		public void addNewSkillGetListener(DataListener listener) {
			currentDataProvider.addDataListener(listener);
		}
		
		public void getNews() {
			//updateProvider();
			currentDataProvider.getNews();
		}
		
		public void getNewSkillGets() {
			//updateProvider();
			currentDataProvider.getNewSkillKindGets();
		}

		public void getNewSkillKindGets() {
			//updateProvider();
			currentDataProvider.getNewSkillKindGets();
		}
	}
}
