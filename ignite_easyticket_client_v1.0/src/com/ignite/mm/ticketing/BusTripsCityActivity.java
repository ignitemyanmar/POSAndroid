package com.ignite.mm.ticketing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DeviceUtil;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.TripsCityAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.TripsCollection;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusTripsCityActivity extends BaseSherlockActivity{
	private GridView grd_trips_city;
	private ProgressDialog dialog;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private int NofColumn;
	private String selectedDate;
	private TextView actionBarNoti;
	private String operatorId;
	private TextView actionBarTitle2;

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
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarNoti = (TextView) actionBar.getCustomView().findViewById(R.id.txt_notify_booking);
		actionBarNoti.setOnClickListener(clickListener);
		actionBarTitle.setText("Choose City");
		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_trips_city);
		
		//Get Operator ID
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			operatorId = bundle.getString("operator_id");
		}
		
		NofColumn = 2;		
		grd_trips_city = (GridView) findViewById(R.id.grd_trips_city);
		grd_trips_city.setNumColumns(NofColumn);
		grd_trips_city.setOnItemClickListener(itemClickListener);
		
		SKConnectionDetector skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			getTripsCity();
			getNotiBooking();
		}else{
			skDetector.showErrorMessage();
			fadeData();
		}
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
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
	        	startActivity(new Intent(getApplicationContext(),	BusBookingListActivity.class));
			}
		}
	};
	
	private void fadeData(){
		 tripsCollections = new ArrayList<TripsCollection>();
		 tripsCollections.add(new TripsCollection("1", "Yangon", "2", "Mandalay"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "3", "Nay Pyi Taw"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "4", "Pyin Oo Lwin"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "5", "Inle"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "6", "Bagan"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "7", "Bago"));
		 grd_trips_city.setAdapter(new TripsCityAdapter(BusTripsCityActivity.this, tripsCollections));
		 //grd_trips_city.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		 //setGridViewHeightBasedOnChildren(grd_trips_city, 2);
	}
	

	protected List<TripsCollection> tripsCollections;	
	
	private void getTripsCity(){
		
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
		
		NetworkEngine.getInstance().getTrips(AppLoginUser.getAccessToken(), operatorId, new Callback<List<TripsCollection>>() {
			
			public void success(List<TripsCollection> arg0, Response arg1) {
				// TODO Auto-generated method stub
				
				if (arg0.size() > 0 && arg0 != null) {
					
					tripsCollections = arg0;
					
					Log.i("", "Trip Operator ID: "+operatorId);
					Log.i("", "Trip List: "+tripsCollections.toString());
					
					grd_trips_city.setAdapter(new TripsCityAdapter(BusTripsCityActivity.this, tripsCollections));
					//grd_trips_city.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
					//setGridViewHeightBasedOnChildren(grd_trips_city, 2);
					dialog.dismiss();
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	
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
					//actionBarNoti.setVisibility(View.VISIBLE);
					actionBarNoti.setText(arg0.toString());
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			final SKCalender skCalender = new SKCalender(BusTripsCityActivity.this);
			
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
						
			        	selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
			        	skCalender.dismiss();
			        	
			        	Bundle bundle = new Bundle();
			        	bundle.putString("operator_id", operatorId);
						bundle.putString("from_id", tripsCollections.get(arg2).getFrom_id());
						bundle.putString("to_id", tripsCollections.get(arg2).getTo_id());
						bundle.putString("from", tripsCollections.get(arg2).getFrom());
						bundle.putString("to", tripsCollections.get(arg2).getTo());
						bundle.putString("date", selectedDate);
						
						startActivity(new Intent(getApplicationContext(), BusTimeActivity.class).putExtras(bundle));			        	
			        	
			        }
			  });

			skCalender.show();
			
		}
	};
	
	public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null || listAdapter.getCount() == 0) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int items = listAdapter.getCount();
		int rows = 0;

		View listItem = listAdapter.getView(0, null, gridView);
		listItem.measure(0, 0);
		totalHeight = listItem.getMeasuredHeight();

		float x = 1;
		if (items > columns) {
			x = items / columns;
			rows = (int) (x + 1);
			totalHeight *= rows;
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);
		
	}
}
