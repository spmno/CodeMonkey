package com.dawnstep.codemonkey.auth;

import android.os.AsyncTask;

public class LoginAsyncTask extends AsyncTask<Void, Integer, Boolean> {

	private AuthenticationActivity parentActivity;
	private String name;
	private String password;
	private Authenticator authenticator = new Authenticator();
	LoginAsyncTask(AuthenticationActivity activity, String name, String password) {
		this.parentActivity = activity;
		this.name = name;
		this.password = password;
	}
	@Override
	protected Boolean doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		return authenticator.authenticate(name, password);
	}

	 @Override  
	 protected void onPostExecute(Boolean result) {  
	     if (result == true) {
	    	 this.parentActivity.finish();
	     } else {
	    	 
	     }
	 } 

}
