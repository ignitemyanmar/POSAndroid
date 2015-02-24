package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.CreditSupplier;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CreditSupplierReportAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Object> tempList;
	public CreditSupplierReportAdapter(Activity aty, List<Object> tempList) {
		super();
		this.tempList = tempList;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return tempList.size();
	}

	public CreditSupplier getItem(int arg0) {
		// TODO Auto-generated method stub
		return (CreditSupplier) tempList.get(arg0);
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
			
			convertView =  mInflater.inflate(R.layout.list_item_credit_supplier_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_supplier_name = (TextView) convertView.findViewById(R.id.txt_supplier_name);
			holder.txt_vou_no = (TextView) convertView.findViewById(R.id.txt_vou_num);
			holder.txt_date = (TextView)convertView.findViewById(R.id.txt_date);
			holder.txt_credit_total = (TextView)convertView.findViewById(R.id.txt_credit_total);
			holder.txt_credit_paid_amount = (TextView)convertView.findViewById(R.id.txt_credit_paid_amount);
			holder.txt_credit_left_amount = (TextView)convertView.findViewById(R.id.txt_credit_left_amount);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Log.i("", "Temp List: "+tempList.toString());
		
		holder.txt_supplier_name.setText(getItem(position).getSupplierName());
		holder.txt_vou_no.setText(getItem(position).getPurchaseVoucherID());
		holder.txt_date.setText(getItem(position).getDate());
		holder.txt_credit_total.setText(getItem(position).getCreditTotal()+"");
		holder.txt_credit_paid_amount.setText(getItem(position).getCreditPaidAmount()+"");
		holder.txt_credit_left_amount.setText(getItem(position).getCreditLeftAmount()+"");
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_supplier_name, txt_date, txt_vou_no, txt_credit_total, txt_credit_paid_amount, txt_credit_left_amount;
	}

}

	

