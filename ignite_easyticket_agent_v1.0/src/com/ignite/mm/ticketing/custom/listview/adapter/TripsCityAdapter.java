package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.sqlite.database.model.OnlineSalePermitTrips;
import com.ignite.mm.ticketing.sqlite.database.model.TripsCollection;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TripsCityAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<TripsCollection> listItem;
	private Activity aty;
	
	public TripsCityAdapter(Activity aty, List<TripsCollection> onlineSalePermitTrips){
		mInflater = LayoutInflater.from(aty);
		listItem = onlineSalePermitTrips;
		this.aty = aty;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public TripsCollection getItem(int position) {
		return listItem.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
        	convertView = mInflater.inflate(R.layout.list_item_trips_city, null);
        	holder.TripCity = (TextView)convertView.findViewById(R.id.txt_trips_city);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(getItem(position).getFrom_id().equals("13")){
			//holder.TripCity.setBackgroundColor(aty.getResources().getColor(R.color.transparent_dark_green));
			holder.TripCity.setBackgroundResource(R.drawable.btn_gold_light);
			holder.TripCity.setTextColor(aty.getResources().getColor(R.color.black));
			
		}else{
			//holder.TripCity.setBackgroundColor(aty.getResources().getColor(R.color.transparent_dark_blue));
			holder.TripCity.setBackgroundResource(R.drawable.btn_gold);
			holder.TripCity.setTextColor(aty.getResources().getColor(R.color.white));
		}
		holder.TripCity.setText(getItem(position).getFrom()+" - "+getItem(position).getTo());
		return convertView;
	}
	static class ViewHolder {
		TextView TripCity;
	}
}
