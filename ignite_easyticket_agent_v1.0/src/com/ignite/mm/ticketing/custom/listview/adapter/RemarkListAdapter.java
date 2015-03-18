package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.Seat_list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RemarkListAdapter extends BaseAdapter{
	
	private List<Seat_list> Seat_list;
	private LayoutInflater mInflater;
	
	public RemarkListAdapter(Activity aty, List<Seat_list> Seat_list) {
		super();
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.Seat_list=Seat_list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return Seat_list.size();
	}

	public Seat_list getItem(int index) {
		// TODO Auto-generated method stub
		return Seat_list.get(index);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.list_item_remark, null);
			holder = new ViewHolder();
			holder.txt_remark = (TextView)view .findViewById(R.id.txt_remark);
			holder.txt_seat_no = (TextView)view .findViewById(R.id.txt_seat_no);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.txt_remark.setText(getItem(position).getRemark());
		holder.txt_seat_no.setText("Seat No. = "+ getItem(position).getSeat_no().toString());
		
		return view;
	}
	
	static class ViewHolder {
		TextView txt_remark;
		TextView txt_seat_no;
				
	}

}
