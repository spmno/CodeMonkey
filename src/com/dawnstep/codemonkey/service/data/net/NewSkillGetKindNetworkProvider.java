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

import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetKindManager;
import com.dawnstep.codemonkey.service.data.CodeMonkeyDatabaseHelper;
import com.dawnstep.codemonkey.service.data.NewSkillGetKindListener;
import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;
import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;
import com.j256.ormlite.dao.Dao;

public class NewSkillGetKindNetworkProvider {
	private NewSkillGetKindListener newSkillGetKindListener;
	public void getNewSkillGetKind(NewSkillGetKindListener listener) {
		// TODO Auto-generated method stub
		this.newSkillGetKindListener = listener;
		GetNewSkillGetKindThread getSkillGetKindThread = new GetNewSkillGetKindThread();
		getSkillGetKindThread.start();
	}
	
	public void saveNewSkillGetKindToDatabase(NewSkillGetKind newSkillGetKind) {
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<NewSkillGetKind, Integer> newSkillGetKindDao = newsDatabaseHelper.getNewSkillGetKindDao();
		try {
			newSkillGetKindDao.create(newSkillGetKind);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isExist(Integer newSkillGetKindId) {
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<NewSkillGetKind, Integer> newSkillGetKindDao = newsDatabaseHelper.getNewSkillGetKindDao();
		try {
			if (newSkillGetKindDao.queryForId(newSkillGetKindId) != null) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void getNewSkillGetKindImp() {
		NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
		String urlPath = CodeMonkeyConfig.getNewSkillGetKindNetPath();
		
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
  
            	NewSkillGetKind newSkillGetKind = new NewSkillGetKind();
            	newSkillGetKind.setNewSkillGetKindId(newSkillGetKindId);
            	newSkillGetKind.setTitle(title);
            	newSkillGetKindManager.addNewsItem(newSkillGetKind);
            	if (!saveFlag)
            		saveNewSkillGetKindToDatabase(newSkillGetKind);
       	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		newSkillGetKindListener.dataArrived();

	}
	
	class GetNewSkillGetKindThread extends Thread {
		
		@Override
		public void run() {
			getNewSkillGetKindImp();
		}
	}
}
