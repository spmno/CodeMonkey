package com.dawnstep.codemonkey.service.data.net;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.dawnstep.codemonkey.news.NewsManager;
import com.dawnstep.codemonkey.service.data.CodeMonkeyDatabaseHelper;
import com.dawnstep.codemonkey.service.data.database.News;
import com.dawnstep.codemonkey.service.data.database.NewsImage;
import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;
import com.j256.ormlite.dao.Dao;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsDataNetworkProvider extends NetworkProvider {
	
	public void saveNewsToDatabase(News news) {
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<News, Integer> newsDao = newsDatabaseHelper.getNewsDao();
		try {
			newsDao.create(news);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveNewsImageToDatabase(NewsImage image) {
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<NewsImage, Integer> newsImageDao = newsDatabaseHelper.getNewsImageDao();
		try {
			newsImageDao.create(image);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isExist(Integer newsId) {
		CodeMonkeyDatabaseHelper newsDatabaseHelper = CodeMonkeyDatabaseHelper.getInstance();
		Dao<News, Integer> newsDao = newsDatabaseHelper.getNewsDao();
		try {
			if (newsDao.queryForId(newsId) != null) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		NewsManager newsManager = NewsManager.getInstance();
		String urlPath = CodeMonkeyConfig.getNewsNetPath();
		int offset = newsManager.getOffset();
		String urlPathWithParam = urlPath 
				+ "?"
				+ "offset="
				+ String.valueOf(offset);
		return urlPathWithParam;
	}

	@Override
	protected void saveData(int i, JSONArray jsonArray) {
		// TODO Auto-generated method stub
		
		NewsManager newsManager = NewsManager.getInstance();
     	JSONObject jsonObject;
		try {
			jsonObject = jsonArray.getJSONObject(i);
	    	//check is exist in db
	    	String newsId = jsonObject.getString("id");
	    	boolean saveFlag = isExist(Integer.valueOf(newsId));
	          	
	    	String title = jsonObject.getString("title");
	    	String content = jsonObject.getString("content");
	    	String dateString = jsonObject.getString("updated_at");
	    	JSONArray imageJsonArray = jsonObject.getJSONArray("images");
	       	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
	    	Date updateDate = formatter.parse(dateString);
	    	News news = new News();
	    	news.setNewsId(newsId);
	    	news.setTitle(title);
	    	news.setContent(content);
	    	news.setUpdateTime(updateDate);
	    	
	    	if (!saveFlag)
	    		saveNewsToDatabase(news);
	    	
	    	newsManager.addNewsItem(news);
	    	
	    	if (imageJsonArray == null) {
	    		return;
	    	}
	    	
	    	int imageJsonArrayLength = imageJsonArray.length();

	    	for (int j = 0; j < imageJsonArrayLength; j++) {
	    		JSONObject imageJsonObject = imageJsonArray.getJSONObject(j);
	    		String imageUrl = imageJsonObject.getString("photo");
	    		if ((imageUrl == null) || (imageUrl.equals("null"))) {
	    			continue;
	    		}
	    		final NewsImage newsImage = new NewsImage();
	    		newsImage.setImageURL(imageUrl);
	    		newsImage.setNewsId(news);
	    		ImageLoader imageLoader = ImageLoader.getInstance(); 

	    		String absolutelyImageUrl = CodeMonkeyConfig.getNetRootPath() + imageUrl;
	    		Bitmap bitmap = imageLoader.loadImageSync(absolutelyImageUrl);
	    		ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

			    byte[] imageBytes =  baos.toByteArray();
			    newsImage.setImageBytes(imageBytes);
			    
			    if (!saveFlag)
			    	saveNewsImageToDatabase(newsImage);
			    
			    newsManager.addNewsImageItem(news.getNewsId(), newsImage);
	    	}
		} catch (JSONException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
	}
}
