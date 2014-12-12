package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.R.color;
import com.ignite.pos.SaleDetailReportActivity;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SaleVouncher;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SalePersonReportAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;
	private DatabaseManager dbManager;
	private List<Object> item_list;
	private saleCallback mCallback;
	private List<Object> sale_item_list;

	public SalePersonReportAdapter(Activity aty, List<Object> listObj) {
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_sale_person_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_vno = (TextView) convertView.findViewById(R.id.txt_vNo);
			holder.txt_date = (TextView)convertView.findViewById(R.id.txt_date);
			holder.txt_sale_person = (TextView)convertView.findViewById(R.id.txt_sale_person_name);
			holder.txt_vou_total = (TextView)convertView.findViewById(R.id.txt_voucher_total);
			holder.btn_view_detail = (Button)convertView.findViewById(R.id.btn_view_detail);
			holder.btn_vou_update = (Button)convertView.findViewById(R.id.btn_vou_update);
			holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final SaleVouncher sv = (SaleVouncher) getItem(position);
		
		holder.txt_vno.setText(sv.getVid());
		holder.txt_date.setText(sv.getVdate());
		holder.txt_sale_person.setText(sv.getSalePerson());
		holder.txt_vou_total.setText(sv.getTotal());
		
		//Get Sale Voucher Item List 
		//Show Color for Sale Return Voucher
		dbManager = new SaleVouncherController(aty);
		SaleVouncherController sv_controller = (SaleVouncherController)dbManager;
		sale_item_list = new ArrayList<Object>();
		sale_item_list = sv_controller.selectRecordByVouID(sv.getVid());
		
/*		if (sale_item_list != null && sale_item_list.size() > 0) {
			
			for (int i = 0; i < sale_item_list.size(); i++) {
				SaleVouncher sVou = (SaleVouncher)sale_item_list.get(i);
				
				//Sale Return Color 
				if (sVou.getReturnableQty() != 0) {
					
					Log.i("", "Enter Hereeeeeeeeeeeeeeee!! "+sVou.getVid());
					
					//holder.txt_vno.setText(sv.getVid()+"(return)");
					//holder.txt_vno.setTextAppearance(aty, android.R.style.TextAppearance_Small);
					//convertView.setBackgroundResource(R.color.bg_info);
					
					break;
				}
			}
		}*/

		holder.btn_view_detail.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent next = new Intent(aty.getApplication(), SaleDetailReportActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("VoucherNo", sv.getVid());
				bundle.putString("Date", sv.getVdate());
				bundle.putString("SalepersonName", sv.getSalePerson());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		holder.btn_vou_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mCallback != null){
					mCallback.onUpdateClick(position);
				}else {
					Log.i("", "mcallback is null!");
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

	public void setCallbackListiner(saleCallback callback){
		mCallback = callback;
	}
    
	public interface saleCallback{
		public void onUpdateClick(Integer pos);
		public void onDeleteClick(Integer pos);
	}

	static class ViewHolder {
		
		TextView txt_vno, txt_date, txt_sale_person, txt_vou_total;
		Button btn_view_detail, btn_vou_update, btn_delete;
	}

}
