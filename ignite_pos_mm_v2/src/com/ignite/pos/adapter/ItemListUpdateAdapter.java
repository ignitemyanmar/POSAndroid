
package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateItemActivity;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
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

public class ItemListUpdateAdapter extends BaseAdapter{

	private List<Object> listItem;
	private LayoutInflater mInflater;
	private Activity aty; 
	private DatabaseManager dbManager;
	private ViewHolder holder;
	private List<Object> item_list;
	
	public ItemListUpdateAdapter(Activity aty , List<Object> listItem) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.listItem = listItem;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItem.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = mInflater.inflate(R.layout.list_item_update_item_list, null);
			holder = new ViewHolder();
			holder.txt_item_code = (TextView)v.findViewById(R.id.txt_item_code);
			holder.txt_item_name = (TextView)v .findViewById(R.id.txt_item_name);
			holder.txt_category = (TextView)v .findViewById(R.id.txt_category);
			holder.btn_delete = (Button)v.findViewById(R.id.btnDelete);
			holder.btn_update = (Button)v.findViewById(R.id.btnUpdate);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		
		final ItemList itemObj = (ItemList)getItem(position);
		holder.txt_item_name.setText(itemObj.getItemName());
		
		
		holder.txt_category.setText(itemObj.getCategoryName());
		
		final String ItemID = itemObj.getItemId();
		holder.txt_item_code.setText(ItemID);
		
		String stockQty;
		
		//Delete Show/Hide
		if (itemObj.getQty().equals("") || itemObj.getQty() == null) {
			stockQty = "0";
		}else {
			stockQty = itemObj.getQty();
		}
		
		if (Integer.valueOf(stockQty) > 0) {
			holder.btn_delete.setVisibility(View.GONE);
		}else {
			holder.btn_delete.setVisibility(View.VISIBLE);
		}
		
		holder.btn_update.setTag(itemObj.getItemName());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateItemActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("ItemID", ItemID);
				bundle.putString("ItemName", itemObj.getItemName());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btn_delete.setId(position);
		holder.btn_delete.setTag(itemObj.getItemId());
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Get ItemList List & Delete
				dbManager = new ItemListController(aty);
				ItemListController ItemList_control = (ItemListController)dbManager;
				
				item_list = new ArrayList<Object>();
				ItemList item = new ItemList();
				item.setItemId(ItemID);
				item_list.add(item);
				
				dbManager = new PurchaseVoucherController(aty);
				PurchaseVoucherController purchaseVoucherController = (PurchaseVoucherController) dbManager;
				List<Object> purchaseVoucherList = new ArrayList<Object>();
				purchaseVoucherList = purchaseVoucherController.selectbyItemID(ItemID);
				if(purchaseVoucherList != null && purchaseVoucherList.size() > 0){
					ItemList_control.hideItem(item_list);
				}else{
					Log.i("", "Hello Delete Item : "+ item_list.toString());
					ItemList_control.deleteItem(item_list);
				}
				
				Log.i("","Hello Delete Data from Database :" + ItemList_control.select().toString());
				
				listItem.remove(v.getId());
				notifyDataSetChanged();
				
				SKToastMessage.showMessage(aty, "ItemList: [ "+itemObj.getItemName()+" ] Deleted!", SKToastMessage.SUCCESS);
			}
		});
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_item_name, txt_item_code, txt_category;
		Button btn_delete, btn_update;
	}

}

