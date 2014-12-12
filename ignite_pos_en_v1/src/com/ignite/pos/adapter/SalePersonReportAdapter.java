package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.adapter.ItemListAdapter.ViewHolder;
import com.ignite.pos.model.SaleVouncher;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SalePersonReportAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;

	public SalePersonReportAdapter(Activity aty, List<Object> listObj) {
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
			
			convertView =  mInflater.inflate(R.layout.list_item_sale_person_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_vno = (TextView) convertView.findViewById(R.id.txt_vNo);
			holder.txt_date = (TextView)convertView.findViewById(R.id.txt_date);
			holder.txt_item = (TextView)convertView.findViewById(R.id.txtItem_name);
			holder.txt_qty = (TextView)convertView.findViewById(R.id.txtQty);
			holder.txt_unitPrice = (TextView)convertView.findViewById(R.id.txtUnitPrice);
			holder.txt_total = (TextView)convertView.findViewById(R.id.txtTotal);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		SaleVouncher saleVoucherItem = (SaleVouncher) getItem(position);
		
		holder.txt_vno.setText(saleVoucherItem.getVid());
		holder.txt_date.setText(saleVoucherItem.getVdate());
		holder.txt_item.setText(saleVoucherItem.getItemname());
		holder.txt_qty.setText(saleVoucherItem.getQty());
		holder.txt_unitPrice.setText(saleVoucherItem.getPrice()+".00");
		holder.txt_total.setText(saleVoucherItem.getItemtotal()+"");
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_vno, txt_date, txt_item, txt_qty, txt_unitPrice, txt_total;
	}

}
