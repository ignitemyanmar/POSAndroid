package com.ignite.purchasecostcalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ignite.purchasecostcalculator.application.BaseActivity;
import com.smk.skalertmessage.SKToastMessage;

public class ActivateActivity extends BaseActivity{

	private EditText edt_activate_code;
	private Button btn_back;
	private Button btn_enter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_activate);
		
		//Check activated or not 
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		boolean activated = pref.getBoolean("activated", false);
		
		if (activated == false) {
			SKToastMessage.showMessage(getApplicationContext(), "You have not activated yet!", SKToastMessage.WARNING);
		}
		  
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
					String activate_code = edt_activate_code.getText().toString();									
					
					//Check Activation Code
					if (activate_code.equals("123123")) {
						startActivity(new Intent(ActivateActivity.this, MenuActivity.class));
					}else {
						SKToastMessage.showMessage(getApplicationContext(), "Wrong Code!", SKToastMessage.ERROR);
					}
				}
			}
		}
	
	};
	
	private boolean checkFields() {
		// TODO Auto-generated method stub
		if (edt_activate_code.getText().toString().length() == 0) {
			edt_activate_code.setError("Enter Activate Code");
			return false;
		}
		
		return true;
	}
}
