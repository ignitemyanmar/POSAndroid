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
import com.ignite.pos.model.Category;
import com.ignite.pos.model.ItemList;

public class CategoryController extends DatabaseManager{

	private Category category;
	private List<Object> category_list;
	
	private static final String TABLE_NAME = "Category";
	private static final String[] FIELD_NAME = {"id","name"};
	
	public CategoryController(Context ctx) {
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
	    		FIELD_NAME[0] + " TEXT PRIMARY KEY, " + 
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
					Category category = (Category) obj;
					values.put(FIELD_NAME[0],category.getCategoryID());
					values.put(FIELD_NAME[1], category.getCategoryName());
					
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
			
			Log.i("","Update :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					category = (Category) obj;
					
					values.put(FIELD_NAME[1], category.getCategoryName());

					String[] VALUE = {String.valueOf(category.getCategoryID())};
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
	
	private OnSelect selectRecord = new OnSelect() {
		
		public List<Object> selectRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public List<Object> selectRecord(String CatID) {
			// TODO Auto-generated method stub
			
			try {
				String[] FROM = { 
						FIELD_NAME[0],
						FIELD_NAME[1],
						};
				
				String[] VALUE = { CatID };
				String WHERE = FIELD_NAME[0] + " =?";
				String GROUP_BY = FIELD_NAME[1];
				String ORDER_BY = FIELD_NAME[1] + " ASC";

				category_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY,null, ORDER_BY);
				Log.i("TAG","Cursor Count ------->" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	category = new Category();
				        	category.setCategoryID(cursor.getInt(0));
				        	category.setCategoryName(cursor.getString(1));
				        	
				        	category_list.add(category);
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
			return category_list;
		}
		
		public List<Object> selectRecord() {
			// TODO Auto-generated method stub
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
					};
				String ORDER_BY = FIELD_NAME[1]+ " ASC";
				
				category_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("", "number of category: "+cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	category = new Category();
				        	
				        	category.setCategoryID(cursor.getInt(0));
				        	category.setCategoryName(cursor.getString(1));
				        	
				        	category_list.add(category);
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
			return category_list;
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
		
		public List<Object> deleteRecord(List<Object> obj) {
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
	public List<Category> getLastCategoryID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<Category> categoryList = new ArrayList<Category>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	Category category = new Category();
	        	category.setCategoryID(cursor.getInt(0));
	        	
	        	Log.i("", "cursor obj from last category id: "+cursor.getInt(0));
	        	
	        	categoryList.add(category);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    
	    //Log.i("","Category List of last:" + categoryList);
	    
	    return categoryList; 
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
