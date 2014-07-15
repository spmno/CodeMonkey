package com.dawnstep.codemonkey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;



public class NewsService extends Service {
	
    private static final String TAG = "NewsService"; 
    private NewsBinder mNewBinder = new NewsBinder();
	public NewsService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		return mNewBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	

	
	public class NewsBinder extends Binder {
		List<NewsDataListener> listenerContainer = new ArrayList<NewsDataListener>();
		/* only for the test
		public List<Map<String, Object>> getNews() {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("content", "test1");
	        
	        data.add(map);
	        
	        map.put("content", "test2");
	        
	        data.add(map);
	         
	        return data;
		}
		*/
		public void addDataArrivedListener(NewsDataListener listener) {
			listenerContainer.add(listener);
		}
		
		public void getNews() {
			GetNewsThread getNewsThread = new GetNewsThread();
			getNewsThread.start();
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
}
