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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.SalePersonReportAdapter;
import com.ignite.pos.adapter.SalepersonSpinnerAdapter;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.spSalePerson;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class SalePersonReportActivity extends SherlockActivity{
	private Spinner spSaleperson;
	private Button fromdate, todate, search;
	private spSalePerson sale_person;
	private List<Object> spList;
	private String selectedSaleperson;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_sale_report;
	private TextView txt_total;
	private SalePersonReportAdapter spReportListViewAdapter;
	private List<Object> listVoucher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saleperson_activity);
		
		spSaleperson = (Spinner)findViewById(R.id.spSalePerson);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_sale_report = (ListView) findViewById(R.id.lv_sale_report);
		txt_total = (TextView) findViewById(R.id.txtTotal);
		
		spSaleperson.setOnItemSelectedListener(salepersonClickListener);
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
		getSalePerson();
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
				dbManager = new SaleVouncherController(SalePersonReportActivity.this);
				SaleVouncherController svController = (SaleVouncherController) dbManager;
				listVoucher = new ArrayList<Object>();
				listVoucher = svController.select(selectedSaleperson, selectedFromDate, selectedToDate);
				
				spReportListViewAdapter = new SalePersonReportAdapter(SalePersonReportActivity.this, listVoucher);
				lv_sale_report.setAdapter(spReportListViewAdapter);
				
				GrandTotal();
				
				if (listVoucher.size() == 0) {
					txt_total.setText("0.00");
				}
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(SalePersonReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(SalePersonReportActivity.this);

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
	
	private OnItemSelectedListener salepersonClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			sale_person = (spSalePerson) spList.get(position); 
			selectedSaleperson = sale_person.getSpusername();
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getSalePerson()
	{
		dbManager = new spSalePersonController(this);
		spSalePersonController control = (spSalePersonController)dbManager;
		spList = new ArrayList<Object>();
		
		spSalePerson salePerson = new spSalePerson();
		salePerson.setSpusername("All");
		
		spList.add(salePerson);
		spList.addAll(control.select());
		
		spSaleperson.setAdapter(new SalepersonSpinnerAdapter(this,spList));
		Log.i("","Sale Person List:" + spList.toString());
	}
	

	private void getVoucher() {
		// TODO Auto-generated method stub
		dbManager = new SaleVouncherController(SalePersonReportActivity.this);
		SaleVouncherController svController = (SaleVouncherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = svController.select();
		spReportListViewAdapter = new SalePersonReportAdapter(SalePersonReportActivity.this, listVoucher);
		lv_sale_report.setAdapter(spReportListViewAdapter);
		
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
