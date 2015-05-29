package com.ignite.mm.ticketing.agent;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.google.gson.reflect.TypeToken;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.application.DecompressGZIP;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.BusOperatorGridAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.ThreeDaySalesLvAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class ThreeDaySalesActivity extends BaseSherlockActivity{

	private String intents = "";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private TextView actionBarTitle2;
	private ImageButton actionBarBack;
	private ListView lv_threeday_sales;
	private ProgressDialog dialog;
	private List<ThreeDaySale> lst_threeday_sale;
	private SKConnectionDetector skDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_threeday_sales);
		
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
		actionBarBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBarTitle.setText("3 Days Sales");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		lv_threeday_sales = (ListView)findViewById(R.id.lst_threeday_sales);				
	}

	/**
	 *  Get Sales Reports for 3 days
	 */
	private void getThreeDaySales() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(ThreeDaySalesActivity.this, "", "Please wait ...", true);
		dialog.setCancelable(false);
		
		Log.i("", "Access Token: "+AppLoginUser.getAccessToken()+"Code No: "+AppLoginUser.getCodeNo());
		
		NetworkEngine.setIP("app.easyticket.com.mm");
		NetworkEngine.getInstance().getThreeDaySales(AppLoginUser.getAccessToken(), AppLoginUser.getCodeNo(), 
				new Callback<List<ThreeDaySale>>() {

			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getResponse() != null) {
					Log.e("",
							"Item Request Error : Response Code = "
									+ arg0.getResponse()
											.getStatus());
					Log.e("", "Error URL: "+arg0.getUrl());
				}
				
				dialog.dismiss();
			}

			public void success(List<ThreeDaySale> arg0, Response arg1) {
				// TODO Auto-generated method stub
				Log.i("", "Success Three Day sales");
				
				if (arg0 != null) {
					
					Log.i("", "not null "+arg0.toString());
					//lst_threeday_sale = DecompressGZIP.fromBody(arg0.getBody(), new TypeToken<List<ThreeDaySale>>(){}.getType());
					
					lst_threeday_sale = arg0;
					
					if (lst_threeday_sale != null && lst_threeday_sale.size() > 0) {
						Log.i("", "Three Day Sale List: "+lst_threeday_sale.toString());
						
						lv_threeday_sales.setAdapter(new ThreeDaySalesLvAdapter(ThreeDaySalesActivity.this, lst_threeday_sale));
					}
					
					dialog.dismiss();
				}
			}
		});		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		skDetector = SKConnectionDetector.getInstance(this);
		skDetector.setMessageStyle(SKConnectionDetector.VERTICAL_TOASH);
		if(skDetector.isConnectingToInternet()){
			getThreeDaySales();
		}else{
			skDetector.showErrorMessage();
		}
	}

}
