package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.SupplierInfoReportListViewAdapter;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;

public class SupplierAddUpdateDeleteActivity extends SherlockActivity{

	private ListView lv_sup_list;
	private DatabaseManager dbManager;
	private List<Object> supplier_list;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_info_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Supplier  |  Add New / Update / Delete");
		title.setText("လကၠားဆုိင္ [အသစ္/ျပင္/ဖ်က္] ျခင္း");
		add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		add_new.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SupplierAddUpdateDeleteActivity.this, AddNewSupplierActivity.class));
			}
		});
	}
	
	private void selectData()
	{
		dbManager = new SupplierController(this);
		SupplierController supplier_control = (SupplierController) dbManager ;
		supplier_list = new ArrayList<Object>();
		supplier_list = supplier_control.select();
		
		Log.i("","Supplier List from DB :" + supplier_list.toString());
		
		lv_sup_list = (ListView) findViewById(R.id.lvSupplierList);
		lv_sup_list.setAdapter(new SupplierInfoReportListViewAdapter(this, supplier_list));
		
		if (supplier_list.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(SupplierAddUpdateDeleteActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Supplier Yet!");
			alert.show();
			alert.setCancelable(true);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		selectData();
	}
	
	
}

