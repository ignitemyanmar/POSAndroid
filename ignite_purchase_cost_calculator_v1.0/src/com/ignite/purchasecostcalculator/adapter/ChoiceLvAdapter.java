package com.ignite.purchasecostcalculator.adapter;

import java.util.List;

import com.ignite.purchasecostcalculator.R;
import com.ignite.purchasecostcalculator.model.Item;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChoiceLvAdapter extends BaseAdapter{

	List<Object> items;
	LayoutInflater mInflater;
	
	public ChoiceLvAdapter(Activity aty, List<Object> items) {
		super();
		
		this.items = items;
		mInflater = LayoutInflater.from(aty);
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_choice_dialog, null);
			
			holder = new ViewHolder();
			
			holder.txt_choice = (TextView) convertView.findViewById(R.id.txt_choice);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.txt_choice.setText(getItem(position)+"");
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_choice;
	}

}
