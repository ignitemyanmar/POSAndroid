package com.ignite.purchasecostcalculator.database.controller;

import java.util.ArrayList;
import java.util.List;
import com.ignite.purchasecostcalculator.database.util.DatabaseManager;
import com.ignite.purchasecostcalculator.database.util.OnDelete;
import com.ignite.purchasecostcalculator.database.util.OnSave;
import com.ignite.purchasecostcalculator.database.util.OnSelect;
import com.ignite.purchasecostcalculator.database.util.OnUpdate;
import com.ignite.purchasecostcalculator.model.ExchangeRate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExchangeRateController extends DatabaseManager{

	private ExchangeRate ExchangeRate;
	private List<Object> exchangeRateList;
	
	private static final String TABLE_NAME = "tbl_exchange_rate";
	private static final String[] FIELD_NAME = {"rateId","exchangeRateChina","exchangeRateThai"};
	
	public ExchangeRateController(Context ctx) {
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
		    		FIELD_NAME[1] + " REAL DEFAULT 0.0," +
		    		FIELD_NAME[2] + " REAL DEFAULT 0.0)" 
		       		);		
	}	
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "ExchangeRate List to save: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ExchangeRate exchangeRate = (ExchangeRate) obj;
					values.put(FIELD_NAME[1], exchangeRate.getExchangeRateChina());
					values.put(FIELD_NAME[2], exchangeRate.getExchangeRateThai());

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
						FIELD_NAME[2]
					};
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				exchangeRateList = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	ExchangeRate exchangeRate = new ExchangeRate();
				        	exchangeRate.setRateId(cursor.getInt(0));
				        	exchangeRate.setExchangeRateChina(cursor.getDouble(1));
				        	exchangeRate.setExchangeRateThai(cursor.getDouble(2));
				        					        		        	
				        	exchangeRateList.add(exchangeRate);
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
			return exchangeRateList;
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
		public List<Object> selectRecord(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "ExchangeRate List to update: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ExchangeRate exchangeRate = (ExchangeRate) obj;
					values.put(FIELD_NAME[1], exchangeRate.getExchangeRateChina());
					
					String[] VALUE = {exchangeRate.getRateId().toString()};
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
			Log.i("", "ExchangeRate Obj to update: "+obj.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				//for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ExchangeRate exchangeRate = (ExchangeRate) obj;
					values.put(FIELD_NAME[2], exchangeRate.getExchangeRateThai());
					
					String[] VALUE = {exchangeRate.getRateId().toString()};
					String WHERE = FIELD_NAME[0] + "=?";
					
					db.update(TABLE_NAME, values, WHERE,VALUE);
				//}
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

