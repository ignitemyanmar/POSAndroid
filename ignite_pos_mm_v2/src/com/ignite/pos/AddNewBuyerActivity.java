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

public class AddNewBuyerActivity extends SherlockActivity{
	
	private Button save;
	private DatabaseManager dbManager;
	private List<Object> buyers;
	private ScrollView scrollView;
	private EditText edt_buyer_name;
	private EditText editText_city;
	private EditText editText_phone;
	private EditText editText_address;
	private String buyer_name;
	private String buyer_city;
	private String buyer_address;
	private String buyer_phone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_buyer);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		edt_buyer_name = (EditText)findViewById(R.id.edt_buyer_name);
		editText_city = (EditText)findViewById(R.id.editText_city);
		editText_phone = (EditText)findViewById(R.id.editText_phone);
		editText_address = (EditText)findViewById(R.id.editText_address);
		
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
		
		//Check buyers for Duplicate
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController)dbManager;
		buyers = new ArrayList<Object>();
		buyers = buyer_control.select();
		
		Log.i("", "Buyer All: "+buyers.toString());		
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == save)
			{
				if (checkFields()) {
					buyer_name = edt_buyer_name.getText().toString();
					buyer_city = editText_city.getText().toString();
					buyer_address = editText_address.getText().toString();
					buyer_phone = editText_phone.getText().toString();
					
					//Check Duplicate 
					if (buyers.size() > 0) {
						
						boolean isExist = false;
						
						for (int i = 0; i < buyers.size(); i++) {
							
							Buyer buyer = (Buyer)buyers.get(i);
							
							if (buyer_name.toLowerCase().equals(buyer.getBuyerName().toLowerCase())) {
								
								isExist = true;
								
								SKToastMessage.showMessage(getApplicationContext(), buyer_name +" is already exist!", SKToastMessage.WARNING);
								
								break;
							}
						}

						if (!isExist) {
							saveData();	
						}
					}else {
						saveData();	
					}
				}
			}
		}
	};
	
	private void saveData()
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController)dbManager;
		buyers = new ArrayList<Object>();
		buyers.add(new Buyer(buyer_name, buyer_city, buyer_phone, buyer_address));
		buyer_control.save(buyers);
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Buyer saved!", SKToastMessage.SUCCESS);
		
		Log.i("", "Buyer list: "+buyer_control.select().toString());
	}
	
	private void clearText()
	{
		edt_buyer_name.getText().clear();
		editText_city.getText().clear();
		editText_address.getText().clear();
		editText_phone.getText().clear();
	}
	
	public boolean checkFields() {
		
		if (edt_buyer_name.getText().toString().length() == 0) {
			edt_buyer_name.setError("Enter Buyer Name");
			return false;
		}
		if (editText_city.getText().toString().length() == 0) {
			editText_city.setError("Enter City");
			return false;
		}
		if (editText_phone.getText().toString().length() == 0) {
			editText_phone.setError("Enter Phone No.");
			return false;
		}
		if (editText_address.getText().toString().length() == 0) {
			editText_address.setError("Enter Address");
			return false;
		}
		return true;
	}
}
