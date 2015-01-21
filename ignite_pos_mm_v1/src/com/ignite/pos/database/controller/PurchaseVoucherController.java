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
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.PurchaseVoucher;
import com.ignite.pos.model.SaleVouncher;

public class PurchaseVoucherController extends DatabaseManager{

	private PurchaseVoucher purchaseVoucher;
	private List<Object> purchase_voucher;
	
	private static final String TABLE_NAME = "tbl_purchase";
	private static final String[] FIELD_NAME = {"vid","supname","itemid","itemname","qty","price","itemtotal","vdate","grandtotal","oldStockQty","marginalprice","saleprice","status"};
	
	public PurchaseVoucherController(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
		setOnSave(saveRecord);
		setOnSelect(selectRecord);
		setOnDelete(deleteRecord);
		setOnUpate(updateRecord);
	}

	@Override
	protected void createTables() {
		// TODO Auto-generated method stub
		 connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME + " (" +
		    		FIELD_NAME[0] + " TEXT NOT NULL," + 
		    		FIELD_NAME[1] + " TEXT NULL," + 
		    		FIELD_NAME[2] + " TEXT NULL," +
		    		FIELD_NAME[3] + " TEXT NULL," +
		    		FIELD_NAME[4] + " TEXT NULL," +
		    		FIELD_NAME[5] + " TEXT NULL," +
		    		FIELD_NAME[6] + " TEXT NULL," +
		    		FIELD_NAME[7] + " TEXT NULL," +
		    		FIELD_NAME[8] + " TEXT NULL," +
		    		FIELD_NAME[9] + " TEXT NULL," +
		    		FIELD_NAME[10] + " TEXT NULL," +
		    		FIELD_NAME[11] + " TEXT NULL," +
		    		FIELD_NAME[12] + " INTEGER DEFAULT 0)"
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			
			Log.i("", "Purchase List to save: "+objList.toString());
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					PurchaseVoucher pv = (PurchaseVoucher) obj;
					values.put(FIELD_NAME[0], pv.getVid());
					values.put(FIELD_NAME[1], pv.getSupplierName());
					values.put(FIELD_NAME[2], pv.getItemid());
					values.put(FIELD_NAME[3], pv.getItemname());
					values.put(FIELD_NAME[4], pv.getQty());
					values.put(FIELD_NAME[5], pv.getPurchasePrice());
					values.put(FIELD_NAME[6], pv.getItemtotal());
					values.put(FIELD_NAME[7], pv.getVdate());
					values.put(FIELD_NAME[8], pv.getGrandtotal());
					values.put(FIELD_NAME[9], pv.getOldStockQty());
					values.put(FIELD_NAME[10], pv.getMarginalPrice());
					values.put(FIELD_NAME[11], pv.getSalePrice());
					values.put(FIELD_NAME[12], pv.getStatus());

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

		/* (non-Javadoc)
		 * @see com.ignite.pos.database.util.OnSelect#selectRecord()
		 */
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
				
				//String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("","Data count All Purchase:" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        	pv.setOldStockQty(cursor.getString(9));
				        	pv.setMarginalPrice(cursor.getString(10));
				        	pv.setSalePrice(cursor.getString(11));
				        	pv.setStatus(cursor.getInt(12));
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
		}

