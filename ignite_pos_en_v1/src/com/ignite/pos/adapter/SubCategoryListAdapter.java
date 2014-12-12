package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateSubCategoryActivity;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SubCategory;
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

public class SubCategoryListAdapter extends BaseAdapter{

	private List<Object> subcategoryList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private DatabaseManager dbManager;
	private ViewHolder holder;
	
	public SubCategoryListAdapter(Activity aty , List<Object> subcategoryList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.subcategoryList = subcategoryList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return subcategoryList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return subcategoryList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			
			v = mInflater.inflate(R.layout.list_item_update_subcategory_list, null);
			
			holder = new ViewHolder();
			
			holder.txt_subcategory_name = (TextView)v .findViewById(R.id.txt_subcategory_name);
			holder.btn_delete = (Button)v.findViewById(R.id.btnDelete);
			holder.btn_update = (Button)v.findViewById(R.id.btnUpdate);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		final SubCategory subcategory = (SubCategory)subcategoryList.get(position);
		holder.txt_subcategory_name.setText(subcategory.getSubCategoryName());
		
		final String SubCategoryID = String.valueOf(subcategory.getSubCategoryID());
		
		holder.btn_update.setTag(subcategory.getSubCategoryName());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateSubCategoryActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("SubCatID", SubCategoryID);
				bundle.putString("SubCatName", subcategory.getSubCategoryName());
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btn_delete.setId(position);
		holder.btn_delete.setTag(subcategory.getSubCategoryID());
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new SubCategoryController(aty);
				SubCategoryController subcategory_control = (SubCategoryController)dbManager;
				subcategory_control.delete(v.getTag().toString());
				
				Log.i("","Delete Data from Database :" + v.getTag().toString());
				
				subcategoryList.remove(v.getId());
				notifyDataSetChanged();
				//Toast.makeText(aty, "Delete Successfully",Toast.LENGTH_SHORT).show();
				SKToastMessage.showMessage(aty, "Delete Successfully!", SKToastMessage.SUCCESS);
			}
		});
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_subcategory_name;
		Button btn_delete, btn_update;
	}

}


