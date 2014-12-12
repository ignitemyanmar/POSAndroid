package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.ItemListReportAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;

public class ItemListReport extends SherlockActivity{
	
	private ActionBar actionBar;
	private ListView lv_items_list_report;
	private DatabaseManager dbManager;
	private List<Object> listItems;
	//private TextView txt_grand_total;
	private TextView title;
	private Button change_mode;
	private RelativeLayout layout_add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		layout_add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		layout_add_new.setVisibility(View.GONE);
		title.setText("Stock Report");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);		
		
		lv_items_list_report = (ListView) findViewById(R.id.lv_items_list_report);
		//txt_grand_total = (TextView) findViewById(R.id.txt_total);
		
		getItem();
	}

	private void getItem() {
		// TODO Auto-generated method stub
		dbManager = new ItemListController(ItemListReport.this);
		ItemListController itemController = (ItemListController) dbManager;
		listItems = new ArrayList<Object>();
		listItems = itemController.selectRecordPurchasedItems();
		
		Log.i("", "Number of stock list: "+listItems.size());
		Log.i("", "stock list in DB: "+listItems.toString());
		
		ItemListReportAdapter adapter = new ItemListReportAdapter(ItemListReport.this, listItems);
		//adapter.setCallbacks(childCallback);
		lv_items_list_report.setAdapter(adapter);
		
		if (listItems.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(ItemListReport.this);
			alert.setTitle("Info");
			alert.setMessage("No Item Yet!");
			alert.show();
			alert.setCancelable(true);
			
		}
	}
	
	
}