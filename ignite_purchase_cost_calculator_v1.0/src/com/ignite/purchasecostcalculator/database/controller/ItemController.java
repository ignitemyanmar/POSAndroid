package com.ignite.purchasecostcalculator.database.controller;

import java.util.ArrayList;
import java.util.List;
import com.ignite.purchasecostcalculator.database.util.DatabaseManager;
import com.ignite.purchasecostcalculator.database.util.OnDelete;
import com.ignite.purchasecostcalculator.database.util.OnSave;
import com.ignite.purchasecostcalculator.database.util.OnSelect;
import com.ignite.purchasecostcalculator.database.util.OnUpdate;
import com.ignite.purchasecostcalculator.model.Item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ItemController extends DatabaseManager{

	private Item item;
	private List<Object> itemList;
	
	private static final String TABLE_NAME = "tbl_item";
	private static final String[] FIELD_NAME = {"itemId","itemName","purchasePrice","transportCost","otherCost","currencyType"};
	
	public ItemController(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
		setOnSave(saveRecord);
		setOnSelect(selectRecord);
		setOnUpate(updateRecord);
		setOnDelete(deleteRecord);
	}

	@Override
	protected void createTables() {
		// TODO Auto-generated method stub
		 connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME + " (" +
		    		FIELD_NAME[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
		    		FIELD_NAME[1] + " TEXT NULL," +
		    		FIELD_NAME[2] + " REAL DEFAULT 0.0," +
		    		FIELD_NAME[3] + " REAL DEFAULT 0.0," +
		    		FIELD_NAME[4] + " REAL DEFAULT 0.0," +
		    		FIELD_NAME[5] + " TEXT NULL)" 
		       		);		
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "Item List to save: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Item item = (Item) obj;
					values.put(FIELD_NAME[1], item.getItemName());
					values.put(FIELD_NAME[2], item.getPurchasePrice());
					values.put(FIELD_NAME[3], item.getTransportCost());
					values.put(FIELD_NAME[4], item.getOtherCost());
					values.put(FIELD_NAME[5], item.getCurrencyType());

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
						FIELD_NAME[5]
					};
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				itemList = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Item item = new Item();
				        	item.setItemId(cursor.getInt(0));
				        	item.setItemName(cursor.getString(1));
				        	item.setPurchasePrice(cursor.getDouble(2));
				        	item.setTransportCost(cursor.getDouble(3));
				        	item.setOtherCost(cursor.getDouble(4));
				        	item.setCurrencyType(cursor.getString(5));
				        					        		        	
				        	itemList.add(item);
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
			return itemList;
		}

		@Override
		public List<Object> selectRecord(String arg0, String arg1) {
			// TODO Auto-generated method stub
			
			return null;
		}

		@Override
		public List<Object> selectRecord(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Object> selectRecord(String currencyType) {
			// TODO Auto-generated method stub
			
			Log.i("", "Currency Type: "+currencyType);
			
			try {
				String[] FROM = { 
						FIELD_NAME[0],
						FIELD_NAME[1],
						FIELD_NAME[2],
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5]
						};
				
				String[] VALUE = {currencyType};
				String WHERE = FIELD_NAME[5] + "=?";
				String ORDER_BY = FIELD_NAME[1] + " ASC";

				itemList = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
						do {
							Item item = new Item();
				        	item.setItemId(cursor.getInt(0));
				        	item.setItemName(cursor.getString(1));
				        	item.setPurchasePrice(cursor.getDouble(2));
				        	item.setTransportCost(cursor.getDouble(3));
				        	item.setOtherCost(cursor.getDouble(4));
				        	item.setCurrencyType(cursor.getString(5));
				        					        		        	
				        	itemList.add(item);
							
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
				if (complete != null) {
					complete.onComplete();
				}
			}
			return itemList;
		}

	};
	
	/**
	 * Get Item List by Item ID
	 */
	public List<Object> selectByItemID(String itemID) {
		// TODO Auto-generated method stub
		
		Log.i("", "Item ID:"+itemID);
		
		try {
			String[] FROM = { 
					FIELD_NAME[0],
					FIELD_NAME[1],
					FIELD_NAME[2],
					FIELD_NAME[3],
					FIELD_NAME[4],
					FIELD_NAME[5]
					};
			
			String[] VALUE = {itemID};
			String WHERE = FIELD_NAME[0] + "=?";
			String ORDER_BY = FIELD_NAME[0] + " ASC";

			itemList = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
			try {
				if (cursor.moveToFirst()) {
					do {
						Item item = new Item();
			        	item.setItemId(cursor.getInt(0));
			        	item.setItemName(cursor.getString(1));
			        	item.setPurchasePrice(cursor.getDouble(2));
			        	item.setTransportCost(cursor.getDouble(3));
			        	item.setOtherCost(cursor.getDouble(4));
			        	item.setCurrencyType(cursor.getString(5));
			        					        		        	
			        	itemList.add(item);
						
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
			if (complete != null) {
				complete.onComplete();
			}
		}
		return itemList;
	}	
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "Item List to update: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Item item = (Item) obj;
					values.put(FIELD_NAME[1], item.getItemName());
					values.put(FIELD_NAME[2], item.getPurchasePrice());
					values.put(FIELD_NAME[3], item.getTransportCost());
					values.put(FIELD_NAME[4], item.getOtherCost());
					values.put(FIELD_NAME[5], item.getCurrencyType());
					
					String[] VALUE = {item.getItemId().toString()};
					String WHERE = FIELD_NAME[0] + "=?";
					
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

		@Override
		public void updateRecord(String arg0) {
			// TODO Auto-generated method stub
			
		}

	};
	
	private OnDelete deleteRecord = new OnDelete() {
		
		public void deleteRecord() {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, null, null);
	        db.close();
		}

		public boolean deleteRecord(List<Object> obj) {
			return false;
			// TODO Auto-generated method stub
			
		}

		public List<Object> deleteRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean deleteRecord(String arg0) {
			// TODO Auto-generated method stub
			
			Log.i("", "Item ID: "+arg0);
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{arg0});
	        db.close();
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
