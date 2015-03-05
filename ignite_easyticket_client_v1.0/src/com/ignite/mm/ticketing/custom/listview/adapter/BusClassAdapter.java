package com.ignite.mm.ticketing.custom.listview.adapter;


import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_plan;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusClassAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Seat_plan> BusClass;
	
	public BusClassAdapter(Activity aty,List<Seat_plan> bus_class) {
		mInflater = LayoutInflater.from(aty);
		this.BusClass = bus_class;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return BusClass.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.bus_class_list_item, null);
			holder = new ViewHolder();
			holder. BusClass=(TextView)convertView.findViewById(R.id.txtBusClass);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.BusClass.setText(BusClass.get(position).getBus_no());
		return convertView;
	}
	
	static class ViewHolder {
		TextView BusClass;
				
	}
}
