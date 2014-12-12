package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.BrandSpinnerAdapter;
import com.ignite.pos.adapter.CategorySpinnerAdapter;
import com.ignite.pos.adapter.SubCategorySpinnerAdapter;
import com.ignite.pos.database.controller.BrandController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Brand;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SubCategory;
import com.smk.skalertmessage.SKToastMessage;

public class AddBarcodeActivity extends SherlockActivity{
	private EditText et_scan ,et_item_name, et_item_price, et_qty;
	private Button save;
	private Spinner category , subcategory, sp_brand;
	private String str_scan, str_item_name, str_item_price, str_qty;
	private Integer selectedCategoryId , selectedSubCategoryId, selectedBrandId;
	private DatabaseManager dbManager;
	private List<Object> itemList;
	private Category category_obj;
	private SubCategory subcategory_obj;
	private Brand brand_obj;
	private List<Object> catList;
	private List<Object> subcatList;
	private List<Object> brandList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addbarcode);
		
		et_scan = (EditText)findViewById(R.id.editText_scan);
		et_item_name = (EditText)findViewById(R.id.editText_item_name);
		et_item_price = (EditText)findViewById(R.id.editText_item_price);
		et_qty = (EditText) findViewById(R.id.editText_qty);
		
		category = (Spinner)findViewById(R.id.spn_category);
		category.setOnItemSelectedListener(categoryClickListener);
		
		subcategory = (Spinner)findViewById(R.id.spn_subcategory);
		subcategory.setOnItemSelectedListener(subcategoryClickListener);
		
		sp_brand = (Spinner) findViewById(R.id.spn_brand);
		sp_brand.setOnItemSelectedListener(brandClickListener);
		
		getCategory();
		getBrand();
		
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
			
		//Check Item List in Database
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController)dbManager;
		itemList = new ArrayList<Object>();
		itemList = item_control.select();
		Log.i("","Item List in Database :" + itemList.toString());
		Log.i("","Item List in Database :" + itemList.size());
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == save)
			{
				if (checkFields()) {
					
					str_scan = et_scan.getText().toString();
					str_item_name = et_item_name.getText().toString();
					str_item_price = et_item_price.getText().toString();
					str_qty = et_qty.getText().toString();
					
					saveData();	
				}
			}
		}
	};
	
	private OnItemSelectedListener categoryClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			category_obj = (Category)catList.get(arg2);
			selectedCategoryId = category_obj.getCategoryID();
			getSubCategory();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnItemSelectedListener subcategoryClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			subcategory_obj = (SubCategory)subcatList.get(arg2);
			selectedSubCategoryId = subcategory_obj.getSubCategoryID();
			
			Log.i("", "selected Sub_category Id for Items: "+selectedSubCategoryId);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnItemSelectedListener brandClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			brand_obj = (Brand)brandList.get(arg2);
			selectedBrandId = brand_obj.getBrandID();
			
			Log.i("", "selected Brand Id for Items: "+selectedBrandId);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void saveData()
	{
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController)dbManager;
		itemList = new ArrayList<Object>();
		
		itemList.add(new ItemList(str_scan, str_item_name, str_item_price, str_qty
					, selectedCategoryId.toString(), selectedSubCategoryId.toString(), selectedBrandId.toString()));
		
		if (itemList != null && itemList.size() > 0) {
			item_control.save(itemList);
		}
		
		Log.i("","Item List in DB after Save :" + item_control.select().toString());
		
		clearText();
		
		SKToastMessage.showMessage(getApplicationContext(), "New Item saved!", SKToastMessage.SUCCESS);
	}
	
	private void getCategory()
	{
		dbManager = new CategoryController(this);
		CategoryController control = (CategoryController)dbManager;
		catList = new ArrayList<Object>();
		catList = control.select();
		category.setAdapter(new CategorySpinnerAdapter(this,catList));
		
		Log.i("","Category List :" + catList.toString());
	}
	
	private void getSubCategory()
	{
		dbManager = new SubCategoryController(this);
		
		SubCategoryController subCat_control = (SubCategoryController)dbManager;
		subcatList = new ArrayList<Object>();
		subcatList = subCat_control.select(selectedCategoryId.toString());
		
		Log.i("","Get sub Category List for Category Id "+selectedCategoryId+": "+ subcatList);
		
		if (subcatList != null && subcatList.size() > 0) {
			subcategory.setAdapter(new SubCategorySpinnerAdapter(this,subcatList));
		}
	}
	
	private void getBrand()
	{
		dbManager = new BrandController(this);
		BrandController control = (BrandController)dbManager;
		brandList = new ArrayList<Object>();
		brandList = control.select();
		sp_brand.setAdapter(new BrandSpinnerAdapter(this,brandList));
		
		Log.i("","Brand List :" + brandList.toString());
	}
	
	private void clearText()
	{
		et_scan.getText().clear();
		et_item_name.getText().clear();
		et_item_price.getText().clear();
		et_qty.getText().clear();
		et_scan.requestFocus();
		
	}
	
	public boolean checkFields() {
		if (et_scan.getText().toString().length() == 0) {
			et_scan.setError("Tab here to scan");
			return false;
		}
		if (et_item_name.getText().toString().length() == 0) {
			et_item_name.setError("Enter Item Name");
			return false;
		}
		if (et_item_price.getText().toString().length() == 0) {
			et_item_price.setError("Enter Price");
			return false;
		}
		if (et_qty.getText().toString().length() == 0) {
			et_qty.setError("Enter Quantity");
			return false;
		}
		if (category.getCount() == 0) {
			SKToastMessage.showMessage(getApplicationContext(), "Choose Category", SKToastMessage.WARNING);
			return false;
		}
		if (subcategory.getCount() == 0) {
			SKToastMessage.showMessage(getApplicationContext(), "Choose Sub_Category", SKToastMessage.WARNING);
			return false;
		}
		if (sp_brand.getCount() == 0) {
			SKToastMessage.showMessage(getApplicationContext(), "Choose Brand", SKToastMessage.WARNING);
			return false;
		}
		
		return true;
	}
}
