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
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;

public class UpdateItemActivity extends SherlockActivity{

	private EditText edit_Name;
	private Button cancel,update;
	private DatabaseManager dbManager;
	private List<Object> item_list;
	private List<Object> item_obj;
	private String ItemID;
	private String ItemName;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_item);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		edit_Name = (EditText)findViewById(R.id.editText_name);
		cancel = (Button)findViewById(R.id.btnCancel);
		update = (Button)findViewById(R.id.btnSave);
		cancel.setOnClickListener(clickListener);
		update.setOnClickListener(clickListener);
		
		Bundle bundle = getIntent().getExtras();
		ItemID = bundle.getString("ItemID");
		ItemName = bundle.getString("ItemName");
		
		//getFromDB(CatID);
		
		edit_Name.setText(ItemName);
		
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
	
	private void updateData()
	{
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController)dbManager;
		
		//Select Item Obj
		item_obj = new ArrayList<Object>();
		item_obj = item_control.select(ItemID);
		ItemList itemL = (ItemList)item_obj.get(0);
		
		item_list = new ArrayList<Object>();
		item_list.add(new ItemList(ItemID, edit_Name.getText().toString(), itemL.getPurchasePrice(), itemL.getSalePrice()
				, itemL.getMarginalPrice(), itemL.getQty(), 0));
		item_control.update(item_list);
		
		Log.i("", "After Update: "+item_control.select().toString());
	}
	
	public boolean checkFields() {
		
		if (edit_Name.getText().toString().length() == 0) {
			edit_Name.setError("Enter Category Name");
			return false;
		}
		return true;
	}
}

