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
import com.ignite.pos.model.Admin;
import com.ignite.pos.model.ItemList;

public class AdminController extends DatabaseManager{

	private Admin admin;
	private List<Object> adminList;
	
	private static final String TABLE_NAME = "tbl_admin";
	private static final String[] FIELD_NAME = {"id","adminname","adminpassword","userLevel"};
	
	public AdminController(Context ctx) {
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
		    		FIELD_NAME[2] + " TEXT NULL," +
		    		FIELD_NAME[3] + " TEXT NULL)" 
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "Admin List to save: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Admin admin = (Admin) obj;
					values.put(FIELD_NAME[1], admin.getAdminname());
					values.put(FIELD_NAME[2], admin.getAdminpassword());
					values.put(FIELD_NAME[3], admin.getUserLevel());

					//Log.i("", "Values :" + values.toString());
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
					};
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				adminList = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Admin admin = new Admin();
				        	admin.setID(cursor.getString(0));
				        	admin.setAdminname(cursor.getString(1));
				        	admin.setAdminpassword(cursor.getString(2));
				        	admin.setUserLevel(cursor.getString(3));
				        					        		        	
				        	adminList.add(admin);
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
			return adminList;
		}

		public List<Object> selectRecord(String AdminName , String Password) {
			// TODO Auto-generated method stub
			
			Log.i("", "Admin Name: "+AdminName);
			Log.i("", "Password: "+Password);
			
			try {
				String[] FROM = { 
						FIELD_NAME[0],
						FIELD_NAME[1],
						FIELD_NAME[2],
						FIELD_NAME[3],
						};
				
				String[] VALUE = new String[2];
				VALUE[0] = AdminName;
				VALUE[1] = Password;
				
				String WHERE = FIELD_NAME[1] + "=? and "+ FIELD_NAME[2] + "=? ";
				String ORDER_BY = FIELD_NAME[1] + " ASC";
				
				adminList = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				
				Log.i("","Admin Count:" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
						do {
							Admin admin = new Admin();

							admin.setID(cursor.getString(0));
							admin.setAdminname(cursor.getString(1));
							admin.setAdminpassword(cursor.getString(2));
							admin.setUserLevel(cursor.getString(3));

							adminList.add(admin);
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
			return adminList;
		}

		public List<Object> selectRecord(String ID) {
			// TODO Auto-generated method stub
			try {
				String[] FROM = { 
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2],
						FIELD_NAME[3],
						};
				
				String[] VALUE = { ID };
				String WHERE = FIELD_NAME[0] + "=? ";
				String ORDER_BY = FIELD_NAME[0] + " ASC";

				adminList = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
						do {
							Admin admin = new Admin();

							admin.setID(cursor.getString(0));
							admin.setAdminname(cursor.getString(1));
							admin.setAdminpassword(cursor.getString(2));
							admin.setUserLevel(cursor.getString(3));

							adminList.add(admin);
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
			return adminList;
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

		public List<Object> selectRecord(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			return null;
		}


	};
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Admin iteml = (Admin) obj;
					values.put(FIELD_NAME[1], iteml.getAdminname());
					values.put(FIELD_NAME[2], iteml.getAdminpassword());
					
					String[] VALUE = {iteml.getOldname() , iteml.getOldpassword()};
					String WHERE = FIELD_NAME[1] + "=? and "+ FIELD_NAME[2] + "=? ";
					
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
	
	public List<Object> selectRecordByUserLevel(String AdminName , String Password, String UserLevel) {
		// TODO Auto-generated method stub
		
		Log.i("", "Admin Name: "+AdminName);
		Log.i("", "Password: "+Password);
		Log.i("", "UserLevel: "+UserLevel);
		
		try {
			String[] FROM = { 
					FIELD_NAME[0],
					FIELD_NAME[1],
					FIELD_NAME[2],
					FIELD_NAME[3],
					};
			
			String[] VALUE = new String[3];
			VALUE[0] = AdminName;
			VALUE[1] = Password;
			VALUE[2] = UserLevel;
			
			String WHERE = FIELD_NAME[1] + "=? and "+ FIELD_NAME[2] + "=? and "+FIELD_NAME[3]+" = ?";
			String ORDER_BY = FIELD_NAME[1] + " ASC";
			
			adminList = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
			
			Log.i("","Admin Count:" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
					do {
						Admin admin = new Admin();

						admin.setID(cursor.getString(0));
						admin.setAdminname(cursor.getString(1));
						admin.setAdminpassword(cursor.getString(2));
						admin.setUserLevel(cursor.getString(3));

						adminList.add(admin);
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
		return adminList;
	}
	
	private OnDelete deleteRecord = new OnDelete() {
		
		public void deleteRecord() {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, null, null);
	        db.close();
		}

		/*public void deleteRecord(String arg0) {
			// TODO Auto-generated method stub
			
		}*/

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
