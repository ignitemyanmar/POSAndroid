package com.ignite.pos.database.util;
import java.io.File;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class DatabaseManager{
	protected Context mContext;
	protected SQLiteDatabase connectSQLiteDatabase = null;
	protected String  DATABASE_FILE_PATH = "/data/data/";	
	protected final String  DATABASE_NAME = "pos_db";
	
	protected OnSave save;
	protected OnUpdate update;
	protected OnSelect select;
	protected OnDelete delete;
	
	protected OnComplete complete;
	
	public DatabaseManager(Context ctx)
	{
		mContext=ctx;
		//To Create Database
		try {
			DATABASE_FILE_PATH += mContext.getApplicationContext().getPackageName()+ "/databases";
			connectSQLiteDatabase = mContext.openOrCreateDatabase(DATABASE_FILE_PATH + File.separator + DATABASE_NAME, Context.MODE_PRIVATE, null);
		} catch (Exception e) {
			Log.e("MYERROR","Cann't crate database!!!!!!!!!!");
		}
		finally
		{
			createTables();
			connectSQLiteDatabase.close();
		}
				
	}
	
	/*To Check Readable to Database*/
	protected SQLiteDatabase getReadableDatabase()
	{
	    connectSQLiteDatabase = mContext.openOrCreateDatabase(DATABASE_FILE_PATH + File.separator + DATABASE_NAME,Context.MODE_PRIVATE, null);
	    return connectSQLiteDatabase;
	}
	/*To Check Writeable to Database*/
	protected SQLiteDatabase getWritableDatabase()
	{
	    connectSQLiteDatabase = mContext.openOrCreateDatabase(DATABASE_FILE_PATH + File.separator + DATABASE_NAME,Context.MODE_PRIVATE, null);
	    return connectSQLiteDatabase;
	}
		
	protected abstract void createTables();
	
	/*Create to folder in external sd card*/
	public Boolean IfExistDatabase() {
		boolean ret = true;
		File file = new File("", DATABASE_FILE_PATH + File.separator + DATABASE_NAME);
		if (!file.exists()) {
			ret = false;
		}
		return ret;
	}
	
	/*Save Record*/
	public void save(Object obj){
		save.saveRecord(obj);
	}
	
	/*Save All Record*/
	public void save(List<Object> objList){
		save.saveRecord(objList);
	}
	
	/*Set Implemention on Save*/
	public void setOnSave(OnSave save){
		this.save = save;
	}
	
	/*Update Record*/
	public void update(List<Object> objList){
		update.updateRecord(objList);
	}
	
	/*Update Record*/
	public void update(Object obj){
		update.updateRecord(obj);
	}
	
	/*Set Implemention on Update*/
	public void setOnUpate(OnUpdate update){
		this.update = update;
	}
	
	/*Select Record*/
	public List<Object> select(){
		return select.selectRecord();
	}
	
	/*Select Record by arg0*/
	public List<Object> select(String arg0){
		return select.selectRecord(arg0);
	}
	
	public List<Object> select(String arg0 , String arg1){
		return select.selectRecord(arg0 ,arg1);
	} 
	
	public List<Object> select(String arg0 , String arg1, String arg2){
		return select.selectRecord(arg0 ,arg1, arg2);
	}
	
	public List<Object> selectbyBuyer(String arg0 , String arg1, String arg2){
		return select.selectRecordByBuyer(arg0, arg1, arg2);
	}
	
	/*Select Record by Object*/
	public List<Object> select(Object obj){
		return select.selectRecord(obj);
	}
	
	/*Set Implemention on Select*/
	public void setOnSelect(OnSelect select){
		this.select = select;
	}
	
	/*Delete All Records*/
	public void delete(){
		delete.deleteRecord();
	}
	
	/*Delete Record*/
	public boolean delete(String arg0){
		return delete.deleteRecord(arg0);
	}
	
	/*Delete Record by Object*/
	public List<Object> delete(Object obj){
		return delete.deleteRecord(obj);
	}
	
	/*Set Implemention on Delete*/
	public void setOnDelete(OnDelete delete){
		this.delete = delete;
	}
	
	/*Set Listener on Complete */
	public void setOnComplete(OnComplete complete){
		this.complete = complete;
	}
	
}
