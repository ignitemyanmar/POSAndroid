package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.PurchaseVoucher;
import com.smk.skscalableview.ScalableTextView;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NotificationDetailLvAdapter extends BaseAdapter{
	
	private List<Object> listItems;
	private List<Object> pvList;
	private LayoutInflater mInflater;
	private Activity aty;
	private DatabaseManager dbManager;

	public NotificationDetailLvAdapter(Activity aty, List<Object> listItems) {
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
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_notification_detail, null);
			
			holder = new ViewHolder();
			holder.txt_item_name = (ScalableTextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_item_code = (ScalableTextView) convertView.findViewById(R.id.txt_item_code);
			holder.txt_qty = (ScalableTextView) convertView.findViewById(R.id.txt_qty);
			holder.txt_supplier_name = (ScalableTextView) convertView.findViewById(R.id.txt_supplier_name);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
			holder.txt_item_name.setText(getItem(position).getItemName());
			holder.txt_item_code.setText(getItem(position).getItemId());
			holder.txt_qty.setText(getItem(position).getQty());

			//Get Supplier Name
			dbManager = new PurchaseVoucherController(aty);
			PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
			pvList = new ArrayList<Object>();
			pvList = pvController.selectbyItemID(getItem(position).getItemId());
			
			//Log.i("", "Purchase List By Item ID: "+pvList.get(0).toString());
			
			if (pvList != null && pvList.size() > 0 ) {
				holder.txt_supplier_name.setText(((PurchaseVoucher)pvList.get(0)).getSupplierName());
			}
		
		return convertView;
	}
	
	static class ViewHolder {
		ScalableTextView txt_item_name;
		ScalableTextView txt_item_code;
		ScalableTextView txt_qty;
		ScalableTextView txt_supplier_name;
	}

}
