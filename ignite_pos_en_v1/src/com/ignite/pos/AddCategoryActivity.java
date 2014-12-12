package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.smk.skalertmessage.SKToastMessage;

public class AddCategoryActivity extends SherlockActivity{

	private EditText categoryName;
	private Button save;
	private DatabaseManager dbManager;
	private List<Object> category_list;
	private int strID;
	private String strName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_category_activity);
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
				saveData();
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
