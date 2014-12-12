package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;

public class UpdatePrice extends SherlockActivity{
	
	private EditText scan , newprice;
	private TextView item_name, item_price;
	private Button btnsave;
	private DatabaseManager dbManager;
	private List<Object> itemList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_prices);
		
		scan = (EditText)findViewById(R.id.editText_scan);
		scan.addTextChangedListener(watcher);
		newprice = (EditText)findViewById(R.id.editText_new_price);
		
		item_name = (TextView)findViewById(R.id.txtItem_name);
		item_price = (TextView)findViewById(R.id.txtItem_price);
		
		btnsave = (Button)findViewById(R.id.btnSave);
		btnsave.setOnClickListener(clickListener);
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == btnsave)
				{
					if (checkFields()) {
						updateData();
					}
					
				}
			}
		}; 
		
	private TextWatcher watcher = new TextWatcher() {
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			getData(s);
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	private void updateData()
	{
		dbManager = new ItemListController(this);
		ItemListController item_list_control = (ItemListController)dbManager;
		itemList = new ArrayList<Object>();
		itemList.add(new ItemList(scan.getText().toString(), item_name.getText().toString(), newprice.getText().toString()));
		item_list_control.update(itemList);
		
		//Log.i("","Select Data from Database :" + itemList);
		
		clearText();
	}
	
	private void getData(CharSequence ItemID)
	{
		dbManager = new ItemListController(this);
		ItemListController item_list_control = (ItemListController)dbManager;
		itemList = new ArrayList<Object>();
		itemList = item_list_control.select(ItemID.toString());
	//	Log.i("","ID :" + ItemID + " and " + itemList);
		if(itemList != null && itemList.size() > 0)
		{
			ItemList item_list = (ItemList)itemList.get(0);
			item_name.setText(item_list.getItemName());
			item_price.setText(item_list.getItemPrice());
			//Log.i("","Get Data from DB :" + item_list);
		}
	}
	
	private void clearText()
	{
		scan.getText().clear();
		item_name.setText(null);
		item_price.setText(null);
		newprice.getText().clear();
		scan.requestFocus();
		
	}
	
	public boolean checkFields() {
		
		if (scan.getText().toString().length() == 0) {
			scan.setError("Tab here to scan");
			return false;
		}
		if (newprice.getText().toString().length() == 0) {
			newprice.setError("Enter New Price");
			return false;
		}
		return true;
	}
}
