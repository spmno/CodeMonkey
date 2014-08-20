package com.dawnstep.codemonkey.service.data;


public abstract class CodeMonkeyDataProvider {
	public abstract void getNews();
	public abstract void getNewSkillKindGets();
	public NewsDataListener newsDataListener;
	public NewSkillGetKindListener newSkillGetKindListener;;
	public void addNewsDataListener(NewsDataListener listener) {
		// TODO Auto-generated method stub
		newsDataListener = listener;
	}
	
	public void addNewSkillGetKindListener(NewSkillGetKindListener listener) {
		newSkillGetKindListener = listener;
	}
	
}
