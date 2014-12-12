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
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleVouncher;

public class ItemListController extends DatabaseManager{

	private ItemList itemList;
	private List<Object> item_list;
	
	private static final String TABLE_NAME = "tbl_item";
	private static final String[] FIELD_NAME = {"itemId","itemName","purchasePrice", "itemPrice","itemQty", "categoryId","subCategoryId","brandId","marginalPrice","status","notifyQty","notifyStatus","registerNotify"};
	
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
		    		FIELD_NAME[5] + " INTEGER NULL," +
		    		FIELD_NAME[6] + " INTEGER NULL," +
		    		FIELD_NAME[7] + " INTEGER NULL," +
		    		FIELD_NAME[8] + " TEXT NULL," +
		    		FIELD_NAME[9] + " INTEGER DEFAULT 0," + 
		    		FIELD_NAME[10] + " INTEGER NULL," +
		    		FIELD_NAME[11] + " INTEGER DEFAULT 0," +
		    		FIELD_NAME[12] + " INTEGER DEFAULT 0)"
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
					values.put(FIELD_NAME[2], iteml.getPurchasePrice());
					values.put(FIELD_NAME[3], iteml.getSalePrice());
					values.put(FIELD_NAME[4], iteml.getQty());
					values.put(FIELD_NAME[5], iteml.getCategoryId());
					values.put(FIELD_NAME[6], iteml.getSubCategoryId());
					values.put(FIELD_NAME[7], iteml.getBrandId());
					values.put(FIELD_NAME[8], iteml.getMarginalPrice());
					values.put(FIELD_NAME[9], iteml.getStatus());
					values.put(FIELD_NAME[10], iteml.getNotifyQty());
					values.put(FIELD_NAME[11], iteml.getNotifyStatus());
					values.put(FIELD_NAME[12], iteml.getRegisterNotify());
					
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
			
