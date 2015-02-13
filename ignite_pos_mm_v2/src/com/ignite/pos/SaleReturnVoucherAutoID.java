package com.ignite.pos;

import java.util.List;

import com.ignite.pos.database.controller.SaleReturnController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SaleReturn;
import android.content.Context;
import android.util.Log;

public class SaleReturnVoucherAutoID extends EntityAutoID{
	private Context mContext;
	private DatabaseManager databaseManager = null;
	private List<SaleReturn> lastVoucherList;
	private int lastVoucherID;
	
	public SaleReturnVoucherAutoID(Context context) {
		super();
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}
	public String generateAutoID()
	{
		String plus = getAutoID(getLastID());                 
		
		return plus; 
		
	}
	public int getLastID()
	{
		databaseManager = new SaleReturnController(mContext);
		
		lastVoucherList = ((SaleReturnController) databaseManager).getLastVoucherID();
		
		Log.i("", "Last Voucher Id List: "+lastVoucherList.toString());
		
		try {
			for (SaleReturn lastID : lastVoucherList) {
				lastVoucherID = Integer.valueOf(lastID.getVid());
		 	} 
		} catch (Exception e) {
			Log.e("MyERROR","Could not get last categoryID!!!!!!");
			lastVoucherID = 0;
		}
		
		Log.i("","Last ID :" + lastVoucherID); 
		
		return lastVoucherID;
		
	}
}

