package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.ignite.pos.BaseSherlockActivity.LoginCallbacks;
import com.ignite.pos.adapter.SaleDetailReportLvAdapter;
import com.ignite.pos.adapter.SaleDetailReportLvAdapter.saleCallback;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;

public class SaleDetailReportActivity extends BaseSherlockActivity{

	private ActionBar actionBar;
	private TextView title;
	private Button change_mode;
	private TextView txt_vou_no, txt_date, txt_sale_person, txt_grand_total, txt_discount;
	private ListView lv_sale_detail;
	private DatabaseManager dbManager;
	private List<Object> listVoucher;
	private String voucherNo, date, sale_person_name;
	private SaleDetailReportLvAdapter lvAdapter;
	private String AdminName;
	private RelativeLayout add_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_sale_detail);
		
		SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
		AdminName = pref.getString("admin_name", "-");
		
		actionBar = getSupportActionBar();						
		actionBar.setCustomView(R.layout.action_bar_update);
		title = (TextView)actionBar.getCustomView().findViewById(R.id.txt_title);
		//title.setText("Sale Detail Report");
		title.setText("အ ေရာင္း အ ေသးစိတ္");
		add_layout = (RelativeLayout)actionBar.getCustomView().findViewById(R.id.layout_add_new);
		add_layout.setVisibility(View.GONE);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		txt_vou_no = (TextView)findViewById(R.id.txt_vou_no);
		txt_date  = (TextView)findViewById(R.id.txt_date);
		txt_sale_person = (TextView)findViewById(R.id.txt_supplier);
		lv_sale_detail = (ListView) findViewById(R.id.lv_sale_detail);
		txt_grand_total = (TextView) findViewById(R.id.txtTotal);
		txt_discount = (TextView) findViewById(R.id.txt_discount);
		
		Bundle bundle = getIntent().getExtras();
		voucherNo = bundle.getString("VoucherNo");
		date = bundle.getString("Date");
		sale_person_name = bundle.getString("SalepersonName");
		
		/*txt_vou_no.setText("Vou No:  "+voucherNo);
		txt_date.setText("Date:  "+date);
		txt_sale_person.setText("Sale Person:  "+sale_person_name);*/
		
		txt_vou_no.setText("ေဘာင္ ခ်ာ နံပါတ္ :  "+voucherNo);
		txt_date.setText("ေန႔ရက္ :  "+date);
		txt_sale_person.setText("ေရာင္းသူ  :  "+sale_person_name);
		
		setOnLoginListener(loginCallbacks);
	}

	private void getVoucherDetail() {
		// TODO Auto-generated method stub
		dbManager = new SaleVouncherController(SaleDetailReportActivity.this);
		SaleVouncherController svController = (SaleVouncherController) dbManager;
		listVoucher = new ArrayList<Object>();
		listVoucher = svController.selectRecordByVouID(voucherNo);
		
		Log.i("", "List Voucher: "+listVoucher.toString());
		
		lvAdapter = new SaleDetailReportLvAdapter(SaleDetailReportActivity.this, listVoucher, date, voucherNo);
		lvAdapter.setCallbackListiner(scallback);
		lv_sale_detail.setAdapter(lvAdapter);
	}
	
	private SaleDetailReportLvAdapter.saleCallback scallback = new saleCallback() {
		
		public void onUpdateClick(Integer pos) {
			// TODO Auto-generated method stub
			SaleVouncher saleVoucherItem = (SaleVouncher) listVoucher.get(pos);
			
			if (AdminName.equals("-")) {
				SKToastMessage.showMessage(SaleDetailReportActivity.this, "Please log in with Admin account first!", SKToastMessage.WARNING);
				showAdminDialog();
			}else {
				Intent next = new Intent(SaleDetailReportActivity.this, UpdateSaleVoucherActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("ItemID", saleVoucherItem.getItemid());
				bundle.putString("ItemName", saleVoucherItem.getItemname());
				bundle.putString("ItemQty", saleVoucherItem.getQty());
				bundle.putString("ItemPrice", saleVoucherItem.getPrice());
				bundle.putString("ItemTotal", saleVoucherItem.getItemtotal());
				bundle.putString("GrandTotal", saleVoucherItem.getTotal());
				bundle.putString("Date", date);
				bundle.putString("VouID", voucherNo);
				bundle.putString("Discount", saleVoucherItem.getDiscount());
				
				next.putExtras(bundle);
				SaleDetailReportActivity.this.startActivity(next);
			}

		}
	};
	
	private LoginCallbacks loginCallbacks = new LoginCallbacks() {
		
		public void onLogin() {
			// TODO Auto-generated method stub
			
			SharedPreferences pref = getSharedPreferences("Admin",Activity.MODE_PRIVATE);
			AdminName = pref.getString("admin_name", "-");
			
			Log.i("", "After click log in: "+AdminName);
			
			//SKToastMessage.showMessage(SupplierPurchaseReportActivity.this, "Login", SKToastMessage.SUCCESS);
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getVoucherDetail();
		
		SaleVouncher sv = (SaleVouncher)listVoucher.get(0);
		
		txt_discount.setText(sv.getDiscount());
		Integer grandTotal = Integer.valueOf(sv.getTotal()) - Integer.valueOf(sv.getDiscount());
		txt_grand_total.setText(grandTotal+"");
	}
}
