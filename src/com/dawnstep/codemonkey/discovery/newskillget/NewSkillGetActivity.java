package com.dawnstep.codemonkey.discovery.newskillget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.CodeMonkeyApplication;
import com.dawnstep.codemonkey.R;
import com.dawnstep.codemonkey.service.CodeMonkeyService.CodeMonkeyBinder;
import com.dawnstep.codemonkey.service.data.DataListener;
import com.dawnstep.codemonkey.service.data.database.NewSkillGet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewSkillGetActivity extends Activity implements DataListener, OnItemClickListener {
	
	private ListView newSkillGetListView;
	private ProgressDialog progressDialog;
	private ArrayAdapter<String> newSkillGetAdapter;
	private List<String> newSkillGetData = new ArrayList<String>();
	NewSkillGetHandler newSkillGetHandler;
	
	static class NewSkillGetHandler extends Handler {
		private WeakReference<NewSkillGetActivity> activity;
		NewSkillGetHandler(NewSkillGetActivity activity) {
			this.activity = new WeakReference<NewSkillGetActivity>(activity);
		}
		
		@Override
		public void handleMessage(Message message) {
			NewSkillGetActivity parentActivity = activity.get();
			parentActivity.getNewSkillGet(parentActivity.newSkillGetData);
			parentActivity.newSkillGetAdapter.notifyDataSetChanged();				
		}
	}
	
	public void getNewSkillGet(List<String> data) {
		NewSkillGetManager newSkillGetManager = NewSkillGetManager.getInstance();
		List<NewSkillGet> newSkillGetList = newSkillGetManager.getNewSkillGetList();
		for (NewSkillGet newSkillGet : newSkillGetList) {
			data.add(newSkillGet.getTitle());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_skill_get);
		
		newSkillGetListView = (ListView)findViewById(R.id.newSkillGetListview);
		newSkillGetHandler = new NewSkillGetHandler(this);
		newSkillGetAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, 
				newSkillGetData);
		newSkillGetListView.setAdapter(newSkillGetAdapter);
		NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
		newSkillGetKindManager.clear();
		
		CodeMonkeyApplication codeMonkeyApplication = (CodeMonkeyApplication)getApplication();
		CodeMonkeyBinder codeMonkeyBinder = codeMonkeyApplication.getCodeMonkeyBinder();
		codeMonkeyBinder.addNewSkillGetListener(this);
		
		progressDialog = new ProgressDialog(this);
		String title = getResources().getString(R.string.downloading_title);
		String text = getResources().getString(R.string.downloading_text);
		progressDialog.setTitle(title);
		progressDialog.setMessage(text);
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		codeMonkeyBinder.getNewSkillGets();	
		
	}

	@Override
	public void dataArrived() {
		// TODO Auto-generated method stub
		newSkillGetHandler.sendEmptyMessage(0);
		progressDialog.dismiss();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		NewSkillGetManager newSkillGetManager = NewSkillGetManager.getInstance();
		String currentSkillGetId = newSkillGetManager.getNewSkillGetId(position);
		
		Intent intent = new Intent(this, NewSkillGetActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("newSkillGetId", currentSkillGetId);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
