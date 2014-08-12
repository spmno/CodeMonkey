package com.dawnstep.codemonkey.service.data;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.dawnstep.codemonkey.service.data.database.News;
import com.dawnstep.codemonkey.service.data.database.NewsImage;
import com.dawnstep.codemonkey.news.NewsManager;
import com.dawnstep.codemonkey.utils.CodeMonkeyConfig;
import com.j256.ormlite.dao.Dao;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CodeMonkeyNetworkProvider extends CodeMonkeyDataProvider {

	@Override
	public void getNews() {
		// TODO Auto-generated method stub
		GetNewsThread getNewsThread = new GetNewsThread();
		getNewsThread.start();
	}
	
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
	public void getNewsImp() {
		NewsManager newsManager = NewsManager.getInstance();
		//String urlPath = "http://192.168.2.231:3000/infos.json";
		String urlPath = CodeMonkeyConfig.getNewsNetPath();//"http://115.29.139.76:3000/infos.json";
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
            		continue;
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
            		/* async
            		imageLoader.loadImage(imageUrl, new SimpleImageLoadingListener() {
            			@Override
            		    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            		        // Do whatever you want with Bitmap
            				ByteArrayOutputStream baos = new ByteArrayOutputStream();

            				loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            			    byte[] imageBytes =  baos.toByteArray();
            			    newsImage.setImageBytes(imageBytes);
            			    saveNewsImageToDatabase(newsImage);
            		    }
            		});
            		*/
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
            	
            	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (NewsDataListener listener : newsDataListenerContainer) {
			listener.dataArrived();
		}
	}
	
	class GetNewsThread extends Thread {
		
		@Override
		public void run() {
			getNewsImp();
		}
	}

	@Override
	public void getNewSkillGets() {
		// TODO Auto-generated method stub
		
	}

}
