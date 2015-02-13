package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.SupplierInfoReportListViewAdapter;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;

public class SupplierInfoReportActivity extends SherlockActivity{

	private ListView lv_sup_list;
	private DatabaseManager dbManager;
	private List<Object> supplier_list;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_info_report);
		
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
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		selectData();
	}
	
	
}
