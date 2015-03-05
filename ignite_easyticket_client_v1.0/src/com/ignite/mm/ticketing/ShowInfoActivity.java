package com.ignite.mm.ticketing;

import java.util.Set;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ShowInfoActivity extends SherlockActivity {
	private TextView actionBarTitle,showTitle,price,totalCost;
	public static EditText noOfTickets;
	private ActionBar actionBar;
	private ImageButton actionBarBack;
	private String TicketTypeID;
	public static String ShowId,ShowName,TicketID,Price,totalTicket;
	public static int  totalValue;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_info);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarTitle.setText("SHOW");

		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle bundle=getIntent().getExtras();
		//TicketTypeID = bundle.getString("ticketTypeId");
		ShowId = bundle.getString("showid");
		ShowName = bundle.getString("showname");
		TicketID=bundle.getString("ticket_id");
		Price=bundle.getString("price");
		setData();
		
	}
	
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}
						
		}
	};
	
	private void setData()
	{
		showTitle=(TextView)findViewById(R.id.txtShowTitle);
		price=(TextView)findViewById(R.id.txtPrice);
		noOfTickets=(EditText)findViewById(R.id.editText1);
		totalCost=(TextView)findViewById(R.id.txtTotalCost);
		
		showTitle.setText(ShowName);
		price.setText(Price +" MMK");
		noOfTickets.addTextChangedListener(onSearchFieldTextChanged);
	}
	
	private TextWatcher onSearchFieldTextChanged = new TextWatcher()
	{
		public void onTextChanged(CharSequence s, int start, int before, int count){
			int value= s.toString().length();
			if (value==0){
				
				 totalValue = calculatePrice(Integer.valueOf(Price),0);
			}
			else{
				totalValue = calculatePrice(Integer.valueOf(Price), Integer.valueOf(s.toString()));
			
			}
			totalCost.setText(String.valueOf(totalValue + " MMK"));
		}

		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private int calculatePrice(int price, int num){
		return price * num;
	}
	
	 
}





