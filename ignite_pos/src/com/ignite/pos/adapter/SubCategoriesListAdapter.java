package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.model.SubCategory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SubCategoriesListAdapter extends BaseAdapter {
	private Activity aty;
	private LayoutInflater mInflater;
	private List<Object> sub_cat_list;
	private ViewHolder holder;

	public SubCategoriesListAdapter(Activity aty , List<Object> subCat) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.sub_cat_list = subCat;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return sub_cat_list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return sub_cat_list.get(position);
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
			holder.subcategories = (TextView)convertView.findViewById(R.id.txtcategories_item);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		SubCategory subCategory = (SubCategory)sub_cat_list.get(position);
		holder.subcategories.setText(subCategory.getSubCategoryName());
		//holder.subcategories.setTag(holder);
		/*holder.categories.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(aty, "SubCategories item clicked",Toast.LENGTH_SHORT).show();
				//sale_activity.getSubCategory();
				
			}
		});*/
		return convertView;
	}

	static class ViewHolder
	{
		TextView subcategories;
	}

}
