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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.application.MCrypt;
import com.ignite.mm.ticketing.application.SecureParam;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusOperatorGridAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.BusOperatorListViewAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusOperatorActivity extends BaseSherlockActivity {

	private GridView grd_trips_city;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private ListView lv_bus_operator;
	private TextView actionBarNoti;
	private BusOperatorListViewAdapter adapter;
	private ProgressDialog dialog;
	protected List<Operator> operatorList;
	private TextView actionBarTitle2;
	private int NofColumn;
	
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
		//actionBarTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarBack.setOnClickListener(clickListener);
		actionBarNoti = (TextView) actionBar.getCustomView().findViewById(R.id.txt_notify_booking);
		actionBarNoti.setOnClickListener(clickListener);
		actionBarTitle.setText("Choose Bus Operator");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		setContentView(R.layout.activity_bus_operator);
		
		lv_bus_operator = (ListView)findViewById(R.id.lv_bus_operator);
		
		NofColumn = 2;		
		grd_trips_city = (GridView) findViewById(R.id.grd_trips_city);
		grd_trips_city.setNumColumns(NofColumn);
		grd_trips_city.setOnItemClickListener(operatorClickListener);
		
		SKConnectionDetector skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			getOperators();
			//getNotiBooking();
		}else{
			skDetector.showErrorMessage();
			fadeData();
		}

	}
	
	private OnItemClickListener operatorClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putString("operator_id", operatorList.get(position).getId());
			
			Log.i("", "User's Operator ID: "+operatorList.get(position).getId());
			
			startActivity(new Intent(getApplicationContext(), BusTripsCityActivity.class).putExtras(bundle));
			
		}
	};
	
	/**
	 * Get Booking List Notification
	 */
	private Integer bookCount;
	private List<Operator> operators;
	private void getNotiBooking(){
		
		String param = MCrypt.getInstance().encrypt(SecureParam.getNotiBookingParam(AppLoginUser.getAccessToken(), getToday()));
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
		});
	}

	private void fadeData() {
		// TODO Auto-generated method stub
		operatorList = new ArrayList<Operator>();
		
		operatorList.add(new Operator("1", "Elite", "Yangon", ""));
		operatorList.add(new Operator("1", "Mandalar Min", "Yangon", ""));
		operatorList.add(new Operator("1", "Myatmandalar Tun", "Yangon", ""));
		operatorList.add(new Operator("1", "Shwe Sin Sat Kyar", "Yangon", ""));
		operatorList.add(new Operator("1", "Shwe Mandalar", "Yangon", ""));
		operatorList.add(new Operator("1", "Elite", "Yangon", ""));
		operatorList.add(new Operator("1", "Mandalar Min", "Yangon", ""));
		operatorList.add(new Operator("1", "Myatmandalar Tun", "Yangon", ""));
		operatorList.add(new Operator("1", "Shwe Sin Sat Kyar", "Yangon", ""));
		operatorList.add(new Operator("1", "Shwe Mandalar", "Yangon", ""));
		operatorList.add(new Operator("1", "Elite", "Yangon", ""));
		operatorList.add(new Operator("1", "Mandalar Min", "Yangon", ""));
		operatorList.add(new Operator("1", "Myatmandalar Tun", "Yangon", ""));
		operatorList.add(new Operator("1", "Shwe Sin Sat Kyar", "Yangon", ""));
		operatorList.add(new Operator("1", "Shwe Mandalar", "Yangon", ""));
		
		adapter = new BusOperatorListViewAdapter(BusOperatorActivity.this, operatorList);
		lv_bus_operator.setAdapter(adapter);
	}

	
	private void getOperators() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(BusOperatorActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(false);
		
		/*SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String userID = pref.getString("user_id", null);	*/			
		
		//String param = MCrypt.getInstance().encrypt(SecureParam.getAllOperatorsParam(AppLoginUser.getAccessToken()));
		NetworkEngine.setIP("app.easyticket.com.mm");
		NetworkEngine.getInstance().getAllOperator(AppLoginUser.getAccessToken(), new Callback<List<Operator>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
				}
				
				dialog.dismiss();
			}

			public void success(List<Operator> arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					
					operatorList = arg0;
					Log.i("", "Operator List: "+arg0.toString());
					grd_trips_city.setAdapter(new BusOperatorGridAdapter(BusOperatorActivity.this, operatorList));
					
					dialog.dismiss();
				}
			}
		});
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == actionBarBack) {
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
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null && listView.getCount() == 0) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

}

