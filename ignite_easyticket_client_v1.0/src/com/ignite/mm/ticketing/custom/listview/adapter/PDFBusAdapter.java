package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;

import com.ignite.mm.ticketing.client.R;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import android.app.Activity;
import android.text.Html;
import android.util.Log;
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
			
			convertView = mInflater.inflate(R.layout.activity_pdf_bus_header, null);
			
			holder = new ViewHolder();
			
			holder.txtPrintDate = (TextView) convertView.findViewById(R.id.txtPrintDate);
			holder.txtPrintTime = (TextView)convertView.findViewById(R.id.txtPrintTime);
			holder.img_barcode = (ImageView)convertView.findViewById(R.id.img_barcode);
			
			holder.txtBarcode = (TextView)convertView.findViewById(R.id.txtBarcode);
			holder.txt_agent = (TextView)convertView.findViewById(R.id.txt_agent);
			holder.txt_passenger = (TextView)convertView.findViewById(R.id.txt_passenger);
			holder.txt_phone = (TextView)convertView.findViewById(R.id.txt_phone);
			holder.txt_operator = (TextView)convertView.findViewById(R.id.txt_operator);
			holder.txt_trip = (TextView)convertView.findViewById(R.id.txt_trip);
			holder.txt_date = (TextView)convertView.findViewById(R.id.txt_date);
			holder.txt_time = (TextView)convertView.findViewById(R.id.txt_time);
			holder.txt_class = (TextView)convertView.findViewById(R.id.txt_class);
			holder.txt_seat_no = (TextView)convertView.findViewById(R.id.txt_seat_no);
			holder.ticket_nos = (TextView)convertView.findViewById(R.id.ticket_nos);
			holder.txt_seat_total = (TextView)convertView.findViewById(R.id.txt_seat_total);
			holder.txt_price = (TextView)convertView.findViewById(R.id.txt_price);
			holder.txt_discount = (TextView)convertView.findViewById(R.id.txt_discount);
			holder.txt_amount = (TextView)convertView.findViewById(R.id.txt_amount);
			
			convertView.setTag(holder);
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		//holder.txt_vno.setText(Html.fromHtml(sv.getVid()+"(update) <font color=red> * </font>"));
		
		holder.txtPrintDate.setText("Print Date :  "+getItem(position).getTodayDate());
		holder.txtPrintTime.setText("Print Time :  "+getItem(position).getCurrentTime());
		holder.txtBarcode.setText(getItem(position).getBarcode());
				
		holder.txt_agent.setText("Agent  :  "+getItem(position).getUserName());
		
		if (getItem(position).getCustomerName() != null && getItem(position).getPhone() != null) {
			holder.txt_passenger.setText("Passenger   :  "+getItem(position).getCustomerName());
			holder.txt_phone.setText("Phone          :  "+getItem(position).getPhone());
		}
		
		holder.txt_operator.setText("Bus               :  "+getItem(position).getOperatorName());
		holder.txt_trip.setText("Trip               :  "+getItem(position).getTrip());
		holder.txt_date.setText("Dept. Date   :  "+getItem(position).getDate());
		holder.txt_time.setText("Dept. Time   :  "+getItem(position).getTime());
		holder.txt_class.setText("Bus Class     :  "+getItem(position).getBusClass());
		holder.txt_seat_no.setText("Seat No        :  "+getItem(position).getSeatNo());		
		
		holder.txt_seat_total.setText("Seat Total    :  "+getItem(position).getSeatCount());
		holder.txt_price.setText("Price             :  "+getItem(position).getPrice()+".00");
		holder.txt_discount.setText("Disc (%)       :  "+getItem(position).getDiscount()+".00");
		holder.txt_amount.setText("Amount        :  "+getItem(position).getAmount()+".00 Ks");
		if (getItem(position).getTicketNo() != null) {
			holder.ticket_nos.setText(Html.fromHtml(getItem(position).getTicketNo()));
		}
		holder.img_barcode.setImageBitmap(getItem(position).getBarcode_img());
		
		return convertView;
	}

	static class ViewHolder {
		
		ImageView img_barcode;
		TextView txtPrintDate, txtPrintTime, txtBarcode;
		TextView txt_agent, txt_passenger, txt_phone, txt_operator, txt_trip, txt_date, txt_time, txt_class, txt_seat_no;
		TextView ticket_nos, txt_seat_total, txt_price, txt_discount, txt_amount;
	}


}
