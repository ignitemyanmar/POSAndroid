
package com.ignite.mm.ticketing;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.clientapi.NetworkEngine;
import com.ignite.mm.ticketing.custom.listview.adapter.OrderListViewAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.CustomerTicketListViewAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;
import com.ignite.mm.ticketing.sqlite.database.model.Saleitem;
import com.smk.skalertmessage.SKToastMessage;
import com.smk.skconnectiondetector.SKConnectionDetector;

public class BusBookingDetailActivity extends BaseSherlockActivity {
	private ListView lst_customer_ticket;
	private List<Saleitem> ticket_list;
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private CheckBox chk_remove_all;
	private CustomerTicketListViewAdapter customerTicketAdapter;
	private boolean childChecked = false;
	private String creditOrderString;
	private CreditOrder creditOrder;
	private TextView txt_customer_name;
	private TextView txt_phone;
	private Button btn_delete;

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
		
		setContentView(R.layout.activity_busticketing_customer_ticket);
		lst_customer_ticket = (ListView) findViewById(R.id.lst_customer_ticket);
		txt_customer_name = (TextView) findViewById(R.id.txt_customer_name);
		txt_phone = (TextView) findViewById(R.id.txt_phone);
		btn_delete = (Button) findViewById(R.id.btn_cancel);
		btn_delete.setOnClickListener(clickListener);
		
		chk_remove_all = (CheckBox) findViewById(R.id.chk_remove_all);
		chk_remove_all.setOnClickListener(clickListener);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   creditOrderString = extras.getString("credit_order");
		}
		creditOrder = new Gson().fromJson(creditOrderString, CreditOrder.class);
		
		ticket_list = new ArrayList<Saleitem>();
		ticket_list.addAll(creditOrder.getSaleitems());
		
		txt_customer_name.setText(creditOrder.getCustomer().toString());
		txt_phone.setText("Phone: "+creditOrder.getPhone());
		
		customerTicketAdapter = new CustomerTicketListViewAdapter(this, ticket_list);
		customerTicketAdapter.setCallbacks(childCheckedCallback);
		lst_customer_ticket.setAdapter(customerTicketAdapter);
		setListViewHeightBasedOnChildren(lst_customer_ticket);	
		
	}
	
	private CustomerTicketListViewAdapter.Callback childCheckedCallback = new CustomerTicketListViewAdapter.Callback() {
		
		public void onChildItemCheckedChange(int position, boolean isChecked) {
			// TODO Auto-generated method stub
			childChecked = true;
			if(chk_remove_all.isChecked()){
				if(!isChecked){
					chk_remove_all.setChecked(false);
				}
			}else{
				if(isChecked){
					for(int i=0; i<ticket_list.size();i++){
						View childView = lst_customer_ticket.getChildAt(i);
						CheckBox chk_remove = (CheckBox) childView.findViewById(R.id.chk_remove);
						if(chk_remove.isChecked()){
							chk_remove_all.setChecked(true);
						}else{
							chk_remove_all.setChecked(false);
							break;
						}
					}
				}
			}
		}
	};
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == actionBarBack){
				finish();
			}
			
			if(v == chk_remove_all){
				for(int i=0;i<ticket_list.size();i++){
					View childView = lst_customer_ticket.getChildAt(i);
					CheckBox chk_remove = (CheckBox) childView.findViewById(R.id.chk_remove);
					if(((CheckBox)v).isChecked()){
						chk_remove.setChecked(true);
					}else{
						chk_remove.setChecked(false);
					}
				}
			}
			
			if(v == btn_delete){
				SKConnectionDetector skDetector = SKConnectionDetector.getInstance(BusBookingDetailActivity.this);
				if(skDetector.isConnectingToInternet()){
					deleteOrder();
				}else{
					skDetector.showErrorMessage();
				}
			}
		}
	};
	private ProgressDialog dialog;
	
	private void deleteOrder(){
		dialog = ProgressDialog.show(this, "", " Please wait...", true);
        dialog.setCancelable(true);
		SharedPreferences pref = getSharedPreferences("User", Activity.MODE_PRIVATE);
		String accessToken = pref.getString("access_token", null);
		if(chk_remove_all.isChecked()){
			NetworkEngine.getInstance().deleteAllOrder(accessToken, creditOrder.getId().toString(), new Callback<JSONObject>() {

				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					SKToastMessage.showMessage(BusBookingDetailActivity.this, "Can't Delete Record.", SKToastMessage.ERROR);
				}

				public void success(JSONObject arg0, Response arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					SKToastMessage.showMessage(BusBookingDetailActivity.this, "Successfully deleted.", SKToastMessage.SUCCESS);
					finish();
				}
			});
		}else{
			String saleItem = "";
			for(int i=0;i<ticket_list.size();i++){
				View childView = lst_customer_ticket.getChildAt(i);
				CheckBox chk_remove = (CheckBox) childView.findViewById(R.id.chk_remove);
				if(chk_remove.isChecked()){
					saleItem += ticket_list.get(i).getId()+",";
				}
			}
			Log.i("","Hello Delete Str = "+ saleItem);
			NetworkEngine.getInstance().deleteOrderItem(accessToken, saleItem, new Callback<JSONObject>() {

				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					Log.e("","Hello: "+ arg0.getResponse().getStatus()+" & "+arg0.getResponse().getReason());
					dialog.dismiss();
					SKToastMessage.showMessage(BusBookingDetailActivity.this, "Can't Delete Record.", SKToastMessage.ERROR);
				}

				public void success(JSONObject arg0, Response arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					SKToastMessage.showMessage(BusBookingDetailActivity.this, "Successfully deleted.", SKToastMessage.SUCCESS);
					finish();
				}
			});
		}
	}
	
	
	private static void setListViewHeightBasedOnChildren(ListView listView) {
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
