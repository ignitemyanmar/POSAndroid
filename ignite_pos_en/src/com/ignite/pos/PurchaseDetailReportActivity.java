package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.ignite.pos.adapter.PurchaseDetailReportLvAdapter;
import com.ignite.pos.adapter.VoucherPurchaseReportLvAdapter;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleVouncher;

public class PurchaseDetailReportActivity extends SherlockActivity{

	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	private TextView txt_vou_no, txt_date, txt_supplier, txt_grand_total;
	private ListView lv_purchase_detail;
	private DatabaseManager dbManager;
	private List<Object> listVoucher;
	private String voucherNo, date, supplier_name;
	private PurchaseDetailReportLvAdapter lvAdapter;
	private TextView txt_discount;
	private RelativeLayout add_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_detail);
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		title.setText("Purchase Detail Report");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		txt_vou_no = (TextView)findViewById(R.id.txt_vou_no);
		txt_date  = (TextView)findViewById(R.id.txt_date);
		txt_supplier = (TextView)findViewById(R.id.txt_supplier);
		lv_purchase_detail = (ListView) findViewById(R.id.lv_sale_detail);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		txt_discount = (TextView) findViewById(R.id.txt_discount);
		
		Bundle bundle = getIntent().getExtras();
		voucherNo = bundle.getString("VoucherNo");
		date = bundle.getString("Date");
		supplier_name = bundle.getString("SupplierName");
		
		listVoucher = new ArrayList<Object>();
		
		txt_vou_no.setText("Vou No:  "+voucherNo);
		txt_date.setText("Date:  "+date);
		txt_supplier.setText("Supplier:  "+supplier_name);
		
		//getVoucherDetail();
		
		//GrandTotal();
		
	}

	private void getVoucherDetail() {
		// TODO Auto-generated method stub
		dbManager = new PurchaseVoucherController(PurchaseDetailReportActivity.this);
		PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = pvController.select(voucherNo);
		
		Log.i("", "List Voucher: "+listVoucher.toString());
		
		lvAdapter = new PurchaseDetailReportLvAdapter(PurchaseDetailReportActivity.this, listVoucher, date, voucherNo);
		lv_purchase_detail.setAdapter(lvAdapter);
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getVoucherDetail();
		//txt_discount.setText(((SaleVouncher) listVoucher.get(0)).getDiscount());
		txt_grand_total.setText(((PurchaseVoucher) listVoucher.get(0)).getGrandtotal());
	}
	
	
}
