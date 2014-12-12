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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.LedgerReportListViewAdapter;
import com.ignite.pos.adapter.SlowMovingReportListViewAdapter;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class SlowMovingReportActivity extends SherlockActivity{
	private EditText editText_maxi_Qty;
	private Button fromdate, todate, search;
	private Integer selectedMaxiQty;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_slow_moving_report;
	private SlowMovingReportListViewAdapter smAdapter;
	private List<Object> itemList;
	private ActionBar actionBar;
	private TextView title;
	private String currentDate;
	private RelativeLayout layout_add_new;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slow_moving_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		layout_add_new = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		layout_add_new.setVisibility(View.GONE);
		title.setText("Slow Moving Report");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		editText_maxi_Qty = (EditText)findViewById(R.id.editText_maximum_Qty);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		lv_slow_moving_report = (ListView) findViewById(R.id.lv_slow_moving_report);
		
		
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
		//GrandTotal();
	}
	
	

	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == search)
			{
				if (checkFields()) {
					
					selectedMaxiQty = Integer.valueOf(editText_maxi_Qty.getText().toString());
					
					/*//Get Data ( >maxi Qty)  from Sale Table
					dbManager = new SaleVouncherController(SlowMovingReportActivity.this);
					SaleVouncherController saleControl = (SaleVouncherController)dbManager;
					saleList = new ArrayList<Object>();
					saleList = saleControl.selectRecordByMaximumQty(selectedMaxiQty, selectedFromDate, selectedToDate);
					
					Log.i("", "Sale list: "+saleList.toString());*/
					
					//Get Data ( < sold Qty ) from Item Table
					dbManager = new ItemListController(SlowMovingReportActivity.this);
					ItemListController itemControl = (ItemListController)dbManager;
					itemList = new ArrayList<Object>();
					itemList = itemControl.selectRecordSlowMoving(selectedFromDate, selectedToDate, selectedMaxiQty);
					
					if(itemList != null){
						
						smAdapter = new SlowMovingReportListViewAdapter(SlowMovingReportActivity.this, itemList);
						
						lv_slow_moving_report.setAdapter(smAdapter);
					}
					
					if (itemList == null || itemList.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(SlowMovingReportActivity.this);
						alert.setTitle("Info");
						alert.setMessage("No Info!");
						alert.show();
						alert.setCancelable(true);
					}
				}
				
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(SlowMovingReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(SlowMovingReportActivity.this);

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
		if (editText_maxi_Qty.getText().toString().length() == 0) {
			editText_maxi_Qty.setError("Enter Maximum Qty");
			return false;
		}
		
		return true;
	}
}

