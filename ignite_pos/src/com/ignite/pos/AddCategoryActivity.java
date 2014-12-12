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
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class AddCategoryActivity extends SherlockActivity{

	private EditText categoryName;
	private Button save;
	private DatabaseManager dbManager;
	private List<Object> category_list;
	private int strID;
	private String strName;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_category_activity);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		categoryName = (EditText)findViewById(R.id.editText_category_name);
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
	
		dbManager = new CategoryController(this);
		CategoryController control = (CategoryController)dbManager;
		category_list = new ArrayList<Object>();
		category_list = control.select();
		Log.i("","Category List :" + category_list.toString());
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			CategoryAutoID autoId = new CategoryAutoID(AddCategoryActivity.this);
			strID = autoId.generateAutoID();
			
			Log.i("","Auto ID :" + strID);
			
			if (checkFields()) {
				strName  = categoryName.getText().toString();
				
				//Check Duplicate 
				if (category_list.size() > 0) {
					
					boolean isExist = false;
					
					for (int i = 0; i < category_list.size(); i++) {
						
						Category cat = (Category)category_list.get(i);
						
						if (strName.toLowerCase().equals(cat.getCategoryName().toLowerCase())) {
							
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
		dbManager = new CategoryController(this);
		CategoryController categoryControl = (CategoryController)dbManager;
		category_list = new ArrayList<Object>();
		category_list.add(new Category(strID,strName));
		categoryControl.save(category_list);
		
		Log.i("","Saving to Database :" + category_list.toString());
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Category saved!", SKToastMessage.SUCCESS);
		
		//finish();
	}

	private void clearText() {
		// TODO Auto-generated method stub
		categoryName.getText().clear();
		categoryName.requestFocus();
	}
	
	public boolean checkFields() {
		if (categoryName.getText().toString().length() == 0) {
			categoryName.setError("Enter Category Name");
			return false;
		}
		
		return true;
	}
}
