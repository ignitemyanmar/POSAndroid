package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.ExtraCity;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExtraCityAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<ExtraCity> timeby_agent;
	private Activity aty;
	
	public ExtraCityAdapter(Activity aty, List<ExtraCity> times_by_agent) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		this.timeby_agent = times_by_agent;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return timeby_agent.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return timeby_agent.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 if (convertView == null) {
	        	LayoutInflater mInflater = (LayoutInflater)aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.spiner_item_list, null);
	        }
	        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
	        txtTitle.setText(timeby_agent.get(position).getCity_name());
	        txtTitle.setSingleLine(true);
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)
                    aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spiner_sub_item_list, null);
        }
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);        
        txtTitle.setText(timeby_agent.get(position).getCity_name());
		return convertView;
	}

}
