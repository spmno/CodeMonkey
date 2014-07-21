package com.dawnstep.codemonkey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.j256.ormlite.dao.Dao;

public class NewsDataNetworkProvider extends NewsDataProvider {

	@Override
	public void getNews() {
		// TODO Auto-generated method stub
		GetNewsThread getNewsThread = new GetNewsThread();
		getNewsThread.start();
	}
	
	public void saveNewsToDatabase(News news) {
		NewsDatabaseHelper newsDatabaseHelper = NewsDatabaseHelper.getInstance();
		Dao<News, Integer> newsDao = newsDatabaseHelper.getNewsDao();
		try {
			newsDao.create(news);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	class GetNewsThread extends Thread {
		
		@Override
		public void run() {
			NewsManager newsManager = NewsManager.getInstance();
			//String urlPath = "http://192.168.2.231:3000/infos.json";
			String urlPath = "http://115.29.139.76:3000//infos.json";
			int offset = newsManager.getOffset();
			String urlPathWithParam = urlPath 
					+ "?"
					+ "offset="
					+ String.valueOf(offset);
			
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urlPathWithParam);
			StringBuilder builder = new StringBuilder();  
			JSONArray jsonArray = null;  
			
			try {
				HttpResponse response = client.execute(httpGet);  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
	            for (String s = reader.readLine(); s != null; s = reader.readLine()) {  
	                builder.append(s);  
	            } 
	            jsonArray = new JSONArray(builder.toString());
	            int listLength = jsonArray.length();
	            for (int i = 0; i < listLength; ++i) {
	            	JSONObject jsonObject = jsonArray.getJSONObject(i);  
	            	News news = new News();
	            	String newsId = jsonObject.getString("id");
	            	String title = jsonObject.getString("title");
	            	String content = jsonObject.getString("content");
	            	JSONArray imageJsonArray = jsonObject.getJSONArray("images");
	            	int imageJsonArrayLength = imageJsonArray.length();
	            	List<String> imagesURLList = new ArrayList<String>();
	            	for (int j = 0; j < imageJsonArrayLength; j++) {
	            		JSONObject imageJsonObject = imageJsonArray.getJSONObject(j);
	            		String imageUrl = imageJsonObject.getString("photo");
	            		imagesURLList.add(imageUrl);
	            	}
	            	news.setNewsId(newsId);
	            	news.setTitle(title);
	            	news.setContent(content);
	            	news.setImages(imagesURLList);
	            	saveNewsToDatabase(news);
	            	newsManager.addNewsItem(news);
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (NewsDataListener listener : listenerContainer) {
				listener.dataArrived();
			}
		}
	}

}
