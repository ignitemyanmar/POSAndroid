package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.PDFBusActivity;
import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.sqlite.database.model.ThreeDaySale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ThreeDaySalesLvAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<ThreeDaySale> listItem;
	private Activity aty;
	
	public ThreeDaySalesLvAdapter(Activity aty, List<ThreeDaySale> _list){
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		listItem = _list;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public ThreeDaySale getItem(int position) {
		return listItem.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
        	convertView = mInflater.inflate(R.layout.list_item_threeday_sales, null);
        	
        	holder.txt_sale_date = (TextView) convertView.findViewById(R.id.txt_sale_date);
        	holder.txt_customer_name = (TextView) convertView.findViewById(R.id.txt_customer_name);
        	holder.txt_trip_operator = (TextView) convertView.findViewById(R.id.txt_trip_operator);
        	holder.txt_trip_date_time_class = (TextView) convertView.findViewById(R.id.txt_trip_date_time_class);
        	holder.txt_seats = (TextView) convertView.findViewById(R.id.txt_seats);
        	holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
        	holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
        	holder.btn_print = (Button) convertView.findViewById(R.id.btn_print);
        	
        	convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_sale_date.setText("Sale Date :  "+getItem(position).getDate());
		holder.txt_customer_name.setText(getItem(position).getCustomerName()+" ["+getItem(position).getCustomerPhone()+"]");
		holder.txt_trip_operator.setText(getItem(position).getTrip()+" ["+getItem(position).getOperator()+"]");
		holder.txt_trip_date_time_class.setText("["+getItem(position).getDepartureDate()+" - "
				+getItem(position).getTime()+"] "+getItem(position).getClass_()+" ["+getItem(position).getPrice()+" Ks]");
		holder.txt_seats.setText(getItem(position).getSeatNo());
		//holder.txt_price.setText("Price :  "+getItem(position).getPrice()+" Ks");
		holder.txt_amount.setText(getItem(position).getTotalAmount()+" Ks");
		
		holder.btn_print.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent showVoucher = new Intent(aty.getApplication(), PDFBusActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("from_intent", "from_threeday_sales");
				bundle.putString("Operator_Name", getItem(position).getOperator());
				bundle.putString("from_to", getItem(position).getTrip());
				bundle.putString("date", getItem(position).getDepartureDate());
				bundle.putString("time", getItem(position).getTime());
				bundle.putString("classes", getItem(position).getClass_());
				bundle.putString("selected_seat", getItem(position).getSeatNo());
				bundle.putString("Price", getItem(position).getPrice());
				bundle.putString("sale_order_no", getItem(position).getOrderId());
				bundle.putString("ConfirmDate", getItem(position).getDate());
				bundle.putString("ConfirmTime", "");
				bundle.putString("BuyerName", getItem(position).getCustomerName());
				bundle.putString("BuyerPhone", getItem(position).getCustomerPhone());
				bundle.putString("BuyerNRC", getItem(position).getCustomerNrc());
				bundle.putString("TicketNo", getItem(position).getTicketNo());
				bundle.putString("SeatCount", String.valueOf(getItem(position).getTicketQty()));
				
				showVoucher.putExtras(bundle);
				aty.startActivity(showVoucher);
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_sale_date, txt_customer_name, txt_trip_operator, txt_trip_date_time_class, txt_seats, txt_price, txt_amount;
		Button btn_print;
	}
}
