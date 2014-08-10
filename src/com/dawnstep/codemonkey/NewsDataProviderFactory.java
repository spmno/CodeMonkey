package com.dawnstep.codemonkey;

public class NewsDataProviderFactory {
	
	enum ProviderType {
		NetProvider,
		DatabaseProvider,
	}
	
	private NewsDataProviderFactory(){}
	private static final NewsDataProviderFactory newsDataProviderFactory = 
			new NewsDataProviderFactory();
	
	public static NewsDataProviderFactory getInstance() {
		return newsDataProviderFactory;
	}
	
	public CodeMonkeyDataProvider createNewsDataProvider(ProviderType type) {
		switch(type) {
		case NetProvider:
			return new CodeMonkeyNetworkProvider();
		case DatabaseProvider:
			return new CodeMonkeyDatabaseProvider();
		default:
			return null;
		}
	}
}
