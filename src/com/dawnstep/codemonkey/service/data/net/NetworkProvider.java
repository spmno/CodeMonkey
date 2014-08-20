package com.dawnstep.codemonkey.service.data.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import com.dawnstep.codemonkey.service.data.DataListener;


public abstract class NetworkProvider {
	
	private DataListener networkListener;
	
	public void getData(DataListener listener) {
		networkListener = listener;
		GetDataThread getDataThread = new GetDataThread();
		getDataThread.start();
	}
	
	protected void getDataImp() {
		
		String urlPath = getUrl();
		
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlPath);
		StringBuilder builder = new StringBuilder();  
		JSONArray jsonArray = null;  
		
		try {
			HttpResponse response = client.execute(httpGet);  
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {  
                builder.append(s);  
            } 
            jsonArray = new JSONArray(builder.toString());
            int listLength = jsonArray.length();
            for (int i = 0; i < listLength; ++i) {
            	saveData(i, jsonArray);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		networkListener.dataArrived();
	}
	
	protected abstract String getUrl();
	protected abstract void saveData(int i, JSONArray jsonArray);
	
	class GetDataThread extends Thread {
		
		@Override
		public void run() {
			getDataImp();
		}
	}
}
