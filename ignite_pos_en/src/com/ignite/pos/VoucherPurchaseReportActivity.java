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
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.VoucherPurchaseReportLvAdapter;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.PurchaseVoucher;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

public class VoucherPurchaseReportActivity extends SherlockActivity{
	//private Spinner sp_voucher;
	private AutoCompleteTextView autocomplete_voucher_no;
	private Button fromdate, todate, search;
	private List<Object> voucherList;
	private String selectedVoucher;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_purchase_report;
	private TextView txt_grand_total, txt_voucher_no, txt_date, txt_supplier;
	private VoucherPurchaseReportLvAdapter lvAdapter;
	private List<Object> listVoucher;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher_purchase_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.btnLogin);
		title.setText("Voucher Purchase Report");
		title.setBackgroundResource(R.color.black);
		change_mode = (Button)actionBar.getCustomView().findViewById(R.id.btnChange_mode);
		change_mode.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		
		autocomplete_voucher_no = (AutoCompleteTextView) findViewById(R.id.autocomplete_voucher_no);
		//sp_voucher = (Spinner)findViewById(R.id.sp_voucher);
		//fromdate  = (Button)findViewById(R.id.btnFromDate);
		//todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		txt_voucher_no = (TextView)findViewById(R.id.txt_vou_no);
		txt_date = (TextView)findViewById(R.id.txt_date);
		txt_supplier = (TextView)findViewById(R.id.txt_supplier);
		lv_purchase_report = (ListView) findViewById(R.id.lv_purchase_report);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		
		listVoucher = new ArrayList<Object>();
		
		//sp_voucher.setOnItemSelectedListener(voucherClickListener);
		//fromdate.setOnClickListener(clickListener);
		//todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		
		getVoucher();
		
		
		//getAllVoucher();
		//GrandTotal();
		
		if (listVoucher.size() == 0) {
			txt_grand_total.setText("0.00");
		}
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == search)
			{
				if (checkFields()) {
					selectedVoucher = autocomplete_voucher_no.getText().toString();
					
					dbManager = new PurchaseVoucherController(VoucherPurchaseReportActivity.this);
					PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
					listVoucher = new ArrayList<Object>();
					listVoucher = pvController.select(selectedVoucher);
					
					Log.i("", "List Voucher: "+listVoucher.toString());
					
					lvAdapter = new VoucherPurchaseReportLvAdapter(VoucherPurchaseReportActivity.this, listVoucher);
					lv_purchase_report.setAdapter(lvAdapter);
					
					txt_voucher_no.setText("Vou No: "+selectedVoucher);
					
					if (listVoucher.size() > 0) {
						PurchaseVoucher purchaseVoucherItem = (PurchaseVoucher) listVoucher.get(0);
						txt_date.setText("Date: "+purchaseVoucherItem.getVdate());
						txt_supplier.setText("Supplier: "+purchaseVoucherItem.getSupplierName());
					}
					
					GrandTotal();
					
					if (listVoucher.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(VoucherPurchaseReportActivity.this);
						alert.setTitle("Info");
						alert.setMessage("No Item!");
						alert.show();
						alert.setCancelable(true);
						
						txt_grand_total.setText("0.00");
					}
				}
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(VoucherPurchaseReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(VoucherPurchaseReportActivity.this);

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
	
	
	private void getVoucher()
	{
		dbManager = new PurchaseVoucherController(this);
		PurchaseVoucherController control = (PurchaseVoucherController)dbManager;
		voucherList = new ArrayList<Object>();
		
		//PurchaseVoucher pVoucher = new PurchaseVoucher();
		//pVoucher.setVid("All");
		
		//voucherList.add(pVoucher);
		//voucherList.addAll(control.selectAllGroupBy());
		
		//Change List<Object> to String Array
		String[] voucherArray = new String[voucherList.size()];
		int index = 0;
		for (Object value : voucherList) {
			
			PurchaseVoucher pv = (PurchaseVoucher)value;
			voucherArray[index] = pv.getVid();
			index++;
		}
		
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, voucherArray);
		autocomplete_voucher_no.setAdapter(adapter);
		
		//Log.i("","supplier List:" + voucherList.toString());
	}
	
	private void getAllVoucher() {
		// TODO Auto-generated method stub
		dbManager = new PurchaseVoucherController(VoucherPurchaseReportActivity.this);
		PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = pvController.select();
		
		Log.i("", "all purchase list: "+listVoucher.toString());
		lvAdapter = new VoucherPurchaseReportLvAdapter(VoucherPurchaseReportActivity.this, listVoucher);
		lv_purchase_report.setAdapter(lvAdapter);
		
		//svController.delete();
	}
	
	private void GrandTotal()
	{		
			Integer grandTotal = 0; 
			
			for (int i = 0; i < listVoucher.size(); i++) {
				Integer total = Integer.valueOf(((PurchaseVoucher) listVoucher.get(i)).getItemtotal());
				grandTotal += total;
			}
			
			txt_grand_total.setText(grandTotal+"");
			
	}
	
	public boolean checkFields() {
		if (autocomplete_voucher_no.getText().toString().length() == 0) {
			autocomplete_voucher_no.setError("Enter Voucher No.");
			return false;
		}
		
		return true;
	}
}

