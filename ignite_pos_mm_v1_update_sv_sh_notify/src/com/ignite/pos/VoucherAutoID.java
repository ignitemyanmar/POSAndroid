package com.ignite.pos;

import java.util.List;
import com.ignite.pos.database.controller.SaleVouncherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.SaleVouncher;

import android.content.Context;
import android.util.Log;

public class VoucherAutoID extends EntityAutoID{
	private Context mContext;
	private DatabaseManager databaseManager = null;
	private List<SaleVouncher> lastPOSVoucherID;
	private int lastVoucherID;
	
	public VoucherAutoID(Context context) {
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
		databaseManager = new SaleVouncherController(mContext);
		
		lastPOSVoucherID = ((SaleVouncherController) databaseManager).getLastVoucherID();
		
		Log.i("", "Last Voucher Id List: "+lastPOSVoucherID.toString());
		
		try {
			for (SaleVouncher lastID : lastPOSVoucherID) {
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
