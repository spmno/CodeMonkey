package com.dawnstep.codemonkey;

import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);
		newsListView = (ListView)rootView.findViewById(R.id.newsListView);
		
		Intent intent = new Intent(getActivity(), NewsService.class);
		mNewsConnection = new NewsServiceConnection();
		getActivity().bindService(intent, mNewsConnection, Context.BIND_AUTO_CREATE);
		return rootView;
	}
	
	public class NewsServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			// TODO Auto-generated method stub
			mNewBinder = (NewsService.NewsBinder)binder;
			List<Map<String, Object>> newsList = mNewBinder.getNews();
			SimpleAdapter adapter = new SimpleAdapter(getActivity(),
					newsList, 
					R.layout.new_list_item,
					new String[] {"image", "content"},
					new int[] {R.id.new_image, R.id.new_content});
			newsListView.setAdapter(adapter);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
