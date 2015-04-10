package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.connection.detector.ConnectionDetector;
import com.ignite.mm.ticketing.custom.listview.adapter.OperatorListAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.BusListAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.DeperatureListAdapter;
import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.CityList;
import com.ignite.mm.ticketing.sqlite.database.model.OAuth2Error;
import com.ignite.mm.ticketing.sqlite.database.model.Operators;
import com.ignite.mm.ticketing.sqlite.database.model.BusDestination;
import com.ignite.mm.ticketing.sqlite.database.model.Time;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class BusFragment extends SherlockFragment{
	private View rootView;
	private  List<BusDestination> bdList;
	private CityList cityList;
	private DatabaseManager dbmanager;
	private String TicketTypeID;
	private ConnectionDetector connectionDetector;
	private LinearLayout mLoadingView;
	private LinearLayout mNoConnection;
	private boolean isLoaded;
	public static boolean mLoading = false;
	//URL to get JSON Array
		private static String url_city = "http://192.168.1.128/city";
		private static String url_time = "http://192.168.1.128/time";
		private static String url_operator= "http://192.168.1.128/operator";
	
		private Spinner from , to , time , operator;
		private Button date ,search;
		private String selectedFromId , selectedToId, selectedTimeId , selectedAgentId;	
		private  List<Time> time_list;
		private Operators operators;
		private ImageButton actionBarSearch;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		connectionDetector = new ConnectionDetector(getActivity());

	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.busdest_list, container, false); //**
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		//lvBus = (ListView) rootView.findViewById(R.id.listBus);
		mLoadingView = (LinearLayout) rootView.findViewById(R.id.ly_loading);
		mNoConnection = (LinearLayout) rootView.findViewById(R.id.no_internet);
			
		TicketTypeID = MainFragment.TicketTypeId;
		
		from = (Spinner)rootView.findViewById(R.id.spn_from);
		
		to = (Spinner)rootView.findViewById(R.id.spn_to);
		
		//date = (Button)rootView.findViewById(R.id.spn_date);
		//time = (Spinner)rootView.findViewById(R.id.spn_time);
		
		operator =  (Spinner)rootView.findViewById(R.id.spn_operator);
		
		/*search = (Button)rootView.findViewById(R.id.btnSearch);
		search.setOnClickListener(clickListener);*/
		date.setOnClickListener(clickListener);
		
		
		if(connectionDetector.isConnectingToInternet()){
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadingView.startAnimation(topInAnimaiton());
			
			getCity();
			getDataOperator();
			getDataTime();
		}else{
			mNoConnection.setVisibility(View.VISIBLE);
			mNoConnection.startAnimation(topInAnimaiton());
			
			getFrom();
			getTo();
			getOperators();
			getTime();
		}
		from.setOnItemSelectedListener(fromClickListener);
		to.setOnItemSelectedListener(fromClickListener);
		time.setOnItemSelectedListener(timeClickListener);
		operator.setOnItemSelectedListener(operatorClickListener);
		
	}
	
	 private Animation topInAnimaiton(){
			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.top_in);
			anim.reset();
			return anim;
			
	 }
	 
	 private Animation topOutAnimaiton(){
			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.top_out);
			anim.reset();
			return anim;
			
	}

	private void getCity() {
		SharedPreferences pref = this.getActivity().getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
/*		NetworkEngine.getInstance().getAllCity(accessToken, new Callback<CityList>() {
		
			public void success(CityList arg0, Response arg1) {
				// TODO Auto-generated method stub
				cityList = arg0;
				getFrom();
				getTo();
				mLoadingView.setVisibility(View.GONE);
				mLoadingView.startAnimation(topOutAnimaiton());
			}

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				OAuth2Error error = (OAuth2Error) arg0.getBodyAs(OAuth2Error.class);
				Log.i("","Hello Error Response Code : "+arg0.getResponse().getStatus());
				Log.i("","Hello Error : "+error.getError());
				Log.i("","Hello Error Desc : "+error.getError_description());
			}
			
		});*/
	}
	
	private void getFrom(){
		/*dbmanager = new DatabaseBusDestination(getActivity());
		bdList = ((DatabaseBusDestination) dbmanager).getAllBusDestination();*/
		from.setAdapter(new BusListAdapter(getActivity(), cityList.getCities()));
	}
	
	private OnItemSelectedListener fromClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedFromId = null;
			selectedFromId = cityList.getCities().get(arg2).getId();
			//Log.i("","selectedFromId :"+ selectedFromId);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private void getTo()
	{
		/*dbmanager = new DatabaseBusDestination(getActivity());
		bdList = ((DatabaseBusDestination) dbmanager).getAllBusDestination();*/
			
		to.setAdapter(new BusListAdapter(getActivity(),cityList.getCities()));
	}
	
	private void getTime()
	{
		/*dbmanager = new DatabaseDeperatureTime(getActivity());
		time_list = ((DatabaseDeperatureTime)dbmanager).getAllDeperature();*/
		
		time.setAdapter(new DeperatureListAdapter(getActivity(),time_list));
	}
	
	private OnItemSelectedListener timeClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedTimeId = null;
			selectedTimeId = time_list.get(arg2).getTripid();
			//Log.i("","selectedTimeID :"+ selectedTimeId);
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getDataTime() {
		/*SharedPreferences pref = this.getActivity().getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		NetworkEngine.getInstance().getAllTime(accessToken, new Callback<List<Time>>() {

			public void success(List<Time> arg0, Response arg1) {
				// TODO Auto-generated method stub
				time_list = arg0;
				getTime();
				mLoadingView.setVisibility(View.GONE);
				mLoadingView.startAnimation(topOutAnimaiton());
			}
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				OAuth2Error error = (OAuth2Error) arg0.getBodyAs(OAuth2Error.class);
				Log.i("","Hello Error Response Code : "+arg0.getResponse().getStatus());
				Log.i("","Hello Error : "+error.getError());
				Log.i("","Hello Error Desc : "+error.getError_description());
			}
		});*/
		
	}
	
	private void getOperators()
	{
		/*dbmanager = new DatabaseOperator(getActivity());
		operators = ((DatabaseOperator)dbmanager).getAllAgentsList();*/
		operator.setAdapter(new OperatorListAdapter(getActivity(), operators.getOperators()));
	}
	
	private OnItemSelectedListener operatorClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedAgentId = null;
			selectedAgentId = operators.getOperators().get(0).getId();
			//Log.i("","selectedAgentId :" + selectedAgentId);
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getDataOperator() {
		SharedPreferences pref = this.getActivity().getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
/*		NetworkEngine.getInstance().getAllOperators(accessToken, new Callback<Operators>() {

			public void success(Operators arg0, Response arg1) {
				// TODO Auto-generated method stub
				operators = arg0;
				getOperators();
				mLoadingView.setVisibility(View.GONE);
				mLoadingView.startAnimation(topOutAnimaiton());
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				OAuth2Error error = (OAuth2Error) arg0.getBodyAs(OAuth2Error.class);
				Log.i("","Hello Error Response Code : "+arg0.getResponse().getStatus());
				Log.i("","Hello Error : "+error.getError());
				Log.i("","Hello Error Desc : "+error.getError_description());
			}
		});*/
	}
	
	private OnClickListener clickListener	= new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == date)
			{
				final SKCalender skCalender = new SKCalender(getActivity());

				  skCalender.setCallbacks(new Callbacks() {

				        public void onChooseDate(String chooseDate) {
				          // TODO Auto-generated method stub
				        	date.setText(chooseDate);
				        	skCalender.dismiss();
				        }
				  });

				  skCalender.show();
			}
		}
	};
	
}
