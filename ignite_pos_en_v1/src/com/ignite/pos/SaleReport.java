package com.ignite.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class SaleReport extends SherlockActivity{
	
	private Button salePerson , buyer , vouncher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_report_activity);
		
		salePerson = (Button)findViewById(R.id.btnSaleperson_report);
		buyer = (Button)findViewById(R.id.btnBuyer_report);
		//vouncher = (Button)findViewById(R.id.btnVoucher_report);
		
		salePerson.setOnClickListener(clickListener);
		buyer.setOnClickListener(clickListener);
		//vouncher.setOnClickListener(clickListener);
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
				startActivity(new Intent(SaleReport.this,BuyerReportActivity.class));
			}
			if(v == vouncher)
			{
	 			startActivity(new Intent(SaleReport.this,VoucherReportActivity.class));
	 		}
	 	}
	 	
	} ; 
}  