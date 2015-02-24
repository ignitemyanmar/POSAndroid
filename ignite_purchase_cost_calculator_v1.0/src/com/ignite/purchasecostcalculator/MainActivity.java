package com.ignite.purchasecostcalculator;

import com.ignite.purchasecostcalculator.application.BaseActivity;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends BaseActivity {
	private Context ctx = this;
	private ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_main);

		// RunAnimations_for_FadeOut();
		Thread splashTread =
				new Thread() {

			@Override
			public void run() {
				try {

					sleep(1700);

				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					startActivity(new Intent(getApplicationContext(), ActivateActivity.class));
					
					/*//Check Activated or not
					String str_activated = "activated";
					
					boolean isActivated;
					
					if (str_activated.equals("aa")) {
						isActivated = true;
						
						SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("User",Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						
						editor.clear();
						editor.commit();
						
						editor.putBoolean("activated", isActivated);
						editor.commit();
												
						startActivity(new Intent(getApplicationContext(), MenuActivity.class));
					}else {
						isActivated = false;
						
						SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("User",Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						
						editor.clear();
						editor.commit();
						
						editor.putBoolean("activated", isActivated);
						editor.commit();
						
						startActivity(new Intent(getApplicationContext(), ActivateActivity.class));
					}*/
				}
			}
		};

		splashTread.start();

	}

	
}
