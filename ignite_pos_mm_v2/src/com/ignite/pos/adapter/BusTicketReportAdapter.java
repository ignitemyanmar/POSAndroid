package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.BusTicketSale;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusTicketReportAdapter extends BaseAdapter{

	private List<Object> listBusTicket;
	private LayoutInflater mInflater;
	private Activity aty;

	public BusTicketReportAdapter(Activity aty, List<Object> listObj) {
		super();
		this.aty = aty;
		this.listBusTicket = listObj;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listBusTicket.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listBusTicket.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_busticket_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_barcode = (TextView) convertView.findViewById(R.id.txt_bar_code);
			holder.txt_date = (TextView)convertView.findViewById(R.id.txt_date);
			holder.txt_customer_name = (TextView)convertView.findViewById(R.id.txt_customer_name);
			holder.txt_operator_name = (TextView)convertView.findViewById(R.id.txt_operator_name);
			holder.txt_trip = (TextView)convertView.findViewById(R.id.txt_trip);
			holder.txt_seat_count = (TextView)convertView.findViewById(R.id.txt_seat_count);
			holder.txt_seat_price = (TextView)convertView.findViewById(R.id.txt_seat_price);
			holder.txt_amount = (TextView)convertView.findViewById(R.id.txt_amount);
			convertView.setTag(holder);
		
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final BusTicketSale sale = (BusTicketSale) getItem(position);

		holder.txt_barcode.setText(sale.getBarcodeNo());
		holder.txt_date.setText(sale.getConfirmDate());
		holder.txt_customer_name.setText(sale.getCustomerName());
		holder.txt_operator_name.setText(sale.getOperatorName());
		
		holder.txt_trip.setText(sale.getTrip());
		holder.txt_seat_count.setText(sale.getSeatCount()+"");
		holder.txt_seat_price.setText(sale.getSeatPrice()+"");
		holder.txt_amount.setText(sale.getSeatCount() * sale.getSeatPrice()+"");

		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_barcode, txt_date, txt_customer_name, txt_operator_name, txt_trip, txt_seat_count, txt_seat_price, txt_amount;
	}

}

