package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.adapter.ItemListReportAdapter;
import com.ignite.pos.application.SaleReportExcelUtility;
import com.ignite.pos.application.StockReportExcelUtility;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;

public class ItemListReport extends SherlockActivity{
	
	private ActionBar actionBar;
	private ListView lv_items_list_report;
	private DatabaseManager dbManager;
	private List<Object> listItems;
	//private TextView txt_grand_total;
	private TextView title;
	private Button btn_print;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_list_report);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_report);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Stock Report");
		title.setText("ပစၥည္းလက္က်န္မွတ္တမ္း");
		btn_print = (Button) actionBar.getCustomView().findViewById(R.id.btn_print);
		btn_print.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);		
		
		lv_items_list_report = (ListView) findViewById(R.id.lv_items_list_report);
		//txt_grand_total = (TextView) findViewById(R.id.txt_total);
		
		getItem();
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_print) {
				SaveFileDialog fileDialog = new SaveFileDialog(ItemListReport.this);
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
							
							if (listItems != null && listItems.size() > 0) {
								new StockReportExcelUtility(listItems, filename).write();
								SKToastMessage.showMessage(ItemListReport.this, filename+".xls is saved in your Device External SD card!", SKToastMessage.SUCCESS);
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

	private void getItem() {
		// TODO Auto-generated method stub
		dbManager = new ItemListController(ItemListReport.this);
		ItemListController itemController = (ItemListController) dbManager;
		listItems = new ArrayList<Object>();
		listItems = itemController.selectRecordPurchasedItems();
		
		Log.i("", "Number of stock list: "+listItems.size());
		Log.i("", "stock list in DB: "+listItems.toString());
		
		ItemListReportAdapter adapter = new ItemListReportAdapter(ItemListReport.this, listItems);
		//adapter.setCallbacks(childCallback);
		lv_items_list_report.setAdapter(adapter);
		
		if (listItems.size() == 0) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(ItemListReport.this);
			alert.setTitle("Info");
			alert.setMessage("No Item Yet!");
			alert.show();
			alert.setCancelable(true);
			
		}
	}
	
	private void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(ItemListReport.this);
		alert.setTitle("Info");
		alert.setMessage(message+"!");
		alert.show();
		alert.setCancelable(true);
	}
	
}