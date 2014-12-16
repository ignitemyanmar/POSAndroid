
package com.ignite.pos.application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.ignite.pos.model.PurchaseVoucher;
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


public class PurchaseReportExcelUtility {

  private static final String RESULT = Environment.getExternalStorageDirectory() +"/POS/";
  private WritableCellFormat boldUnderline;
  private WritableCellFormat labels;
  private String inputFile;
  private List<Object> purchaseVoucherList;
  private WritableCellFormat headerTitle;
  
  public PurchaseReportExcelUtility(List<Object> purchaseVoucherList, String filename) {
	  this.purchaseVoucherList = purchaseVoucherList;
	  IfExistFileDir(RESULT);
	  inputFile = filename == null ? RESULT+getToday()+"_DailyReport_SaleVoucher.xls" : RESULT+filename+".xls";
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

    // create create a bold font with unterlines
    WritableFont headerBoldUnderline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
        UnderlineStyle.SINGLE);
    boldUnderline = new WritableCellFormat(headerBoldUnderline);
    // Lets automatically wrap the cells
    boldUnderline.setWrap(true);
    
    //Create Header 
    WritableFont header = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
    headerTitle = new WritableCellFormat(header);
    headerTitle.setWrap(true);

    CellView cv = new CellView();
    cv.setFormat(labels);
    cv.setFormat(boldUnderline);
    cv.setFormat(headerTitle);
    cv.setAutosize(true);
    //To change here
    
    //Write Title 
    addTitle(sheet, 0, 0, "Purchase Report");
    
    // Write a few headers
    addCaption(sheet, 0, 2, "Voucher No.");
    addCaption(sheet, 1, 2, "Date");
    addCaption(sheet, 2, 2, "Supplier Name");
    addCaption(sheet, 3, 2, "Total Amount");

  }

  private void addTitle(WritableSheet sheet, int column, int row, String s) 
		  throws RowsExceededException, WriteException {
	// TODO Auto-generated method stub
	    Label label;
	    label = new Label(column, row, s, headerTitle);
	    sheet.addCell(label);
  }

private void createContent(WritableSheet sheet) throws WriteException,
      RowsExceededException {
	//To change here
    int i = 3;
    for(Object purchaseV: purchaseVoucherList){
    	
    	PurchaseVoucher pv = (PurchaseVoucher) purchaseV; 
    	
    	addLabel(sheet, 0, i, pv.getVid());
    	addLabel(sheet, 1, i, pv.getVdate());
    	addLabel(sheet, 2, i, pv.getSupplierName());
    	addLabel(sheet, 3, i, pv.getGrandtotal());
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
