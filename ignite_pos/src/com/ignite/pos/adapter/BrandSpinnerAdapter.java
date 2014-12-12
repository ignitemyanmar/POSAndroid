package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.Brand;
import com.ignite.pos.model.Category;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BrandSpinnerAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<Object> brand_list;
	private Activity aty;
	
	public BrandSpinnerAdapter(Activity aty , List<Object> brand) {
		super();
		// TODO Auto-generated constructor stub
		this.brand_list = brand;
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return brand_list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return brand_list.get(position);
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
        Brand brand = (Brand)brand_list.get(position);
        txtTitle.setText(brand.getBrandName());
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
        Brand brand = (Brand)brand_list.get(position);
        txtTitle.setText(brand.getBrandName());
		return convertView;
	}

}
