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
import com.ignite.pos.model.Supplier;

public class SupplierController extends DatabaseManager{

	private Supplier supplier;
	private List<Object> supplier_list;
	
	private static final String TABLE_NAME = "tbl_supplier";
	private static final String[] FIELD_NAME = {"supId","supCoName","supName","supCity","supPh","supAddress"};
	
	public SupplierController(Context ctx) {
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
		    		FIELD_NAME[5] + " TEXT NULL)" 
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("", "isRecord to save: "+objList.size());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Supplier supplier = (Supplier) obj;
					//values.put(FIELD_NAME[0], supplier.getSupId());
					values.put(FIELD_NAME[1], supplier.getSupCoName());
					values.put(FIELD_NAME[2], supplier.getSupName());
					values.put(FIELD_NAME[3], supplier.getSupCity());
					values.put(FIELD_NAME[4], supplier.getSupPh());
					values.put(FIELD_NAME[5], supplier.getSupAddr());

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
					};
				String ORDER_BY = FIELD_NAME[1]+ " ASC";
				
				supplier_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("TAG", "-----> Cursor Count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	supplier = new Supplier();
				        	supplier.setSupId(cursor.getInt(0));
				        	supplier.setSupCoName(cursor.getString(1));
				        	supplier.setSupName(cursor.getString(2));
				        	supplier.setSupCity(cursor.getString(3));
				        	supplier.setSupPh(cursor.getString(4));
				        	supplier.setSupAddr(cursor.getString(5));
				        					        		        	
				        	supplier_list.add(supplier);
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
			return supplier_list;
		}

		public List<Object> selectRecord(String supID) {
			// TODO Auto-generated method stub
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
					};
				String[] VALUE = {supID};
				String WHERE = FIELD_NAME[0] + "=? ";
				//String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				supplier_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				Log.i("TAG","Cursor Count ------->" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	supplier = new Supplier();
				        	supplier.setSupId(cursor.getInt(0));
				        	supplier.setSupCoName(cursor.getString(1));
				        	supplier.setSupName(cursor.getString(2));
				        	supplier.setSupCity(cursor.getString(3));
				        	supplier.setSupPh(cursor.getString(4));
				        	supplier.setSupAddr(cursor.getString(5));
				        					        		        	
				        	supplier_list.add(supplier);
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
			return supplier_list;
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

		public List<Object> selectRecordByVoucher(String arg0, String arg1,
				String arg2) {
			// TODO Auto-generated method stub
			return null;
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

		public List<Object> selectRecordByItemCode(String arg0, String arg1,
				String arg2) {
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
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {
					
					ContentValues values = new ContentValues();
					Supplier supplier = (Supplier) obj;
					values.put(FIELD_NAME[1], supplier.getSupCoName());
					values.put(FIELD_NAME[2], supplier.getSupName());
					values.put(FIELD_NAME[3], supplier.getSupCity());
					values.put(FIELD_NAME[4], supplier.getSupPh());
					values.put(FIELD_NAME[5], supplier.getSupAddr());

					String[] VALUE = {String.valueOf(supplier.getSupId())};
					String WHERE = FIELD_NAME[0] + " =? ";
					
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

		public boolean deleteRecord(String id) {
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{id});
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
	

