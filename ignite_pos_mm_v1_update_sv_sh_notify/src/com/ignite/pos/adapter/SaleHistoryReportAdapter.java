package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleHistory;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SaleHistoryReportAdapter extends BaseAdapter{

	private List<Object> listVoucher;
	private LayoutInflater mInflater;
	private Activity aty;
	private DatabaseManager dbManager;
	private List<Object> listItem;

	public SaleHistoryReportAdapter(Activity aty, List<Object> listObj) {
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
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView =  mInflater.inflate(R.layout.list_item_sale_history_report, null);
			
			holder = new ViewHolder();
			
			holder.txt_sale_vou_no = (TextView) convertView.findViewById(R.id.txt_sale_vou_no);
			holder.txt_update_date = (TextView)convertView.findViewById(R.id.txt_update_date);
			holder.txt_item_code = (TextView)convertView.findViewById(R.id.txt_item_code);
			holder.txt_item_name = (TextView)convertView.findViewById(R.id.txt_item_name);
			holder.txt_old_qty = (TextView)convertView.findViewById(R.id.txt_old_qty);
			holder.txt_update_qty = (TextView)convertView.findViewById(R.id.txt_update_qty);
			holder.txt_update_person = (TextView)convertView.findViewById(R.id.txt_update_person);
			holder.txt_action = (TextView)convertView.findViewById(R.id.txt_action);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final SaleHistory sh = (SaleHistory) getItem(position);
		
		holder.txt_sale_vou_no.setText(sh.getVid());
		holder.txt_update_date.setText(sh.getUpdateDate());
		holder.txt_item_code.setText(sh.getItemid());
		
//		//Get Item Name by Item id
//		dbManager = new ItemListController(aty);
//		ItemListController itemControl = (ItemListController) dbManager;
//		listItem = new ArrayList<Object>();
//		listItem = itemControl.select(sh.getItemid());
//		
//		if (listItem != null && listItem.size() > 0) {
//			ItemList iL = (ItemList)listItem.get(0);
//			
			holder.txt_item_name.setText(sh.getItemName());
//		}
		
		holder.txt_old_qty.setText(sh.getOldQty()+"");
		holder.txt_update_qty.setText(sh.getNewQty()+"");
		holder.txt_update_person.setText(sh.getUpdatePerson());
		holder.txt_action.setText(sh.getStatus());
		
		return convertView;
	}
	
	static class ViewHolder {
		
		TextView txt_sale_vou_no, txt_update_date, txt_item_code, txt_item_name, txt_old_qty, txt_update_qty, txt_update_person, txt_action;
	}

}


