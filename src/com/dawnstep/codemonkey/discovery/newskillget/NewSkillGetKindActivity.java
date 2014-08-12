package com.dawnstep.codemonkey.discovery.newskillget;

import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_new_skill_get_kind, container, false);
			newSkillGetListView = (ListView)rootView.findViewById(R.id.newsListView);
			newSkillGetListView.setAdapter(new ArrayAdapter<String>(getActivity(), 
					android.R.layout.simple_expandable_list_item_1, 
					getNewSkillGetKind()));
			return rootView;
		}
		
		public List<String> getNewSkillGetKind() {
			List<String> data = new ArrayList<String>();
			data.add("新技能get");
	        data.add("找女神");
	        data.add("找设计");
	        data.add("近期活动");
	        return data;
		}
		
		
	}

}
