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
import com.ignite.pos.model.Credit;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.Supplier;

public class CreditBuyerController extends DatabaseManager{

	private Credit credit;
	private List<Object> credit_list;
	
	private static final String TABLE_NAME = "tbl_credit";
	private static final String[] FIELD_NAME = {"credit_id", "buyer_id","salevoucher_id","date","creditTotal","creditPaidAmount","creditLeftAmount","buyer_name"};
	
	public CreditBuyerController(Context ctx) {
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
		    		FIELD_NAME[1] + " INTEGER NULL," + 
		    		FIELD_NAME[2] + " TEXT NULL," + 
		    		FIELD_NAME[3] + " TEXT NULL," +
		    		FIELD_NAME[4] + " INTEGER DEFAULT 0," +
		    		FIELD_NAME[5] + " INTEGER DEFAULT 0," +
		    		FIELD_NAME[6] + " INTEGER DEFAULT 0," +
		    		FIELD_NAME[7] + " TEXT NULL)" 
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "Credit ObjList to save: "+objList);
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Credit credit = (Credit) obj;
					//values.put(FIELD_NAME[0], credit.get());
					values.put(FIELD_NAME[1], credit.getBuyer_id());
					values.put(FIELD_NAME[2], credit.getSalevoucher_id());
					values.put(FIELD_NAME[3], credit.getDate());
					values.put(FIELD_NAME[4], credit.getCreditTotal());
					values.put(FIELD_NAME[5], credit.getCreditPaidAmount());
					values.put(FIELD_NAME[6], credit.getCreditLeftAmount());
					values.put(FIELD_NAME[7], credit.getBuyer_name());

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
				
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				credit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("TAG", "-----> Cursor Count of Credit:" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	credit = new Credit();
				        	credit.setCredit_id(cursor.getInt(0));
				        	credit.setBuyer_id(cursor.getInt(1));
				        	credit.setSalevoucher_id(cursor.getString(2));
				        	credit.setDate(cursor.getString(3));
				        	credit.setCreditTotal(cursor.getInt(4));
				        	credit.setCreditPaidAmount(cursor.getInt(5));
				        	credit.setCreditLeftAmount(cursor.getInt(6));
				        	credit.setBuyer_name(cursor.getString(7));
				        					        		        	
				        	credit_list.add(credit);
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
			return credit_list;
		}

		public List<Object> selectRecord(String buyerID) {
			
			Log.i("", "Selected Buyer ID: "+buyerID);
			
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
				
				String[] VALUE = {buyerID};
				String WHERE = FIELD_NAME[1] + "=? ";
				
				credit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Count Credit list by Buyer iD: " + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	credit = new Credit();
				        	credit.setCredit_id(cursor.getInt(0));
				        	credit.setBuyer_id(cursor.getInt(1));
				        	credit.setSalevoucher_id(cursor.getString(2));
				        	credit.setDate(cursor.getString(3));
				        	credit.setCreditTotal(cursor.getInt(4));
				        	credit.setCreditPaidAmount(cursor.getInt(5));
				        	credit.setCreditLeftAmount(cursor.getInt(6));
				        	credit.setBuyer_name(cursor.getString(7));
				        					        		        	
				        	credit_list.add(credit);
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
			
			Log.i("", "DB return List: "+credit_list.toString());
			return credit_list;
			
		}

		public List<Object> selectRecord(String itemCode, String date) {
			// TODO Auto-generated method stub
			
			/*Log.i("", "Selected Item Code: "+itemCode);
			Log.i("", "Selected Date: "+date);
			
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
						FIELD_NAME[8]
					};
				
				String[] VALUE;
				String WHERE;
				
					VALUE = new String[2];
					VALUE[0] = date;
					VALUE[1] = itemCode;
					WHERE = FIELD_NAME[3]+" = ? and "+FIELD_NAME[1]+" = ?";
				
				credit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Data count of Credit :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	Credit Credit = new Credit();
				        	
				        	Credit.setCreditId(cursor.getInt(0));
				        	Credit.setItemId(cursor.getString(1));
				        	Credit.setItemName(cursor.getString(2));
				        	Credit.setDate(cursor.getString(3));
				        	Credit.setOldStockQty(cursor.getInt(4));
				        	Credit.setPurchaseQty(cursor.getInt(5));
				        	Credit.setSaleQty(cursor.getInt(6));
				        	Credit.setNewStockQty(cursor.getInt(7));
				        	Credit.setReturnQty(cursor.getInt(8));
				        					        		        	
				        	credit_list.add(Credit);
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
			return credit_list;*/
			
			return null;
		}

		public List<Object> selectRecord(String buyerName, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Buyer Name: "+buyerName);
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
						FIELD_NAME[7]
					};
				
				String[] VALUE;
				String WHERE;
				
				if (buyerName.toLowerCase().equals("all")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = buyerName;
					WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ? and "+FIELD_NAME[7]+" = ?"; //COLLATE NOCASE (case insensative)
				}
					
