package com.ignite.mm.ticketing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.BookingCodeDialog;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusMenuActivity extends BaseSherlockActivity {
	private LinearLayout btn_sale_ticket;
	private LinearLayout btn_order;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private LinearLayout btn_old_sale;
	private TextView actionBarNoti;
	private LinearLayout btn_all_booking;
	private LinearLayout btn_search_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarNoti = (TextView) actionBar.getCustomView().findViewById(R.id.txt_notify_booking);
		actionBarNoti.setOnClickListener(clickListener);
		actionBarTitle.setText("Easy Ticket");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_busticketing_menu);
		
		btn_sale_ticket = (LinearLayout) findViewById(R.id.btn_sale_ticket);
		btn_order = (LinearLayout) findViewById(R.id.btn_credit_list);
		btn_old_sale = (LinearLayout) findViewById(R.id.btn_cancel_order);
		btn_all_booking = (LinearLayout) findViewById(R.id.all_booking);
		btn_search_code = (LinearLayout) findViewById(R.id.btn_search_code);
		
		btn_sale_ticket.setOnClickListener(clickListener);
		btn_order.setOnClickListener(clickListener);
		btn_old_sale.setOnClickListener(clickListener);
		btn_all_booking.setOnClickListener(clickListener);
		btn_search_code.setOnClickListener(clickListener);
		
		SKConnectionDetector detector = SKConnectionDetector.getInstance(this);
		if(detector.isConnectingToInternet()){
			getNotiBooking();
		}
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarNoti){
				SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", getToday());
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			}
			if(v == btn_sale_ticket){
				SharedPreferences sharedPreferences = getSharedPreferences("old_sale",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("working_date", "");
				editor.commit();
				Intent intent = new Intent(getApplicationContext(),	BusTripsCityActivity.class);
				startActivity(intent);
			}
			
			if(v == btn_order){
				final SKCalender skCalender = new SKCalender(BusMenuActivity.this);
				
				  skCalender.setCallbacks(new Callbacks() {

						public void onChooseDate(String chooseDate) {
				        	// TODO Auto-generated method stub
				        	Date formatedDate = null;
							try {
								formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
								
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
								String selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
					        	SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedPreferences.edit();
								editor.clear();
								editor.commit();
								editor.putString("order_date", selectedDate);
								editor.commit();
					        	skCalender.dismiss();
					        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));	
								
							
				        			        	
				        	
				        }
				  });

				skCalender.show();
			}
			
			if(v == btn_old_sale){
				final SKCalender skCalender = new SKCalender(BusMenuActivity.this);
				
				  skCalender.setCallbacks(new Callbacks() {
						public void onChooseDate(String chooseDate) {
				        	// TODO Auto-generated method stub
							Date today = null;
				        	Date formatedDate = null;
							try {
								Log.i("","Hello Choose Date : "+ chooseDate);
								formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
								today = new SimpleDateFormat("yyyy-MM-dd").parse(getToday());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							int compare = today.compareTo(formatedDate);
							Log.i("","Hello Compare : "+ compare);
							if(compare > 0){
							
					        	String selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
					        	
					        	SharedPreferences sharedPreferences = getSharedPreferences("old_sale",MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedPreferences.edit();
								editor.clear();
								editor.commit();
								editor.putString("working_date", selectedDate);
								editor.commit();
					        	skCalender.dismiss();
					        	startActivity(new Intent(getApplicationContext(), BusTripsCityActivity.class));
							}else{
								SKToastMessage.showMessage(BusMenuActivity.this, "Must be less than today!.", SKToastMessage.ERROR);
							}
				        }
				  });

				skCalender.show();
			}
			
			if(v == btn_all_booking){
	        	SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", "");
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			}
			
			if(v == btn_search_code){
				BookingCodeDialog bookingCodeDialog = new BookingCodeDialog(BusMenuActivity.this);
				bookingCodeDialog.setCallbackListener(new BookingCodeDialog.Callback() {
					
					public void onSearch(String code) {
						// TODO Auto-generated method stub
						if(code.length() > 0){
							SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedPreferences.edit();
							editor.clear();
							editor.commit();
							editor.putString("book_code", code);
							editor.commit();
				        	startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
						}else{
							SKToastMessage.showMessage(BusMenuActivity.this, "Please Enter Booking Code", SKToastMessage.ERROR);
						}
					}
					
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
				bookingCodeDialog.show();
			}
			
			if(v == actionBarBack){
				finish();
			}
		}
	};
	
	private void getNotiBooking(){
		
		NetworkEngine.getInstance().getNotiBooking(AppLoginUser.getAccessToken(), getToday() , new Callback<Integer>() {
			
			public void success(Integer arg0, Response arg1) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences = getSharedPreferences("NotifyBooking",Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				
				editor.clear();
				editor.commit();
				
				editor.putInt("count", arg0);
				editor.commit();
				
				if(arg0 > 0){
					actionBarNoti.setVisibility(View.VISIBLE);
					actionBarNoti.setText(arg0.toString());
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
