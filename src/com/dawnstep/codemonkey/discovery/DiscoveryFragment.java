package com.dawnstep.codemonkey.discovery;

import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DiscoveryFragment extends Fragment {
	
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
	
}
