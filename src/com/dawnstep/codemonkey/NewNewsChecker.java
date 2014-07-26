package com.dawnstep.codemonkey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class NewNewsChecker {
	
	private List<NewNewsCheckerListener> newNewsCheckerContainer = new ArrayList<NewNewsCheckerListener>();

	public boolean isNew() {
		String urlPath = NewsConfig.getLastNewsDatePath();//"http://115.29.139.76:3000/infos/last_info_time.json";
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlPath);
		StringBuilder builder = new StringBuilder();  
		int compareResult = 0;
		try {
			HttpResponse response = client.execute(httpGet);  
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {  
                builder.append(s);  
            } 
            JSONObject timeJson = new JSONObject(builder.toString());
            String timeString = timeJson.getString("time");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Date updateDate = formatter.parse(timeString);
            
            NewsDatabaseHelper newsDatabaseHelper = NewsDatabaseHelper.getInstance();
			Dao<News, Integer> newsDao = newsDatabaseHelper.getNewsDao();
			QueryBuilder<News, Integer> queryBuilder = newsDao.queryBuilder();
			queryBuilder.orderBy("updateTime", false).limit(1L);
			PreparedQuery<News> preparedQuery = queryBuilder.prepare();  
			List<News> newsList = newsDao.query(preparedQuery);  
			News lastNews = newsList.get(0);
			compareResult = lastNews.getUpdateTime().compareTo(updateDate);
			if (compareResult < 0) {
				return true;
			}
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void startCheck() {
		NewsCheckerThread newsCheckerThread = new NewsCheckerThread();
		newsCheckerThread.start();
	}
	
	class NewsCheckerThread extends Thread {
		
		@Override 
		public void run() {
			int compareResult = 0;
			if (isNew()) {
				compareResult = 1; 
			}
			
			for (NewNewsCheckerListener listener : newNewsCheckerContainer) {
				listener.onResult(compareResult);
			}
		
		}
	}
}
