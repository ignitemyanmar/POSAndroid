
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
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Category;

public class UpdateCategoryActivity extends SherlockActivity{

	private EditText edit_Name;
	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> category_list;
	private String CatID;
	private String CatName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_category);
		
		edit_Name = (EditText)findViewById(R.id.editText_name);
		cancel = (Button)findViewById(R.id.btnCancel);
		update = (Button)findViewById(R.id.btnSave);
		cancel.setOnClickListener(clickListener);
		update.setOnClickListener(clickListener);
		
		Bundle bundle = getIntent().getExtras();
		CatID = bundle.getString("CatID");
		CatName = bundle.getString("CatName");
		
		//getFromDB(CatID);
		
		edit_Name.setText(CatName);
		
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
	
	private void getFromDB(String CatID)
	{
		dbManager = new CategoryController(this);
		CategoryController category_control = (CategoryController)dbManager;
		category_list = new ArrayList<Object>();
		category_list = category_control.select(CatID);
		
		Log.i("","Get Data from DB :" + CatID + category_list.toString());
		
		if (category_list != null && category_list.size() > 0) {
			Category category = (Category) category_list.get(0);
			//ID = buyer.getId();
			edit_Name.setText(category.getCategoryName());
		}
	}
	
	private void updateData()
	{
		dbManager = new CategoryController(this);
		CategoryController category_control = (CategoryController)dbManager;
		category_list = new ArrayList<Object>();
		category_list.add(new Category(Integer.valueOf(CatID), edit_Name.getText().toString()));
		category_control.update(category_list);
		
		//Log.i("","Update to Database :" + category_list);
		Log.i("", "After Update: "+category_control.select().toString());
	}
	
public boolean checkFields() {
		
		if (edit_Name.getText().toString().length() == 0) {
			edit_Name.setError("Enter Category Name");
			return false;
		}
		return true;
	}
}
