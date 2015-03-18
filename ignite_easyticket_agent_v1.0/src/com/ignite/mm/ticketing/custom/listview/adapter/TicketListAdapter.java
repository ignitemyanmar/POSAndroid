package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.PriceList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TicketListAdapter extends BaseAdapter {

	private ArrayList<PriceList> ticket;
	private LayoutInflater mInflater;
	
	public TicketListAdapter(Activity aty,ArrayList<PriceList> ticketList) {
		super();
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.ticket=ticketList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return ticket.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ticket.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.ticket_item, null);
			holder = new ViewHolder();
			holder.txtprice = (TextView)convertView.findViewById(R.id.ticketprice);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtprice.setText(ticket.get(position).getPrice() +"MMK");
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtprice;
				
	}	

}