				//String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[2]+ " ASC";
				
				credit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				
				Log.i("","Count Credit list by Buyer iD + date: " + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	credit = new Credit
				        			();
				        	credit.setCredit_id(cursor.getInt(0));
				        	credit.setBuyer_id(cursor.getInt(1));
				        	credit.setSalevoucher_id(cursor.getString(2));
				        	credit.setDate(cursor.getString(3));
				        	credit.setCreditTotal(cursor.getInt(4));
				        	credit.setCreditPaidAmount(cursor.getInt(5));
				        	credit.setCreditLeftAmount(cursor.getInt(6));
				        	credit.setBuyer_name(cursor.getString(7));
				        					        		        	
				        	credit_list.add(credit);
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
			return credit_list;
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

	};
	
	//Get Data By Voucher ID (Group By Voucher ID)
	public List<Object> selectByVoucherID(String voucherID) {
		
		Log.i("", "Selected Voucher ID: "+voucherID);
		
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
			
			String[] VALUE = {voucherID};
			String WHERE = FIELD_NAME[2] + "=? ";
			String GROUP_BY = FIELD_NAME[2];
			
			credit_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, null);
			
			Log.i("","Count Credit list by Voucher ID: " + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	credit = new Credit();
			        	credit.setCredit_id(cursor.getInt(0));
			        	credit.setBuyer_id(cursor.getInt(1));
			        	credit.setSalevoucher_id(cursor.getString(2));
			        	credit.setDate(cursor.getString(3));
			        	credit.setCreditTotal(cursor.getInt(4));
			        	credit.setCreditPaidAmount(cursor.getInt(5));
			        	credit.setCreditLeftAmount(cursor.getInt(6));
			        	credit.setBuyer_name(cursor.getString(7));
			        					        		        	
			        	credit_list.add(credit);
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
		
		Log.i("", "DB return List: "+credit_list.toString());
		return credit_list;
		
	}
	
	//Get Data By Buyer ID (Group By Vouchers)
		public List<Object> selectGroupByVoucher(String BuyerID) {
			
			Log.i("", "Selected Voucher ID: "+BuyerID);
			
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
				
				String[] VALUE = {BuyerID};
				String WHERE = FIELD_NAME[1] + "=? ";
				String GROUP_BY = FIELD_NAME[2];
				
				credit_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, null);
				
				Log.i("","Count Credit list by Buyer ID (groupby voucher): " + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	credit = new Credit();
				        	credit.setCredit_id(cursor.getInt(0));
				        	credit.setBuyer_id(cursor.getInt(1));
				        	credit.setSalevoucher_id(cursor.getString(2));
				        	credit.setDate(cursor.getString(3));
				        	credit.setCreditTotal(cursor.getInt(4));
				        	credit.setCreditPaidAmount(cursor.getInt(5));
				        	credit.setCreditLeftAmount(cursor.getInt(6));
				        	credit.setBuyer_name(cursor.getString(7));
				        					        		        	
				        	credit_list.add(credit);
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
			
			Log.i("", "DB return List: "+credit_list.toString());
			return credit_list;
			
		}

	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Credit list to update:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Credit credit = (Credit) obj;
					values.put(FIELD_NAME[3], credit.getCreditTotal());
					values.put(FIELD_NAME[4], credit.getCreditPaidAmount());
					values.put(FIELD_NAME[5], credit.getCreditLeftAmount());

					String[] VALUE = {credit.getBuyer_id().toString(),credit.getDate()};
					String WHERE = FIELD_NAME[0] + "=? and "+FIELD_NAME[2] +" = ?";
					
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
			/*Log.i("","Update :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					Credit Credit = (Credit) obj;
					//values.put(FIELD_NAME[0], Credit.getCreditId());
					values.put(FIELD_NAME[1], Credit.getItemId());
					values.put(FIELD_NAME[2], Credit.getItemName());
					values.put(FIELD_NAME[3], Credit.getDate());
					values.put(FIELD_NAME[4], Credit.getOldStockQty());
					values.put(FIELD_NAME[5], Credit.getPurchaseQty());
					values.put(FIELD_NAME[6], Credit.getSaleQty());
					values.put(FIELD_NAME[7], Credit.getNewStockQty());
					values.put(FIELD_NAME[8], Credit.getReturnQty());

					String[] VALUE = {Credit.getItemId(), Credit.getDate()};
					String WHERE = FIELD_NAME[1] + "=? and "+FIELD_NAME[3]+"=?";
					
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
			}*/
		
		}
	};
	
	private OnDelete deleteRecord = new OnDelete() {
		
		public boolean deleteRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			return false;
		}
		
		public List<Object> deleteRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public void deleteRecord() {
			// TODO Auto-generated method stub
			
			/*SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, null, null);
	        db.close();
			return;*/
		}

		public boolean deleteRecord(String string) {
			/*SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, FIELD_NAME[0]+"=? ", new String[]{string});
	        db.close();
			return true;*/
			
			return false;
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
	
