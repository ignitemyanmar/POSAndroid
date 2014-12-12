
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
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.LedgerReportListViewAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class LedgerReportActivity extends SherlockActivity{
	private AutoCompleteTextView autocom_item_code;
	private Button fromdate, todate, search;
	private String selectedItemCode;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_ledger_report;
	private LedgerReportListViewAdapter ledgerAdapter;
	private List<Object> listItemCode;
	private List<Object> ledgerList;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	private String currentDate;
	private RelativeLayout layout_add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ledger_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		layout_add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		layout_add_new.setVisibility(View.GONE);
		title.setText("Ledger Report");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		autocom_item_code = (AutoCompleteTextView)findViewById(R.id.autocom_item_code);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_ledger_report = (ListView) findViewById(R.id.lv_ledger_report);
		
		
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
		//Item Code AutoComplete
		getItemCode();
		
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
					
					selectedItemCode = autocom_item_code.getText().toString();
					
					//Get Data from Ledger Table
					dbManager = new LedgerController(LedgerReportActivity.this);
					LedgerController ledgerControl = (LedgerController)dbManager;
					ledgerList = new ArrayList<Object>();
					ledgerList = ledgerControl.select(selectedItemCode, selectedFromDate, selectedToDate);
					
					Log.i("", "Ledger list: "+ledgerList.toString());
					
					List<Object> listLedger = new ArrayList<Object>();
					
					for (int i = 0; i < ledgerList.size(); i++) {
						Ledger ledger = (Ledger)ledgerList.get(i);
						
						Integer checkTransaction = ledger.getPurchaseQty() + ledger.getSaleQty();
						
						if (checkTransaction > 0) {
							listLedger.add(ledger);
						}
					}
					
					Log.i("", "Ledger List to show: "+listLedger.toString());
					
					ledgerAdapter = new LedgerReportListViewAdapter(LedgerReportActivity.this, listLedger);
					lv_ledger_report.setAdapter(ledgerAdapter);
					
					if (ledgerList.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(LedgerReportActivity.this);
						alert.setTitle("Info");
						alert.setMessage("No Transition!");
						alert.show();
						alert.setCancelable(true);
					}
				}
				
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(LedgerReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(LedgerReportActivity.this);

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
			
			itemArray[i] = itemObj.getItemId();  
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemArray);
		autocom_item_code.setAdapter(adapter);
		
		// specify the minimum type of characters before drop-down list is shown
		autocom_item_code.setThreshold(1);
		
	}
	
	public boolean checkFields() {
		if (autocom_item_code.getText().toString().length() == 0) {
			autocom_item_code.setError("Enter Item Code");
			return false;
		}
		
		return true;
	}
}

