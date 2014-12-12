package com.ignite.pos.database.controller;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.database.util.OnSave;
import com.ignite.pos.database.util.OnSelect;
import com.ignite.pos.model.Ledger;
import com.ignite.pos.model.SaleHistory;

public class SaleHistoryController extends DatabaseManager{

	private SaleHistory saleHistory;
	private List<Object> sale_history_list;
	
	private static final String TABLE_NAME = "tbl_sale_history";
	private static final String[] FIELD_NAME = {"vid","itemid","oldQty","newQty","updateDate","updatePerson","status"};
	
	public SaleHistoryController(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
		setOnSave(saveRecord);
		setOnSelect(selectRecord);
	}

	@Override
	
	protected void createTables() {
		// TODO Auto-generated method stub
		 connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME + " (" +
		    		FIELD_NAME[0] + " TEXT NOT NULL," + 
		    		FIELD_NAME[1] + " TEXT NULL," + 
		    		FIELD_NAME[2] + " Integer NULL," +
		    		FIELD_NAME[3] + " Integer NULL," +
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
			Log.i("", "Voucher ObjList to save: "+objList);
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					SaleHistory sh = (SaleHistory) obj;
					values.put(FIELD_NAME[0], sh.getVid());
					values.put(FIELD_NAME[1], sh.getItemid());
					values.put(FIELD_NAME[2], sh.getOldQty());
					values.put(FIELD_NAME[3], sh.getNewQty());
					values.put(FIELD_NAME[4], sh.getUpdateDate());
					values.put(FIELD_NAME[5], sh.getUpdatePerson());
					values.put(FIELD_NAME[6], sh.getStatus());

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
					};
				
