package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.ItemListReportAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleVouncher;

public class ItemListReport extends SherlockActivity{
	
	private ListView lv_items_list_report;
	private DatabaseManager dbManager;
	private List<Object> listItems;
	//private TextView txt_grand_total;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list_report);
		
		lv_items_list_report = (ListView) findViewById(R.id.lv_items_list_report);
		//txt_grand_total = (TextView) findViewById(R.id.txt_total);
		
		getItem();
	}

	private void getItem() {
		// TODO Auto-generated method stub
		dbManager = new ItemListController(ItemListReport.this);
		ItemListController controller = (ItemListController) dbManager;
		listItems = new ArrayList<Object>();
		listItems = controller.select();
		
		Log.i("", "Number of List Items: "+listItems.size());
		Log.i("", "List Items in DB: "+listItems.toString());
		
		ItemListReportAdapter adapter = new ItemListReportAdapter(ItemListReport.this, listItems);
		//adapter.setCallbacks(childCallback);
		lv_items_list_report.setAdapter(adapter);
	}
	
}