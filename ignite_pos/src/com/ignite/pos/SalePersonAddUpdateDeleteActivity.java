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
import com.ignite.pos.adapter.SalePersonLvAdapter;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;

public class SalePersonAddUpdateDeleteActivity extends SherlockActivity{

	private ListView lv_sale_person;
	private DatabaseManager dbManager;
	private List<Object> sale_person_list;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saleperson_add_update_delete);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Sale Person  |  Add New / Update / Delete");
		title.setText("ေရာင္းသူ [အသစ္/ျပင္/ဖ်က္] ျခင္း");
		add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		add_new.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SalePersonAddUpdateDeleteActivity.this, AddNewSalePerson.class));
			}
		});
	}
	
	private void selectData()
	{
		dbManager = new spSalePersonController(this);
		spSalePersonController sp_control = (spSalePersonController) dbManager ;
		sale_person_list = new ArrayList<Object>();
		sale_person_list = sp_control.select();
		
		Log.i("","Sale Person List from DB :" + sale_person_list.toString());

		lv_sale_person = (ListView) findViewById(R.id.lv_sale_person);
		lv_sale_person.setAdapter(new SalePersonLvAdapter(this, sale_person_list));
		
		if (sale_person_list.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(SalePersonAddUpdateDeleteActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Sale Person Yet!");
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


