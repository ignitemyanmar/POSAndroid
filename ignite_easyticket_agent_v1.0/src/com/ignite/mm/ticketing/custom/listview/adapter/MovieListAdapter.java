package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.MovieList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MovieListAdapter extends BaseAdapter{
	
	private ArrayList<MovieList> movieList;
	private LayoutInflater mInflater;
	
	public MovieListAdapter(Activity aty,ArrayList<MovieList> movieList) {
		super();
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.movieList=movieList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return movieList.size();
	}

	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return movieList.get(index);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.movie_item, null);
			holder = new ViewHolder();
			holder.movieName = (TextView)view .findViewById(R.id.MovieTitle);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		holder.movieName.setText(movieList.get(position).getMovietitle());
		
		return view;
	}
	
	static class ViewHolder {
		TextView movieName;
				
	}

}
