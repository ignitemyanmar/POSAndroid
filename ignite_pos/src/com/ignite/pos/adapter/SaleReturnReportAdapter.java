package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleReturn;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SaleReturnReportAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;
	private DatabaseManager dbManager;
	private List<Object> listItem;

	public SaleReturnReportAdapter(Activity aty, List<Object> listObj) {
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
			
			convertView =  mInflater.inflate(R.layout.list_item_sale_return_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_return_vou_no = (TextView) convertView.findViewById(R.id.txt_return_vou_no);
			holder.txt_return_date = (TextView)convertView.findViewById(R.id.txt_return_date);
			holder.txt_item_code = (TextView)convertView.findViewById(R.id.txt_item_code);
			holder.txt_item_name = (TextView)convertView.findViewById(R.id.txt_item_name);
			holder.txt_old_qty = (TextView)convertView.findViewById(R.id.txt_old_qty);
			holder.txt_return_qty = (TextView)convertView.findViewById(R.id.txt_return_qty);
			holder.txt_price = (TextView)convertView.findViewById(R.id.txt_price);
			holder.txt_total = (TextView)convertView.findViewById(R.id.txt_total);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final SaleReturn sr = (SaleReturn) getItem(position);
		
		holder.txt_return_vou_no.setText(sr.getVid());
		holder.txt_return_date.setText(sr.getReturnDate());
		holder.txt_item_code.setText(sr.getItemid());
		
//		//Get Item Name by Item id
//		dbManager = new ItemListController(aty);
//		ItemListController itemControl = (ItemListController) dbManager;
//		listItem = new ArrayList<Object>();
//		listItem = itemControl.select(sr.getItemid());
//		
//		if (listItem != null && listItem.size() > 0) {
//			ItemList iL = (ItemList)listItem.get(0);
//			
			holder.txt_item_name.setText(sr.getItemName());
//		}
		
		holder.txt_old_qty.setText(sr.getOldQty()+"");
		holder.txt_return_qty.setText(sr.getReturnQty()+"");
		holder.txt_price.setText(sr.getSalePrice()+"");
		holder.txt_total.setText(sr.getItemTotal()+"");
		
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_return_vou_no, txt_return_date, txt_item_code, txt_item_name, txt_old_qty, txt_return_qty, txt_price, txt_total;
	}

}

