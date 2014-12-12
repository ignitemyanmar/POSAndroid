package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;

public class UpdateCustomerInfoEdit extends SherlockActivity{

	private EditText etName,etCity,etPhone,etAddress;
	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> cus_list;
	private String CusName;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_cus_info);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		etName = (EditText)findViewById(R.id.editText_name);
		etCity = (EditText)findViewById(R.id.editText_item_city);
		etPhone = (EditText)findViewById(R.id.editText_item_phone);
		etAddress = (EditText)findViewById(R.id.editText_item_address);
		
		Bundle bundle = getIntent().getExtras();
		CusName = bundle.getString("CusName");
		
		getFromDB(CusName);
		
		cancel = (Button)findViewById(R.id.btnCancel);
		update = (Button)findViewById(R.id.btnSave);
		cancel.setOnClickListener(clickListener);
		update.setOnClickListener(clickListener);
		
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == cancel)
			{
				finish();
			}
			if(v == update)
			{
				if (checkFields()) {
					updateData();
					finish();
				}
				
			}
		}
	};
	
	private void getFromDB(String CusName)
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController)dbManager;
		cus_list = new ArrayList<Object>();
		cus_list = buyer_control.select(CusName);
		
		Log.i("","Get Data from DB :" + CusName + cus_list.toString());
		
		if (cus_list != null && cus_list.size() > 0) {
			Buyer buyer = (Buyer) cus_list.get(0);
			//ID = buyer.getId();
			etName.setText(buyer.getCusName());
			etCity.setText(buyer.getCusCity());
			etPhone.setText(buyer.getCusPh());
			etAddress.setText(buyer.getCusAddress());
		}
	}
	
	private void updateData()
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController)dbManager;
		cus_list = new ArrayList<Object>();
		cus_list.add(new Buyer(CusName, etName.getText().toString(), etCity.getText().toString()
				, etPhone.getText().toString() ,etAddress.getText().toString()));
		buyer_control.update(cus_list);
		
		Log.i("","After Update :" + buyer_control.select().toString());
	}
	
	public boolean checkFields() {
		
		if (etName.getText().toString().length() == 0) {
			etName.setError("Enter Customer Name");
			return false;
		}
		
		return true;
	}
}
