package com.ignite.mm.ticketing.custom.listview.adapter;


import com.ignite.mm.ticketing.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class MoviePriceAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private String[] price;

	public MoviePriceAdapter(Activity aty, String[] prices) {
		mInflater = LayoutInflater.from(aty);
		this.price = prices;
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.movie_price_list_item, null);
			holder = new ViewHolder();
			holder. price=(TextView)convertView.findViewById(R.id.tPrice);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.price.setText(price[position]+"KS");
		return convertView;
	}
	static class ViewHolder {
		TextView price;
				
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return price.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
