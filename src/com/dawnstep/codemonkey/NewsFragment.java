package com.dawnstep.codemonkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsFragment extends Fragment {
	private ListView newsListView;
	private NewsService.NewsBinder mNewBinder;
	private NewsServiceConnection mNewsConnection;
	private NewsHandler newsHandler;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);
		newsListView = (ListView)rootView.findViewById(R.id.newsListView);
		
		Intent intent = new Intent(getActivity(), NewsService.class);
		mNewsConnection = new NewsServiceConnection();
		getActivity().bindService(intent, mNewsConnection, Context.BIND_AUTO_CREATE);
		newsHandler = new NewsHandler();
		return rootView;
	}
	
	public class NewsServiceConnection implements ServiceConnection, NewsDataListener {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			// TODO Auto-generated method stub
			mNewBinder = (NewsService.NewsBinder)binder;
			mNewBinder.addDataArrivedListener(this);
			mNewBinder.getNews();
			//List<Map<String, Object>> newsList = mNewBinder.getNews();

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void dataArrived() {
			newsHandler.sendEmptyMessage(0);
		}
		
	}
	
	class NewsHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			NewsManager newsManager = NewsManager.getInstance();
			List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
			List<News> orignalList = newsManager.getNewsList();
			
			for (News news : orignalList) {
	        	Map<String, Object> newsMap = new HashMap<String, Object>();
	        	newsMap.put("title", news.getTitle());
	        	newsMap.put("content", news.getContent());
	        	newsList.add(newsMap);
			}
			
			SimpleAdapter adapter = new SimpleAdapter(getActivity(),
					newsList, 
					R.layout.new_list_item,
					new String[] {"image", "content"},
					new int[] {R.id.new_image, R.id.new_content});
			newsListView.setAdapter(adapter);
		}
	}

}
