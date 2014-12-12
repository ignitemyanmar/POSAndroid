package com.ignite.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.R;
import com.ignite.pos.database.controller.BrandController;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Brand;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.smk.skscalableview.ScalableTextView;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemListReportAdapter extends BaseAdapter{
	
	private List<Object> listItems;
	private LayoutInflater mInflater;
	//private Callback mCallbacks;
	private DatabaseManager dbManager;
	private Activity aty;
	private List<Object> listCategory;
	private List<Object> listBrand;
	private Category category;
	private Brand brand;

	public ItemListReportAdapter(Activity aty, List<Object> listItems) {
		super();
		this.listItems = listItems;
		mInflater = LayoutInflater.from(aty);
		this.aty = aty;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	public ItemList getItem(int position) {
		// TODO Auto-generated method stub
		return (ItemList) listItems.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Log.i("", "Item Id: "+getItemId(position));
		
		ViewHolder holder = null;
		
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.list_item_items_list_report, null);
			
			holder = new ViewHolder();
			holder.txt_category = (ScalableTextView) convertView.findViewById(R.id.txt_category);
			holder.txt_brand = (ScalableTextView) convertView.findViewById(R.id.txt_brand);
			holder.txt_item_name = (ScalableTextView) convertView.findViewById(R.id.txt_item_name);
			holder.txt_qty = (ScalableTextView) convertView.findViewById(R.id.txt_qty);
			holder.txt_unit_price = (ScalableTextView) convertView.findViewById(R.id.txt_unit_price);
			holder.txt_total = (ScalableTextView) convertView.findViewById(R.id.txt_total);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//Get Category Name by Category ID
		dbManager = new CategoryController(this.aty);
		CategoryController categoryController = (CategoryController) dbManager;
		listCategory = new ArrayList<Object>();
		listCategory = categoryController.select(getItem(position).getCategoryId());
		
		Log.i("", "Category List for Item Report: "+listCategory.toString());
		
		if (listCategory.size() > 0) {
			category = (Category)listCategory.get(0);
			holder.txt_category.setText(category.getCategoryName());
		}
		
		
		//Get Brand Name by Brand ID
		dbManager = new BrandController(this.aty);
		BrandController brandController = (BrandController) dbManager;
		listBrand = new ArrayList<Object>();
		listBrand = brandController.select(getItem(position).getBrandId());
		
		Log.i("", "Brand List for Item Report: "+listBrand.toString());
		
		if (listBrand.size() > 0) {
			brand = (Brand)listBrand.get(0);
			holder.txt_brand.setText(brand.getBrandName());
		}
		
		holder.txt_item_name.setText(getItem(position).getItemName());
		
		Log.i("", "Item Name: "+getItem(position).getItemName());
		
		holder.txt_qty.setText(getItem(position).getQty());
		holder.txt_unit_price.setText(getItem(position).getItemPrice()+".00");
		
		
		return convertView;
	}
	
	static class ViewHolder {
		ScalableTextView txt_category;
		ScalableTextView txt_brand;
		ScalableTextView txt_item_name;
		ScalableTextView txt_qty;
		ScalableTextView txt_unit_price;
		ScalableTextView txt_total;
	}

}
