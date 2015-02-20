package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.CreditBuyerReportAdapter;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.CreditBuyerController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Credit;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;


public class CreditBuyerReportActivity extends SherlockActivity{
	private AutoCompleteTextView autocom_buyer_name;
	private Button fromdate, todate, search;
	private String selectedBuyerName;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_credit_buyer_report;
	private CreditBuyerReportAdapter creditAdapter;
	private List<Object> buyerList;
	private List<Object> creditBuyerList;
	private ActionBar actionBar;
	private TextView title;
	private String currentDate;
	private View btn_print;
	private List<Object> listCredit;
	private TextView txt_credit_total;
	private TextView txt_credit_paid_total;
	private TextView txt_credit_left_total;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_buyer_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_report);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Ledger Report");
		title.setText("အေႂကြးစာရင္း မွတ္တမ္း (၀ယ္သူ)");
		btn_print = (Button) actionBar.getCustomView().findViewById(R.id.btn_print);
		btn_print.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		autocom_buyer_name = (AutoCompleteTextView)findViewById(R.id.autocom_buyer_name);
		autocom_buyer_name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ZawgyiOne2008.ttf"));
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_credit_buyer_report = (ListView) findViewById(R.id.lv_credit_buyer_report);
		txt_credit_total = (TextView)findViewById(R.id.txt_credit_total);
		txt_credit_paid_total = (TextView)findViewById(R.id.txt_credit_paid_total);
		txt_credit_left_total = (TextView)findViewById(R.id.txt_credit_left_total);
		
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
		//Buyer Name AutoComplete
		getBuyerName();
		
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
		//GrandTotal();
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v == search)
			{
				if (checkFields()) {
					
					selectedBuyerName = autocom_buyer_name.getText().toString();
					
					//Get Data from Credit-Buyer Table
					dbManager = new CreditBuyerController(CreditBuyerReportActivity.this);
					CreditBuyerController creditControl = (CreditBuyerController)dbManager;
					creditBuyerList = new ArrayList<Object>();
					creditBuyerList = creditControl.select(selectedBuyerName, selectedFromDate, selectedToDate);
					//creditBuyerList = creditControl.select();
					
					Log.i("", "Credit Buyer list: "+creditBuyerList.toString());
					
					if (creditBuyerList != null && creditBuyerList.size() > 0) {
						creditAdapter = new CreditBuyerReportAdapter(CreditBuyerReportActivity.this, creditBuyerList);
						lv_credit_buyer_report.setAdapter(creditAdapter);
						
						grandTotal();
					}else {
						alertDialog("No Info");
						lv_credit_buyer_report.setAdapter(null);
						txt_credit_total.setText("0");
						txt_credit_paid_total.setText("0");
						txt_credit_left_total.setText("0");
					}
					
					
				}
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(CreditBuyerReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(CreditBuyerReportActivity.this);

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
			if (v == btn_print) {
			/*	SaveFileDialog fileDialog = new SaveFileDialog(CreditBuyerReportActivity.this);
		        fileDialog.setCallbackListener(new SaveFileDialog.Callback() {
					
					public void onCancel() {
						// TODO Auto-generated method stub
					}

					public void onSave(String filename, boolean PDFChecked,
							boolean ExcelChecked) {
						// TODO Auto-generated method stub
						if(PDFChecked){
							//new SeatbyAgentPDFUtility(seatReports).createPdf(filename);
						}
						if(ExcelChecked){
							
							if (listCredit != null && listCredit.size() > 0) {
								for (int j = 0; j < listCredit.size(); j++) {
									
									Ledger ledger = (Ledger) listCredit.get(j);
									
									String ledgerDate = ledger.getDate();
									//Split sale date 
									String[] parts = ledgerDate.split("-");
									String year = parts[0]; 
									String month = parts[1];
									String day = parts[2];
									
									String formatedDate = day+"-"+month+"-"+year;
									
									((Ledger)listCredit.get(j)).setDate(formatedDate);
								}
							}
							
							List<String> searchInfoList = new ArrayList<String>();
							searchInfoList.add(selectedBuyerName);
							searchInfoList.add(dmyDateFormat(selectedFromDate));
							searchInfoList.add(dmyDateFormat(selectedToDate));
							
							if (listCredit != null && listCredit.size() > 0) {
								new LedgerReportExcelUtility(listCredit, filename, searchInfoList).write();
								SKToastMessage.showMessage(LedgerReportActivity.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
							}else {
								alertDialog("No Data Yet");
							}
						}
					}
				});
		        fileDialog.show();
		        return;*/
			}
		}
	};
	
	private void getBuyerName()
	{
		dbManager = new BuyerController(this);
		BuyerController buyerControl = (BuyerController)dbManager;
		buyerList = new ArrayList<Object>();
		
		buyerList.add(new Buyer("All"));
		buyerList.addAll(buyerControl.select());
		
		Log.i("", "Buyer Name List: "+buyerList.toString());
		
		//Change List<Object> to String Array
		String[] buyerArray = new String[buyerList.size()];
		
		for (int i = 0; i < buyerList.size(); i++) {
			
			Buyer buyerObj = (Buyer)buyerList.get(i);
			
			buyerArray[i] = buyerObj.getBuyerName();  
			
			Log.i("", "Buyer Name Array: "+buyerArray[i]);
		}

		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, R.layout.custom_autocomplete_view, buyerArray);
		autocom_buyer_name.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autocom_buyer_name.setThreshold(1);
		
	}
	
	private void grandTotal() {
		// TODO Auto-generated method stub
		String voucherNo = "";
			
		Integer credit_total = 0;
		Integer credit_paid_total = 0;
		Integer credit_left_total = 0;
		
		for (int i = 0; i < creditBuyerList.size(); i++) {
			Credit credit = (Credit)creditBuyerList.get(i);
			
			if (!credit.getSalevoucher_id().equals(voucherNo)) {
				voucherNo = credit.getSalevoucher_id();
				credit_total += credit.getCreditTotal();
				credit_paid_total += credit.getCreditPaidAmount();
			}else {
				credit_paid_total += credit.getCreditPaidAmount();
			}
		}
		
		credit_left_total = credit_total - credit_paid_total; 
		
		txt_credit_total.setText(credit_total+"");
		txt_credit_paid_total.setText(credit_paid_total+"");
		txt_credit_left_total.setText(credit_left_total+"");
	}
	
	public boolean checkFields() {
		if (autocom_buyer_name.getText().toString().length() == 0) {
			autocom_buyer_name.setError("Enter Buyer Name");
			return false;
		}
		
		return true;
	}
	
	private void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(CreditBuyerReportActivity.this);
		alert.setTitle("Info");
		alert.setMessage(message+"!");
		alert.show();
		alert.setCancelable(true);
	}
	
	private String dmyDateFormat(String date) {
		// TODO Auto-generated method stub
		String[] parts = date.split("-");
		String year = parts[0]; 
		String month = parts[1];
		String day = parts[2];
		
		String formatedDate = day+"-"+month+"-"+year;
		
		return formatedDate;
	}
}

