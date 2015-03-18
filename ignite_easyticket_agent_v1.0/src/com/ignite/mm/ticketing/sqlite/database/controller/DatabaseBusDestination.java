package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.BusDestination;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseBusDestination extends DatabaseManager {

	public DatabaseBusDestination(Context context) {
		super(context);
	}

	public void add(BusDestination busDes) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[1][0], busDes.getDesID());
		values.put(FIELD_NAME[1][1], busDes.getDesName());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[1], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All BusDestination
		public List<BusDestination> getAllBusDestination() {
			String[] FROM = {
					FIELD_NAME[1][0], 
					FIELD_NAME[1][1]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<BusDestination> list=new ArrayList<BusDestination>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[1], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	BusDestination bd=new BusDestination();
		        	bd.setDesID(cursor.getString(0));
		        	bd.setDesName(cursor.getString(1));
		        			        	
		        	list.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<BusDestination> getBusDesID(String ID) {
			String[] FROM = {
					FIELD_NAME[1][0], 
					FIELD_NAME[1][1]};
			String ORDER_BY = FIELD_NAME[1][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[1][0]+ "=?";

			List<BusDestination> feed=new ArrayList<BusDestination>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[1], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	BusDestination bd=new BusDestination();
		        	bd.setDesID(cursor.getString(0));
		        	bd.setDesName(cursor.getString(1));
		        			        	
		        	feed.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single feed
		public void deleteBusDes() {

			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME[1],null,null);
			db.close();

		}
		
		public boolean hasData() {
	    	boolean has = false;
	    	SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[1], null, null, null, null, null, null);
			if(cursor.getCount() > 0){
				has = true;
			}
			db.close();
			return has;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<BusDestination> add(ArrayList<BusDestination> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
