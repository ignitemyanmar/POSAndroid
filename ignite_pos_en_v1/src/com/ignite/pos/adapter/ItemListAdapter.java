package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ignite.pos.ItemListReport;
import com.ignite.pos.R;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter{
	private List<SaleVouncher> listObj;
	private LayoutInflater mInflater;
	private Activity aty;
	private Callback mCallback;
	private ItemListController dbManager;
	private List<Object> listItems;

	public ItemListAdapter(Activity aty, List<SaleVouncher> obj_list) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.listObj = obj_list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listObj.size();
	}

	public SaleVouncher getItem(int position) {
		// TODO Auto-generated method stub
		return listObj.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView =  mInflater.inflate(R.layout.activity_voucher_list_item, null);
			
			holder = new ViewHolder();
			
			holder.item_name = (TextView)convertView.findViewById(R.id.txtItem_name);
			holder.price = (TextView)convertView.findViewById(R.id.txtUnitPrice);
			holder.total = (TextView)convertView.findViewById(R.id.txtTotal);
			holder.qty = (TextView)convertView.findViewById(R.id.txtQty);
			holder.btnPlus = (TextView)convertView.findViewById(R.id.btnPlus);
			holder.btnMinus = (TextView)convertView.findViewById(R.id.btnMinus);
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
			holder.item_name.setText(getItem(position).getItemname());
			holder.qty.setText(getItem(position).getQty());
			holder.price.setText(getItem(position).getPrice()+"");
			
			Double totalAmount = Integer.valueOf(holder.qty.getText().toString()) * Double.valueOf(holder.price.getText().toString());
			holder.total.setText(totalAmount+"0");
			holder.total.setTag(position);
			
			
			
			holder.btnPlus.setTag(holder);
			holder.btnPlus.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ViewHolder viewHolder = (ViewHolder) v.getTag();
					int qty = Integer.valueOf( viewHolder.qty.getText().toString()) + 1;
					
					//Get Stock Qty
					String ItemID = getItem(position).getItemid();
					
					dbManager = new ItemListController(aty);
					ItemListController controller = (ItemListController) dbManager;
					listItems = new ArrayList<Object>();
					listItems = controller.select(ItemID);
					
					//Stock Qty Control
					if (listItems.size() > 0) {
						ItemList itemList = (ItemList) listItems.get(0);
						Integer stock_qty = Integer.valueOf(itemList.getQty());
						
						if (qty > stock_qty) {
							SKToastMessage.showMessage(aty, "Stock Not Enough!", SKToastMessage.WARNING);
						}else {
							viewHolder.qty.setText(String.valueOf(qty));
							double price = Double.parseDouble(viewHolder.price.getText().toString());
							double total = price * qty;
							viewHolder.total.setText(total+"0");
							
							if(mCallback != null){
								mCallback.onPlusClick(position, price);
							}
						}
					}
					
				}
			});
			
			holder.btnMinus.setTag(holder);
			holder.btnMinus.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ViewHolder viewHolder = (ViewHolder) v.getTag();
					int qty = Integer.valueOf( viewHolder.qty.getText().toString()) - 1;
					if (qty > 0) {
						viewHolder.qty.setText(String.valueOf(qty));
						double price = Double.parseDouble(viewHolder.price.getText().toString());
						double total = price * qty;
						viewHolder.total.setText(total+"0");
						
						if(mCallback != null){
							mCallback.onMinusClick(position, price);
						}
					}
				}
			});
			
		
		return convertView;
	}
	
	public void setCallbackListiner(Callback callback){
		mCallback = callback;
	}
	
	public interface Callback{
		public void onPlusClick(Integer pos,Double price);
		public void onMinusClick(Integer pos,Double price);
		
	}
	
	 static class ViewHolder
	{
		TextView item_name , price, total , qty;
		TextView btnPlus , btnMinus;
	}

}
