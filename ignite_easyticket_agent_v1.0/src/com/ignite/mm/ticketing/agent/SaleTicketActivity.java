package com.ignite.mm.ticketing.agent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.FromCitiesAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.ToCitiesAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.TripTimeAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Times;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class SaleTicketActivity extends BaseSherlockActivity{

	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private Spinner spn_from_trip;
	private Spinner spn_to_trip;
	private Spinner spn_trip_date;
	private Spinner spn_trip_time;
	private Button btn_search;
	private ArrayList<String> fromCities;
	private FromCitiesAdapter fromCitiesAdapter;
	private String selectedFromCity;
	private ProgressDialog dialog;
	private ArrayList<String> toCities;
	private ToCitiesAdapter toCitiesAdapter;
	private String selectedToCity;
	private String selectedTripDate;
	private String currentDate;
	private String tomorrowDate;
	private Button btn_trip_date;
	private List<Times> tripTimes;
	private TripTimeAdapter tripTimeAdapter;
	private SKConnectionDetector skDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		//actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarTitle.setText("Sale Tickets");
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_sale_ticket);
		
		spn_from_trip = (Spinner)findViewById(R.id.spn_from_trip);
		spn_to_trip = (Spinner)findViewById(R.id.spn_to_trip);
		btn_trip_date = (Button)findViewById(R.id.btn_trip_date);
		spn_trip_time = (Spinner)findViewById(R.id.spn_trip_time);
		btn_search = (Button)findViewById(R.id.btn_search);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		currentDate = sdf.format(cal.getTime());
		
		//Add one day to current date time
		cal.add(Calendar.DATE, 1);
		tomorrowDate = sdf.format(cal.getTime());
		btn_trip_date.setText(currentDate);
		
		skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			
			dialog = ProgressDialog.show(SaleTicketActivity.this, "", "Please wait ...", true);
			dialog.setCancelable(true);
			
			getFromCities();
			getToCities();
		}else{
			skDetector.showErrorMessage();
		}
		
		spn_from_trip.setOnItemSelectedListener(fromCityClickListener);
		spn_to_trip.setOnItemSelectedListener(toCityClickListener);
		spn_trip_time.setOnItemSelectedListener(tripTimeClickListener);
		
		btn_trip_date.setOnClickListener(clickListener);
		btn_search.setOnClickListener(clickListener);
	}

	private void getFromCities() {
		// TODO Auto-generated method stub
		
		fromCities = new ArrayList<String>();
		fromCities.add("Choose - From City");
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getFromCities(AppLoginUser.getAccessToken(), new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub

				if (arg0 != null) {
					List<String> fromCityList = new ArrayList<String>();
					fromCityList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<String>>(){}.getType());
					
					fromCities.addAll(fromCityList);
					
					if (fromCities != null && fromCities.size() > 0) {
						fromCitiesAdapter = new FromCitiesAdapter(SaleTicketActivity.this, fromCities);
						spn_from_trip.setAdapter(fromCitiesAdapter);						
					}
				}
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
			}
		});
	}

	private void getToCities() {
		// TODO Auto-generated method stub

		toCities = new ArrayList<String>();
		toCities.add("Choose - To City");
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getToCities(AppLoginUser.getAccessToken(), new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub

				if (arg0 != null) {
					List<String> toCityList = new ArrayList<String>();
					toCityList = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<String>>(){}.getType());
					
					toCities.addAll(toCityList);
					
					if (toCities != null && toCities.size() > 0) {
						toCitiesAdapter = new ToCitiesAdapter(SaleTicketActivity.this, toCities);
						spn_to_trip.setAdapter(toCitiesAdapter);		
					}
				}
				
				dialog.dismiss();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		});
	}
	
	private void getTripTime() {
		// TODO Auto-generated method stub

			dialog = ProgressDialog.show(SaleTicketActivity.this, "", "Please wait ...", true);
			dialog.setCancelable(true);
			
			tripTimes = new ArrayList<Times>();
			
			NetworkEngine.setIP("starticketmyanmar.com");
			NetworkEngine.getInstance().getTimesByTrip(AppLoginUser.getAccessToken(), selectedFromCity, selectedToCity, new Callback<Response>() {
				
				public void success(Response arg0, Response arg1) {
					// TODO Auto-generated method stub

					if (arg0 != null) {
						List<Times> Times = new ArrayList<Times>();
						Times = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<Times>>(){}.getType());
						
						if (Times != null && Times.size() > 0) {
							tripTimes.add(new Times("Choose Time (All)", "", ""));
							tripTimes.addAll(Times);
						}
						
						if (tripTimes != null && tripTimes.size() > 0) {
							tripTimeAdapter = new TripTimeAdapter(SaleTicketActivity.this, tripTimes);
							spn_trip_time.setAdapter(tripTimeAdapter);						
						}
					}
					
					dialog.dismiss();
				}
				
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					if (arg0.getResponse() != null) {
						Log.i("", "Error: "+arg0.getResponse().getStatus());
					}
					
					if (dialog != null) {
						dialog.dismiss();
					}
				}
			});
	}
	
	//----------------------------------------- Click Listener ---------------------------------------------------------------------

	private OnItemSelectedListener fromCityClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
				if (position > 0) {
					selectedFromCity = fromCities.get(position);
				}else {
					selectedFromCity = "";
				}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	}; 
	
	private OnItemSelectedListener toCityClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
				if (position > 0) {
					selectedToCity = toCities.get(position);
					if (selectedFromCity.equals("Choose - From City") || selectedToCity.equals("Choose - To City")) {
						SKToastMessage.showMessage(SaleTicketActivity.this, "ခရီးစဥ္  အခ်ိန္မ်ား ၾကည့္ရန္ သြားမည့္ ခရီးကုိ ေရြးပါ", SKToastMessage.WARNING);
					}else {
						if(skDetector.isConnectingToInternet()){
							getTripTime();
						}else{
							skDetector.showErrorMessage();
						}
					}
				}else {
					selectedToCity = "";
				}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	protected String selectedTripTime; 
	
	private OnItemSelectedListener tripTimeClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
				if (position > 0) {
					selectedTripTime = tripTimes.get(position).getTime();
				}else {
					selectedTripTime = "";
				}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_trip_date) {
				
				final SKCalender skCalender = new SKCalender(SaleTicketActivity.this);
				skCalender.setCallbacks(new Callbacks() {

					public void onChooseDate(String chooseDate) {
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
						int compare = formatedDate.compareTo(today);
						Log.i("","Hello Compare : "+ compare);
						if(compare >= 0){
							selectedTripDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
							btn_trip_date.setText(selectedTripDate);
				        	skCalender.dismiss();
						}else {
							SKToastMessage.showMessage(SaleTicketActivity.this, "Please choose today or grater than today!", SKToastMessage.WARNING);
						}
					}
				});
				
				skCalender.show();
			}
			if (v == btn_search) {
				
				if (checkFields()) {
					Bundle bundle = new Bundle();
					bundle.putString("from_city", selectedFromCity);
					bundle.putString("to_city", selectedToCity);
					bundle.putString("trip_date", btn_trip_date.getText().toString());
					bundle.putString("trip_time", selectedTripTime);
					
					startActivity(new Intent(SaleTicketActivity.this, BusOperatorSeatsActivity.class).putExtras(bundle));
				}
			}
		}
	};
	
	public boolean checkFields() {
		
		// TODO Auto-generated method stub
		if (spn_from_trip.getSelectedItem().toString().equals("Choose - From City")) {
			SKToastMessage.showMessage(SaleTicketActivity.this, "ခရီးစဥ္ ( မွ ) ကုိ ေရြးပါ", SKToastMessage.WARNING);
			return false;
		}
		if (spn_to_trip.getSelectedItem().toString().equals("Choose - To City")) {
			SKToastMessage.showMessage(SaleTicketActivity.this, "ခရီးစဥ္ ( သုိ႔ ) ကုိ ေရြးပါ", SKToastMessage.WARNING);
			return false;
		}		
		
		return true;
	}
}
