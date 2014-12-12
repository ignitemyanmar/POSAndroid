
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
import com.ignite.pos.model.Brand;
import com.ignite.pos.model.Category;

public class BrandController extends DatabaseManager{

	private Brand brand;
	private List<Object> brand_list;
	
	private static final String TABLE_NAME = "tbl_brand";
	private static final String[] FIELD_NAME = {"id","name"};
	
	public BrandController(Context ctx) {
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
	    		FIELD_NAME[0] + " INTEGER PRIMARY KEY, " + 
	    		FIELD_NAME[1] + " TEXT NULL)" 
	       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {
			// TODO Auto-generated method stub
			
		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Brand brand = (Brand) obj;
					values.put(FIELD_NAME[0],brand.getBrandID());
					values.put(FIELD_NAME[1], brand.getBrandName());
					
					db.insert(TABLE_NAME, null, values);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				//Log.e("DB ERROR", e.toString());
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
	
	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					brand = (Brand) obj;
					//values.put(FIELD_NAME[0], Brand.getCategoryID());
					values.put(FIELD_NAME[1], brand.getBrandName());

					String[] VALUE = {String.valueOf(brand.getBrandID())};
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

		public void updateStockQty(List<Object> objList) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnSelect selectRecord = new OnSelect() {
		
		public List<Object> selectRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public List<Object> selectRecord(String brandID) {
			// TODO Auto-generated method stub
			
			try {
				String[] FROM = { 
						FIELD_NAME[0],
						FIELD_NAME[1],
						};
				
				String[] VALUE = { brandID };
				String WHERE = FIELD_NAME[0] + " =?";
				//String ORDER_BY = FIELD_NAME[1] + " ASC";

				brand_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, null);
				Log.i("TAG","Cursor Count ------->" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	brand = new Brand();
				        	brand.setBrandID(cursor.getInt(0));
				        	brand.setBrandName(cursor.getString(1));
				        	
				        	brand_list.add(brand);
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
			return brand_list;
		}
		
		public List<Object> selectRecord() {
			// TODO Auto-generated method stub
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
					};
				String ORDER_BY = FIELD_NAME[1]+ " ASC";
				
				brand_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("", "number of Brand: "+cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	brand = new Brand();
				        	
				        	brand.setBrandID(cursor.getInt(0));
				        	brand.setBrandName(cursor.getString(1));
				        	
				        	brand_list.add(brand);
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
			return brand_list;
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

		public List<Object> selectRecordByMaximumQty(String arg0, String arg1,
				String arg2) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordSlowMoving() {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordGroupBy(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private OnDelete deleteRecord = new OnDelete() {
		
		public boolean deleteRecord(List<Object> obj) {
			// TODO Auto-generated method stub
			return false;
		}
		
		public boolean deleteRecord(String arg0) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{arg0});
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
	};
	
	/*To Select Last ID for AutoID*/
	public List<Brand> getLastBrandID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<Brand> brandList = new ArrayList<Brand>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	Brand brand = new Brand();
	        	brand.setBrandID(cursor.getInt(0));
	        	
	        	Log.i("", "cursor obj from last Brand id: "+cursor.getInt(0));
	        	
	        	brandList.add(brand);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    
	    //Log.i("","Brand List of last:" + categoryList);
	    
	    return brandList; 
	}
	
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
