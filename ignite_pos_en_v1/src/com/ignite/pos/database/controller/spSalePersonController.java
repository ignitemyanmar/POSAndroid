package com.ignite.pos.database.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.database.util.OnDelete;
import com.ignite.pos.database.util.OnSave;
import com.ignite.pos.database.util.OnSelect;
import com.ignite.pos.model.spSalePerson;

public class spSalePersonController extends DatabaseManager{

	private spSalePerson sale_person;
	private List<Object> salePerson;
	
	private static final String TABLE_NAME = "spSalePerson";
	private static final String[] FIELD_NAME = {"spusername","sppassword"};
	
	public spSalePersonController(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
		setOnSave(saveRecord);
		setOnSelect(selectRecord);
		setOnDelete(deleteRecord);
	}

	@Override
	protected void createTables() {
		// TODO Auto-generated method stub
		try {
			 connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME + " (" +
			    		FIELD_NAME[0] + " TEXT PRIMARY KEY," + 
			    		FIELD_NAME[1] + " TEXT NULL)" 
			       		);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		
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
					spSalePerson sp = (spSalePerson) obj;
					values.put(FIELD_NAME[0], sp.getSpusername());
					values.put(FIELD_NAME[1], sp.getSppassword());

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
					};
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				salePerson = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	spSalePerson sp = new spSalePerson();
				        	
				        	sp.setSpusername(cursor.getString(0));
				        	sp.setSppassword(cursor.getString(1));
				        					        		        	
				        	salePerson.add(sp);
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
			return salePerson;
		}

		public List<Object> selectRecord(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecord(String username, String password) {
			// TODO Auto-generated method stub
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
					};
				
				String[] VALUE = new String[2];
				VALUE[0] = username;
				VALUE[1] = password;
				
				String WHERE = FIELD_NAME[0] + "=? and "+ FIELD_NAME[1] + "=?";
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				salePerson = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				
				Log.i("","Cursor Count:" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	spSalePerson sp = new spSalePerson();
				        	
				        	sp.setSpusername(cursor.getString(0));
				        	sp.setSppassword(cursor.getString(1));
				        					        		        	
				        	salePerson.add(sp);
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
			return salePerson;
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
