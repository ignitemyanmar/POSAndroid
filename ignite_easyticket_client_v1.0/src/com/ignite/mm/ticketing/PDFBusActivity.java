package com.ignite.mm.ticketing;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ignite.mm.ticketing.application.BluetoothDeviceDialog;
import com.ignite.mm.ticketing.custom.listview.adapter.DeviceAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.PDFBusAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import com.ignite.mm.ticketing.sqlite.database.model.Device;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.smk.skalertmessage.SKToastMessage;
import com.zkc.helper.printer.BlueToothService;
import com.zkc.helper.printer.PrintService;
import com.zkc.helper.printer.PrinterClass;
import com.zkc.helper.printer.PrinterClassFactory;

@SuppressLint({ "SdCardPath", "ShowToast" })
public class PDFBusActivity extends BaseSherlockActivity {
	private final static String PDF_FILE_PATH = Environment.getExternalStorageDirectory()+"/IgniteEasyTicket/";
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
	public static String Bar_Code_No = "0";
	public static String TripDate = "";
	public static String SelectedSeat = "";
	private static int bitmapWidth = 150;
	private static int bitmapHeight = 50;
	private String ConfirmDate;
	private String ConfirmTime;
	private static ListView lv_bus_booking_sheet;
	private Bitmap bmTicketView;
	
	//Print Ticket Slip - Variables
	public Handler mhandler = null;
	public static PrinterClass printerClass = null;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter = null;
	public static ArrayAdapter<String> mNewDevicesArrayAdapter = null;
	public static List<Device> deviceList;
	private DeviceAdapter adapter;
	private String BuyerName;
	
	//Check Device connected
	public static boolean checkState = true;
	private Thread tv_update;
	TextView textView_state;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	//Handler mhandler=null;
	Handler handler = null;
	
	private ProgressDialog dialog;
	private LinearLayout img_print;
	private String OrderDateTime;
	private String Phone;
	private String TicketNo;
	private String SeatCount;

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
		actionBarTitle.setText("Ticket Sheet");
		img_print = (LinearLayout)actionBar.getCustomView().findViewById(R.id.img_print_layout);
		img_print.setVisibility(View.VISIBLE);
		img_print.setOnClickListener(clickListener);
		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		//deviceList = new ArrayList<Device>();
		
		//Check Bluetooth Connection
		checkBluetoothConnect();
		
		if (printerClass != null) {
			Log.i("", "Check State (2nd) On Create: "+printerClass.getState());
		}
		
		Bundle bundle = getIntent().getExtras();
			
		if (bundle != null) {
			OperatorName = bundle.getString("Operator_Name");
			BusTrip = bundle.getString("from_to");
			TripDate = bundle.getString("date");
			TripTime = bundle.getString("time");
			BusClass = bundle.getString("classes");
			SelectedSeat = bundle.getString("selected_seat");
			SeatPrice = bundle.getString("Price");
			Bar_Code_No = bundle.getString("sale_order_no");
			ConfirmDate = bundle.getString("ConfirmDate");
			ConfirmTime = bundle.getString("ConfirmTime");
			OrderDateTime = bundle.getString("order_date");
			BuyerName = bundle.getString("BuyerName");
			Phone = bundle.getString("BuyerPhone");
			TicketNo = bundle.getString("TicketNo");
			SeatCount = bundle.getString("SeatCount");
		}

		lv_bus_booking_sheet = (ListView)findViewById(R.id.lv_bus_booking_sheet);
		getData();
		
		//to link another App
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtras(bundle);
		sendIntent.setType("text/plain");
		
