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
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SubCategory;

public class SubCategoryController extends DatabaseManager{

	private SubCategory subcat;
	private List<Object> subCat_List;
	private List<Object> item_list;
	
	private static final String TABLE_NAME = "tbl_subcategory";
	private static final String[] FIELD_NAME = {"subCategoryID","subCategoryName","categoryID"};
	
	public SubCategoryController(Context ctx) {
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
		    		FIELD_NAME[0] + " INTEGER PRIMARY KEY," + 
		    		FIELD_NAME[1] + " TEXT NULL," +
		    		FIELD_NAME[2] + " TEXT NOT NULL)"
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {
		
		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					subcat = (SubCategory) obj;
					values.put(FIELD_NAME[0], subcat.getSubCategoryID());
					values.put(FIELD_NAME[1], subcat.getSubCategoryName());
					values.put(FIELD_NAME[2], subcat.getCategoryID());

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
		
		public void saveRecord(Object obj) {
			// TODO Auto-generated method stub
			
		}
	};

	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "Update: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					subcat = (SubCategory) obj;
					
					values.put(FIELD_NAME[1], subcat.getSubCategoryName());

					String[] VALUE = {String.valueOf(subcat.getSubCategoryID())};
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
		
		public List<Object> selectRecord(String categoryId) {
			// TODO Auto-generated method stub
			//Log.i("","Selected categoryID :" + categoryId);
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2],
					};
				String[] VALUE = {categoryId};
				String WHERE = FIELD_NAME[2] + "=? ";
				String ORDER_BY = FIELD_NAME[1]+ " ASC";
				String GROUP_BY = FIELD_NAME[1];
				
				subCat_List = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SubCategory sub_category = new SubCategory();
				        	
				        	sub_category.setSubCategoryID(cursor.getInt(0)); 
				        	//Log.i("", "Sub_category id: "+cursor.getInt(0));
				        	
				        	sub_category.setSubCategoryName(cursor.getString(1));
				        	//Log.i("", "Sub_category name: "+cursor.getString(1));
				        	
				        	sub_category.setCategoryID(cursor.getInt(2));
				        					        		        	
				        	subCat_List.add(sub_category);
				        } while (cursor.moveToNext());
				    }
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					cursor.close();
					db.close();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally
			{
				if(complete != null){
					complete.onComplete();
				}
			}
			return subCat_List;
		}
		
		public List<Object> selectRecord() {
			// TODO Auto-generated method stub
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
												
					};
				String ORDER_BY = FIELD_NAME[1]+ " ASC";
				
				subCat_List = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	subcat = new SubCategory();
				        	
				        	subcat.setSubCategoryID(cursor.getInt(0));
				        	subcat.setSubCategoryName(cursor.getString(1));
				        	subcat.setCategoryID(cursor.getInt(2));
				        					        					        		        	
				        	subCat_List.add(subcat);
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
			return subCat_List;
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
	
	private OnDelete deleteRecord = new OnDelete() {
		
		public boolean deleteRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			//Delete Sub_Category 
			SQLiteDatabase db = getWritableDatabase();
			for (Object obj : objList) {
					
				subcat = (SubCategory) obj;
				db.delete(TABLE_NAME, FIELD_NAME[0]+" = ?", new String[]{String.valueOf(subcat.getSubCategoryID())});
			}
			
			db.close();
			return true;
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
			SQLiteDatabase db = getWritableDatabase();
			db.delete(TABLE_NAME, null, null);
			db.close();
			return;
		}
	};
	
	/*To Select Last ID for AutoID*/
	public List<SubCategory> getLastSubCategoryID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<SubCategory> subCategoryList=new ArrayList<SubCategory>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
	    if (cursor.moveToFirst()) {
	        do {
	        	SubCategory subcategory=new SubCategory();
	        	subcategory.setSubCategoryID(cursor.getInt(0));
	        	subCategoryList.add(subcategory);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	   //Log.i("","Category List :" + subCategoryList.get(0).getSubCategoryID());
	    return subCategoryList;
	    
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
