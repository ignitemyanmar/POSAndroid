package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.CustomerListAdapter;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.util.DatabaseManager;

public class UpdateCustomerInfoListActivity extends SherlockActivity{

	private ListView cus_list;
	private DatabaseManager dbManager;
	private List<Object> buyer_list_obj;
	private View title_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_cus_list);
		
	}
	
	private void selectData()
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController) dbManager ;
		buyer_list_obj = new ArrayList<Object>();
		buyer_list_obj = buyer_control.select();
		
		Log.i("","Select Customer List from DB :" + buyer_list_obj.toString());
		
		title_view = LayoutInflater.from(this).inflate(R.layout.update_cus_list_title,null,false);
		cus_list = (ListView) findViewById(R.id.lvCustomerList);
		
		if(cus_list.getHeaderViewsCount() == 0)
		{
			cus_list.addHeaderView(title_view);
		}
		cus_list.setAdapter(new CustomerListAdapter(this, buyer_list_obj));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		selectData();
	}
	
	
}
