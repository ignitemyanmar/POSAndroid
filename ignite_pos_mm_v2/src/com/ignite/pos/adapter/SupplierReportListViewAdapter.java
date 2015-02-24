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
			holder.btn_confirm.setVisibility(View.VISIBLE);
			holder.btn_vou_update.setVisibility(View.VISIBLE);
			holder.btn_vou_update.setText("ျပင္ ရန္");
			holder.btn_delete.setVisibility(View.VISIBLE);
			convertView.setBackgroundResource(R.color.bg_warning);
		}else if (purchaseVoucherItem.getStatus() == 1) {
			holder.btn_confirm.setVisibility(View.GONE);
			holder.btn_vou_update.setText("အေႂကြး ျပင္မည္");
			//holder.btn_vou_update.setVisibility(View.GONE);
			holder.btn_delete.setVisibility(View.GONE);
			convertView.setBackgroundResource(R.color.bg_success);
		}
		
		
		holder.btn_confirm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mCallback != null){
					mCallback.onConfirmClick(position);
				}
			}
		});
		
		holder.btn_view_detail.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent next = new Intent(aty.getApplication(), PurchaseDetailReportActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("VoucherNo", purchaseVoucherItem.getVid());
				bundle.putString("Date", purchaseVoucherItem.getVdate());
				bundle.putString("SupplierName", purchaseVoucherItem.getSupplierName());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btn_vou_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mCallback != null){
					mCallback.onUpdateClick(position);
				}

			}
		});
		
		holder.btn_delete.setId(position);
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mCallback != null){
					mCallback.onDeleteClick(position);
				}
				
			}
		});	
		
		return convertView;
	}
	
    protected void removeItemFromList(int position, String vid) {
    	
        final int deletePosition = position;
        
        AlertDialog.Builder alert = new AlertDialog.Builder(aty);
    
        alert.setTitle("Delete Purchasse Voucher - "+vid+" ?");
        
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        	
        	
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				//Voucher Object that you want to delete
				PurchaseVoucher pv = (PurchaseVoucher) getItem(deletePosition);
				
				//Get Voucher Item List By VouID
				dbManager = new PurchaseVoucherController(aty);
				PurchaseVoucherController pvControl = (PurchaseVoucherController)dbManager;
				List<Object> itemList = new ArrayList<Object>();
				itemList = pvControl.selectRecordByVouID(pv.getVid());
				
				Log.i("", "Voucher Item List: "+itemList);
				
				//Delete in Purchase Table
				dbManager = new PurchaseVoucherController(aty);
				PurchaseVoucherController pv_control = (PurchaseVoucherController)dbManager;
				pv_control.delete(pv.getVid());
				
				Log.i("","Deleted Voucher : " + pv.getVid());
				
				listVoucher.remove(deletePosition);
				notifyDataSetChanged();
				
				SKToastMessage.showMessage(aty, pv.getVid()+" Deleted!", SKToastMessage.SUCCESS);
            	
			}
		});
        
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
      
        alert.show();
        alert.setCancelable(true);
    }
    
	public void setCallbackListiner(Callback callback){
		mCallback = callback;
	}
    
	public interface Callback{
		public void onConfirmClick(Integer pos);
		public void onUpdateClick(Integer pos);
		public void onDeleteClick(Integer pos);
	}

	static class ViewHolder {
		
		TextView txt_vno, txt_date, txt_supplier, txt_vou_total;
		Button btn_view_detail, btn_vou_update, btn_delete, btn_confirm;
	}

}

	

