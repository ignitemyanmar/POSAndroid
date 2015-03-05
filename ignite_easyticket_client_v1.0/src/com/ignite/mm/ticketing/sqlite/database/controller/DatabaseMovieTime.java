package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.MovieTime;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseMovieTime extends DatabaseManager {

	public DatabaseMovieTime(Context context) {
		super(context);
	}

	public void add(MovieTime movTime) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[5][0], movTime.getMovieId());
		values.put(FIELD_NAME[5][1], movTime.getMovieTime());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[5], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All MovieList
		public List<MovieTime> getAllMovieTime() {
			String[] FROM = {
					FIELD_NAME[5][0], 
					FIELD_NAME[5][1]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<MovieTime> list=new ArrayList<MovieTime>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[5], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	MovieTime bd=new MovieTime();
		        	bd.setMovieId(cursor.getString(0));
		        	bd.setMovieTime(cursor.getString(1));
		        			        	
		        	list.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<MovieTime> getMovieTimeID(String ID) {
			String[] FROM = {
					FIELD_NAME[5][0], 
					FIELD_NAME[5][1]};
			String ORDER_BY = FIELD_NAME[5][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[5][0]+ "=?";

			List<MovieTime> feed=new ArrayList<MovieTime>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[5], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	MovieTime bd=new MovieTime();
		        	bd.setMovieId(cursor.getString(0));
		        	bd.setMovieTime(cursor.getString(1));
		        			        	
		        	feed.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single MovieTime
		public void deleteMovieTime() {

			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME[5],null,null);
			db.close();

		}
		
		public boolean hasData() {
	    	boolean has = false;
	    	SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[5], null, null, null, null, null, null);
			if(cursor.getCount() > 0){
				has = true;
			}
			db.close();
			return has;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<MovieTime> add(ArrayList<MovieTime> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
