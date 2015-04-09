package com.ignite.pos.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ignite.pos.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends Activity {

	public static ActionBar actionbar;
	private Callback mCallback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionbar = getActionBar();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//EasyTracker.getInstance().activityStart(this);
	}
	@Override
	public void onStop() {
	    super.onStop();
	   // The rest of your onStop() code.
	    //EasyTracker.getInstance().activityStop(this);  // Add this method.
	}
	
	public void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Info");
		alert.setMessage(message+"!");
		alert.show();
		alert.setCancelable(true);
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
		return DateFormat.format("dd MMMM, yyyy", StartDate).toString();
	}

	public void setCallbackListener(Callback mCallback) {
		this.mCallback = mCallback;
	}

	public interface Callback {
    	void onDelete();
    }
}
