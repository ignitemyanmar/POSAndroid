package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.ShowList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShowListAdapter extends BaseAdapter {
	
	private ArrayList<ShowList> show;
	private LayoutInflater mInflater;
	
	public ShowListAdapter(Activity aty,ArrayList<ShowList> showList) {
		super();
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.show = showList;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return show.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return show.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.show_item, null);
			holder = new ViewHolder();
			holder.showName = (TextView)convertView.findViewById(R.id.txtshow);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.showName.setText(show.get(position).getShowName());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView showName;
				
	}

}
