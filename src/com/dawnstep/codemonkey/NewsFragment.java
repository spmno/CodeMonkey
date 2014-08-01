package com.dawnstep.codemonkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Bitmap; 
import android.graphics.BitmapFactory;

public class NewsFragment extends Fragment implements OnScrollListener, OnItemClickListener {
	
	private static final String TAG = "NewsFragment";
	private ListView newsListView;
	private NewsService.NewsBinder mNewBinder;
	private NewsServiceConnection mNewsConnection;
	private NewsHandler newsHandler;
	//ability of save data moves to newsnanager
	//List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
	private NewsDataAdapter adapter;
	private View moreView; //加载更多页面
	private int lastItem;
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
		moreView = getActivity().getLayoutInflater().inflate(R.layout.load, null);
		adapter = new NewsDataAdapter();
		newsListView.addFooterView(moreView);
		newsListView.setAdapter(adapter);
		newsListView.setOnScrollListener(NewsFragment.this);
		newsListView.setOnItemClickListener(this);
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
			/*
			NewsManager newsManager = NewsManager.getInstance();
			
			List<News> orignalList = newsManager.getNewsList();
			
			for (News news : orignalList) {
				List<NewsImage> newsImageList = newsManager.getNewsImage(news.getNewsId());
	        	Map<String, Object> newsMap = new HashMap<String, Object>();
	        	newsMap.put("title", news.getTitle());
	        	newsMap.put("content", news.getContent());
	        	if (newsImageList != null) {
	        		newsMap.put("images", newsImageList.get(0).getImageBytes());
	        	}
	        	
	        	newsList.add(newsMap);
			}
			*/
			adapter.notifyDataSetChanged();
            moreView.setVisibility(View.GONE); 
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		lastItem = firstVisibleItem + visibleItemCount - 1; 
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		int count = NewsManager.getInstance().getOffset();
		if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){ 
			Log.i(TAG, "拉到最底部");
            moreView.setVisibility(view.VISIBLE);
		}  
		mNewBinder.getNews();
    }

	class NewsDataAdapter extends BaseAdapter {

		private NewsManager newsManager = NewsManager.getInstance();
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsManager.getOffset();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = getActivity().getLayoutInflater();
			News currentNews = newsManager.getNewsList().get(position);
			/* old implements
			if (!currentNews.getImages().isEmpty()) {

				String imageUrl = currentNews.getImages().get(0);
				if ((!imageUrl.isEmpty()) && (!imageUrl.equals("null"))) {
					convertView = inflater.inflate(R.layout.new_list_item, null);
					ImageView imageView = (ImageView)convertView.findViewById(R.id.new_image);
					DisplayImageOptions options = new DisplayImageOptions.Builder()  
	                .showImageOnLoading(R.drawable.ic_launcher)  
	                .showImageOnFail(R.drawable.ic_launcher)  
	                .cacheInMemory(true)  
	                .cacheOnDisk(true)  
	                .bitmapConfig(Bitmap.Config.RGB_565)  
	                .build();
					String absolutelyImageUrl = NewsConfig.getNetRootPath() + imageUrl;
					ImageLoader.getInstance().displayImage(absolutelyImageUrl, imageView, options); 
				} else {
					convertView = inflater.inflate(R.layout.news_list_item_no_image, null);
				}
	 
			} else {
				convertView = inflater.inflate(R.layout.news_list_item_no_image, null);
			}
			*/
			List<NewsImage> newsImageList = newsManager.getNewsImage(currentNews.getNewsId());
			if (newsImageList != null) {
				byte[] imageBytes = newsImageList.get(0).getImageBytes();
				Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
				convertView = inflater.inflate(R.layout.new_list_item, null);
				ImageView imageView = (ImageView)convertView.findViewById(R.id.new_image);
				imageView.setImageBitmap(bitmap);
			} else {
				convertView = inflater.inflate(R.layout.news_list_item_no_image, null);
			}
			TextView titleTextView = (TextView)convertView.findViewById(R.id.new_title);
			titleTextView.setText(currentNews.getTitle());
			TextView contentTextView = (TextView)convertView.findViewById(R.id.new_content);
			contentTextView.setText(currentNews.getContent());
			
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), NewsShowActivity.class);
		Bundle bundle = new Bundle(); 
		NewsManager newsManager = NewsManager.getInstance();
		News currentNews = newsManager.getNewsList().get(position);
		bundle.putString("newsId", currentNews.getNewsId());
		intent.putExtras(bundle);
		getActivity().startActivity(intent);
	}
}
