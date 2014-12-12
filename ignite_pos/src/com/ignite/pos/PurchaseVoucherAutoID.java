package com.ignite.pos;

import java.util.List;
import com.ignite.pos.database.controller.PurchaseVoucherController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.PurchaseVoucher;
import android.content.Context;
import android.util.Log;

public class PurchaseVoucherAutoID extends EntityAutoID{
	private Context mContext;
	private DatabaseManager databaseManager = null;
	private List<PurchaseVoucher> lastPurchaseVoucherID;
	private int lastVoucherID;
	
	public PurchaseVoucherAutoID(Context context) {
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
		databaseManager = new PurchaseVoucherController(mContext);
		
		lastPurchaseVoucherID = ((PurchaseVoucherController) databaseManager).getLastVoucherID();
		
		Log.i("", "Last Voucher Id List: "+lastPurchaseVoucherID.toString());
		
		try {
			for (PurchaseVoucher lastID : lastPurchaseVoucherID) {
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
