
package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;

public class AdminMainActivity extends Activity{
	
	private android.app.ActionBar actionBar;
	private TextView title;
	private RelativeLayout layout_notify;
	private ImageView imgv_notify;
	private Button	btn_new_brand, new_barcode, new_customer, new_saleperson , new_category, new_subcategory, update_prices, admin_acc, update_cus , addAdmin , sales_report , credit_report;
	private Button btn_new_supplier, btn_new_purchase_voucher;
	private Button btn_purchase_report, btn_other_reports;
	private Button btn_update_category, btn_update_subcategory, btn_update_supplier, btn_update_item;
	private Button btn_register_notification, btn_sale_return, btn_sale_return_report;
	private DatabaseManager dbManager;
	private Button btn_buyer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_main);
		
		//Get User Level 
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		String user_level = pref.getString("admin_userlevel", "");
		
		//ADD NEW Items
		new_barcode = (Button)findViewById(R.id.btnBarcode);
		btn_new_purchase_voucher = (Button) findViewById(R.id.btn_new_purchase_voucher);
		btn_register_notification = (Button) findViewById(R.id.btn_register_notification);
		btn_sale_return = (Button) findViewById(R.id.btn_sale_return);
		//btn_sale_return_report = (Button) findViewById(R.id.btn_sale_return_report);
		
		//UPDATE
		btn_update_item = (Button) findViewById(R.id.btn_update_item);
		btn_update_category = (Button) findViewById(R.id.btn_update_category);
		btn_update_subcategory = (Button) findViewById(R.id.btn_update_subcategory);
		update_prices = (Button) findViewById(R.id.btn_update_prices);
		//admin_acc = (Button) findViewById(R.id.btn_admin_acc);
		update_cus = (Button) findViewById(R.id.btn_update_cus);
		//btn_update_supplier = (Button) findViewById(R.id.btn_update_sup);
		
		
		// REPORT / Add Person
		sales_report = (Button) findViewById(R.id.btn_sales_report);
		btn_purchase_report = (Button) findViewById(R.id.btn_purchase_report);
		btn_other_reports = (Button) findViewById(R.id.btn_other_reports);
		addAdmin = (Button) findViewById(R.id.btn_add_admin_account);
		new_saleperson= (Button)findViewById(R.id.btn_sale_person);		
		btn_new_supplier = (Button) findViewById(R.id.btn_supplier_info);
		btn_buyer = (Button)findViewById(R.id.btn_buyer);
		
		//Limit User Level Access
		if (user_level.equals("Staff")) {
			addAdmin.setVisibility(View.GONE);
			btn_new_supplier.setVisibility(View.GONE);
			
		}else {
			addAdmin.setVisibility(View.VISIBLE);
			btn_new_supplier.setVisibility(View.VISIBLE);
		}
		

		//btn_sale_return_report.setOnClickListener(clickListener);
		btn_sale_return.setOnClickListener(clickListener);
		btn_register_notification.setOnClickListener(clickListener);
		btn_new_purchase_voucher.setOnClickListener(clickListener);
		//btn_new_brand.setOnClickListener(clickListener);
		new_barcode.setOnClickListener(clickListener);
		//new_customer.setOnClickListener(clickListener);
		new_saleperson.setOnClickListener(clickListener);
		//new_category.setOnClickListener(clickListener);
	//	new_subcategory.setOnClickListener(clickListener);
		btn_new_supplier.setOnClickListener(clickListener);
		
		btn_update_item.setOnClickListener(clickListener);
		//btn_update_supplier.setOnClickListener(clickListener);
		btn_update_category.setOnClickListener(clickListener);
		btn_update_subcategory.setOnClickListener(clickListener);
		update_prices.setOnClickListener(clickListener);
		//admin_acc.setOnClickListener(clickListener);
		update_cus.setOnClickListener(clickListener);
		addAdmin.setOnClickListener(clickListener);
		
		sales_report.setOnClickListener(clickListener);
		btn_purchase_report.setOnClickListener(clickListener);
		btn_other_reports.setOnClickListener(clickListener);
		//credit_report.setOnClickListener(clickListener);
		
		btn_buyer.setOnClickListener(clickListener);
		
	}

	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_sale_return_report) {
				startActivity(new Intent(getApplicationContext(), SaleReturnReportActivity.class));
			}
			if (v == btn_sale_return) {
				startActivity(new Intent(AdminMainActivity.this, SaleReturnActivity.class));
			}
			if (v == btn_register_notification) {
				startActivity(new Intent(getApplicationContext(), NotificationAddUpdateDeleteActivity.class));
			}
			if (v == btn_new_purchase_voucher) {
				startActivity(new Intent(getApplicationContext(), AddNewPurchaseVoucherActivity.class));
			}
			if (v == btn_new_supplier) {
				startActivity(new Intent(AdminMainActivity.this, SupplierAddUpdateDeleteActivity.class));
			}
			if(v == new_barcode)
			{
				startActivity(new Intent(AdminMainActivity.this,AddBarcodeActivity.class));
			}
			if(v == btn_buyer)
			{
				startActivity(new Intent(AdminMainActivity.this, BuyerAddUpdateDeleteActivity.class));
			}
			if(v == new_saleperson)
			{
				startActivity(new Intent(AdminMainActivity.this,SalePersonAddUpdateDeleteActivity.class));
			}
			if(v == new_category)
			{
				startActivity(new Intent(AdminMainActivity.this,AddCategoryActivity.class));
			}
			if(v == new_subcategory)
			{
				startActivity(new Intent(AdminMainActivity.this,AddSubCategoryActivity.class));
			}
			if (v == btn_update_item) {
				startActivity(new Intent(getApplicationContext(), UpdateItemListActivity.class));
			}
			if (v == btn_update_category) {
				startActivity(new Intent(AdminMainActivity.this, UpdateCategoryListActivity.class));
			}
			if (v == btn_update_subcategory) {
				startActivity(new Intent(AdminMainActivity.this, UpdateSubCategoryListActivity.class));
			}
			if (v == btn_update_supplier) {
				startActivity(new Intent(AdminMainActivity.this, SupplierAddUpdateDeleteActivity.class));
			}
			if(v == btn_new_brand)
			{
				startActivity(new Intent(AdminMainActivity.this,AddNewBrandActivity.class));
			}
			if(v == update_prices)
			{
				startActivity(new Intent(AdminMainActivity.this,UpdateSalePrice.class));
			}
			if(v == admin_acc)
			{
				startActivity(new Intent(AdminMainActivity.this,UpdateAdminAccount.class));
			}
			if(v == addAdmin)
			{
				startActivity(new Intent(AdminMainActivity.this, AdminAddUpdateDeleteActivity.class));
			}
			if(v == sales_report)
			{
				startActivity(new Intent(AdminMainActivity.this,SalePersonReportActivity.class));
			}
			if (v == btn_purchase_report) {
				startActivity(new Intent(AdminMainActivity.this, SupplierPurchaseReportActivity.class));
			}
			if (v == btn_other_reports) {
				startActivity(new Intent(AdminMainActivity.this, OtherReportsActivity.class));
			}
			if(v == credit_report)
			{
				//startActivity(new Intent(AdminMainActivity.this,CreditReport.class));
			}
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		actionBar = getActionBar();						
		actionBar.setCustomView(R.layout.action_bar_2);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Admin Panel");
		title.setText("စီမံခန္႔ခြဲသည့္အပုိင္း");
		layout_notify = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_notify);
		imgv_notify = (ImageView)actionBar.getCustomView().findViewById(R.id.imgv_notify);
		
		//Show|Hide Notify Image 
		dbManager = new ItemListController(AdminMainActivity.this);
		ItemListController itemControl = (ItemListController)dbManager;
		List<Object> itemList = new ArrayList<Object>();
		itemList = itemControl.selectByNotifyStatus();
		
		Log.i("", "Notify List: "+itemList.toString());
		
		if (itemList.size() > 0) {
			layout_notify.setVisibility(View.VISIBLE);
			imgv_notify.setVisibility(View.VISIBLE);
		}else {
			layout_notify.setVisibility(View.GONE);
			imgv_notify.setVisibility(View.GONE);
		}
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		layout_notify.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AdminMainActivity.this, NotificationDetailActivity.class));
			}
		});
	}
}
