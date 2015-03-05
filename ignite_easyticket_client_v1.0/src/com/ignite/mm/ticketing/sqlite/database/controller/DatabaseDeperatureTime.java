package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.Deperature;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseDeperatureTime extends DatabaseManager {

	public DatabaseDeperatureTime(Context context) {
		super(context);
	}

	public void add(Deperature deperature) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[3][0], deperature.getDepID());
		values.put(FIELD_NAME[3][1], deperature.getDepTime());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[3], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All BusDestination
		public List<Deperature> getAllDeperature() {
			String[] FROM = {
					FIELD_NAME[3][0], 
					FIELD_NAME[3][1]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<Deperature> list=new ArrayList<Deperature>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[3], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	Deperature bd=new Deperature();
		        	bd.setDepID(cursor.getString(0));
		        	bd.setDepTime(cursor.getString(1));
		        			        	
		        	list.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<Deperature> getDeperatureID(String ID) {
			String[] FROM = {
					FIELD_NAME[3][0], 
					FIELD_NAME[3][1]};
			String ORDER_BY = FIELD_NAME[3][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[3][0]+ "=?";

			List<Deperature> feed=new ArrayList<Deperature>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[3], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	Deperature bd=new Deperature();
		        	bd.setDepID(cursor.getString(0));
		        	bd.setDepTime(cursor.getString(1));
		        			        	
		        	feed.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single feed
		public void deleteDeperature() {

			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME[3],null,null);
			db.close();

		}
		
		public boolean hasData() {
	    	boolean has = false;
	    	SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[3], null, null, null, null, null, null);
			if(cursor.getCount() > 0){
				has = true;
			}
			db.close();
			return has;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<Deperature> add(ArrayList<Deperature> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
