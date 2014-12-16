package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Category;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BuyerSpinnerAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<Object> buyerList;
	private Activity aty;
	
	public BuyerSpinnerAdapter(Activity aty , List<Object> buyer) {
		super();
		// TODO Auto-generated constructor stub
		this.buyerList = buyer;
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return buyerList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return buyerList.get(position);
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
        Buyer buyer = (Buyer)buyerList.get(position);
        
        txtTitle.setText(buyer.getCusName());
        
        txtTitle.setSingleLine(true);
        
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		/*if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)
                    aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spiner_sub_item_list, null);
        }
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);        
        Buyer b = (Buyer)buyerList.get(position);
        txtTitle.setText(b.getCusName());
        txtTitle.setTextColor(Color.WHITE);
		return convertView;*/
		
		return getView(position, convertView, parent);
	}

}
