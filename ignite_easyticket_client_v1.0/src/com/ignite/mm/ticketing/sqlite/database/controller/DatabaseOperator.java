package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.Operators;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseOperator extends DatabaseManager {

	public DatabaseOperator(Context context) {
		super(context);
	}

	public void add(Operators agent) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[2][0], agent.getOperators().get(0).getId());
		values.put(FIELD_NAME[2][1], agent.getOperators().get(0).getName());	
		values.put(FIELD_NAME[2][2], agent.getOperators().get(0).getAddress());
		values.put(FIELD_NAME[2][3], agent.getOperators().get(0).getPhone());
		
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[2], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All BusDestination
		public List<Operators> getAllAgentsList() {
			String[] FROM = {
					FIELD_NAME[2][0], 
					FIELD_NAME[2][1],
					FIELD_NAME[2][2],
					FIELD_NAME[2][3]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<Operators> list=new ArrayList<Operators>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[2], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	Operators bd=new Operators();
		        	/*bd.;
		        	bd.setName(cursor.getString(1));
		        	bd.setAddress(cursor.getString(2));
		        	bd.setPhone(cursor.getString(3));*/
		        	list.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<Operators> getAgentsListID(String ID) {
			String[] FROM = {
					FIELD_NAME[2][0], 
					FIELD_NAME[2][1],
					FIELD_NAME[2][2],
					FIELD_NAME[2][3]};
			String ORDER_BY = FIELD_NAME[2][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[2][0]+ "=?";

			List<Operators> feed=new ArrayList<Operators>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[2], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	Operators bd=new Operators();
		        	/*bd.setOperators(cursor.getString(0));
		        	bd.setName(cursor.getString(1));
		        	bd.setAddress(cursor.getString(2));
		        	bd.setPhone(cursor.getString(3));*/
		        	feed.add(bd);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single feed
		public void deleteAgentsList() {

			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME[2],null,null);
			db.close();

		}
		
		public boolean hasData() {
	    	boolean has = false;
	    	SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[2], null, null, null, null, null, null);
			if(cursor.getCount() > 0){
				has = true;
			}
			db.close();
			return has;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<Operators> add(ArrayList<Operators> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
