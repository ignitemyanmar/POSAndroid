package com.ignite.mm.ticketing.application;

import android.app.Activity;
public class MyApplication extends Activity {
	private static MyApplication objInstance = null;
	private boolean isLoadedLogin = false;
	
	public static MyApplication getInstance(){
		if(objInstance == null){
			objInstance = new MyApplication();
		}
		return objInstance;
	}
	public boolean isLoadedLogin() {
		return isLoadedLogin;
	}
}
