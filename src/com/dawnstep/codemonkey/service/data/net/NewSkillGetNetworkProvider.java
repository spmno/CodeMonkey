package com.dawnstep.codemonkey.service.data.net;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetManager;
import com.dawnstep.codemonkey.service.data.CodeMonkeyDatabaseHelper;
import com.dawnstep.codemonkey.service.data.database.NewSkillGet;
import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;
import com.j256.ormlite.dao.Dao;

public class NewSkillGetNetworkProvider extends NetworkProvider {
	
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
	
	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		NewSkillGetManager newSkillGetManager = NewSkillGetManager.getInstance();
		String currentSkillGetKindId = newSkillGetManager.getCurrentSkillGetKind();
		String urlPath = CodeMonkeyConfig.getNewSkillGetNetPath(currentSkillGetKindId);
		return urlPath;
	}

	@Override
	protected void saveData(int i, JSONArray jsonArray) {
		// TODO Auto-generated method stub
		NewSkillGetManager newSkillGetManager = NewSkillGetManager.getInstance();
		JSONObject jsonObject;
		try {
			jsonObject = jsonArray.getJSONObject(i);
	    	String newSkillGetKindId = jsonObject.getString("id");    	
	    	boolean saveFlag = isExist(Integer.valueOf(newSkillGetKindId));
	    	String title = jsonObject.getString("title");

	    	NewSkillGet newSkillGet = new NewSkillGet();
	    	newSkillGet.setTitle(title);
	    	newSkillGetManager.addNewSkillGetItem(newSkillGet);
	    	if (!saveFlag)
	    		saveNewSkillGetToDatabase(newSkillGet);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	//check is exist in db

	}
}
