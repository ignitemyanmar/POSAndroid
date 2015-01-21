package com.ignite.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.ignite.pos.adapter.SalePersonReportAdapter;
import com.ignite.pos.adapter.SalePersonReportAdapter.saleCallback;
import com.ignite.pos.adapter.SalepersonSpinnerAdapter;
import com.ignite.pos.application.SaleReportExcelUtility;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.SaleHistoryController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.SaleHistory;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.spSalePerson;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;
import com.smk.skalertmessage.SKToastMessage;

public class SalePersonReportActivity extends BaseSherlockActivity{
	private Spinner spSaleperson;
	private Button fromdate, todate, search;
	private spSalePerson sale_person;
	private List<Object> spList;
	private String selectedSaleperson;
	private String selectedFromDate;
	private String selectedToDate;
	private DatabaseManager dbManager;
	private ListView lv_sale_report;
	private TextView txt_grand_total;
	private SalePersonReportAdapter spReportListViewAdapter;
	private List<Object> listVoucher;
	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	private String currentDate;
	private String AdminName;
	private RelativeLayout add_layout;
	private Button btn_print;
	private String currentDateTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saleperson_activity);
		
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		AdminName = pref.getString("admin_name", "-");
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Sale Report");
		title.setText("အ ေရာင္းမွတ္တမ္း");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		spSaleperson = (Spinner)findViewById(R.id.spSalePerson);
		fromdate  = (Button)findViewById(R.id.btnFromDate);
		todate = (Button)findViewById(R.id.btnToDate);
		search = (Button)findViewById(R.id.btnSearch);
		btn_print = (Button)findViewById(R.id.btn_print);
		lv_sale_report = (ListView) findViewById(R.id.lv_sale_report);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		
		spSaleperson.setOnItemSelectedListener(salepersonClickListener);
		fromdate.setOnClickListener(clickListener);
		todate.setOnClickListener(clickListener);
		search.setOnClickListener(clickListener);
		btn_print.setOnClickListener(clickListener);
		
		getSalePerson();
		
		//Limit Default Dates
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		
		//Date & Time Format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		currentDateTime = dateFormat.format(cal.getTime());
		System.out.println("Current Date Time : " + dateFormat.format(cal.getTime()));
		
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
		
		/*getVoucher();
		GrandTotal();*/
		
		/*if (listVoucher.size() == 0) {
			txt_grand_total.setText("0.00");
		}*/
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == search)
			{
				/*if (selectedSaleperson.equals("All") && fromdate.getText().toString().equals("From Date") && todate.getText().toString().equals("To Date")) {
					getVoucher();
				}else {*/
					dbManager = new SaleVouncherController(SalePersonReportActivity.this);
					SaleVouncherController svController = (SaleVouncherController) dbManager;
					listVoucher = new ArrayList<Object>();
					listVoucher = svController.select(selectedSaleperson, selectedFromDate, selectedToDate);
					
					Log.i("", "Sale voucher Total: "+listVoucher.toString());
					
					spReportListViewAdapter = new SalePersonReportAdapter(SalePersonReportActivity.this, listVoucher);
					spReportListViewAdapter.setCallbackListiner(sCallback);
					lv_sale_report.setAdapter(spReportListViewAdapter);
					
					GrandTotal();
					
					if (listVoucher.size() == 0) {
						
						AlertDialog.Builder alert = new AlertDialog.Builder(SalePersonReportActivity.this);
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
				final SKCalender skCalender = new SKCalender(SalePersonReportActivity.this);

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
				final SKCalender skCalender = new SKCalender(SalePersonReportActivity.this);

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
				SaveFileDialog fileDialog = new SaveFileDialog(SalePersonReportActivity.this);
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
							
							for (int j = 0; j < listVoucher.size(); j++) {
								
								SaleVouncher sv = (SaleVouncher) listVoucher.get(j);
								
								String saleDate = sv.getVdate();
								//Split sale date 
								String[] parts = saleDate.split("-");
								String year = parts[0]; 
								String month = parts[1];
								String day = parts[2];
								
								String formatedDate = day+"-"+month+"-"+year;
								
								((SaleVouncher)listVoucher.get(j)).setVdate(formatedDate);
							}
							
							List<String> searchInfoList = new ArrayList<String>();
							searchInfoList.add(selectedSaleperson);
							searchInfoList.add(dmyDateFormat(selectedFromDate));
							searchInfoList.add(dmyDateFormat(selectedToDate));
							
							if (listVoucher != null && listVoucher.size() > 0) {
								new SaleReportExcelUtility(listVoucher, filename, searchInfoList).write();
								SKToastMessage.showMessage(SalePersonReportActivity.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
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
	
	private OnItemSelectedListener salepersonClickListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			sale_person = (spSalePerson) spList.get(position); 
			selectedSaleperson = sale_person.getSpusername();
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void getSalePerson()
	{
		dbManager = new spSalePersonController(this);
		spSalePersonController control = (spSalePersonController)dbManager;
		spList = new ArrayList<Object>();
		
		spSalePerson salePerson = new spSalePerson();
		salePerson.setSpusername("All");
		
		spList.add(salePerson);
		spList.addAll(control.select());
		
		if (spList.size() > 0) {
			spSaleperson.setAdapter(new SalepersonSpinnerAdapter(this,spList));
		}
		
		Log.i("","Sale Person List:" + spList.toString());
	}
	

	private void getVoucher() {
		// TODO Auto-generated method stub
		dbManager = new SaleVouncherController(SalePersonReportActivity.this);
		SaleVouncherController svController = (SaleVouncherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = svController.selectAllGroupBy(selectedFromDate, selectedToDate);
		
		spReportListViewAdapter = new SalePersonReportAdapter(SalePersonReportActivity.this, listVoucher);
		spReportListViewAdapter.setCallbackListiner(sCallback);
		lv_sale_report.setAdapter(spReportListViewAdapter);
		
		if (listVoucher.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(SalePersonReportActivity.this);
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
				Integer total = Integer.valueOf(((SaleVouncher) listVoucher.get(i)).getTotal());
				grandTotal += total;
			}
			
			txt_grand_total.setText(grandTotal+"");
			
	}
	
	private SalePersonReportAdapter.saleCallback sCallback = new saleCallback() {
		
		public void onUpdateClick(Integer pos) {
			// TODO Auto-generated method stub
			Log.i("", "Update Listener: "); 
			
			SaleVouncher sv = (SaleVouncher) listVoucher.get(pos);
			
			if (AdminName.equals("-")) {
				SKToastMessage.showMessage(SalePersonReportActivity.this, "Please log in with Admin account first!", SKToastMessage.WARNING);
				showAdminDialog();
			}else {
				
				Intent next = new Intent(SalePersonReportActivity.this, SaleUpdateActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("VoucherNo", sv.getVid());
				
				next.putExtras(bundle);
				SalePersonReportActivity.this.startActivity(next);
			}
		}
		
		public void onDeleteClick(Integer pos) {
			// TODO Auto-generated method stub
			SaleVouncher sv = (SaleVouncher) listVoucher.get(pos);
			
			if (AdminName.equals("-")) {
				SKToastMessage.showMessage(SalePersonReportActivity.this, "Please log in with Admin account first!", SKToastMessage.WARNING);
				showAdminDialog();
			}else {
				removeItemFromList(pos, sv.getVid());
			}
		}
	};
	
    protected void removeItemFromList(int position, String vid) {
    	
        final int deletePosition = position;
        
        AlertDialog.Builder alert = new AlertDialog.Builder(SalePersonReportActivity.this);
    
        alert.setTitle("Delete Sale Voucher - "+vid+" ?");
        
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        	
        	
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				//Voucher Object that you want to delete
				SaleVouncher sv = (SaleVouncher) listVoucher.get(deletePosition);
				
				//Get Voucher Item List By VouID
				dbManager = new SaleVouncherController(SalePersonReportActivity.this);
				SaleVouncherController svControl = (SaleVouncherController)dbManager;
				List<Object> itemList = new ArrayList<Object>();
				itemList = svControl.selectRecordByVouID(sv.getVid());
				
				Log.i("", "Voucher Item List: "+itemList);
				
				if (itemList.size() > 0 && itemList != null) {
					
					//Update in Ledger Table after Delete Voucher
					dbManager = new LedgerController(SalePersonReportActivity.this);
					LedgerController ledgerControl = (LedgerController)dbManager;
					List<Object> ledgerList = new ArrayList<Object>();
					
					for (int i = 0; i < itemList.size(); i++) {
					
						SaleVouncher salev = (SaleVouncher) itemList.get(i);
						
						ledgerList = ledgerControl.select(salev.getItemid(), salev.getVdate(), salev.getVdate());
						
						Log.i("", "Ledger List by ItemID + Voucher Date: "+ledgerList.toString());
						
						List<Object> updateLedgerList;
						
						if (ledgerList.size() > 0) {
							//Update New Sale Qty in Ledger 
							Ledger ledger = (Ledger) ledgerList.get(0);
								
								//Minus to Sale Qty in Ledger 
								Integer newSaleQty = ledger.getSaleQty() - Integer.valueOf(salev.getQty()); 
								
								Log.i("", "new sale qty: "+newSaleQty);
								
								//Get New Stock Qty 
								Integer newStockQty = ( ledger.getOldStockQty() + ledger.getPurchaseQty() ) - newSaleQty + ledger.getReturnQty();
								
								//Update New Sale Qty 
								updateLedgerList = new ArrayList<Object>();
								updateLedgerList.add(new Ledger(ledger.getItemId(), ledger.getItemName(), ledger.getDate()
										, ledger.getOldStockQty(), ledger.getPurchaseQty(), newSaleQty, newStockQty, ledger.getReturnQty()));
								ledgerControl.updateQtyRecord(updateLedgerList);
								
								Log.i("", "After delete voucher, update ledger table: "
										+ledgerControl.select(salev.getItemid(), salev.getVdate(), salev.getVdate()).toString());
						}
					}//End For Loop of Ledger Update
					
					//Update in Profit Table
					dbManager = new ProfitController(SalePersonReportActivity.this);
					ProfitController profitControl = (ProfitController)dbManager;
					List<Object> profitObj = new ArrayList<Object>();
					
					for (int i = 0; i < itemList.size(); i++) {
						SaleVouncher saleVou = (SaleVouncher) itemList.get(i);
						
						profitObj = profitControl.selectByVidItemidDate(saleVou.getVid(), saleVou.getItemname(), saleVou.getVdate());
						
						if (profitObj.size() > 0 && profitObj != null) {
							
							profitControl.delete(profitObj);
						}
					}
					
					//Update in Item Stock Table 
					dbManager = new ItemListController(SalePersonReportActivity.this);
					ItemListController itemControl = (ItemListController)dbManager;
					List<Object> listItem = new ArrayList<Object>();
					
					for (int i = 0; i < itemList.size(); i++) {
						
						SaleVouncher salev = (SaleVouncher)itemList.get(i);
						
						listItem = itemControl.select(salev.getItemid());
						
						Log.i("", "Item List: "+listItem.toString());
						
						if (listItem.size() > 0) {
							
							ItemList iteml = (ItemList)listItem.get(0);
										
							//Increase Stock Qty 
							Integer newStockQty = Integer.valueOf(iteml.getQty()) + Integer.valueOf(salev.getQty()); 
											
							Log.i("", "new stock increase qty: "+newStockQty);
											
							//Update New Stock Qty 
							listItem.add(new ItemList(salev.getItemid(), newStockQty.toString()));
							itemControl.updateNewStockQty(listItem);
											
							Log.i("", "After delete Voucher, update stock table: "+itemControl.select(salev.getItemid()).toString());
						}
					}//End Loop Stock Table
					
					//Save in Sale History (Voucher Delete) 
					dbManager = new SaleHistoryController(SalePersonReportActivity.this);
					SaleHistoryController shControl = (SaleHistoryController)dbManager;
					List<Object> shList = new ArrayList<Object>();
					
					SaleVouncher salev = null;
					
					for (int i = 0; i < itemList.size(); i++) {
						
						salev = (SaleVouncher)itemList.get(i);
						
						shList.add(new SaleHistory(salev.getVid(), salev.getItemid(), Integer.valueOf(salev.getOldQty())
								, 0, currentDateTime, AdminName, "delete"));
						
					}//End Loop Sale History 
					
					shControl.save(shList);
					Log.i("", "After save in Sale History (Delete Voucher): "+shControl.selectRecordByVouID(salev.getVid()));
					
				}//End If (Item list by Voucher ID)
				
				//Delete in Sale Table
				dbManager = new SaleVouncherController(SalePersonReportActivity.this);
				SaleVouncherController sv_control = (SaleVouncherController)dbManager;
				sv_control.delete(sv.getVid());
				
				Log.i("","Deleted Voucher : " + sv.getVid());
				
				listVoucher.remove(deletePosition);
				spReportListViewAdapter.notifyDataSetChanged();
				
				SKToastMessage.showMessage(SalePersonReportActivity.this, sv.getVid()+" Deleted!", SKToastMessage.SUCCESS);
            	
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
	

	private void alertDialog(String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(SalePersonReportActivity.this);
		alert.setTitle("Info");
		alert.setMessage(string+"!");
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
