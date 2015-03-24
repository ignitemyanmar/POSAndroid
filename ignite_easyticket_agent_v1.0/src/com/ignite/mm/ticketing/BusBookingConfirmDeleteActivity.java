package com.ignite.mm.ticketing;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusBookingConfirmDeleteActivity extends BaseSherlockActivity {
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private Button btn_pay;
	private Button btn_cancel_order;
	private Button btn_back;
	private String creditOrderString;
	private CreditOrder creditOrder;

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
		actionBarTitle.setText("Easy Ticket");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_pay_delete);
		
		btn_pay = (Button) findViewById(R.id.btn_pay);
		btn_cancel_order = (Button) findViewById(R.id.btn_cancel);
		btn_back = (Button) findViewById(R.id.btn_back);
		
		btn_pay.setOnClickListener(clickListener);
		btn_cancel_order.setOnClickListener(clickListener);
		btn_back.setOnClickListener(clickListener);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(creditOrderString != null){
			finish();
		}else{
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
			   creditOrderString = extras.getString("credit_order");
			}
			creditOrder = new Gson().fromJson(creditOrderString, CreditOrder.class);
		}
	}
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			
			if(v == btn_pay){
				Intent nextScreen = new Intent(BusBookingConfirmDeleteActivity.this, BusConfirmActivity.class);
				String SeatLists = "";
				for(int i=0; i<creditOrder.getSaleitems().size(); i++){
					SeatLists += creditOrder.getSaleitems().get(i).getSeatNo()+",";
				}
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "booking");
				bundle.putString("from_to", creditOrder.getTrip());
				bundle.putString("time", creditOrder.getTime());
				bundle.putString("classes",creditOrder.getClasses());
				bundle.putString("date", creditOrder.getDate());
				bundle.putString("agent_id", creditOrder.getAgentId().toString());
				bundle.putString("name", creditOrder.getCustomer());
				bundle.putString("phone", creditOrder.getPhone());
				bundle.putString("selected_seat",  SeatLists);
				bundle.putString("sale_order_no", creditOrder.getId().toString());
				bundle.putString("bus_occurence", creditOrder.getSaleitems().get(0).getBusoccuranceId().toString());
				nextScreen.putExtras(bundle);
				startActivity(nextScreen);
			}
			
			if(v == btn_cancel_order){
				Intent intent = new Intent(getApplicationContext(), BusBookingDetailActivity.class);
				intent.putExtra("credit_order", new Gson().toJson(creditOrder));
				startActivity(intent);
			}
			
			if(v == btn_back){
				finish();
			}
		}
	};
	private ProgressDialog dialog;
	
	private void payCredit(){
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		NetworkEngine.getInstance().confirmBooking(accessToken, creditOrder.getId().toString(), new Callback<JSONObject>() {
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				SKToastMessage.showMessage(BusBookingConfirmDeleteActivity.this, "Can't confirmed.", SKToastMessage.ERROR);
			}

			public void success(JSONObject arg0, Response arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				SKToastMessage.showMessage(BusBookingConfirmDeleteActivity.this, "Successfully confirm.", SKToastMessage.SUCCESS);
				finish();
			}
		});
	}
}
