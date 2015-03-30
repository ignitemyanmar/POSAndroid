package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.ActionBar;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mm.ticketing.custom.listview.adapter.OperatorListAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.TimeAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.City;
import com.ignite.mm.ticketing.sqlite.database.model.OAuth2Error;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.ignite.mm.ticketing.sqlite.database.model.Operators;
import com.ignite.mm.ticketing.sqlite.database.model.Time;

public class BusTimeActivity extends BaseSherlockActivity {
	
	private ConnectionDetector connectionDetector;
	private LinearLayout mLoadingView;
	private LinearLayout mNoConnection;
	private Spinner from , to , time , operator;
	private Button date;
	private String selectedFromId = ""; 
	private String selectedToId = ""; 
	private String selectedTime = "";
	private String selectedOperatorId = "";
	private String selectedAgentId = "";
	private String selectedDate = "";	
	private  List<Time> time_list;
	private Operators operatorList;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	protected List<City> cities;
	protected List<Operator> operators;
	private ListView lst_morning_time;
	private TextView txt_operator;
	private LinearLayout layout_operator;
	private ListView lst_evening_time;
	private List<Time> time_morning_list;
	private List<Time> time_evening_list;
	private String selectedFrom;
	private String selectedTo;
	private TextView actionBarNoti;
	private Integer NotifyBooking;
	protected String selectedClasses;
	private TextView actionBarTitle2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarNoti = (TextView) actionBar.getCustomView().findViewById(R.id.txt_notify_booking);
		actionBarNoti.setOnClickListener(clickListener);
		actionBarBack.setOnClickListener(clickListener);
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.busdest_list);
		
		connectionDetector = new ConnectionDetector(this);
		
		mLoadingView = (LinearLayout) findViewById(R.id.ly_loading);
		mNoConnection = (LinearLayout) findViewById(R.id.no_internet);
			
		from = (Spinner)findViewById(R.id.spn_from);
		
		to = (Spinner)findViewById(R.id.spn_to);
		
		//date = (Button)findViewById(R.id.spn_date);
		//time = (Spinner)findViewById(R.id.spn_time);
		
		txt_operator = (TextView)findViewById(R.id.txt_operator);
		layout_operator = (LinearLayout)findViewById(R.id.layout_operator);
		operator =  (Spinner)findViewById(R.id.spn_operator);
		lst_morning_time = (ListView) findViewById(R.id.lst_time_morning);
		lst_morning_time.setOnItemClickListener(morningTimeClickListener);
		
		lst_evening_time = (ListView) findViewById(R.id.lst_time_evening);
		lst_evening_time.setOnItemClickListener(eveningTimeClickListener);
			
		//date.setOnClickListener(clickListener);
		
		Bundle bundle = getIntent().getExtras();
		selectedOperatorId = bundle.getString("operator_id");
		selectedFromId = bundle.getString("from_id");
		selectedToId = bundle.getString("to_id");
		selectedFrom = bundle.getString("from");
		selectedTo = bundle.getString("to");
		selectedDate = bundle.getString("date");
		
		SharedPreferences notify = getSharedPreferences("NotifyBooking", Context.MODE_PRIVATE);
		NotifyBooking = notify.getInt("count", 0);
		if(NotifyBooking > 0){
			actionBarNoti.setVisibility(View.GONE);
			actionBarNoti.setText(NotifyBooking.toString());
		}
		
		actionBarTitle.setText(selectedFrom+" - "+selectedTo);
		actionBarTitle2.setText("[ "+changeDate(selectedDate)+" ]");
		
		if(connectionDetector.isConnectingToInternet()){
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadingView.startAnimation(topInAnimaiton());
			
			SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
			String user_id = pref.getString("user_id", null);
			String user_type = pref.getString("user_type", null);
			
			Log.i("", "User Type for Time: "+user_type);
			
			if(user_type.equals("Agent")){
				
				txt_operator.setVisibility(View.GONE);
				layout_operator.setVisibility(View.GONE);
				//selectedOperatorId = user_id;
				
				getTime();
				
			}else{
				getOperator();
				operator.setOnItemSelectedListener(operatorClickListener);
			}
		}else{
			mNoConnection.setVisibility(View.VISIBLE);
			mNoConnection.startAnimation(topInAnimaiton());
		}
		
		//from.setOnItemSelectedListener(fromClickListener);
		//to.setOnItemSelectedListener(toClickListener);
		//time.setOnItemSelectedListener(timeClickListener);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(selectedTime.length() != 0){
			finish();
		}
	}
	
	private Animation topInAnimaiton(){
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.top_in);
		anim.reset();
		return anim;
		
	}
	 
	private Animation topOutAnimaiton(){
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.top_out);
			anim.reset();
			return anim;
			
	}
	
	private void getTime() {

		/*SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		final String accessToken = pref.getString("access_token", null);*/
		
		Log.i("", "AccessToken: "+AppLoginUser.getAccessToken()+", OperatorID: "+selectedOperatorId
				+", FromID: "+selectedFromId+", ToID: "+selectedToId+", Date: "+selectedDate);
		
		NetworkEngine.getInstance().getAllTime(AppLoginUser.getAccessToken(), selectedOperatorId
				,selectedFromId, selectedToId, selectedDate, new Callback<List<Time>>() {
				
			public void success(List<Time> arg0, Response arg1) {
				
				Log.i("", "accessToken: "+AppLoginUser.getAccessToken()+", selectedOperatorId: "
						+selectedOperatorId+", selectedFromId: "+selectedFromId+", selectedToId: "+selectedToId+", selectedDate: "+selectedDate);
				
				// TODO Auto-generated method stub
				time_morning_list = new ArrayList<Time>();
				time_evening_list = new ArrayList<Time>();
				
				for(Time time: arg0){
					//String timeStr = time.getTime().replaceAll(" ", "").toLowerCase();
					//String timeTypeStr = timeStr.substring(timeStr.length() - 2, timeStr.length());
					
					if(time.getTime().toLowerCase().contains("am")){
						time_morning_list.add(time);
					}else{
						time_evening_list.add(time);
					}
				}
				
				Log.i("", "Time morning list: "+time_morning_list.toString());
				Log.i("", "Time evening list: "+time_evening_list.toString());
				
				lst_morning_time.setAdapter(new TimeAdapter(BusTimeActivity.this, time_morning_list));
				setListViewHeightBasedOnChildren(lst_morning_time);
				
				lst_evening_time.setAdapter(new TimeAdapter(BusTimeActivity.this, time_evening_list));
				setListViewHeightBasedOnChildren(lst_evening_time);				
				
				mLoadingView.setVisibility(View.GONE);
				mLoadingView.startAnimation(topOutAnimaiton());
				
				if(time_morning_list.size() == 0 && time_evening_list.size() == 0){
					
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(BusTimeActivity.this);
					alertDialog.setTitle("Info");
					alertDialog.setMessage("There is no trip yet.");
					alertDialog.setCancelable(true);
/*					alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});*/
					alertDialog.show();
				}
			}
			public void failure(RetrofitError arg0) {
				
				Log.i("", "accessToken: "+AppLoginUser.getAccessToken()+", selectedOperatorId: "
				+selectedOperatorId+", selectedFromId: "+selectedFromId+", selectedToId: "+selectedToId+", selectedDate: "+selectedDate);
				
				//TODO Auto-generated method stub
				//OAuth2Error error = (OAuth2Error) arg0.getBodyAs(OAuth2Error.class);
				//Log.i("","Hello Error Response Code : "+arg0.getResponse().getStatus());
				/*Log.i("","Hello Error : "+arg0.getError());
				Log.i("","Hello Error Desc : "+arg0.getError_description());*/
			}
		});
		
	}
	
	private OnItemClickListener morningTimeClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedTime = time_morning_list.get(arg2).getTime();
			selectedClasses = time_morning_list.get(arg2).getClass_id();
			
			Bundle bundle = new Bundle();
	        bundle.putString("agent_id", selectedAgentId);
			bundle.putString("operator_id", selectedOperatorId);
			bundle.putString("from_city_id", selectedFromId);
			bundle.putString("from_city", selectedFrom);
			bundle.putString("to_city_id", selectedToId);
			bundle.putString("to_city", selectedTo);
			bundle.putString("class_id", selectedClasses);
			bundle.putString("time", selectedTime);
			bundle.putString("date", selectedDate);
			startActivity(new Intent(getApplicationContext(), BusSelectSeatActivity.class).putExtras(bundle));
			
		}
	};
	
	private OnItemClickListener eveningTimeClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedTime = time_evening_list.get(arg2).getTime();
			selectedClasses = time_evening_list.get(arg2).getClass_id();
			Bundle bundle = new Bundle();
	        bundle.putString("agent_id", selectedAgentId);
			bundle.putString("operator_id", selectedOperatorId);
			bundle.putString("from_city_id", selectedFromId);
			bundle.putString("from_city", selectedFrom);
			bundle.putString("to_city_id", selectedToId);
			bundle.putString("to_city", selectedTo);
			bundle.putString("class_id", selectedClasses);
			bundle.putString("time", selectedTime);
			bundle.putString("date", selectedDate);
			startActivity(new Intent(getApplicationContext(), BusSelectSeatActivity.class).putExtras(bundle));
			
		}
	};
		
	private OnItemSelectedListener operatorClickListener = new OnItemSelectedListener() {
	
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedOperatorId = operators.get(arg2).getId();
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadingView.startAnimation(topInAnimaiton());
			getTime();
			
		}
	
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getOperator() {
		
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		
		NetworkEngine.getInstance().getAllOperators(accessToken, new Callback<Operators>() {
	
			public void success(Operators arg0, Response arg1) {
				// TODO Auto-generated method stub
				operatorList = arg0;
				operators = new ArrayList<Operator>();
				operators.addAll(operatorList.getOperators());
				operator.setAdapter(new OperatorListAdapter(BusTimeActivity.this, operators));
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				OAuth2Error error = (OAuth2Error) arg0.getBodyAs(OAuth2Error.class);
				Log.i("","Hello Error Response Code : "+arg0.getResponse().getStatus());
				Log.i("","Hello Error : "+error.getError());
				Log.i("","Hello Error Desc : "+error.getError_description());
			}
		});
	}
	
	private OnClickListener clickListener	= new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			if(v == actionBarNoti){
				SharedPreferences sharedPreferences = getSharedPreferences("order",MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				editor.putString("order_date", getToday());
				editor.commit();
	        	startActivity(new Intent(getApplicationContext(), BusBookingListActivity.class));
			}
		}
		
	};
	
	private void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
}
