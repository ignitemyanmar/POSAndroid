package com.ignite.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class AdminMainActivity extends SherlockActivity{
	private Button	btn_new_brand, new_barcode, new_customer, new_saleperson , new_category, new_subcategory, update_prices, admin_acc, update_cus , addAdmin , sales_report , item_list_report, credit_report;
	private Button btn_update_category, btn_update_subcategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_main);
		
		//ADD NEW Items
		btn_new_brand = (Button)findViewById(R.id.btn_new_brand);
		new_category = (Button)findViewById(R.id.btn_category);
		new_subcategory = (Button)findViewById(R.id.btn_subcategory);
		new_barcode = (Button)findViewById(R.id.btnBarcode);
		
		//UPDATE
		btn_update_category = (Button) findViewById(R.id.btn_update_category);
		btn_update_subcategory = (Button) findViewById(R.id.btn_update_subcategory);
		update_prices = (Button) findViewById(R.id.btn_update_prices);
		admin_acc = (Button) findViewById(R.id.btn_admin_acc);
		update_cus = (Button) findViewById(R.id.btn_update_cus);
		
		
		// REPORT / Add Person
		sales_report = (Button) findViewById(R.id.btn_sales_report);
		item_list_report = (Button) findViewById(R.id.btn_item_list_report);
		addAdmin = (Button) findViewById(R.id.btn_add_admin_account);
		new_saleperson= (Button)findViewById(R.id.btn_sale_person);
		new_customer = (Button)findViewById(R.id.btn_customer_info);
		
		
		btn_new_brand.setOnClickListener(clickListener);
		new_barcode.setOnClickListener(clickListener);
		new_customer.setOnClickListener(clickListener);
		new_saleperson.setOnClickListener(clickListener);
		new_category.setOnClickListener(clickListener);
		new_subcategory.setOnClickListener(clickListener);
		
		btn_update_category.setOnClickListener(clickListener);
		btn_update_subcategory.setOnClickListener(clickListener);
		update_prices.setOnClickListener(clickListener);
		admin_acc.setOnClickListener(clickListener);
		update_cus.setOnClickListener(clickListener);
		addAdmin.setOnClickListener(clickListener);
		
		sales_report.setOnClickListener(clickListener);
		item_list_report.setOnClickListener(clickListener);
		//credit_report.setOnClickListener(clickListener);
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(v == new_barcode)
			{
				startActivity(new Intent(AdminMainActivity.this,AddBarcodeActivity.class));
			}
			if(v == new_customer)
			{
				startActivity(new Intent(AdminMainActivity.this,AddNewCustomerActivity.class));
			}
			if(v == new_saleperson)
			{
				startActivity(new Intent(AdminMainActivity.this,AddNewSalePerson.class));
			}
			if(v == new_category)
			{
				startActivity(new Intent(AdminMainActivity.this,AddCategoryActivity.class));
			}
			if(v == new_subcategory)
			{
				startActivity(new Intent(AdminMainActivity.this,AddSubCategoryActivity.class));
			}
			if (v == btn_update_category) {
				startActivity(new Intent(AdminMainActivity.this, UpdateCategoryListActivity.class));
			}
			if (v == btn_update_subcategory) {
				startActivity(new Intent(AdminMainActivity.this, UpdateSubCategoryListActivity.class));
			}
			if(v == btn_new_brand)
			{
				startActivity(new Intent(AdminMainActivity.this,AddNewBrandActivity.class));
			}
			if(v == update_prices)
			{
				startActivity(new Intent(AdminMainActivity.this,UpdatePrice.class));
			}
			if(v == admin_acc)
			{
				startActivity(new Intent(AdminMainActivity.this,UpdateAdminAccount.class));
			}
			if(v == update_cus)
			{
				startActivity(new Intent(AdminMainActivity.this,UpdateCustomerInfoListActivity.class));
			}
			if(v == addAdmin)
			{
				startActivity(new Intent(AdminMainActivity.this,AddNewAdminAccount.class));
			}
			if(v == sales_report)
			{
				startActivity(new Intent(AdminMainActivity.this,SaleReport.class));
			}
			if(v == item_list_report)
			{
				startActivity(new Intent(AdminMainActivity.this,ItemListReport.class));
			}
			if(v == credit_report)
			{
				startActivity(new Intent(AdminMainActivity.this,CreditReport.class));
			}
		}
	};
}
