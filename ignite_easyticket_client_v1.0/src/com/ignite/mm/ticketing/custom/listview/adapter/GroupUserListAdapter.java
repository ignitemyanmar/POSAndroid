package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorGroupUser;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupUserListAdapter extends BaseAdapter{
	
	private List<OperatorGroupUser> OperatorGroupUser;
	private LayoutInflater mInflater;
	
	public GroupUserListAdapter(Activity aty, List<OperatorGroupUser> OperatorGroupUser) {
		super();
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.OperatorGroupUser=OperatorGroupUser;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return OperatorGroupUser.size();
	}

	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return OperatorGroupUser.get(index);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.list_item_group_user, null);
			holder = new ViewHolder();
			holder.color	= (View)view.findViewById(R.id.view_color);
			holder.userName = (TextView)view .findViewById(R.id.txt_group_user);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		holder.userName.setText(OperatorGroupUser.get(position).getUsername());
		
		switch(OperatorGroupUser.get(position).getColor()){
			case 1:
				holder.color.setBackgroundColor(Color.parseColor("#FF8514"));
				break;
			case 2:
				holder.color.setBackgroundColor(Color.parseColor("#4BFFFF"));
				break;
			case 3:
				holder.color.setBackgroundColor(Color.parseColor("#B08620"));
				break;
			case 4:
				holder.color.setBackgroundColor(Color.parseColor("#640F6D"));
				break;
			default:
		}
		
		return view;
	}
	
	static class ViewHolder {
		TextView userName;
		View color;
				
	}

}
