package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdatePurchaseVoucherActivity;
import com.ignite.pos.UpdateSaleVoucherActivity;
import com.ignite.pos.model.PurchaseVoucher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PurchaseDetailReportLvAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;
	private String Date, VouNo;

	public PurchaseDetailReportLvAdapter(Activity aty, List<Object> listObj, String date, String Vno) {
		super();
		this.aty = aty;
		this.listVoucher = listObj;
		Date = date;
		VouNo = Vno;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listVoucher.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listVoucher.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_detail_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_qty = (TextView)convertView.findViewById(R.id.txt_qty);
			holder.txt_unit_price = (TextView)convertView.findViewById(R.id.txt_unit_price);
			holder.txt_item_total = (TextView)convertView.findViewById(R.id.txt_item_total);
			//holder.btn_update = (Button)convertView.findViewById(R.id.btn_update);
			//holder.btn_update.setVisibility(View.GONE);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final PurchaseVoucher purchaseVoucherItem = (PurchaseVoucher) getItem(position);
		
		holder.txt_item_name.setText(purchaseVoucherItem.getItemname());
		holder.txt_qty.setText(purchaseVoucherItem.getQty());
		holder.txt_unit_price.setText(purchaseVoucherItem.getPurchasePrice());
		holder.txt_item_total.setText(purchaseVoucherItem.getItemtotal());
		
		/*holder.btn_update.setTag(purchaseVoucherItem.getItemid());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdatePurchaseVoucherActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("ItemID", purchaseVoucherItem.getItemid());
				bundle.putString("ItemName", purchaseVoucherItem.getItemname());
				bundle.putString("ItemQty", purchaseVoucherItem.getQty());
				bundle.putString("ItemPrice", purchaseVoucherItem.getPurchasePrice());
				bundle.putString("ItemTotal", purchaseVoucherItem.getItemtotal());
				bundle.putString("GrandTotal", purchaseVoucherItem.getGrandtotal());
				bundle.putString("Date", Date);
				bundle.putString("VouID", VouNo);
				//bundle.putString("Discount", purchaseVoucherItem.getDiscount());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});*/
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_item_name, txt_qty, txt_unit_price, txt_item_total;
		Button btn_update, btn_delete;
	}
	
	

}

	

