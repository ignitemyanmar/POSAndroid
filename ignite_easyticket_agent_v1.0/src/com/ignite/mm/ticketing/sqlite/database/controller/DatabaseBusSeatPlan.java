package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.BusSeat;

public class DatabaseBusSeatPlan extends DatabaseManager{

	public DatabaseBusSeatPlan(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	public void add(BusSeat busseat) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		//values.put(FIELD_NAME[4][0], busseat.getSeat());
	//	values.put(FIELD_NAME[4][1], busseat.getSeatNo());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[4], null, values);
		db.close(); // Closing database connection

	}
	
	// Getting All BusSeatPlan
			public List<BusSeat> getAllBusSeat() {
				String[] FROM = {
						FIELD_NAME[4][0], 
						FIELD_NAME[4][1]};
				//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

				List<BusSeat> list=new ArrayList<BusSeat>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME[4], FROM, null, null, null, null, null);
			    if (cursor.moveToFirst()) {
			        do {
			        	//BusSeat bs=new BusSeat();
			        	//bs.setSeat(cursor.getString(0));
			        	//bs.setSeatNo(cursor.getString(1));
			        			        	
			        	//list.add(bs);
			        } while (cursor.moveToNext());
			    }
			    db.close();
			    return list;
			}
					
			public List<BusSeat> getBusSeatPlanID(String ID) {
				String[] FROM = {
						FIELD_NAME[4][0], 
						FIELD_NAME[4][1]};
				String ORDER_BY = FIELD_NAME[4][0]+ " DESC";
				String[] VALUE = new String[]{ID};
				String WHERE = FIELD_NAME[4][0]+ "=?";

				List<BusSeat> feed=new ArrayList<BusSeat>();
				SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME[4], FROM, WHERE, VALUE, null, null, ORDER_BY);
			    if (cursor.moveToFirst()) {
			        do {
			        	//BusSeat bs=new BusSeat();
			        	//bs.setSeat(cursor.getString(0));
			        //	bs.setSeatNo(cursor.getString(1));
			        			        	
			        	//feed.add(bs);
			        } while (cursor.moveToNext());
			    }
			    db.close();
			    return feed;
			}
			
			
			
			// Deleting single feed
			public void deleteBusSeatPlan() {

				SQLiteDatabase db = this.getWritableDatabase();
				db.delete(TABLE_NAME[4],null,null);
				db.close();

			}
			
			public boolean hasData() {
		    	boolean has = false;
		    	SQLiteDatabase db = getReadableDatabase();
				Cursor cursor = db.query(TABLE_NAME[4], null, null, null, null, null, null);
				if(cursor.getCount() > 0){
					has = true;
				}
				db.close();
				return has;
			}

		public static void main(String[] args) {
			// TODO Auto-generated method stub

		}

		public List<BusSeat> add(ArrayList<BusSeat> datalist) {
			// TODO Auto-generated method stub
			return null;
		}

}
