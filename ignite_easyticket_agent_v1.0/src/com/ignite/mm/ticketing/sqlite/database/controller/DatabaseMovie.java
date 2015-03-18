package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.MovieList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseMovie extends DatabaseManager {

	public DatabaseMovie(Context context) {
		super(context);
	}

	public void add(MovieList movList) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[4][0], movList.getID());
		values.put(FIELD_NAME[4][1], movList.getMovietitle());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[4], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All MovieList
		public List<MovieList> getAllMovieList() {
			String[] FROM = {
					FIELD_NAME[4][0], 
					FIELD_NAME[4][1]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<MovieList> list=new ArrayList<MovieList>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[4], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	MovieList bd=new MovieList();
		        	bd.setID(cursor.getString(0));
		        	bd.setMovietitle(cursor.getString(1));
		        			        	
		        	list.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<MovieList> getMovieListID(String ID) {
			String[] FROM = {
					FIELD_NAME[4][0], 
					FIELD_NAME[4][1]};
			String ORDER_BY = FIELD_NAME[4][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[4][0]+ "=?";

			List<MovieList> feed=new ArrayList<MovieList>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[4], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	MovieList bd=new MovieList();
		        	bd.setID(cursor.getString(0));
		        	bd.setMovietitle(cursor.getString(1));
		        			        	
		        	feed.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single Movie
		public void deleteMovieList() {

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

	public List<MovieList> add(ArrayList<MovieList> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
