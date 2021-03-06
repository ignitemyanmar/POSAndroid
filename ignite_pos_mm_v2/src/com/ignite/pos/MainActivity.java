package com.ignite.pos;

import com.actionbarsherlock.app.SherlockActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends SherlockActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
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

					startActivity(new Intent(MainActivity.this, MenuActivity.class));

				}
			}
		};
		splashTread.start();
    }

}
