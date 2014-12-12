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
import com.ignite.pos.adapter.UpdateNotificationLvAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;

public class NotificationAddUpdateDeleteActivity extends SherlockActivity{

	private ListView lv_notify;
	private DatabaseManager dbManager;
	private List<Object> register_notify;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_notification);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Notification  |  Add New / Update / Delete");
		title.setText("လက္က်န္ သတိေပး [အသစ္/ျပင္/ဖ်က္] ျခင္း");
		add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		add_new.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(NotificationAddUpdateDeleteActivity.this, RegisterNotificationActivity.class));
			}
		});
	}
	
	private void selectData()
	{
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController) dbManager ;
		register_notify = new ArrayList<Object>();
		register_notify = item_control.selectByRegisterNotify();
		
		Log.i("","Register Notify List : " + register_notify.toString());
		
		lv_notify = (ListView) findViewById(R.id.lv_notify);
		lv_notify.setAdapter(new UpdateNotificationLvAdapter(this, register_notify));
		
		if (register_notify.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(NotificationAddUpdateDeleteActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Notification Yet!");
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

