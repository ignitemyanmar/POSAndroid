package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.ShowList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseShow extends DatabaseManager {

	public DatabaseShow(Context context) {
		super(context);
	}

	public void add(ShowList showList) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[6][0], showList.getShowID());
		values.put(FIELD_NAME[6][1], showList.getShowName());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[6], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All MovieList
		public List<ShowList> getAllShow() {
			String[] FROM = {
					FIELD_NAME[6][0], 
					FIELD_NAME[6][1]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<ShowList> list=new ArrayList<ShowList>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[6], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	ShowList bd=new ShowList();
		        	bd.setShowID(cursor.getString(0));
		        	bd.setShowName(cursor.getString(1));
		        			        	
		        	list.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<ShowList> getShowListID(String ID) {
			String[] FROM = {
					FIELD_NAME[6][0], 
					FIELD_NAME[6][1]};
			String ORDER_BY = FIELD_NAME[6][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[6][0]+ "=?";

			List<ShowList> feed=new ArrayList<ShowList>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[6], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	ShowList bd=new ShowList();
		        	bd.setShowID(cursor.getString(0));
		        	bd.setShowName(cursor.getString(1));
		        			        	
		        	feed.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single Movie
		public void deleteMovieList() {

			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME[6],null,null);
			db.close();

		}
		
		public boolean hasData() {
	    	boolean has = false;
	    	SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[6], null, null, null, null, null, null);
			if(cursor.getCount() > 0){
				has = true;
			}
			db.close();
			return has;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<ShowList> add(ArrayList<ShowList> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
