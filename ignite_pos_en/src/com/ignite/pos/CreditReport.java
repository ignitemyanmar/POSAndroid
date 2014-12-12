package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.BuyerSpinnerAdapter;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;

public class CreditReport extends BaseSherlockActivity{

	private Button  search;
	private Spinner spBuyer;
	private Buyer buyer;
	private String buyerName;
	private List<Object>buyer_obj;
	private DatabaseManager dbManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credit_report_activity);
		
		spBuyer = (Spinner)findViewById(R.id.btnSelectBuyer);
		search = (Button)findViewById(R.id.btnSearch);
		
		spBuyer.setOnItemSelectedListener(buyerClickListener);
		search.setOnClickListener(clickListener);
		
		getBuyer();
		
		showAdminDialog();
	}
	
	private OnItemSelectedListener buyerClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			buyer = (Buyer)buyer_obj.get(position);
			buyerName = buyer.getCusName();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getBuyer()
	{
		dbManager = new BuyerController(this);
		BuyerController control = (BuyerController)dbManager;
		buyer_obj = new ArrayList<Object>();
		buyer_obj = control.select();
		spBuyer.setAdapter(new BuyerSpinnerAdapter(this,buyer_obj));
		Log.i("","Buyer List:" + buyer_obj.toString());
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if(v == search)
			{
				
			}
		}
	};
}
