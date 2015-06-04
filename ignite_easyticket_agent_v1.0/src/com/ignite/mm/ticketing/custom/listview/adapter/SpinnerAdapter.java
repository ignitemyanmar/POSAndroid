package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter{


	private TextView txtTitle;
	private List<Object> list_item;
	private Activity aty;
	private TextView txtSpinnerItem;
	
	public SpinnerAdapter(Activity aty , List<Object> list_item) {
		super();
		// TODO Auto-generated constructor stub
		this.list_item = list_item;
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list_item.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_item.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_spinner, null);
        }
		
        txtSpinnerItem = (TextView) convertView.findViewById(R.id.txt_spinner_items);
        //Supplier supplier = (Supplier)supplierList.get(position);
        
        txtSpinnerItem.setText("Choose");
        
        txtSpinnerItem.setSingleLine(true);
        
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_spinner, null);
        }
		
		txtSpinnerItem = (TextView) convertView.findViewById(R.id.txt_spinner_items);
        //Supplier supplier = (Supplier)supplierList.get(position);
        
        txtSpinnerItem.setText("Choose");
        
        txtSpinnerItem.setSingleLine(true);
        
		return convertView;
		
		//return getView(position, convertView, parent);
	}
}
