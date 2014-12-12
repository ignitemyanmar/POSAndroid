package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.ItemList;
import com.smk.skscalableview.ScalableTextView;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemListReportAdapter extends BaseAdapter{
	
	private List<Object> listItems;
	private LayoutInflater mInflater;
	private Activity aty;

	public ItemListReportAdapter(Activity aty, List<Object> listItems) {
		super();
		this.listItems = listItems;
		mInflater = LayoutInflater.from(aty);
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	public ItemList getItem(int position) {
		// TODO Auto-generated method stub
		return (ItemList) listItems.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Log.i("", "Item Id: "+getItemId(position));
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_items_list_report, null);
			
			holder = new ViewHolder();
			holder.txt_item_name = (ScalableTextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_qty = (ScalableTextView) convertView.findViewById(R.id.txt_qty);
			holder.txt_purchase_price = (ScalableTextView) convertView.findViewById(R.id.txt_purchase_price);
			holder.txt_sale_price = (ScalableTextView) convertView.findViewById(R.id.txt_sale_price);
			holder.txt_marginal_price = (ScalableTextView) convertView.findViewById(R.id.txt_marginal_price);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//Get Purchase Qty (Stock)
		if (getCount() > 0) {
			
			//if (getItem(position).getPurchasePrice().length() > 0 && getItem(position).getSalePrice().length() > 0) {
				holder.txt_item_name.setText(getItem(position).getItemName());
			//}
			holder.txt_qty.setText(getItem(position).getQty());
			holder.txt_purchase_price.setText(getItem(position).getPurchasePrice());
			holder.txt_sale_price.setText(getItem(position).getSalePrice());
			holder.txt_marginal_price.setText(getItem(position).getMarginalPrice());
		}		
		
		return convertView;
	}
	
	static class ViewHolder {
		ScalableTextView txt_item_name;
		ScalableTextView txt_qty;
		ScalableTextView txt_purchase_price;
		ScalableTextView txt_sale_price;
		ScalableTextView txt_marginal_price;
	}

}
