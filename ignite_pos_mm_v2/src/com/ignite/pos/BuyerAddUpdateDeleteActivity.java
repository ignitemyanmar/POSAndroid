package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.BuyerInfoReportListViewAdapter;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.util.DatabaseManager;

public class BuyerAddUpdateDeleteActivity extends SherlockActivity{

	private ListView lv_buyer_list;
	private DatabaseManager dbManager;
	private List<Object> buyer_list;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buyer_info_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Supplier  |  Add New / Update / Delete");
		title.setText("၀ယ္သူ [အသစ္/ျပင္/ဖ်က္] ျခင္း");
		add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		add_new.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(BuyerAddUpdateDeleteActivity.this, AddNewBuyerActivity.class));
			}
		});
	}
	
	/**
	 * Get Buyer List from DB
	 */
	private void selectData()
	{
		dbManager = new BuyerController(this);
		BuyerController buyer_control = (BuyerController) dbManager ;
		buyer_list = new ArrayList<Object>();
		buyer_list = buyer_control.select();
		
		if (buyer_list != null && buyer_list.size() > 0) {
			
			lv_buyer_list = (ListView) findViewById(R.id.lv_buyer);
			lv_buyer_list.setAdapter(new BuyerInfoReportListViewAdapter(this, buyer_list));
		}
		
		
		if (buyer_list.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(BuyerAddUpdateDeleteActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Buyer Yet!");
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

