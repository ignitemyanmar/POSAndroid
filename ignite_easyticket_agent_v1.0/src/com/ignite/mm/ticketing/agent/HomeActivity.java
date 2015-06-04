package com.ignite.mm.ticketing.agent;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class HomeActivity extends SherlockActivity{

	private Button btn_sale_tickets;
	private Button btn_book_confirm;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private Button btn_three_day_sales;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		//actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBarTitle.setText("Menu");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		//Check Screen Size
		Configuration config = getResources().getConfiguration();
       
		//For Tablet
		if (config.smallestScreenWidthDp >= 600) {
			setContentView(R.layout.activity_home);
		}else {
			setContentView(R.layout.activity_home_phone);
		}
		
		
		//Log.i("", "User Login: "+AppLoginUser.getCodeNo());
		
		btn_sale_tickets = (Button)findViewById(R.id.btn_sale_tickets);
		btn_book_confirm = (Button)findViewById(R.id.btn_book_confirm);
		btn_three_day_sales = (Button)findViewById(R.id.btn_three_day_sales);
		
		btn_sale_tickets.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "sale");
				startActivity(new Intent(getApplicationContext(), SaleTicketActivity.class).putExtras(bundle));
			}
		});
		
		btn_book_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "reservation");
				startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class).putExtras(bundle));
			}
		});
		
		btn_three_day_sales.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), ThreeDaySalesActivity.class));
			}
		});
	}
}