				String WHERE = FIELD_NAME[2] + " != "+FIELD_NAME[6];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				sale_history_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, null, null, null, ORDER_BY);
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleHistory sh = new SaleHistory();
				        	
				        	sh.setVid(cursor.getString(0));
				        	sh.setItemid(cursor.getString(1));
				        	sh.setOldQty(cursor.getInt(2));
				        	sh.setNewQty(cursor.getInt(3));
				        	sh.setUpdateDate(cursor.getString(4));
				        	sh.setUpdatePerson(cursor.getString(5));
				        	sh.setStatus(cursor.getString(6));
				        		        	
				        	sale_history_list.add(sh);
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
			return sale_history_list;
		}

		public List<Object> selectRecord(String VouncherID) {
			// TODO Auto-generated method stub
			Log.i("","Voucher ID : " + VouncherID);
			try {
				String[] FROM = { 
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6]
						};
				
				String[] VALUE = { VouncherID };
				String WHERE = FIELD_NAME[0] + "=? " ;
				String ORDER_BY = FIELD_NAME[0] + " DESC LIMIT 1";
				
				sale_history_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				Log.i("","Cursor size :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
						do {
							SaleHistory sh = new SaleHistory();
				        	
				        	sh.setVid(cursor.getString(0));
				        	sh.setItemid(cursor.getString(1));
				        	sh.setOldQty(cursor.getInt(2));
				        	sh.setNewQty(cursor.getInt(3));
				        	sh.setUpdateDate(cursor.getString(4));
				        	sh.setUpdatePerson(cursor.getString(5));
				        	sh.setStatus(cursor.getString(6));
				        		        	
				        	sale_history_list.add(sh);
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
			return sale_history_list;
		}

		public List<Object> selectRecord(String vouid, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Voucher id: "+vouid);
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
					};
				
				String[] VALUE;
				String WHERE;
				
				if (vouid.toLowerCase().equals("all")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[4]+" >= ? and "+FIELD_NAME[4]+" <= ? and "+FIELD_NAME[2] + " != "+FIELD_NAME[6];
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = vouid;
					WHERE = FIELD_NAME[4]+" >= ? and "+FIELD_NAME[4]+" <= ? and "+FIELD_NAME[0]+" = ? and "+FIELD_NAME[2] + " != "+FIELD_NAME[6];
				}
				
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				sale_history_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
							SaleHistory sh = new SaleHistory();
				        	
				        	sh.setVid(cursor.getString(0));
				        	sh.setItemid(cursor.getString(1));
				        	sh.setOldQty(cursor.getInt(2));
				        	sh.setNewQty(cursor.getInt(3));
				        	sh.setUpdateDate(cursor.getString(4));
				        	sh.setUpdatePerson(cursor.getString(5));
				        	sh.setStatus(cursor.getString(6));
				        		        	
				        	sale_history_list.add(sh);
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
			return sale_history_list;
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
					};
				
				String[] VALUE;
				String WHERE;
				
				VALUE = new String[2];
				VALUE[0] = VouID;
				VALUE[1] = ItemID;
				WHERE = FIELD_NAME[0]+" = ? and "+FIELD_NAME[1]+" = ?";
				
				sale_history_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
							SaleHistory sh = new SaleHistory();
				        	
				        	sh.setVid(cursor.getString(0));
				        	sh.setItemid(cursor.getString(1));
				        	sh.setOldQty(cursor.getInt(2));
				        	sh.setNewQty(cursor.getInt(3));
				        	sh.setUpdateDate(cursor.getString(4));
				        	sh.setUpdatePerson(cursor.getString(5));
				        	sh.setStatus(cursor.getString(6));
				        		        	
				        	sale_history_list.add(sh);
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
			return sale_history_list;
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
					};
				
				String[] VALUE;
				String WHERE;
				
				VALUE = new String[2];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				WHERE = FIELD_NAME[4]+" >= ? and "+FIELD_NAME[4]+" <= ?";
				
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				sale_history_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
							SaleHistory sh = new SaleHistory();
				        	
				        	sh.setVid(cursor.getString(0));
				        	sh.setItemid(cursor.getString(1));
				        	sh.setOldQty(cursor.getInt(2));
				        	sh.setNewQty(cursor.getInt(3));
				        	sh.setUpdateDate(cursor.getString(4));
				        	sh.setUpdatePerson(cursor.getString(5));
				        	sh.setStatus(cursor.getString(6));
				        		        	
				        	sale_history_list.add(sh);
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
			return sale_history_list;
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
			
			try {
				String[] FROM = {
						FIELD_NAME[0], 
						FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						FIELD_NAME[4],
						FIELD_NAME[5],
						FIELD_NAME[6],
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
					WHERE = FIELD_NAME[4]+" = ? and "+FIELD_NAME[1]+" = ?";
					
				//}
				
				String GROUP_BY = FIELD_NAME[4];
				String ORDER_BY = FIELD_NAME[4]+ " ASC";
				
				sale_history_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count of Sale :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
							SaleHistory sh = new SaleHistory();
				        	
				        	sh.setVid(cursor.getString(0));
				        	sh.setItemid(cursor.getString(1));
				        	sh.setOldQty(cursor.getInt(2));
				        	sh.setNewQty(cursor.getInt(3));
				        	sh.setUpdateDate(cursor.getString(4));
				        	sh.setUpdatePerson(cursor.getString(5));
				        	sh.setStatus(cursor.getString(6));
				        		        	
				        	sale_history_list.add(sh);
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
			return sale_history_list;
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
						};
				
				String[] VALUE = { VoucherID };
				String WHERE = FIELD_NAME[0] + "=? " ;
				String ORDER_BY = FIELD_NAME[0] + " ASC";
				
				sale_history_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				
				Log.i("","Cursor size :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
						do {
							SaleHistory sh = new SaleHistory();
				        	
				        	sh.setVid(cursor.getString(0));
				        	sh.setItemid(cursor.getString(1));
				        	sh.setOldQty(cursor.getInt(2));
				        	sh.setNewQty(cursor.getInt(3));
				        	sh.setUpdateDate(cursor.getString(4));
				        	sh.setUpdatePerson(cursor.getString(5));
				        	sh.setStatus(cursor.getString(6));
				        		        	
				        	sale_history_list.add(sh);
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
			return sale_history_list;
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
				};
			
			String[] VALUE = { itemCode };
			String WHERE = FIELD_NAME[1]+" = ?";
			
			sale_history_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
			
			Log.i("","Data count of Sale :" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
						SaleHistory sh = new SaleHistory();
			        	
			        	sh.setVid(cursor.getString(0));
			        	sh.setItemid(cursor.getString(1));
			        	sh.setOldQty(cursor.getInt(2));
			        	sh.setNewQty(cursor.getInt(3));
			        	sh.setUpdateDate(cursor.getString(4));
			        	sh.setUpdatePerson(cursor.getString(5));
			        	sh.setStatus(cursor.getString(6));
			        		        	
			        	sale_history_list.add(sh);
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
		return sale_history_list;
	}
	
	public List<Object> selectByVouIdDate(String voucherID, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		
		Log.i("", "Enter here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		Log.i("", "Selected Voucher ID: "+voucherID);
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
					FIELD_NAME[6]
				};
			
			String[] VALUE;
			String WHERE;
			String HAVING;
			
			if (voucherID.toLowerCase().equals("all")) {
				VALUE = new String[2];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				WHERE = FIELD_NAME[4]+" >= ? and "+FIELD_NAME[4]+" <= ?";
			}else{
				VALUE = new String[3];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				VALUE[2] = voucherID;
				WHERE = FIELD_NAME[4]+" >= ? and "+FIELD_NAME[4]+" <= ? and "+FIELD_NAME[0]+" = ?";
			}
				
			String ORDER_BY = FIELD_NAME[0]+ " ASC";
			
			sale_history_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
			
			Log.i("","Data count of sale history :" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
						SaleHistory sh = new SaleHistory();
			        	
			        	sh.setVid(cursor.getString(0));
			        	sh.setItemid(cursor.getString(1));
			        	sh.setOldQty(cursor.getInt(2));
			        	sh.setNewQty(cursor.getInt(3));
			        	sh.setUpdateDate(cursor.getString(4));
			        	sh.setUpdatePerson(cursor.getString(5));
			        	sh.setStatus(cursor.getString(6));
			        		        	
			        	sale_history_list.add(sh);
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
		return sale_history_list;
	}	
	
	
	/*To Select Last ID for AutoID*/
	public List<SaleHistory> getLastVoucherID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<SaleHistory> voucherList = new ArrayList<SaleHistory>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	SaleHistory voucher = new SaleHistory();
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

