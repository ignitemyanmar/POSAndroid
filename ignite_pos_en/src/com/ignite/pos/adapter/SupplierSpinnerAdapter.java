package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.Supplier;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SupplierSpinnerAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<Object> supplierList;
	private Activity aty;
	
	public SupplierSpinnerAdapter(Activity aty , List<Object> supplierList) {
		super();
		// TODO Auto-generated constructor stub
		this.supplierList = supplierList;
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return supplierList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return supplierList.get(position);
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
        Supplier supplier = (Supplier)supplierList.get(position);
        
        txtTitle.setText(supplier.getSupCoName());
        
        txtTitle.setSingleLine(true);
        
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spiner_sub_item_list, null);
        }
		
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        Supplier supplier = (Supplier)supplierList.get(position);
        
        txtTitle.setText(supplier.getSupCoName());
        
		return convertView;
		
		//return getView(position, convertView, parent);
	}

}
