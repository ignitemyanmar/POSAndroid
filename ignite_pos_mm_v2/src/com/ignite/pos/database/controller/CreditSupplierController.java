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
import com.ignite.pos.model.CreditSupplier;
import com.ignite.pos.model.Profit;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleVouncher;
import com.ignite.pos.model.Supplier;

public class CreditSupplierController extends DatabaseManager{

	private CreditSupplier creditSupplier;
	private List<Object> creditSupplier_list;
	
	private static final String TABLE_NAME = "tbl_credit_supplier";
	private static final String[] FIELD_NAME = {"creditID", "supplierID", "purchaseVoucherID","date","creditTotal","creditPaidAmount","creditLeftAmount", "supplierName"};
	
	public CreditSupplierController(Context ctx) {
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
			
			Log.i("", "CreditSupplier ObjList to save: "+objList);
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					CreditSupplier creditSupplier = (CreditSupplier) obj;
					//values.put(FIELD_NAME[0], CreditSupplier.get());
					values.put(FIELD_NAME[1], creditSupplier.getSupplierID());
					values.put(FIELD_NAME[2], creditSupplier.getPurchaseVoucherID());
					values.put(FIELD_NAME[3], creditSupplier.getDate());
					values.put(FIELD_NAME[4], creditSupplier.getCreditTotal());
					values.put(FIELD_NAME[5], creditSupplier.getCreditPaidAmount());
					values.put(FIELD_NAME[6], creditSupplier.getCreditLeftAmount());
					values.put(FIELD_NAME[7], creditSupplier.getSupplierName());

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
				
				String ORDER_BY = FIELD_NAME[2]+ " ASC";
				
				creditSupplier_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("TAG", "-----> Cursor Count of CreditSupplier:" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	creditSupplier = new CreditSupplier();
				        	creditSupplier.setCreditID(cursor.getInt(0));
				        	creditSupplier.setSupplierID(cursor.getInt(1));
				        	creditSupplier.setPurchaseVoucherID(cursor.getString(2));
				        	creditSupplier.setDate(cursor.getString(3));
				        	creditSupplier.setCreditTotal(cursor.getInt(4));
				        	creditSupplier.setCreditPaidAmount(cursor.getInt(5));
				        	creditSupplier.setCreditLeftAmount(cursor.getInt(6));
				        	creditSupplier.setSupplierName(cursor.getString(7));
				        					        		        	
				        	creditSupplier_list.add(creditSupplier);
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
			return creditSupplier_list;
		}

		public List<Object> selectRecord(String supplierID) {
			
			Log.i("", "Selected supplierID: "+supplierID);
			
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
				
				String[] VALUE = {supplierID};
				String WHERE = FIELD_NAME[1] + "=? ";
				
				creditSupplier_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Count Credit list by Supplier iD: " + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	creditSupplier = new CreditSupplier();
				        	creditSupplier.setCreditID(cursor.getInt(0));
				        	creditSupplier.setSupplierID(cursor.getInt(1));
				        	creditSupplier.setPurchaseVoucherID(cursor.getString(2));
				        	creditSupplier.setDate(cursor.getString(3));
				        	creditSupplier.setCreditTotal(cursor.getInt(4));
				        	creditSupplier.setCreditPaidAmount(cursor.getInt(5));
				        	creditSupplier.setCreditLeftAmount(cursor.getInt(6));
				        	creditSupplier.setSupplierName(cursor.getString(7));
				        					        		        	
				        	creditSupplier_list.add(creditSupplier);
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
			
			return creditSupplier_list;
			
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
				
				CreditSupplier_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Data count of CreditSupplier :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	CreditSupplier CreditSupplier = new CreditSupplier();
				        	
				        	CreditSupplier.setCreditSupplierId(cursor.getInt(0));
				        	CreditSupplier.setItemId(cursor.getString(1));
				        	CreditSupplier.setItemName(cursor.getString(2));
				        	CreditSupplier.setDate(cursor.getString(3));
				        	CreditSupplier.setOldStockQty(cursor.getInt(4));
				        	CreditSupplier.setPurchaseQty(cursor.getInt(5));
				        	CreditSupplier.setSaleQty(cursor.getInt(6));
				        	CreditSupplier.setNewStockQty(cursor.getInt(7));
				        	CreditSupplier.setReturnQty(cursor.getInt(8));
				        					        		        	
				        	CreditSupplier_list.add(CreditSupplier);
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
			return CreditSupplier_list;*/
			
			return null;
		}

		public List<Object> selectRecord(String supplierName, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected supplier Name: "+supplierName);
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
				
				if (supplierName.toLowerCase().equals("all")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = supplierName;
					WHERE = FIELD_NAME[3]+" >= ? and "+FIELD_NAME[3]+" <= ? and "+FIELD_NAME[7]+" = ? COLLATE NOCASE"; //COLLATE NOCASE (case insensative)
				}
					
				//String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[2]+ " ASC";
				
				creditSupplier_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				
				Log.i("","Count Credit list by Supplier iD + date: " + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	creditSupplier = new CreditSupplier();
				        	creditSupplier.setCreditID(cursor.getInt(0));
				        	creditSupplier.setSupplierID(cursor.getInt(1));
				        	creditSupplier.setPurchaseVoucherID(cursor.getString(2));
				        	creditSupplier.setDate(cursor.getString(3));
				        	creditSupplier.setCreditTotal(cursor.getInt(4));
				        	creditSupplier.setCreditPaidAmount(cursor.getInt(5));
				        	creditSupplier.setCreditLeftAmount(cursor.getInt(6));
				        	creditSupplier.setSupplierName(cursor.getString(7));
				        					        		        	
				        	creditSupplier_list.add(creditSupplier);
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
			return creditSupplier_list;
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
			
			creditSupplier_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, null);
			
			Log.i("","Count Credit list by Voucher ID: " + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	creditSupplier = new CreditSupplier();
			        	creditSupplier.setCreditID(cursor.getInt(0));
			        	creditSupplier.setSupplierID(cursor.getInt(1));
			        	creditSupplier.setPurchaseVoucherID(cursor.getString(2));
			        	creditSupplier.setDate(cursor.getString(3));
			        	creditSupplier.setCreditTotal(cursor.getInt(4));
			        	creditSupplier.setCreditPaidAmount(cursor.getInt(5));
			        	creditSupplier.setCreditLeftAmount(cursor.getInt(6));
			        	creditSupplier.setSupplierName(cursor.getString(7));
			        					        		        	
			        	creditSupplier_list.add(creditSupplier);
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
		
		return creditSupplier_list;
		
	}
	
	//Get Data By Supplier ID (Group By Vouchers)
		public List<Object> selectGroupByVoucher(String supplierID) {
			
			Log.i("", "Selected Supplier ID: "+supplierID);
			
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
				
				String[] VALUE = {supplierID};
				String WHERE = FIELD_NAME[1] + "=? ";
				String GROUP_BY = FIELD_NAME[2];
				
				creditSupplier_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, null);
				
				Log.i("","Count Credit list by Supplier ID (groupby voucher): " + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	creditSupplier = new CreditSupplier();
				        	creditSupplier.setCreditID(cursor.getInt(0));
				        	creditSupplier.setSupplierID(cursor.getInt(1));
				        	creditSupplier.setPurchaseVoucherID(cursor.getString(2));
				        	creditSupplier.setDate(cursor.getString(3));
				        	creditSupplier.setCreditTotal(cursor.getInt(4));
				        	creditSupplier.setCreditPaidAmount(cursor.getInt(5));
				        	creditSupplier.setCreditLeftAmount(cursor.getInt(6));
				        	creditSupplier.setSupplierName(cursor.getString(7));
				        					        		        	
				        	creditSupplier_list.add(creditSupplier);
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
			
			return creditSupplier_list;
			
		}

	private OnUpdate updateRecord = new OnUpdate() {
		
		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Credit Supplier list to update:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					CreditSupplier credit = (CreditSupplier) obj;
					values.put(FIELD_NAME[3], credit.getCreditTotal());
					values.put(FIELD_NAME[4], credit.getCreditPaidAmount());
					values.put(FIELD_NAME[5], credit.getCreditLeftAmount());

					String[] VALUE = {credit.getSupplierID().toString(),credit.getDate()};
					String WHERE = FIELD_NAME[1] + "=? and "+FIELD_NAME[3] +" = ?";
					
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
					CreditSupplier CreditSupplier = (CreditSupplier) obj;
					//values.put(FIELD_NAME[0], CreditSupplier.getCreditSupplierId());
					values.put(FIELD_NAME[1], CreditSupplier.getItemId());
					values.put(FIELD_NAME[2], CreditSupplier.getItemName());
					values.put(FIELD_NAME[3], CreditSupplier.getDate());
					values.put(FIELD_NAME[4], CreditSupplier.getOldStockQty());
					values.put(FIELD_NAME[5], CreditSupplier.getPurchaseQty());
					values.put(FIELD_NAME[6], CreditSupplier.getSaleQty());
					values.put(FIELD_NAME[7], CreditSupplier.getNewStockQty());
					values.put(FIELD_NAME[8], CreditSupplier.getReturnQty());

					String[] VALUE = {CreditSupplier.getItemId(), CreditSupplier.getDate()};
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

		public boolean deleteRecord(String voucherID) {
			SQLiteDatabase db = getWritableDatabase();
	        db.delete(TABLE_NAME, FIELD_NAME[2]+"=? ", new String[]{voucherID});
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
	

