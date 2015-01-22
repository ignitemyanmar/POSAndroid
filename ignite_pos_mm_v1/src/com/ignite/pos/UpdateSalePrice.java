package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.LedgerReportListViewAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.smk.skalertmessage.SKToastMessage;

public class UpdateSalePrice extends SherlockActivity{
	
	private EditText newprice;
	private AutoCompleteTextView auto_item_code;
	private TextView item_name, item_price;
	private Button btnsave, btn_search;
	private DatabaseManager dbManager;
	private List<Object> itemList;
	private String itemID;
	private boolean isSame;
	private ScrollView scrollView;
	private List<Object> listItemCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_prices);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		auto_item_code = (AutoCompleteTextView)findViewById(R.id.auto_item_code);
		auto_item_code.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ZawgyiOne2008.ttf"));
		//auto_item_code.addTextChangedListener(watcher);
		
		btn_search = (Button)findViewById(R.id.btn_search);
		btn_search.setOnClickListener(clickListener);
		
		newprice = (EditText)findViewById(R.id.editText_new_price);
		
		item_name = (TextView)findViewById(R.id.txtItem_name);
		item_price = (TextView)findViewById(R.id.txtItem_price);
		
		btnsave = (Button)findViewById(R.id.btnSave);
		btnsave.setOnClickListener(clickListener);
		
		//Item Code AutoComplete
		getItemCode();
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == btnsave)
				{
					if (checkFields()) {
						
/*						if (!isSame) {
							SKToastMessage.showMessage(UpdateSalePrice.this, "Wrong Item ID!", SKToastMessage.ERROR);
							item_name.setText(null);
							item_price.setText(null);
							newprice.getText().clear();
							auto_item_code.requestFocus();
						}else {
							updateData();
						}*/
						
						updateData();
					}
					
				}
				if(v == btn_search)
				{
					if (auto_item_code.getText().toString().length() == 0) {
						auto_item_code.setError("Enter item name");
					}else {
						itemID = auto_item_code.getText().toString();
						getData(itemID);
					}
				}
			}
		}; 
		
	private TextWatcher watcher = new TextWatcher() {
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			//getData(s);
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
	//	itemList.add(new ItemList(auto_item_code.getText().toString(), item_name.getText().toString(), newprice.getText().toString()));
		itemList.add(new ItemList("0", auto_item_code.getText().toString(), "0", newprice.getText().toString(), "0", "0", 0));
		Object itemL = (Object)itemList.get(0);
		//item_list_control.update(itemL);
		item_list_control.updateSalePricebyItemName(itemL);
		
		//Log.i("","After update New Sale Price :" +item_list_control.select(auto_item_code.getText().toString()));
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Price Updated !", SKToastMessage.SUCCESS);
	}
	
	private void getData(String ItemID)
	{
		//Check Item ID
		dbManager = new ItemListController(this);
		ItemListController item_list_control = (ItemListController)dbManager;
		itemList = new ArrayList<Object>();
		itemList = item_list_control.selectByItemName(ItemID);
		
		Log.i("", "Itemlist by item Name: "+itemList.toString());
		
		if(itemList != null && itemList.size() > 0)
		{
			ItemList item_list = (ItemList)itemList.get(0);
			item_name.setText(item_list.getItemId());
			item_price.setText(item_list.getSalePrice());
		}
		
/*		if (itemList.size() > 0) {
			
			isSame = false;
			
			for (int i = 0; i < itemList.size(); i++) {
				
				ItemList itemL = (ItemList)itemList.get(i);
				
				if (ItemID.toString().toLowerCase().equals(itemL.getItemId().toLowerCase())) {
					
					isSame = true;
					
					itemList = new ArrayList<Object>();
					//itemList = item_list_control.select(ItemID.toString());
					itemList = item_list_control.selectByItemName(itemName);
					
					if(itemList != null && itemList.size() > 0)
					{
						ItemList item_list = (ItemList)itemList.get(0);
						item_name.setText(item_list.getItemName());
						item_price.setText(item_list.getSalePrice());
					}
					
					break;
				}
			}
			
			if (!isSame) {
				SKToastMessage.showMessage(UpdateSalePrice.this, "Wrong Item ID!", SKToastMessage.ERROR);
				item_name.setText(null);
				item_price.setText(null);
				auto_item_code.requestFocus();
			}
		}*/
		
		
	}
	
	private void getItemCode()
	{
		dbManager = new ItemListController(this);
		ItemListController itemControl = (ItemListController)dbManager;
		listItemCode = new ArrayList<Object>();
		
		//listItemCode.add(new ItemList("All"));
		listItemCode.addAll(itemControl.select());
		
		Log.i("", "Item Code List: "+listItemCode.toString());
		
		//Change List<Object> to String Array
		String[] itemArray = new String[listItemCode.size()];
		
		for (int i = 0; i < listItemCode.size(); i++) {
			
			ItemList itemObj = (ItemList)listItemCode.get(i);
			
			itemArray[i] = itemObj.getItemName();  
			
			Log.i("", "Item Name Array: "+itemArray[i]);
		}

		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, R.layout.custom_autocomplete_view, itemArray);
		auto_item_code.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		auto_item_code.setThreshold(1);
		
	}
	
	private void clearText()
	{
		auto_item_code.getText().clear();
		item_name.setText(null);
		item_price.setText(null);
		newprice.getText().clear();
		auto_item_code.requestFocus();
		
	}
	
	public boolean checkFields() {
		
		if (auto_item_code.getText().toString().length() == 0) {
			auto_item_code.setError("Tap here to scan [or] Enter Item Code");
			return false;
		}
		if (newprice.getText().toString().length() == 0) {
			newprice.setError("Enter New Price");
			return false;
		}
		return true;
	}
}
