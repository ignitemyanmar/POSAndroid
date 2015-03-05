package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.Agent;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AgentListAdapter extends BaseAdapter {

	private List<Agent> operators;
	private Activity aty;
	private TextView txtTitle;

	public AgentListAdapter(Activity aty, List<Agent> list) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		this.operators = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return operators.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return operators.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.spiner_item_list, null);
		}
		txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
		txtTitle.setText(operators.get(position).getName());
		txtTitle.setSingleLine(true);
		return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)
                    aty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spiner_sub_item_list, null);
        }
        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);        
        txtTitle.setText(operators.get(position).getName());
		return convertView;
	}

}