		public List<Object> selectRecord(String VoucherID) {
			// TODO Auto-generated method stub
			Log.i("","Fields : " + VoucherID);
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
				
				String[] VALUE = { VoucherID };
				String WHERE = FIELD_NAME[0] + "=? " ;
				String ORDER_BY = FIELD_NAME[0] + " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				Log.i("","Cursor size :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
						do {
							PurchaseVoucher pv = new PurchaseVoucher();

							pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        	pv.setOldStockQty(cursor.getString(9));
				        	pv.setMarginalPrice(cursor.getString(10));
				        	pv.setSalePrice(cursor.getString(11));
				        	pv.setStatus(cursor.getInt(12));

							purchase_voucher.add(pv);
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
			return purchase_voucher;
		}

		public List<Object> selectRecord(String supName, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Supplier: "+supName);
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
				
				if (supName.equals("All")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = supName;
					WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ? and "+FIELD_NAME[1]+" = ?";
				}
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        	pv.setOldStockQty(cursor.getString(9));
				        	pv.setMarginalPrice(cursor.getString(10));
				        	pv.setSalePrice(cursor.getString(11));
				        	pv.setStatus(cursor.getInt(12));
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
		}

		public List<Object> selectRecord(String VouID, String ItemID) {
			// TODO Auto-generated method stub
			
			
			Log.i("", "Purchase Voucher ID: "+VouID);
			Log.i("", "Purchase Item ID: "+ItemID);
			
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
				
				VALUE = new String[2];
				VALUE[0] = VouID;
				VALUE[1] = ItemID;
				
				WHERE = FIELD_NAME[0]+" = ? and "+FIELD_NAME[2]+" = ?";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        	pv.setOldStockQty(cursor.getString(9));
				        	pv.setMarginalPrice(cursor.getString(10));
				        	pv.setSalePrice(cursor.getString(11));
				        	pv.setStatus(cursor.getInt(12));
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
		}

		public List<Object> selectRecordByItemCode(String itemCode, String date) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Item Code: "+itemCode);
			Log.i("", "Selected From Date: "+date);
			//Log.i("", "Selected To Date: "+toDate);
			
			try {
				String[] FROM = {
						//FIELD_NAME[0],
						//FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						//FIELD_NAME[4],
						//FIELD_NAME[5],
						//FIELD_NAME[6],
						FIELD_NAME[7],
						//FIELD_NAME[8],
						"SUM(qty) AS qtytotal",
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[12]
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
					WHERE = FIELD_NAME[7]+" = ? and "+FIELD_NAME[2]+" = ?";
				//}
				
				String GROUP_BY = FIELD_NAME[7];
				String ORDER_BY = FIELD_NAME[7]+ " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count of Purchase :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	//pv.setVid(cursor.getString(0));
				        	//pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(0));
				        	pv.setItemname(cursor.getString(1));
				        	//pv.setQty(cursor.getString(4));
				        	//pv.setPurchasePrice(cursor.getString(5));
				        	//pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(2));
				        	//pv.setGrandtotal(cursor.getString(8));
				        	pv.setQtyTotal(cursor.getString(3));
				        	pv.setOldStockQty(cursor.getString(4));
				        	pv.setMarginalPrice(cursor.getString(5));
				        	pv.setStatus(cursor.getInt(6));
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
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
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
					};
				
				String[] VALUE;
				String WHERE;
				
				VALUE = new String[2];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ?";
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        	pv.setOldStockQty(cursor.getString(9));
				        	pv.setMarginalPrice(cursor.getString(10));
				        	pv.setSalePrice(cursor.getString(11));
				        	pv.setStatus(cursor.getInt(12));
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
			
		}

		public List<Object> selectPurchaseVoubyItemID(String itemID) {
			// TODO Auto-generated method stub
			
			Log.i("","Fields : " + itemID);
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
				
				String[] VALUE = { itemID };
				String WHERE = FIELD_NAME[2] + " =?";
				//String ORDER_BY = FIELD_NAME[1] + " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, null);
				Log.i("","Cursor size :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
						do {
							PurchaseVoucher pv = new PurchaseVoucher();

							pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        	pv.setOldStockQty(cursor.getString(9));
				        	pv.setMarginalPrice(cursor.getString(10));
				        	pv.setSalePrice(cursor.getString(11));
				        	pv.setStatus(cursor.getInt(12));

							purchase_voucher.add(pv);
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
			
			return purchase_voucher;
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
					};
				
				String GROUP_BY = FIELD_NAME[2];
				String ORDER_BY = FIELD_NAME[2]+ " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
		}

		public List<Object> selectRecordOldStockQty(String itemcode, String date) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Item Code: "+itemcode);
			Log.i("", "Selected Date: "+date);
			
			try {
				String[] FROM = {
						//FIELD_NAME[0],
						//FIELD_NAME[1],
						FIELD_NAME[2], 
						//FIELD_NAME[3],
						//FIELD_NAME[4],
						//FIELD_NAME[5],
						//FIELD_NAME[6],
						FIELD_NAME[7],
						//FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10]
					};
				
				String[] VALUE;
				String WHERE;
				
					VALUE = new String[2];
					VALUE[0] = itemcode;
					VALUE[1] = date;
					WHERE = FIELD_NAME[2]+" = ? and "+FIELD_NAME[7]+" = ?";
				
				String ORDER_BY = FIELD_NAME[7]+ " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY, "1");
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	pv.setItemid(cursor.getString(0));
				        	pv.setVdate(cursor.getString(1));
				        	pv.setOldStockQty(cursor.getString(2));
				        	pv.setMarginalPrice(cursor.getString(3));				        	
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
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
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12]
						};
				
				String[] VALUE = { VoucherID };
				String WHERE = FIELD_NAME[0] + " = ? " ;
				String ORDER_BY = FIELD_NAME[0] + " ASC";
				
				purchase_voucher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				
				Log.i("","Cursor size :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
						do {
							PurchaseVoucher pv = new PurchaseVoucher();
				        	
				        	pv.setVid(cursor.getString(0));
				        	pv.setSupplierName(cursor.getString(1));
				        	pv.setItemid(cursor.getString(2));
				        	pv.setItemname(cursor.getString(3));
				        	pv.setQty(cursor.getString(4));
				        	pv.setOldQty(cursor.getString(4));
				        	pv.setPurchasePrice(cursor.getString(5));
				        	pv.setItemtotal(cursor.getString(6));
				        	pv.setVdate(cursor.getString(7));
				        	pv.setGrandtotal(cursor.getString(8));
				        	pv.setOldStockQty(cursor.getString(9));
				        	pv.setMarginalPrice(cursor.getString(10));
				        	pv.setSalePrice(cursor.getString(11));
				        	pv.setStatus(cursor.getInt(12));
				        		        	
				        	purchase_voucher.add(pv);
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
			return purchase_voucher;
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
	
	public List<Object> selectbyItemID(String itemID) {
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
			
			String[] VALUE = { itemID };
			String WHERE = FIELD_NAME[2] + " =?";
			String ORDER_BY = FIELD_NAME[1] + " DESC";
			
			purchase_voucher = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
			Log.i("","Cursor size :" + cursor.getCount());
			try {
				if (cursor.moveToFirst()) {
					do {
						PurchaseVoucher pv = new PurchaseVoucher();

						pv.setVid(cursor.getString(0));
			        	pv.setSupplierName(cursor.getString(1));
			        	pv.setItemid(cursor.getString(2));
			        	pv.setItemname(cursor.getString(3));
			        	pv.setQty(cursor.getString(4));
			        	pv.setPurchasePrice(cursor.getString(5));
			        	pv.setItemtotal(cursor.getString(6));
			        	pv.setVdate(cursor.getString(7));
			        	pv.setGrandtotal(cursor.getString(8));
			        	pv.setOldStockQty(cursor.getString(9));
			        	pv.setMarginalPrice(cursor.getString(10));
			        	pv.setSalePrice(cursor.getString(11));
			        	pv.setStatus(cursor.getInt(12));

						purchase_voucher.add(pv);
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
		
		return purchase_voucher;
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
			
			Log.i("", "Item List to delete: "+objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			for (Object obj : objList) {
					
				PurchaseVoucher itemObj = (PurchaseVoucher) obj;
				
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

		public boolean deleteRecord(String id) {
			// TODO Auto-generated method stub
			Log.i("", "Voucher ID to delete: "+id);
			
			SQLiteDatabase db = getWritableDatabase();
			
			db.delete(TABLE_NAME, FIELD_NAME[0]+" = ? ", new String[]{id});
			db.close();
			return true;
		}
	};
	
	private OnUpdate updateRecord = new OnUpdate() {

		public void updateRecord(Object obj) {
			// TODO Auto-generated method stub
			
		}

		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update Purchase Voucher:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					PurchaseVoucher pv = (PurchaseVoucher) obj;
					
					values.put(FIELD_NAME[4], pv.getQty());
					values.put(FIELD_NAME[5], pv.getPurchasePrice());
					values.put(FIELD_NAME[6], pv.getItemtotal());
					values.put(FIELD_NAME[8], pv.getGrandtotal());
					
					String[] VALUE = {pv.getVid(), pv.getItemid()};
					String WHERE = FIELD_NAME[0] + " =? and "+FIELD_NAME[2]+" = ?";
					
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
			
		}
		
		
	};
	
	//Update Purchase Voucher
	public void updateByVouIDItemID(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Update List for Purchase Voucher:" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				PurchaseVoucher pv = (PurchaseVoucher) obj;
				
				values.put(FIELD_NAME[4], pv.getQty());
				values.put(FIELD_NAME[6], pv.getItemtotal());
				values.put(FIELD_NAME[8], pv.getGrandtotal());
				values.put(FIELD_NAME[1], pv.getSupplierName());
				values.put(FIELD_NAME[5], pv.getPurchasePrice());
				
				String[] VALUE = {pv.getVid(), pv.getItemid()};
				String WHERE = FIELD_NAME[0] + " =? and "+FIELD_NAME[2]+" = ?";
				
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
	
	//Update Purchase Voucher by VouID
		public void updateByVouID(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update List for Purchase Voucher:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					PurchaseVoucher pv = (PurchaseVoucher) obj;
					
					//values.put(FIELD_NAME[4], salev.getQty());
					//values.put(FIELD_NAME[6], salev.getItemtotal());
					values.put(FIELD_NAME[8], pv.getGrandtotal());
					//values.put(FIELD_NAME[9], salev.getSalePerson());
				//	values.put(FIELD_NAME[11], salev.getDiscount());
					
					String[] VALUE = {pv.getVid()};
					String WHERE = FIELD_NAME[0] + " =? ";
					
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
		
		//Update Purchase Confirm
		public void updatePurchaseConfirm(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update List to confirm :" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					PurchaseVoucher pv = (PurchaseVoucher) obj;
					
					values.put(FIELD_NAME[10], pv.getMarginalPrice());
					values.put(FIELD_NAME[11], pv.getSalePrice());
					values.put(FIELD_NAME[12], pv.getStatus());
					
					String[] VALUE = {pv.getVid(), pv.getItemid()};
					String WHERE = FIELD_NAME[0] + " = ? and "+FIELD_NAME[2]+" = ?";
					
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
	public List<PurchaseVoucher> getLastVoucherID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<PurchaseVoucher> voucherList = new ArrayList<PurchaseVoucher>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	PurchaseVoucher voucher = new PurchaseVoucher();
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

