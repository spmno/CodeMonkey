package com.dawnstep.codemonkey;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeMonkeyDataProvider {
	public abstract void getNews();
	List<NewsDataListener> listenerContainer = new ArrayList<NewsDataListener>();
	public void add(NewsDataListener listener) {
		// TODO Auto-generated method stub
		listenerContainer.add(listener);
	}
}
