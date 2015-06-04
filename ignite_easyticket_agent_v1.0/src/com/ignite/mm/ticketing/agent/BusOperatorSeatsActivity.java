package com.ignite.mm.ticketing.agent;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.ActionBar;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OperatorSeatsAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorSeat;
import com.ignite.mm.ticketing.sqlite.database.model.SearchOperatorSeat;
import com.ignite.mm.ticketing.sqlite.database.model.Trip;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusOperatorSeatsActivity extends BaseSherlockActivity{

	private String selectedFromCity = "";
	private String selectedToCity = "";
	private String selectedTripDate = "";
	private String selectedTripTime = "";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private ListView lv_operator_seats;
	private ProgressDialog dialog;
	private List<OperatorSeat> OperatorSeats;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_show_operator_seats);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
			selectedFromCity = bundle.getString("from_city");
			selectedToCity = bundle.getString("to_city");
			selectedTripDate = bundle.getString("trip_date");
			selectedTripTime = bundle.getString("trip_time");
		}
		
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
		
		actionBarBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBarTitle.setText(selectedFromCity+" - "+selectedToCity);
		
		if (selectedTripTime != null) {
			if (!selectedTripDate.equals("") && selectedTripDate != null) {
				if (!selectedTripTime.equals("") && selectedTripTime != null) {
					actionBarTitle2.setText(changeDate(selectedTripDate)+" ["+selectedTripTime+"]");
				}else if (selectedTripTime.equals("") || selectedTripTime == null)
				{
					actionBarTitle2.setText(changeDate(selectedTripDate)+" [All Time]");
				}
			}else {
				actionBarTitle2.setText("00/00/0000 [ 00:00:00 ]");
			}
		}
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		lv_operator_seats = (ListView)findViewById(R.id.lv_operator_seats);
		lv_operator_seats.setDividerHeight(0);
		lv_operator_seats.setOnItemClickListener(clickListener);
		
		SKConnectionDetector skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			getOperatorSeats();
		}else{
			skDetector.showErrorMessage();
		}
	}

	private void getOperatorSeats() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(BusOperatorSeatsActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(true);
		
		NetworkEngine.setIP("starticketmyanmar.com");
		NetworkEngine.getInstance().postSearch(selectedFromCity, selectedToCity, selectedTripDate
				, selectedTripTime, AppLoginUser.getAccessToken(), new Callback<Response>() {
			
			public void success(Response arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					OperatorSeats = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<OperatorSeat>>(){}.getType());
					
					if (OperatorSeats != null && OperatorSeats.size() > 0) {
						
						lv_operator_seats.setAdapter(new OperatorSeatsAdapter(BusOperatorSeatsActivity.this, OperatorSeats));
						setListViewHeightBasedOnChildren(lv_operator_seats);
					}else {
						showAlert("No Trip!");
					}
				}
				
				dialog.dismiss();
			}
			
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.i("", "Error: "+arg0.getResponse().getStatus());
				}
				
				dialog.dismiss();
			}
		});
	}
	
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putString("operator_id", OperatorSeats.get(position).getOperatorId());
			bundle.putString("operator_name", OperatorSeats.get(position).getOperator());
			bundle.putString("tripId", OperatorSeats.get(position).getId());
			bundle.putString("from_city", OperatorSeats.get(position).getFromName());
			bundle.putString("to_city", OperatorSeats.get(position).getToName());
			bundle.putString("trip_date", selectedTripDate);
			bundle.putString("trip_time", OperatorSeats.get(position).getTime());
			
			startActivity(new Intent(BusOperatorSeatsActivity.this, BusSelectSeatActivity.class).putExtras(bundle));
		}
	};
	
	public void setListViewHeightBasedOnChildren(ListView listView) {
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
