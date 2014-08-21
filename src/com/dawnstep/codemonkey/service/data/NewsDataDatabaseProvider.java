package com.dawnstep.codemonkey.service.data;

import java.sql.SQLException;
import java.util.List;

import com.dawnstep.codemonkey.news.NewsManager;
import com.dawnstep.codemonkey.service.data.database.News;
import com.dawnstep.codemonkey.service.data.database.NewsImage;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class NewsDataDatabaseProvider extends DatabaseProvider {

	@Override
	protected void saveData() {
		// TODO Auto-generated method stub
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

	}

}
