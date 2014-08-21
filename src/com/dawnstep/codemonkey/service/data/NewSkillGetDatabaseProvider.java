package com.dawnstep.codemonkey.service.data;

import java.sql.SQLException;
import java.util.List;

import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetManager;
import com.dawnstep.codemonkey.service.data.database.NewSkillGet;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class NewSkillGetDatabaseProvider extends DatabaseProvider {

	@Override
	protected void saveData() {
		// TODO Auto-generated method stub
		NewSkillGetManager newSkillGetManager = NewSkillGetManager.getInstance();
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<NewSkillGet, Integer> newSkillGetDao = newsDatabaseHelper.getNewSkillGetDao();
		QueryBuilder<NewSkillGet, Integer> queryBuilder = newSkillGetDao.queryBuilder();

		try {
			PreparedQuery<NewSkillGet> preparedQuery = queryBuilder.prepare();  
		    List<NewSkillGet> newSkillGetList = newSkillGetDao.query(preparedQuery);  
		    for (NewSkillGet newSkillGet : newSkillGetList) {
		    	newSkillGetManager.addNewSkillGetItem(newSkillGet);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
