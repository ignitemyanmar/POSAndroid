package com.ignite.pos.database.controller;

import java.util.ArrayList;
import java.util.List;
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
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleVouncher;

public class LedgerController extends DatabaseManager{

	private Ledger ledger;
	private List<Object> ledger_list;
	
	private static final String TABLE_NAME = "tbl_ledger";
	private static final String[] FIELD_NAME = {"ledgerId","itemId","itemName","date","oldStockQty","purchaseQty","saleQty","newStockQty","returnQty"};
	
	public LedgerController(Context ctx) {
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
		    		FIELD_NAME[4] + " INTEGER NULL," +
		    		FIELD_NAME[5] + " INTEGER NULL," +
		    		FIELD_NAME[6] + " INTEGER NULL," +
		    		FIELD_NAME[7] + " INTEGER NULL," +
		    		FIELD_NAME[8] + " INTEGER NULL)" 
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Ledger ledger = (Ledger) obj;
					values.put(FIELD_NAME[0], ledger.getLedgerId());
					values.put(FIELD_NAME[1], ledger.getItemId());
					values.put(FIELD_NAME[2], ledger.getItemName());
					values.put(FIELD_NAME[3], ledger.getDate());
					values.put(FIELD_NAME[4], ledger.getOldStockQty());
					values.put(FIELD_NAME[5], ledger.getPurchaseQty());
					values.put(FIELD_NAME[6], ledger.getSaleQty());
					values.put(FIELD_NAME[7], ledger.getNewStockQty());
					values.put(FIELD_NAME[8], ledger.getReturnQty());

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
						FIELD_NAME[8]
					};
				
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				ledger_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("TAG", "-----> Cursor Count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Ledger ledger = new Ledger();
				        	ledger.setLedgerId(cursor.getInt(0));
				        	ledger.setItemId(cursor.getString(1));
				        	ledger.setItemName(cursor.getString(2));
				        	ledger.setDate(cursor.getString(3));
				        	ledger.setOldStockQty(cursor.getInt(4));
				        	ledger.setPurchaseQty(cursor.getInt(5));
				        	ledger.setSaleQty(cursor.getInt(6));
				        	ledger.setNewStockQty(cursor.getInt(7));
				        	ledger.setReturnQty(cursor.getInt(8));
				        					        		        	
				        	ledger_list.add(ledger);
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
			return ledger_list;
		}

		public List<Object> selectRecord(String arg0) {
			return null;
			// TODO Auto-generated method stub
			
		}

