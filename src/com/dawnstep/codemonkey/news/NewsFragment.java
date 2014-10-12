package com.dawnstep.codemonkey.news;

import java.lang.ref.WeakReference;
import java.util.List;

import com.dawnstep.codemonkey.CodeMonkeyApplication;
import com.dawnstep.codemonkey.R;
import com.dawnstep.codemonkey.service.data.DataListener;
import com.dawnstep.codemonkey.service.data.database.News;
import com.dawnstep.codemonkey.service.data.database.NewsImage;
import com.dawnstep.codemonkey.service.CodeMonkeyService.CodeMonkeyBinder;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
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

public class NewsFragment extends Fragment implements OnScrollListener, OnItemClickListener, DataListener, OnRefreshListener {
	
	private static final String TAG = "NewsFragment";
	private ListView newsListView;
	private NewsHandler newsHandler;
	private NewsDataAdapter adapter;
	private View moreView; //���ظ���ҳ��
	private SwipeRefreshLayout swipeLayout; 
	private int lastItem;
	private ProgressDialog progressDialog;
	private CodeMonkeyBinder codeMonkeyBinder;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);
		newsListView = (ListView)rootView.findViewById(R.id.newsListView);
		swipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.id_swipe_ly);
		newsHandler = new NewsHandler(this);
		moreView = getActivity().getLayoutInflater().inflate(R.layout.load, null);
		adapter = new NewsDataAdapter();
		newsListView.addFooterView(moreView);
		newsListView.setAdapter(adapter);
		newsListView.setOnScrollListener(NewsFragment.this);
		newsListView.setOnItemClickListener(this);		
		swipeLayout.setOnRefreshListener(this);
		Activity parentActivity = NewsFragment.this.getActivity();
		progressDialog = new ProgressDialog(parentActivity);
		String title = parentActivity.getResources().getString(R.string.downloading_title);
		String text = parentActivity.getResources().getString(R.string.downloading_text);
		progressDialog.setTitle(title);
		progressDialog.setMessage(text);
		progressDialog.setCancelable(false);
		progressDialog.show();
		final CodeMonkeyApplication codeMonkeyApplication = (CodeMonkeyApplication)getActivity().getApplication();
		codeMonkeyBinder = codeMonkeyApplication.getCodeMonkeyBinder();
		if (codeMonkeyBinder != null) {
			codeMonkeyBinder.addNewsDataArrivedListener(this);
			codeMonkeyBinder.getNews();
		} else {
			new Thread() {
				public void run() {
					while(true) {
						try {
							Thread.sleep(500);
							codeMonkeyBinder = codeMonkeyApplication.getCodeMonkeyBinder();
							if (codeMonkeyBinder != null) {
								codeMonkeyBinder.addNewsDataArrivedListener(NewsFragment.this);
								codeMonkeyBinder.getNews();
								break;
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		
		
		return rootView;
	}
	
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void dataArrived() {
		newsHandler.sendEmptyMessage(0);
		progressDialog.dismiss();
	}
	
	
	static class NewsHandler extends Handler {
		private WeakReference<NewsFragment> fragment;
		NewsHandler(NewsFragment fragment) {
			this.fragment = new WeakReference<NewsFragment>(fragment);
		}
		@Override
		public void handleMessage(Message message) {
			NewsFragment parentFragment = fragment.get();
			parentFragment.adapter.notifyDataSetChanged();
			parentFragment.moreView.setVisibility(View.GONE); 
			parentFragment.swipeLayout.setRefreshing(false); 
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
		if(lastItem == count  && scrollState == NewsFragment.SCROLL_STATE_IDLE){ 
			Log.i(TAG, "������ײ�");
            moreView.setVisibility(AbsListView.VISIBLE);
		}  
		codeMonkeyBinder.getNews();
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

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		codeMonkeyBinder.getNews();
	}
}
