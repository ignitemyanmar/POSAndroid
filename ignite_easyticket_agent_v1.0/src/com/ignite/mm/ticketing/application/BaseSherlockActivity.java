package com.ignite.mm.ticketing.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ignite.mm.ticketing.agent.R;

public class BaseSherlockActivity extends SherlockActivity {
	
	public LoginUser AppLoginUser;
	
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.ignite.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();
	public static final IntentFilter INTENT_FILTER = createIntentFilter();
	
	private static IntentFilter createIntentFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
		return filter;
	}
	
	protected void registerBaseActivityReceiver() {
		registerReceiver(baseActivityReceiver, INTENT_FILTER);
	}
	
	protected void unRegisterBaseActivityReceiver() {
		unregisterReceiver(baseActivityReceiver);
	}
	
	public class BaseActivityReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)){
				finish();
			}
		}
	} 
	
	protected void closeAllActivities(){
		sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		registerBaseActivityReceiver();
		AppLoginUser = new LoginUser(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
/*		if(AppLoginUser.isExpires()){
			closeAllActivities();
		}*/
		super.onResume();
	}
	
	protected String getToday(){
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		Log.i("","Hello Today: "+formattedDate);
		return formattedDate;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menu_logout) {
			AppLoginUser.logout();
        	closeAllActivities();
        	return true;
		}else {
			return false;  
		}
		
	    /*switch(item.getItemId()) {
	        case R.id.menu_logout:
	        	AppLoginUser.logout();
	        	closeAllActivities();
	        	return true;
   	   	}
		return false; */ 
	 }
	
	public static String changeDate(String date){
		Log.i("", "to change date: "+date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("", "start date: "+StartDate);
		return DateFormat.format("dd-MM-yyyy", StartDate).toString();
	}
	
	public static String changeDateString(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date StartDate = null;
		try {
			StartDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DateFormat.format("dd-MMMM-yyyy", StartDate).toString();
	}
	
}
