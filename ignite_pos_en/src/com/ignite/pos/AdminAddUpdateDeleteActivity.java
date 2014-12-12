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
import com.ignite.pos.adapter.AdminLvAdapter;
import com.ignite.pos.adapter.SalePersonLvAdapter;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;

public class AdminAddUpdateDeleteActivity extends SherlockActivity{

	private ListView lv_admin;
	private DatabaseManager dbManager;
	private List<Object> admin_list;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_add_update_delete);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("Admin  |  Add New / Update / Delete");
		add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		add_new.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AdminAddUpdateDeleteActivity.this, AddNewAdminAccount.class));
			}
		});
	}
	
	private void selectData()
	{
		dbManager = new AdminController(this);
		AdminController admin_control = (AdminController) dbManager ;
		admin_list = new ArrayList<Object>();
		admin_list = admin_control.select();
		
		Log.i("","Admin List from DB :" + admin_list.toString());

		lv_admin = (ListView) findViewById(R.id.lv_admin);
		lv_admin.setAdapter(new AdminLvAdapter(this, admin_list));
		
		if (admin_list.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(AdminAddUpdateDeleteActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Admin Yet!");
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


