package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateBuyerInfoEdit;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import com.smk.skalertmessage.SKToastMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BuyerInfoReportListViewAdapter extends BaseAdapter{

	private List<Object> buyerList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private ViewHolder holder;
	private DatabaseManager dbManager;
	
	public BuyerInfoReportListViewAdapter(Activity aty , List<Object> buyerList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.buyerList = buyerList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return buyerList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return buyerList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = mInflater.inflate(R.layout.list_item_buyer_info_report, null);
			holder = new ViewHolder();
			holder.txt_buyer_name = (TextView) v.findViewById(R.id.txt_buyer_name);
			holder.txtcity = (TextView)v .findViewById(R.id.txtCity);
			holder.txtphone = (TextView)v .findViewById(R.id.txtPhone);
			holder.txtaddress = (TextView)v .findViewById(R.id.txtAddress);
			holder.btn_update = (Button)v.findViewById(R.id.btn_update);
			holder.btnDelete = (Button)v.findViewById(R.id.btnDelete);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		final Buyer buyer = (Buyer)buyerList.get(position);
		
		holder.txt_buyer_name.setText(buyer.getBuyerName());
		holder.txtcity.setText(buyer.getBuyerCity());
		holder.txtphone.setText(buyer.getBuyerPhone());
		holder.txtaddress.setText(buyer.getBuyerAddress());
		
		holder.btn_update.setTag(buyer.getBuyerId());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateBuyerInfoEdit.class);
				Bundle bundle = new Bundle();
				bundle.putString("BuyerID", buyer.getBuyerId().toString());
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btnDelete.setId(position);
		holder.btnDelete.setTag(buyer.getBuyerId());
		holder.btnDelete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new BuyerController(aty);
				BuyerController buyer_control = (BuyerController)dbManager;
				buyer_control.delete(v.getTag().toString());
				
				Log.i("","Delete Buyer from Database :" + buyer.getBuyerName());
				
				buyerList.remove(v.getId());
				notifyDataSetChanged();
				
				SKToastMessage.showMessage(aty, buyer.getBuyerName()+" Deleted", SKToastMessage.SUCCESS);
			}
		});			
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_buyer_name, txtcity,txtphone,txtaddress;
		Button btn_update, btnDelete;
	}

}
