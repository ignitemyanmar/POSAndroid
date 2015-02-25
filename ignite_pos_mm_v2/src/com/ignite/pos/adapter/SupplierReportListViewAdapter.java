package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ignite.pos.LoginActivity;
import com.ignite.pos.PurchaseConfirmListActivity;
import com.ignite.pos.PurchaseDetailReportActivity;
import com.ignite.pos.PurchaseUpdateActivity;
import com.ignite.pos.R;
import com.ignite.pos.adapter.ItemListAdapter.Callback;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.LedgerController;
import com.ignite.pos.database.controller.ProfitController;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.PurchaseVoucher;
import com.smk.skalertmessage.SKToastMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ResourceAsColor") public class SupplierReportListViewAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;
	private DatabaseManager dbManager;
	private String adminName;
	private Callback mCallback;

	public SupplierReportListViewAdapter(Activity aty, List<Object> listObj, String adminName) {
		super();
		this.aty = aty;
		this.listVoucher = listObj;
		mInflater = LayoutInflater.from(aty);
		this.adminName = adminName;
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
			
			convertView =  mInflater.inflate(R.layout.list_item_voucher_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_vno = (TextView) convertView.findViewById(R.id.txt_vNo);
			holder.txt_date = (TextView)convertView.findViewById(R.id.txt_date);
			holder.txt_supplier = (TextView)convertView.findViewById(R.id.txt_supplier_name);
			holder.txt_vou_total = (TextView)convertView.findViewById(R.id.txt_voucher_total);
			holder.btn_view_detail = (Button)convertView.findViewById(R.id.btn_view_detail);
			//holder.btn_view_detail.setVisibility(View.GONE);
			holder.btn_vou_update = (Button)convertView.findViewById(R.id.btn_vou_update);
			holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
			holder.btn_confirm = (Button)convertView.findViewById(R.id.btn_confirm);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final PurchaseVoucher purchaseVoucherItem = (PurchaseVoucher) getItem(position);
		
		Log.i("", "Purchase Voucher to confirm: "+purchaseVoucherItem.toString());
		
		holder.txt_vno.setText(purchaseVoucherItem.getVid());
		holder.txt_date.setText(purchaseVoucherItem.getVdate());
		holder.txt_supplier.setText(purchaseVoucherItem.getSupplierName());
		holder.txt_vou_total.setText(purchaseVoucherItem.getGrandtotal());
		
		
		//Show Confirm Button when pending..... 
		if (purchaseVoucherItem.getStatus() == 0) {
			convertView.setBackgroundResource(R.color.bg_warning);
		}else if (purchaseVoucherItem.getStatus() == 1) {
			convertView.setBackgroundResource(R.color.bg_success);
			holder.txt_vno.setText(Html.fromHtml(purchaseVoucherItem.getVid()+"(confirm) <font color=red> * </font>"));
		}
		
		holder.btn_view_detail.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mCallback != null){
					mCallback.onDetailClick(position, v);
				}
			}
		});
		
		return convertView;
	}	
    
	public void setCallbackListiner(Callback callback){
		mCallback = callback;
	}
    
	public interface Callback{
		public void onDetailClick(Integer pos, View v);
	}

	static class ViewHolder {
		
		TextView txt_vno, txt_date, txt_supplier, txt_vou_total;
		Button btn_view_detail, btn_vou_update, btn_delete, btn_confirm;
	}

}

	

