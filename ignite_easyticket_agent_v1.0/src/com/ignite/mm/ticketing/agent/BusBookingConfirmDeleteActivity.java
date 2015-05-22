package com.ignite.mm.ticketing.agent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;

public class BusBookingConfirmDeleteActivity extends BaseSherlockActivity {
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private Button btn_pay;
	private Button btn_cancel_order;
	private Button btn_back;
	private String creditOrderString;
	private CreditOrder creditOrder;
	private TextView action_bar_title2;
	private String todayDate;
	private String todayTime;

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
		actionBarTitle.setText("Star Ticket");
		action_bar_title2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		action_bar_title2.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_pay_delete);
		
		btn_pay = (Button) findViewById(R.id.btn_pay);
		btn_cancel_order = (Button) findViewById(R.id.btn_cancel);
		btn_back = (Button) findViewById(R.id.btn_back);
		
		btn_pay.setOnClickListener(clickListener);
		btn_cancel_order.setOnClickListener(clickListener);
		btn_back.setOnClickListener(clickListener);
		
		//Get TodayDate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		todayDate = sdf.format(new Date());
		
		Log.i("", "Today date: "+todayDate);
		
		//Get Current Time
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		todayTime = sdf2.format(cal.getTime()).toString();
		
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
					if (i == creditOrder.getSaleitems().size() - 1) {
						SeatLists += creditOrder.getSaleitems().get(i).getSeatNo();
					}else {
						SeatLists += creditOrder.getSaleitems().get(i).getSeatNo()+",";
					}
				}
				
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "booking");
				bundle.putString("Operator_Name", creditOrder.getOperator());
				bundle.putString("from_to", creditOrder.getTrip());
				bundle.putString("time", creditOrder.getTime());
				bundle.putString("classes",creditOrder.getClasses());
				bundle.putString("date", creditOrder.getDate());
				bundle.putString("agent_id", creditOrder.getAgentId().toString());
				bundle.putString("name", creditOrder.getCustomer());
				bundle.putString("phone", creditOrder.getPhone());
				bundle.putString("Price", creditOrder.getPrice().toString());
				bundle.putString("selected_seat",  SeatLists);
				bundle.putString("sale_order_no", creditOrder.getId().toString());
				bundle.putString("order_date", creditOrder.getOrderdate());
				bundle.putString("bus_occurence", creditOrder.getSaleitems().get(0).getTripId().toString());
				bundle.putString("ConfirmDate", todayDate);
				bundle.putString("ConfirmTime", todayTime);
				bundle.putString("permit_access_token", creditOrder.getPermit_access_token());
				bundle.putString("permit_ip", creditOrder.getPermit_ip());
				bundle.putString("permit_operator_group_id", creditOrder.getPermit_operator_group_id());
				bundle.putString("permit_agent_id", creditOrder.getPermit_agent_id());
				//Get Seat Count
				String[] seats = SeatLists.split(",");
				bundle.putString("SeatCount", seats.length+"");
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
/*		NetworkEngine.getInstance().confirmBooking(accessToken, creditOrder.getId().toString(), new Callback<JSONObject>() {
			
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
		});*/
	}
}
