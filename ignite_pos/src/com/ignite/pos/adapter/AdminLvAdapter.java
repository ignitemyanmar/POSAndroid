package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateAdminAccount;
import com.ignite.pos.database.controller.AdminController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Admin;
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

public class AdminLvAdapter extends BaseAdapter{

	private List<Object> adminList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private ViewHolder holder;
	private DatabaseManager dbManager;
	
	public AdminLvAdapter(Activity aty , List<Object> adminList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.adminList = adminList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return adminList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return adminList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			
			v = mInflater.inflate(R.layout.list_item_admin, null);
			
			holder = new ViewHolder();
			holder.txt_admin = (TextView) v.findViewById(R.id.txt_admin);
			holder.btn_delete = (Button)v.findViewById(R.id.btn_delete);
			holder.btn_update = (Button)v.findViewById(R.id.btn_update);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		final Admin adminObj = (Admin)getItem(position);
		
		holder.txt_admin.setText(adminObj.getAdminname());
		
		//holder.btn_update.setTag(supplier.getSupId());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateAdminAccount.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("userName", adminObj.getAdminname());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btn_delete.setId(position);
		holder.btn_delete.setTag(adminObj.getID());
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new AdminController(aty);
				AdminController admin_control = (AdminController)dbManager;
				admin_control.delete(v.getTag().toString());
				
				Log.i("","Delete Admin from Database :" + adminObj.getAdminname().toString());
				
				adminList.remove(v.getId());
				notifyDataSetChanged();
				
				SKToastMessage.showMessage(aty, adminObj.getAdminname()+" Deleted", SKToastMessage.SUCCESS);
			}
		});	
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_admin;
		Button btn_delete, btn_update;
	}

}
