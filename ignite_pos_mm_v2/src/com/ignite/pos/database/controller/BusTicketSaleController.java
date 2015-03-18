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
import com.ignite.pos.model.BusTicketSale;

public class BusTicketSaleController extends DatabaseManager{

	private BusTicketSale busTicketSale;
	private List<Object> sale_list;
	
	private static final String TABLE_NAME = "tbl_bus_ticket_sale";
	private static final String[] FIELD_NAME = {"id","barcodeNo","customerName","operatorName","trip","date","time","busClass","seatNo","seatCount","seatPrice","confirmDate","buyerName","buyerPhone","buyerNRC"};
	
	public BusTicketSaleController(Context ctx) {
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
		    		FIELD_NAME[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
		    		FIELD_NAME[1] + " TEXT NULL," + 
		    		FIELD_NAME[2] + " TEXT NULL," +
		    		FIELD_NAME[3] + " TEXT NULL," +
		    		FIELD_NAME[4] + " TEXT NULL," +
		    		FIELD_NAME[5] + " TEXT NULL," +
		    		FIELD_NAME[6] + " TEXT NULL," +
		    		FIELD_NAME[7] + " TEXT NULL," +
		    		FIELD_NAME[8] + " TEXT NULL," +
		    		FIELD_NAME[9] + " INTEGER DEFAULT 0," +
		    		FIELD_NAME[10] + " INTEGER DEFAULT 0," +
		    		FIELD_NAME[11] + " TEXT NULL," +
		    		FIELD_NAME[12] + " TEXT NULL," +
		    		FIELD_NAME[13] + " TEXT NULL," +
		    		FIELD_NAME[14] + " TEXT NULL)"
		       		);
	}
	
