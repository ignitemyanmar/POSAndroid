
package com.ignite.pos.application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.ignite.pos.database.controller.ItemListController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.ItemList;
import com.ignite.pos.model.SaleReturn;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;
import android.os.Environment;
import android.util.Log;


public class SaleReturnReportExcelUtility {

  private static final String RESULT = Environment.getExternalStorageDirectory() +"/POS/";
  private WritableCellFormat boldUnderline;
  private WritableCellFormat labels;
  private String inputFile;
  private List<Object> saleReturnList;
  private DatabaseManager dbManager;
  private List<Object> listItem;
  
  public SaleReturnReportExcelUtility(List<Object> saleReturnList, String filename) {
	  this.saleReturnList = saleReturnList;
	  IfExistFileDir(RESULT);
	  inputFile = filename == null ? RESULT+getToday()+"_DailyReport_SaleReturn.xls" : RESULT+filename+".xls";
	  IfExistPDF(inputFile);
  }

  public void write() {
	
	File file = new File(inputFile);
	WorkbookSettings wbSettings = new WorkbookSettings();
	
	wbSettings.setLocale(new Locale("en", "EN"));
	
	WritableWorkbook workbook;
	try {
		workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet);
		workbook.write();
		workbook.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (WriteException e){
		e.printStackTrace();
	}
  }

  private void createLabel(WritableSheet sheet)
      throws WriteException {
    // Lets create a labels font
    WritableFont font = new WritableFont(WritableFont.ARIAL, 10);
    // Define the cell format
    labels = new WritableCellFormat(font);
    // Lets automatically wrap the cells
    labels.setWrap(true);

    // create a bold font with unterlines
    WritableFont headerBoldUnderline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
        UnderlineStyle.SINGLE);
    boldUnderline = new WritableCellFormat(headerBoldUnderline);
    // Lets automatically wrap the cells
    boldUnderline.setWrap(true);

    CellView cv = new CellView();
    cv.setFormat(labels);
    cv.setFormat(boldUnderline);
    cv.setAutosize(true);
    //To change here
    // Write a few headers
    addCaption(sheet, 0, 0, "Return Voucher");
    addCaption(sheet, 1, 0, "Return Date");
    addCaption(sheet, 2, 0, "Item Code");
    addCaption(sheet, 3, 0, "Item Name");
    addCaption(sheet, 4, 0, "Old Sale Qty");
    addCaption(sheet, 5, 0, "Return Qty");
    addCaption(sheet, 6, 0, "Refund Price");
    addCaption(sheet, 7, 0, "Refund Amount");

  }

  private void createContent(WritableSheet sheet) throws WriteException,
      RowsExceededException {
	//To change here
    int i = 1;
    for(Object saleR: saleReturnList){
    	
    	SaleReturn saler = (SaleReturn) saleR; 
    	
    	addLabel(sheet, 0, i, saler.getVid());
    	addLabel(sheet, 1, i, saler.getReturnDate());
    	addLabel(sheet, 2, i, saler.getItemid());
	    addLabel(sheet, 3, i, saler.getItemName());
    	addLabel(sheet, 4, i, saler.getOldQty()+"");
    	addLabel(sheet, 5, i, saler.getReturnQty()+"");
    	addLabel(sheet, 6, i, saler.getSalePrice()+"");
    	addLabel(sheet, 7, i, saler.getItemTotal()+"");
    	i++;
    }
  }

  private void addCaption(WritableSheet sheet, int column, int row, String s)
      throws RowsExceededException, WriteException {
    Label label;
    label = new Label(column, row, s, boldUnderline);
    sheet.addCell(label);
  }

  public void addNumber(WritableSheet sheet, int column, int row,
      Integer integer) throws WriteException, RowsExceededException {
    Number number;
    number = new Number(column, row, integer, labels);
    sheet.addCell(number);
  }

  private void addLabel(WritableSheet sheet, int column, int row, String s)
      throws WriteException, RowsExceededException {
    Label label;
    label = new Label(column, row, s, labels);
    sheet.addCell(label);
  }
  
  	public Boolean IfExistFileDir(String Dir){
		File fileDir = new File(Dir);
		if(!fileDir.exists()){
			fileDir.mkdirs();
			return false;
		}
		return true;
	}
	private Boolean IfExistPDF(String pathName) {
		boolean ret = true;
	
		File file = new File(pathName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				Log.e("TravellerLog :: ", "Problem creating folder");
				ret = false;
			}
		}
		return ret;
	}
	private String getToday(){
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		Log.i("","Hello Today: "+formattedDate);
		return formattedDate;
	}
} 

