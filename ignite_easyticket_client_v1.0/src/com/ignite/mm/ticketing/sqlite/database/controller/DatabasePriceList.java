package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.Operators;
import com.ignite.mm.ticketing.sqlite.database.model.PriceList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabasePriceList extends DatabaseManager {

	public DatabasePriceList(Context context) {
		super(context);
	}

	public void add(PriceList pl) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[7][0], pl.getID());
		values.put(FIELD_NAME[7][1], pl.getPrice());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[7], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All BusDestination
		public List<PriceList> getAllPriceList() {
			String[] FROM = {
					FIELD_NAME[7][0], 
					FIELD_NAME[7][1]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<PriceList> list=new ArrayList<PriceList>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[7], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	PriceList bd=new PriceList();
		        	bd.setID(cursor.getString(0));
		        	bd.setPrice(cursor.getString(1));
		        			        	
		        	list.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<PriceList> getPriceListID(String ID) {
			String[] FROM = {
					FIELD_NAME[7][0], 
					FIELD_NAME[7][1]};
			String ORDER_BY = FIELD_NAME[7][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[7][0]+ "=?";

			List<PriceList> feed=new ArrayList<PriceList>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[7], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	PriceList bd=new PriceList();
		        	bd.setID(cursor.getString(0));
		        	bd.setPrice(cursor.getString(1));
		        			        	
		        	feed.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single feed
		public void deletePriceList() {

			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME[7],null,null);
			db.close();

		}
		
		public boolean hasData() {
	    	boolean has = false;
	    	SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[7], null, null, null, null, null, null);
			if(cursor.getCount() > 0){
				has = true;
			}
			db.close();
			return has;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<PriceList> add(ArrayList<PriceList> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
