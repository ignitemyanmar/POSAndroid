
package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.UpdateCategoryActivity;
import com.ignite.pos.database.controller.BuyerController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SubCategory;
import com.smk.skalertmessage.SKToastMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryListAdapter extends BaseAdapter{

	private List<Object> categoryList;
	private List<Object> subCategoryList;
	private List<Object> itemList;
	private LayoutInflater mInflater;
	private Activity aty; 
	private DatabaseManager dbManager;
	private ViewHolder holder;
	private SubCategory subCategory;
	
	public CategoryListAdapter(Activity aty , List<Object> categoryList) {
		super();
		// TODO Auto-generated constructor stub
		this.aty = aty;
		mInflater = LayoutInflater.from(aty);
		this.categoryList = categoryList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return categoryList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categoryList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (v == null) {
			v = mInflater.inflate(R.layout.list_item_update_category_list, null);
			holder = new ViewHolder();
			holder.txt_category_name = (TextView)v .findViewById(R.id.txt_category_name);
			holder.btn_delete = (Button)v.findViewById(R.id.btnDelete);
			holder.btn_update = (Button)v.findViewById(R.id.btnUpdate);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		
		final Category category = (Category)categoryList.get(position);
		holder.txt_category_name.setText(category.getCategoryName());
		
		final String CategoryID = String.valueOf(category.getCategoryID());
		
		holder.btn_update.setTag(category.getCategoryName());
		holder.btn_update.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent next = new Intent(aty.getApplication(), UpdateCategoryActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("CatID", CategoryID);
				bundle.putString("CatName", category.getCategoryName());
				
				next.putExtras(bundle);
				aty.startActivity(next);
			}
		});
		
		//Get Sub-Category List
		dbManager = new SubCategoryController(aty);
		final SubCategoryController subcategory_control = (SubCategoryController)dbManager;
		subCategoryList = subcategory_control.select(CategoryID);
		
		Log.i("", "Cat ID: "+CategoryID);
		Log.i("", "Sub cat list: "+subCategoryList.toString());
		
		String subCatID;
		
		if (subCategoryList.size() > 0) {
			for (int i = 0; i < subCategoryList.size(); i++) {
				subCategory = (SubCategory)subCategoryList.get(i);
				subCatID = String.valueOf(subCategory.getSubCategoryID());
				
				//Permit Delete or Not 
				dbManager = new ItemListController(aty);
				ItemListController item_control = (ItemListController) dbManager ;
				itemList = new ArrayList<Object>();
				itemList = item_control.selectRecordAllByCatSub(CategoryID, subCatID);
				
				Log.i("", "Item List by Cat + Sub-Cat: "+itemList.toString());
				
				if (itemList.size() > 0) {
					holder.btn_delete.setVisibility(View.GONE);
				}else {
					holder.btn_delete.setVisibility(View.VISIBLE);
				}
			}
			
		}
		
		holder.btn_delete.setId(position);
		holder.btn_delete.setTag(category.getCategoryID());
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbManager = new CategoryController(aty);
				CategoryController cat_control = (CategoryController)dbManager;
				cat_control.delete(v.getTag().toString());
				
				Log.i("","Delete Data from Database :" + v.getTag().toString());
				
				categoryList.remove(v.getId());
				notifyDataSetChanged();
				SKToastMessage.showMessage(aty, "Deleted!", SKToastMessage.SUCCESS);
				
			}
		});
		 
		return v;
	}
	
	static class ViewHolder
	{
		TextView txt_category_name;
		Button btn_delete, btn_update;
	}

}

