package com.dawnstep.codemonkey.service.data;


public abstract class CodeMonkeyDataProvider {
	public abstract void getNews();
	public abstract void getNewSkillKindGets();
	public DataListener networkListener;
	public NewSkillGetKindListener newSkillGetKindListener;;
	public void addNewsDataListener(DataListener listener) {
		// TODO Auto-generated method stub
		networkListener = listener;
	}
	
	public void addNewSkillGetKindListener(NewSkillGetKindListener listener) {
		newSkillGetKindListener = listener;
	}
	
}
