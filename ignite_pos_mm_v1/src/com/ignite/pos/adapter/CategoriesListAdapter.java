package com.ignite.pos.adapter;

import java.util.List;

import com.ignite.pos.R;
import com.ignite.pos.model.Category;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoriesListAdapter extends BaseAdapter{

	private Activity aty;
	private List<Object> categories_list;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private Category category;
	
	public CategoriesListAdapter(Activity aty , List<Object> listobj) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.categories_list = listobj;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return categories_list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categories_list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position,View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView =  mInflater.inflate(R.layout.categories_girditem, null);
			holder = new ViewHolder();
			holder.categories = (TextView)convertView.findViewById(R.id.txtcategories_item);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		category = (Category)categories_list.get(position);
		holder.categories.setText(category.getCategoryName());
		//holder.categories.setTag(holder);
		/*holder.categories.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(aty, "Categories item clicked",Toast.LENGTH_SHORT).show();
				
				dbManager = new SubCategoryController(aty);
				category = (Category)categories_list.get(position);
				categoryID = String.valueOf(category.getCategoryID());
				SubCategoryController sub_control = (SubCategoryController)dbManager;
				sub_category_list = new ArrayList<Object>();
				sub_category_list = sub_control.select(categoryID);
				//Log.i("","Get SubCategory List :"+ sub_category_list.toString());
				if(sub_category_list != null && sub_category_list.size()>0)
				{
					grid = (GridView)v.findViewById(R.id.gvCategories);
					grid.setAdapter(new SubCategoriesListAdapter(aty, sub_category_list));
					Log.i("","Set Adapter :" + sub_category_list.toString());
				}
				
			}

		});*/
		return convertView;
	}
	
	static class ViewHolder
	{
		TextView categories;
	}

}
