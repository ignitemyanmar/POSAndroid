package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.Ledger;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LedgerReportListViewAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Object> tempList;
	public LedgerReportListViewAdapter(Activity aty, List<Object> tempList) {
		super();
		this.tempList = tempList;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return tempList.size();
	}

	public Ledger getItem(int arg0) {
		// TODO Auto-generated method stub
		return (Ledger) tempList.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Log.i("", "tmp list size: "+getCount());
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_ledger_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_date = (TextView)convertView.findViewById(R.id.txt_date);
			holder.txt_old_stock_qty = (TextView) convertView.findViewById(R.id.txt_old_stock_qty);
			holder.txt_purchase_qty = (TextView)convertView.findViewById(R.id.txt_purchase_qty);
			holder.txt_sale_qty = (TextView)convertView.findViewById(R.id.txt_sale_qty);
			holder.txt_return_qty = (TextView)convertView.findViewById(R.id.txt_return_qty);
			holder.txt_new_stock_qty = (TextView)convertView.findViewById(R.id.txt_new_stock_qty);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_item_name.setText(getItem(position).getItemName());
		holder.txt_date.setText(getItem(position).getDate());
		holder.txt_old_stock_qty.setText(getItem(position).getOldStockQty().toString());
		holder.txt_purchase_qty.setText(getItem(position).getPurchaseQty().toString());
		holder.txt_sale_qty.setText(getItem(position).getSaleQty().toString());
		holder.txt_return_qty.setText(getItem(position).getReturnQty().toString());
		holder.txt_new_stock_qty.setText(getItem(position).getNewStockQty().toString());
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_item_name, txt_date, txt_old_stock_qty, txt_purchase_qty, txt_sale_qty, txt_new_stock_qty, txt_return_qty;
	}

}

	

