package com.dawnstep.codemonkey.discovery;

import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.R;
import com.dawnstep.codemonkey.discovery.newskillget.NewSkillGetKindActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DiscoveryFragment extends Fragment implements OnItemClickListener {
	
	private ListView discoveryListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);
		discoveryListView = (ListView)rootView.findViewById(R.id.newsListView);
		discoveryListView.setAdapter(new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_expandable_list_item_1, 
				getDiscoveries()));
		discoveryListView.setOnItemClickListener(this);
		return rootView;
	}
	
	public List<String> getDiscoveries() {
		List<String> data = new ArrayList<String>();
		data.add("新技能get");
        data.add("找女神");
        data.add("找设计");
        data.add("近期活动");
        return data;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		if (position == 0) {
			Intent intent = new Intent(getActivity(), NewSkillGetKindActivity.class);
			getActivity().startActivity(intent);
		}

	}
	
}
