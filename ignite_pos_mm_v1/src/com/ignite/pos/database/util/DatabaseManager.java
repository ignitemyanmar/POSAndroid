package com.ignite.pos.database.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public abstract class DatabaseManager{
	protected Context mContext;
	protected SQLiteDatabase connectSQLiteDatabase = null;
	protected String  DATABASE_FILE_PATH = Environment.getExternalStorageDirectory()+"/IgnitePOS/.database";	
	protected final String  DATABASE_NAME = "pos_db.sqlite3";
	
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
			//DATABASE_FILE_PATH += mContext.getApplicationContext().getPackageName()+ "/databases";
			
			IfExistDatabase();
				connectSQLiteDatabase = mContext.openOrCreateDatabase(DATABASE_FILE_PATH + File.separator + DATABASE_NAME, Context.MODE_PRIVATE, null);
//			else
//			{
//				this.getReadableDatabase();
//		        try {
//		            copyDataBase();
//		        } catch (IOException mIOException) {
//		            mIOException.printStackTrace();
//		            throw new Error("Error copying database");
//		        } finally {
//		        	SQLiteDatabase.releaseMemory();
//		        	connectSQLiteDatabase.close();
//		        }
//			}
				
				
//			//Copy Database 
//				if (IfExistDatabase()) {
//					connectSQLiteDatabase = mContext.openOrCreateDatabase(DATABASE_FILE_PATH + File.separator + DATABASE_NAME, Context.MODE_PRIVATE, null);
//				}
//				else
//				{
//					this.getReadableDatabase();
//		        	try {
//		           	 copyDataBase();
//		       		 } catch (IOException mIOException) {
//		            mIOException.printStackTrace();
//		            throw new Error("Error copying database");
//		        	} finally {
//		        	SQLiteDatabase.releaseMemory();
//		        	connectSQLiteDatabase.close();
//		        	}
//				}
				
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
			IfExistFileDir(DATABASE_FILE_PATH);
			ret = false;
		}
		return ret;
	}
	
	public Boolean IfExistFileDir(String Dir){
		File fileDir = new File(Dir);
		if(!fileDir.exists()){
			try {
				fileDir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return false;
		}
		return true;
	}
	
	 /**
	  * This method will copy database from /assets directory to application
	  * package /databases directory
	  **/
	 public void copyDataBase() throws IOException {
	     try {
	    	 Log.i("Copy Database","Copy Database is begin!.");
	         InputStream mInputStream = mContext.getAssets().open(DATABASE_NAME);
	         String outFileName = DATABASE_FILE_PATH + File.separator + DATABASE_NAME;
	         OutputStream mOutputStream = new FileOutputStream(outFileName);
	         byte[] buffer = new byte[1024];
	         int length;
	         while ((length = mInputStream.read(buffer)) > 0) {
	             mOutputStream.write(buffer, 0, length);
	         }
	         mOutputStream.flush();
	         mOutputStream.close();
	         mInputStream.close();
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
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
	
	/*Update Stock Qty*/
	public void updateStockQty(List<Object> objList){
		update.updateStockQty(objList);
	}
	
	/*Set Implemention on Update*/
	public void setOnUpate(OnUpdate update){
		this.update = update;
	}
	
	/*Select Record*/
	public List<Object> select(){
		return select.selectRecord();
	}
	
	public List<Object> selectAllGroupBy(String arg0, String arg1){
		return select.selectRecordGroupBy(arg0, arg1);
	}
	
	/*Select Record by arg0*/
	public List<Object> select(String arg0){
		return select.selectRecord(arg0);
	}
	
	public List<Object> selectPurchaseVouByItemID(String arg0){
		return select.selectPurchaseVoubyItemID(arg0);
	}
	
	public List<Object> select(String arg0 , String arg1){
		return select.selectRecord(arg0 ,arg1);
	} 
	
	public List<Object> select(String arg0 , String arg1, String arg2){
		return select.selectRecord(arg0 ,arg1, arg2);
	}
	
	
	//Select Record by Item Code
	public List<Object> selectRecordByItemCode(String arg0 , String arg1){
		return select.selectRecordByItemCode(arg0, arg1);
	}
	
	public List<Object> selectGroupByItemCode(){
		return select.selectGroupByItemCode();
	}
	
	
	/*Select Record for Old Stock Qty*/
	public List<Object> selectRecordOldStockQty(String arg0, String arg1){
		return select.selectRecordOldStockQty(arg0, arg1);
	}
	
	/*Select Record By Voucher ID*/
	public List<Object> selectRecordByVouID(String arg0){
		return select.selectRecordByVouID(arg0);
	}
	
	/*Select Record All Items By CAT ID and Sub-Cat ID*/
	public List<Object> selectRecordAllByCatSub(String arg0, String arg1){
		return select.selectRecordAllByCatSub(arg0, arg1);
	}
	
	/*Select Record only Purchased Items */
	public List<Object> selectRecordPurchasedItems(){
		return select.selectRecordPurchasedItems();
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
	
	/*Delete Record List*/
	public boolean delete(List<Object> objList){
		return delete.deleteRecord(objList);
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
