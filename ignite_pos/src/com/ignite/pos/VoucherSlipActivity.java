package com.ignite.pos;

import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.pos.adapter.VoucherSlipListViewAdapter;
import com.ignite.pos.model.BundleListObjet;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;

public class VoucherSlipActivity extends SherlockActivity{

	private TextView txt_voucher_no;
	private TextView txt_date;
	private TextView txt_sale_person;
	private TextView txt_buyer;
	private ListView lv_voucher;
	private TextView txt_grand_total;
	private TextView txt_discount;
	private TextView txt_net_amount;
	private String saleVoucherString;
	private BundleListObjet bundleListObjet;
	private List<SaleVouncher> saleVoucherList;
	private Button btn_done;
	private Button btn_print;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher_slip);
		
		txt_voucher_no = (TextView) findViewById(R.id.txt_vou_no);
		txt_date = (TextView) findViewById(R.id.txt_date);
		txt_sale_person = (TextView) findViewById(R.id.txt_sale_person);
		txt_buyer = (TextView) findViewById(R.id.txt_buyer);
		lv_voucher = (ListView) findViewById(R.id.lv_voucher);
		txt_grand_total = (TextView) findViewById(R.id.txt_grand_total);
		txt_discount = (TextView) findViewById(R.id.txt_discount);
		txt_net_amount = (TextView) findViewById(R.id.txt_net_amount);
		btn_done = (Button) findViewById(R.id.btn_done);
		btn_print = (Button) findViewById(R.id.btn_print);
		
		btn_done.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_print.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SKToastMessage.showMessage(VoucherSlipActivity.this, "Not setup with printer.", SKToastMessage.ERROR);
			}
		});
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			saleVoucherString = extras.getString("saleVoucher");
		}
		
		bundleListObjet = new Gson().fromJson(saleVoucherString, BundleListObjet.class);
		
		saleVoucherList = bundleListObjet.getSaleVouncher();
		
		Log.i("", "Sale voucher list : "+saleVoucherList);
		
		
		txt_voucher_no.setText(saleVoucherList.get(0).getVid());
		txt_date.setText(saleVoucherList.get(0).getVdate());
		txt_sale_person.setText(saleVoucherList.get(0).getSalePerson());
		//txt_buyer.setText(saleVoucherList.get(0).getCusname());
		
		Integer grandTotal = 0; 
		
		for (int i = 0; i < saleVoucherList.size(); i++) {
			
			grandTotal += Integer.valueOf(saleVoucherList.get(i).getItemtotal());
		}
		
		txt_grand_total.setText(grandTotal+"");
		txt_discount.setText(String.valueOf((grandTotal * Integer.valueOf(saleVoucherList.get(0).getDiscount())) / 100)+"");
		txt_net_amount.setText(saleVoucherList.get(0).getTotal());
		
		VoucherSlipListViewAdapter adapter = new VoucherSlipListViewAdapter(VoucherSlipActivity.this, saleVoucherList);
		lv_voucher.setAdapter(adapter);
		setListViewHeightBasedOnChildren(lv_voucher);
		
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null && listView.getCount() == 0) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
}
