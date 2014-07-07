package com.dawnstep.codemonkey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsManager {
	private NewsManager(){}
	private static final NewsManager newsManager = new NewsManager();
	List<News> newsList = new ArrayList<News>();
	
	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public static NewsManager getInstance() {
		return newsManager;
	}
	
	
	private int currentFirstNumber = 0;
	private int currentLastNumber = 0;
	private int currentOffset = 0;
	
	public void getNews() {
		
	}
	
	public void addNewsItem(News item) {
		newsList.add(item);
		currentOffset++;
	}
	
	public int getOffset() {
		return currentOffset;
	}
	
}
