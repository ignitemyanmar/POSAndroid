package com.ignite.pos.database.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.database.util.OnDelete;
import com.ignite.pos.database.util.OnSave;
import com.ignite.pos.database.util.OnSelect;
import com.ignite.pos.model.Buyer;
import com.ignite.pos.model.Credit;

public class CreditController extends DatabaseManager{

	private Credit credit;
	private List<Object> credit_list;
	
	private static final String TABLE_NAME = "Credit";
	private static final String[] FIELD_NAME = {"creditCusName","creditDate","creditTotal","creditPayAmt","creditLeftToPayAmt"};
	
	public CreditController(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
		setOnSave(saveRecord);
		setOnSelect(selectRecord);
		setOnDelete(deleteRecord);
	}

	@Override
	protected void createTables() {
		// TODO Auto-generated method stub
		 connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME + " (" +
		    		FIELD_NAME[0] + " TEXT PRIMARY KEY," + 
		    		FIELD_NAME[1] + " TEXT NULL," + 
		    		FIELD_NAME[2] + " TEXT NULL," +
		    		FIELD_NAME[3] + " TEXT NULL," +
		    		FIELD_NAME[4] + " TEXT NULL)" 
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
					Credit credit = (Credit) obj;
					values.put(FIELD_NAME[0], credit.getCreditCusName());
					values.put(FIELD_NAME[1], credit.getCreditDate());
					values.put(FIELD_NAME[2], credit.getCreditTotal());
					values.put(FIELD_NAME[3], credit.getCreditPayAmt());
					values.put(FIELD_NAME[4], credit.getCreditLeftToPayAmt());

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
						
					};
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				credit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Credit credit = new Credit();
				        	
				        	credit.setCreditCusName(cursor.getString(0));
				        	credit.setCreditDate(cursor.getString(1));
				        	credit.setCreditTotal(cursor.getString(2));
				        	credit.setCreditPayAmt(cursor.getString(3));
				        	credit.setCreditLeftToPayAmt(cursor.getString(4));
				        					        		        	
				        	credit_list.add(credit);
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
			return credit_list;
		}

		public List<Object> selectRecord(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecord(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecord(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordByBuyer(String arg0, String arg1,
				String arg2) {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
private OnDelete deleteRecord = new OnDelete() {
		
		public void deleteRecord() {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, null, null);
	        db.close();
			return;
		}

		/*public void deleteRecord(String arg0) {
			// TODO Auto-generated method stub
			
		}*/

		public List<Object> deleteRecord(List<Object> obj) {
			return null;
			// TODO Auto-generated method stub
			
		}

		public List<Object> deleteRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean deleteRecord(String arg0) {
			// TODO Auto-generated method stub
			return false;
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
