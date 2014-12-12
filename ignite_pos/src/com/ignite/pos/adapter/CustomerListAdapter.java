package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.UpdateCustomerInfoEdit;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerListAdapter extends BaseAdapter{

	private List<Object> cusList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private DatabaseManager dbManager;
	private ViewHolder holder;
	
	public CustomerListAdapter(Activity aty , List<Object> buyer) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.cusList = buyer;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return cusList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cusList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = mInflater.inflate(R.layout.activity_update_cus_listitem, null);
			holder = new ViewHolder();
			holder.txtname = (TextView)v .findViewById(R.id.txtName);
			holder.txtcity = (TextView)v .findViewById(R.id.txtCity);
			holder.txtphone = (TextView)v .findViewById(R.id.txtPhone);
			holder.txtaddress = (TextView)v .findViewById(R.id.txtAddress);
			holder.btndelete = (Button)v.findViewById(R.id.btnDelete);
			holder.btndetails = (Button)v.findViewById(R.id.btnViewDetails);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Buyer buyer = (Buyer)cusList.get(position);
		holder.txtname.setText(buyer.getCusName());
		holder.txtcity.setText(buyer.getCusCity());
		holder.txtphone.setText(buyer.getCusPh());
		holder.txtaddress.setText(buyer.getCusAddress());
		
		holder.btndetails.setTag(buyer.getCusName());
		holder.btndetails.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateCustomerInfoEdit.class);
				Bundle bundle = new Bundle();
				bundle.putString("CusName", v.getTag().toString());
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		holder.btndelete.setId(position);
		holder.btndelete.setTag(buyer.getCusName());
		holder.btndelete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new BuyerController(aty);
				BuyerController buyer_control = (BuyerController)dbManager;
				buyer_control.delete(v.getTag().toString());
				
				Log.i("","Delete Data from Database :" + v.getTag().toString());
				
				cusList.remove(v.getId());
				notifyDataSetChanged();
				Toast.makeText(aty, "Delete Successfully",Toast.LENGTH_SHORT).show();
			}
		});
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txtname,txtcity,txtphone,txtaddress;
		Button btndelete, btndetails;
	}

}
