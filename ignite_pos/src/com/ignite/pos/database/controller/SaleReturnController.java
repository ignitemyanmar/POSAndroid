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
import com.ignite.pos.model.SaleReturn;
import com.ignite.pos.model.SaleReturn;

public class SaleReturnController extends DatabaseManager{

	private List<Object> return_list;
	
	private static final String TABLE_NAME = "tbl_return";
	private static final String[] FIELD_NAME = {"vid","itemid","oldQty","returnQty","salePrice","itemTotal","returnDate","updatePerson"};
	
	public SaleReturnController(Context ctx) {
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
		    		FIELD_NAME[0] + " TEXT NOT NULL," + 
		    		FIELD_NAME[1] + " TEXT NULL," + 
		    		FIELD_NAME[2] + " Integer NULL," +
		    		FIELD_NAME[3] + " Integer NULL," +
		    		FIELD_NAME[4] + " Integer NULL," +
		    		FIELD_NAME[5] + " Integer NULL," +
		    		FIELD_NAME[6] + " TEXT NULL," +
		    		FIELD_NAME[7] + " TEXT NULL)" 
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("", "Voucher ObjList to save: "+objList);
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					SaleReturn re = (SaleReturn) obj;
					values.put(FIELD_NAME[0], re.getVid());
					values.put(FIELD_NAME[1], re.getItemid());
					values.put(FIELD_NAME[2], re.getOldQty());
					values.put(FIELD_NAME[3], re.getReturnQty());
					values.put(FIELD_NAME[4], re.getSalePrice());
					values.put(FIELD_NAME[5], re.getItemTotal());
					values.put(FIELD_NAME[6], re.getReturnDate());
					values.put(FIELD_NAME[7], re.getUpdatePerson());

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
						FIELD_NAME[6],
						FIELD_NAME[7]
					};
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				return_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, GROUP_BY, null, ORDER_BY);
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleReturn re = new SaleReturn();
				        	
				        	re.setVid(cursor.getString(0));
				        	re.setItemid(cursor.getString(1));
				        	re.setOldQty(cursor.getInt(2));
				        	re.setReturnQty(cursor.getInt(3));
				        	re.setSalePrice(cursor.getInt(4));
				        	re.setItemTotal(cursor.getInt(5));
				        	re.setReturnDate(cursor.getString(6));
				        	re.setUpdatePerson(cursor.getString(7));
				        		        	
				        	return_list.add(re);
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
			return return_list;
		}

		public List<Object> selectRecord(String VouncherID) {
			// TODO Auto-generated method stub
			Log.i("","Fields : " + VouncherID);
			try {
				String[] FROM = { 
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
						FIELD_NAME[7]
						};
				
				String[] VALUE = { VouncherID };
				String WHERE = FIELD_NAME[0] + "=? " ;
				String ORDER_BY = FIELD_NAME[0] + " DESC LIMIT 1";
				
				return_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				Log.i("","Cursor size :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
						do {
				        	SaleReturn re = new SaleReturn();
				        	
				        	re.setVid(cursor.getString(0));
				        	re.setItemid(cursor.getString(1));
				        	re.setOldQty(cursor.getInt(2));
				        	re.setReturnQty(cursor.getInt(3));
				        	re.setSalePrice(cursor.getInt(4));
				        	re.setItemTotal(cursor.getInt(5));
				        	re.setReturnDate(cursor.getString(6));
				        	re.setUpdatePerson(cursor.getString(7));
				        		        	
				        	return_list.add(re);
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
			return return_list;
		}

		public List<Object> selectRecord(String vouNo, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Return Voucher No: "+vouNo);
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
					};
				
				String[] VALUE;
				String WHERE;
				
				if (vouNo.toLowerCase().equals("all")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[6]+" >= ? and "+FIELD_NAME[6]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = vouNo;
					WHERE = FIELD_NAME[6]+" >= ? and "+FIELD_NAME[6]+" <= ? and "+FIELD_NAME[0]+" = ?";
				}
				
				//String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				return_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleReturn re = new SaleReturn();
				        	
				        	re.setVid(cursor.getString(0));
				        	re.setItemid(cursor.getString(1));
				        	re.setOldQty(cursor.getInt(2));
				        	re.setReturnQty(cursor.getInt(3));
				        	re.setSalePrice(cursor.getInt(4));
				        	re.setItemTotal(cursor.getInt(5));
				        	re.setReturnDate(cursor.getString(6));
				        	re.setUpdatePerson(cursor.getString(7));
				        		        	
				        	return_list.add(re);
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
			return return_list;
		}

		public List<Object> selectRecord(String VouID, String ItemID) {
			// TODO Auto-generated method stub
			
			Log.i("", "Sale Voucher ID: "+VouID);
			Log.i("", "Sale Item ID: "+ItemID);
			
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
					};
				
				String[] VALUE;
				String WHERE;
				
				VALUE = new String[2];
				VALUE[0] = VouID;
				VALUE[1] = ItemID;
				WHERE = FIELD_NAME[0]+" = ? and "+FIELD_NAME[1]+" = ?";
				
				return_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleReturn re = new SaleReturn();
				        	
				        	re.setVid(cursor.getString(0));
				        	re.setItemid(cursor.getString(1));
				        	re.setOldQty(cursor.getInt(2));
				        	re.setReturnQty(cursor.getInt(3));
				        	re.setSalePrice(cursor.getInt(4));
				        	re.setItemTotal(cursor.getInt(5));
				        	re.setReturnDate(cursor.getString(6));
				        	re.setUpdatePerson(cursor.getString(7));
				        		        	
				        	return_list.add(re);
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
			return return_list;
		}

		public List<Object> selectRecordGroupBy(String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
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
					};
				
				String[] VALUE;
				String WHERE;
				
				VALUE = new String[2];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				WHERE = FIELD_NAME[6]+" >= ? and "+FIELD_NAME[6]+" <= ?";
				
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				return_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleReturn re = new SaleReturn();
				        	
				        	re.setVid(cursor.getString(0));
				        	re.setItemid(cursor.getString(1));
				        	re.setOldQty(cursor.getInt(2));
				        	re.setReturnQty(cursor.getInt(3));
				        	re.setSalePrice(cursor.getInt(4));
				        	re.setItemTotal(cursor.getInt(5));
				        	re.setReturnDate(cursor.getString(6));
				        	re.setUpdatePerson(cursor.getString(7));
				        		        	
				        	return_list.add(re);
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
			return return_list;
		}

		public List<Object> selectPurchaseVoubyItemID(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectGroupByItemCode() {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordByItemCode(String itemCode, String date) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Item Code: "+itemCode);
			Log.i("", "Selected From Date: "+date);
			//Log.i("", "Selected To Date: "+toDate);
			
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
						"SUM(qty) AS qtytotal"
					};
				
				String[] VALUE;
				String WHERE;
				
				/*if (VoucherNo.equals("All")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ?";
				}else{*/
					VALUE = new String[2];
					VALUE[0] = date;
					VALUE[1] = itemCode;
					//WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ? and "+FIELD_NAME[2]+" = ?";
					WHERE = FIELD_NAME[6]+" = ? and "+FIELD_NAME[1]+" = ?";
					
				//}
				
				String GROUP_BY = FIELD_NAME[6];
				String ORDER_BY = FIELD_NAME[6]+ " ASC";
				
				return_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count of Sale :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleReturn re = new SaleReturn();
				        	
				        	re.setVid(cursor.getString(0));
				        	re.setItemid(cursor.getString(1));
				        	re.setOldQty(cursor.getInt(2));
				        	re.setReturnQty(cursor.getInt(3));
				        	re.setSalePrice(cursor.getInt(4));
				        	re.setItemTotal(cursor.getInt(5));
				        	re.setReturnDate(cursor.getString(6));
				        	re.setUpdatePerson(cursor.getString(7));
				        		        	
				        	return_list.add(re);
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
			return return_list;
		}

		public List<Object> selectRecordOldStockQty(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordByVouID(String VoucherID) {
			// TODO Auto-generated method stub
			
			Log.i("","Voucher ID : " + VoucherID);
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
						};
				
				String[] VALUE = { VoucherID };
				String WHERE = FIELD_NAME[0] + "=? " ;
				String ORDER_BY = FIELD_NAME[0] + " ASC";
				
				return_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				
				Log.i("","Cursor size :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
						do {
				        	SaleReturn re = new SaleReturn();
				        	
				        	re.setVid(cursor.getString(0));
				        	re.setItemid(cursor.getString(1));
				        	re.setOldQty(cursor.getInt(2));
				        	re.setReturnQty(cursor.getInt(3));
				        	re.setSalePrice(cursor.getInt(4));
				        	re.setItemTotal(cursor.getInt(5));
				        	re.setReturnDate(cursor.getString(6));
				        	re.setUpdatePerson(cursor.getString(7));
				        		        	
				        	return_list.add(re);
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
			return return_list;
		}

		public List<Object> selectRecordAllByCatSub(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Object> selectRecordPurchasedItems() {
			// TODO Auto-generated method stub
			return null;
		}

	};
	
	//Select Record by Item ID
	public List<Object> selectRecordByItemCode(String itemCode) {
		// TODO Auto-generated method stub
		
		Log.i("", "Selected Item Code: "+itemCode);
		
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
				};
			
			String[] VALUE = { itemCode };
			String WHERE = FIELD_NAME[1]+" = ?";
			
			return_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
			
			Log.i("","Data count of Sale :" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	SaleReturn re = new SaleReturn();
			        	
			        	re.setVid(cursor.getString(0));
			        	re.setItemid(cursor.getString(1));
			        	re.setOldQty(cursor.getInt(2));
			        	re.setReturnQty(cursor.getInt(3));
			        	re.setSalePrice(cursor.getInt(4));
			        	re.setItemTotal(cursor.getInt(5));
			        	re.setReturnDate(cursor.getString(6));
			        	re.setUpdatePerson(cursor.getString(7));
			        		        	
			        	return_list.add(re);
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
		return return_list;
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

		public boolean deleteRecord(List<Object> objList) {
			
			Log.i("", "Item List to delete"+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			for (Object obj : objList) {
					
				SaleReturn itemObj = (SaleReturn) obj;
				
				String WHERE = FIELD_NAME[0]+" = ? and "+FIELD_NAME[2]+" = ?";
				String[] VALUE = {itemObj.getVid(), itemObj.getItemid()};
				//db.delete(TABLE_NAME, FIELD_NAME[0]+" = ?", new String[]{String.valueOf(itemObj.getItemid())});
				db.delete(TABLE_NAME, WHERE, VALUE);
			}
			db.close();
			return true;
			
		}

		public List<Object> deleteRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean deleteRecord(String voucherID) {
			// TODO Auto-generated method stub
			Log.i("", "Voucher ID to delete: "+voucherID);
			
			SQLiteDatabase db = getWritableDatabase();
			
			db.delete(TABLE_NAME, FIELD_NAME[0]+" = ? ", new String[]{voucherID});
			db.close();
			return true;
		}
	};
	
	/*To Select Last ID for AutoID*/
	public List<SaleReturn> getLastVoucherID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<SaleReturn> voucherList = new ArrayList<SaleReturn>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	SaleReturn voucher = new SaleReturn();
	        	voucher.setVid(cursor.getString(0));
	        	
	        	Log.i("", "cursor obj from last category id: "+cursor.getString(0));
	        	
	        	voucherList.add(voucher);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    
	    //Log.i("","Category List of last:" + categoryList);
	    
	    return voucherList; 
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
