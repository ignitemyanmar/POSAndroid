package com.ignite.mm.ticketing.clientapi;

import retrofit.RestAdapter;

public class NetworkEngine {
	static INetworkEngine instance;
	public static INetworkEngine getInstance() {
		if (instance==null) {
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://easyticket.com.mm/").setErrorHandler(new MyErrorHandler()).build();
			instance = adapter.create(INetworkEngine.class);
		}
		return instance;
	}
}
