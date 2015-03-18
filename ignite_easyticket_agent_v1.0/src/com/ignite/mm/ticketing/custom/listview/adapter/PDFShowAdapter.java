package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.ArrayList;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ignite.barcode.GenerateBarcode;
import com.ignite.mm.ticketing.PDFBusActivity;
import com.ignite.mm.ticketing.PDFTicketActivity;
import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.ShowInfoActivity;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import com.ignite.mm.ticketing.sqlite.database.model.AllShowObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PDFShowAdapter extends BaseAdapter {
	private ImageView iv;
	private TextView barcodeText;
	private LayoutInflater mInflater;
	private int bitmapWidth = 150;
	private int bitmapHeight = 70;
	private ArrayList<AllShowObject> allShowObject;
	private TextView showName,price,Address;
	
	public PDFShowAdapter(Activity aty,
			 ArrayList<AllShowObject> allShowObject) {
		// TODO Auto-generated constructor stub
		this.mInflater= LayoutInflater.from(aty);
		this.allShowObject=allShowObject;
		//Log.i("","AllMovie :" + allMovieObject);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return allShowObject.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return allShowObject.get(position);
	}

	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		v = mInflater.inflate(R.layout.show_ticket_item, null);
		iv = (ImageView)v.findViewById(R.id.img_barcode);
		barcodeText = (TextView)v.findViewById(R.id.txtBarcode);
		
		showName=(TextView)v.findViewById(R.id.txtShow_name);
		showName.setText(allShowObject.get(position).getShowName());
		
		price=(TextView)v.findViewById(R.id.txtprice);
		price.setText( allShowObject.get(position).getPrice() + " MMK");
		
		Address=(TextView)v.findViewById(R.id.txt_Address);
		Address.setText("Address : Kandawgyi ");
		
		getBarcode();
		return v;
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
