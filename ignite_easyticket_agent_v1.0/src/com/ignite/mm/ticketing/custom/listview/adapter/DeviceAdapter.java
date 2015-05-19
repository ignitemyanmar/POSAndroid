package com.ignite.mm.ticketing.custom.listview.adapter;

import java.util.List;

import com.ignite.mm.ticketing.agent.R;
import com.ignite.mm.ticketing.custom.listview.adapter.MovieListAdapter.ViewHolder;
import com.ignite.mm.ticketing.sqlite.database.model.City;
import com.ignite.mm.ticketing.sqlite.database.model.Device;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeviceAdapter extends BaseAdapter {
	
	private List<Device> listDevice;
	private LayoutInflater mInflater;
	
	public DeviceAdapter(Activity aty,List<Device> listDevice) {
		super();
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(aty);
		this.listDevice = listDevice;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listDevice.size();
	}

	public Device getItem(int index) {
		// TODO Auto-generated method stub
		return listDevice.get(index);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.list_item_device, null);
			holder = new ViewHolder();
			holder.txt_device_name = (TextView)view .findViewById(R.id.txt_device_name);
			holder.txt_device_address = (TextView)view.findViewById(R.id.txt_device_address);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		holder.txt_device_name.setText(getItem(position).getDeviceName());
		holder.txt_device_address.setText(getItem(position).getDeviceAddress());
		
		return view;
	}
	
	static class ViewHolder {
		TextView txt_device_name, txt_device_address;
				
	}

}

