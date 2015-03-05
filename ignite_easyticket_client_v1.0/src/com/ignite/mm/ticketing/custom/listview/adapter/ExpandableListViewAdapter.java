package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.MovieCinema;
import com.ignite.mm.ticketing.sqlite.database.model.MovieDate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

	private List<MovieDate> movieDate;
	private HashMap<Integer, ArrayList<MovieCinema>> movieCinema;
	private ViewHolder holder;
	private LayoutInflater mInflater;

	public ExpandableListViewAdapter(Activity aty, List<MovieDate> movieDate2,
			HashMap<Integer, ArrayList<MovieCinema>> movieCinema2) {
		mInflater = LayoutInflater.from(aty);
		this.movieDate = movieDate2;
		this.movieCinema = movieCinema2;

	}

	public MovieCinema getChild(int groupPosition, int childPosition) {
		return this.movieCinema.get(this.movieDate.get(groupPosition)).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return this.movieCinema.get(groupPosition).size();
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View v, ViewGroup parent) {

		if (v == null) {
			v = mInflater.inflate(R.layout.moviedate_listitem, null);
			holder = new ViewHolder();
			holder.cinema = (TextView) v.findViewById(R.id.txtcinema);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		//Log.i("","Cinema Size : "+movieCinema.size());
		holder.cinema.setText(movieCinema.get(groupPosition).get(childPosition).getCinemaName());
		return v;
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.movieDate.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View v,
			ViewGroup parent) {

		if (v == null) {
			v = mInflater.inflate(R.layout.moviedate_listgroup, null);
			holder = new ViewHolder();
			holder.date = (TextView) v.findViewById(R.id.date);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.date.setText(movieDate.get(groupPosition).getDate());

		return v;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	private class ViewHolder {
		public TextView cinema, date;

	}
}
