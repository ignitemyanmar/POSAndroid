
package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.ItemListUpdateAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;

public class UpdateItemListActivity extends SherlockActivity{

	private ListView lv_item_list;
	private DatabaseManager dbManager;
	private List<Object> item_list_obj;
	private List<Object> pvList;
	private View title_view;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_item_list);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Update Item");
		title.setText("ပစၥည္းအမည္ ျပင္ျခင္း");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}
	
	private void selectData()
	{
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController) dbManager ;
		item_list_obj = new ArrayList<Object>();
		item_list_obj = item_control.select();
		
		title_view = LayoutInflater.from(this).inflate(R.layout.update_item_list_title,null,false);
		lv_item_list = (ListView) findViewById(R.id.lv_item_list);
		
		if(lv_item_list.getHeaderViewsCount() == 0)
		{
			lv_item_list.addHeaderView(title_view);
		}
		lv_item_list.setAdapter(new ItemListUpdateAdapter(this, item_list_obj));
		
		if (item_list_obj.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(UpdateItemListActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Item Yet!");
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
