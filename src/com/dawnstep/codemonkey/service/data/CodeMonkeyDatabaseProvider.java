package com.dawnstep.codemonkey.service.data;


public class CodeMonkeyDatabaseProvider extends CodeMonkeyDataProvider {
	
	@Override
	public void getNews() {
		// TODO Auto-generated method stub
		NewsDataDatabaseProvider newsDataDatabaseProvider = new NewsDataDatabaseProvider();
		newsDataDatabaseProvider.getData(dataListener);
	}


	@Override
	public void getNewSkillKindGets() {
		// TODO Auto-generated method stub
		NewSkillGetKindDatabaseProvider newSkillGetKindDatabaseProvider = new NewSkillGetKindDatabaseProvider();
		newSkillGetKindDatabaseProvider.getData(dataListener);
	}


	@Override
	public void getNewSkillGets() {
		// TODO Auto-generated method stub
		NewSkillGetDatabaseProvider newSkillGetDatabaseProvider = new NewSkillGetDatabaseProvider();
		newSkillGetDatabaseProvider.getData(dataListener);
	}

}
