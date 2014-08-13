package com.dawnstep.codemonkey.discovery.newskillget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.R;
import com.dawnstep.codemonkey.service.CodeMonkeyService;
import com.dawnstep.codemonkey.service.data.NewSkillGetKindListener;
import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class NewSkillGetKindActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_skill_get_kind);

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
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private ListView newSkillGetListView;
		private CodeMonkeyService.CodeMonkeyBinder newBinder;
		private ProgressDialog progressDialog;
		private ArrayAdapter<String> newSkillGetKindAdapter;
		NewSkillGetKindHandler newSkillGetKindHandle;
		private NewSkillGetKindServiceConnection newSkillGetKindServiceConnection;
		NewSkillGetKindHandler newSkillGetKindHandler;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_new_skill_get_kind, container, false);
			newSkillGetListView = (ListView)rootView.findViewById(R.id.newSkillKindListview);

			newSkillGetKindHandle = new NewSkillGetKindHandler(this);
			Intent intent = new Intent(getActivity(), CodeMonkeyService.class);
			newSkillGetKindServiceConnection = new NewSkillGetKindServiceConnection();
			getActivity().bindService(intent, newSkillGetKindServiceConnection, Context.BIND_AUTO_CREATE);
			newSkillGetKindHandler = new NewSkillGetKindHandler(this);
			return rootView;
		}
		
		public List<String> getNewSkillGetKind() {
			NewSkillGetKindManager newSkillGetKindManager = NewSkillGetKindManager.getInstance();
			List<NewSkillGetKind> newSkillGetKindList = newSkillGetKindManager.getNewSkillGetKindList();
			List<String> data = new ArrayList<String>();
			for (NewSkillGetKind newSkillGetKind : newSkillGetKindList) {
				data.add(newSkillGetKind.getTitle());
			}
	        return data;
		}
		
		static class NewSkillGetKindHandler extends Handler {
			private WeakReference<PlaceholderFragment> fragment;
			NewSkillGetKindHandler(PlaceholderFragment fragment) {
				this.fragment = new WeakReference<PlaceholderFragment>(fragment);
			}
			@Override
			public void handleMessage(Message message) {
				PlaceholderFragment parentFragment = fragment.get();
				//parentFragment.newSkillGetKindAdapter.notifyDataSetChanged();
				parentFragment.newSkillGetKindAdapter = new ArrayAdapter<String>(parentFragment.getActivity(), 
						android.R.layout.simple_expandable_list_item_1, 
						parentFragment.getNewSkillGetKind());
				parentFragment.newSkillGetListView.setAdapter(parentFragment.newSkillGetKindAdapter);
			}
		}

		
		public class NewSkillGetKindServiceConnection implements ServiceConnection, NewSkillGetKindListener {

			@Override
			public void dataArrived() {
				// TODO Auto-generated method stub
				newSkillGetKindHandle.sendEmptyMessage(0);
				progressDialog.dismiss();
			}

			@Override
			public void onServiceConnected(ComponentName arg0, IBinder binder) {
				// TODO Auto-generated method stub
				newBinder = (CodeMonkeyService.CodeMonkeyBinder)binder;
				newBinder.addNewSkillGetKindListener(this);
				Activity parentActivity = PlaceholderFragment.this.getActivity();
				progressDialog = new ProgressDialog(parentActivity);
				String title = parentActivity.getResources().getString(R.string.downloading_title);
				String text = parentActivity.getResources().getString(R.string.downloading_text);
				progressDialog.setTitle(title);
				progressDialog.setMessage(text);
				progressDialog.setCancelable(false);
				progressDialog.show();
				
				newBinder.getNewSkillGets();	
			}

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
	}

}
