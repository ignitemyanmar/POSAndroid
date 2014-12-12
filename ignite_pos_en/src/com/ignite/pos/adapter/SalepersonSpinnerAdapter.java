package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.spSalePerson;

import android.R.color;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SalepersonSpinnerAdapter extends BaseAdapter {

	private TextView txtTitle;
	private List<Object> spList;
	private Activity aty;
	
	public SalepersonSpinnerAdapter(Activity aty , List<Object> saleperson) {
		super();
		// TODO Auto-generated constructor stub
		this.spList = saleperson;
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return spList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return spList.get(position);
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
        spSalePerson buyer = (spSalePerson)spList.get(position);
        txtTitle.setText(buyer.getSpusername());
      //  txtTitle.setTextColor(color.white);
        txtTitle.setSingleLine(true);
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spiner_sub_item_list, null);
        }
		
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        spSalePerson buyer = (spSalePerson)spList.get(position);
        txtTitle.setText(buyer.getSpusername());
		return convertView;
	
		//return getView(position, convertView, parent);
	}

}
