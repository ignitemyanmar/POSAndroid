package com.ignite.purchasecostcalculator;

import com.ignite.purchasecostcalculator.application.BaseActivity;
import com.smk.skalertmessage.SKToastMessage;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MenuActivity extends BaseActivity{
	private TextView txt_china;
	private TextView txt_thai;
	private ImageView img_back;
	private TextView txt_title;
	private LinearLayout ly_new_exchange_rate;
	private LinearLayout ly_new_item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//Check activated or not 
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		boolean activated = pref.getBoolean("activated", false);
		
		if (activated == true) {
			SKToastMessage.showMessage(getApplicationContext(), "You have already activated ^_^", SKToastMessage.SUCCESS);
		}
		
		txt_china = (TextView)findViewById(R.id.txt_foreign_currency);
		txt_thai = (TextView)findViewById(R.id.txt_thai);
		
		txt_china.setOnClickListener(clicklistener);
		txt_thai.setOnClickListener(clicklistener);
	}
	
	private OnClickListener clicklistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == txt_china) {
				startActivity(new Intent(MenuActivity.this, ChinaToKyatDetailActivity.class));
			}
			if (v == txt_thai) {
				startActivity(new Intent(MenuActivity.this, ThaiToKyatDetailActivity.class));
			}
		}
	};
}

