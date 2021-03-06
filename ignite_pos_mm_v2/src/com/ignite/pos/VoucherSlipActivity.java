package com.ignite.pos;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.pos.adapter.DeviceAdapter;
import com.ignite.pos.adapter.VoucherSlipListViewAdapter;
import com.ignite.pos.application.BluetoothDeviceDialog;
import com.ignite.pos.model.BundleListObjet;
import com.ignite.pos.model.Device;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;
import com.zkc.helper.printer.BlueToothService;
import com.zkc.helper.printer.PrintService;
import com.zkc.helper.printer.PrinterClass;
import com.zkc.helper.printer.PrinterClassFactory;

@SuppressLint("SdCardPath")
@TargetApi(Build.VERSION_CODES.KITKAT)
public class VoucherSlipActivity extends SherlockActivity{

	private TextView txt_voucher_no;
	private TextView txt_date;
	private TextView txt_sale_person;
	private TextView txt_buyer;
	private static ListView lv_voucher;
	private TextView txt_grand_total;
	private TextView txt_discount;
	private TextView txt_net_amount;
	private String saleVoucherString;
	private BundleListObjet bundleListObjet;
	private List<SaleVouncher> saleVoucherList;
	private Button btn_done;
	private Button btn_print;
	private TextView txt_paid_amount;
	private TextView txt_credit_left_amount;
	
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
	Handler handler = null;
	
