package com.dawnstep.codemonkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsManager {
	private NewsManager(){}
	private static final NewsManager newsManager = new NewsManager();
	List<News> newsList = new ArrayList<News>();
	Map<String, List<NewsImage>> newsImageContainer = new HashMap<String, List<NewsImage>>();
	
	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public static NewsManager getInstance() {
		return newsManager;
	}
	
	
	private int currentOffset = 0;
	
	public void addNewsItem(News item) {
		newsList.add(item);
		currentOffset++;
	}
	
	public int getOffset() {
		return currentOffset;
	}
	
	public void addNewsImageItem(String newsId, NewsImage newsImage) {
		List<NewsImage> newsList = newsImageContainer.get(newsId);
		if (newsList != null) {
			newsList.add(newsImage);
		} else {
			newsList = new ArrayList<NewsImage>();
			newsList.add(newsImage);
			newsImageContainer.put(newsId, newsList);
		}
	}
	
	public List<NewsImage> getNewsImage(String newsId) {
		return newsImageContainer.get(newsId);
	}
	
}
