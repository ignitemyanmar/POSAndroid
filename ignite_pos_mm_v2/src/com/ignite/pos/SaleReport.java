package com.ignite.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class SaleReport extends SherlockActivity{
	
	private Button salePerson , buyer , voucher, itemlist;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_report_activity);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		title.setText("Sale Report");
		title.setBackgroundResource(R.color.black);
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		salePerson = (Button)findViewById(R.id.btnSaleperson_report);
		buyer = (Button)findViewById(R.id.btnBuyer_report);
		voucher = (Button)findViewById(R.id.btn_sale_voucher_report);
		itemlist = (Button)findViewById(R.id.btn_sale_itemlist_report);
		
		salePerson.setOnClickListener(clickListener);
		buyer.setOnClickListener(clickListener);
		voucher.setOnClickListener(clickListener);
		itemlist.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == salePerson)
			{
				startActivity(new Intent(SaleReport.this,SalePersonReportActivity.class));
			}
			if(v == buyer)
			{
				//startActivity(new Intent(SaleReport.this,BuyerReportActivity.class));
			}
			if(v == voucher)
			{
	 			//startActivity(new Intent(SaleReport.this,VoucherReportActivity.class));
	 		}
			if(v == itemlist)
			{
	 			//startActivity(new Intent(SaleReport.this,VoucherReportActivity.class));
	 		}
	 	}
	 	
	} ; 
}  