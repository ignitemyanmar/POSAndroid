package com.ignite.pos.adapter;

import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.model.PurchaseVoucher;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class autocompleteAdapter extends BaseAdapter{

	private TextView txtTitle;
	private List<Object> voucherList;
	private Activity aty;
	
	
	public autocompleteAdapter(Activity aty, List<Object> voucherList) {
		super();
		this.aty = aty;
		this.voucherList = voucherList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return voucherList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return voucherList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spiner_item_list, null);
        }
		
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        PurchaseVoucher pVoucher = (PurchaseVoucher)voucherList.get(position);
        
        txtTitle.setText(pVoucher.getVid());
        
        txtTitle.setSingleLine(true);
        
		return convertView;
		
	}

}
