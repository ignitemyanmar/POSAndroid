package com.ignite.pos.database.util;

import java.util.List;

public interface OnSelect {
	public List<Object> selectRecord();
	public List<Object> selectRecordPurchasedItems();
	public List<Object> selectRecordGroupBy(String arg0, String arg1);
	public List<Object> selectRecord(String arg0 , String arg1);
	public List<Object> selectRecordAllByCatSub(String arg0 , String arg1); 
	public List<Object> selectRecord(String arg0 , String arg1, String arg2);
	public List<Object> selectRecord(String arg0);
	public List<Object> selectPurchaseVoubyItemID(String arg0);
	public List<Object> selectRecordByItemCode(String arg0, String arg1);
	public List<Object> selectGroupByItemCode();
	public List<Object> selectRecordOldStockQty(String arg0, String arg1);
	public List<Object> selectRecordByVouID(String arg0);
}
