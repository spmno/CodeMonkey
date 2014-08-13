package com.dawnstep.codemonkey.service.data.net;

import com.dawnstep.codemonkey.service.data.CodeMonkeyDataProvider;

public class CodeMonkeyNetworkProvider extends CodeMonkeyDataProvider {

	@Override
	public void getNews() {
		// TODO Auto-generated method stub
		NewsDataNetworkProvider newsDataNetworkProvider = new NewsDataNetworkProvider();
		newsDataNetworkProvider.getNews(newsDataListenerContainer);
	}

	@Override
	public void getNewSkillGets() {
		// TODO Auto-generated method stub
		NewSkillGetKindNetworkProvider newSkillGetKindNetworkProvider = new NewSkillGetKindNetworkProvider();
		newSkillGetKindNetworkProvider.getNewSkillGetKind(newSkillGetKindListenerContainer);
	}



}
