
package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.CategorySpinnerAdapter;
import com.ignite.pos.adapter.ItemListUpdateAdapter;
import com.ignite.pos.adapter.LedgerReportListViewAdapter;
import com.ignite.pos.adapter.SalepersonSpinnerAdapter;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.spSalePerson;

public class UpdateItemListActivity extends SherlockActivity{

	private ListView lv_item_list;
	private DatabaseManager dbManager;
	private List<Object> item_list_obj;
	private List<Object> pvList;
	private View title_view;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout add_layout;
	private List<Object> category_list_obj;
	private Spinner spnCategory;
	private EditText edt_item_name;
	private Button btn_search;
	private String selectedItemName = "";
	private String selectedCategoryID;
	
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
		
		selectData();
		getCategory();
		
	}

	
	CategoryController category_control;
	
	private void selectData()
	{
		//Get Item List from Item Table
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController) dbManager ;
		item_list_obj = new ArrayList<Object>();
		item_list_obj = item_control.select();
		
		title_view = LayoutInflater.from(this).inflate(R.layout.update_item_list_title,null,false);
		
		spnCategory = (Spinner)title_view.findViewById(R.id.spCategory);
		edt_item_name = (EditText) title_view.findViewById(R.id.etxt_item_name);
		btn_search = (Button)title_view.findViewById(R.id.btnSearch);
		btn_search.setOnClickListener(clicklistener);
		spnCategory.setOnItemSelectedListener(categoryClickListener);
		
		lv_item_list = (ListView) findViewById(R.id.lv_item_list);
		
		if(lv_item_list.getHeaderViewsCount() == 0)
		{
			lv_item_list.addHeaderView(title_view);
		}
		
		//Get Category List from Category Table
		dbManager = new CategoryController(this);
		category_control = (CategoryController) dbManager ;
		category_list_obj = new ArrayList<Object>();
		
		for (int i = 0; i < item_list_obj.size(); i++) {
			
			ItemList itemL = (ItemList)item_list_obj.get(i);			
			category_list_obj = category_control.select(itemL.getCategoryId());
			if(category_list_obj.size() > 0){
				Category cate = (Category) category_list_obj.get(0);
				((ItemList)item_list_obj.get(i)).setCategoryName(cate.getCategoryName());
			}else{
				((ItemList)item_list_obj.get(i)).setCategoryName("-");
			}
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
	
	
	private OnItemSelectedListener categoryClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			Category cat = (Category) category_list.get(position); 
			selectedCategoryID = String.valueOf(cat.getCategoryID());
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnClickListener clicklistener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == spnCategory) {
				
			}
			if (v == btn_search) {
				//if (checkFields()) {
					
					selectedItemName = edt_item_name.getText().toString();
					
					//Get Data from Item Table
					dbManager = new ItemListController(UpdateItemListActivity.this);
					ItemListController itemControl = (ItemListController)dbManager;
					item_list_obj = new ArrayList<Object>();
					item_list_obj = itemControl.selectByCatItemName(selectedCategoryID, selectedItemName);
					
					//Get Category List from Category Table
					dbManager = new CategoryController(UpdateItemListActivity.this);
					category_control = (CategoryController) dbManager ;
					category_list_obj = new ArrayList<Object>();
					
					if (item_list_obj != null && item_list_obj.size() > 0) {
						for (int i = 0; i < item_list_obj.size(); i++) {
							
							ItemList itemL = (ItemList)item_list_obj.get(i);			
							category_list_obj = category_control.select(itemL.getCategoryId());
							if(category_list_obj.size() > 0){
								Category cate = (Category) category_list_obj.get(0);
								((ItemList)item_list_obj.get(i)).setCategoryName(cate.getCategoryName());
							}else{
								((ItemList)item_list_obj.get(i)).setCategoryName("-");
							}
						}
						
						lv_item_list.setAdapter(new ItemListUpdateAdapter(UpdateItemListActivity.this, item_list_obj));

					} 
					
					if (item_list_obj == null || item_list_obj.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(UpdateItemListActivity.this);
						alert.setTitle("Info");
						alert.setMessage("No Item!");
						alert.show();
						alert.setCancelable(true);
						//lv_item_list.removeAllViewsInLayout();
						lv_item_list.setAdapter(null);
					}

				//}
			}
		}
	};
	private List<Object> category_list;

	private void getCategory()
	{
		Category cat = new Category(0,"All");
		category_list = new ArrayList<Object>();
		category_list.add(cat);
		category_list.addAll(category_control.select());
		
		if (category_list_obj.size() > 0) {
			spnCategory.setAdapter(new CategorySpinnerAdapter(this,category_list));
		}
		
		Log.i("","Category List:" + category_list.toString());
	}
	
	public boolean checkFields() {
		if (edt_item_name.getText().toString().length() == 0) {
			edt_item_name.setError("Enter Item Name");
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	
}
