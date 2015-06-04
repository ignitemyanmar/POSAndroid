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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.TripsCityAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.BundleListObject;
import com.ignite.mm.ticketing.sqlite.database.model.OnlineSalePermitTrips;
import com.ignite.mm.ticketing.sqlite.database.model.Permission;
import com.ignite.mm.ticketing.sqlite.database.model.TripsCollection;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;
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
	
	//Permission Variables
	private String permit_ip, permit_access_token, permit_operator_id, permit_operator_group_id, permit_agent_id;
	private String operator_name;
	private String client_operator_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_trips_city);
		
		//Get Operator ID
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			operatorId = bundle.getString("operator_id");
			operator_name = bundle.getString("operator_name");
			client_operator_id = bundle.getString("client_operator_id");
		}
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		//actionBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		//actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarNoti = (TextView) actionBar.getCustomView().findViewById(R.id.txt_notify_booking);
		actionBarNoti.setOnClickListener(clickListener);
		actionBarTitle.setText(operator_name);
		//actionBarTitle2.setText("Choose Trip");
		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
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
		 /*tripsCollections = new ArrayList<TripsCollection>();
		 tripsCollections.add(new TripsCollection("1", "Yangon", "2", "Mandalay"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "3", "Nay Pyi Taw"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "4", "Pyin Oo Lwin"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "5", "Inle"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "6", "Bagan"));
		 tripsCollections.add(new TripsCollection("1", "Yangon", "7", "Bago"));
		 grd_trips_city.setAdapter(new TripsCityAdapter(BusTripsCityActivity.this, tripsCollections));
		 //grd_trips_city.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		 //setGridViewHeightBasedOnChildren(grd_trips_city, 2);
*/	}
	

	private Permission permission;	
	private List<TripsCollection> tripsCollections;
	private List<OnlineSalePermitTrips> onlineSalePermitTrips;
	private List<TripsCollection> showTripList;
	
	private void getTripsCity(){
		
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
        
        //1. Get Permission
        NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getPermission(AppLoginUser.getAccessToken(), operatorId, new Callback<Response>() {
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
					Log.i("", "Trip Operator ID: "+operatorId);
				}
				
				dialog.dismiss();
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					Log.i("", "Get Trip Body : "+arg0.getBody());
					permission = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Permission>(){}.getType());
					
					if (permission != null) {
						Log.i("", "Trip Operator ID: "+operatorId);
						Log.i("", "Permission: "+permission.toString());
						
						/*PermissionGlobal pSave = new PermissionGlobal(BusTripsCityActivity.this);
						pSave.setIp(permission.getIp());
						pSave.setAccess_token(permission.getAccess_token());
						pSave.setOperator_id(permission.getOperator_id());
						pSave.setOperator_group_id(permission.getOperator_group_id());
						pSave.setAgent_id(permission.getAgent_id());
						pSave.permission();*/
						
						permit_ip = permission.getIp();
						permit_access_token = permission.getAccess_token();
						permit_operator_id = permission.getOperator_id();
						permit_operator_group_id = permission.getOperatorgroup_id();
						permit_agent_id = permission.getOnlinesaleagent_id();												
						
						String param = MCrypt.getInstance().encrypt(SecureParam.getTripsParam(permit_access_token, permit_operator_id));
						
						NetworkEngine.setIP(permit_ip);
						
						Log.i("", "Permit IP: "+permit_ip);
						Log.i("", "Network engine instance: "+NetworkEngine.instance);
						
						//2. Get Trips by OperatorId
						NetworkEngine.getInstance().getTrips(param, new Callback<Response>() {

							public void failure(RetrofitError arg0) {
								// TODO Auto-generated method stub
								if (arg0.getResponse() != null) {
									Log.i("", "Fail permission: "+arg0.getResponse().getStatus());
									Log.i("", "Trip Operator ID: "+operatorId);
								}
								
								dialog.dismiss();
							}

							public void success(Response arg0, Response arg1) {
								// TODO Auto-generated method stub
								if (arg0 != null) {
									
									tripsCollections = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<TripsCollection>>(){}.getType());
									if (tripsCollections != null && tripsCollections.size() > 0) {
										
										Log.i("", "Trip collection: "+tripsCollections.toString());
										
										//3. Get Filter Trips by 24 hrs
										getOnlineSalePermitTrips(tripsCollections);
									}
								}
							}
						});
					}
				}
			}
		});
	}
	
	/**
	 *  Get Online Sale Permit Trips for 24 hrs
	 * @param tripsCollections All Trips by Operator
	 */
	private BundleListObject bundleOnlineTrips;
	
	private void getOnlineSalePermitTrips(final List<TripsCollection> tripsCollections) {
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().getOnlineSalePermitTrip(AppLoginUser.getAccessToken(), client_operator_id, new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "Token: "+AppLoginUser.getAccessToken()+", OperatorId: "+client_operator_id);
				
				if (arg0 != null) {
					
					onlineSalePermitTrips = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OnlineSalePermitTrips>>(){}.getType());
					
					if (onlineSalePermitTrips != null && onlineSalePermitTrips.size() > 0) {
						
						Log.i("", "Online Sale Trips(24hrs): "+onlineSalePermitTrips.toString());
						
						showTripList = new ArrayList<TripsCollection>();
						
							for (int i = 0; i < tripsCollections.size(); i++) {
								boolean flat = false;
								for (int j = 0; j < onlineSalePermitTrips.size(); j++) {
									//all trips fromId & toId  == 24 hrs trips fromId & toId ?? 
									if (tripsCollections.get(i).getFrom_id().equals(onlineSalePermitTrips.get(j).getFromId()) && tripsCollections.get(i).getTo_id().equals(onlineSalePermitTrips.get(j).getToId())) {
										flat = true;
										break;
									}
								}
								//check duplicate trip
								if(flat){
									showTripList.add(tripsCollections.get(i));
								}
							}
							
							//Put OnlineSalePermitTrips into bundle object to send into BusTimeActivity
							bundleOnlineTrips = new BundleListObject();
							for (int i = 0; i < onlineSalePermitTrips.size(); i++) {
								bundleOnlineTrips.getOnlineSalePermitTrips().add(onlineSalePermitTrips.get(i));
							}
						

						Log.i("", "Show Trip List size : "+showTripList.size()+", Show Trip List (24 hrs) : "+showTripList);
						
						if (showTripList != null && showTripList.size() > 0) {
							grd_trips_city.setAdapter(new TripsCityAdapter(BusTripsCityActivity.this, showTripList));
							//grd_trips_city.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
							//setGridViewHeightBasedOnChildren(grd_trips_city, 2);
						}else {
							showAlert("No Trips!");
						}
						
					}
				}
				
				dialog.dismiss();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error Response: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismiss();
			}
		});
	}
	
	
	private Integer bookCount;
	private void getNotiBooking(){
		
/*		String param = MCrypt.getInstance().encrypt(SecureParam.getNotiBookingParam(AppLoginUser.getAccessToken(), getToday()));
		NetworkEngine.getInstance().getNotiBooking(param, new Callback<Response>() {
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					bookCount = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<Integer>(){}.getType());
					SharedPreferences sharedPreferences = getSharedPreferences("NotifyBooking",Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					
					editor.clear();
					editor.commit();
					
					editor.putInt("count", bookCount);
					editor.commit();
					
					if(bookCount > 0){
						actionBarNoti.setVisibility(View.GONE);
						actionBarNoti.setText(bookCount.toString());
					}
				}
			}
		});*/
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			
			final SKCalender skCalender = new SKCalender(BusTripsCityActivity.this);
			
			  skCalender.setCallbacks(new Callbacks() {

					public void onChooseDate(String chooseDate) {
			        	// TODO Auto-generated method stub
						SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
			        	Date formatedDate = null;
			        	Date todayDate = null; 
			        	Date choosedDate = null;
						try {
							formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							choosedDate = ymd.parse(ymd.format(formatedDate));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
			        	Calendar cal = Calendar.getInstance();
						todayDate = cal.getTime();
						
						Long choosedDateLong = choosedDate.getTime();
						Long todayDateLong = todayDate.getTime();
						
			        	Log.i("", "choose date: "+ymd.format(choosedDate)+", todaydate: "+ymd.format(todayDate));
			        	
			        	if (choosedDateLong <= todayDateLong) {
							//showAlert("Please choose grater than today!");
			        		SKToastMessage.showMessage(BusTripsCityActivity.this, "Please choose grater than today!", SKToastMessage.WARNING);
						}else {
							selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
				        	skCalender.dismiss();
				        	
				        	Bundle bundle = new Bundle();
				        	bundle.putString("permit_access_token", permit_access_token);
				        	bundle.putString("operator_id", permit_operator_id);
							bundle.putString("from_id", showTripList.get(arg2).getFrom_id());
							bundle.putString("to_id", showTripList.get(arg2).getTo_id());
							bundle.putString("from", showTripList.get(arg2).getFrom());
							bundle.putString("to", showTripList.get(arg2).getTo());
							bundle.putString("date", selectedDate);
							bundle.putString("permit_ip", permit_ip);
							bundle.putString("operator_name", operator_name);
							bundle.putString("permit_operator_group_id", permit_operator_group_id);
							bundle.putString("permit_agent_id", permit_agent_id);
							bundle.putString("online_sale_permit_trips", new Gson().toJson(bundleOnlineTrips));
							bundle.putString("client_operator_id", client_operator_id);
							
							Log.i("", "To time (from) : "+tripsCollections.get(arg2).getFrom()+", To time(to): "+tripsCollections.get(arg2).getTo());
							
							startActivity(new Intent(getApplicationContext(), 
									BusTimeActivity.class).putExtras(bundle));
							
						}
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