		public List<Object> selectRecord(String itemCode, String date) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Item Code: "+itemCode);
			Log.i("", "Selected Date: "+date);
			
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
						FIELD_NAME[8]
					};
				
				String[] VALUE;
				String WHERE;
				
					VALUE = new String[2];
					VALUE[0] = date;
					VALUE[1] = itemCode;
					WHERE = FIELD_NAME[3]+" = ? and "+FIELD_NAME[1]+" = ?";
				
				ledger_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Data count of Ledger :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Ledger ledger = new Ledger();
				        	
				        	ledger.setLedgerId(cursor.getInt(0));
				        	ledger.setItemId(cursor.getString(1));
				        	ledger.setItemName(cursor.getString(2));
				        	ledger.setDate(cursor.getString(3));
				        	ledger.setOldStockQty(cursor.getInt(4));
				        	ledger.setPurchaseQty(cursor.getInt(5));
				        	ledger.setSaleQty(cursor.getInt(6));
				        	ledger.setNewStockQty(cursor.getInt(7));
				        	ledger.setReturnQty(cursor.getInt(8));
				        					        		        	
				        	ledger_list.add(ledger);
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
			return ledger_list;
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
						FIELD_NAME[7],
						FIELD_NAME[8]
					};
				
				String[] VALUE;
				String WHERE;
				
				if (itemCode.toLowerCase().equals("all")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = itemCode;
					WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ? and "+FIELD_NAME[1]+" = ? COLLATE NOCASE";
				}
					
				//String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[2]+ " ASC";
				
				ledger_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Ledger ledger = new Ledger();
				        	
				        	ledger.setLedgerId(cursor.getInt(0));
				        	ledger.setItemId(cursor.getString(1));
				        	ledger.setItemName(cursor.getString(2));
				        	ledger.setDate(cursor.getString(3));
				        	ledger.setOldStockQty(cursor.getInt(4));
				        	ledger.setPurchaseQty(cursor.getInt(5));
				        	ledger.setSaleQty(cursor.getInt(6));
				        	ledger.setNewStockQty(cursor.getInt(7));
				        	ledger.setReturnQty(cursor.getInt(8));
				        					        		        	
				        	ledger_list.add(ledger);
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
			return ledger_list;
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

		public List<Object> selectRecordGroupBy(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
	//Select By Item Name
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
					FIELD_NAME[7],
					FIELD_NAME[8]
				};
			
			String[] VALUE;
			String WHERE;
			
			if (itemName.toLowerCase().equals("all")) {
				VALUE = new String[2];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ?";
			}else{
				VALUE = new String[3];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				VALUE[2] = itemName;
				WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ? and "+FIELD_NAME[2]+" = ?";
			}
				
			//String GROUP_BY = FIELD_NAME[0];
			String ORDER_BY = FIELD_NAME[2]+ " ASC";
			
			ledger_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
			
			Log.i("","Data count :" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	Ledger ledger = new Ledger();
			        	
			        	ledger.setLedgerId(cursor.getInt(0));
			        	ledger.setItemId(cursor.getString(1));
			        	ledger.setItemName(cursor.getString(2));
			        	ledger.setDate(cursor.getString(3));
			        	ledger.setOldStockQty(cursor.getInt(4));
			        	ledger.setPurchaseQty(cursor.getInt(5));
			        	ledger.setSaleQty(cursor.getInt(6));
			        	ledger.setNewStockQty(cursor.getInt(7));
			        	ledger.setReturnQty(cursor.getInt(8));
			        					        		        	
			        	ledger_list.add(ledger);
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
		return ledger_list;
	}
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update Ledger:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Ledger ledger = (Ledger) obj;
					//values.put(FIELD_NAME[0], ledger.getLedgerId());
					//values.put(FIELD_NAME[1], ledger.getItemId());
					//values.put(FIELD_NAME[2], ledger.getItemName());
					//values.put(FIELD_NAME[3], ledger.getDate());
					//values.put(FIELD_NAME[4], ledger.getOldStockQty());
					//values.put(FIELD_NAME[5], ledger.getPurchaseQty());
					values.put(FIELD_NAME[6], ledger.getSaleQty());
					//values.put(FIELD_NAME[7], ledger.getNewStockQty());

					String[] VALUE = {ledger.getItemId()};
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
			Log.i("","Update :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Ledger ledger = (Ledger) obj;
					//values.put(FIELD_NAME[0], ledger.getLedgerId());
					values.put(FIELD_NAME[1], ledger.getItemId());
					values.put(FIELD_NAME[2], ledger.getItemName());
					values.put(FIELD_NAME[3], ledger.getDate());
					values.put(FIELD_NAME[4], ledger.getOldStockQty());
					values.put(FIELD_NAME[5], ledger.getPurchaseQty());
					values.put(FIELD_NAME[6], ledger.getSaleQty());
					values.put(FIELD_NAME[7], ledger.getNewStockQty());
					values.put(FIELD_NAME[8], ledger.getReturnQty());

					String[] VALUE = {ledger.getItemId(), ledger.getDate()};
					String WHERE = FIELD_NAME[1] + "=? and "+FIELD_NAME[3]+"=?";
					
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
	};
	
	//Update Sale Qty & Purchase Qty
	public void updateQtyRecord(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Update Ledger:" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				Ledger ledger = (Ledger) obj;
				//values.put(FIELD_NAME[0], ledger.getLedgerId());
				//values.put(FIELD_NAME[1], ledger.getItemId());
				values.put(FIELD_NAME[2], ledger.getItemName());
				values.put(FIELD_NAME[3], ledger.getDate());
				values.put(FIELD_NAME[4], ledger.getOldStockQty());
				values.put(FIELD_NAME[5], ledger.getPurchaseQty());
				values.put(FIELD_NAME[6], ledger.getSaleQty());
				values.put(FIELD_NAME[7], ledger.getNewStockQty());

				String[] VALUE = {ledger.getItemId(), ledger.getDate()};
				String WHERE = FIELD_NAME[1] + " =? and "+FIELD_NAME[3]+" = ?";
				
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
		
		public boolean deleteRecord(List<Object> obj) {
			// TODO Auto-generated method stub
			return false;
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
	
