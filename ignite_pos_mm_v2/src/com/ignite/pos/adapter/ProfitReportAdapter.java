package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.ProfitDetailReportActivity;
import com.ignite.pos.R;
import com.ignite.pos.SaleDetailReportActivity;
import com.ignite.pos.model.Profit;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfitReportAdapter extends BaseAdapter{

	private List<Object> listProfit;
	private LayoutInflater mInflater;
	private Activity aty;
	private String selectedItemCode;

	public ProfitReportAdapter(Activity aty, List<Object> listObj, String selectedItemCode) {
		super();
		this.aty = aty;
		this.listProfit = listObj;
		mInflater = LayoutInflater.from(aty);
		this.selectedItemCode = selectedItemCode;
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
			holder.btn_view_detail = (Button)convertView.findViewById(R.id.btn_view_detail);
			holder.detail_layout = (LinearLayout)convertView.findViewById(R.id.layout_detail);
			convertView.setTag(holder);
		
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Profit profit = (Profit) getItem(position);
		
		if (selectedItemCode.toLowerCase().equals("all")) {
			holder.detail_layout.setVisibility(View.VISIBLE);
			holder.txt_total_discount_price.setText(profit.getTotalDiscount()+"");
			
			Integer profitFinal = profit.getTotalProfit() - profit.getTotalDiscount();
			holder.txt_profit.setText(profitFinal+"");
		}else {
			holder.detail_layout.setVisibility(View.GONE);
			holder.txt_total_discount_price.setText("0");
			holder.txt_profit.setText(profit.getTotalProfit()+"");
		}

		holder.txt_date.setText(profit.getDate());
		
		holder.txt_total_sale_price.setText(profit.getTotalSalePrice().toString());
		
		holder.txt_total_purchase_price.setText(profit.getPurchasePrice().toString());

		holder.btn_view_detail.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent next = new Intent(aty.getApplication(), ProfitDetailReportActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("Date", profit.getDate());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_date, txt_total_sale_price, txt_total_purchase_price, txt_profit, txt_total_discount_price;
		Button btn_view_detail;
		LinearLayout detail_layout;
	}

}
