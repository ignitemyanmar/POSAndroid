package com.ignite.pos;

import java.util.List;
import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;

import android.content.Context;
import android.util.Log;

public class ItemsAutoID extends EntityAutoID{
	private Context mContext;
	private DatabaseManager databaseManager=null;
	private List<ItemList> lastPOSItemID;
	private int lastCategoryID;
	
	public ItemsAutoID(Context context) {
		super();
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}
	public int generateAutoID()
	{
		int plus = getLastID() +1;
		
		return plus; 
		
	}
	public int getLastID()
	{
		databaseManager = new ItemListController(mContext);
		
		lastPOSItemID = ((ItemListController) databaseManager).getLastItemID();
		try {
			for (ItemList lastID : lastPOSItemID) {
		        lastCategoryID = Integer.valueOf(lastID.getItemId());
		 	} 
		} catch (Exception e) {
			Log.e("MyERROR","Could not get last categoryID!!!!!!");
			lastCategoryID = 0;
		}
		Log.i("","Last ID :" + lastCategoryID); 
		return lastCategoryID;
		
	}
}
