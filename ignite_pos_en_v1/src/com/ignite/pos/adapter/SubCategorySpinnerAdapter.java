package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.model.SubCategory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubCategorySpinnerAdapter extends BaseAdapter{

	private Activity aty;
	private TextView txtTitle;
	private List<Object>subCategory;
	
	public SubCategorySpinnerAdapter(Activity aty , List<Object>sub_category) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		this.subCategory = sub_category;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return subCategory.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return subCategory.get(position);
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
        SubCategory subcategory = (SubCategory)subCategory.get(position);
        txtTitle.setText(subcategory.getSubCategoryName());
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
        SubCategory subcat = (SubCategory)subCategory.get(position);
        txtTitle.setText(subcat.getSubCategoryName());
		return convertView;
	}

}
