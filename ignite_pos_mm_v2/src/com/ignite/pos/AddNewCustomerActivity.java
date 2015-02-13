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
import com.smk.skalertmessage.SKToastMessage;

public class AddNewCustomerActivity extends SherlockActivity{
	
	private EditText name , city , address, phone;
	private Button save;
	private DatabaseManager dbManager;
	private List<Object> buyers;
	private String strname, strcity, straddress, strphone;
	private ScrollView scrollView;
	//private int id = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_customer_info);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		name = (EditText)findViewById(R.id.editText_cus_name);
		city = (EditText)findViewById(R.id.editText_city);
		phone = (EditText)findViewById(R.id.editText_phone);
		address = (EditText)findViewById(R.id.editText_address);
		
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == save)
			{
				if (checkFields()) {
					strname = name.getText().toString();
					strcity = city.getText().toString();
					straddress = address.getText().toString();
					strphone = phone.getText().toString();
					saveData();
				}
			}
		}
	};
	
	private void saveData()
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController)dbManager;
		buyers = new ArrayList<Object>();
		buyers.add(new Buyer(strname, strcity, strphone, straddress));
		buyer_control.save(buyers);
		Log.i("","Saving to Database :" + buyers);
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Customer saved!", SKToastMessage.SUCCESS);
	}
	
	private void clearText()
	{
		name.getText().clear();
		city.getText().clear();
		address.getText().clear();
		phone.getText().clear();
		name.requestFocus();
		
	}
	
	public boolean checkFields() {
		
		if (name.getText().toString().length() == 0) {
			name.setError("Enter Customer Name");
			return false;
		}
		
		return true;
	}
}
