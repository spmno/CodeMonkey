package com.dawnstep.codemonkey;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class NewsDataDatabaseProvider extends NewsDataProvider {

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
			NewsDatabaseHelper newsDatabaseHelper = NewsDatabaseHelper.getInstance();
			Dao<News, Integer> newsDao = newsDatabaseHelper.getNewsDao();
			Dao<NewsImage, Integer> newsImageDao = newsDatabaseHelper.getNewsImageDao();
			QueryBuilder<News, Integer> queryBuilder = newsDao.queryBuilder();
			long startRow = newsManager.getOffset();
			long maxLimit = 10;
			try {
				queryBuilder.orderBy("updateTime", true).offset(startRow).limit(maxLimit);
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

			for (NewsDataListener listener : listenerContainer) {
				listener.dataArrived();
			}
		}
	}
}
