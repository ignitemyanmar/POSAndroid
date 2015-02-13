package com.ignite.pos.database.controller;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.database.util.OnDelete;
import com.ignite.pos.database.util.OnSave;
import com.ignite.pos.database.util.OnSelect;
import com.ignite.pos.database.util.OnUpdate;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleVouncher;

@SuppressLint("DefaultLocale")
public class ProfitController extends DatabaseManager{

	private Profit profit;
	private List<Object> profit_list;
	
	private static final String TABLE_NAME = "tbl_profit";
	private static final String[] FIELD_NAME = {"profitId","itemId","date","marginalPrice","salePrice","saleQty","profit","vid","itemName","discount"};
	
	public ProfitController(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
		setOnSave(saveRecord);
		setOnUpate(updateRecord);
		setOnSelect(selectRecord);
		setOnDelete(deleteRecord);
	}

	@Override
	protected void createTables() {
		// TODO Auto-generated method stub
		 connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME + " (" +
		    		FIELD_NAME[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
		    		FIELD_NAME[1] + " TEXT NULL," + 
		    		FIELD_NAME[2] + " TEXT NULL," +
		    		FIELD_NAME[3] + " TEXT NULL," +
		    		FIELD_NAME[4] + " TEXT NULL," +
		    		FIELD_NAME[5] + " TEXT NULL," +
		    		FIELD_NAME[6] + " TEXT NULL," +
		    		FIELD_NAME[7] + " TEXT NULL," +
		    		FIELD_NAME[8] + " TEXT NULL," +
		    		FIELD_NAME[9] + " INTEGER DEFAULT 0)" 
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("", "Profit List to save: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Profit profit = (Profit) obj;
					values.put(FIELD_NAME[0], profit.getProfitId());
					values.put(FIELD_NAME[1], profit.getItemId());
					values.put(FIELD_NAME[2], profit.getDate());
					values.put(FIELD_NAME[3], profit.getMarginalPrice());
					values.put(FIELD_NAME[4], profit.getSalePrice());
					values.put(FIELD_NAME[5], profit.getSaleQty());
					values.put(FIELD_NAME[6], profit.getProfit());
					values.put(FIELD_NAME[7], profit.getVid());
					values.put(FIELD_NAME[8], profit.getItemName());
					values.put(FIELD_NAME[9], profit.getDiscount());

					db.insert(TABLE_NAME, null, values);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
				if (complete != null) {
					complete.onComplete();
				}
			}
		}
	};
	
	
	public OnSelect selectRecord = new OnSelect() {

		public List<Object> selectRecord() {
			// TODO Auto-generated method stub
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9]
					};
				
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				profit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("TAG", "-----> Cursor Count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Profit profit = new Profit();
				        	profit.setProfitId(cursor.getInt(0));
				        	profit.setItemId(cursor.getString(1));
				        	profit.setDate(cursor.getString(2));
				        	profit.setMarginalPrice(cursor.getInt(3));
				        	profit.setSalePrice(cursor.getInt(4));
				        	profit.setSaleQty(cursor.getInt(5));
				        	profit.setProfit(cursor.getInt(6));
				        	profit.setVid(cursor.getString(7));
				        	profit.setItemName(cursor.getString(8));
				        	profit.setDiscount(cursor.getInt(9));
				        					        		        	
				        	profit_list.add(profit);
				        } while (cursor.moveToNext());
				    }
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					cursor.close();
					db.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if(complete != null){
					complete.onComplete();
				}
			}
			return profit_list;
		}

		public List<Object> selectRecord(String arg0) {
			return null;
			// TODO Auto-generated method stub
			
		}

		public List<Object> selectRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecord(String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Item Code: "+fromDate);
			Log.i("", "Selected Date: "+toDate);
			
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						"SUM(marginalPrice) AS marginalPricetotal",
						"SUM(salePrice * saleQty) AS salePricetotal",
						"SUM(saleQty) AS saleQtytotal",
						"SUM(profit) AS profittotal",
						"SUM(marginalPrice * saleQty) AS purchasePrice",
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9]
					};
				
				String[] VALUE;
				String WHERE;
				
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[2]+" >= ? and "+FIELD_NAME[2]+" <= ?";
					
				String GROUP_BY = FIELD_NAME[2];
				String ORDER_BY = FIELD_NAME[2]+ " ASC";
				
				profit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count of Profit :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Profit profit = new Profit();
				        	profit.setProfitId(cursor.getInt(0));
				        	profit.setItemId(cursor.getString(1));
				        	profit.setDate(cursor.getString(2));
				        	profit.setMarginalPrice(cursor.getInt(3));
				        	profit.setSalePrice(cursor.getInt(4));
				        	profit.setSaleQty(cursor.getInt(5));
				        	profit.setProfit(cursor.getInt(6));
				        	profit.setTotalmarginalPrice(cursor.getInt(7));
				        	profit.setTotalSalePrice(cursor.getInt(8));
				        	profit.setTotalSaleQty(cursor.getInt(9));
				        	profit.setTotalProfit(cursor.getInt(10));
				        	profit.setPurchasePrice(cursor.getInt(11));
				        	profit.setVid(cursor.getString(12));
				        	profit.setItemName(cursor.getString(13));
				        	profit.setDiscount(cursor.getInt(14));
				        	
				        	profit_list.add(profit);
				        } while (cursor.moveToNext());
				    }
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					cursor.close();
					db.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if(complete != null){
					complete.onComplete();
				}
			}
			return profit_list;
			
		}

		public List<Object> selectRecord(String itemCode, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Item Code: "+itemCode);
			Log.i("", "Selected From Date: "+fromDate);
			Log.i("", "Selected To Date: "+toDate);
			
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						"SUM(marginalPrice) AS marginalPricetotal",
						"SUM(salePrice * saleQty) AS salePricetotal",
						"SUM(saleQty) AS saleQtytotal",
						"SUM(profit) AS profittotal",
						"SUM(marginalPrice * saleQty) AS purchasePrice",
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9]
						
					};
				
				String[] VALUE;
				String WHERE;
				
				if (itemCode.toLowerCase().equals("all")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[2]+" >= ? and "+FIELD_NAME[2]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = itemCode;
					WHERE = FIELD_NAME[2]+" >= ? and "+FIELD_NAME[2]+" <= ? and "+FIELD_NAME[1]+" = ? COLLATE NOCASE";
				}
				
				String GROUP_BY = FIELD_NAME[2];
				String ORDER_BY = FIELD_NAME[2]+ " ASC";
				
				profit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Profit profit = new Profit();
				        	
				        	profit.setProfitId(cursor.getInt(0));
				        	profit.setItemId(cursor.getString(1));
				        	profit.setDate(cursor.getString(2));
				        	profit.setMarginalPrice(cursor.getInt(3));
				        	profit.setSalePrice(cursor.getInt(4));
				        	profit.setSaleQty(cursor.getInt(5));
				        	profit.setProfit(cursor.getInt(6));
				        	profit.setTotalmarginalPrice(cursor.getInt(7));
				        	profit.setTotalSalePrice(cursor.getInt(8));
				        	profit.setTotalSaleQty(cursor.getInt(9));
				        	profit.setTotalProfit(cursor.getInt(10));
				        	profit.setPurchasePrice(cursor.getInt(11));
				        	profit.setVid(cursor.getString(12));
				        	profit.setItemName(cursor.getString(13));
				        	profit.setDiscount(cursor.getInt(14));
				        	
				        	profit_list.add(profit);
				        } while (cursor.moveToNext());
				    }
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					cursor.close();
					db.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if(complete != null){
					complete.onComplete();
				}
			}
			return profit_list;
		}
		
		
		

		public List<Object> selectRecordGroupBy() {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectPurchaseVoubyItemID(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectGroupByItemCode() {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordOldStockQty(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordByItemCode(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordByVouID(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordAllByCatSub(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordPurchasedItems() {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordByBuyer(String arg0, String arg1,
				String arg2) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordGroupBy(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
	public List<Object> selectByItemName(String itemName, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		
		Log.i("", "Selected Item Name: "+itemName);
		Log.i("", "Selected From Date: "+fromDate);
		Log.i("", "Selected To Date: "+toDate);
		
		try {
			String[] FROM = {
					FIELD_NAME[0], 
					FIELD_NAME[1],
					FIELD_NAME[2], 
					FIELD_NAME[3],
					FIELD_NAME[4],
					FIELD_NAME[5],
					FIELD_NAME[6],
					"SUM(marginalPrice) AS marginalPricetotal",
					"SUM(salePrice * saleQty) AS salePricetotal",
					"SUM(saleQty) AS saleQtytotal",
					"SUM(profit) AS profittotal",
					"SUM(marginalPrice * saleQty) AS purchasePrice",
					FIELD_NAME[7], 
					FIELD_NAME[8],
					FIELD_NAME[9],
					"SUM(discount) AS discountTotal"
					//"(SELECT SUM(discount) FROM tbl_profit GROUP BY vid) AS discountTotal"
				};
			
			String[] VALUE;
			String WHERE;
			
			if (itemName.toLowerCase().equals("all")) {
				VALUE = new String[2];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				WHERE = FIELD_NAME[2]+" >= ? and "+FIELD_NAME[2]+" <= ?";
			}else{
				VALUE = new String[3];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				VALUE[2] = itemName;
				WHERE = FIELD_NAME[2]+" >= ? and "+FIELD_NAME[2]+" <= ? and "+FIELD_NAME[8]+" = ?";
			}
			
			String GROUP_BY = FIELD_NAME[2];
			String ORDER_BY = FIELD_NAME[2]+ " ASC";
			
			profit_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
			
			Log.i("","Data count :" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	Profit profit = new Profit();
			        	
			        	profit.setProfitId(cursor.getInt(0));
			        	profit.setItemId(cursor.getString(1));
			        	profit.setDate(cursor.getString(2));
			        	profit.setMarginalPrice(cursor.getInt(3));
			        	profit.setSalePrice(cursor.getInt(4));
			        	profit.setSaleQty(cursor.getInt(5));
			        	profit.setProfit(cursor.getInt(6));
			        	profit.setTotalmarginalPrice(cursor.getInt(7));
			        	profit.setTotalSalePrice(cursor.getInt(8));
			        	profit.setTotalSaleQty(cursor.getInt(9));
			        	profit.setTotalProfit(cursor.getInt(10));
			        	profit.setPurchasePrice(cursor.getInt(11));
			        	profit.setVid(cursor.getString(12));
			        	profit.setItemName(cursor.getString(13));
			        	profit.setDiscount(cursor.getInt(14));
			        	profit.setTotalDiscount(cursor.getInt(15));
			        	
			        	profit_list.add(profit);
			        } while (cursor.moveToNext());
			    }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				cursor.close();
				db.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(complete != null){
				complete.onComplete();
			}
		}
		return profit_list;
	}
	
	public List<Object> selectByVidItemidDate(String Vid, String ItemName, String Date) {
		// TODO Auto-generated method stub
		
		Log.i("", "Selected Voucher No: "+Vid);
		Log.i("", "Selected Item ID: "+ItemName);
		Log.i("", "Selected Date: "+Date);
		
		try {
			String[] FROM = {
					FIELD_NAME[0], 
					FIELD_NAME[1],
					FIELD_NAME[2], 
					FIELD_NAME[3],
					FIELD_NAME[4],
					FIELD_NAME[5],
					FIELD_NAME[6],
					FIELD_NAME[7],
					FIELD_NAME[8],
					FIELD_NAME[9]
				};
			
			String[] VALUE;
			String WHERE;
			
			VALUE = new String[3];
			VALUE[0] = Vid;
			VALUE[1] = ItemName;
			VALUE[2] = Date;
			
			WHERE = FIELD_NAME[7]+" = ? and "+FIELD_NAME[8]+" = ? and "+FIELD_NAME[2]+" = ?";
			
			profit_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
			
			Log.i("","Data count in profit:" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	Profit profit = new Profit();
			        	
			        	profit.setProfitId(cursor.getInt(0));
			        	profit.setItemId(cursor.getString(1));
			        	profit.setDate(cursor.getString(2));
			        	profit.setMarginalPrice(cursor.getInt(3));
			        	profit.setSalePrice(cursor.getInt(4));
			        	profit.setSaleQty(cursor.getInt(5));
			        	profit.setProfit(cursor.getInt(6));
			        	profit.setVid(cursor.getString(7));
			        	profit.setItemName(cursor.getString(8));
			        	profit.setDiscount(cursor.getInt(9));
			        	
			        	profit_list.add(profit);
			        } while (cursor.moveToNext());
			    }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				cursor.close();
				db.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(complete != null){
				complete.onComplete();
			}
		}
		return profit_list;
	}
	
	
	public List<Object> selectByDate(String Date) {
		// TODO Auto-generated method stub
		
		Log.i("", "Selected Date: "+Date);
		
		try {
			String[] FROM = {
					FIELD_NAME[0], 
					FIELD_NAME[1],
					FIELD_NAME[2], 
					FIELD_NAME[3],
					FIELD_NAME[4],
					FIELD_NAME[5],
					FIELD_NAME[6],
					FIELD_NAME[7],
					FIELD_NAME[8],
					FIELD_NAME[9],
					"SUM(salePrice * saleQty) AS salePricetotal",
					"SUM(profit) AS profittotal",
					"SUM(marginalPrice * saleQty) AS purchasePrice",
				};
			
			String WHERE;
			
			String[] VALUE = {Date};
			WHERE = FIELD_NAME[2]+" = ?";
			String GROUP_BY = FIELD_NAME[7];
			
			profit_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, null);
			
			Log.i("","Data count in profit by Date:" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	Profit profit = new Profit();
			        	
			        	profit.setProfitId(cursor.getInt(0));
			        	profit.setItemId(cursor.getString(1));
			        	profit.setDate(cursor.getString(2));
			        	profit.setMarginalPrice(cursor.getInt(3));
			        	profit.setSalePrice(cursor.getInt(4));
			        	profit.setSaleQty(cursor.getInt(5));
			        	profit.setProfit(cursor.getInt(6));
			        	profit.setVid(cursor.getString(7));
			        	profit.setItemName(cursor.getString(8));
			        	profit.setDiscount(cursor.getInt(9));
			        	profit.setTotalSalePrice(cursor.getInt(10));
			        	profit.setTotalProfit(cursor.getInt(11));
			        	profit.setPurchasePrice(cursor.getInt(12));
			        	
			        	profit_list.add(profit);
			        } while (cursor.moveToNext());
			    }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				cursor.close();
				db.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(complete != null){
				complete.onComplete();
			}
		}
		return profit_list;
	}
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Profit Profit = (Profit) obj;
					//values.put(FIELD_NAME[0], Profit.getProfitId());
					//values.put(FIELD_NAME[1], Profit.getItemId());
					//values.put(FIELD_NAME[2], Profit.getItemName());
					//values.put(FIELD_NAME[3], Profit.getDate());
					//values.put(FIELD_NAME[4], Profit.getOldStockQty());
					//values.put(FIELD_NAME[5], Profit.getPurchaseQty());
					values.put(FIELD_NAME[6], Profit.getSaleQty());
					//values.put(FIELD_NAME[7], Profit.getNewStockQty());

					String[] VALUE = {Profit.getItemId()};
					String WHERE = FIELD_NAME[1] + "=?";
					
					db.update(TABLE_NAME, values, WHERE,VALUE);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
				if (complete != null) {
					complete.onComplete();
				}
			}
		
		}
		
		public void updateRecord(Object obj) {
			// TODO Auto-generated method stub
			
		}

		public void updateStockQty(List<Object> objList) {
			// TODO Auto-generated method stub
			
		
		}
	};
	
	//Update Sale Total Price
		public void updateTotalPriceRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update Profit:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Profit profit = (Profit) obj;
					values.put(FIELD_NAME[5], profit.getSaleQty());
					values.put(FIELD_NAME[6], profit.getProfit());
					values.put(FIELD_NAME[4], profit.getSalePrice());
					values.put(FIELD_NAME[9], profit.getDiscount());

					String[] VALUE = {profit.getItemName(), profit.getDate(), profit.getVid()};
					String WHERE = FIELD_NAME[8] + " =? and "+FIELD_NAME[2]+" = ? and "+FIELD_NAME[7]+" = ?";
					
					db.update(TABLE_NAME, values, WHERE,VALUE);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
				if (complete != null) {
					complete.onComplete();
				}
			}
		
		}
		
		//Update Purchase Total Price
		public void updateTotalPurchasePrice(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update Profit:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Profit profit = (Profit) obj;
					values.put(FIELD_NAME[3], profit.getMarginalPrice());
					values.put(FIELD_NAME[6], profit.getProfit());

					String[] VALUE = {profit.getItemId(), profit.getDate(), profit.getVid()};
					String WHERE = FIELD_NAME[1] + " =? and "+FIELD_NAME[2]+" = ? and "+FIELD_NAME[7]+" = ?";
					
					db.update(TABLE_NAME, values, WHERE,VALUE);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
				if (complete != null) {
					complete.onComplete();
				}
			}
		
		}
	
	private OnDelete deleteRecord = new OnDelete() {
		
		public boolean deleteRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("", "Profit Obj to delete: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			for (Object obj : objList) {
					
				Profit profitObj = (Profit) obj;
				
				String WHERE = FIELD_NAME[8]+" = ? and "+FIELD_NAME[2]+" = ? and "+FIELD_NAME[7]+" = ?";
				String[] VALUE = {profitObj.getItemName(), profitObj.getDate(), profitObj.getVid()};
				//db.delete(TABLE_NAME, FIELD_NAME[0]+" = ?", new String[]{String.valueOf(itemObj.getItemid())});
				db.delete(TABLE_NAME, WHERE, VALUE);
			}
			db.close();
			return true;
		}
		
		public List<Object> deleteRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public void deleteRecord() {
			// TODO Auto-generated method stub
			return;
		}

		public boolean deleteRecord(String string) {
			/*SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{string});
	        db.close();*/
			return true;
		}
	};
		
	public boolean hasData() {
    	boolean has = false;
    	SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		try {
			if(cursor.getCount() > 0){
				has = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			cursor.close();
			db.close();
		}
		return has;
	}

}
	
