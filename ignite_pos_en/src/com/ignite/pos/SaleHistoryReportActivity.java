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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.ignite.pos.adapter.SaleHistoryReportAdapter;
import com.ignite.pos.database.controller.SaleHistoryController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SaleHistory;
import com.ignite.pos.model.SaleVouncher;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class SaleHistoryReportActivity extends BaseSherlockActivity{
	private Button fromdate, todate, search;
	private String selectedSaleVou;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_sale_history_report;
	private SaleHistoryReportAdapter saleHistoryReportAdapter;
	private List<Object> listVoucher;
	private ActionBar actionBar;
	private TextView title;
	private String currentDate;
	private RelativeLayout add_layout;
	private AutoCompleteTextView autocom_sale_vou_no;
	private List<Object> listReturnVou;
	private List<Object> listSaleVouList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_history_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("Sale History Report");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		autocom_sale_vou_no = (AutoCompleteTextView)findViewById(R.id.autocom_sale_vou_no);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_sale_history_report = (ListView) findViewById(R.id.lv_sale_history_report);
		
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
		getSaleVoucherList();
		
	}
	
	private void getSaleVoucherList() {
		// TODO Auto-generated method stub
		dbManager = new SaleVouncherController(this);
		SaleVouncherController svControl = (SaleVouncherController)dbManager;
		listSaleVouList = new ArrayList<Object>();
		
		listSaleVouList.add(new SaleVouncher("All"));
		listSaleVouList.addAll(svControl.selectVouListGroupBy());
		
		Log.i("", "Sale Voucher List: "+listSaleVouList.toString());
		
		//Change List<Object> to String Array
		String[] vouArray = new String[listSaleVouList.size()];
		
		for (int i = 0; i < listSaleVouList.size(); i++) {
			
			SaleVouncher vouObj = (SaleVouncher)listSaleVouList.get(i);
			
			vouArray[i] = vouObj.getVid();  
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vouArray);
		autocom_sale_vou_no.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autocom_sale_vou_no.setThreshold(1);
	}

	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == search)
			{
				/*if (selectedSaleVou.equals("All") && fromdate.getText().toString().equals("From Date") && todate.getText().toString().equals("To Date")) {
					getVoucher();
				}else {*/
				
				if (checkFields()) {
					
					selectedSaleVou = autocom_sale_vou_no.getText().toString();
					
					dbManager = new SaleHistoryController(SaleHistoryReportActivity.this);
					SaleHistoryController shController = (SaleHistoryController) dbManager;
					listVoucher = new ArrayList<Object>();
					listVoucher = shController.selectByVouIdDate(selectedSaleVou, selectedFromDate, selectedToDate);
					
					List<Object> listSaleHistory = new ArrayList<Object>();
					
					for (int i = 0; i < listVoucher.size(); i++) {
						
						SaleHistory sh = (SaleHistory)listVoucher.get(i);
						
						if (sh.getOldQty() != sh.getNewQty()) {
							listSaleHistory.add(sh);
						}
					}
					
					saleHistoryReportAdapter = new SaleHistoryReportAdapter(SaleHistoryReportActivity.this, listSaleHistory);
					lv_sale_history_report.setAdapter(saleHistoryReportAdapter);
					
					if (listVoucher.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(SaleHistoryReportActivity.this);
						alert.setTitle("Info");
						alert.setMessage("No Item!");
						alert.show();
						alert.setCancelable(true);
						
					}
				}

				//}
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(SaleHistoryReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(SaleHistoryReportActivity.this);

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
	
	public boolean checkFields() {
		if (autocom_sale_vou_no.getText().toString().length() == 0) {
			autocom_sale_vou_no.setError("Enter Sale Voucher");
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


