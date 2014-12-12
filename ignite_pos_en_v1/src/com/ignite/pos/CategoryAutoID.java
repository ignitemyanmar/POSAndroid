package com.ignite.pos;

import java.util.List;

import com.ignite.pos.database.controller.CategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Category;

import android.content.Context;
import android.util.Log;

public class CategoryAutoID extends EntityAutoID{
	private Context mContext;
	private DatabaseManager databaseManager = null;
	private List<Category> lastPOSCategoryID;
	private int lastCategoryID;
	
	public CategoryAutoID(Context context) {
		super();
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}
	public int generateAutoID()
	{
		int plus = getLastID() + 1;                 
		
		return plus; 
		
	}
	public int getLastID()
	{
		databaseManager = new CategoryController(mContext);
		
		lastPOSCategoryID = ((CategoryController) databaseManager).getLastCategoryID();
		
		Log.i("", "Last Category Id List: "+lastPOSCategoryID.toString());
		
		try {
			for (Category lastID : lastPOSCategoryID) {
		        lastCategoryID = lastID.getCategoryID();
		 	} 
		} catch (Exception e) {
			Log.e("MyERROR","Could not get last categoryID!!!!!!");
			lastCategoryID = 0;
		}
		
		Log.i("","Last ID :" + lastCategoryID); 
		
		return lastCategoryID;
		
	}
}
