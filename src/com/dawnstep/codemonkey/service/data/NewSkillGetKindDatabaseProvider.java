package com.dawnstep.codemonkey.service.data;

import java.sql.SQLException;
import java.util.List;

import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetKindManager;
import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class NewSkillGetKindDatabaseProvider extends DatabaseProvider {
	
	@Override
	protected void saveData() {
		NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<NewSkillGetKind, Integer> newSkillGetKindDao = newsDatabaseHelper.getNewSkillGetKindDao();
		QueryBuilder<NewSkillGetKind, Integer> queryBuilder = newSkillGetKindDao.queryBuilder();

		try {
			PreparedQuery<NewSkillGetKind> preparedQuery = queryBuilder.prepare();  
		    List<NewSkillGetKind> newSkillGetKindList = newSkillGetKindDao.query(preparedQuery);  
		    for (NewSkillGetKind newSkillGetKind : newSkillGetKindList) {
		    	newSkillGetKindManager.addNewSkillGetKindItem(newSkillGetKind);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
