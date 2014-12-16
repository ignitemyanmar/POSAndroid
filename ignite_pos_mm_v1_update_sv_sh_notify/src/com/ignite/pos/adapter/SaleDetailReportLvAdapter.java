package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateSaleVoucherActivity;
import com.ignite.pos.adapter.SalePersonReportAdapter.saleCallback;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleVouncher;

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

public class SaleDetailReportLvAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;
	private DatabaseManager dbManager;
	private List<Object> item_list;
	private String Date, VouNo;
	private saleCallback mCallback;

	public SaleDetailReportLvAdapter(Activity aty, List<Object> listObj, String date, String Vno) {
		super();
		this.aty = aty;
		this.listVoucher = listObj;
		Date = date;
		VouNo = Vno;
		mInflater = LayoutInflater.from(aty);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listVoucher.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listVoucher.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_purchase_detail_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_qty = (TextView)convertView.findViewById(R.id.txt_qty);
			holder.txt_unit_price = (TextView)convertView.findViewById(R.id.txt_unit_price);
			holder.txt_item_total = (TextView)convertView.findViewById(R.id.txt_item_total);
			holder.btn_update = (Button)convertView.findViewById(R.id.btn_update);
			holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final SaleVouncher saleVoucherItem = (SaleVouncher) getItem(position);
		
		holder.txt_item_name.setText(saleVoucherItem.getItemname());
		holder.txt_qty.setText(saleVoucherItem.getQty());
		holder.txt_unit_price.setText(saleVoucherItem.getPrice());
		holder.txt_item_total.setText(saleVoucherItem.getItemtotal());
		
		holder.btn_update.setTag(saleVoucherItem.getItemid());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mCallback != null){
					mCallback.onUpdateClick(position);
				}else {
					Log.i("", "mcallback is null!");
				}

			}
		});
		
		/*holder.btn_delete.setId(position);
	//	holder.btn_delete.setTag(itemObj.getItemId());
		holder.btn_delete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Get Sale Voucher Item List & Delete
				dbManager = new SaleVouncherController(aty);
				SaleVouncherController sv_control = (SaleVouncherController)dbManager;
				
				item_list = new ArrayList<Object>();
				item_list.add(new ItemList(ItemID));
				
				ItemList_control.deleteItem(item_list);
				
				Log.i("","Delete Data from Database :" + ItemList_control.select().toString());
				
				listItem.remove(v.getId());
				notifyDataSetChanged();
				
				SKToastMessage.showMessage(aty, "ItemList: [ "+itemObj.getItemName()+" ] Deleted!", SKToastMessage.SUCCESS);
			}
		});*/
		
		return convertView;
	}
	
	public void setCallbackListiner(saleCallback callback){
		mCallback = callback;
	}
    
	public interface saleCallback{
		public void onUpdateClick(Integer pos);
	}
	
	static class ViewHolder {
		
		TextView txt_item_name, txt_qty, txt_unit_price, txt_item_total;
		Button btn_update, btn_delete;
	}

}

	