		// Verify that the intent will resolve to an activity
		if (sendIntent.resolveActivity(getPackageManager()) != null) {
			startActivity(sendIntent);
		}
	}
	
	/**
	 *  Set Bus Ticket Data into adapter
	 */
	private void getData() {
		
		allBusObject = new ArrayList<AllBusObject>();
		Integer amount = Integer.valueOf(SeatCount) * Integer.valueOf(SeatPrice);
		allBusObject.add(new AllBusObject(BusTrip, TripDate, "", OperatorName, TripTime, ""
				, SelectedSeat, SeatPrice, "", AppLoginUser.getUserName(), BusClass, ConfirmDate, ConfirmTime
				, Bar_Code_No, getBarcode(), BuyerName, Phone, TicketNo, SeatCount, 0, amount));
		
		Log.i("", "All Bus Object: "+allBusObject.toString());
		lv_bus_booking_sheet.setAdapter(new PDFBusAdapter(PDFBusActivity.this, allBusObject));
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				closeAllActivities();
				UserLogin login = new UserLogin();
				login.finish();
			}
			if (v == img_print) {
				if (printerClass != null) {
					Log.i("", "Check State (2nd) On Print Click: "+printerClass.getState());
					
					//If Bluetooth Support not have ... 
			        if (!BlueToothService.HasDevice()) {
			        	
			            SKToastMessage.showMessage(PDFBusActivity.this, "The device does not have Bluetooth support!", SKToastMessage.LENGTH_LONG);
			            
			        }else {
			        	if (!printerClass.IsOpen()) {
							printerClass.open(PDFBusActivity.this);
							connectBluetoothService();
							printSlip();
						}else {
							printSlip();
						}
			        	
					}
				}
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
	
	public boolean saveTicket() {
		boolean printed = false;
		bmTicketView = getWholeListViewItemsToBitmap();
		
		if (storeImage(bmTicketView, PDF_FILE_PATH, "BookingSheet.png")) {
			printed = true;
		}
		
		return printed;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		/*menu.add(0, 1, 0, null).setIcon(R.drawable.print_icon)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);*/
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}	
	
	BluetoothDeviceDialog deviceDialog;
	
	/**
	 *  Print the Slip !
	 */
	
	protected ProgressDialog progressDialog;
	private void printSlip() {
		// TODO Auto-generated method stub
		
		Log.i("", "Check State (2nd) On Print Method: "+printerClass.getState());
		
		//Save Ticket
		if (saveTicket()) {
			
			Log.i("", "Saved!");
			
			/*Toast.makeText(
					this,
					"Your ticket is saved to " + PDF_FILE_PATH
							+ "BookingSheet", Toast.LENGTH_SHORT).show();*/
		} else {
			Log.i("", "Fail Saved!");
			/*Toast.makeText(this, "Can't save your ticket!",
					Toast.LENGTH_LONG).show();*/
		}
		
		//Print Ticket
		//If not connected , show dialog for device list
		//If connected, print directly
		final Bitmap ticketBitmap = getWholeListViewItemsToBitmap();
		if (ticketBitmap != null) {
			
			Log.i("", "Ticket Bitmap is not null!");
			
			if (printerClass != null) {
				
				Log.i("", "Printer class is not null!");
				
				//If Bluetooth Support not have ... 
		        if (!BlueToothService.HasDevice()) {
		        	
		            SKToastMessage.showMessage(PDFBusActivity.this, "The device does not have Bluetooth support!", SKToastMessage.LENGTH_LONG);
		            
		        }else {
		        	
		        	Log.i("", "Check State (2nd) On Print Method ==> If bluetooth support: "+printerClass.getState());
		        	
					if(printerClass.getState() != PrinterClass.STATE_CONNECTED)
					{
						Log.i("", "Not connect yet with device !!! ");
						
						BluetoothDeviceDialog deviceDialog = new BluetoothDeviceDialog(PDFBusActivity.this);
						
						if (printerClass != null) {
							if (printerClass.getState() == PrinterClass.STATE_CONNECTED) {
								Toast.makeText(PDFBusActivity.this, "connected", Toast.LENGTH_SHORT).show();	
								checkState = true;
							}else if (printerClass.getState() == PrinterClass.STATE_CONNECTING) {
								Toast.makeText(PDFBusActivity.this, "connecting...", Toast.LENGTH_SHORT).show();	
							}else if(printerClass.getState() == PrinterClass.STATE_SCAN_STOP)
							{
								Toast.makeText(PDFBusActivity.this, "stop scanning...", Toast.LENGTH_SHORT).show();	
								
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
							}else if(printerClass.getState() == PrinterClass.STATE_SCANING)
							{
								Log.i("", "Scanning...!");
								
								Toast.makeText(PDFBusActivity.this, "scanning....", Toast.LENGTH_LONG).show();	
								
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
								
							}else {
								//int ss=PrintActivity.pl.getState();
								
								Toast.makeText(PDFBusActivity.this, "Not connect yet!", Toast.LENGTH_SHORT).show();	
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
							}
						}
						
						//Print Setting
						mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
								R.drawable.device_name);
						
						//Paired DeviceList Show
						//if (deviceList != null && deviceList.size() > 0) {
							/*Log.i("", "Paired Device List !! ");
							
							adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
							deviceDialog.getListView().setAdapter(adapter);	*/
							
							//New DeviceList & Scan							
							mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
									R.drawable.device_name);
							
							deviceList = new ArrayList<Device>();
							
							if(deviceList!=null)
							{
								deviceList.clear();
							}
							
							mNewDevicesArrayAdapter.clear();
							printerClass.scan();
							
							//Get Device List after scanning
							deviceList = printerClass.getDeviceList();
							
							if (deviceList != null && deviceList.size() > 0) {
								Log.i("", "New Device's list: "+deviceList.toString());
								
								adapter = new DeviceAdapter(PDFBusActivity.this, deviceList);
								deviceDialog.getListView().setAdapter(adapter);	
							}
							
						deviceDialog.setCallbackListener(new BluetoothDeviceDialog.Callback() {
							
							public void onDeviceChoose(int position) {
								//Connect to Selected Device's Address
								Log.i("", "Enter Here, device choose !!!!!!!!!!!!!!");
								
								//Connect with the selected device
								printerClass.connect(deviceList.get(position).getDeviceAddress());
															
								/*if (printerClass.getState() == PrinterClass.STATE_CONNECTING) {
									Toast.makeText(PDFBusActivity.this, "connecting , pls wait ... !", Toast.LENGTH_SHORT).show();
								}*/ 
								
								progressDialog = ProgressDialog.show(PDFBusActivity.this, "", "Connecting , pls wait  ...", true);
								progressDialog.setCancelable(true);
							}
						});
						
						deviceDialog.show();
						
					}else if(printerClass.getState() == PrinterClass.STATE_CONNECTED){
						
						Log.i("", "Connect with Device !!!!!!!!!");
						
						try {
							checkState = true;
							printerClass.printImage(ticketBitmap);
							Toast.makeText(PDFBusActivity.this, "Connected & Printing ...", Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(PDFBusActivity.this, "Fail to print!", Toast.LENGTH_LONG).show();
						}
					}//End if - check connected 
				}//End if - check if have Bluetooth support
			}else {
				Log.i("", "Printer class is null!");
			}
		}
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
	
	//Check Bluetooth Connect on Create
	public void checkBluetoothConnect() {
		// TODO Auto-generated method stub
		
		mhandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MESSAGE_READ:
					 byte[] readBuf = (byte[]) msg.obj;
					 Log.i("", "readBuf:"+readBuf[0]);
					 if(readBuf[0]==0x13)
					 {
						 PrintService.isFUll=true;
					 }
					 else if(readBuf[0]==0x11)
					 {
						 PrintService.isFUll=false;
					 }
					 else{
		                // construct a string from the valid bytes in the buffer
		                String readMessage = new String(readBuf, 0, msg.arg1);
		                /*Toast.makeText(getApplicationContext(),readMessage,
	                               Toast.LENGTH_SHORT).show();*/
					 }
					break;
				case MESSAGE_STATE_CHANGE:// è“�ç‰™è¿žæŽ¥çŠ¶
					switch (msg.arg1) {
					case PrinterClass.STATE_CONNECTED:// å·²ç»�è¿žæŽ¥
						break;
					case PrinterClass.STATE_CONNECTING:// æ­£åœ¨è¿žæŽ¥
						break;
					case PrinterClass.STATE_LISTEN:
					case PrinterClass.STATE_NONE:
						break;
					case PrinterClass.SUCCESS_CONNECT:
						Log.i("", "Success Connected & Printing..... ");
						final Bitmap ticketBitmap = getWholeListViewItemsToBitmap();
						if (ticketBitmap != null) {
							if (printerClass.getState() == PrinterClass.STATE_CONNECTED) {
								
								checkState = true;
								printerClass.printImage(ticketBitmap);
								progressDialog.dismiss();
								Toast.makeText(PDFBusActivity.this, "Connected & printing ... !", Toast.LENGTH_SHORT).show();
							}
						}
						break;
					case PrinterClass.FAILED_CONNECT:
						break;
					case PrinterClass.LOSE_CONNECT:
						Log.i("", "LOSE_CONNECT");
					}
					break;
				case MESSAGE_WRITE:

					break;
				}
				super.handleMessage(msg);
			}
		};
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					break;
				case 1:// æ‰«æ��å®Œæ¯•
					Device d=(Device)msg.obj;
					if(d!=null)
					{
						if(deviceList == null)
						{
							deviceList = new ArrayList<Device>();
						}
						
						if(!checkData(deviceList, d))
						{
							//deviceList.add(d);
							Log.i("", "Device list: "+PDFBusActivity.deviceList.toString());
							
						}
					}else {
						Log.i("", "Message Device Addr: is null!");
					}
					break;
				case 2:// å�œæ­¢æ‰«æ��
					break;
				}
			}
		};
		
		if(checkState)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
			if (PDFBusActivity.printerClass != null) {								
				
				if (PDFBusActivity.printerClass.getState() == PrinterClass.STATE_CONNECTED) {
					Toast.makeText(PDFBusActivity.this, "connected device", Toast.LENGTH_LONG).show();	
					checkState=true;
				}else if (PDFBusActivity.printerClass.getState() == PrinterClass.STATE_CONNECTING) {
					
					Toast.makeText(PDFBusActivity.this, "connecting device ...", Toast.LENGTH_LONG).show();
					connectBluetoothService();  //Edited by Su Wai on 24 Mar, 2015
					
				}else if(PDFBusActivity.printerClass.getState() == PrinterClass.LOSE_CONNECT
						|| PDFBusActivity.printerClass.getState() == PrinterClass.FAILED_CONNECT){
					
					checkState = false;
					Toast.makeText(PDFBusActivity.this, "Not connect device yet!", Toast.LENGTH_LONG).show();
					
					connectBluetoothService();   //Edited by Su Wai on 24 Mar, 2015
					
				}else{
					Toast.makeText(PDFBusActivity.this, "Not connect device yet!", Toast.LENGTH_LONG).show();
					connectBluetoothService();   //Edited by Su Wai on 24 Mar, 2015
				}
			}else {
				connectBluetoothService();
			}//End If printerClass is Null
		}
	}
	
	private void connectBluetoothService() {
		// TODO Auto-generated method stub
		//Connect Bluetooth Service
		try {
			printerClass = PrinterClassFactory.create(0, this, mhandler, handler);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(PDFBusActivity.this, "Fail Bluetooth Service!", Toast.LENGTH_SHORT);
		}
	}

	
    private boolean checkData(List<Device> list,Device d)
    {
    	for (Device device : list) {
			if(device.getDeviceAddress().equals(d.getDeviceAddress()))
			{
				Log.i("", "Equal!");
				return true;
			}
		}
    	
    	Log.i("", "Not Equal!");
    	return false;
    }	
    
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	checkState = true;
    	
    	Log.i("", "On Restart: "+checkState);
    	
    	super.onRestart();
    }
    
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		closeAllActivities();
		UserLogin login = new UserLogin();
		login.finish();
		//android.os.Process.killProcess(android.os.Process.myPid());
	}
}
