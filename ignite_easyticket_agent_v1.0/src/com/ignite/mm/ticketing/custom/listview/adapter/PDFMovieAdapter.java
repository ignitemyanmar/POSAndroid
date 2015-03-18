package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ignite.barcode.GenerateBarcode;
import com.ignite.mm.ticketing.PDFMovieActivity;
import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.SelectingSeatActivity;
import com.ignite.mm.ticketing.sqlite.database.model.AllMovieObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PDFMovieAdapter extends BaseAdapter {

	private TextView cinemaName, movieName, ticketNo, price, time , Date;
	private ImageView iv;
	private TextView barcodeText;
	private LayoutInflater mInflater;
	private int bitmapWidth = 150;
	private int bitmapHeight = 70;
	private ArrayList<AllMovieObject> allMovieObject;
	
	public PDFMovieAdapter(Activity aty,
			ArrayList<AllMovieObject> allMovieObject) {
		// TODO Auto-generated constructor stub
		this.mInflater= LayoutInflater.from(aty);
		this.allMovieObject=allMovieObject;
		//Log.i("","AllMovie :" + allMovieObject);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return allMovieObject.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return allMovieObject.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		convertView = mInflater.inflate(R.layout.movie_ticket_item, null);
		iv = (ImageView)convertView.findViewById(R.id.img_barcode);
		barcodeText = (TextView)convertView.findViewById(R.id.txtBarcode);
		cinemaName=(TextView)convertView.findViewById(R.id.txtcinema_name);
		cinemaName.setText(allMovieObject.get(position).getCinemaName());
		
		movieName=(TextView)convertView.findViewById(R.id.txtmoviename);
		movieName.setText(allMovieObject.get(position).getMovietitle());
		
		ticketNo=(TextView)convertView.findViewById(R.id.txtSeatNo);
		ticketNo.setText("Seat No	: " + allMovieObject.get(position).getSeatNo());
		
		price=(TextView)convertView.findViewById(R.id.txtprice);
		//price.setText(allMovieObject.get(position).getDate());
		
		time=(TextView)convertView.findViewById(R.id.txtTime);
		time.setText("Time	: " + allMovieObject.get(position).getMovieTime());
		
		Date=(TextView)convertView.findViewById(R.id.txtDate);
		Date.setText("Date	: " +allMovieObject.get(position).getDate());
		
		getBarcode();
		return convertView;
	}
	
	private void getBarcode() {
		

		// barcode data
		String barcode_data = "FA-0123456";

		// barcode image
		Bitmap bitmap = null;

		try {
			GenerateBarcode code = new GenerateBarcode();
			bitmap = code.encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128,
					bitmapWidth, bitmapHeight);
			/*
			 * GenerateBarcode code = new GenerateBarcode(barcode_data,150,70);
			 * bitmap = code.generateBarCode();
			 */
			iv.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}

		// ly.addView(iv);

		// barcodeText.setGravity(Gravity.CENTER_HORIZONTAL);
		barcodeText.setText(barcode_data);

		// ly.addView(barcodeText);
	}

	


}
