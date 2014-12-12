
package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.PurchaseVoucher;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VoucherPurchaseReportLvAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;

	public VoucherPurchaseReportLvAdapter(Activity aty, List<Object> listObj) {
		super();
		this.aty = aty;
		this.listVoucher = listObj;
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
			
			convertView =  mInflater.inflate(R.layout.list_item_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_qty = (TextView)convertView.findViewById(R.id.txt_qty);
			holder.txt_unit_price = (TextView)convertView.findViewById(R.id.txt_unit_price);
			holder.txt_item_total = (TextView)convertView.findViewById(R.id.txt_item_total);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		
		PurchaseVoucher purchaseVoucherItem = (PurchaseVoucher) getItem(position);
		
		Log.i("", "purchase item : "+purchaseVoucherItem.getItemname());
		
		holder.txt_item_name.setText(purchaseVoucherItem.getItemname());
		holder.txt_qty.setText(purchaseVoucherItem.getQty());
		
		Log.i("", "unit price: "+purchaseVoucherItem.getPurchasePrice());
		
		holder.txt_unit_price.setText(purchaseVoucherItem.getPurchasePrice());
		holder.txt_item_total.setText(purchaseVoucherItem.getItemtotal());
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_item_name, txt_qty, txt_unit_price, txt_item_total;
	}

}

	

