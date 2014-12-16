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
import com.ignite.pos.database.controller.BrandController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Brand;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class AddNewBrandActivity extends SherlockActivity{

	private EditText brandName;
	private Button save;
	private DatabaseManager dbManager;
	private List<Object> brand_list;
	private int strID;
	private String strName;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_brand);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		brandName = (EditText)findViewById(R.id.editText_brand_name);
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
	
		dbManager = new BrandController(this);
		BrandController control = (BrandController)dbManager;
		brand_list = new ArrayList<Object>();
		brand_list = control.select();
		
		Log.i("","Brand List :" + brand_list.toString());
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			BrandAutoID autoId = new BrandAutoID(AddNewBrandActivity.this);
			strID = autoId.generateAutoID();
			
			Log.i("","Auto ID :" + strID);
			
			if (checkFields()) {
				strName  = brandName.getText().toString();
				
				//Check Duplicate 
				if (brand_list.size() > 0) {
					
					boolean isExist = false;
					
					for (int i = 0; i < brand_list.size(); i++) {
						
						Brand brand = (Brand)brand_list.get(i);
						
						if (strName.toLowerCase().equals(brand.getBrandName().toLowerCase())) {
							
							isExist = true;
							
							SKToastMessage.showMessage(getApplicationContext(), strName +" is already exist!", SKToastMessage.WARNING);
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
	};
	
	private void saveData()
	{
		dbManager = new BrandController(this);
		BrandController brandControl = (BrandController)dbManager;
		brand_list = new ArrayList<Object>();
		brand_list.add(new Brand(strID, strName));
		brandControl.save(brand_list);
		
		Log.i("","Saving to Database :" + brand_list.toString());
		
		Log.i("","Brand List :" + brandControl.select().toString());
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Brand saved!", SKToastMessage.SUCCESS);
	}

	private void clearText() {
		// TODO Auto-generated method stub
		brandName.getText().clear();
		brandName.requestFocus();
	}
	
	public boolean checkFields() {
		
		if (brandName.getText().toString().length() == 0) {
			brandName.setError("Enter Brand Name");
			return false;
		}
		return true;
		
	}
}
