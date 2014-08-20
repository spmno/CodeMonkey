package com.dawnstep.codemonkey.service.data;

import java.sql.SQLException;
import java.util.List;

import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;
import com.dawnstep.codemonkey.service.data.database.News;
import com.dawnstep.codemonkey.service.data.database.NewsImage;
import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetKindManager;
import com.dawnstep.codemonkey.news.NewsManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class CodeMonkeyDatabaseProvider extends CodeMonkeyDataProvider {

	@Override
	public void getNews() {
		// TODO Auto-generated method stub
		GetNewsThread getNewsThread = new GetNewsThread();
		getNewsThread.start();
	}

	class GetNewsThread extends Thread {
		
		@Override
		public void run() {
			NewsManager newsManager = NewsManager.getInstance();
			CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
			Dao<News, Integer> newsDao = newsDatabaseHelper.getNewsDao();
			Dao<NewsImage, Integer> newsImageDao = newsDatabaseHelper.getNewsImageDao();
			QueryBuilder<News, Integer> queryBuilder = newsDao.queryBuilder();
			long startRow = newsManager.getOffset();
			long maxLimit = 10;
			try {
				queryBuilder.orderBy("updateTime", false).offset(startRow).limit(maxLimit);
				PreparedQuery<News> preparedQuery = queryBuilder.prepare();  
			    List<News> newsList = newsDao.query(preparedQuery);  
			    for (News news : newsList) {
			    	newsManager.addNewsItem(news);
			    	String newsId = news.getNewsId();
			    	List<NewsImage> newsImageList = newsImageDao.queryForEq("newsId_id", newsId);
			    	for (NewsImage newsImage : newsImageList) {
			    		newsManager.addNewsImageItem(newsId, newsImage);
			    	}
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			newsDataListener.dataArrived();
		}
	}

	@Override
	public void getNewSkillKindGets() {
		// TODO Auto-generated method stub
		GetNewSkillGetKindThread getNewSkillGetKindThread = new GetNewSkillGetKindThread();
		getNewSkillGetKindThread.start();
	}
	
	class GetNewSkillGetKindThread extends Thread {
		
		@Override
		public void run() {
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

			newSkillGetKindListener.dataArrived();
			
		}
	}
}
