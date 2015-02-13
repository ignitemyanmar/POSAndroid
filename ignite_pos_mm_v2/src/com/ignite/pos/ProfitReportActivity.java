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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.ProfitReportAdapter;
import com.ignite.pos.application.ProfitReportExcelUtility;
import com.ignite.pos.application.StockReportExcelUtility;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.spSalePerson;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;

public class ProfitReportActivity extends SherlockActivity{
	
	private AutoCompleteTextView autocom_item_code;
	private Button fromdate, todate, search;
	private spSalePerson sale_person;
	private List<String> weeklyList;
	private List<String> monthlyList;
	private String selectedWeek;
	private String selectedDate;
	private String selectedMonth;
	private String selectedItemCode;
	private DatabaseManager dbManager;
	private ListView lv_profit_report;
	private ProfitReportAdapter profitReportListViewAdapter;
	private List<Object> listProfit;
	private List<Object> itemInfo;
	private ActionBar actionBar;
	private TextView title, txt_item_code, txt_item_name;
	private Button change_mode;
	private TextView txt_grand_total;
	private String currentDate;
	private String selectedFromDate;
	private String selectedToDate;
	private LinearLayout lyheader;
	private List<Object> listItemCode;
	private Button btn_print;
	private List<Object> listDiscount;
	private LinearLayout layout_detail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profit_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_report);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Profit Report");
		title.setText("အရံႈး/အျမတ္မွတ္တမ္း");
		btn_print = (Button) actionBar.getCustomView().findViewById(R.id.btn_print);
		btn_print.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		autocom_item_code = (AutoCompleteTextView)findViewById(R.id.autocom_item_code);
		autocom_item_code.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ZawgyiOne2008.ttf"));
		fromdate  = (Button)findViewById(R.id.btn_from_date);
		todate = (Button)findViewById(R.id.btn_to_date);
		search = (Button)findViewById(R.id.btnSearch);
		lyheader = (LinearLayout)findViewById(R.id.lyheader);
		txt_item_code = (TextView) findViewById(R.id.txt_item_code);
		txt_item_name = (TextView) findViewById(R.id.txt_item_name);
		lv_profit_report = (ListView) findViewById(R.id.lv_profit_report);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		layout_detail = (LinearLayout)findViewById(R.id.layout_detail);
		
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
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
		
		getItemCode();
		
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v == search)
			{
				if (checkFields()) {
					
					selectedItemCode = autocom_item_code.getText().toString();
					
					dbManager = new ProfitController(ProfitReportActivity.this);
					ProfitController profitController = (ProfitController) dbManager;
					listProfit = new ArrayList<Object>();
					listProfit = profitController.selectByItemName(selectedItemCode, selectedFromDate, selectedToDate);
					
					//Get Discount Total for each day
					listDiscount = new ArrayList<Object>();
					
					for (int i = 0; i < listProfit.size(); i++) {
						
						Profit profit = (Profit)listProfit.get(i);
						
						listDiscount = profitController.selectByDate(profit.getDate());
						
						Integer discountTotal = 0;
						
						for (int j = 0; j < listDiscount.size(); j++) {
							
							Profit p = (Profit)listDiscount.get(j);
							discountTotal += p.getDiscount();
							
							Log.i("", "1 day - Each Voucher's Discount: "+p.getDiscount());
						}
						
						Log.i("", "1 day Discount Total: "+discountTotal);
						
						profit.setTotalDiscount(discountTotal);
					}//End each day's discount total
					
					Log.i("", "List Profit : "+listProfit.toString());
					
					if (listProfit.size() > 0 && listProfit != null) {
						
						if (selectedItemCode.toLowerCase().equals("all")) {
							
							lyheader.setVisibility(View.GONE);
							layout_detail.setVisibility(View.VISIBLE);
							
							profitReportListViewAdapter = new ProfitReportAdapter(ProfitReportActivity.this, listProfit, selectedItemCode);
							lv_profit_report.setAdapter(profitReportListViewAdapter);
							
						}else {
								
								Profit profit = (Profit)listProfit.get(0);
								
								//Get Item Information 
								dbManager = new ItemListController(ProfitReportActivity.this);
								ItemListController control = (ItemListController)dbManager;
								itemInfo = new ArrayList<Object>();
								itemInfo = control.select(profit.getItemId());
								
								lyheader.setVisibility(View.VISIBLE);
								layout_detail.setVisibility(View.GONE);
								
								if (itemInfo.size() > 0 && itemInfo != null) {
									
									txt_item_code.setText(((ItemList)itemInfo.get(0)).getItemId());
									txt_item_name.setText(((ItemList)itemInfo.get(0)).getItemName());
									
									profitReportListViewAdapter = new ProfitReportAdapter(ProfitReportActivity.this, listProfit, selectedItemCode);
									lv_profit_report.setAdapter(profitReportListViewAdapter);
									
								}else {
									lyheader.setVisibility(View.GONE);
								}
						}
						
						GrandTotal(selectedItemCode);
						
				}else {
					
					AlertDialog.Builder alert2 = new AlertDialog.Builder(ProfitReportActivity.this);
					alert2.setTitle("Info");
					alert2.setMessage("No Info!");
					alert2.show();
					alert2.setCancelable(true);
					
					Log.i("", "Hellooooooooooooooooooooo !! ");
					
					lv_profit_report.setAdapter(null);
					lyheader.setVisibility(View.GONE);
					//notifyDataSetChanged();
					txt_grand_total.setText("0.00");
					
				}//End If ==> No data in Profit DB
						
			  }//End If ==> Check Field
				
			}//End If ==> Search Button
			
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(ProfitReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(ProfitReportActivity.this);

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
				SaveFileDialog fileDialog = new SaveFileDialog(ProfitReportActivity.this);
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
							
							if (listProfit != null && listProfit.size() > 0) {
								for (int j = 0; j < listProfit.size(); j++) {
									
									Profit profit = (Profit) listProfit.get(j);
									
									String profitDate = profit.getDate();
									//Split sale date 
									String[] parts = profitDate.split("-");
									String year = parts[0]; 
									String month = parts[1];
									String day = parts[2];
									
									String formatedDate = day+"-"+month+"-"+year;
									
									((Profit)listProfit.get(j)).setDate(formatedDate);
								}
							}

							List<String> searchInfoList = new ArrayList<String>();
							searchInfoList.add(selectedItemCode);
							searchInfoList.add(dmyDateFormat(selectedFromDate));
							searchInfoList.add(dmyDateFormat(selectedToDate));
							
							if (listProfit != null && listProfit.size() > 0) {
								new ProfitReportExcelUtility(listProfit, filename, searchInfoList).write();
								SKToastMessage.showMessage(ProfitReportActivity.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
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
	
	private void getItemCode()
	{
		dbManager = new ItemListController(this);
		ItemListController itemControl = (ItemListController)dbManager;
		listItemCode = new ArrayList<Object>();
		
		listItemCode.add(new ItemList("All"));
		listItemCode.addAll(itemControl.select());
		
		Log.i("", "Item Code List: "+listItemCode.toString());
		
		//Change List<Object> to String Array
		String[] itemArray = new String[listItemCode.size()];
		
		for (int i = 0; i < listItemCode.size(); i++) {
			
			ItemList itemObj = (ItemList)listItemCode.get(i);
			itemArray[i] = itemObj.getItemName(); 
			
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, R.layout.custom_autocomplete_view, itemArray);
		autocom_item_code.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autocom_item_code.setThreshold(1);
		
	}	
	
	private void GrandTotal(String selectedItemCode)
	{		
			Integer grandTotal = 0; 

			for (int i = 0; i < listProfit.size(); i++) {
				Profit profit = (Profit)listProfit.get(i);
				
				if (selectedItemCode.toLowerCase().equals("all")) {
					Integer profitFinal = profit.getTotalProfit() - profit.getTotalDiscount();
					grandTotal += profitFinal;
				}else {
					grandTotal += profit.getTotalProfit();
				}
			}
			
			txt_grand_total.setText(grandTotal+"");
	}
	
	public boolean checkFields() {
		if (autocom_item_code.getText().toString().length() == 0) {
			autocom_item_code.setError("Enter Item Code");
			return false;
		}
		
		return true;
	}
	
	private void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(ProfitReportActivity.this);
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
