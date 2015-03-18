package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.City;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusListAdapter extends BaseAdapter {
	
	private List<City> city;
	private TextView txtTitle;
	private Activity aty;
	
	public BusListAdapter(Activity aty,List<City> city) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		this.city = city;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return city.size();
	}

	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return city.get(index);
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
	        txtTitle.setText(city.get(position).getName());
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
        txtTitle.setText(city.get(position).getName());
		return convertView;
	}
}
