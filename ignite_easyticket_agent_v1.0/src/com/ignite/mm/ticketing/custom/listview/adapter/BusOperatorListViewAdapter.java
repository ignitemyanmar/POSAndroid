package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusOperatorListViewAdapter extends BaseAdapter{

	List<Operator> operators;
	LayoutInflater mInflater;
	
	public BusOperatorListViewAdapter(Activity aty, List<Operator> operators) {
		super();
		
		this.operators = operators;
		mInflater = LayoutInflater.from(aty);
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return operators.size();
	}

	public Operator getItem(int position) {
		// TODO Auto-generated method stub
		return operators.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_bus_operator, null);
			
			holder = new ViewHolder();
			
			holder.txt_operator_name = (TextView) convertView.findViewById(R.id.txt_operator_name);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
				
		
		holder.txt_operator_name.setText(getItem(position).getName());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_operator_name;
	}

}
