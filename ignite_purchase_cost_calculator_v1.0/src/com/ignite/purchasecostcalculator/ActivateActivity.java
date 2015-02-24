package com.ignite.purchasecostcalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ignite.purchasecostcalculator.application.BaseActivity;
import com.smk.activate.Activation;
import com.smk.skalertmessage.SKToastMessage;

public class ActivateActivity extends BaseActivity{

	private EditText edt_activate_code;
	private Button btn_back;
	private Button btn_enter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Check activated or not 
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		boolean activated = pref.getBoolean("activated", false);
		
		if (activated == true) {
			startActivity(new Intent(getApplicationContext(), MenuActivity.class));
			finish();
		}
		
		setContentView(R.layout.activity_activate);
		
		edt_activate_code = (EditText)findViewById(R.id.edt_activate_code);
		btn_back = (Button)findViewById(R.id.btn_back);
		btn_enter = (Button)findViewById(R.id.btn_enter);
		
		btn_back.setOnClickListener(clicklistner);
		btn_enter.setOnClickListener(clicklistner);
		
	}
	
	private OnClickListener clicklistner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_back) {
				finish();
			}
			if (v == btn_enter) {
				if (checkFields()) {
					Integer activate_code = Integer.valueOf(edt_activate_code.getText().toString());									
					
					//Check Activation Code
					if (Activation.activate(activate_code, getSysLongDate())) {
						
						SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.clear();
						editor.commit();
						editor.putBoolean("activated", true);
						editor.commit();
						startActivity(new Intent(ActivateActivity.this, MenuActivity.class));
						finish();
						
					}else {
						SKToastMessage.showMessage(getApplicationContext(), "Wrong Code!", SKToastMessage.ERROR);
					}
				}
			}
		}
	
	};
	
	private long getSysLongDate(){
		long long_date = 0;
		Calendar c = Calendar.getInstance(Locale.getDefault());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:59");
		String formattedDate = df.format(c.getTime());
		try {
			Log.i("","Hello Date String: "+ formattedDate);
			long_date =  df.parse(formattedDate).getTime() / 1000;
			Log.i("","Hello Long Date: "+ long_date);
			Log.i("","Hello - Long Date: "+ long_date / 3600);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return long_date;
	}
	
	private boolean checkFields() {
		// TODO Auto-generated method stub
		if (edt_activate_code.getText().toString().length() == 0) {
			edt_activate_code.setError("Enter Activate Code");
			return false;
		}
		
		return true;
	}
}
