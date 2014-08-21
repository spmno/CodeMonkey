package com.dawnstep.codemonkey.service.data.net;

import com.dawnstep.codemonkey.service.data.CodeMonkeyDataProvider;

public class CodeMonkeyNetworkProvider extends CodeMonkeyDataProvider {

	@Override
	public void getNews() {
		// TODO Auto-generated method stub
		NewsDataNetworkProvider newsDataNetworkProvider = new NewsDataNetworkProvider();
		newsDataNetworkProvider.getData(dataListener);
	}

	@Override
	public void getNewSkillKindGets() {
		// TODO Auto-generated method stub
		NewSkillGetKindNetworkProvider newSkillGetKindNetworkProvider = new NewSkillGetKindNetworkProvider();
		newSkillGetKindNetworkProvider.getData(dataListener);
	}

	@Override
	public void getNewSkillGets() {
		// TODO Auto-generated method stub
		NewSkillGetNetworkProvider newSkillGetNetworkProvider = new NewSkillGetNetworkProvider();
		newSkillGetNetworkProvider.getData(dataListener);
	}



}
