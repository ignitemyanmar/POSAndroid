package com.ignite.purchasecostcalculator.adapter;

import java.util.List;

import com.ignite.purchasecostcalculator.ChinaToKyatDetailActivity;
import com.ignite.purchasecostcalculator.ThaiToKyatDetailActivity;
import com.ignite.purchasecostcalculator.R;
import com.ignite.purchasecostcalculator.model.Item;
import com.smk.skscalableview.ScalableTextView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DetailLvAdapter extends BaseAdapter{

	List<Object> items;
	LayoutInflater mInflater;
	
	public DetailLvAdapter(Activity aty, List<Object> items) {
		super();
		
		this.items = items;
		mInflater = LayoutInflater.from(aty);
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	public Item getItem(int position) {
		// TODO Auto-generated method stub
		return (Item) items.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_foregin_to_kyat, null);
			
			holder = new ViewHolder();
			
			holder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_foreign_currency = (TextView) convertView.findViewById(R.id.txt_foreign_currency);
			holder.txt_transport_cost = (TextView) convertView.findViewById(R.id.txt_transport_cost);
			holder.txt_other_cost = (TextView) convertView.findViewById(R.id.txt_other_cost);
			holder.txt_kyat = (TextView) convertView.findViewById(R.id.txt_kyat);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.txt_item_name.setText(getItem(position).getItemName());
		holder.txt_foreign_currency.setText(getItem(position).getPurchasePrice()+"");
		holder.txt_transport_cost.setText(getItem(position).getTransportCost()+"");
		holder.txt_other_cost.setText(getItem(position).getOtherCost()+"");
		
		if (getItem(position).getCurrencyType().equals("china")) {
			//Change China Yuan to Myanmar Kyat
			if (ChinaToKyatDetailActivity.chinaExRate != null) {
				Double mmKyat = getItem(position).getPurchasePrice() / ChinaToKyatDetailActivity.chinaExRate;
				Double totalKyat = mmKyat + getItem(position).getTransportCost() + getItem(position).getOtherCost();
				holder.txt_kyat.setText(String.format("%.0f", totalKyat));
			}
		} else if (getItem(position).getCurrencyType().equals("thai")) {
			//Change Thai Baht to Myanmar Kyat
			if (ThaiToKyatDetailActivity.thaiExRate != null) {
				Double mmKyat = getItem(position).getPurchasePrice() / ThaiToKyatDetailActivity.thaiExRate;
				Double totalKyat = mmKyat + getItem(position).getTransportCost() + getItem(position).getOtherCost();
				holder.txt_kyat.setText(String.format("%.0f", totalKyat));
			}
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_item_name, txt_foreign_currency, txt_transport_cost, txt_other_cost, txt_kyat;
	}

}