	private ProgressDialog dialog;
	private Bitmap bitmapVoucher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher_slip);
		
		//Data to print
		setData();
		
		//Check Bluetooth Connection
		checkBluetoothConnect();
		
		btn_done = (Button) findViewById(R.id.btn_done);
		btn_print = (Button) findViewById(R.id.btn_print);
		
		btn_done.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_print.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Save & Print the Slip
				printSlip();
				
				//SKToastMessage.showMessage(VoucherSlipActivity.this, "Print not set up..!!", SKToastMessage.ERROR);
				
				//private int verson = 52;
				
				/*PrintHelper printHelper = new PrintHelper(VoucherSlipActivity.this);
				printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
				
				// Get the image
				Bitmap image = getWholeListViewItemsToBitmap();
				Log.i("","bitmap :" +image.getHeight());
				
				if (image != null) {

					Bitmap bitmapOrg = image;// BitmapFactory.decodeFile(picPath);
					int w = bitmapOrg.getWidth();
					int h = bitmapOrg.getHeight();
					//mBTService.PrintImage(resizeImage(bitmapOrg, (int)(verson* 8), h),80);
					
				    // Send it to the print helper
				    printHelper.printBitmap("PrintShop", image);
					
					return;
				}*/
				
				/*// Get the print manager from the context
			    PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

			    // Start a print job, passing in a PrintDocumentAdapter implementation
			    // to handle the generation of a print document
			    printManager.print("Test Print", new SaleVoucherSlipAdapter(getApplicationContext()),
			             null);*/
			    
			}
		});
	}
	
	static View invoiceHeader;
	static View invoiceFooter;
	
	private void setData() {
		// TODO Auto-generated method stub
		
		//Header View
		invoiceHeader = LayoutInflater.from(this).inflate(R.layout.activity_sale_slip_header,null,false);
		txt_voucher_no = (TextView) invoiceHeader.findViewById(R.id.txt_vou_no);
		txt_date = (TextView) invoiceHeader.findViewById(R.id.txt_date);
		txt_sale_person = (TextView) invoiceHeader.findViewById(R.id.txt_sale_person);
		txt_buyer = (TextView) invoiceHeader.findViewById(R.id.txt_buyer);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			saleVoucherString = extras.getString("saleVoucher");
		}
		
		bundleListObjet = new Gson().fromJson(saleVoucherString, BundleListObjet.class);
		saleVoucherList = bundleListObjet.getSaleVouncher();
		
		Log.i("", "Sale voucher list : "+saleVoucherList);
		
		txt_voucher_no.setText(saleVoucherList.get(0).getVid());
		txt_date.setText(saleVoucherList.get(0).getVdate());
		txt_sale_person.setText(saleVoucherList.get(0).getSalePerson());
		txt_buyer.setText(saleVoucherList.get(0).getCusname());
		
		//Footer View
		invoiceFooter = LayoutInflater.from(this).inflate(R.layout.activity_sale_slip_footer, null, false);
		txt_grand_total = (TextView) invoiceFooter.findViewById(R.id.txt_grand_total);
		txt_discount = (TextView) invoiceFooter.findViewById(R.id.txt_discount);
		txt_net_amount = (TextView) invoiceFooter.findViewById(R.id.txt_net_amount);
		txt_paid_amount = (TextView)invoiceFooter.findViewById(R.id.txt_paid_amount);
		txt_credit_left_amount = (TextView)invoiceFooter.findViewById(R.id.txt_credit_left_amount);
		
		txt_grand_total.setText(saleVoucherList.get(0).getTotal());
		//txt_discount.setText(String.valueOf((grandTotal * Integer.valueOf(saleVoucherList.get(0).getDiscount())) / 100)+"");
		txt_discount.setText(saleVoucherList.get(0).getDiscount());
		
		Integer net_amt;
		
		if (saleVoucherList.get(0).getDiscount() == null) {
			
			Integer discount = 0;
			net_amt = Integer.valueOf(saleVoucherList.get(0).getTotal()) - discount;
			txt_net_amount.setText(net_amt+"");
			
		}else {
			
			net_amt = Integer.valueOf(saleVoucherList.get(0).getTotal()) - Integer.valueOf(saleVoucherList.get(0).getDiscount());
			txt_net_amount.setText(net_amt+"");
		}	
		
		txt_paid_amount.setText("ေပးေခ်ေငြ  - "+SaleActivity.creditPaidAmount+" က်ပ္");
		
		//Get credit left amount
		Integer credit_left_amount = net_amt - Integer.valueOf(SaleActivity.creditPaidAmount);
		
		txt_credit_left_amount.setText("ေပးရန္က်န္ေငြ  - "+credit_left_amount+" က်ပ္");
		
		lv_voucher = (ListView) findViewById(R.id.lv_slip_container);
		lv_voucher.addHeaderView(invoiceHeader);
		lv_voucher.setAdapter(new VoucherSlipListViewAdapter(this, saleVoucherList));
		lv_voucher.addFooterView(invoiceFooter);
		//setListViewHeightBasedOnChildren(lv_voucher);
	}

	private final static String PRINT_FILE_PATH = "/sdcard/external_sd/POS_SaleVoucher/";
	static List<Bitmap> bmps;
	
	public static Bitmap getWholeListViewItemsToBitmap() {

	    ListView listview    = lv_voucher;
	    ListAdapter adapter  = listview.getAdapter();
	    int itemscount       = adapter.getCount();
	    int allitemsheight   = 0;
	    bmps = new ArrayList<Bitmap>();

	    //Change Bitmap from Header View
	    //getBitmapFromView(invoiceHeader);
	    
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
	    
	    //Change Bitmap from Footer View
	    //getBitmapFromView(invoiceFooter);
	    
	    //Log.i("", "Bit Map Size after adding Footer: "+bmps.size());
	    
	    //Bitmap bigbitmap    = Bitmap.createBitmap(listview.getMeasuredWidth(), allHeight, Bitmap.Config.ARGB_8888);
	    Bitmap bigbitmap    = Bitmap.createBitmap(lv_voucher.getWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
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
	    
	   // storeImage(bigbitmap, PRINT_FILE_PATH, "SaleVoucher.png");
	    return bigbitmap;
	}
	
	public boolean isSaveVoucher() {
		boolean saved = false;
		bitmapVoucher = getWholeListViewItemsToBitmap();
		
		if (storeImage(bitmapVoucher, PRINT_FILE_PATH, "SaleVoucher.png")) {
			saved = true;
		}
		
		return saved;
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
	
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,height, matrix, true);
		return resizedBitmap;
	}
	
	/**
	 *  Print the Slip !
	 */
	protected ProgressDialog progressDialog;
	private void printSlip() {
		// TODO Auto-generated method stub
		Log.i("", "Check State (2nd) On Print Method: "+printerClass.getState());
		
		//Save Voucher
		if (isSaveVoucher()) {
			
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
		
		//Print Voucher
		//If not connected , show dialog for device list
		//If connected, print directly
		final Bitmap bitmapVoucher = getWholeListViewItemsToBitmap();
		if (bitmapVoucher != null) {
			
			Log.i("", "Voucher Bitmap is not null!");
			
			if (printerClass != null) {
				
				Log.i("", "Printer class is not null!");
				
				//If Bluetooth Support not have ... 
		        if (!BlueToothService.HasDevice()) {
		        	
		            SKToastMessage.showMessage(VoucherSlipActivity.this, "The device does not have Bluetooth support!", SKToastMessage.LENGTH_LONG);
		            
		        }else {
		        	
		        	Log.i("", "Check State (2nd) On Print Method ==> If bluetooth support: "+printerClass.getState());
		        	
					if(printerClass.getState() != PrinterClass.STATE_CONNECTED)
					{
						Log.i("", "Not connect yet with device !!! ");
						
						BluetoothDeviceDialog deviceDialog = new BluetoothDeviceDialog(VoucherSlipActivity.this);
						
						if (printerClass != null) {
							if (printerClass.getState() == PrinterClass.STATE_CONNECTED) {
								Toast.makeText(VoucherSlipActivity.this, "connected", Toast.LENGTH_SHORT).show();	
								checkState = true;
							}else if (printerClass.getState() == PrinterClass.STATE_CONNECTING) {
								Toast.makeText(VoucherSlipActivity.this, "connecting...", Toast.LENGTH_SHORT).show();	
							}else if(printerClass.getState() == PrinterClass.STATE_SCAN_STOP)
							{
								Toast.makeText(VoucherSlipActivity.this, "stop scanning...", Toast.LENGTH_SHORT).show();	
								
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(VoucherSlipActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
							}else if(printerClass.getState() == PrinterClass.STATE_SCANING)
							{
								Log.i("", "Scanning...!");
								
								Toast.makeText(VoucherSlipActivity.this, "scanning....", Toast.LENGTH_LONG).show();	
								
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(VoucherSlipActivity.this, deviceList);
									deviceDialog.getListView().setAdapter(adapter);	
								}
								
							}else {
								
								Toast.makeText(VoucherSlipActivity.this, "Not connect yet!", Toast.LENGTH_SHORT).show();	
								if (deviceList != null && deviceList.size() > 0) {
									adapter = new DeviceAdapter(VoucherSlipActivity.this, deviceList);
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
							
							adapter = new DeviceAdapter(VoucherSlipActivity.this, deviceList);
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
								
								adapter = new DeviceAdapter(VoucherSlipActivity.this, deviceList);
								deviceDialog.getListView().setAdapter(adapter);	
							}
							
						deviceDialog.setCallbackListener(new BluetoothDeviceDialog.Callback() {
							
							public void onDeviceChoose(int position) {
								//Connect to Selected Device's Address
								Log.i("", "Enter Here, device choose !!!!!!!!!!!!!!");
								
								//Connect with the selected device
								printerClass.connect(deviceList.get(position).getDeviceAddress());
															
								/*if (printerClass.getState() == PrinterClass.STATE_CONNECTING) {
									Toast.makeText(VoucherSlipActivity.this, "connecting , pls wait ... !", Toast.LENGTH_SHORT).show();
								}*/ 
								
								progressDialog = ProgressDialog.show(VoucherSlipActivity.this, "", "Connecting , pls wait  ...", true);
								progressDialog.setCancelable(true);
							}
						});
						
						deviceDialog.show();
						
					}else if(printerClass.getState() == PrinterClass.STATE_CONNECTED){
						
						Log.i("", "Connect with Device !!!!!!!!!");
						
						try {
							checkState = true;
							printerClass.printImage(bitmapVoucher);
							Toast.makeText(VoucherSlipActivity.this, "Connected & Printing ...", Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(VoucherSlipActivity.this, "Fail to print!", Toast.LENGTH_LONG).show();
						}
					}//End if - check connected 
				}//End if - check if have Bluetooth support
			}else {
				Log.i("", "Printer class is null!");
			}
		}
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
				case MESSAGE_STATE_CHANGE:// Ã¨â€œï¿½Ã§â€°â„¢Ã¨Â¿Å¾Ã¦Å½Â¥Ã§Å Â¶
					switch (msg.arg1) {
					case PrinterClass.STATE_CONNECTED:// Ã¥Â·Â²Ã§Â»ï¿½Ã¨Â¿Å¾Ã¦Å½Â¥
						break;
					case PrinterClass.STATE_CONNECTING:// Ã¦Â­Â£Ã¥Å“Â¨Ã¨Â¿Å¾Ã¦Å½Â¥
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
								Toast.makeText(VoucherSlipActivity.this, "Connected & printing ... !", Toast.LENGTH_SHORT).show();
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
				case 1:// Ã¦â€°Â«Ã¦ï¿½ï¿½Ã¥Â®Å’Ã¦Â¯â€¢
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
							Log.i("", "Device list: "+VoucherSlipActivity.deviceList.toString());
						}
					}else {
						Log.i("", "Message Device Addr: is null!");
					}
					break;
				case 2:// Ã¥ï¿½Å“Ã¦Â­Â¢Ã¦â€°Â«Ã¦ï¿½ï¿½
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
			
			if (VoucherSlipActivity.printerClass != null) {								
				
				if (VoucherSlipActivity.printerClass.getState() == PrinterClass.STATE_CONNECTED) {
					Toast.makeText(VoucherSlipActivity.this, "connected device", Toast.LENGTH_LONG).show();	
					checkState=true;
				}else if (VoucherSlipActivity.printerClass.getState() == PrinterClass.STATE_CONNECTING) {
					
					Toast.makeText(VoucherSlipActivity.this, "connecting device ...", Toast.LENGTH_LONG).show();
					connectBluetoothService();  //Edited by Su Wai on 24 Mar, 2015
					
				}else if(VoucherSlipActivity.printerClass.getState() == PrinterClass.LOSE_CONNECT
						|| VoucherSlipActivity.printerClass.getState() == PrinterClass.FAILED_CONNECT){
					
					checkState = false;
					Toast.makeText(VoucherSlipActivity.this, "Not connect device yet!", Toast.LENGTH_LONG).show();
					
					connectBluetoothService();   //Edited by Su Wai on 24 Mar, 2015
					
				}else{
					Toast.makeText(getApplicationContext(), "Not connect device yet!", Toast.LENGTH_LONG).show();
					connectBluetoothService();   //Edited by Su Wai on 24 Mar, 2015
				}
			}else {
				connectBluetoothService();
			}//End If printerClass is Null
		}
	}
	
	/**
	 *  If the first time, connect Bluetooth ... 
	 */
	private void connectBluetoothService() {
		// TODO Auto-generated method stub
		//Connect Bluetooth Service
		try {
			printerClass = PrinterClassFactory.create(0, this, mhandler, handler);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(VoucherSlipActivity.this, "Fail Bluetooth Service!", Toast.LENGTH_SHORT).show();
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
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null && listView.getCount() == 0) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	checkState = true;
    	Log.i("", "On Restart: "+checkState);
    	super.onRestart();
    }
    
    
}
