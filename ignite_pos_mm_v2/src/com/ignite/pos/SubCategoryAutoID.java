package com.ignite.pos;

import java.util.List;

import com.ignite.pos.database.controller.SubCategoryController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SubCategory;

import android.content.Context;
import android.util.Log;

public class SubCategoryAutoID{
	private Context mContext;
	private DatabaseManager databaseManager=null;
	private List<SubCategory> lastPOSsubCategoryID;
	private int lastsubCategoryID;
	
	public SubCategoryAutoID(Context context) {
		super();
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}
	public int generateAutoID()
	{
		int autoID = getLastID() + 1;
		
		return autoID; 
		
	}
	public int getLastID()
	{
		databaseManager=new SubCategoryController(mContext);
		lastPOSsubCategoryID=((SubCategoryController) databaseManager).getLastSubCategoryID();
		try {
			for (SubCategory lstsubcatID : lastPOSsubCategoryID) {
		        lastsubCategoryID=lstsubcatID.getSubCategoryID();
		 	} 
		} catch (Exception e) {
			Log.e("MyERROR","Could not get last categoryID!!!!!!");
			lastsubCategoryID = 0;
		}
		Log.i("","Last ID :" + lastsubCategoryID); 
		return lastsubCategoryID;
		
	}
}
