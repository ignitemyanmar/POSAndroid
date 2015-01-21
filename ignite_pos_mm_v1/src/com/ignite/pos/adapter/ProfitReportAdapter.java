package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.Profit;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProfitReportAdapter extends BaseAdapter{

	private List<Object> listProfit;
	private LayoutInflater mInflater;
	private Activity aty;

	public ProfitReportAdapter(Activity aty, List<Object> listObj) {
		super();
		this.aty = aty;
		this.listProfit = listObj;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listProfit.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listProfit.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_profit_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
			holder.txt_total_sale_price = (TextView)convertView.findViewById(R.id.txt_total_sale_price);
			holder.txt_total_purchase_price = (TextView)convertView.findViewById(R.id.txt_total_purchase_price);
			holder.txt_profit = (TextView)convertView.findViewById(R.id.txt_profit);
			holder.txt_total_discount_price = (TextView)convertView.findViewById(R.id.txt_total_discount_price);
			convertView.setTag(holder);
		
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Profit profit = (Profit) getItem(position);
		
		holder.txt_date.setText(profit.getDate());
		
		holder.txt_total_sale_price.setText(profit.getTotalSalePrice().toString());
		
		holder.txt_total_purchase_price.setText(profit.getPurchasePrice().toString());
		
		holder.txt_total_discount_price.setText(profit.getTotalDiscount()+"");
		
		holder.txt_profit.setText(profit.getTotalProfit().toString());
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_date, txt_total_sale_price, txt_total_purchase_price, txt_profit, txt_total_discount_price;
	}

}
