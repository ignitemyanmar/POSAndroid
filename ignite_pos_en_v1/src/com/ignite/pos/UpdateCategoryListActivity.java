
package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.CategoriesListAdapter;
import com.ignite.pos.adapter.CategoryListAdapter;
import com.ignite.pos.adapter.CustomerListAdapter;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.util.DatabaseManager;

public class UpdateCategoryListActivity extends SherlockActivity{

	private ListView lv_category_list;
	private DatabaseManager dbManager;
	private List<Object> category_list_obj;
	private View title_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_category_list);
	}
	
	private void selectData()
	{
		dbManager = new CategoryController(this);
		CategoryController category_control = (CategoryController) dbManager ;
		category_list_obj = new ArrayList<Object>();
		category_list_obj = category_control.select();
		
		Log.i("","Category List from DB :" + category_list_obj.toString());
		
		title_view = LayoutInflater.from(this).inflate(R.layout.update_category_list_title,null,false);
		lv_category_list = (ListView) findViewById(R.id.lvCategoryList);
		
		if(lv_category_list.getHeaderViewsCount() == 0)
		{
			lv_category_list.addHeaderView(title_view);
		}
		lv_category_list.setAdapter(new CategoryListAdapter(this, category_list_obj));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		selectData();
	}
	
	
}
