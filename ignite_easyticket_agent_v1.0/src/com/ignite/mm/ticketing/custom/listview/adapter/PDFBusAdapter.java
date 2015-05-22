package com.ignite.mm.ticketing.custom.listview.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PDFBusAdapter extends BaseAdapter {

	private ImageView iv;
	private LayoutInflater mInflater;
	private ArrayList<AllBusObject> allBusObject;
	
	public PDFBusAdapter(Activity aty,
			 ArrayList<AllBusObject> allBusObject) {
		// TODO Auto-generated constructor stub
		this.mInflater= LayoutInflater.from(aty);
		this.allBusObject=allBusObject;
		//Log.i("","AllMovie :" + allMovieObject);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return allBusObject.size();
	}

	public AllBusObject getItem(int position) {
		// TODO Auto-generated method stub
		return allBusObject.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			//convertView = mInflater.inflate(R.layout.activity_pdf_bus_header, null);
			
			convertView = mInflater.inflate(R.layout.activity_pdf_bus_voucher, null);
			
			holder = new ViewHolder();
			
			holder.txt_today_date_time = (TextView) convertView.findViewById(R.id.txt_today_date_time);
			holder.txt_order_no = (TextView)convertView.findViewById(R.id.txt_order_no);
			holder.txt_passenger = (TextView)convertView.findViewById(R.id.txt_passenger);
			
			holder.txt_phone = (TextView)convertView.findViewById(R.id.txt_phone);
			holder.txt_nrc = (TextView)convertView.findViewById(R.id.txt_nrc);
			holder.txt_operator = (TextView)convertView.findViewById(R.id.txt_operator);
			holder.txt_trip_class = (TextView)convertView.findViewById(R.id.txt_trip_class);
			holder.txt_trip_date_time = (TextView)convertView.findViewById(R.id.txt_trip_date_time);
			holder.txt_seat_no = (TextView)convertView.findViewById(R.id.txt_seat_no);
			
			holder.txt_ticket_no = (TextView)convertView.findViewById(R.id.txt_ticket_no);
			holder.txt_price = (TextView)convertView.findViewById(R.id.txt_price);
			holder.txt_qty = (TextView)convertView.findViewById(R.id.txt_qty);
			holder.txt_total = (TextView)convertView.findViewById(R.id.txt_total);
			
			convertView.setTag(holder);
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		//holder.txt_vno.setText(Html.fromHtml(sv.getVid()+"(update) <font color=red> * </font>"));
		
		holder.txt_today_date_time.setText(getItem(position).getTodayDate()+" "+getItem(position).getCurrentTime());
		holder.txt_order_no.setText(": "+getItem(position).getBarcode());
				
		if (getItem(position).getCustomerName() != null && getItem(position).getPhone() != null && getItem(position).getNRC() != null) {
			holder.txt_passenger.setText(": "+getItem(position).getCustomerName());
			holder.txt_phone.setText(": "+getItem(position).getPhone());
			holder.txt_nrc.setText(": "+getItem(position).getNRC());
		}
		
		holder.txt_operator.setText(": "+getItem(position).getOperatorName());
		holder.txt_trip_class.setText(getItem(position).getTrip()+" ["+getItem(position).getBusClass()+"]");
		holder.txt_trip_date_time.setText(getItem(position).getDate()+" ("+getItem(position).getTime()+")");
		holder.txt_seat_no.setText(": "+getItem(position).getSeatNo());		
		
		if (getItem(position).getTicketNo() != null) {
			holder.txt_ticket_no.setText(": "+getItem(position).getTicketNo());
		}
		
		//Change (0,000,000) format
		NumberFormat nf = NumberFormat.getInstance();
		String price = nf.format(Integer.valueOf(getItem(position).getPrice()));
		String amount = nf.format(Integer.valueOf(getItem(position).getAmount()));
		
		holder.txt_price.setText(": KS "+price);
		holder.txt_qty.setText(": "+getItem(position).getSeatCount());
		holder.txt_total.setText("TOTAL : KS "+amount);
		
		//holder.img_barcode.setImageBitmap(getItem(position).getBarcode_img());
		
		return convertView;
	}

	static class ViewHolder {
		
		TextView txt_today_date_time, txt_order_no, txt_passenger, txt_phone, txt_nrc, txt_operator, txt_trip_class;
		TextView txt_trip_date_time, txt_seat_no, txt_ticket_no, txt_price, txt_qty, txt_total;
	}


}
