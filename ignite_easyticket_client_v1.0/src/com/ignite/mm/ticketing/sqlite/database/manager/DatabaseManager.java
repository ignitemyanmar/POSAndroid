package com.ignite.mm.ticketing.sqlite.database.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager extends Activity {
	private String DATABASE_FILE_PATH = "/data/data/";
	private final String DATABASE_NAME = "easy_ticket_db";

	protected final String[] TABLE_NAME = {
			/*0*/			"tbl_MenuList",
			/*1*/			"tbl_BusDestination",
			/*2*/			"tbl_TravelOperator",
			/*3*/			"tbl_DeperatureTime",
			/*4*/			"tbl_MovieList",
			/*5*/			"tbl_MovieTime",
			/*6*/			"tbl_ShowList",
			/*7*/			"tbl_Show_Price",
							};
	protected final String[][] FIELD_NAME = {
		  /*0*//*2 fields*/{"id","ticket_name"},
		  /*1*//*2 fields*/{"id","des_name"},
		  /*2*//*4 fields*/{"id","name","address","phone"},
		  /*3*//*2 fields*/{"id","dep_time"},
		  /*4*//*2 fields*/{"id","mov_name"}, 
		  /*5*//*2 fields*/{"id","mov_time"},
		  /*6*//*2 fields*/{"id","show_name"},
		  /*7*//*2 fields*/{"id","price"}, 
	};
	
	private SQLiteDatabase connectSQLiteDatabase = null;

	private Context mContext;

	public DatabaseManager(Context ctx) {
		mContext = ctx;
		// To Create Database
		try {
			DATABASE_FILE_PATH += mContext.getApplicationContext()
					.getPackageName() + "/database";
			connectSQLiteDatabase = mContext.openOrCreateDatabase(
					DATABASE_FILE_PATH + File.separator + DATABASE_NAME,
					Context.MODE_PRIVATE, null);
		} catch (Exception e) {
			Log.e("MYERROR", "Cann't create database!!!!!!!!!!");
		} finally {
			createTables();
			connectSQLiteDatabase.close();
		}

	}

	/* To Check Readable to Database */
	protected SQLiteDatabase getReadableDatabase() {
		connectSQLiteDatabase = mContext.openOrCreateDatabase(
				DATABASE_FILE_PATH + File.separator + DATABASE_NAME,
				Context.MODE_PRIVATE, null);
		return connectSQLiteDatabase;
	}

	/* To Check Writeable to Database */
	protected SQLiteDatabase getWritableDatabase() {
		connectSQLiteDatabase = mContext.openOrCreateDatabase(
				DATABASE_FILE_PATH + File.separator + DATABASE_NAME,
				Context.MODE_PRIVATE, null);
		return connectSQLiteDatabase;
	}

	/* Create Table */
	private void createTables()
	{
		/*For create Menu*/
	    connectSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS  " + TABLE_NAME[0] + " (" +
	    		FIELD_NAME[0][0] + " TEXT NULL," + 
	    		FIELD_NAME[0][1] + " TEXT NULL)" 
	    		);
	    
	  //For create Bus
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[1] + " (" +
	    		//FIELD_NAME[1][0] + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," + 
	    		FIELD_NAME[1][0] + " TEXT PRIMARY KEY," +
	    		FIELD_NAME[1][1] + " TEXT NULL)" 
	    		);
	    
	    /*For create Agent*/
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[2] + " (" +
	    		FIELD_NAME[2][0] + " TEXT PRIMARY KEY," + 
	    		FIELD_NAME[2][1] + " TEXT NULL," +
	    		FIELD_NAME[2][2] + " TEXT NULL," +
	    		FIELD_NAME[2][3] + " TEXT NULL)" 
	    	
	    		);
	    
	    /*For create DeperatureTime*/
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[3] + " (" +
	    		FIELD_NAME[3][0] + " TEXT PRIMARY KEY," + 
	    		FIELD_NAME[3][1] + " TEXT NULL)" 
	    		
	    		);
	    
	    //For create MovieList
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[4] + " (" +
	    		FIELD_NAME[4][0] + " TEXT PRIMARY KEY," + 
	    		FIELD_NAME[4][1] + " TEXT NULL)" 
	    		);
	    
	    //For create MovieTime
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[5] + " (" +
	    		FIELD_NAME[5][0] + " TEXT PRIMARY KEY," + 
	    		FIELD_NAME[5][1] + " TEXT NULL)" 
	    		);
	    
	    //For create ShowList
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[6] + " (" +
	    		FIELD_NAME[6][0] + " TEXT PRIMARY KEY," + 
	    		FIELD_NAME[6][1] + " TEXT  NULL)" 
	    		);
	        
	    //For create ShowPrice
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[7] + " (" +
	    		FIELD_NAME[7][0] + " TEXT PRIMARY KEY," + 
	    		FIELD_NAME[7][1] + " TEXT  NULL)" 
	    		//FIELD_NAME[7][3] + " TEXT  NULL)" 
	    		);
	    	    
	    /*For create videoList
	    connectSQLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME[8] + " (" +
	    		FIELD_NAME[8][0] + " TEXT PRIMARY KEY," + 
	    		FIELD_NAME[8][1] + " TEXT  NULL," +
	    		FIELD_NAME[8][2] + " TEXT  NULL," +
	    		FIELD_NAME[8][3] + " TEXT  NULL," +
	    		FIELD_NAME[8][4] + " TEXT NULL)"
	    		);*/
	}
	public static boolean fileWrite(Context ctx, String dataJson, String fileName){
		File myFile = new File("/data/data/"+ctx.getApplicationContext().getPackageName()+ File.separator + fileName +".json");
		try {
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(dataJson);
			myOutWriter.close();
			fOut.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static String fileRead(Context ctx,String fileName){
		try {
			File myFile = new File("/data/data/"+ctx.getApplicationContext().getPackageName()+ File.separator + fileName +".json");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			myReader.close();
			return aBuffer;
		} catch (IOException e) {
			return null;
		}
	}
	/* Create to folder in external sd card */
	public Boolean IfExistDatabase() {
		boolean ret = true;
		File file = new File("", DATABASE_FILE_PATH + File.separator
				+ DATABASE_NAME);   
		if (!file.exists()) {
			ret = false;
		}
		return ret;
	}
}
