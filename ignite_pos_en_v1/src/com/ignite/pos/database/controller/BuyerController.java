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
import com.ignite.pos.model.Buyer;

public class BuyerController extends DatabaseManager{

	private Buyer buyer;
	private List<Object> buyer_list;
	
	private static final String TABLE_NAME = "Buyer";
	private static final String[] FIELD_NAME = {"cusName","cusCity","cusPh","cusAddress","cusCredit"};
	
	public BuyerController(Context ctx) {
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
		    		FIELD_NAME[0] + " TEXT PRIMARY KEY," + 
		    		FIELD_NAME[1] + " TEXT NULL," + 
		    		FIELD_NAME[2] + " TEXT NULL," +
		    		FIELD_NAME[3] + " TEXT NULL," +
		    		//FIELD_NAME[4] + " TEXT NULL," +
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
					Buyer buyer = (Buyer) obj;
					//values.put(FIELD_NAME[0], buyer.getId());
					values.put(FIELD_NAME[0], buyer.getCusName());
					values.put(FIELD_NAME[1], buyer.getCusCity());
					values.put(FIELD_NAME[2], buyer.getCusPh());
					values.put(FIELD_NAME[3], buyer.getCusAddress());
					values.put(FIELD_NAME[4], buyer.getCusCredit());

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
						//FIELD_NAME[5],
						
					};
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				buyer_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				Log.i("TAG", "-----> Cursor Count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Buyer buyer = new Buyer();
				        	//buyer.setId(cursor.getInt(0));
				        	buyer.setCusName(cursor.getString(0));
				        	buyer.setCusCity(cursor.getString(1));
				        	buyer.setCusPh(cursor.getString(2));
				        	buyer.setCusAddress(cursor.getString(3));
				        	buyer.setCusCredit(cursor.getString(4));
				        					        		        	
				        	buyer_list.add(buyer);
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
			return buyer_list;
		}

		public List<Object> selectRecord(String CusName) {
			// TODO Auto-generated method stub
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						//FIELD_NAME[5],
					};
				String[] VALUE = {CusName};
				String WHERE = FIELD_NAME[0] + "=? ";
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				buyer_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Buyer buyer = new Buyer();
				        	//buyer.setId(cursor.getInt(0));
				        	buyer.setCusName(cursor.getString(0));
				        	buyer.setCusCity(cursor.getString(1));
				        	buyer.setCusPh(cursor.getString(2));
				        	buyer.setCusAddress(cursor.getString(3));
				        	buyer.setCusCredit(cursor.getString(4));
				        					        		        	
				        	buyer_list.add(buyer);
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
			return buyer_list;
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
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Buyer buyer = (Buyer) obj;
					//values.put(FIELD_NAME[0],buyer.getId());
					values.put(FIELD_NAME[0], buyer.getCusName());
					values.put(FIELD_NAME[1], buyer.getCusCity());
					values.put(FIELD_NAME[2], buyer.getCusPh());
					values.put(FIELD_NAME[3], buyer.getCusAddress());
					values.put(FIELD_NAME[4], buyer.getCusCredit());

					String[] VALUE = {buyer.getCusOldName().toString()};
					String WHERE = FIELD_NAME[0] + "=? ";
					
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
	};
	
	private OnDelete deleteRecord = new OnDelete() {
		
		public List<Object> deleteRecord(List<Object> obj) {
			// TODO Auto-generated method stub
			return null;
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
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{string});
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
	
