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
import com.ignite.pos.adapter.CategoryListAdapter;
import com.ignite.pos.adapter.SubCategoryListAdapter;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;

public class UpdateSubCategoryListActivity extends SherlockActivity{

	private ListView lv_subcategory_list;
	private DatabaseManager dbManager;
	private List<Object> subcategory_list_obj;
	private View title_view;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_subcategory_list);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Update Sub-Category");
		title.setText("ပစၥည္းအမ်ိဳးအစားခြဲ ျပင္ျခင္း");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}
	
	private void selectData()
	{
		dbManager = new SubCategoryController(this);
		SubCategoryController subcategory_control = (SubCategoryController) dbManager ;
		subcategory_list_obj = new ArrayList<Object>();
		subcategory_list_obj = subcategory_control.select();
		
		Log.i("","Sub-Category List from DB :" + subcategory_list_obj.toString());
		
		title_view = LayoutInflater.from(this).inflate(R.layout.update_subcategory_list_title,null,false);
		lv_subcategory_list = (ListView) findViewById(R.id.lvSubCategoryList);
		
		if(lv_subcategory_list.getHeaderViewsCount() == 0)
		{
			lv_subcategory_list.addHeaderView(title_view);
		}
		lv_subcategory_list.setAdapter(new SubCategoryListAdapter(this, subcategory_list_obj));
		
		if (subcategory_list_obj.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(UpdateSubCategoryListActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Sub-Category Yet!");
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
