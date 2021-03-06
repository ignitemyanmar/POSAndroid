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
import com.ignite.pos.application.SaleReturnReportExcelUtility;
import com.ignite.pos.application.StockReportExcelUtility;
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
import com.smk.skalertmessage.SKToastMessage;

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
	private Button btn_print;
	private List<Object> listItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_return_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_report);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Sale Return Report");
		title.setText("ျပန္၀င္ ပစၥည္းမွတ္တမ္း");
		btn_print = (Button) actionBar.getCustomView().findViewById(R.id.btn_print);
		btn_print.setOnClickListener(clickListener);
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
					
					//Get Item Name by Item id
					for (int i = 0; i < listVoucher.size(); i++) {
						
						dbManager = new ItemListController(getApplicationContext());
						ItemListController itemControl = (ItemListController) dbManager;
						listItem = new ArrayList<Object>();
						listItem = itemControl.select(((SaleReturn)listVoucher.get(i)).getItemid());
						
						if (listItem != null && listItem.size() > 0) {
							ItemList iL = (ItemList)listItem.get(0);
							((SaleReturn)listVoucher.get(i)).setItemName(iL.getItemName());
						}
					}
					
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
			if (v == btn_print) {
				SaveFileDialog fileDialog = new SaveFileDialog(SaleReturnReportActivity.this);
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
							
							if (listVoucher != null && listVoucher.size() > 0) {
								for (int j = 0; j < listVoucher.size(); j++) {
									
									SaleReturn sr = (SaleReturn) listVoucher.get(j);
									
									String saleReturnDate = sr.getReturnDate();
									//Split sale date 
									String[] parts = saleReturnDate.split("-");
									String year = parts[0]; 
									String month = parts[1];
									String day = parts[2];
									
									String formatedDate = day+"-"+month+"-"+year;
									
									((SaleReturn)listVoucher.get(j)).setReturnDate(formatedDate);
								}
							}

							List<String> searchInfoList = new ArrayList<String>();
							searchInfoList.add(selectedReturnVou);
							searchInfoList.add(dmyDateFormat(selectedFromDate));
							searchInfoList.add(dmyDateFormat(selectedToDate));
							
							if (listVoucher != null && listVoucher.size() > 0) {
								new SaleReturnReportExcelUtility(listVoucher, filename, searchInfoList).write();
								SKToastMessage.showMessage(SaleReturnReportActivity.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
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
	
	private void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(SaleReturnReportActivity.this);
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

