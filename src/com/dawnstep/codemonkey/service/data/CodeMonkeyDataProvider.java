package com.dawnstep.codemonkey.service.data;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeMonkeyDataProvider {
	public abstract void getNews();
	public abstract void getNewSkillGets();
	List<NewsDataListener> newsDataListenerContainer = new ArrayList<NewsDataListener>();
	List<NewSkillGetKindListener> newSkillGetKindListenerContainer = new ArrayList<NewSkillGetKindListener>();
	public void addNewsDataListener(NewsDataListener listener) {
		// TODO Auto-generated method stub
		newsDataListenerContainer.add(listener);
	}
	public void addNewSkillGetKindListener(NewSkillGetKindListener listener) {
		newSkillGetKindListenerContainer.add(listener);
	}
}