			Log.i("","New Sale Price: " + obj.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				//for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ItemList iteml = (ItemList) obj;
					
					//values.put(FIELD_NAME[2], iteml.getPurchasePrice());
					values.put(FIELD_NAME[3], iteml.getSalePrice());
					//values.put(FIELD_NAME[4], iteml.getQty());
					//values.put(FIELD_NAME[8], iteml.getMarginalPrice());
					
					String[] VALUE = {iteml.getItemId()};
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
			Log.i("","Stock Qty:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ItemList iteml = (ItemList) obj;
					
					values.put(FIELD_NAME[1], iteml.getItemName());
					values.put(FIELD_NAME[2], iteml.getPurchasePrice());
					values.put(FIELD_NAME[3], iteml.getSalePrice());
					values.put(FIELD_NAME[4], iteml.getQty());
					values.put(FIELD_NAME[8], iteml.getMarginalPrice());
					values.put(FIELD_NAME[11], iteml.getNotifyStatus());
					
					Log.i("","New Qty :" + iteml.getQty());
					
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
		
		public void updateStockQty(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","New Stock Qty: " + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					ItemList iteml = (ItemList) obj;
					
					values.put(FIELD_NAME[2], iteml.getPurchasePrice());
					values.put(FIELD_NAME[3], iteml.getSalePrice());
					values.put(FIELD_NAME[4], iteml.getQty());
					values.put(FIELD_NAME[8], iteml.getMarginalPrice());
					
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
	
	public void updateNewStockQty(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Update New Stock Qty:" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				ItemList iteml = (ItemList) obj;
				
				//values.put(FIELD_NAME[1], iteml.getItemName());
				//values.put(FIELD_NAME[2], iteml.getPurchasePrice());
				//values.put(FIELD_NAME[3], iteml.getSalePrice());
				values.put(FIELD_NAME[4], iteml.getQty());
				//values.put(FIELD_NAME[8], iteml.getMarginalPrice());
				
				//Log.i("","New Qty :" + iteml.getQty());
				
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
	
	public void updateRecord(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Update :" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				ItemList iteml = (ItemList) obj;
				
				values.put(FIELD_NAME[1], iteml.getItemName());
				values.put(FIELD_NAME[2], iteml.getPurchasePrice());
				values.put(FIELD_NAME[3], iteml.getSalePrice());
				values.put(FIELD_NAME[4], iteml.getQty());
				values.put(FIELD_NAME[8], iteml.getMarginalPrice());
				//values.put(FIELD_NAME[9], 1);
				
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
	
	public void updateNotifyQty(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Update Notify Qty+Status :" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				ItemList iteml = (ItemList) obj;
				
				values.put(FIELD_NAME[10], iteml.getNotifyQty());
				values.put(FIELD_NAME[11], iteml.getNotifyStatus());
				values.put(FIELD_NAME[12], iteml.getRegisterNotify());
				
				String[] VALUE = {iteml.getItemId()};
				String WHERE = FIELD_NAME[0] + " = ? ";
				
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
	
	public void updateNotify(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Update Notify Qty :" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				ItemList iteml = (ItemList) obj;
				
				values.put(FIELD_NAME[10], iteml.getNotifyQty());
				values.put(FIELD_NAME[11], iteml.getNotifyStatus());
				
				String[] VALUE = {iteml.getItemId()};
				String WHERE = FIELD_NAME[0] + " = ? ";
				
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
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
					};
				String ORDER_BY = FIELD_NAME[0]+ " DESC";
				String WHERE = FIELD_NAME[9] + " == 0 ";
				
				
				item_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, null, null, null, ORDER_BY);
				
				Log.i("", "number of item list: "+cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	ItemList itemL = new ItemList();
				        	
				        	
				        		itemL.setItemId(cursor.getString(0));
					        	itemL.setItemName(cursor.getString(1));
					        	itemL.setPurchasePrice(cursor.getString(2));
					        	itemL.setSalePrice(cursor.getString(3));
					        	itemL.setQty(cursor.getString(4));
					        	itemL.setCategoryId(cursor.getString(5));
					        	itemL.setSubCategoryId(cursor.getString(6));
					        	itemL.setBrandId(cursor.getString(7));
					        	itemL.setMarginalPrice(cursor.getString(8));
					        	itemL.setStatus(cursor.getInt(9));
					        	itemL.setNotifyQty(cursor.getInt(10));
					        	itemL.setNotifyStatus(cursor.getInt(11));
					        	itemL.setRegisterNotify(cursor.getInt(12));
							
				        	
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
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
						};
				
				String[] VALUE = {itemId};
				String WHERE = FIELD_NAME[0] + "=? and "+FIELD_NAME[9] +" == 0";
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
					        	itemL.setPurchasePrice(cursor.getString(2));
					        	itemL.setSalePrice(cursor.getString(3));
					        	itemL.setQty(cursor.getString(4));
					        	itemL.setCategoryId(cursor.getString(5));
					        	itemL.setSubCategoryId(cursor.getString(6));
					        	itemL.setBrandId(cursor.getString(7));
					        	itemL.setMarginalPrice(cursor.getString(8));
					        	itemL.setStatus(cursor.getInt(9));
					        	itemL.setNotifyQty(cursor.getInt(10));
					        	itemL.setNotifyStatus(cursor.getInt(11));
					        	itemL.setRegisterNotify(cursor.getInt(12));
							
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
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
						};
				
				String[] VALUE = { CatID , SubID };
				String WHERE = FIELD_NAME[5] + "=? and "+FIELD_NAME[6]+"=? and "+FIELD_NAME[4]+" > 0 and "+FIELD_NAME[9]+" == 0";
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
					        	itemL.setPurchasePrice(cursor.getString(2));
					        	itemL.setSalePrice(cursor.getString(3));
					        	itemL.setQty(cursor.getString(4));
					        	itemL.setCategoryId(cursor.getString(5));
					        	itemL.setSubCategoryId(cursor.getString(6));
					        	itemL.setBrandId(cursor.getString(7));
					        	itemL.setMarginalPrice(cursor.getString(8));
					        	itemL.setStatus(cursor.getInt(9));
					        	itemL.setNotifyQty(cursor.getInt(10));
					        	itemL.setNotifyStatus(cursor.getInt(11));
					        	itemL.setRegisterNotify(cursor.getInt(12));
							

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

		public List<Object> selectRecord(String itemCode, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Item Code: "+itemCode);
			Log.i("", "Selected From Date: "+fromDate);
			Log.i("", "Selected To Date: "+toDate);
			
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
					};
				
				String[] VALUE;
				String WHERE;
				
				/*if (supName.equals("All")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ?";
				}else{*/
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = itemCode;
					WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ? and "+FIELD_NAME[0]+" = ?";
				//}
				
				//String GROUP_BY = FIELD_NAME[0];
				//String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				item_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	ItemList itemL = new ItemList();

								itemL.setItemId(cursor.getString(0));
					        	itemL.setItemName(cursor.getString(1));
					        	itemL.setPurchasePrice(cursor.getString(2));
					        	itemL.setSalePrice(cursor.getString(3));
					        	itemL.setQty(cursor.getString(4));
					        	itemL.setCategoryId(cursor.getString(5));
					        	itemL.setSubCategoryId(cursor.getString(6));
					        	itemL.setBrandId(cursor.getString(7));
					        	itemL.setMarginalPrice(cursor.getString(8));
					        	itemL.setStatus(cursor.getInt(9));
					        	itemL.setNotifyQty(cursor.getInt(10));
					        	itemL.setNotifyStatus(cursor.getInt(11));
					        	itemL.setRegisterNotify(cursor.getInt(12));
							

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
			
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9]
					};
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				item_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	ItemList itemL = new ItemList();

								itemL.setItemId(cursor.getString(0));
					        	itemL.setItemName(cursor.getString(1));
					        	itemL.setPurchasePrice(cursor.getString(2));
					        	itemL.setSalePrice(cursor.getString(3));
					        	itemL.setQty(cursor.getString(4));
					        	itemL.setCategoryId(cursor.getString(5));
					        	itemL.setSubCategoryId(cursor.getString(6));
					        	itemL.setBrandId(cursor.getString(7));
					        	itemL.setMarginalPrice(cursor.getString(8));
					        	itemL.setStatus(cursor.getInt(9));
							

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

		public List<Object> selectRecordAllByCatSub(String CatID, String SubID) {
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
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
						};
				
				String[] VALUE = { CatID , SubID };
				String WHERE = FIELD_NAME[5] + "=? and "+FIELD_NAME[6]+"=? and "+FIELD_NAME[9]+" == 0";
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
					        	itemL.setPurchasePrice(cursor.getString(2));
					        	itemL.setSalePrice(cursor.getString(3));
					        	itemL.setQty(cursor.getString(4));
					        	itemL.setCategoryId(cursor.getString(5));
					        	itemL.setSubCategoryId(cursor.getString(6));
					        	itemL.setBrandId(cursor.getString(7));
					        	itemL.setMarginalPrice(cursor.getString(8));
					        	itemL.setStatus(cursor.getInt(9));
					        	itemL.setNotifyQty(cursor.getInt(10));
					        	itemL.setNotifyStatus(cursor.getInt(11));
					        	itemL.setRegisterNotify(cursor.getInt(12));
							

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

		public List<Object> selectRecordPurchasedItems() {
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
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
					};
				
				String WHERE = FIELD_NAME[2] + " > 0 and "+FIELD_NAME[9]+" == 0";
				String ORDER_BY = FIELD_NAME[1]+ " ASC";
				
				item_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, null, null, null, ORDER_BY);
				
				Log.i("", "number of item list: "+cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	ItemList itemL = new ItemList();
				        	
								itemL.setItemId(cursor.getString(0));
					        	itemL.setItemName(cursor.getString(1));
					        	itemL.setPurchasePrice(cursor.getString(2));
					        	itemL.setSalePrice(cursor.getString(3));
					        	itemL.setQty(cursor.getString(4));
					        	itemL.setCategoryId(cursor.getString(5));
					        	itemL.setSubCategoryId(cursor.getString(6));
					        	itemL.setBrandId(cursor.getString(7));
					        	itemL.setMarginalPrice(cursor.getString(8));
					        	itemL.setStatus(cursor.getInt(9));
					        	itemL.setNotifyQty(cursor.getInt(10));
					        	itemL.setNotifyStatus(cursor.getInt(11));
					        	itemL.setRegisterNotify(cursor.getInt(12));
							
				        	
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

		public List<Object> selectRecordGroupBy(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
	
	public List<Object> selectRecordSlowMoving(String fromDate, String toDate, Integer maxiQty) {
		// TODO Auto-generated method stub
		
		Log.i("", "Selected From Date: "+fromDate);
		Log.i("", "Selected To Date: "+toDate);
		Log.i("", "Selected MaxiQty: "+maxiQty);
		
		try {
			String[] FROM = { 
					FIELD_NAME[0],
					FIELD_NAME[1],
					FIELD_NAME[2],
					FIELD_NAME[3],
					FIELD_NAME[4],
					FIELD_NAME[5],
					FIELD_NAME[6],
					FIELD_NAME[7],
					FIELD_NAME[8],
					FIELD_NAME[9],
					FIELD_NAME[10],
					FIELD_NAME[11],
					FIELD_NAME[12]
					};
			SQLiteDatabase db = getReadableDatabase();
			
			//Get Not In ID from Sale Table
			String[] FROM1 = {
					"itemid",
					"SUM(qty) AS sumQty"
				};
			
			String[] VALUE1;
			String WHERE1;
			
			VALUE1 = new String[2];
			VALUE1[0] = fromDate;
			VALUE1[1] = toDate;
			//VALUE1[2] = String.valueOf(maxiQty);
			
			WHERE1 = " vdate >= ? and vdate <= ? ";
			String GROUP_BY1 = "itemid";
			
			Cursor cursor1 = db.query("tbl_sale", FROM1, WHERE1, VALUE1, GROUP_BY1, "sumQty >= "+maxiQty, null);
			
			Log.i("","Hello Cursor Count : "+ cursor1.getCount());
			
			String itemId = "";
			
			if (cursor1.moveToFirst()) {
				int count = cursor1.getCount() - 1;
				int i = 0;
				do {
					if(i < count)
						itemId += "'"+cursor1.getString(0)+"', ";
					else
						itemId += "'"+cursor1.getString(0)+"' ";
					i++;
				} while (cursor1.moveToNext());
			}//Get Not In ID from Sale Table
			
			Log.i("","Hello Item ID : "+ itemId);
			
			
			String WHERE = FIELD_NAME[0] + " NOT IN("+itemId+") and "+FIELD_NAME[9]+" == 0 and "+FIELD_NAME[4]+" > 0";
			String ORDER_BY = FIELD_NAME[1] + " ASC";

			item_list = new ArrayList<Object>();
			
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, null, null,null, ORDER_BY);
			Log.i("TAG","Cursor Count ------->" + cursor.getCount());
			try {
				if (cursor.moveToFirst()) {
					do {
						ItemList itemL = new ItemList();

							itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setPurchasePrice(cursor.getString(2));
				        	itemL.setSalePrice(cursor.getString(3));
				        	itemL.setQty(cursor.getString(4));
				        	itemL.setCategoryId(cursor.getString(5));
				        	itemL.setSubCategoryId(cursor.getString(6));
				        	itemL.setBrandId(cursor.getString(7));
				        	itemL.setMarginalPrice(cursor.getString(8));
				        	itemL.setStatus(cursor.getInt(9));
				        	itemL.setNotifyQty(cursor.getInt(10));
				        	itemL.setNotifyStatus(cursor.getInt(11));
				        	itemL.setRegisterNotify(cursor.getInt(12));

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
	
	public List<Object> selectRecordBySubCat(String SubID) {
		// TODO Auto-generated method stub
		
		Log.i("", "Sub Cat ID: "+SubID);
		
		try {
			String[] FROM = { 
					FIELD_NAME[0],
					FIELD_NAME[1],
					FIELD_NAME[2],
					FIELD_NAME[3],
					FIELD_NAME[4],
					FIELD_NAME[5],
					FIELD_NAME[6],
					FIELD_NAME[7],
					FIELD_NAME[8],
					FIELD_NAME[9],
					FIELD_NAME[10],
					FIELD_NAME[11],
					FIELD_NAME[12]
					};
			
			String[] VALUE = { SubID };
			String WHERE = FIELD_NAME[6] + " = ? and "+FIELD_NAME[9]+" == 0";

			item_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, null);
			
			Log.i("TAG","Cursor Count ------->" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
					do {
						ItemList itemL = new ItemList();

							itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setPurchasePrice(cursor.getString(2));
				        	itemL.setSalePrice(cursor.getString(3));
				        	itemL.setQty(cursor.getString(4));
				        	itemL.setCategoryId(cursor.getString(5));
				        	itemL.setSubCategoryId(cursor.getString(6));
				        	itemL.setBrandId(cursor.getString(7));
				        	itemL.setMarginalPrice(cursor.getString(8));
				        	itemL.setStatus(cursor.getInt(9));
				        	itemL.setNotifyQty(cursor.getInt(10));
				        	itemL.setNotifyStatus(cursor.getInt(11));
				        	itemL.setRegisterNotify(cursor.getInt(12));

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
	
	//Select All Both Delete & Undelete Items by Item ID
	public List<Object> selectRecordAllByItemID(String itemId) {
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
					FIELD_NAME[7],
					FIELD_NAME[8],
					FIELD_NAME[9],
					FIELD_NAME[10],
					FIELD_NAME[11],
					FIELD_NAME[12]
					};
			
			String[] VALUE = {itemId};
			String WHERE = FIELD_NAME[0] + " =?";

			item_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, null);
			try {
				if (cursor.moveToFirst()) {
					do {
						ItemList itemL = new ItemList();

							itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setPurchasePrice(cursor.getString(2));
				        	itemL.setSalePrice(cursor.getString(3));
				        	itemL.setQty(cursor.getString(4));
				        	itemL.setCategoryId(cursor.getString(5));
				        	itemL.setSubCategoryId(cursor.getString(6));
				        	itemL.setBrandId(cursor.getString(7));
				        	itemL.setMarginalPrice(cursor.getString(8));
				        	itemL.setStatus(cursor.getInt(9));
				        	itemL.setNotifyQty(cursor.getInt(10));
				        	itemL.setNotifyStatus(cursor.getInt(11));
				        	itemL.setRegisterNotify(cursor.getInt(12));

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
	
	public List<Object> selectByNotifyStatus() {
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
					FIELD_NAME[7],
					FIELD_NAME[8],
					FIELD_NAME[9],
					FIELD_NAME[10],
					FIELD_NAME[11],
					FIELD_NAME[12]
				};
			String ORDER_BY = FIELD_NAME[0]+ " ASC";
			String WHERE = FIELD_NAME[11] + " == 1 ";
			
			
			item_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, null, null, null, ORDER_BY);
			
			Log.i("", "number of item list: "+cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	ItemList itemL = new ItemList();
			        	
			        		itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setPurchasePrice(cursor.getString(2));
				        	itemL.setSalePrice(cursor.getString(3));
				        	itemL.setQty(cursor.getString(4));
				        	itemL.setCategoryId(cursor.getString(5));
				        	itemL.setSubCategoryId(cursor.getString(6));
				        	itemL.setBrandId(cursor.getString(7));
				        	itemL.setMarginalPrice(cursor.getString(8));
				        	itemL.setStatus(cursor.getInt(9));
				        	itemL.setNotifyQty(cursor.getInt(10));
				        	itemL.setNotifyStatus(cursor.getInt(11));
				        	itemL.setRegisterNotify(cursor.getInt(12));
			        	
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
	
	public List<Object> selectByRegisterNotify() {
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
					FIELD_NAME[7],
					FIELD_NAME[8],
					FIELD_NAME[9],
					FIELD_NAME[10],
					FIELD_NAME[11],
					FIELD_NAME[12]
				};
			String ORDER_BY = FIELD_NAME[0]+ " ASC";
			String WHERE = FIELD_NAME[12] + " == 1 ";
			
			
			item_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, null, null, null, ORDER_BY);
			
			Log.i("", "number of item list: "+cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	ItemList itemL = new ItemList();
			        	
			        		itemL.setItemId(cursor.getString(0));
				        	itemL.setItemName(cursor.getString(1));
				        	itemL.setPurchasePrice(cursor.getString(2));
				        	itemL.setSalePrice(cursor.getString(3));
				        	itemL.setQty(cursor.getString(4));
				        	itemL.setCategoryId(cursor.getString(5));
				        	itemL.setSubCategoryId(cursor.getString(6));
				        	itemL.setBrandId(cursor.getString(7));
				        	itemL.setMarginalPrice(cursor.getString(8));
				        	itemL.setStatus(cursor.getInt(9));
				        	itemL.setNotifyQty(cursor.getInt(10));
				        	itemL.setNotifyStatus(cursor.getInt(11));
				        	itemL.setRegisterNotify(cursor.getInt(12));
			        	
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

		public boolean deleteRecord(List<Object> objList) {
			
			SQLiteDatabase db = getWritableDatabase();
			for (Object obj : objList) {
					
				itemList = (ItemList) obj;
				db.delete(TABLE_NAME, FIELD_NAME[0]+" = ?", new String[]{String.valueOf(itemList.getItemId())});
			}
			db.close();
			return true;
			
			// TODO Auto-generated method stub
			
		}

		public List<Object> deleteRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean deleteRecord(String string) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = getWritableDatabase();
			db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{string});
			db.close();
			return true;
		}
	};
	
	//Change item Status
	public void hideItem(List<Object> objList) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {
				
				ContentValues values = new ContentValues();
				ItemList iteml = (ItemList) obj;
				
				values.put(FIELD_NAME[9], 1);
				
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
	
	public void deleteItem(List<Object> objList) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {
				ItemList iteml = (ItemList) obj;
				db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{iteml.getItemId()});
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
	
	//Change Notify Status (0)
	public void deleteNotify(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Delete NOtify & Register:" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				ItemList iteml = (ItemList) obj;
				
				values.put(FIELD_NAME[11], 0);
				values.put(FIELD_NAME[12], 0);
				
				String[] VALUE = {iteml.getItemId()};
				String WHERE = FIELD_NAME[0] + " = ? ";
				
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
