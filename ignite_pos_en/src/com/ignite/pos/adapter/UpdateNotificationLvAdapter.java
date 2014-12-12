package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateNotifyEditActivity;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.PurchaseVoucher;
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

public class UpdateNotificationLvAdapter extends BaseAdapter{

	private List<Object> notifyList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private ViewHolder holder;
	private DatabaseManager dbManager;
	private List<Object> pvList;
	
	public UpdateNotificationLvAdapter(Activity aty , List<Object> notifyList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.notifyList = notifyList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return notifyList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notifyList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = mInflater.inflate(R.layout.list_item_update_notification, null);
			holder = new ViewHolder();
			holder.txt_item_code = (TextView) v.findViewById(R.id.txt_item_code);
			holder.txt_item_name = (TextView)v .findViewById(R.id.txt_item_name);
			holder.txt_qty = (TextView)v .findViewById(R.id.txt_notify_qty);
			holder.txt_stock_qty= (TextView)v.findViewById(R.id.txt_stock_qty); 
			holder.txt_supplier_name = (TextView)v .findViewById(R.id.txt_supplier_name);
			holder.btn_delete = (Button)v.findViewById(R.id.btn_delete);
			holder.btn_update = (Button)v.findViewById(R.id.btn_update);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		final ItemList itemObj = (ItemList)getItem(position);
		
		holder.txt_item_code.setText(itemObj.getItemId());
		holder.txt_item_name.setText(itemObj.getItemName());
		holder.txt_qty.setText(itemObj.getNotifyQty()+"");
		holder.txt_stock_qty.setText(itemObj.getQty());
		
		//Get Supplier Name
		dbManager = new PurchaseVoucherController(aty);
		PurchaseVoucherController pvController = (PurchaseVoucherController) dbManager;
		pvList = new ArrayList<Object>();
		pvList = pvController.selectbyItemID(itemObj.getItemId());
		
		Log.i("", "Purchase List By Item ID: "+pvList.get(0).toString());
		
		holder.txt_supplier_name.setText(((PurchaseVoucher)pvList.get(0)).getSupplierName());
		
		//holder.btn_update.setTag(supplier.getSupId());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateNotifyEditActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("itemCode", itemObj.getItemId());
				bundle.putString("itemName", itemObj.getItemName());
				bundle.putInt("notifyQty", itemObj.getNotifyQty());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btn_delete.setId(position);
		holder.btn_delete.setTag(itemObj.getItemId());
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new ItemListController(aty);
				ItemListController item_control = (ItemListController)dbManager;
				
				List<Object> notifyL = new ArrayList<Object>();
				notifyL.add(new ItemList(v.getTag().toString()));
				
				//Change Status (0) for Notify Status 
				item_control.deleteNotify(notifyL);
				
				Log.i("","Change Status (0) for notify item :" + v.getTag().toString());
				
				notifyList.remove(v.getId());
				notifyDataSetChanged();
				
				SKToastMessage.showMessage(aty, itemObj.getItemId()+" Deleted", SKToastMessage.SUCCESS);
			}
		});		
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_item_code, txt_item_name, txt_qty, txt_supplier_name, txt_stock_qty;
		Button btn_delete, btn_update;
	}

}
