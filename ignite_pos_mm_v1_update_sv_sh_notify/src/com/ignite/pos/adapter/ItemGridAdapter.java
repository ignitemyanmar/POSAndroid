package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.adapter.SubCategoriesListAdapter.ViewHolder;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SubCategory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemGridAdapter extends BaseAdapter {

	private Activity aty;
	private LayoutInflater mInflater;
	private List<Object>item_list;
	private ViewHolder holder;
	
	public ItemGridAdapter(Activity aty , List<Object> itemList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.item_list = itemList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return item_list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return item_list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView =  mInflater.inflate(R.layout.categories_girditem, null);
			holder = new ViewHolder();
			holder.itemName = (TextView)convertView.findViewById(R.id.txtcategories_item);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		ItemList item = (ItemList)item_list.get(position);
		holder.itemName.setText(item.getItemName());
		//holder.itemName.setTag(holder);
		return convertView;
	}
	
	static class ViewHolder
	{
		TextView itemName;
	}
}
