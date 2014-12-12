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

public class SaleVouncherController extends DatabaseManager{

	private SaleVouncher saleVouncher;
	private List<Object> sale_vouncher;
	
	private static final String TABLE_NAME = "tbl_sale";
	private static final String[] FIELD_NAME = {"vid","cusname","itemid","itemname","qty","price","itemtotal","vdate","total","salePerson","updatePerson","discount","updateDate","marginalPrice","returnableQty"};
	
	public SaleVouncherController(Context ctx) {
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
		    		FIELD_NAME[4] + " Integer NULL," +
		    		FIELD_NAME[5] + " TEXT NULL," +
		    		FIELD_NAME[6] + " TEXT NULL," +
		    		FIELD_NAME[7] + " TEXT NULL," +
		    		FIELD_NAME[8] + " TEXT NULL," +
		    		FIELD_NAME[9] + " TEXT NULL," +
		    		FIELD_NAME[10] + " TEXT NULL," +
		    		FIELD_NAME[11] + " TEXT NULL," +
		    		FIELD_NAME[12] + " TEXT NULL," +
		    		FIELD_NAME[13] + " Integer NULL," +
		    		FIELD_NAME[14] + " INTEGER DEFAULT 0)" 
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
					SaleVouncher sv = (SaleVouncher) obj;
					values.put(FIELD_NAME[0], sv.getVid());
					values.put(FIELD_NAME[1], sv.getCusname());
					values.put(FIELD_NAME[2], sv.getItemid());
					values.put(FIELD_NAME[3], sv.getItemname());
					values.put(FIELD_NAME[4], sv.getQty());
					values.put(FIELD_NAME[5], sv.getPrice());
					values.put(FIELD_NAME[6], sv.getItemtotal());
					values.put(FIELD_NAME[7], sv.getVdate());
					values.put(FIELD_NAME[8], sv.getTotal());
					values.put(FIELD_NAME[9], sv.getSalePerson());
					values.put(FIELD_NAME[10], sv.getUpdatePerson());
					values.put(FIELD_NAME[11], sv.getDiscount());
					values.put(FIELD_NAME[12], sv.getUpdateDate());
					values.put(FIELD_NAME[13], sv.getMarginalPrice());
					values.put(FIELD_NAME[14], sv.getReturnableQty());

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
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12],
						FIELD_NAME[13],
						FIELD_NAME[14]
					};
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				sale_vouncher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleVouncher sv = new SaleVouncher();
				        	
				        	sv.setVid(cursor.getString(0));
				        	sv.setCusname(cursor.getString(1));
				        	sv.setItemid(cursor.getString(2));
				        	sv.setItemname(cursor.getString(3));
				        	sv.setQty(cursor.getString(4));
				        	sv.setPrice(cursor.getString(5));
				        	sv.setItemtotal(cursor.getString(6));
				        	sv.setVdate(cursor.getString(7));
				        	sv.setTotal(cursor.getString(8));
				        	sv.setSalePerson(cursor.getString(9));
				        	sv.setUpdatePerson(cursor.getString(10));
				        	sv.setDiscount(cursor.getString(11));
				        	sv.setUpdateDate(cursor.getString(12));
				        	sv.setMarginalPrice(cursor.getInt(13));
				        	sv.setReturnableQty(cursor.getInt(14));
				        		        	
				        	sale_vouncher.add(sv);
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
			return sale_vouncher;
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
						FIELD_NAME[7],
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12],
						FIELD_NAME[13],
						FIELD_NAME[14]
						};
				
				String[] VALUE = { VouncherID };
				String WHERE = FIELD_NAME[6] + "=? " ;
				String ORDER_BY = FIELD_NAME[0] + " DESC LIMIT 1";
				
				sale_vouncher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				Log.i("","Cursor size :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
						do {
							SaleVouncher sv = new SaleVouncher();

							sv.setVid(cursor.getString(0));
							sv.setCusname(cursor.getString(1));
							sv.setItemid(cursor.getString(2));
							sv.setItemname(cursor.getString(3));
							sv.setQty(cursor.getString(4));
							sv.setPrice(cursor.getString(5));
				        	sv.setItemtotal(cursor.getString(6));
				        	sv.setVdate(cursor.getString(7));
				        	sv.setTotal(cursor.getString(8));
				        	sv.setSalePerson(cursor.getString(9));
				        	sv.setUpdatePerson(cursor.getString(10));
				        	sv.setDiscount(cursor.getString(11));
				        	sv.setUpdateDate(cursor.getString(12));
				        	sv.setMarginalPrice(cursor.getInt(13));
				        	sv.setReturnableQty(cursor.getInt(14));

							sale_vouncher.add(sv);
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
			return sale_vouncher;
		}

		public List<Object> selectRecord(String spName, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected Sale Person: "+spName);
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
						FIELD_NAME[12],
						FIELD_NAME[13],
						FIELD_NAME[14]
					};
				
				String[] VALUE;
				String WHERE;
				
				if (spName.equals("All")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = spName;
					WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ? and "+FIELD_NAME[9]+" = ?";
				}
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				sale_vouncher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleVouncher sv = new SaleVouncher();
				        	
				        	sv.setVid(cursor.getString(0));
				        	sv.setCusname(cursor.getString(1));
				        	sv.setItemid(cursor.getString(2));
				        	sv.setItemname(cursor.getString(3));
				        	sv.setQty(cursor.getString(4));
				        	sv.setPrice(cursor.getString(5));
				        	sv.setItemtotal(cursor.getString(6));
				        	sv.setVdate(cursor.getString(7));
				        	sv.setTotal(cursor.getString(8));
				        	sv.setSalePerson(cursor.getString(9));
				        	sv.setUpdatePerson(cursor.getString(10));
				        	sv.setDiscount(cursor.getString(11));
				        	sv.setUpdateDate(cursor.getString(12));
				        	sv.setMarginalPrice(cursor.getInt(13));
				        	sv.setReturnableQty(cursor.getInt(14));
				        		        	
				        	sale_vouncher.add(sv);
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
			return sale_vouncher;
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
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12],
						FIELD_NAME[13],
						FIELD_NAME[14]
					};
				
				String[] VALUE;
				String WHERE;
				
				VALUE = new String[2];
				VALUE[0] = VouID;
				VALUE[1] = ItemID;
				WHERE = FIELD_NAME[0]+" = ? and "+FIELD_NAME[2]+" = ?";
				
				sale_vouncher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleVouncher sv = new SaleVouncher();
				        	
				        	sv.setVid(cursor.getString(0));
				        	sv.setCusname(cursor.getString(1));
				        	sv.setItemid(cursor.getString(2));
				        	sv.setItemname(cursor.getString(3));
				        	sv.setQty(cursor.getString(4));
				        	sv.setPrice(cursor.getString(5));
				        	sv.setItemtotal(cursor.getString(6));
				        	sv.setVdate(cursor.getString(7));
				        	sv.setTotal(cursor.getString(8));
				        	sv.setSalePerson(cursor.getString(9));
				        	sv.setUpdatePerson(cursor.getString(10));
				        	sv.setDiscount(cursor.getString(11));
				        	sv.setUpdateDate(cursor.getString(12));
				        	sv.setMarginalPrice(cursor.getInt(13));
				        	sv.setReturnableQty(cursor.getInt(14));
				        		        	
				        	sale_vouncher.add(sv);
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
			return sale_vouncher;
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
						FIELD_NAME[12],
						FIELD_NAME[13],
						FIELD_NAME[14]
					};
				
				String[] VALUE;
				String WHERE;
				
				VALUE = new String[2];
				VALUE[0] = fromDate;
				VALUE[1] = toDate;
				WHERE = FIELD_NAME[7]+" >= ? and "+FIELD_NAME[7]+" <= ?";
				
				
				String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[0]+ " ASC";
				
				sale_vouncher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleVouncher sv = new SaleVouncher();
				        	
				        	sv.setVid(cursor.getString(0));
				        	sv.setCusname(cursor.getString(1));
				        	sv.setItemid(cursor.getString(2));
				        	sv.setItemname(cursor.getString(3));
				        	sv.setQty(cursor.getString(4));
				        	sv.setPrice(cursor.getString(5));
				        	sv.setItemtotal(cursor.getString(6));
				        	sv.setVdate(cursor.getString(7));
				        	sv.setTotal(cursor.getString(8));
				        	sv.setSalePerson(cursor.getString(9));
				        	sv.setUpdatePerson(cursor.getString(10));
				        	sv.setDiscount(cursor.getString(11));
				        	sv.setUpdateDate(cursor.getString(12));
				        	sv.setMarginalPrice(cursor.getInt(13));
				        	sv.setReturnableQty(cursor.getInt(14));
				        		        	
				        	sale_vouncher.add(sv);
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
			return sale_vouncher;
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
						//FIELD_NAME[0], 
						//FIELD_NAME[1],
						FIELD_NAME[2], 
						FIELD_NAME[3],
						//FIELD_NAME[4],
						//FIELD_NAME[5],
						//FIELD_NAME[6],
						FIELD_NAME[7],
						//FIELD_NAME[8],
						//FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
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
					WHERE = FIELD_NAME[7]+" = ? and "+FIELD_NAME[2]+" = ?";
					
				//}
				
				String GROUP_BY = FIELD_NAME[7];
				String ORDER_BY = FIELD_NAME[7]+ " ASC";
				
				sale_vouncher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, GROUP_BY, null, ORDER_BY);
				
				Log.i("","Data count of Sale :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	SaleVouncher sv = new SaleVouncher();
				        	
				        	//sv.setVid(cursor.getString(0));
				        	//sv.setCusname(cursor.getString(1));
				        	sv.setItemid(cursor.getString(0));
				        	sv.setItemname(cursor.getString(1));
				        	//sv.setQty(cursor.getString(4));
				        	//sv.setPrice(cursor.getString(5));
				        	//sv.setItemtotal(cursor.getString(6));
				        	sv.setVdate(cursor.getString(2));
				        	//sv.setTotal(cursor.getString(8));
				        	//sv.setSalePerson(cursor.getString(9));
				        	sv.setUpdatePerson(cursor.getString(3));
				        	sv.setDiscount(cursor.getString(4));
				        	sv.setQtyTotal(cursor.getString(5));
				        		        	
				        	sale_vouncher.add(sv);
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
			return sale_vouncher;
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
						FIELD_NAME[8],
						FIELD_NAME[9],
						FIELD_NAME[10],
						FIELD_NAME[11],
						FIELD_NAME[12],
						FIELD_NAME[13],
						FIELD_NAME[14]
						};
				
				String[] VALUE = { VoucherID };
				String WHERE = FIELD_NAME[0] + "=? " ;
				String ORDER_BY = FIELD_NAME[0] + " ASC";
				
				sale_vouncher = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, ORDER_BY);
				
				Log.i("","Cursor size :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
						do {
							SaleVouncher sv = new SaleVouncher();

							sv.setVid(cursor.getString(0));
				        	sv.setCusname(cursor.getString(1));
				        	sv.setItemid(cursor.getString(2));
				        	sv.setItemname(cursor.getString(3));
				        	sv.setQty(cursor.getString(4));
				        	sv.setOldQty(cursor.getString(4));
				        	sv.setPrice(cursor.getString(5));
				        	sv.setItemtotal(cursor.getString(6));
				        	sv.setVdate(cursor.getString(7));
				        	sv.setTotal(cursor.getString(8));
				        	sv.setSalePerson(cursor.getString(9));
				        	sv.setUpdatePerson(cursor.getString(10));
				        	sv.setDiscount(cursor.getString(11));
				        	sv.setUpdateDate(cursor.getString(12));
				        	sv.setMarginalPrice(cursor.getInt(13));
				        	sv.setReturnableQty(cursor.getInt(14));

				        	sale_vouncher.add(sv);
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
			return sale_vouncher;
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
	
	//Select Voucher ID Group By
	public List<Object> selectVouListGroupBy() {
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
					FIELD_NAME[12],
					FIELD_NAME[13],
					FIELD_NAME[14]
				};
			
			String GROUP_BY = FIELD_NAME[0];
			String ORDER_BY = FIELD_NAME[0]+ " ASC";
			
			sale_vouncher = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, null, null, GROUP_BY, null, ORDER_BY);
			Log.i("","Data count :" + cursor.getCount());
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	SaleVouncher sv = new SaleVouncher();
			        	
			        	sv.setVid(cursor.getString(0));
			        	sv.setCusname(cursor.getString(1));
			        	sv.setItemid(cursor.getString(2));
			        	sv.setItemname(cursor.getString(3));
			        	sv.setQty(cursor.getString(4));
			        	sv.setPrice(cursor.getString(5));
			        	sv.setItemtotal(cursor.getString(6));
			        	sv.setVdate(cursor.getString(7));
			        	sv.setTotal(cursor.getString(8));
			        	sv.setSalePerson(cursor.getString(9));
			        	sv.setUpdatePerson(cursor.getString(10));
			        	sv.setDiscount(cursor.getString(11));
			        	sv.setUpdateDate(cursor.getString(12));
			        	sv.setMarginalPrice(cursor.getInt(13));
			        	sv.setReturnableQty(cursor.getInt(14));
			        		        	
			        	sale_vouncher.add(sv);
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
		return sale_vouncher;
	}
	
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
					FIELD_NAME[8],
					FIELD_NAME[9],
					FIELD_NAME[10],
					FIELD_NAME[11],
					FIELD_NAME[12],
					FIELD_NAME[13],
					FIELD_NAME[14]
				};
			
			String[] VALUE = { itemCode };
			String WHERE = FIELD_NAME[2]+" = ?";
			
			sale_vouncher = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, null);
			
			Log.i("","Data count of Sale :" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	SaleVouncher sv = new SaleVouncher();

						sv.setVid(cursor.getString(0));
			        	sv.setCusname(cursor.getString(1));
			        	sv.setItemid(cursor.getString(2));
			        	sv.setItemname(cursor.getString(3));
			        	sv.setQty(cursor.getString(4));
			        	sv.setPrice(cursor.getString(5));
			        	sv.setItemtotal(cursor.getString(6));
			        	sv.setVdate(cursor.getString(7));
			        	sv.setTotal(cursor.getString(8));
			        	sv.setSalePerson(cursor.getString(9));
			        	sv.setUpdatePerson(cursor.getString(10));
			        	sv.setDiscount(cursor.getString(11));
			        	sv.setUpdateDate(cursor.getString(12));
			        	sv.setMarginalPrice(cursor.getInt(13));
			        	sv.setReturnableQty(cursor.getInt(14));

			        	sale_vouncher.add(sv);
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
		return sale_vouncher;
	}
	
	private OnUpdate updateRecord = new OnUpdate() {

		public void updateRecord(Object obj) {
			// TODO Auto-generated method stub
			
		}

		public void updateRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update Sale Voucher:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					SaleVouncher salev = (SaleVouncher) obj;
					
					values.put(FIELD_NAME[4], salev.getQty());
					values.put(FIELD_NAME[5], salev.getPrice());
					values.put(FIELD_NAME[6], salev.getItemtotal());
					values.put(FIELD_NAME[8], salev.getTotal());
					
					String[] VALUE = {salev.getVid(), salev.getItemid()};
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
	
	//Update Sale Voucher
	public void updateRecByVouIDItemID(List<Object> objList) {
		// TODO Auto-generated method stub
		Log.i("","Update List for SaleVou:" + objList.toString());
		
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			for (Object obj : objList) {

				ContentValues values = new ContentValues();
				SaleVouncher salev = (SaleVouncher) obj;
				
				values.put(FIELD_NAME[4], salev.getQty());
				values.put(FIELD_NAME[6], salev.getItemtotal());
				values.put(FIELD_NAME[8], salev.getTotal());
				values.put(FIELD_NAME[9], salev.getSalePerson());
				values.put(FIELD_NAME[11], salev.getDiscount());
				
				String[] VALUE = {salev.getVid(), salev.getItemid()};
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
	
	//Update Sale Voucher by VouID
		public void updateByVouID(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update List for SaleVou:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					SaleVouncher salev = (SaleVouncher) obj;
					
					//values.put(FIELD_NAME[4], salev.getQty());
					//values.put(FIELD_NAME[6], salev.getItemtotal());
					values.put(FIELD_NAME[8], salev.getTotal());
					//values.put(FIELD_NAME[9], salev.getSalePerson());
				//	values.put(FIELD_NAME[11], salev.getDiscount());
					
					String[] VALUE = {salev.getVid()};
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
		
		//Update Sale Voucher
		public void updateReturnableQty(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("","Update List for SaleVou:" + objList.toString());
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					SaleVouncher salev = (SaleVouncher) obj;
					
					values.put(FIELD_NAME[14], salev.getReturnableQty());
					
					String[] VALUE = {salev.getVid(), salev.getItemid()};
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
					
				SaleVouncher itemObj = (SaleVouncher) obj;
				
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
	public List<SaleVouncher> getLastVoucherID() {
		String[] FROM = {
				FIELD_NAME[0]
				};
		String ORDER_BY = FIELD_NAME[0]+ " DESC LIMIT 1";
		List<SaleVouncher> voucherList = new ArrayList<SaleVouncher>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		
	    if (cursor.moveToFirst()) {
	        do {
	        	SaleVouncher voucher = new SaleVouncher();
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
