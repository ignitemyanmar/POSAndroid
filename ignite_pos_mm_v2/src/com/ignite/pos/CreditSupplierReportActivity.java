package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
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
import com.ignite.pos.adapter.CreditSupplierReportAdapter;
import com.ignite.pos.application.CreditBuyerReportExcelUtility;
import com.ignite.pos.application.CreditSupplierReportExcelUtility;
import com.ignite.pos.database.controller.CreditSupplierController;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Credit;
import com.ignite.pos.model.CreditSupplier;
import com.ignite.pos.model.Supplier;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;

@SuppressLint("SimpleDateFormat") public class CreditSupplierReportActivity extends SherlockActivity{
	private Button fromdate, todate, search;
	private String selectedSupplierName;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private CreditSupplierReportAdapter creditAdapter;
	private List<Object> supplierList;
	private List<Object> creditsupplierList;
	private ActionBar actionBar;
	private TextView title;
	private String currentDate;
	private View btn_print;
	private TextView txt_credit_total;
	private TextView txt_credit_paid_total;
	private TextView txt_credit_left_total;
	private AutoCompleteTextView autocom_supplier_name;
	private ListView lv_credit_supplier_report;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_supplier_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_report);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Ledger Report");
		title.setText("အေႂကြးစာရင္း မွတ္တမ္း (လကၠားဆုိင္ သုိ႔ ေပးရန္ အေႂကြး)");
		btn_print = (Button) actionBar.getCustomView().findViewById(R.id.btn_print);
		btn_print.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		autocom_supplier_name = (AutoCompleteTextView)findViewById(R.id.autocom_supplier_name);
		autocom_supplier_name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ZawgyiOne2008.ttf"));
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_credit_supplier_report = (ListView) findViewById(R.id.lv_credit_supplier_report);
		txt_credit_total = (TextView)findViewById(R.id.txt_credit_total);
		txt_credit_paid_total = (TextView)findViewById(R.id.txt_credit_paid_total);
		txt_credit_left_total = (TextView)findViewById(R.id.txt_credit_left_total);
		
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
		//Supplier Name AutoComplete
		getSupplierName();
		
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
					
					selectedSupplierName = autocom_supplier_name.getText().toString();
					
					//Get Data from Credit-Supplier Table
					dbManager = new CreditSupplierController(CreditSupplierReportActivity.this);
					CreditSupplierController creditControl = (CreditSupplierController)dbManager;
					creditsupplierList = new ArrayList<Object>();
					creditsupplierList = creditControl.select(selectedSupplierName, selectedFromDate, selectedToDate);
					//creditsupplierList = creditControl.select();
					
					Log.i("", "Credit Supplier list: "+creditsupplierList.toString());
					
					if (creditsupplierList != null && creditsupplierList.size() > 0) {
						creditAdapter = new CreditSupplierReportAdapter(CreditSupplierReportActivity.this, creditsupplierList);
						lv_credit_supplier_report.setAdapter(creditAdapter);
						
						grandTotal();
					}else {
						alertDialog("No Info");
						lv_credit_supplier_report.setAdapter(null);
						txt_credit_total.setText("0");
						txt_credit_paid_total.setText("0");
						txt_credit_left_total.setText("0");
					}
				}
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(CreditSupplierReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(CreditSupplierReportActivity.this);

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
				SaveFileDialog fileDialog = new SaveFileDialog(CreditSupplierReportActivity.this);
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
							
							if (creditsupplierList != null && creditsupplierList.size() > 0) {
								for (int j = 0; j < creditsupplierList.size(); j++) {
									
									CreditSupplier credit = (CreditSupplier) creditsupplierList.get(j);
									
									String creditDate = credit.getDate();
									//Split sale date 
									String[] parts = creditDate.split("-");
									String year = parts[0]; 
									String month = parts[1];
									String day = parts[2];
									
									String formatedDate = day+"-"+month+"-"+year;
									
									((CreditSupplier)creditsupplierList.get(j)).setDate(formatedDate);
								}
							}
							
							List<String> searchInfoList = new ArrayList<String>();
							searchInfoList.add(selectedSupplierName);
							searchInfoList.add(dmyDateFormat(selectedFromDate));
							searchInfoList.add(dmyDateFormat(selectedToDate));
							
							if (creditsupplierList != null && creditsupplierList.size() > 0) {
								new CreditSupplierReportExcelUtility(creditsupplierList, filename, searchInfoList).write();
								SKToastMessage.showMessage(CreditSupplierReportActivity.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
							}else {
								alertDialog("No Data Yet");
							}
						}
					}
				});
		        fileDialog.show();
		        return;
			}
		}
	};
	
	private void getSupplierName()
	{
		dbManager = new SupplierController(this);
		SupplierController supplierControl = (SupplierController)dbManager;
		supplierList = new ArrayList<Object>();
		
		supplierList.add(new Supplier("All"));
		supplierList.addAll(supplierControl.select());
		
		Log.i("", "Supplier Name List: "+supplierList.toString());
		
		//Change List<Object> to String Array
		String[] supplierArray = new String[supplierList.size()];
		
		for (int i = 0; i < supplierList.size(); i++) {
			
			Supplier supplierObj = (Supplier)supplierList.get(i);
			
			supplierArray[i] = supplierObj.getSupCoName();  
			
			Log.i("", "Supplier Name Array: "+supplierArray[i]);
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, R.layout.custom_autocomplete_view, supplierArray);
		autocom_supplier_name.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autocom_supplier_name.setThreshold(1);
		
	}
	
	private void grandTotal() {
		// TODO Auto-generated method stub
		String voucherNo = "";
			
		Integer credit_total = 0;
		Integer credit_paid_total = 0;
		Integer credit_left_total = 0;
		
		for (int i = 0; i < creditsupplierList.size(); i++) {
			CreditSupplier credit = (CreditSupplier)creditsupplierList.get(i);
			
			if (!credit.getPurchaseVoucherID().equals(voucherNo)) {
				voucherNo = credit.getPurchaseVoucherID();
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
		if (autocom_supplier_name.getText().toString().length() == 0) {
			autocom_supplier_name.setError("Enter Supplier Name");
			return false;
		}
		
		return true;
	}
	
	private void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(CreditSupplierReportActivity.this);
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

