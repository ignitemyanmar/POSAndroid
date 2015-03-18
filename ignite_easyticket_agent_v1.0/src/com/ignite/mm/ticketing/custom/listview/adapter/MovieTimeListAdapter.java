package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.MovieTime;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MovieTimeListAdapter extends BaseAdapter {

	private ArrayList<MovieTime> mTime;
	private LayoutInflater mInflater;
	
	public MovieTimeListAdapter(Activity aty,
			ArrayList<MovieTime> mTime) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.mTime=mTime;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mTime.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTime.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (v == null) {
			v = mInflater.inflate(R.layout.time_item, null);
			holder = new ViewHolder();
			holder.time = (TextView)v .findViewById(R.id.txtTime);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		holder.time.setText(mTime.get(position).getMovieTime());
		
		return v;
	}
	
	static class ViewHolder {
		TextView time;
				
	}
	
}
