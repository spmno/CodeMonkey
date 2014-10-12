package com.dawnstep.codemonkey.discovery.newskillget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.CodeMonkeyApplication;
import com.dawnstep.codemonkey.R;
import com.dawnstep.codemonkey.service.CodeMonkeyService.CodeMonkeyBinder;
import com.dawnstep.codemonkey.service.data.DataListener;
import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class NewSkillGetKindActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_skill_get_kind);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_skill_get_kind, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements DataListener, OnItemClickListener {
		private ListView newSkillGetListView;
		private ProgressDialog progressDialog;
		private ArrayAdapter<String> newSkillGetKindAdapter;
		private List<String> newSkillGetKindData = new ArrayList<String>();
		NewSkillGetKindHandler newSkillGetKindHandler;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_new_skill_get_kind, container, false);
			newSkillGetListView = (ListView)rootView.findViewById(R.id.newSkillKindListview);

			newSkillGetKindHandler = new NewSkillGetKindHandler(this);
			newSkillGetKindAdapter = new ArrayAdapter<String>(getActivity(), 
					android.R.layout.simple_list_item_1, 
					newSkillGetKindData);
			newSkillGetListView.setAdapter(newSkillGetKindAdapter);
			newSkillGetListView.setOnItemClickListener(this);
			NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
			newSkillGetKindManager.clear();
			
			Activity parentActivity = getActivity();
			CodeMonkeyApplication codeMonkeyApplication = (CodeMonkeyApplication)parentActivity.getApplication();
			CodeMonkeyBinder codeMonkeyBinder = codeMonkeyApplication.getCodeMonkeyBinder();
			codeMonkeyBinder.addNewSkillGetKindListener(this);
			
			progressDialog = new ProgressDialog(parentActivity);
			String title = parentActivity.getResources().getString(R.string.downloading_title);
			String text = parentActivity.getResources().getString(R.string.downloading_text);
			progressDialog.setTitle(title);
			progressDialog.setMessage(text);
			progressDialog.setCancelable(false);
			progressDialog.show();
			
			codeMonkeyBinder.getNewSkillKindGets();	
			
			return rootView;
		}
		
		public void getNewSkillGetKind(List<String> data) {
			NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
			List<NewSkillGetKind> newSkillGetKindList = newSkillGetKindManager.getNewSkillGetKindList();
			for (NewSkillGetKind newSkillGetKind : newSkillGetKindList) {
				data.add(newSkillGetKind.getTitle());
			}
		}
		
		static class NewSkillGetKindHandler extends Handler {
			private WeakReference<PlaceholderFragment> fragment;
			NewSkillGetKindHandler(PlaceholderFragment fragment) {
				this.fragment = new WeakReference<PlaceholderFragment>(fragment);
			}
			@Override
			public void handleMessage(Message message) {
				PlaceholderFragment parentFragment = fragment.get();
				parentFragment.getNewSkillGetKind(parentFragment.newSkillGetKindData);
				parentFragment.newSkillGetKindAdapter.notifyDataSetChanged();				
			}
		}

		@Override
		public void dataArrived() {
			// TODO Auto-generated method stub
			newSkillGetKindHandler.sendEmptyMessage(0);
			progressDialog.dismiss();
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			// TODO Auto-generated method stub
			NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
			String currentKindId = newSkillGetKindManager.getNewSkillGetKindId(position);
			NewSkillGetManager newSkillGetManager = NewSkillGetManager.getInstance();
			newSkillGetManager.setCurrentSkillGetKind(currentKindId);
			Intent intent = new Intent(getActivity(), NewSkillGetActivity.class);
			startActivity(intent);
		}
	}

}
