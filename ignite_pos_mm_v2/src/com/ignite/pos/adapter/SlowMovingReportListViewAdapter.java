package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.ItemList;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SlowMovingReportListViewAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Object> saleList;
	
	public SlowMovingReportListViewAdapter(Activity aty, List<Object> saleList) {
		super();
		this.saleList = saleList;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return saleList.size();
	}

	public ItemList getItem(int arg0) {
		// TODO Auto-generated method stub
		return (ItemList) saleList.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	 
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Log.i("", "tmp list size: "+getCount());
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_slow_moving_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_qty = (TextView)convertView.findViewById(R.id.txt_qty);
			holder.txt_purchase_price = (TextView) convertView.findViewById(R.id.txt_purchase_price);
			holder.txt_sale_price = (TextView)convertView.findViewById(R.id.txt_sale_price);
			holder.txt_marginal_price = (TextView)convertView.findViewById(R.id.txt_marginal_price);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_item_name.setText(getItem(position).getItemName());
		holder.txt_qty.setText(getItem(position).getQty());
		holder.txt_purchase_price.setText(getItem(position).getPurchasePrice());
		holder.txt_sale_price.setText(getItem(position).getSalePrice());
		holder.txt_marginal_price.setText(getItem(position).getMarginalPrice());
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_item_name, txt_qty, txt_purchase_price, txt_sale_price, txt_marginal_price;
	}

}

	

