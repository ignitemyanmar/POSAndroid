package com.ignite.mm.ticketing.clientapi;

import retrofit.RestAdapter;

public class NetworkEngine {
	public static INetworkEngine instance;
	public static String ip = "app.easyticket.com.mm"; 
	public static void setIP(String address){
		ip = address;
		instance = null;
	}
	public static INetworkEngine getInstance() {
		if (instance==null) {
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://"+ ip +"/").setLogLevel(RestAdapter.LogLevel.FULL).setErrorHandler(new MyErrorHandler()).build();
			instance = adapter.create(INetworkEngine.class);
		}
		return instance;
	}
}
