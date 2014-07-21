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
	
	public NewsDataProvider createNewsDataProvider(ProviderType type) {
		switch(type) {
		case NetProvider:
			return new NewsDataNetworkProvider();
		case DatabaseProvider:
			return new NewsDataDatabaseProvider();
		default:
			return null;
		}
	}
}
