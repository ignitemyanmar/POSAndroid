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

public class ItemListController extends DatabaseManager{

	private ItemList itemList;
	private List<Object> item_list;
	
	private static final String TABLE_NAME = "ItemList";
	private static final String[] FIELD_NAME = {"itemId","itemName","itemPrice","itemQty","categoryId","subCategoryId","brandId"};
	
	public ItemListController(Context ctx) {
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
		    		FIELD_NAME[4] + " TEXT NULL," +
		    		FIELD_NAME[5] + " TEXT NULL," +
		    		FIELD_NAME[6] + " TEXT NULL)" 
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
					ItemList iteml = (ItemList) obj;
					values.put(FIELD_NAME[0], iteml.getItemId());
					values.put(FIELD_NAME[1], iteml.getItemName());
					values.put(FIELD_NAME[2], iteml.getItemPrice());
					values.put(FIELD_NAME[3], iteml.getQty());
					values.put(FIELD_NAME[4], iteml.getCategoryId());
					values.put(FIELD_NAME[5], iteml.getSubCategoryId());
					values.put(FIELD_NAME[6], iteml.getBrandId());

					db.insert(TABLE_NAME, null, values);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				Log.e("DB ERROR", e.toString());
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
		
		public void updateRecord(Object obj) {
			Log.i("", "Update Item Price :" + obj.toString());
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				//for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ItemList iteml = (ItemList) obj;
					values.put(FIELD_NAME[0], iteml.getItemId());
					values.put(FIELD_NAME[1], iteml.getItemName());
					values.put(FIELD_NAME[2], iteml.getItemPrice());
					values.put(FIELD_NAME[3], iteml.getQty());
					values.put(FIELD_NAME[4], iteml.getCategoryId());
					values.put(FIELD_NAME[5], iteml.getSubCategoryId());
					values.put(FIELD_NAME[6], iteml.getBrandId());
					
					String[] VALUE = {iteml.getItemId().toString()};
					String WHERE = FIELD_NAME[0] + "=? ";
					
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
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ItemList iteml = (ItemList) obj;
					values.put(FIELD_NAME[2], iteml.getItemPrice());
					
					String[] VALUE = {iteml.getItemId()};
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
						FIELD_NAME[6],
					};
				String ORDER_BY = FIELD_NAME[1]+ " ASC";
				
				item_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("", "number of item list: "+cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	ItemList itemL = new ItemList();
				        	
				        	itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setItemPrice(cursor.getString(2));
				        	itemL.setQty(cursor.getString(3));
				        	itemL.setCategoryId(cursor.getString(4));
				        	itemL.setSubCategoryId(cursor.getString(5));
				        	itemL.setBrandId(cursor.getString(6));
				        	
				        	item_list.add(itemL);
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
			return item_list;
		}

		public List<Object> selectRecord(String itemId) {
			// TODO Auto-generated method stub
			try {
				String[] FROM = { 
						FIELD_NAME[0],
						FIELD_NAME[1],
						FIELD_NAME[2],
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						};
				
				String[] VALUE = {itemId};
				String WHERE = FIELD_NAME[0] + "=? ";
				String ORDER_BY = FIELD_NAME[0] + " ASC";

				item_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				try {
					if (cursor.moveToFirst()) {
						do {
							ItemList itemL = new ItemList();

							itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setItemPrice(cursor.getString(2));
				        	itemL.setQty(cursor.getString(3));
				        	itemL.setCategoryId(cursor.getString(4));
				        	itemL.setSubCategoryId(cursor.getString(5));
				        	itemL.setBrandId(cursor.getString(6));

							item_list.add(itemL);
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
			return item_list;
		}

		public List<Object> selectRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecord(String CatID, String SubID) {
			// TODO Auto-generated method stub
			try {
				String[] FROM = { 
						FIELD_NAME[0],
						FIELD_NAME[1],
						FIELD_NAME[2],
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						};
				
				String[] VALUE = { CatID , SubID };
				String WHERE = FIELD_NAME[4] + "=? and "+FIELD_NAME[5]+"=? ";
				String ORDER_BY = FIELD_NAME[1] + " ASC";

				item_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				Log.i("TAG","Cursor Count ------->" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
						do {
							ItemList itemL = new ItemList();

							itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setItemPrice(cursor.getString(2));
				        	itemL.setQty(cursor.getString(3));
				        	itemL.setCategoryId(cursor.getString(4));
				        	itemL.setSubCategoryId(cursor.getString(5));
				        	itemL.setBrandId(cursor.getString(6));

							item_list.add(itemL);
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
			return item_list;
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

		public boolean deleteRecord(String string) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	/*To Select Last ID for AutoID*/
	public List<ItemList> getLastItemID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<ItemList> itemList = new ArrayList<ItemList>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
	    if (cursor.moveToFirst()) {
	        do {
	        	ItemList item = new ItemList();
	        	item.setItemId(String.valueOf(cursor.getInt(0)));
	        	itemList.add(item);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	   // Log.i("","Category List :" + categoryList);
	    return itemList;
	    
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
