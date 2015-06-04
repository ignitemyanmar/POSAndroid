package com.ignite.mm.ticketing.custom.listview.adapter;

import java.text.NumberFormat;
import java.util.List;

import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.sqlite.database.model.OperatorSeat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OperatorSeatsAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<OperatorSeat> listItem;
	private Activity aty;
	public OperatorSeatsAdapter(Activity aty, List<OperatorSeat> operatorSeats){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = operatorSeats;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public OperatorSeat getItem(int position) {
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
			
        	convertView = mInflater.inflate(R.layout.list_item_time, null);
        	
        	holder.trip_time = (TextView)convertView.findViewById(R.id.txt_time);
        	holder.classes = (TextView)convertView.findViewById(R.id.txt_class);
        	holder.Line = (View)convertView.findViewById(R.id.v_indicator);
        	holder.total_seat = (TextView)convertView.findViewById(R.id.txt_total_seat);
        	holder.txt_price = (TextView)convertView.findViewById(R.id.txt_price);
        	holder.txt_operator_name = (TextView)convertView.findViewById(R.id.txt_operator_name);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_operator_name.setText(getItem(position).getOperator());
		holder.classes.setText(getItem(position).getClass_());

		if(getItem(position).getTime().toLowerCase().contains("am")){
			
			//holder.trip_time.setBackgroundColor(aty.getResources().getColor(R.color.golden_brown_light));
			holder.trip_time.setTextColor(aty.getResources().getColor(R.color.golden_brown_dark));
			//convertView.setBackgroundResource(R.drawable.bg_gold_light2);
		}else{
			//holder.trip_time.setBackgroundColor(aty.getResources().getColor(R.color.dark_blue));
			holder.trip_time.setTextColor(aty.getResources().getColor(R.color.dark_blue));
			//convertView.setBackgroundResource(R.drawable.bg_gold_light);
		}
		
		holder.trip_time.setText(getItem(position).getTime());
		holder.total_seat.setText("Seats : "+(getItem(position).getPermitseatTotal() - getItem(position).getPermitseatSoldtotal()));
		if (holder.txt_price != null) {
			//Change (0,000,000) format
			NumberFormat nf = NumberFormat.getInstance();
			String price = nf.format(Integer.valueOf(getItem(position).getPrice()));
			
			holder.txt_price.setText(price+" Ks");
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView trip_time, classes, sold_seat, total_seat, txt_price, txt_operator_name;
		View Line;
	}
}

