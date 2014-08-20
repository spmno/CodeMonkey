package com.dawnstep.codemonkey.discovery.newskillget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.R;
import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetKindActivity.PlaceholderFragment;
import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetKindActivity.PlaceholderFragment.NewSkillGetKindHandler;
import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewSkillGetActivity extends Activity {
	
	private ListView newSkillGetListView;
	private ProgressDialog progressDialog;
	private ArrayAdapter<String> newSkillGetAdapter;
	private List<String> newSkillGetData = new ArrayList<String>();
	NewSkillGetHandler newSkillGetKindHandler;
	
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
		NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
		List<NewSkillGetKind> newSkillGetKindList = newSkillGetKindManager.getNewSkillGetKindList();
		for (NewSkillGetKind newSkillGetKind : newSkillGetKindList) {
			data.add(newSkillGetKind.getTitle());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_skill_get);
		
		newSkillGetListView = (ListView)findViewById(R.id.newSkillGetListview);
		
	}
}
