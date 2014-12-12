package com.ignite.pos;

import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.pos.adapter.ReturnVoucherSlipLvAdapter;
import com.ignite.pos.model.BundleListObj;
import com.ignite.pos.model.SaleReturn;
import com.smk.skalertmessage.SKToastMessage;

public class ReturnVoucherSlipActivity extends SherlockActivity{

	private TextView txt_voucher_no;
	private TextView txt_date;
	private TextView txt_sale_person;
	private TextView txt_buyer;
	private ListView lv_voucher;
	private TextView txt_grand_total;
	private TextView txt_discount;
	private TextView txt_net_amount;
	private String saleVoucherString;
	private BundleListObj bundleListObj;
	private List<SaleReturn> returnVouList;
	private Button btn_done;
	private Button btn_print;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_voucher_slip);
		
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
				SKToastMessage.showMessage(ReturnVoucherSlipActivity.this, "Not setup with printer.", SKToastMessage.ERROR);
			}
		});
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			saleVoucherString = extras.getString("SaleReturnVoucher");
		}
		
		bundleListObj = new Gson().fromJson(saleVoucherString, BundleListObj.class);
		
		returnVouList = bundleListObj.getListObj();
		
		Log.i("", "Return voucher list : "+returnVouList);
		
		if (returnVouList != null && returnVouList.size() > 0) {
			//SaleReturn sr = (SaleReturn)returnVouList.get(0);
			
			txt_voucher_no.setText(returnVouList.get(0).getVid());
			txt_date.setText(returnVouList.get(0).getReturnDate());
			txt_sale_person.setText(returnVouList.get(0).getUpdatePerson());
			//txt_buyer.setText(returnVouList.get(0).getCusname());
		}

		Integer grandTotal = 0; 
		
		for (int i = 0; i < returnVouList.size(); i++) {
			
			//SaleReturn saleR = (SaleReturn)returnVouList.get(i);
			
			grandTotal += returnVouList.get(i).getItemTotal();
		}
		
		txt_grand_total.setText(grandTotal+"");
		txt_net_amount.setText(grandTotal+"");
		
		ReturnVoucherSlipLvAdapter adapter = new ReturnVoucherSlipLvAdapter(ReturnVoucherSlipActivity.this, returnVouList);
		lv_voucher.setAdapter(adapter);
		
	}
}



