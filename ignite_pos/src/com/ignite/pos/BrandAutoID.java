package com.ignite.pos;

import java.util.List;

import com.ignite.pos.database.controller.BrandController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.Brand;

import android.content.Context;
import android.util.Log;

public class BrandAutoID extends EntityAutoID{
	private Context mContext;
	private DatabaseManager databaseManager = null;
	private List<Brand> lastPOSBrandID;
	private int lastBrandID;
	
	public BrandAutoID(Context context) {
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
		databaseManager = new BrandController(mContext);
		
		lastPOSBrandID = ((BrandController) databaseManager).getLastBrandID();
		
		Log.i("", "Last Brand Id List: "+lastPOSBrandID.toString());
		
		try {
			for (Brand lastID : lastPOSBrandID) {
		        lastBrandID = lastID.getBrandID();
		 	} 
		} catch (Exception e) {
			Log.e("MyERROR","Could not get last categoryID!!!!!!");
			lastBrandID = 0;
		}
		
		Log.i("","Last ID :" + lastBrandID); 
		
		return lastBrandID;
		
	}
}
