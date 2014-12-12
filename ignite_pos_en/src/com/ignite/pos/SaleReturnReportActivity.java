package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.SalePersonReportAdapter;
import com.ignite.pos.adapter.SaleReturnReportAdapter;
import com.ignite.pos.adapter.SalepersonSpinnerAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.SaleReturnController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleReturn;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.spSalePerson;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class SaleReturnReportActivity extends SherlockActivity{
	private Spinner spSaleperson;
	private Button fromdate, todate, search;
	private spSalePerson sale_person;
	private List<Object> spList;
	private String selectedReturnVou;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_sale_return_report;
	private TextView txt_grand_total;
	private SaleReturnReportAdapter saleReturnReportAdapter;
	private List<Object> listVoucher;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	private String currentDate;
	private RelativeLayout add_layout;
	private AutoCompleteTextView autocom_return_vou_no;
	private List<Object> listReturnVou;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_return_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("Sale Return Report");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		autocom_return_vou_no = (AutoCompleteTextView)findViewById(R.id.autocom_return_vou_no);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_sale_return_report = (ListView) findViewById(R.id.lv_sale_return_report);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
		//Limit Default Dates
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		
		//Split current date 
		String[] parts = currentDate.split("-");
		String year = parts[0]; 
		String month = parts[1];
		
		String defaultFromDate = year+"-"+month+"-"+"01";
		
		fromdate.setText(defaultFromDate);
		selectedFromDate = fromdate.getText().toString();
		todate.setText(currentDate);
		selectedToDate = todate.getText().toString();
		
		//Return Voucher No. AutoComplete
		getReturnVoucherNo();
		
	}
	
	private void getReturnVoucherNo() {
		// TODO Auto-generated method stub
		dbManager = new SaleReturnController(this);
		SaleReturnController returnControl = (SaleReturnController)dbManager;
		listReturnVou = new ArrayList<Object>();
		
		listReturnVou.add(new SaleReturn("All"));
		listReturnVou.addAll(returnControl.select());
		
		Log.i("", "Return Voucher List: "+listReturnVou.toString());
		
		//Change List<Object> to String Array
		String[] vouArray = new String[listReturnVou.size()];
		
		for (int i = 0; i < listReturnVou.size(); i++) {
			
			SaleReturn vouObj = (SaleReturn)listReturnVou.get(i);
			
			vouArray[i] = vouObj.getVid();  
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vouArray);
		autocom_return_vou_no.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autocom_return_vou_no.setThreshold(1);
	}

	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == search)
			{
				/*if (selectedReturnVou.equals("All") && fromdate.getText().toString().equals("From Date") && todate.getText().toString().equals("To Date")) {
					getVoucher();
				}else {*/
				
				if (checkFields()) {
					
					selectedReturnVou = autocom_return_vou_no.getText().toString();
					
					dbManager = new SaleReturnController(SaleReturnReportActivity.this);
					SaleReturnController returnController = (SaleReturnController) dbManager;
					listVoucher = new ArrayList<Object>();
					listVoucher = returnController.select(selectedReturnVou, selectedFromDate, selectedToDate);
					
					Log.i("", "Return List by Voucher No. + Date: "+listVoucher.toString());
					
					saleReturnReportAdapter = new SaleReturnReportAdapter(SaleReturnReportActivity.this, listVoucher);
					lv_sale_return_report.setAdapter(saleReturnReportAdapter);
					
					GrandTotal();
					
					if (listVoucher.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(SaleReturnReportActivity.this);
						alert.setTitle("Info");
						alert.setMessage("No Item!");
						alert.show();
						alert.setCancelable(true);
						
						txt_grand_total.setText("0.00");
					}
				}

				//}
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(SaleReturnReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(SaleReturnReportActivity.this);

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
	
	private void GrandTotal()
	{		
			Integer grandTotal = 0; 
			
			for (int i = 0; i < listVoucher.size(); i++) {
				Integer total = Integer.valueOf(((SaleReturn) listVoucher.get(i)).getItemTotal());
				grandTotal += total;
			}
			
			txt_grand_total.setText(grandTotal+"");
	}
	
	public boolean checkFields() {
		if (autocom_return_vou_no.getText().toString().length() == 0) {
			autocom_return_vou_no.setError("Enter Return Voucher");
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//getVoucher();
		/*GrandTotal();
		
		if (listVoucher.size() == 0) {
			txt_grand_total.setText("0.00");
		}*/
	}
	
	
}

