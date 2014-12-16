package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.AddNewPurchaseVoucherActivity;
import com.ignite.pos.R;
import com.ignite.pos.UpdatePurchaseVoucherActivity;
import com.ignite.pos.UpdateSaleVoucherActivity;
import com.ignite.pos.adapter.ItemListAdapter.Callback;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.PurchaseVoucher;
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
import android.widget.EditText;
import android.widget.TextView;

public class PurchaseConfirmLvAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private List<Object> itemList;
	private LayoutInflater mInflater;
	private Activity aty;
	private String Date, VouNo;
	private DatabaseManager dbManager;
	private ViewHolder holder;

	public PurchaseConfirmLvAdapter(Activity aty, List<Object> listObj) {
		super();
		this.aty = aty;
		this.listVoucher = listObj;
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

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_purchase_confirm, null);
			
			holder = new ViewHolder();
			
			holder.txt_item_name = (TextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_item_code = (TextView) convertView.findViewById(R.id.txt_item_code);
			holder.txt_purchase_price = (TextView)convertView.findViewById(R.id.txt_purchase_price);
			holder.txt_qty = (TextView)convertView.findViewById(R.id.txt_qty);
			holder.txt_marginal_price = (TextView)convertView.findViewById(R.id.txt_marginal_price);
			holder.editTxt_sale_price = (EditText)convertView.findViewById(R.id.editTxt_sale_price);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final PurchaseVoucher pvItem = (PurchaseVoucher) getItem(position);
		
		holder.txt_item_name.setText(pvItem.getItemname());
		holder.txt_item_code.setText(pvItem.getItemid());
		holder.txt_purchase_price.setText(pvItem.getPurchasePrice());
		holder.txt_qty.setText(pvItem.getQty());
		
		//Get item's marginal price & old stock qty
		dbManager = new ItemListController(aty);
		ItemListController itemlistControl = (ItemListController)dbManager;
		itemList = itemlistControl.select(pvItem.getItemid());
		
		String showMarginalPrice = null;
		
		if (itemList != null && itemList.size() > 0) {
			ItemList itemObj = (ItemList) itemList.get(0);

			Log.i("", "Margianl Price: "+itemObj.getMarginalPrice());
			String marginalPrice = null;
			
			if (itemObj.getMarginalPrice().equals("") || itemObj.getMarginalPrice().length() == 0) {
				marginalPrice = "0";
			}else {
				marginalPrice = itemObj.getMarginalPrice();
			}
			
			showMarginalPrice = calculateMarginalPrice(marginalPrice, pvItem.getPurchasePrice()
					, itemObj.getQty(), pvItem.getQty());
			
			Log.i("", "show Marginal Price: "+showMarginalPrice);
			
			holder.txt_marginal_price.setText(showMarginalPrice);
			
			if (itemObj.getSalePrice() != null) {
				if (itemObj.getSalePrice().length() > 0) {
					holder.editTxt_sale_price.setText(itemObj.getSalePrice());
				}
			}
		}
		
		return convertView;
	}
	
	private String calculateMarginalPrice(String oldMarginPrice, String newPurchasePrice, String oldStockQty, String newPurchaseQty){
		
		String marginalPrice = null;
		
		if (oldMarginPrice != null && oldStockQty != null) {
			
			if(Integer.valueOf(oldMarginPrice) > 0 && Integer.valueOf(oldStockQty) > 0){
				if (Integer.valueOf(newPurchasePrice) > 0 && Integer.valueOf(newPurchaseQty) > 0) {
					 
				 	Integer grandTotal = (Integer.valueOf(newPurchasePrice) * Integer.valueOf(newPurchaseQty)) + (Integer.valueOf(oldMarginPrice) * Integer.valueOf(oldStockQty));
					Integer qtyTotal = Integer.valueOf(newPurchaseQty) + Integer.valueOf(oldStockQty);
					
					if (Integer.valueOf(oldStockQty) > 0) {
						marginalPrice = String.valueOf(grandTotal / qtyTotal); 
					}
				}
			}else{
				marginalPrice = newPurchasePrice;
			}
		}else {
			marginalPrice = newPurchasePrice;
		}
		
		return marginalPrice;
	}
	
	static class ViewHolder {
		
		TextView txt_item_name, txt_item_code, txt_qty, txt_purchase_price, txt_marginal_price;
		EditText editTxt_sale_price;
	}

}

	


