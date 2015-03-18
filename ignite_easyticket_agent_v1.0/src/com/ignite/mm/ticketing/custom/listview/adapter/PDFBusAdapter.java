package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;
import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import android.app.Activity;
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
			holder.txtDestination = (TextView)convertView.findViewById(R.id.txtDestination);
			holder.txtOperatorName = (TextView)convertView.findViewById(R.id.txt_operator_name);
			holder.txtSeatNo = (TextView)convertView.findViewById(R.id.txtSeatNo);
			holder.txtTime = (TextView)convertView.findViewById(R.id.txtTime);
			holder.txtDate = (TextView)convertView.findViewById(R.id.txtDate);
			holder.txtprice = (TextView)convertView.findViewById(R.id.txtprice);
			holder.txtBarcode = (TextView)convertView.findViewById(R.id.txtBarcode);
			holder.txtBusClass = (TextView)convertView.findViewById(R.id.txt_bus_class);
			holder.img_barcode = (ImageView)convertView.findViewById(R.id.img_barcode);
			holder.txt_buyer_name = (TextView)convertView.findViewById(R.id.txt_customer);
			holder.txt_agent_name = (TextView)convertView.findViewById(R.id.txt_user_id);
			
			convertView.setTag(holder);
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.txtPrintDate.setText("Print Date: "+getItem(position).getTodayDate());
		holder.txtPrintTime.setText("Print Time: "+getItem(position).getCurrentTime());
		holder.txtDestination.setText(getItem(position).getTrip());
		holder.txtOperatorName.setText(getItem(position).getOperatorName());
		holder.txtSeatNo.setText("Seat No: "+getItem(position).getSeatNo());
		
		holder.txtTime.setText(getItem(position).getTime());
		holder.txtDate.setText(getItem(position).getDate());
		holder.txtprice.setText(getItem(position).getPrice()+" Ks");
		holder.txtBarcode.setText(getItem(position).getBarcode());
		holder.txtBusClass.setText(getItem(position).getBusClass());
		holder.txt_buyer_name.setText("Passenger : "+getItem(position).getCustomerName());
		holder.txt_agent_name.setText("Agent : "+getItem(position).getUserName());
		
		holder.img_barcode.setImageBitmap(getItem(position).getBarcode_img());
		
		return convertView;
	}

	static class ViewHolder {
		
		ImageView img_barcode;
		TextView txtPrintDate, txtPrintTime, txtBarcode;
		TextView txtDestination, txtOperatorName, txtSeatNo, txtTime, txtDate, txtprice, txtBusClass, txt_buyer_name, txt_agent_name;
	}


}
