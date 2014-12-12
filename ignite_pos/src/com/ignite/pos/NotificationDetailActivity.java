package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.NotificationDetailLvAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;

public class NotificationDetailActivity extends SherlockActivity{
	
	private ActionBar actionBar;
	private ListView lv_notification_detail;
	private DatabaseManager dbManager;
	private List<Object> listItems;
	private TextView title;
	private Button change_mode;
	private RelativeLayout add_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_detail);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("Notification Detail");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);	
		
		lv_notification_detail = (ListView) findViewById(R.id.lv_notification_detail);
		
		getItem();
	}

	private void getItem() {
		// TODO Auto-generated method stub
		dbManager = new ItemListController(NotificationDetailActivity.this);
		ItemListController itemController = (ItemListController) dbManager;
		listItems = new ArrayList<Object>();
		listItems = itemController.selectByNotifyStatus();
		
		Log.i("", "Notification Detail: "+listItems.toString());
		
		NotificationDetailLvAdapter adapter = new NotificationDetailLvAdapter(NotificationDetailActivity.this, listItems);
		lv_notification_detail.setAdapter(adapter);
	}
	
	
}