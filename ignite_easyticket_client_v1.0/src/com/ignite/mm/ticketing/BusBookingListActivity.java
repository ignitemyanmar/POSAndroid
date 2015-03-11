package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.BookingFilterDialog;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OrderListViewAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Cities;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;
import com.ignite.mm.ticketing.sqlite.database.model.From;
import com.ignite.mm.ticketing.sqlite.database.model.TimesbyOperator;
import com.ignite.mm.ticketing.sqlite.database.model.To;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusBookingListActivity extends BaseSherlockActivity {
	private ListView lst_credit;
	private List<CreditOrder> credit_list;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private TextView txt_title;
	private Button btn_search;
	private String BookCode = "";
	private TextView actionBarTitle2;
	private String OperatorID;

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
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		actionBarBack.setOnClickListener(clickListener);
		actionBarTitle.setText("Easy Ticket");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_busticketing_credit);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			OperatorID = bundle.getString("operator_id");
		}

		txt_title = (TextView) findViewById(R.id.txt_title);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(clickListener);
		SharedPreferences pref = getSharedPreferences("order", Activity.MODE_PRIVATE);
		String orderDate = pref.getString("order_date", "");
		BookCode = pref.getString("book_code", "");
		if(!orderDate.equals("")){
			txt_title.setText("( "+ changeDate(orderDate) +" ) ေန႕ အတြက္ Booking စာရင္းမ်ား");
		}else{
			txt_title.setText("Booking စာရင္း အားလံုး");
		}
		
		lst_credit = (ListView) findViewById(R.id.lst_credit);
		
		credit_list = new ArrayList<CreditOrder>();
	
		lst_credit.setOnItemClickListener(itemClickListener);
		
		getTimeData();
		getCity();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SKConnectionDetector connectionDetector = SKConnectionDetector.getInstance(this);
		if(connectionDetector.isConnectingToInternet()){
			getBookingList();
		}else{
			connectionDetector.showErrorMessage();
		}
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			if(v == btn_search){
				
				BookingFilterDialog filterDialog = new BookingFilterDialog(BusBookingListActivity.this);
				filterDialog.setFromCity(fromCities);
				filterDialog.setToCity(toCities);
				filterDialog.setTime(Times);
				filterDialog.setCallbackListener(new BookingFilterDialog.Callback() {
					
					public void onSave(String date, String from_id, String to_id, String time) {
						// TODO Auto-generated method stub
						SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.clear();
						editor.commit();
						editor.putString("order_date", date);
						editor.putString("from", from_id);
						editor.putString("to", to_id);
						editor.putString("time", time);
						editor.commit();
						getBookingList();
					}
					
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
				filterDialog.show();
			}
		}
	};
	
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), BusBookingConfirmDeleteActivity.class);
			intent.putExtra("credit_order", new Gson().toJson(credit_list.get(arg2)));
			startActivity(intent);
			
		}
	};
	
	private ProgressDialog dialog;
	protected List<From> fromCities;
	protected List<To> toCities;
	protected List<TimesbyOperator> Times;
	
	private void getCity() {
		NetworkEngine.getInstance().getCitybyOperator(AppLoginUser.getAccessToken(), OperatorID, new Callback<Cities>() {
		
			public void success(Cities arg0, Response arg1) {
				// TODO Auto-generated method stub
				fromCities = arg0.getFrom();
				toCities = arg0.getTo();
			}

			public void failure(RetrofitError arg0) {
				
			}
			
		});
	}
	
	private void getTimeData() {
		NetworkEngine.getInstance().getTimebyOperator(AppLoginUser.getAccessToken(), OperatorID, new Callback<List<TimesbyOperator>>() {

			public void success(List<TimesbyOperator> arg0, Response arg1) {
				// TODO Auto-generated method stub
				Times = arg0;
			}
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void getBookingList(){
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
		SharedPreferences pref = getSharedPreferences("order", Activity.MODE_PRIVATE);
		String orderDate = pref.getString("order_date", null);
		String from = pref.getString("from", null);
		String to = pref.getString("to", null);
		String time = pref.getString("time", null);
		
		Log.i("","Hello : "+ AppLoginUser.getUserID()+" , Order Date = "+orderDate );
		
		NetworkEngine.getInstance().getBookingOrder(AppLoginUser.getAccessToken(), AppLoginUser.getUserID(), orderDate,
				from, to, time, BookCode, new Callback<List<CreditOrder>>() {
			
			public void success(List<CreditOrder> arg0, Response arg1) {
				// TODO Auto-generated method stub
				credit_list = arg0;
				Log.i("","Hello size: "+ credit_list.size());
				lst_credit.setAdapter(new OrderListViewAdapter(BusBookingListActivity.this, credit_list));
				dialog.dismiss();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				Log.i("","Failure : "+ arg0.getCause());
				dialog.dismiss();
			}
		});
		
	}
	
}
