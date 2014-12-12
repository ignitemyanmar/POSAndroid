package com.ignite.pos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class OtherReportsActivity extends SherlockActivity{
	
	private Button btn_ledger, btn_profit, btn_slow_moving, btn_stock_report, btn_sale_return_report, btn_sale_history_report;
	private ActionBar actionBar;
	private TextView title;
	private RelativeLayout layout_add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_reports);
		
		//scrollView = (ScrollView) findViewById(R.id.scrollView1);
		//scrollView.setVerticalScrollBarEnabled(false);
		//scrollView.setScrollbarFadingEnabled(false);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		layout_add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		layout_add_new.setVisibility(View.GONE);
		//title.setText("Other Reports");
		title.setText("အျခားမွတ္တမ္းမ်ား");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		btn_sale_history_report = (Button) findViewById(R.id.btn_sale_history_report);
		btn_sale_return_report = (Button) findViewById(R.id.btn_sale_return_report);
		btn_ledger = (Button)findViewById(R.id.btn_ledger_report);
		btn_profit = (Button)findViewById(R.id.btn_profit_report);
		btn_slow_moving = (Button)findViewById(R.id.btn_slow_moving_report);
		btn_stock_report = (Button) findViewById(R.id.btn_stock_report);
		
		btn_sale_history_report.setOnClickListener(clickListener);
		btn_sale_return_report.setOnClickListener(clickListener);
		btn_ledger.setOnClickListener(clickListener);
		btn_profit.setOnClickListener(clickListener);
		btn_slow_moving.setOnClickListener(clickListener);
		btn_stock_report.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_sale_history_report) {
				startActivity(new Intent(OtherReportsActivity.this,SaleHistoryReportActivity.class));
			}
			if (v == btn_sale_return_report) {
				startActivity(new Intent(OtherReportsActivity.this,SaleReturnReportActivity.class));
			}
			if(v == btn_ledger)
			{
	 			startActivity(new Intent(OtherReportsActivity.this,LedgerReportActivity.class));
	 		}
			if(v == btn_profit)
			{
	 			startActivity(new Intent(OtherReportsActivity.this,ProfitReportActivity.class));
	 		}
			if(v == btn_slow_moving)
			{
	 			startActivity(new Intent(OtherReportsActivity.this,SlowMovingReportActivity.class));
	 		}
			if (v == btn_stock_report) {
				startActivity(new Intent(OtherReportsActivity.this,ItemListReport.class));
			}
	 	}
	 	
	} ; 
}  
