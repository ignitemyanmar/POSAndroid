package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.BusTicketReportAdapter;
import com.ignite.pos.database.controller.BusTicketSaleController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.BusTicketSale;
import com.ignite.pos.model.ItemList;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;

@SuppressLint("ShowToast") public class BusTicketSaleReportActivity extends SherlockActivity{
	
	private AutoCompleteTextView autocom_operator_name;
	private Button fromdate, todate, search;
	private String selectedOperatorName;
	private DatabaseManager dbManager;
	private ListView lv_bus_ticket_report;
	private BusTicketReportAdapter adapter;
	private List<Object> listBusTicket;
	private ActionBar actionBar;
	private TextView title;
	private TextView txt_grand_total;
	private String currentDate;
	private String selectedFromDate;
	private String selectedToDate;
	private List<Object> listItemCode;
	private Button btn_print;
	private List<Object> busTicketList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busticket_sale_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_report);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Profit Report");
		title.setText("ဘတ္ (စ္) ကား အ ေရာင္းမွတ္တမ္း");
		btn_print = (Button) actionBar.getCustomView().findViewById(R.id.btn_print);
		btn_print.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		autocom_operator_name = (AutoCompleteTextView)findViewById(R.id.autocom_operator_name);
		autocom_operator_name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ZawgyiOne2008.ttf"));
		fromdate  = (Button)findViewById(R.id.btn_from_date);
		todate = (Button)findViewById(R.id.btn_to_date);
		search = (Button)findViewById(R.id.btnSearch);
		lv_bus_ticket_report = (ListView) findViewById(R.id.lv_bus_ticket_report);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		
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
		
		//Get Data from Bus Ticket App & Save 
		//saveBusTicket();
		getBusTicket();
		
		//Get Auto Operator Name
		//getOperatorName();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void getBusTicket() {
		// TODO Auto-generated method stub
		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		
		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				//handleSendText(intent);
				saveBusTicket(intent);
			}else {
				Log.i("", "Data is not Text!");
			}
		}else {
			Log.i("", "Data Can't receive!");
		}
	}
	
	private void saveBusTicket(Intent intent) {
		// TODO Auto-generated method stub
		
		Bundle bundle = intent.getExtras();
		
		if (bundle != null) {
			String BarcodeNo = bundle.getString("sale_order_no");
			String CustomerName = bundle.getString("CustomerName");
			String OperatorName = bundle.getString("Operator_Name");
			String Trip = bundle.getString("Bus_Trip");
			String Trip_Date = bundle.getString("Trip_Date");
			String Trip_Time = bundle.getString("Trip_Time");
			String Bus_Class = bundle.getString("Bus_Class");
			String Selected_Seats = bundle.getString("Selected_Seats");
			String SeatCount = bundle.getString("SeatCount");
			String Price = bundle.getString("Price");
			String ConfirmDate = bundle.getString("ConfirmDate");
			
			Log.i("", "rBarcode: "+BarcodeNo+", rCustomerName: "+CustomerName+", rOperator: "+OperatorName);
			
			busTicketList = new ArrayList<Object>();
		    busTicketList.add(new BusTicketSale(BarcodeNo, CustomerName, OperatorName, Trip
		    		, Trip_Date, Trip_Time, Bus_Class, Selected_Seats, Integer.valueOf(SeatCount), Integer.valueOf(Price), ConfirmDate));
		}

        Log.i("", "Bus Ticket List Recevie: "+busTicketList.toString());
        
        if (busTicketList != null && busTicketList.size() > 0) {
        	dbManager = new BusTicketSaleController(BusTicketSaleReportActivity.this);
        	BusTicketSaleController busControl = (BusTicketSaleController)dbManager;
    		busControl.save(busTicketList);
    		
    		Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
		}
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v == search)
			{
				if (checkFields()) {
					
					selectedOperatorName = autocom_operator_name.getText().toString();
					
					dbManager = new BusTicketSaleController(BusTicketSaleReportActivity.this);
					BusTicketSaleController control = (BusTicketSaleController) dbManager;
					listBusTicket = new ArrayList<Object>();
					//listBusTicket = control.select(selectedOperatorName, selectedFromDate, selectedToDate);
					listBusTicket = control.select();
					
					Log.i("", "List Bus Ticket: "+listBusTicket.toString());
					
					if (listBusTicket.size() > 0 && listBusTicket != null) {
						adapter = new BusTicketReportAdapter(BusTicketSaleReportActivity.this, listBusTicket);
						lv_bus_ticket_report.setAdapter(adapter);
					}else {
						alertDialog("No Sale!");
						lv_bus_ticket_report.setAdapter(null);
						txt_grand_total.setText("0.00");
					}
						
					GrandTotal();
				}
						
			  }//End If ==> No data in Profit DB
				
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(BusTicketSaleReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(BusTicketSaleReportActivity.this);

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
				/*SaveFileDialog fileDialog = new SaveFileDialog(BusTicketSaleReportActivity.this);
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
							
							if (listBusTicket != null && listBusTicket.size() > 0) {
								for (int j = 0; j < listBusTicket.size(); j++) {
									
									Profit profit = (Profit) listBusTicket.get(j);
									
									String profitDate = profit.getDate();
									//Split sale date 
									String[] parts = profitDate.split("-");
									String year = parts[0]; 
									String month = parts[1];
									String day = parts[2];
									
									String formatedDate = day+"-"+month+"-"+year;
									
									((Profit)listBusTicket.get(j)).setDate(formatedDate);
								}
							}

							List<String> searchInfoList = new ArrayList<String>();
							searchInfoList.add(selectedOperatorName);
							searchInfoList.add(dmyDateFormat(selectedFromDate));
							searchInfoList.add(dmyDateFormat(selectedToDate));
							
							if (listBusTicket != null && listBusTicket.size() > 0) {
								new ProfitReportExcelUtility(listBusTicket, filename, searchInfoList).write();
								SKToastMessage.showMessage(BusTicketSaleReportActivity.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
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
		autocom_operator_name.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autocom_operator_name.setThreshold(1);
		
	}	
	
	private void GrandTotal()
	{		
			Integer grandTotal = 0; 

			for (int i = 0; i < listBusTicket.size(); i++) {
				BusTicketSale ticketSale = (BusTicketSale)listBusTicket.get(i);
				grandTotal += ticketSale.getSeatCount() * ticketSale.getSeatPrice();
			}
			
			txt_grand_total.setText(grandTotal+"");
	}
	
	public boolean checkFields() {
		if (autocom_operator_name.getText().toString().length() == 0) {
			autocom_operator_name.setError("Enter Operator Name");
			return false;
		}
		
		return true;
	}
	
	private void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(BusTicketSaleReportActivity.this);
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