	private OnSave saveRecord = new OnSave() {

		public void saveRecord(Object obj) {

		}

		public void saveRecord(List<Object> objList) {
			// TODO Auto-generated method stub
			Log.i("", "Bus Ticket Objlist to save: "+objList);
			
			SQLiteDatabase db = getWritableDatabase();
			db.beginTransaction();
			
			try {
				for (Object obj : objList) {

					ContentValues values = new ContentValues();
					BusTicketSale sv = (BusTicketSale) obj;
					//values.put(FIELD_NAME[0], sv.getId());
					values.put(FIELD_NAME[1], sv.getBarcodeNo());
					values.put(FIELD_NAME[2], sv.getCustomerName());
					values.put(FIELD_NAME[3], sv.getOperatorName());
					values.put(FIELD_NAME[4], sv.getTrip());
					values.put(FIELD_NAME[5], sv.getDate());
					values.put(FIELD_NAME[6], sv.getTime());
					values.put(FIELD_NAME[7], sv.getBusClass());
					values.put(FIELD_NAME[8], sv.getSeatNo());
					values.put(FIELD_NAME[9], sv.getSeatCount());
					values.put(FIELD_NAME[10], sv.getSeatPrice());
					values.put(FIELD_NAME[11], sv.getConfirmDate());
					values.put(FIELD_NAME[12], sv.getBuyerName());
					values.put(FIELD_NAME[13], sv.getBuyerPhone());
					values.put(FIELD_NAME[14], sv.getBuyerNRC());

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
				
				String ORDER_BY = FIELD_NAME[11]+ " ASC";
				
				sale_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	BusTicketSale sv = new BusTicketSale();
				        	
				        	sv.setId(cursor.getInt(0));
				        	sv.setBarcodeNo(cursor.getString(1));
				        	sv.setCustomerName(cursor.getString(2));
				        	sv.setOperatorName(cursor.getString(3));
				        	sv.setTrip(cursor.getString(4));
				        	sv.setDate(cursor.getString(5));
				        	sv.setTime(cursor.getString(6));
				        	sv.setBusClass(cursor.getString(7));
				        	sv.setSeatNo(cursor.getString(8));
				        	sv.setSeatCount(cursor.getInt(9));
				        	sv.setSeatPrice(cursor.getInt(10));
				        	sv.setConfirmDate(cursor.getString(11));
				        	sv.setBuyerName(cursor.getString(12));
				        	sv.setBuyerPhone(cursor.getString(13));
				        	sv.setBuyerNRC(cursor.getString(14));
				        		        	
				        	sale_list.add(sv);
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
			return sale_list;
		}

		public List<Object> selectRecord(String barcode) {
			// TODO Auto-generated method stub
			Log.i("","Bar Code No : " + barcode);
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
				
				String[] VALUE = { barcode };
				String WHERE = FIELD_NAME[1] + "=? " ;
				//String ORDER_BY = FIELD_NAME[1] + " DESC";
				
				sale_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null,null, null);
				Log.i("","Cursor size :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
						do {
							BusTicketSale sv = new BusTicketSale();
				        	
				        	sv.setId(cursor.getInt(0));
				        	sv.setBarcodeNo(cursor.getString(1));
				        	sv.setCustomerName(cursor.getString(2));
				        	sv.setOperatorName(cursor.getString(3));
				        	sv.setTrip(cursor.getString(4));
				        	sv.setDate(cursor.getString(5));
				        	sv.setTime(cursor.getString(6));
				        	sv.setBusClass(cursor.getString(7));
				        	sv.setSeatNo(cursor.getString(8));
				        	sv.setSeatCount(cursor.getInt(9));
				        	sv.setSeatPrice(cursor.getInt(10));
				        	sv.setConfirmDate(cursor.getString(11));
				        	sv.setBuyerName(cursor.getString(12));
				        	sv.setBuyerPhone(cursor.getString(13));
				        	sv.setBuyerNRC(cursor.getString(14));
				        		        	
				        	sale_list.add(sv);
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
			return sale_list;
		}

		public List<Object> selectRecord(String operatorName, String fromDate, String toDate) {
			// TODO Auto-generated method stub
			
			Log.i("", "Selected operatorName: "+operatorName);
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
				
				if (operatorName.equals("All")) {
					VALUE = new String[2];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					WHERE = FIELD_NAME[11]+" >= ? and "+FIELD_NAME[11]+" <= ?";
				}else{
					VALUE = new String[3];
					VALUE[0] = fromDate;
					VALUE[1] = toDate;
					VALUE[2] = operatorName;
					WHERE = FIELD_NAME[11]+" >= ? and "+FIELD_NAME[11]+" <= ? and "+FIELD_NAME[3]+" = ? COLLATE NOCASE";
				}
				
				//String GROUP_BY = FIELD_NAME[0];
				String ORDER_BY = FIELD_NAME[11]+ " ASC";
				
				sale_list = new ArrayList<Object>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, VALUE, null, null, ORDER_BY);
				
				Log.i("","Data count :" + cursor.getCount());
				try {
					if (cursor.moveToFirst()) {
				        do {
				        	BusTicketSale sv = new BusTicketSale();
				        	
				        	sv.setId(cursor.getInt(0));
				        	sv.setBarcodeNo(cursor.getString(1));
				        	sv.setCustomerName(cursor.getString(2));
				        	sv.setOperatorName(cursor.getString(3));
				        	sv.setTrip(cursor.getString(4));
				        	sv.setDate(cursor.getString(5));
				        	sv.setTime(cursor.getString(6));
				        	sv.setBusClass(cursor.getString(7));
				        	sv.setSeatNo(cursor.getString(8));
				        	sv.setSeatCount(cursor.getInt(9));
				        	sv.setSeatPrice(cursor.getInt(10));
				        	sv.setConfirmDate(cursor.getString(11));
				        	sv.setBuyerName(cursor.getString(12));
				        	sv.setBuyerPhone(cursor.getString(13));
				        	sv.setBuyerNRC(cursor.getString(14));
				        		        	
				        	sale_list.add(sv);
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
			return sale_list;
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

		public List<Object> selectRecord(String arg0, String arg1) {
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

	};
	
	//Select Operator Names - Group by
	public List<Object> selectOperators() {
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
					FIELD_NAME[11]
					
				};
			
			String GROUP_BY = FIELD_NAME[3];
			String ORDER_BY = FIELD_NAME[3]+ " ASC";
			
			sale_list = new ArrayList<Object>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, FROM, null, null, GROUP_BY, null, ORDER_BY);
			
			Log.i("","Data count :" + cursor.getCount());
			
			try {
				if (cursor.moveToFirst()) {
			        do {
			        	BusTicketSale sv = new BusTicketSale();
			        	
			        	sv.setId(cursor.getInt(0));
			        	sv.setBarcodeNo(cursor.getString(1));
			        	sv.setCustomerName(cursor.getString(2));
			        	sv.setOperatorName(cursor.getString(3));
			        	sv.setTrip(cursor.getString(4));
			        	sv.setDate(cursor.getString(5));
			        	sv.setTime(cursor.getString(6));
			        	sv.setBusClass(cursor.getString(7));
			        	sv.setSeatNo(cursor.getString(8));
			        	sv.setSeatCount(cursor.getInt(9));
			        	sv.setSeatPrice(cursor.getInt(10));
			        	sv.setConfirmDate(cursor.getString(11));
			        		        	
			        	sale_list.add(sv);
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
		return sale_list;
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

					/*ContentValues values = new ContentValues();
					BusTicketSale salev = (BusTicketSale) obj;
					
					values.put(FIELD_NAME[4], salev.getQty());
					values.put(FIELD_NAME[5], salev.getPrice());
					values.put(FIELD_NAME[6], salev.getItemtotal());
					values.put(FIELD_NAME[8], salev.getTotal());
					values.put(FIELD_NAME[15], salev.getUpdated());
					
					String[] VALUE = {salev.getVid(), salev.getItemid()};*/
				//	String WHERE = FIELD_NAME[0] + " =? and "+FIELD_NAME[2]+" = ?";
					
					//db.update(TABLE_NAME, values, WHERE,VALUE);
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

		
	
	private OnDelete deleteRecord = new OnDelete() {
		
		public void deleteRecord() {
			// TODO Auto-generated method stub
			//SQLiteDatabase db = getWritableDatabase();
			//db.delete(TABLE_NAME, null, null);
			//db.close();
		}

		/*public void deleteRecord(String arg0) {
			// TODO Auto-generated method stub
			
		}*/

		public boolean deleteRecord(List<Object> objList) {
			
			Log.i("", "Item List to delete"+objList.toString());
			
/*			SQLiteDatabase db = getWritableDatabase();
			for (Object obj : objList) {
					
				BusTicketSale itemObj = (BusTicketSale) obj;
				
				String WHERE = FIELD_NAME[0]+" = ? and "+FIELD_NAME[2]+" = ?";
				String[] VALUE = {itemObj.getVid(), itemObj.getItemid()};
				//db.delete(TABLE_NAME, FIELD_NAME[0]+" = ?", new String[]{String.valueOf(itemObj.getItemid())});
				db.delete(TABLE_NAME, WHERE, VALUE);
			}
			db.close();*/
			return true;
			
		}

		public List<Object> deleteRecord(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean deleteRecord(String voucherID) {
			// TODO Auto-generated method stub
/*			Log.i("", "Voucher ID to delete: "+voucherID);
			
			SQLiteDatabase db = getWritableDatabase();
			
			db.delete(TABLE_NAME, FIELD_NAME[0]+" = ? ", new String[]{voucherID});
			db.close();*/
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
