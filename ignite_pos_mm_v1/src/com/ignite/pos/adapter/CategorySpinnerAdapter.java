package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.model.Category;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategorySpinnerAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<Object> cat_list;
	private Activity aty;
	
	public CategorySpinnerAdapter(Activity aty , List<Object> catgory) {
		super();
		// TODO Auto-generated constructor stub
		this.cat_list = catgory;
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return cat_list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cat_list.get(position);
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
        Category category = (Category)cat_list.get(position);
        txtTitle.setText(category.getCategoryName());
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
        Category category = (Category)cat_list.get(position);
        txtTitle.setText(category.getCategoryName());
		return convertView;
	}

}
