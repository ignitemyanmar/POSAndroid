
package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

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
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.smk.skalertmessage.SKToastMessage;

public class RegisterNotificationActivity extends SherlockActivity{
	
	private EditText editText_notify_qty;
	private AutoCompleteTextView auto_item_code;
	private TextView txt_item_name;
	private Button btnSave, btn_search;
	private DatabaseManager dbManager;
	private List<Object> itemList;
	private List<Object> listItemCode;
	private String itemID;
	private boolean isSame;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_notification);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		auto_item_code = (AutoCompleteTextView)findViewById(R.id.auto_item_code);
		editText_notify_qty = (EditText)findViewById(R.id.editText_notify_qty);
		btn_search = (Button)findViewById(R.id.btn_search);
		txt_item_name = (TextView)findViewById(R.id.txt_item_name);
		btnSave = (Button)findViewById(R.id.btnSave);
		
		btn_search.setOnClickListener(clickListener);
		btnSave.setOnClickListener(clickListener);
		
		//Item Code AutoComplete
		getItemCode();
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == btnSave)
				{
					if (checkFields()) {
						
						if (!isSame) {
							SKToastMessage.showMessage(RegisterNotificationActivity.this, "Wrong Item ID!", SKToastMessage.ERROR);
							txt_item_name.setText(null);
							editText_notify_qty.getText().clear();
							auto_item_code.requestFocus();
						}else {
							updateData();
						}
					}
					
				}
				if(v == btn_search)
				{
					if (auto_item_code.getText().toString().length() == 0) {
						auto_item_code.setError("Tap here to scan [or] Enter Item Code");
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
		itemList = item_list_control.select(itemID);
		
		Integer notifyStatus = 0;
		
		if (itemList != null && itemList.size() > 0) {
			ItemList itemObj = (ItemList)itemList.get(0);
			
			Integer stockQty = Integer.valueOf(itemObj.getQty());
			Integer notifyQty = Integer.valueOf(editText_notify_qty.getText().toString());
			
			if (stockQty <= notifyQty) {
				notifyStatus = 1;
			}else {
				notifyStatus = 0;
			}
			
		}
		
		itemList.add(new ItemList(auto_item_code.getText().toString(), txt_item_name.getText().toString()
				, "", "", "", "", "", "", "", 0, Integer.valueOf(editText_notify_qty.getText().toString())
				, notifyStatus, 1));
		
		item_list_control.updateNotifyQty(itemList);
		
		Log.i("","After update Notify Qty + Notify Status + Register Notify :" +item_list_control.select(itemID).toString());
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "Register Successful!", SKToastMessage.SUCCESS);
	}
	
	private void getData(String ItemID)
	{
		//Check Item ID
		dbManager = new ItemListController(this);
		ItemListController item_list_control = (ItemListController)dbManager;
		itemList = new ArrayList<Object>();
		itemList = item_list_control.select();
		
		if (itemList.size() > 0) {
			
			isSame = false;
			
			for (int i = 0; i < itemList.size(); i++) {
				
				ItemList itemL = (ItemList)itemList.get(i);
				
				if (ItemID.toString().toLowerCase().equals(itemL.getItemId().toLowerCase())) {
					
					isSame = true;
					
					itemList = new ArrayList<Object>();
					itemList = item_list_control.select(ItemID.toString());
					
					if(itemList != null && itemList.size() > 0)
					{
						ItemList item_list = (ItemList)itemList.get(0);
						txt_item_name.setText(item_list.getItemName());
					}
					
					break;
				}
			}
			
			if (!isSame) {
				SKToastMessage.showMessage(RegisterNotificationActivity.this, "Wrong Item ID!", SKToastMessage.ERROR);
				txt_item_name.setText(null);
				auto_item_code.requestFocus();
			}
		}
		
	}
	
	private void getItemCode()
	{
		dbManager = new ItemListController(this);
		ItemListController itemControl = (ItemListController)dbManager;
		listItemCode = new ArrayList<Object>();
		
		//listItemCode.add(new ItemList("All"));
		listItemCode = itemControl.selectRecordPurchasedItems();
		
		Log.i("", "Item Code List: "+listItemCode.toString());
		
		//Change List<Object> to String Array
		String[] itemArray = new String[listItemCode.size()];
		
		for (int i = 0; i < listItemCode.size(); i++) {
			
			ItemList itemObj = (ItemList)listItemCode.get(i);
			
			itemArray[i] = itemObj.getItemId();  
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemArray);
		auto_item_code.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		auto_item_code.setThreshold(1);
		
	}
	
	private void clearText()
	{
		auto_item_code.getText().clear();
		txt_item_name.setText(null);
		editText_notify_qty.getText().clear();
		auto_item_code.requestFocus();
		
	}
	
	public boolean checkFields() {
		
		if (auto_item_code.getText().toString().length() == 0) {
			auto_item_code.setError("Tap here to scan [or] Enter Item Code");
			return false;
		}
		if (editText_notify_qty.getText().toString().length() == 0) {
			editText_notify_qty.setError("Enter Notify Stock Qty");
			return false;
		}
		return true;
	}
}

