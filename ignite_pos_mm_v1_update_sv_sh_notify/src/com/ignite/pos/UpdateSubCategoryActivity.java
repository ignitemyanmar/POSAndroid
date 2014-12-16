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
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.SubCategory;

public class UpdateSubCategoryActivity extends SherlockActivity{

	private EditText edit_Name;
	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> subcategory_list;
	private String SubCatID;
	private String SubCatName;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_subcategory);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		edit_Name = (EditText)findViewById(R.id.editText_name);
		cancel = (Button)findViewById(R.id.btnCancel);
		update = (Button)findViewById(R.id.btnSave);
		cancel.setOnClickListener(clickListener);
		update.setOnClickListener(clickListener);
		
		Bundle bundle = getIntent().getExtras();
		SubCatID = bundle.getString("SubCatID");
		SubCatName = bundle.getString("SubCatName");
		
		//getFromDB(SubCatID);
		edit_Name.setText(SubCatName);
		
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
	
	private void getFromDB(String SubCatID)
	{
		dbManager = new SubCategoryController(this);
		SubCategoryController subcategory_control = (SubCategoryController)dbManager;
		subcategory_list = new ArrayList<Object>();
		subcategory_list = subcategory_control.select(SubCatID);
		
		Log.i("","Get Data from DB :" + SubCatID + subcategory_list.toString());
		
		if (subcategory_list != null && subcategory_list.size() > 0) {
			Category category = (Category) subcategory_list.get(0);
			//ID = buyer.getId();
			edit_Name.setText(category.getCategoryName());
		}
	}
	
	private void updateData()
	{
		dbManager = new SubCategoryController(this);
		SubCategoryController subcategory_control = (SubCategoryController)dbManager;
		subcategory_list = new ArrayList<Object>();
		subcategory_list.add(new SubCategory(Integer.valueOf(SubCatID), edit_Name.getText().toString()));
		subcategory_control.update(subcategory_list);
		
		//Log.i("","Update to Database :" + subcategory_list);
		Log.i("", "After Update: "+subcategory_control.select().toString());
	}
	
public boolean checkFields() {
		
		if (edit_Name.getText().toString().length() == 0) {
			edit_Name.setError("Enter Sub-Category Name");
			return false;
		}
		return true;
	}
}
