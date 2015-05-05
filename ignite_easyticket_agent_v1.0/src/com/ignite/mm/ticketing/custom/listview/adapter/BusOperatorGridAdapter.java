package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.client.R;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusOperatorGridAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private List<Operator> listOperator;
	private Activity aty;
	
	public BusOperatorGridAdapter(Activity aty, List<Operator> _list){
		mInflater = LayoutInflater.from(aty);
		listOperator = _list;
		this.aty = aty;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listOperator.size();
	}

	public Operator getItem(int position) {
		return listOperator.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			holder = new ViewHolder();
        	convertView = mInflater.inflate(R.layout.list_item_trips_city, null);
        	holder.operator = (TextView)convertView.findViewById(R.id.txt_trips_city);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.operator.setBackgroundResource(R.drawable.border_radius_orange);
		holder.operator.setTextColor(aty.getResources().getColor(R.color.black));

		holder.operator.setText(getItem(position).getName());
		
		return convertView;
	}
	

	static class ViewHolder {
		TextView operator;
	}
	
}


