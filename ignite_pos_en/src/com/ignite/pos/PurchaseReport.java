package com.ignite.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class PurchaseReport extends SherlockActivity{
	
	private Button btn_by_supplier , btn_by_voucher , btn_by_item_list;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		title.setText("Purchase Report");
		title.setBackgroundResource(R.color.black);
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		btn_by_supplier = (Button)findViewById(R.id.btn_by_supplier_report);
		btn_by_voucher = (Button)findViewById(R.id.btn_by_voucher_report);
		btn_by_item_list = (Button)findViewById(R.id.btn_by_itemlist_report);
		
		btn_by_supplier.setOnClickListener(clickListener);
		btn_by_voucher.setOnClickListener(clickListener);
		btn_by_item_list.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_by_supplier)
			{
				startActivity(new Intent(PurchaseReport.this,SupplierPurchaseReportActivity.class));
			}
			if(v == btn_by_voucher)
			{
				startActivity(new Intent(PurchaseReport.this,VoucherPurchaseReportActivity.class));
			}
			if(v == btn_by_item_list)
			{
	 			startActivity(new Intent(PurchaseReport.this,ItemListReport.class));
	 		}
	 	}
	 	
	} ; 
}  
