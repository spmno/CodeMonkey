package com.dawnstep.codemonkey.service.data;


public abstract class CodeMonkeyDataProvider {
	public abstract void getNews();
	public abstract void getNewSkillKindGets();
	public abstract void getNewSkillGets();
	public DataListener dataListener;

	public void addDataListener(DataListener listener) {
		// TODO Auto-generated method stub
		dataListener = listener;
	}
	
}
