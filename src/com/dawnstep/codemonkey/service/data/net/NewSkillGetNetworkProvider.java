package com.dawnstep.codemonkey.service.data.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetManager;
import com.dawnstep.codemonkey.service.data.CodeMonkeyDatabaseHelper;
import com.dawnstep.codemonkey.service.data.NewSkillGetListener;
import com.dawnstep.codemonkey.service.data.database.NewSkillGet;
import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;
import com.j256.ormlite.dao.Dao;

public class NewSkillGetNetworkProvider {
	private NewSkillGetListener newSkillGetListener;
	public void getNewSkillGetKind(NewSkillGetListener listener) {
		// TODO Auto-generated method stub
		this.newSkillGetListener = listener;
		GetNewSkillGetThread getSkillGetThread = new GetNewSkillGetThread();
		getSkillGetThread.start();
	}
	
	public void saveNewSkillGetToDatabase(NewSkillGet newSkillGet) {
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<NewSkillGet, Integer> newSkillGetDao = newsDatabaseHelper.getNewSkillGetDao();
		try {
			newSkillGetDao.create(newSkillGet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isExist(Integer newSkillGetId) {
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<NewSkillGet, Integer> newSkillGetDao = newsDatabaseHelper.getNewSkillGetDao();
		try {
			if (newSkillGetDao.queryForId(newSkillGetId) != null) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void getNewSkillGetImp() {
		NewSkillGetManager newSkillGetManager = NewSkillGetManager.getInstance();
		String currentSkillGetKindId = newSkillGetManager.getCurrentSkillGetKind();
		String urlPath = CodeMonkeyConfig.getNewSkillGetNetPath(currentSkillGetKindId);
		
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
            	JSONObject jsonObject = jsonArray.getJSONObject(i);  
            	//check is exist in db
            	String newSkillGetKindId = jsonObject.getString("id");    	
            	boolean saveFlag = isExist(Integer.valueOf(newSkillGetKindId));
            	String title = jsonObject.getString("title");
  
            	NewSkillGet newSkillGet = new NewSkillGet();
            	newSkillGet.setTitle(title);
            	newSkillGetManager.addNewSkillGetItem(newSkillGet);
            	if (!saveFlag)
            		saveNewSkillGetToDatabase(newSkillGet);
       	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		newSkillGetListener.dataArrived();

	}
	
	class GetNewSkillGetThread extends Thread {
		
		@Override
		public void run() {
			getNewSkillGetImp();
		}
	}
}
