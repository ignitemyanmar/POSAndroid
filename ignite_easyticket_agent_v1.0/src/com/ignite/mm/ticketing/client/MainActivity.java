package com.ignite.mm.ticketing.client;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.client.R;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

@SuppressLint("ShowToast") public class MainActivity extends SherlockActivity {
	private Context ctx = this;
	private ActionBar actionBar;
	
	//Check Device connected
	public static boolean checkState = true;
	private Thread tv_update;
	TextView textView_state;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	Handler mhandler=null;
	Handler handler = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		
		actionBar.hide();
		//getActionBar().hide();
		setContentView(R.layout.activity_main);
		
		Thread splashTread = new Thread() {

			@Override
			public void run() {
				try {
					
					sleep(1000);
					
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();

					//startActivity(new Intent(ctx, UserLogin.class));
					startActivity(new Intent(ctx, UserLogin.class));
					
				}
			}
		};

		splashTread.start();

	} 
}
