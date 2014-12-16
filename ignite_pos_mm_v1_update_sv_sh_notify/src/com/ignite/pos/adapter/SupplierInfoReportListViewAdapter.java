package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateSupplierInfoEdit;
import com.ignite.pos.database.controller.SupplierController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Supplier;
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

public class SupplierInfoReportListViewAdapter extends BaseAdapter{

	private List<Object> supList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private ViewHolder holder;
	private DatabaseManager dbManager;
	
	public SupplierInfoReportListViewAdapter(Activity aty , List<Object> supplierList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		supList = supplierList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return supList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return supList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = mInflater.inflate(R.layout.list_item_supplier_info_report, null);
			holder = new ViewHolder();
			holder.txt_co_name = (TextView) v.findViewById(R.id.txt_co_name);
			holder.txtname = (TextView)v .findViewById(R.id.txt_sup_name);
			holder.txtcity = (TextView)v .findViewById(R.id.txtCity);
			holder.txtphone = (TextView)v .findViewById(R.id.txtPhone);
			holder.txtaddress = (TextView)v .findViewById(R.id.txtAddress);
			holder.btn_update = (Button)v.findViewById(R.id.btn_update);
			holder.btnDelete = (Button)v.findViewById(R.id.btnDelete);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		final Supplier supplier = (Supplier)supList.get(position);
		
		holder.txt_co_name.setText(supplier.getSupCoName());
		holder.txtname.setText(supplier.getSupName());
		holder.txtcity.setText(supplier.getSupCity());
		holder.txtphone.setText(supplier.getSupPh());
		holder.txtaddress.setText(supplier.getSupAddr());
		
		holder.btn_update.setTag(supplier.getSupId());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateSupplierInfoEdit.class);
				Bundle bundle = new Bundle();
				bundle.putString("SupId", supplier.getSupId().toString());
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btnDelete.setId(position);
		holder.btnDelete.setTag(supplier.getSupId());
		holder.btnDelete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new SupplierController(aty);
				SupplierController supplier_control = (SupplierController)dbManager;
				supplier_control.delete(v.getTag().toString());
				
				Log.i("","Delete Supplier from Database :" + supplier.getSupCoName().toString());
				
				supList.remove(v.getId());
				notifyDataSetChanged();
				
				SKToastMessage.showMessage(aty, supplier.getSupCoName()+" Deleted", SKToastMessage.SUCCESS);
			}
		});			
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_co_name, txtname,txtcity,txtphone,txtaddress;
		Button btn_update, btnDelete;
	}

}
