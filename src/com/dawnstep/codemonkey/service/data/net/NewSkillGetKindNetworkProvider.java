package com.dawnstep.codemonkey.service.data.net;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetKindManager;
import com.dawnstep.codemonkey.service.data.CodeMonkeyDatabaseHelper;
import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;
import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;
import com.j256.ormlite.dao.Dao;

public class NewSkillGetKindNetworkProvider extends NetworkProvider {

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
	
	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		String urlPath = CodeMonkeyConfig.getNewSkillGetKindNetPath();
		return urlPath;
	}

	@Override
	protected void saveData(int i, JSONArray jsonArray) {
		// TODO Auto-generated method stub
		NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
		JSONObject jsonObject;
		try {
			jsonObject = jsonArray.getJSONObject(i);
		  	//check is exist in db
	    	String newSkillGetKindId = jsonObject.getString("id");    	
	    	boolean saveFlag = isExist(Integer.valueOf(newSkillGetKindId));
	    	String title = jsonObject.getString("title");

	    	NewSkillGetKind newSkillGetKind = new NewSkillGetKind();
	    	newSkillGetKind.setNewSkillGetKindId(newSkillGetKindId);
	    	newSkillGetKind.setTitle(title);
	    	newSkillGetKindManager.addNewSkillGetKindItem(newSkillGetKind);
	    	if (!saveFlag)
	    		saveNewSkillGetKindToDatabase(newSkillGetKind);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  
	}
}
