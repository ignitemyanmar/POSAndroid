package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleReturn;
import com.ignite.pos.model.SaleVouncher;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SaleReturnAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private Activity aty;
	private List<Object> returnList;
	
	public SaleReturnAdapter(Activity aty, List<Object> returnList) {
		// TODO Auto-generated constructor stub
		super();
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.returnList = returnList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return returnList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return returnList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView =  mInflater.inflate(R.layout.list_item_sale_return, null);
			
			holder = new ViewHolder();
			
			holder.txt_sale_vou_id = (TextView)convertView.findViewById(R.id.txt_sale_vou_id);
			holder.txt_sale_date = (TextView)convertView.findViewById(R.id.txt_sale_date);
			holder.txt_item_code = (TextView)convertView.findViewById(R.id.txt_item_code);
			holder.txt_return_item_name = (TextView)convertView.findViewById(R.id.txt_return_item_name);
			holder.txt_old_qty = (TextView)convertView.findViewById(R.id.txt_old_qty);
			holder.txt_return_qty = (TextView)convertView.findViewById(R.id.txt_return_qty);
			holder.txt_refund_price = (TextView)convertView.findViewById(R.id.txt_refund_price);
			holder.txt_refund_item_total = (TextView)convertView.findViewById(R.id.txt_refund_item_total);
			holder.btn_redo = (ImageButton)convertView.findViewById(R.id.btn_redo);
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
			SaleReturn sr = (SaleReturn)getItem(position);
			
			holder.txt_sale_vou_id.setText(sr.getSaleVouId());
			holder.txt_sale_date.setText(sr.getSaleDate());
			holder.txt_item_code.setText(sr.getItemid());	
			holder.txt_return_item_name.setText(sr.getItemName());
			holder.txt_old_qty.setText(sr.getOldQty()+"");
			holder.txt_return_qty.setText(sr.getReturnQty()+"");
			holder.txt_refund_price.setText(sr.getSalePrice()+"");
			
			Integer refund_item_amount = Integer.valueOf(holder.txt_return_qty.getText().toString()) * Integer.valueOf(holder.txt_refund_price.getText().toString());
			holder.txt_refund_item_total.setText(refund_item_amount+"");
			
			holder.btn_redo.setId(position);
			//holder.btn_return.setTag(sv.getItemid());
			holder.btn_redo.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					SaleVoucherAdapter.returnList.remove(position);
					notifyDataSetChanged();
				}
			});
		
		return convertView;
	}

	static class ViewHolder
		{
			TextView txt_item_code, txt_return_item_name , txt_old_qty, txt_return_qty, txt_refund_price , txt_refund_item_total, txt_sale_date;
			TextView txt_sale_vou_id;
			ImageButton btn_redo;
		}

}



