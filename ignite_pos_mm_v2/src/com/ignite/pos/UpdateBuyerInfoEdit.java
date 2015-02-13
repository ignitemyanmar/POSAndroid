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
import com.ignite.pos.model.Buyer;
import com.smk.skalertmessage.SKToastMessage;

public class UpdateBuyerInfoEdit extends SherlockActivity{

	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> buyer_list;
	private String BuyerID;
	private ScrollView scrollView;
	private EditText edt_buyer_name;
	private EditText editText_city;
	private EditText editText_phone;
	private EditText editText_addr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_buyer_info);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		edt_buyer_name = (EditText)findViewById(R.id.edt_buyer_name);
		editText_city = (EditText)findViewById(R.id.editText_city);
		editText_phone = (EditText)findViewById(R.id.editText_phone);
		editText_addr = (EditText)findViewById(R.id.editText_addr);
		
		Bundle bundle = getIntent().getExtras();
		BuyerID = bundle.getString("BuyerID");
		
		Log.i("", "Buyer ID: "+BuyerID);
		
		getFromDB(BuyerID);
		
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
				}//End Check Fields
				
			}
		}
		
	};
	
	/**
	 * Get Buyer List by ID
	 * @param BuyerId
	 */
	private void getFromDB(String BuyerId)
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController)dbManager;
		buyer_list = new ArrayList<Object>();
		buyer_list = buyer_control.select(BuyerID);
		
		Log.i("","Get Data from DB :" + BuyerID + buyer_list.toString());
		
		if (buyer_list != null && buyer_list.size() > 0) {
			Buyer buyer = (Buyer) buyer_list.get(0);
			edt_buyer_name.setText(buyer.getBuyerName());
			editText_city.setText(buyer.getBuyerCity());
			editText_phone.setText(buyer.getBuyerPhone());
			editText_addr.setText(buyer.getBuyerAddress());
		}
	}
	
	private void updateData()
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController)dbManager;
		buyer_list = new ArrayList<Object>();
		buyer_list.add(new Buyer(Integer.valueOf(BuyerID), edt_buyer_name.getText().toString(), editText_city.getText().toString()
				, editText_phone.getText().toString(), editText_addr.getText().toString()));
		
		buyer_control.update(buyer_list);
		
		Log.i("","After Update Buyer:" + buyer_control.select(BuyerID).toString());
	}
	
	public boolean checkFields() {
		
		if (edt_buyer_name.getText().toString().length() == 0) {
			edt_buyer_name.setError("Enter Buyer Name");
			return false;
		}
		if (editText_city.getText().toString().length() == 0) {
			editText_city.setError("Enter City");
		}
		if (editText_phone.getText().toString().length() == 0) {
			editText_phone.setError("Enter Phone No.");
		}
		if (editText_addr.getText().toString().length() == 0) {
			editText_addr.setError("Enter Address");
		}
		return true;
	}
}
