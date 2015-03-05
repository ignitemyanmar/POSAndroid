package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.Menu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public  class MenuListAdapter extends BaseAdapter {
	private List<Menu> mList;
	private LayoutInflater mInflater;
		
	public MenuListAdapter(Activity aty,List<Menu> menuList) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.mList=menuList;
		
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return mList.get(index);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.menu_item, null);
			holder = new ViewHolder();
			holder.Name = (TextView)convertView .findViewById(R.id.titleMenu);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.Name.setText(mList.get(position).getTicketTypeName());
		holder.Name.setCompoundDrawablesWithIntrinsicBounds(mList.get(position).getImage(), 0, 0, 0);
		
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView Name;
		
		
	}
}
