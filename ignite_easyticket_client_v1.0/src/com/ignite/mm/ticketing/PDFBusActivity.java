package com.ignite.mm.ticketing;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ignite.barcode.GenerateBarcode;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import com.ignite.mm.ticketing.custom.listview.adapter.PDFBusAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@SuppressLint("SdCardPath")
public class PDFBusActivity extends BaseSherlockActivity {
	private final static String PDF_FILE_PATH = "/sdcard/external_sd/IgniteEasyTticket/";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;

	private  ArrayList<AllBusObject> allBusObject;
	private TextView actionBarTitle2;
	private String OperatorName = "";
	private String BusTrip =  "";
	private String TripTime = "";
	private String BusClass = "";
	private String SeatPrice = "0";
	private static String Bar_Code_No = "0";
	public static String TripDate = "";
	public static String SelectedSeat = "";
	private static int bitmapWidth = 150;
	private static int bitmapHeight = 50;
	private String ConfirmDate;
	private String ConfirmTime;
	private static ListView lv_bus_booking_sheet;

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_ticket_item);

		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarTitle2 = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title2);
		actionBarTitle2.setVisibility(View.GONE);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarTitle.setText("Booking Sheet");
		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		Bundle bundle = getIntent().getExtras();
			
		if (bundle != null) {
			OperatorName = bundle.getString("Operator_Name");
			BusTrip = bundle.getString("Bus_Trip");
			TripDate = bundle.getString("Trip_Date");
			TripTime = bundle.getString("Trip_Time");
			BusClass = bundle.getString("Bus_Class");
			SelectedSeat = bundle.getString("Selected_Seats");
			SeatPrice = bundle.getString("Price");
			Bar_Code_No = bundle.getString("sale_order_no");
			ConfirmDate = bundle.getString("ConfirmDate");
			ConfirmTime = bundle.getString("ConfirmTime");
		}

		lv_bus_booking_sheet = (ListView)findViewById(R.id.lv_bus_booking_sheet);
		getData();
		
	}
	
	//Set Data into Adapter
	private void getData() {
		
		allBusObject = new ArrayList<AllBusObject>();
		allBusObject.add(new AllBusObject(BusTrip, TripDate, "", OperatorName, TripTime, ""
				, SelectedSeat, SeatPrice, "", AppLoginUser.getUserName(), BusClass,  ConfirmDate, ConfirmTime, Bar_Code_No, getBarcode()));
		
		lv_bus_booking_sheet.setAdapter(new PDFBusAdapter(PDFBusActivity.this, allBusObject));
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}
		}
	};

	/**	 
	 * @param pathName file path to save
	 * @return true if file name is already exist
	 */
	public Boolean IfExistPDF(String pathName) {
		boolean ret = true;

		File file = new File(PDF_FILE_PATH + pathName);
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

	public void changePDF() {
		IfExistPDF("busticket.pdf");
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(PDF_FILE_PATH	+ "busticket.pdf"));
			document.open();

			for (int i = 0; i < Integer.valueOf(Bus_Info_Activity.no_of_ticket); i++) {
				Image image1 = Image.getInstance(PDF_FILE_PATH + "busticket" + (i + 1) + ".png");
				image1.scalePercent(20);
				document.add(image1);
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		goToMail("busticket.png");
	}

	private void goToMail(String pathName) {

		File file = new File(PDF_FILE_PATH + pathName);
		Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Bus Ticketing ");
		intent.putExtra(Intent.EXTRA_TEXT, "");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(intent);
	}
	
	private Bitmap bmTicketView;

	public boolean printTicket() {
		boolean printed = false;
		bmTicketView = getWholeListViewItemsToBitmap();
		
		if (storeImage(bmTicketView, PDF_FILE_PATH, "BookingSheet.png")) {
			printed = true;
		}
		
		return printed;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 1, 0, null).setIcon(R.drawable.print_icon)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			if (printTicket()) {
				//changePDF();
				Toast.makeText(
						this,
						"Your ticket is printed to " + PDF_FILE_PATH
								+ "BookingSheet", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "Can't print your ticket!",
						Toast.LENGTH_LONG).show();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				null, false);
		// Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, newWidth,
		// newHeight, false);
		return resizedBitmap;
	}
	
	//Generate Bar Code Image (Bitmap)
	public static Bitmap getBarcode() {
		
		// barcode data
		String barcode_data = Bar_Code_No;
		
		// barcode image
		Bitmap bitmap = null;

		try {
			GenerateBarcode code = new GenerateBarcode();
			bitmap = code.encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128,
					bitmapWidth, bitmapHeight);
			
			//img_barcode.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return bitmap;
	}

	static List<Bitmap> bmps;
	
	/**
	 * Change ListView to Bitmap file & Save 
	 * @return Bitmap
	 */
	public static Bitmap getWholeListViewItemsToBitmap() {

	    ListView listview    = lv_bus_booking_sheet;
	    ListAdapter adapter  = listview.getAdapter();
	    int itemscount       = adapter.getCount();
	    int allitemsheight   = 0;
	    bmps = new ArrayList<Bitmap>();

	    Log.i("", "Items Count: "+itemscount);
	    
	    //Change Bitmap from listItems (Listview)
	    for (int i = 0; i < itemscount; i++) {

	        View childView = adapter.getView(i, null, listview);
	        childView.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), MeasureSpec.EXACTLY), 
	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

	        childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
	        childView.setDrawingCacheEnabled(true);
	        childView.buildDrawingCache();
	        bmps.add(childView.getDrawingCache());
	        allitemsheight += childView.getMeasuredHeight();
	    }
	    
	    Log.i("", "Bit Map Size after adding List Items: "+bmps.size());
	    
	    Bitmap bigbitmap    = Bitmap.createBitmap(lv_bus_booking_sheet.getWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
	    Canvas bigcanvas    = new Canvas(bigbitmap);

	    bigcanvas.drawColor(Color.WHITE);
	    
	    Paint paint = new Paint();
	    int iHeight = 0;

	    for (int i = 0; i < bmps.size(); i++) {
	    	
	    	Log.i("", "Bitmap Size: "+bmps.size());
	    	
	        Bitmap bmp = bmps.get(i);
	        
	        //To cover  java.lang.RuntimeException Bitmap recycle 
	        //android.graphics.Canvas.throwIfCannotDraw  (Error)
	        //if (bmp != null && !bmp.isRecycled()) {
	        	
		        bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
		        iHeight+=bmp.getHeight();
		        
	        	//bmp.recycle();
	        	bmp = null; 
//	        }else {
//				Log.i("", "Bitmap is already recycled!");
//			}
	    }
	    
	   // storeImage(bigbitmap, PRINT_FILE_PATH, "BusTicketing.png");
	    
	    return bigbitmap;
	}
	
	public static void getBitmapFromView(View view) {
		//view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
        bmps.add(view.getDrawingCache());
	}
	
	public static boolean storeImage(Bitmap imageData, String path, String filename) {
		// get path to external storage (SD card)
		String photoPath = path;
		File sdIconStorageDir = new File(photoPath);

		// create storage directories, if they don't exist
		sdIconStorageDir.mkdirs();

		try {
			String filePath = sdIconStorageDir.toString() + "/" + filename;
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);

			BufferedOutputStream bos = new BufferedOutputStream(
					fileOutputStream);

			// choose another format if PNG doesn't suit you
			imageData.compress(CompressFormat.PNG, 100, bos);

			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		} catch (IOException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return false;
		}

		return true;
	}
}
