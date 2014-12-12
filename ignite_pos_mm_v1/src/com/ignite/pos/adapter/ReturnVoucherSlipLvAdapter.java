package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.SaleReturn;
import com.smk.skscalableview.ScalableTextView;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReturnVoucherSlipLvAdapter extends BaseAdapter{
	
	private List<SaleReturn> saleReturnVoucherList;
	private LayoutInflater mInflater;
	private Activity aty;

	public ReturnVoucherSlipLvAdapter(Activity aty, List<SaleReturn> saleReturnVoucherList) {
		super();
		this.saleReturnVoucherList = saleReturnVoucherList;
		mInflater = LayoutInflater.from(aty);
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return saleReturnVoucherList.size();
	}

	public SaleReturn getItem(int position) {
		// TODO Auto-generated method stub
		return saleReturnVoucherList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Log.i("", "Return Sale vou obj: "+getItem(position).toString());
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_voucher_slip, null);
			
			holder = new ViewHolder();
			holder.txt_item_name = (ScalableTextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_qty = (TextView) convertView.findViewById(R.id.txt_qty);
			holder.txt_unit_price = (TextView) convertView.findViewById(R.id.txt_price);
			holder.txt_item_total = (ScalableTextView) convertView.findViewById(R.id.txt_item_total);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//SaleReturn sr = (SaleReturn)getItem(position);
		
		holder.txt_item_name.setText(getItem(position).getItemName());
		holder.txt_qty.setText(getItem(position).getReturnQty()+"");
		holder.txt_unit_price.setText(getItem(position).getSalePrice()+" x ");
		holder.txt_item_total.setText(getItem(position).getItemTotal()+"");
		
		return convertView;
	}
	
	static class ViewHolder {
		ScalableTextView txt_item_name;
		TextView txt_qty;
		TextView txt_unit_price;
		ScalableTextView txt_item_total;
	}

}

