
package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StringSpinnerAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<String> spList;
	private Activity aty;
	
	public StringSpinnerAdapter(Activity aty , List<String> spList) {
		super();
		// TODO Auto-generated constructor stub
		this.spList = spList;
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return spList.size();
	}

	public String getItem(int position) {
		// TODO Auto-generated method stub
		return spList.get(position);
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
        txtTitle.setText(getItem(position));
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
        txtTitle.setText(getItem(position));
		return convertView;
	
		//return getView(position, convertView, parent);
	}

}
