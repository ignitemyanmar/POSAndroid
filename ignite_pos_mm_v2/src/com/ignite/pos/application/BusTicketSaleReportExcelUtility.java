package com.ignite.pos.application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.ignite.pos.model.BusTicketSale;
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

public class BusTicketSaleReportExcelUtility {

  private static final String RESULT = Environment.getExternalStorageDirectory() +"/POS/";
  private WritableCellFormat boldUnderline;
  private WritableCellFormat labels;
  private String inputFile;
  private List<Object> listBusTicket;
  private WritableCellFormat headerTitle;
  private List<String> searchInfoList;

  
  public BusTicketSaleReportExcelUtility(List<Object> listBusTicket, String filename, List<String> searchInfoList) {
	  this.listBusTicket = listBusTicket;
	  this.searchInfoList = searchInfoList;
	  IfExistFileDir(RESULT);
	  inputFile = filename == null ? RESULT+getToday()+"_DailyReport_BusTicketSale.xls" : RESULT+filename+".xls";
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
    addTitle(sheet, 0, 0, "Bus Ticket Sale Report");
    
    //Write Some Info
    addLabel(sheet, 0, 2, "Operator Name: "+searchInfoList.get(0));
    addLabel(sheet, 0, 3, "From Date: "+searchInfoList.get(1));
    addLabel(sheet, 0, 4, "To Date: "+searchInfoList.get(2));
    
    // Write a few headers
    addCaption(sheet, 0, 6, "Code No.");
    addCaption(sheet, 1, 6, "Sale Date");
    addCaption(sheet, 2, 6, "Customer");
    addCaption(sheet, 3, 6, "Operator Name");
    addCaption(sheet, 4, 6, "Trip");
    addCaption(sheet, 5, 6, "Departure Date");
    addCaption(sheet, 6, 6, "Departure Time");
    addCaption(sheet, 7, 6, "Bus Class");
    addCaption(sheet, 8, 6, "Seat Numbers");
    addCaption(sheet, 9, 6, "Seat Count");
    addCaption(sheet, 10, 6, "Price");
    addCaption(sheet, 11, 6, "Amount");

  }

  private void createContent(WritableSheet sheet) throws WriteException,
      RowsExceededException {
	//To change here
    int i = 8;
    for(Object busL: listBusTicket){
    	
    	BusTicketSale busSale = (BusTicketSale) busL; 
    	
    	addLabel(sheet, 0, i, busSale.getBarcodeNo());
    	addLabel(sheet, 1, i, busSale.getConfirmDate());
    	addLabel(sheet, 2, i, busSale.getCustomerName());
    	addLabel(sheet, 3, i, busSale.getOperatorName());
    	addLabel(sheet, 4, i, busSale.getTrip());
    	addLabel(sheet, 5, i, busSale.getDate());
    	addLabel(sheet, 6, i, busSale.getTime());
    	addLabel(sheet, 7, i, busSale.getBusClass());
    	addLabel(sheet, 8, i, busSale.getSeatNo());
    	addLabel(sheet, 9, i, busSale.getSeatCount()+"");
    	addLabel(sheet, 10, i, busSale.getSeatPrice()+"");
    	addLabel(sheet, 11, i, busSale.getSeatCount() * busSale.getSeatPrice()+"");
    	i++;
    }
  }
  
  private void addTitle(WritableSheet sheet, int column, int row, String s) 
		  throws RowsExceededException, WriteException {
	// TODO Auto-generated method stub
	    Label label;
	    label = new Label(column, row, s, headerTitle);
	    sheet.addCell(label);
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


