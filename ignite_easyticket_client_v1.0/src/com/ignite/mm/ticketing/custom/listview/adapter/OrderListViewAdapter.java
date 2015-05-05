package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.client.R;
import com.ignite.mm.ticketing.sqlite.database.model.CreditOrder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CreditOrder> listItem;
	private String bus_seats;
	public OrderListViewAdapter(Activity aty, List<CreditOrder> _list, String bus_seats){
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
		this.bus_seats = bus_seats;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public CreditOrder getItem(int position) {
		return listItem.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
        	convertView = mInflater.inflate(R.layout.list_item_credit, null);
        	holder.txt_trip = (TextView) convertView.findViewById(R.id.txt_trip);
        	holder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
        	holder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
        	holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seats);
        	holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
        	holder.txt_order_date = (TextView) convertView.findViewById(R.id.txt_order_date);
        	holder.txt_seat_count = (TextView) convertView.findViewById(R.id.txt_seat_count);
        	holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_trip.setText("Trip:  "+getItem(position).getTrip()+" ["+getItem(position).getOperator()+"]");
		holder.txt_date.setText("Date:  "+getItem(position).getDate());
		holder.txt_time.setText("Time:  "+getItem(position).getTime());
		holder.txt_seats.setText("Seat No:  "+this.bus_seats);
		holder.txt_price.setText("Price:  "+getItem(position).getPrice()+" Ks");
		holder.txt_order_date.setText("Order Date:  "+getItem(position).getOrderdate());
		holder.txt_seat_count.setText("Total Seats:  "+getItem(position).getTotalTicket());
		holder.txt_amount.setText("Amount: "+getItem(position).getAmount()+" Ks");
		
		return convertView;
	}
	static class ViewHolder {
		TextView txt_trip, txt_date, txt_time, txt_seats, txt_price, txt_order_date, txt_seat_count, txt_amount;
	}
}
