package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.R;
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
	public OrderListViewAdapter(Activity aty, List<CreditOrder> _list){
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
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
        	holder.buyer = (TextView) convertView.findViewById(R.id.txt_buyer);
        	holder.phone = (TextView) convertView.findViewById(R.id.txt_phone);
        	holder.name = (TextView) convertView.findViewById(R.id.txt_agent_name);
        	holder.trip = (TextView) convertView.findViewById(R.id.txt_trip);
        	holder.date = (TextView) convertView.findViewById(R.id.txt_date);
        	holder.total_ticket = (TextView) convertView.findViewById(R.id.txt_total_ticket);
        	holder.amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.buyer.setText("Buyer: "+getItem(position).getCustomer());
		holder.phone.setText("Phone: "+getItem(position).getPhone());
		holder.name.setText("Agent: "+getItem(position).getAgent());
		holder.trip.setText(getItem(position).getTrip().toString()+" ["+getItem(position).getTime()+"] "+getItem(position).getClasses());
		holder.date.setText("Order Date: "+getItem(position).getOrderdate());
		holder.total_ticket.setText("Total Ticket: "+getItem(position).getTotalTicket().toString());
		holder.amount.setText("Amount: "+getItem(position).getAmount().toString());
		
		return convertView;
	}
	static class ViewHolder {
		TextView buyer;
		TextView phone;
		TextView name;
		TextView trip;
		TextView date;
		TextView total_ticket;
		TextView amount;
	}
}
