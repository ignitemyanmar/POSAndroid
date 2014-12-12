package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

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
	private EditText edt_item_code ,edt_item_name; 
	//private EditText editTxt_sale_price, editTxt_purchase_price;
	private Button save, btn_new_category, btn_new_subcategory, btn_new_brand;
	private Spinner category , subcategory, sp_brand;
	private String str_item_code, str_item_name, str_purchase_price, str_sale_price;
	private Integer selectedCategoryId = 0 , selectedSubCategoryId = 0, selectedBrandId = 0;
	private DatabaseManager dbManager;
	private List<Object> itemList;
	private Category category_obj;
	private SubCategory subcategory_obj;
	private Brand brand_obj;
	private List<Object> catList;
	private List<Object> subcatList;
	private List<Object> brandList;
	private SubCategorySpinnerAdapter subCatSpinnerAdapter;
	private CategorySpinnerAdapter catSpinnerAdapter;
	private BrandSpinnerAdapter brandSpinnerAdapter;
	private ScrollView scrollView;
	private TextView txt_last_item_code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addbarcode);
		
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		
		edt_item_code = (EditText)findViewById(R.id.editText_scan);
		txt_last_item_code = (TextView) findViewById(R.id.txt_last_item_code);
		edt_item_name = (EditText)findViewById(R.id.editText_item_name);
		//editTxt_sale_price = (EditText)findViewById(R.id.editText_sale_price);
		//editTxt_purchase_price = (EditText)findViewById(R.id.editTxt_purchase_price);
		
		//For Spinners
		category = (Spinner)findViewById(R.id.spn_category);
		category.setOnItemSelectedListener(categoryClickListener);
		
		subcategory = (Spinner)findViewById(R.id.spn_subcategory);
		subcategory.setOnItemSelectedListener(subcategoryClickListener);
		
		sp_brand = (Spinner) findViewById(R.id.spn_brand);
		sp_brand.setOnItemSelectedListener(brandClickListener);
		
		btn_new_category = (Button) findViewById(R.id.btn_new_category);
		btn_new_subcategory = (Button) findViewById(R.id.btn_new_subcategory);
		btn_new_brand = (Button) findViewById(R.id.btn_new_brand);
		
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(clickListener);
		btn_new_category.setOnClickListener(clickListener);
		btn_new_subcategory.setOnClickListener(clickListener);
		btn_new_brand.setOnClickListener(clickListener);
			

		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == save)
			{
				
				if (checkFields()) {
					
					str_item_code = edt_item_code.getText().toString();
					str_item_name = edt_item_name.getText().toString();
					
					//Check Duplicate Item Code 
					if (itemList.size() > 0) {
						
						boolean isExist = false;
						
						for (int i = 0; i < itemList.size(); i++) {
							
							ItemList itemobj = (ItemList) itemList.get(i);
							
							if (str_item_code.toLowerCase().equals(itemobj.getItemId().toLowerCase())) {
								
								isExist = true;
								
								SKToastMessage.showMessage(getApplicationContext(), str_item_code +" is already exist!", SKToastMessage.WARNING);
								
								break;
							}
						}
						
						if (!isExist) {
							saveData();	
						}
					}else {
						saveData();	
					}
				}
				
				
			}
			if (v == btn_new_category) {
				startActivity(new Intent(AddBarcodeActivity.this, AddCategoryActivity.class));
			}
			if (v == btn_new_subcategory) {
				startActivity(new Intent(AddBarcodeActivity.this, AddSubCategoryActivity.class));
			}
			if (v == btn_new_brand) {
				startActivity(new Intent(AddBarcodeActivity.this, AddNewBrandActivity.class));
			}
		}
	};
	
	private OnItemSelectedListener categoryClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2 > 0){
				category_obj = (Category)catList.get(arg2);
				selectedCategoryId = category_obj.getCategoryID();
				getSubCategory();
			}else{
				selectedCategoryId = 0;
			}
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnItemSelectedListener subcategoryClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2 > 0){
				subcategory_obj = (SubCategory)subcatList.get(arg2);
				selectedSubCategoryId = subcategory_obj.getSubCategoryID();
			}else{
				selectedSubCategoryId = 0;
			}
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnItemSelectedListener brandClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2 > 0){
				brand_obj = (Brand)brandList.get(arg2);
				selectedBrandId = brand_obj.getBrandID();
			}
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
		
		itemList.add(new ItemList(str_item_code, str_item_name, str_purchase_price, str_sale_price
				, "", "", selectedCategoryId.toString(), selectedSubCategoryId.toString(), selectedBrandId.toString()
				, 0, 0, 0, 0));
		
		if (itemList != null && itemList.size() > 0) {
			item_control.save(itemList);
		}
		
		Log.i("","Item List in DB after Save :" + itemList.toString());
		
		clearText();
		
		//Check Item List in Database
		itemList = new ArrayList<Object>();
		itemList = item_control.select();
		
		if(itemList != null && itemList.size() > 0){
			txt_last_item_code.setText("Last Item Code ["+((ItemList)itemList.get(0)).getItemId()+"]");
		}
		
		SKToastMessage.showMessage(getApplicationContext(), "Item - "+str_item_code+" saved!", SKToastMessage.SUCCESS);
		
	}
	
	private void getCategory()
	{
		dbManager = new CategoryController(this);
		CategoryController control = (CategoryController)dbManager;
		catList = new ArrayList<Object>();
		catList.add(new Category(0, "Select Category"));
		catList.addAll(control.select());
		
		catSpinnerAdapter = new CategorySpinnerAdapter(this, catList);
		category.setAdapter(catSpinnerAdapter);
		
		catSpinnerAdapter.notifyDataSetChanged();
	}
	
	private void getSubCategory()
	{
		dbManager = new SubCategoryController(this);
		
		SubCategoryController subCat_control = (SubCategoryController)dbManager;
		subcatList = new ArrayList<Object>();
		subcatList.add(new SubCategory(0, "Select Subcategory", 0));
		subcatList.addAll(subCat_control.select(selectedCategoryId.toString()));
		
		subCatSpinnerAdapter = new SubCategorySpinnerAdapter(this, subcatList);
		subcategory.setAdapter(subCatSpinnerAdapter);
		
		subCatSpinnerAdapter.notifyDataSetChanged();
	}
	
	private void getBrand()
	{
		dbManager = new BrandController(this);
		BrandController control = (BrandController)dbManager;
		brandList = new ArrayList<Object>();
		brandList.add(new Brand(0, "Select Brand"));
		brandList.addAll(control.select());
		
		brandSpinnerAdapter = new BrandSpinnerAdapter(this,brandList);
		sp_brand.setAdapter(brandSpinnerAdapter);
		
		brandSpinnerAdapter.notifyDataSetChanged();
	}
	
	private void clearText()
	{
		edt_item_code.getText().clear();
		edt_item_name.getText().clear();
		edt_item_code.requestFocus();
		
	}
	
	public boolean checkFields() {
		if (edt_item_code.getText().toString().length() == 0) {
			edt_item_code.setError("Enter Item Code");
			return false;
		}
		
		if (edt_item_name.getText().toString().length() == 0) {
			edt_item_name.setError("Enter Item Name");
			return false;
		}
		if (selectedCategoryId == 0) {
			SKToastMessage.showMessage(getApplicationContext(), "Pls Add/Choose New Category", SKToastMessage.WARNING);
			return false;
		}
		if (selectedSubCategoryId == 0) {
			SKToastMessage.showMessage(getApplicationContext(), "Pls Add/Choose New Sub-Category!", SKToastMessage.WARNING);
			return false;
		}
		if (selectedBrandId == 0) {
			SKToastMessage.showMessage(getApplicationContext(), "Pls Add/Choose New Brand", SKToastMessage.WARNING);
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getCategory();
		getBrand();
		
		//Check Item List in Database
		dbManager = new ItemListController(this);
		ItemListController item_control = (ItemListController)dbManager;
		itemList = new ArrayList<Object>();
		itemList = item_control.select();
		
		if(itemList.size() > 0){
			txt_last_item_code.setText("Last Item Code ["+((ItemList)itemList.get(0)).getItemId()+"]");
		}
	}
}
