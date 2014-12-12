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
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Supplier;

public class UpdateSupplierInfoEdit extends SherlockActivity{

	private EditText editText_co_name,editText_supplier_name,editText_city,editText_phone,editText_addr;
	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> sup_list;
	private String SupId;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_supplier_info);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		editText_co_name = (EditText)findViewById(R.id.editText_co_name);
		editText_supplier_name = (EditText)findViewById(R.id.editText_supplier_name);
		editText_city = (EditText)findViewById(R.id.editText_city);
		editText_phone = (EditText)findViewById(R.id.editText_phone);
		editText_addr = (EditText)findViewById(R.id.editText_addr);
		
		Bundle bundle = getIntent().getExtras();
		SupId = bundle.getString("SupId");
		
		Log.i("", "Supplier ID: "+SupId);
		
		getFromDB(SupId);
		
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
	
	private void getFromDB(String SupId)
	{
		dbManager = new SupplierController(this);
		SupplierController supplier_control = (SupplierController)dbManager;
		sup_list = new ArrayList<Object>();
		sup_list = supplier_control.select(SupId);
		
		Log.i("","Get Data from DB :" + SupId + sup_list.toString());
		
		if (sup_list != null && sup_list.size() > 0) {
			Supplier supplier = (Supplier) sup_list.get(0);
			//ID = Supplier.getId();
			editText_co_name.setText(supplier.getSupCoName());
			editText_supplier_name.setText(supplier.getSupName());
			editText_city.setText(supplier.getSupCity());
			editText_phone.setText(supplier.getSupPh());
			editText_addr.setText(supplier.getSupAddr());
		}
	}
	
	private void updateData()
	{
		dbManager = new SupplierController(this);
		SupplierController supplier_control = (SupplierController)dbManager;
		sup_list = new ArrayList<Object>();
		sup_list.add(new Supplier(Integer.valueOf(SupId), editText_co_name.getText().toString(), editText_supplier_name.getText().toString(), editText_city.getText().toString()
				, editText_phone.getText().toString() ,editText_addr.getText().toString()));
		
		Log.i("", "Supplier List: "+sup_list.toString());
		
		supplier_control.update(sup_list);
		
		Log.i("","After Update :" + supplier_control.select().toString());
	}
	
	public boolean checkFields() {
		
		if (editText_co_name.getText().toString().length() == 0) {
			editText_co_name.setError("Enter Supplier Co. Name");
			return false;
		}
		
		return true;
	}
}
