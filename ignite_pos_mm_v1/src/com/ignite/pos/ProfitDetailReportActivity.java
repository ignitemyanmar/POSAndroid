package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.BaseSherlockActivity.LoginCallbacks;
import com.ignite.pos.adapter.ProfitDetailReportLvAdapter;
import com.ignite.pos.adapter.SaleDetailReportLvAdapter;
import com.ignite.pos.adapter.SaleDetailReportLvAdapter.saleCallback;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;

public class ProfitDetailReportActivity extends BaseSherlockActivity{

	private ActionBar actionBar;
	private TextView title;
	private TextView txt_grand_total;
	private ListView lv_profit_by_voucher;
	private DatabaseManager dbManager;
	private List<Object> listVoucher;
	private String date;
	private ProfitDetailReportLvAdapter lvAdapter;
	private String AdminName;
	private RelativeLayout add_layout;
	private TextView txt_date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profit_detail_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("အ႐ံႈး/အျမတ္ - ေဘာင္ ခ်ာ မွတ္တမ္း");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		txt_date = (TextView)findViewById(R.id.txt_profit_date);
		lv_profit_by_voucher = (ListView) findViewById(R.id.lv_profit_detail_report);
		txt_grand_total = (TextView) findViewById(R.id.txt_profit_total);
		
		Bundle bundle = getIntent().getExtras();
		date = bundle.getString("Date");
		
		txt_date.setText("Date: "+date);
		getVoucherDetail();
		
		Integer grandTotal = 0;
		for (int i = 0; i < listVoucher.size(); i++) {
			Profit profit = (Profit)listVoucher.get(i);
			
			Integer profit_voucher = profit.getTotalProfit() - profit.getDiscount();
			grandTotal += profit_voucher;
			txt_grand_total.setText(grandTotal+"");
		}
		
		
		//Integer grandTotal = Integer.valueOf(sv.getTotal()) - Integer.valueOf(sv.getDiscount());
		//txt_grand_total.setText(grandTotal+"");
	}

	private void getVoucherDetail() {
		// TODO Auto-generated method stub
		dbManager = new ProfitController(ProfitDetailReportActivity.this);
		ProfitController profitControl = (ProfitController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = profitControl.selectByDate(date);
		
		Log.i("", "List Voucher: "+listVoucher.toString());
		
		lvAdapter = new ProfitDetailReportLvAdapter(ProfitDetailReportActivity.this, listVoucher);
		lv_profit_by_voucher.setAdapter(lvAdapter);
	}	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
}

