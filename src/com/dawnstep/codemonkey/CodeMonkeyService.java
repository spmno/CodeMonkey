package com.dawnstep.codemonkey;


import com.dawnstep.codemonkey.ConnectManager.ConnectStatus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;



public class CodeMonkeyService extends Service {
	
    private static final String TAG = "CodeMonkeyService"; 
    private CodeMonkeyBinder mNewBinder = new CodeMonkeyBinder();
    private NewsDataProvider currentDataProvider;
    private Context appContext;
	public CodeMonkeyService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		Log.i(TAG, "onBind");
		return mNewBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = getApplicationContext();
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
		/* only for the test
		public List<Map<String, Object>> getNews() {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("content", "test1");
	        
	        data.add(map);
	        
	        map.put("content", "test2");
	        
	        data.add(map);
	         
	        return data;
		}
		*/
		public void addDataArrivedListener(NewsDataListener listener) {
			currentDataProvider.add(listener);
		}
		
		public void getNews() {
			currentDataProvider.getNews();
		}

	}
}
