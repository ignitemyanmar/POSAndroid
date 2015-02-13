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
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;

public class UpdateNotifyEditActivity extends SherlockActivity{

	private EditText editTxt_notify_qty;
	private TextView txt_item_code, txt_item_name;
	private Button btnCancel, btnSave;
	private DatabaseManager dbManager;
	private List<Object> notify_list;
	private List<Object> notifyL;
	private String itemID, itemName;
	private Integer notifyQty;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_notify_edit);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setScrollbarFadingEnabled(false);
		
		editTxt_notify_qty = (EditText)findViewById(R.id.editTxt_notify_qty);
		txt_item_code = (TextView)findViewById(R.id.txt_item_code);
		txt_item_name = (TextView)findViewById(R.id.txt_item_name);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnSave = (Button)findViewById(R.id.btnSave);
		
		Bundle bundle = getIntent().getExtras();
		itemID = bundle.getString("itemCode");
		itemName = bundle.getString("itemName");
		notifyQty = bundle.getInt("notifyQty");
		
		Log.i("", "Item ID: "+itemID);
		
		getData();
		
		btnCancel.setOnClickListener(clickListener);
		btnSave.setOnClickListener(clickListener);
		
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btnCancel)
			{
				finish();
			}
			if(v == btnSave)
			{
				if (checkFields()) {
					updateData();
					finish();
				}
				
			}
		}
	};
	
	private void getData()
	{
		txt_item_code.setText(itemID);
		txt_item_name.setText(itemName);
		editTxt_notify_qty.setText(notifyQty+"");
	}
	
	private void updateData()
	{
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController)dbManager;
		notify_list = new ArrayList<Object>();
		
		notifyL = new ArrayList<Object>();
		notifyL = item_control.select(txt_item_code.getText().toString());
		ItemList itemL = (ItemList)notifyL.get(0);
		
		Integer notifyStatus;
		
		if (Integer.valueOf(itemL.getQty()) <= Integer.valueOf(editTxt_notify_qty.getText().toString()) ) {
			notifyStatus = 1;
		}else {
			notifyStatus = 0;
		}
		
		notify_list.add(new ItemList(txt_item_code.getText().toString()
				, txt_item_name.getText().toString()
				, "", "", "", "", "", "", "", 0, Integer.valueOf(editTxt_notify_qty.getText().toString()), notifyStatus, 0));
		
		item_control.updateNotify(notify_list);
		
		Log.i("","After Update Notify Qty :" + item_control.select(itemID).toString());
	}
	
	public boolean checkFields() {
		
		if (editTxt_notify_qty.getText().toString().length() == 0) {
			editTxt_notify_qty.setError("Enter Notify Qty");
			return false;
		}
		
		return true;
	}
}

