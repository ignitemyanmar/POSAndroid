package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateSalePersonActivity;
import com.ignite.pos.database.controller.spSalePersonController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.spSalePerson;
import com.smk.skalertmessage.SKToastMessage;

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

public class SalePersonLvAdapter extends BaseAdapter{

	private List<Object> salerpersonList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private ViewHolder holder;
	private DatabaseManager dbManager;
	
	public SalePersonLvAdapter(Activity aty , List<Object> salerpersonList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.salerpersonList = salerpersonList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return salerpersonList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return salerpersonList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = mInflater.inflate(R.layout.list_item_sale_person, null);
			holder = new ViewHolder();
			holder.txt_sale_person_name = (TextView) v.findViewById(R.id.txt_sale_person_name);
			holder.btn_delete = (Button)v.findViewById(R.id.btn_delete);
			holder.btn_update = (Button)v.findViewById(R.id.btn_update);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		final spSalePerson spObj = (spSalePerson)getItem(position);
		
		holder.txt_sale_person_name.setText(spObj.getSpusername());
		
		//holder.btn_update.setTag(supplier.getSupId());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateSalePersonActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("userName", spObj.getSpusername());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btn_delete.setId(position);
		holder.btn_delete.setTag(spObj.getSpusername());
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new spSalePersonController(aty);
				spSalePersonController sp_control = (spSalePersonController)dbManager;
				sp_control.delete(v.getTag().toString());
				
				Log.i("","Delete Sale Person from Database :" + v.getTag().toString());
				
				salerpersonList.remove(v.getId());
				notifyDataSetChanged();
				SKToastMessage.showMessage(aty, spObj.getSpusername()+" Deleted", SKToastMessage.SUCCESS);
			}
		});	
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_sale_person_name;
		Button btn_delete, btn_update;
	}

}
