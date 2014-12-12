package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.actionbarsherlock.app.ActionBar;
import com.ignite.pos.adapter.SupplierReportListViewAdapter;
import com.ignite.pos.adapter.SupplierReportListViewAdapter.Callback;
import com.ignite.pos.adapter.SupplierSpinnerAdapter;
import com.ignite.pos.application.PurchaseReportExcelUtility;
import com.ignite.pos.application.SaleReportExcelUtility;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.Supplier;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;

public class SupplierPurchaseReportActivity extends BaseSherlockActivity{
	private Spinner sp_supplier;
	private Button fromdate, todate, search;
	private Supplier supplier;
	private List<Object> supplierList;
	private String selectedSupplier;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_purchase_report;
	private TextView txt_grand_total;
	private SupplierReportListViewAdapter supplierReportListViewAdapter;
	private List<Object> listVoucher;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	private String currentDate;
	private String AdminName;
	private RelativeLayout add_layout;
	private Button btn_print;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_purchase_report);
		
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		AdminName = pref.getString("admin_name", "-");
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Purchase Report");
		title.setText("အ၀ယ္မွတ္တမ္း");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		sp_supplier = (Spinner)findViewById(R.id.sp_supplier);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		btn_print = (Button)findViewById(R.id.btn_print);
		lv_purchase_report = (ListView) findViewById(R.id.lv_purchase_report);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		
		sp_supplier.setOnItemSelectedListener(supplierClickListener);
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		btn_print.setOnClickListener(clickListener);
		
		getSupplier();
		
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
		
		setOnLoginListener(loginCallbacks);
		
		//getVoucher();
		//GrandTotal();
		
		/*if (listVoucher.size() == 0) {
			txt_grand_total.setText("0.00");
		}*/
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == search)
			{
				/*if (selectedSupplier.equals("All") && fromdate.getText().toString().equals("From Date") && todate.getText().toString().equals("To Date")) {
					getVoucher();
				}else{*/
					dbManager = new PurchaseVoucherController(SupplierPurchaseReportActivity.this);
					PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
					listVoucher = new ArrayList<Object>();
					listVoucher = pvController.select(selectedSupplier, selectedFromDate, selectedToDate);
					
					supplierReportListViewAdapter = new SupplierReportListViewAdapter(SupplierPurchaseReportActivity.this, listVoucher, AdminName);
					supplierReportListViewAdapter.setCallbackListiner(callback);
					lv_purchase_report.setAdapter(supplierReportListViewAdapter);
					
					GrandTotal();
					
					if (listVoucher.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(SupplierPurchaseReportActivity.this);
						alert.setTitle("Info");
						alert.setMessage("No Item!");
						alert.show();
						alert.setCancelable(true);
						
						txt_grand_total.setText("0.00");
					}
				//}
				
			}
			if(v == fromdate)
			{
				final SKCalender skCalender = new SKCalender(SupplierPurchaseReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(SupplierPurchaseReportActivity.this);

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
				SaveFileDialog fileDialog = new SaveFileDialog(SupplierPurchaseReportActivity.this);
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
								new PurchaseReportExcelUtility(listVoucher, filename).write();
								SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
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
	
	private SupplierReportListViewAdapter.Callback callback = new Callback() {
		
		public void onUpdateClick(Integer pos) {
			// TODO Auto-generated method stub

			PurchaseVoucher pv = (PurchaseVoucher) listVoucher.get(pos);
			
			if (AdminName.equals("-")) {
				SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, "Please log in with Admin account first!", SKToastMessage.WARNING);
				showAdminDialog();
			}else {
				Intent next = new Intent(SupplierPurchaseReportActivity.this, PurchaseUpdateActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("VoucherNo", pv.getVid());
				bundle.putString("SupplierName", pv.getSupplierName());
				
				next.putExtras(bundle);
				SupplierPurchaseReportActivity.this.startActivity(next);
			}
		}
		
		public void onDeleteClick(Integer pos) {
			// TODO Auto-generated method stub
			PurchaseVoucher pv = (PurchaseVoucher) listVoucher.get(pos);
			
			if (AdminName.equals("-")) {
				SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, "Please log in with Admin account first!", SKToastMessage.WARNING);
				showAdminDialog();
				
			}else {
				removeItemFromList(pos, pv.getVid());
			}
		}
		
		public void onConfirmClick(Integer pos) {
			// TODO Auto-generated method stub
			
			PurchaseVoucher pv = (PurchaseVoucher) listVoucher.get(pos);
			
			if (AdminName.equals("-")) {
				SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, "Please log in with Admin account first!", SKToastMessage.WARNING);
				showAdminDialog();
			}else {
				Intent next = new Intent(SupplierPurchaseReportActivity.this, PurchaseConfirmListActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("VoucherNo", pv.getVid());
				bundle.putString("Date", pv.getVdate());
				bundle.putString("SupplierName", pv.getSupplierName());
				
				next.putExtras(bundle);
				SupplierPurchaseReportActivity.this.startActivity(next);
				
				//startActivity(new Intent(SupplierPurchaseReportActivity.this, PurchaseConfirmListActivity.class).getBundleExtra("bundle"));
			}
		}
	};
	
	/**
	 * 
	 * @param position (position that you want to delete)
	 * @param vid (Vourcher No that you want to delete)
	 */
    protected void removeItemFromList(int position, String vid) {
    	
        final int deletePosition = position;
        
        AlertDialog.Builder alert = new AlertDialog.Builder(SupplierPurchaseReportActivity.this);
    
        alert.setTitle("Delete Purchasse Voucher - "+vid+" ?");
        
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        	
        	
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				//Voucher Object that you want to delete
				PurchaseVoucher pv = (PurchaseVoucher) listVoucher.get(deletePosition);
				
				//Get Voucher Item List By VouID
				dbManager = new PurchaseVoucherController(SupplierPurchaseReportActivity.this);
				PurchaseVoucherController pvControl = (PurchaseVoucherController)dbManager;
				List<Object> itemList = new ArrayList<Object>();
				itemList = pvControl.selectRecordByVouID(pv.getVid());
				
				Log.i("", "Voucher Item List: "+itemList);
				
				//Delete in Purchase Table
				dbManager = new PurchaseVoucherController(SupplierPurchaseReportActivity.this);
				PurchaseVoucherController pv_control = (PurchaseVoucherController)dbManager;
				pv_control.delete(pv.getVid());
				
				Log.i("","Deleted Voucher : " + pv.getVid());
				
				listVoucher.remove(deletePosition);
				supplierReportListViewAdapter.notifyDataSetChanged();
		
				SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, pv.getVid()+" Deleted!", SKToastMessage.SUCCESS);
            	
			}
		});
        
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
      
        alert.show();
        alert.setCancelable(true);
    }
    
	private LoginCallbacks loginCallbacks = new LoginCallbacks() {
		
		public void onLogin() {
			// TODO Auto-generated method stub
			
			SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
			AdminName = pref.getString("admin_name", "-");
			
			Log.i("", "After click log in: "+AdminName);
			
			//SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, "Login", SKToastMessage.SUCCESS);
		}
	};
	
	private OnItemSelectedListener supplierClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			supplier = (Supplier) supplierList.get(position); 
			selectedSupplier= supplier.getSupCoName();
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getSupplier()
	{
		dbManager = new SupplierController(this);
		SupplierController control = (SupplierController)dbManager;
		supplierList = new ArrayList<Object>();
		
		Supplier supplier = new Supplier();
		supplier.setSupCoName("All");
		
		supplierList.add(supplier);
		supplierList.addAll(control.select());
		
		if (supplierList.size() > 0) {
			sp_supplier.setAdapter(new SupplierSpinnerAdapter(this,supplierList));
		}
		
		//Log.i("","supplier List:" + supplierList.toString());
	}
	
	
	private void getVoucher() {
		// TODO Auto-generated method stub
		dbManager = new PurchaseVoucherController(SupplierPurchaseReportActivity.this);
		PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = pvController.selectAllGroupBy(selectedFromDate, selectedToDate);
		
		supplierReportListViewAdapter = new SupplierReportListViewAdapter(SupplierPurchaseReportActivity.this, listVoucher, AdminName);
		supplierReportListViewAdapter.setCallbackListiner(callback);
		lv_purchase_report.setAdapter(supplierReportListViewAdapter);
		
		if (listVoucher.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(SupplierPurchaseReportActivity.this);
			alert.setTitle("Info");
			alert.setMessage("No Item Yet!");
			alert.show();
			alert.setCancelable(true);
			
			txt_grand_total.setText("0.00");
		}
		
		//svController.delete();
	}
	
	private void GrandTotal()
	{		
		
			Integer grandTotal = 0; 
			
			for (int i = 0; i < listVoucher.size(); i++) {
				Integer total = Integer.valueOf(((PurchaseVoucher) listVoucher.get(i)).getGrandtotal());
				grandTotal += total;
			}
			
			txt_grand_total.setText(grandTotal+"");
			
	}
	
	private void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(SupplierPurchaseReportActivity.this);
		alert.setTitle("Info");
		alert.setMessage(message+"!");
		alert.show();
		alert.setCancelable(true);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		getVoucher();
		GrandTotal();
		
		if (listVoucher.size() == 0) {
			txt_grand_total.setText("0.00");
		}
	}
}
