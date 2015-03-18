package com.ignite.mm.ticketing.sqlite.database.controller;

import java.util.ArrayList;
import java.util.List;

import com.ignite.mm.ticketing.sqlite.database.manager.DatabaseManager;
import com.ignite.mm.ticketing.sqlite.database.model.Menu;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseMenu extends DatabaseManager {

	public DatabaseMenu(Context context) {
		super(context);
	}

	public void add(Menu menuList) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIELD_NAME[0][0], menuList.getTicketTypeId());
		values.put(FIELD_NAME[0][1], menuList.getTicketTypeName());	
				
		// Inserting Row
		db.insertOrThrow(TABLE_NAME[0], null, values);
		db.close(); // Closing database connection

	}
	
		// Getting All Menus
		public List<Menu> getAllMenuList() {
			String[] FROM = {
					FIELD_NAME[0][0], 
					FIELD_NAME[0][1]};
			//String ORDER_BY = FIELD_NAME[0][0]+ "DESC";

			List<Menu> list=new ArrayList<Menu>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[0], FROM, null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		        do {
		        	Menu ml=new Menu();
		        	ml.setTicketTypeId(cursor.getString(0));
		        	ml.setTicketTypeName(cursor.getString(1));
		        			        	
		        	list.add(ml);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return list;
		}
				
		public List<Menu> getMenuListID(String ID) {
			String[] FROM = {
					FIELD_NAME[0][0], 
					FIELD_NAME[0][1]};
			String ORDER_BY = FIELD_NAME[0][0]+ " DESC";
			String[] VALUE = new String[]{ID};
			String WHERE = FIELD_NAME[0][0]+ "=?";

			List<Menu> feed=new ArrayList<Menu>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[0], FROM, WHERE, VALUE, null, null, ORDER_BY);
		    if (cursor.moveToFirst()) {
		        do {
		        	Menu menu=new Menu();
		        	menu.setTicketTypeId(cursor.getString(0));
		        	menu.setTicketTypeName(cursor.getString(1));
		        			        	
		        	feed.add(menu);
		        } while (cursor.moveToNext());
		    }
		    db.close();
		    return feed;
		}
		
		
		
		// Deleting single feed
		public void deleteMenuList() {

			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME[0],null,null);
			db.close();

		}
		
		public boolean hasData() {
	    	boolean has = false;
	    	SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME[0], null, null, null, null, null, null);
			if(cursor.getCount() > 0){
				has = true;
			}
			db.close();
			return has;
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<Menu> add(ArrayList<Menu> datalist) {
		// TODO Auto-generated method stub
		return null;
	}

}
