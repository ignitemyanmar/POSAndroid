package com.ignite.mm.ticketing;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends SherlockActivity {
	private Context ctx = this;
	private ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		actionBar.hide();
		//getActionBar().hide();
		setContentView(R.layout.activity_main);

		// RunAnimations_for_FadeOut();
		Thread splashTread =
				new Thread() {

			@Override
			public void run() {
				try {
					// int waited = 0;

					// while(_active && (waited < _splashTime)) {

					sleep(1000);

					// if(_active) {

					// waited += 500;
					// }
					// }
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
