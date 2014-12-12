package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.BuyerReportAdapter;
import com.ignite.pos.adapter.BuyerSpinnerAdapter;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.SaleVouncher;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class BuyerReportActivity extends SherlockActivity{
	private Spinner spBuyer;
	private Button fromdate, todate, search;
	private Buyer buyer;
	private List<Object> buyerList;
	private String selectedBuyer;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_sale_report;
	private TextView txt_total;
	private BuyerReportAdapter buyerReportListViewAdapter;
	private List<Object> listVoucher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyer_activity);
		
		spBuyer = (Spinner)findViewById(R.id.spBuyer);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_sale_report = (ListView) findViewById(R.id.lv_sale_report);
		txt_total = (TextView) findViewById(R.id.txtTotal);
		
		spBuyer.setOnItemSelectedListener(buyerClickListener);
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
		getBuyer();
		getVoucher();
		GrandTotal();
		
		if (listVoucher.size() == 0) {
			txt_total.setText("0.00");
		}
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == search)
			{
				dbManager = new SaleVouncherController(BuyerReportActivity.this);
				SaleVouncherController svController = (SaleVouncherController) dbManager;
				listVoucher = new ArrayList<Object>();
				listVoucher = svController.selectbyBuyer(selectedBuyer, selectedFromDate, selectedToDate);
				
				buyerReportListViewAdapter = new BuyerReportAdapter(BuyerReportActivity.this, listVoucher);
				lv_sale_report.setAdapter(buyerReportListViewAdapter);
				
				GrandTotal();
				
				if (listVoucher.size() == 0) {
					txt_total.setText("0.00");
				}
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(BuyerReportActivity.this);

				  skCalender.setCallbacks(new Callbacks() {

				        public void onChooseDate(String chooseDate) {
				          // TODO Auto-generated method stub
				        	
				        	Date formatedDate = null;
				        	try {
								formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	selectedFromDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
				        	fromdate.setText(selectedFromDate);
				        	
				        	skCalender.dismiss();
				        }
				  });

				  skCalender.show();
			}
			if( v == todate)
			{
				final SKCalender skCalender = new SKCalender(BuyerReportActivity.this);

				  skCalender.setCallbacks(new Callbacks() {

				        public void onChooseDate(String chooseDate) {
				          // TODO Auto-generated method stub
				        	Date formatedDate = null;
				        	try {
								formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	selectedToDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
				        	todate.setText(selectedToDate);
				        	skCalender.dismiss();
				        }
				  });

				  skCalender.show();
			}
		}
	};
	
	private OnItemSelectedListener buyerClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			buyer = (Buyer) buyerList.get(position); 
			selectedBuyer= buyer.getCusName();
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getBuyer()
	{
		dbManager = new BuyerController(this);
		BuyerController control = (BuyerController)dbManager;
		buyerList = new ArrayList<Object>();
		
		Buyer buyer = new Buyer();
		buyer.setCusName("All");
		
		buyerList.add(buyer);
		buyerList.addAll(control.select());
		spBuyer.setAdapter(new BuyerSpinnerAdapter(this,buyerList));
		Log.i("","Buyer List:" + buyerList.toString());
	}
	
	private void getVoucher() {
		// TODO Auto-generated method stub
		dbManager = new SaleVouncherController(BuyerReportActivity.this);
		SaleVouncherController svController = (SaleVouncherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = svController.select();
		buyerReportListViewAdapter = new BuyerReportAdapter(BuyerReportActivity.this, listVoucher);
		lv_sale_report.setAdapter(buyerReportListViewAdapter);
		
		//svController.delete();
	}
	
	private void GrandTotal()
	{		
			double grandTotal = 0; 
			
			for (int i = 0; i < listVoucher.size(); i++) {
				double total = Double.valueOf(((SaleVouncher) listVoucher.get(i)).getItemtotal());
				grandTotal += total;
			}
			
			txt_total.setText(grandTotal+"0");
			
	}
}
