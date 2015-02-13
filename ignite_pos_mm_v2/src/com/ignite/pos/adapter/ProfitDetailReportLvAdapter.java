package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.SaleDetailReportActivity;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.SaleVouncher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ProfitDetailReportLvAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;

	public ProfitDetailReportLvAdapter(Activity aty, List<Object> listObj) {
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_profit_detail_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_sale_voucher_no = (TextView)convertView.findViewById(R.id.txt_sale_voucher_no);
			holder.txt_sale_voucher_total = (TextView) convertView.findViewById(R.id.txt_sale_voucher_total);
			holder.txt_marginal_price_total = (TextView)convertView.findViewById(R.id.txt_marginal_price_total);
			holder.txt_discount_price = (TextView)convertView.findViewById(R.id.txt_discount_price);
			holder.txt_voucher_profoit = (TextView)convertView.findViewById(R.id.txt_voucher_profoit);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Profit profit = (Profit) getItem(position);
		
		holder.txt_sale_voucher_no.setText(profit.getVid());
		holder.txt_sale_voucher_total.setText(profit.getTotalSalePrice()+"");
		holder.txt_marginal_price_total.setText(profit.getPurchasePrice()+"");
		holder.txt_discount_price.setText(profit.getDiscount()+"");
		
		Integer profit_voucher = profit.getTotalProfit() - profit.getDiscount();
		holder.txt_voucher_profoit.setText(profit_voucher+"");
		
		return convertView;
	}


	static class ViewHolder {
		
		TextView txt_sale_voucher_no, txt_sale_voucher_total, txt_marginal_price_total, txt_discount_price, txt_voucher_profoit;
	}

}
