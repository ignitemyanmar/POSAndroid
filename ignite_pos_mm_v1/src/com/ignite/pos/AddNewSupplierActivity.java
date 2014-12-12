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
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Supplier;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class AddNewSupplierActivity extends SherlockActivity{
	
	private EditText company_name , supplier_name, city , address, phone;
	private Button save;
	private DatabaseManager dbManager;
	private List<Object> suppliers;
	private String str_co_name, strname, strcity, straddress, strphone;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_supplier);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		company_name = (EditText)findViewById(R.id.editText_company_name);
		supplier_name = (EditText) findViewById(R.id.editText_sup_name);
		city = (EditText)findViewById(R.id.editText_city);
		phone = (EditText)findViewById(R.id.editText_phone);
		address = (EditText)findViewById(R.id.editText_address);
		
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
		
		//Check Suppliers for Duplicate
		dbManager = new SupplierController(this);
		SupplierController supplier_control = (SupplierController)dbManager;
		suppliers = new ArrayList<Object>();
		suppliers = supplier_control.select();
		
		Log.i("", "Supplier All: "+suppliers.toString());		
	}

	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == save)
			{
				if (checkFields()) {
					str_co_name = company_name.getText().toString();
					strname = supplier_name.getText().toString();
					strcity = city.getText().toString();
					straddress = address.getText().toString();
					strphone = phone.getText().toString();
					
					//Check Duplicate 
					if (suppliers.size() > 0) {
						
						boolean isExist = false;
						
						for (int i = 0; i < suppliers.size(); i++) {
							
							Supplier supplier = (Supplier)suppliers.get(i);
							
							if (str_co_name.toLowerCase().equals(supplier.getSupCoName().toLowerCase())) {
								
								isExist = true;
								
								SKToastMessage.showMessage(getApplicationContext(), str_co_name +" is already exist!", SKToastMessage.WARNING);
								clearText();
								
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
		dbManager = new SupplierController(this);
		SupplierController supplier_control = (SupplierController)dbManager;
		suppliers = new ArrayList<Object>();
		suppliers.add(new Supplier(str_co_name, strname, strcity, strphone, straddress));
		supplier_control.save(suppliers);
		//Log.i("","Saving to Database :" + suppliers);
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Supplier saved!", SKToastMessage.SUCCESS);
		
		Log.i("", "supplier list: "+supplier_control.select().toString());
	}
	
	private void clearText()
	{
		company_name.getText().clear();
		supplier_name.getText().clear();
		city.getText().clear();
		address.getText().clear();
		phone.getText().clear();
	}
	
	public boolean checkFields() {
		
		if (company_name.getText().toString().length() == 0) {
			company_name.setError("Enter Supplier Company Name");
			return false;
		}
		
		return true;
	}
}
