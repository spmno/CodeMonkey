package com.dawnstep.codemonkey;

import com.dawnstep.codemonkey.service.CodeMonkeyService;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


public class CodeMonkeyApplication extends Application {
	private CodeMonkeyConnection codeMonkeyConnection;
	private CodeMonkeyService.CodeMonkeyBinder codeMonkeyBinder;

	
	@Override
    public void onCreate() {
        super.onCreate();
		Intent intent = new Intent(this, CodeMonkeyService.class);
		codeMonkeyConnection = new CodeMonkeyConnection();
		bindService(intent, codeMonkeyConnection, Context.BIND_AUTO_CREATE);
    }
    
	public CodeMonkeyService.CodeMonkeyBinder getCodeMonkeyBinder() {
		return codeMonkeyBinder;
	}
	
	public class CodeMonkeyConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder iBinder) {
			// TODO Auto-generated method stub
			codeMonkeyBinder = (CodeMonkeyService.CodeMonkeyBinder)iBinder;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
