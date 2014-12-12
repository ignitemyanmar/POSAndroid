package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.CategorySpinnerAdapter;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.SubCategory;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

public class AddSubCategoryActivity extends SherlockActivity{

	private Spinner categoryName;
	private EditText subCategoryName;
	private String  str_subcat_name;
	private int selectedCategoryID , subCategoryID;
	private Button save;
	private Category category;
	public List<Object> categoryList;
	private DatabaseManager dbManager;
	private List<Object> subcat_list;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adding_subcategory_activity);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		categoryName = (Spinner)findViewById(R.id.spn_category_name);
		subCategoryName = (EditText)findViewById(R.id.editText_subcat_name);
		save = (Button)findViewById(R.id.btnSave);
		
		getCategory();
		
		save.setOnClickListener(clickListener);
		categoryName.setOnItemSelectedListener(categoryClickListener);
		
		dbManager = new SubCategoryController(this);
		SubCategoryController control = (SubCategoryController)dbManager;
		subcat_list = new ArrayList<Object>();
		subcat_list = control.select();
		Log.i("","SubCategory List :" + subcat_list.toString());
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == save)
			{
				SubCategoryAutoID autoId = new SubCategoryAutoID(AddSubCategoryActivity.this);
				subCategoryID = autoId.generateAutoID();
				
				Log.i("","Last SubCategory ID :" + subCategoryID);
				
				if (checkFields()) {
					str_subcat_name = subCategoryName.getText().toString();
					
					//Check Duplicate 
					if (subcat_list.size() > 0) {
						
						boolean isExist = false;
						
						for (int i = 0; i < subcat_list.size(); i++) {
							
							SubCategory sub_cat = (SubCategory)subcat_list.get(i);
							
							if (str_subcat_name.toLowerCase().equals(sub_cat.getSubCategoryName().toLowerCase())) {
								
								isExist = true;
								
								SKToastMessage.showMessage(getApplicationContext(), str_subcat_name +" is already exist!", SKToastMessage.WARNING);
								clearText();
								
								break;
							}
						}

						if (!isExist) {
							saveSubCategory();
						}
					}else {
						saveSubCategory();
					}
				}
				
			}
		}
	};
	
	private OnItemSelectedListener categoryClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//selectedCategoryID = 0 ;
			category = (Category) categoryList.get(arg2); 
			selectedCategoryID = category.getCategoryID();
			
			Log.i("", "selected category id for sub_cat: "+selectedCategoryID);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getCategory()
	{
		dbManager = new CategoryController(this);
		CategoryController control = (CategoryController)dbManager;
		categoryList = new ArrayList<Object>();
		categoryList = control.select();
		categoryName.setAdapter(new CategorySpinnerAdapter(this,categoryList));
		
		Log.i("","Category List :" + categoryList.toString());
	}
	
	private void saveSubCategory()
	{
		dbManager = new SubCategoryController(this);
		SubCategoryController subcat_control = (SubCategoryController)dbManager;
		subcat_list = new ArrayList<Object>();
		subcat_list.add(new SubCategory(subCategoryID,str_subcat_name,selectedCategoryID));
		subcat_control.save(subcat_list);
		
		Log.i("","Saving to Database :" + subcat_list.toString());
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Sub-Category saved!", SKToastMessage.SUCCESS);
		
		//finish();
	}
	
	private void clearText() {
		// TODO Auto-generated method stub
		subCategoryName.getText().clear();
		subCategoryName.requestFocus();
	}
	
	public boolean checkFields() {
		
		if (categoryName.getCount() == 0) {
			SKToastMessage.showMessage(getApplicationContext(), "Choose Category", SKToastMessage.WARNING);
			return false;
		}
		if (subCategoryName.getText().toString().length() == 0) {
			subCategoryName.setError("Enter Sub-Category Name");
			return false;
		}
		
		return true;
	}
}
