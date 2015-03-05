package com.ignite.mm.ticketing;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.R.integer;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.bluetoothprinter.BlueToothService;
import com.example.bluetoothprinter.BlueToothService.OnReceiveDataHandleEvent;
import com.ignite.mm.ticketing.application.BluetoothDeviceDialog;
import com.ignite.mm.ticketing.custom.listview.adapter.PDFShowAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllShowObject;

public class ShowInvoice extends SherlockActivity{
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	private int verson = 52;
	private BlueToothService mBTService = null;
	private ListView mDeviceList;
	private ArrayAdapter<String> mNewDevicesArrayAdapter = null;
	private Set<BluetoothDevice> devices;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	private BluetoothDeviceDialog mBluetoothDeviceDialog;
	private ProgressDialog dialog;
	public static ListView lvShow;
	private String TicketTypeId;
	private  ArrayList<AllShowObject> allShowObject;
	public static String ShowId, ShowName, TicketID, Price ,qty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_ticket_list);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarTitle.setText("SHOW");

		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle bundle = getIntent().getExtras();
		//TicketTypeId =bundle.getString("ticketTypeId");
		ShowId =bundle.getString("Show_ID");
		ShowName =bundle.getString("Show_Name");
		TicketID =bundle.getString("Ticket_ID"); 
		Price =bundle.getString("Price");
		qty = bundle.getString("No_of_ticket");
		getData();
		bluetoothPrint();
	}
	
	public void bluetoothPrint()
	{
		mBluetoothDeviceDialog = new BluetoothDeviceDialog(this);
       	mBluetoothDeviceDialog.show();
       	mDeviceList = mBluetoothDeviceDialog.getListView();
        mDeviceList.setOnItemClickListener(itemClickListener);
        mBTService = new BlueToothService(this, handler);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        if (!mBTService.IsOpen()) {
			mBTService.OpenDevice();
			return;
		}
		if (mBTService.GetScanState() == BlueToothService.STATE_SCANING)
			return;
		
		mNewDevicesArrayAdapter.clear();
		devices = mBTService.GetBondedDevice();

		if (devices.size() > 0) {

			for (BluetoothDevice device : devices) {
				mNewDevicesArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress());
			}
		}
		mDeviceList.setAdapter(mNewDevicesArrayAdapter);
		mBTService.setOnReceive(new OnReceiveDataHandleEvent() {

			public void OnReceive(BluetoothDevice device) {
				// TODO Auto-generated method stub
				if (device != null) {
					mNewDevicesArrayAdapter.add(device.getName() + "\n"
							+ device.getAddress());
				} else {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				}
			}
		});
		new Thread() {
			public void run() {
				mBTService.ScanDevice();
			}
		}.start();
	}
	
	private LinearLayout ticketContainer;
	private TextView showName;
	private TextView amount;
	private String SelectedPrice;
	private String Qty;
	
	private void getData() {
		View invoice = LayoutInflater.from(this).inflate(R.layout.activity_show_invoice,null,false);
		ticketContainer = (LinearLayout)invoice.findViewById(R.id.ticket_item_container);
		showName=(TextView)invoice.findViewById(R.id.txtShowTitle);
		amount=(TextView)invoice.findViewById(R.id.txt_grand_total);
		
		showName.setText("Show ပြဲ: "+ ShowName);
		int GrandTotal = 0;
		allShowObject = new ArrayList<AllShowObject>();
			View ticket = LayoutInflater.from(this).inflate(R.layout.list_item_show,null,false);
			((TextView)ticket.findViewById(R.id.txt_qty)).setText(qty);
			((TextView)ticket.findViewById(R.id.txt_ticket_price_oneitem)).setText(Price);
			ticketContainer.addView( ticket);
			GrandTotal += ShowInfoActivity.totalValue ;
			
			for (int i = 0; i < Integer.parseInt(qty); i++){
			SelectedPrice = ShowInvoice.Price;
			Qty = ShowInvoice.qty;
			allShowObject.add (new AllShowObject( ShowId, ShowName,  TicketID, SelectedPrice, Qty));
		}
		
		//Log.i("","Hello :" + allShowObject.size());
		lvShow=(ListView)findViewById(R.id.lvShow);
		lvShow.addHeaderView(invoice);
		lvShow.setAdapter(new PDFShowAdapter(this,allShowObject));
		amount.setText(String.valueOf("စုစုေပါင္း: "+GrandTotal + " MMK"));
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
	private final static String PDF_FILE_PATH = "/sdcard/external_sd/show_ticketing/";
	public static Bitmap getWholeListViewItemsToBitmap() {

	    ListView listview    = lvShow;
	    ListAdapter adapter  = listview.getAdapter(); 
	    int itemscount       = adapter.getCount();
	    int allitemsheight   = 0;
	    List<Bitmap> bmps    = new ArrayList<Bitmap>();

	    for (int i = 0; i < itemscount; i++) {

	        View childView      = adapter.getView(i, null, listview);
	        childView.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), MeasureSpec.EXACTLY), 
	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

	        childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
	        childView.setDrawingCacheEnabled(true);
	        childView.buildDrawingCache();
	        bmps.add(childView.getDrawingCache());
	        allitemsheight+=childView.getMeasuredHeight();
	    }

	    Bitmap bigbitmap    = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
	    Canvas bigcanvas    = new Canvas(bigbitmap);

	    Paint paint = new Paint();
	    int iHeight = 0;

	    for (int i = 0; i < bmps.size(); i++) {
	        Bitmap bmp = bmps.get(i);
	        bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
	        iHeight+=bmp.getHeight();

	        bmp.recycle();
	        bmp=null;
	    }

	    storeImage(bigbitmap, PDF_FILE_PATH, "showticket.png");
	    return bigbitmap;
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == actionBarBack) {
				finish();
			}
		}

	};
	
	 private Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case MESSAGE_STATE_CHANGE:
					switch (msg.arg1) {
					case BlueToothService.STATE_CONNECTED:
						break;
					case BlueToothService.STATE_CONNECTING:
						break;
					case BlueToothService.STATE_LISTEN:
					case BlueToothService.STATE_NONE:
						break;
					case BlueToothService.SUCCESS_CONNECT:
						dialog.dismiss();
						Toast.makeText(getApplicationContext(),"Success connected", Toast.LENGTH_LONG).show();
						mDeviceList.setVisibility(View.GONE);
						break;
					case BlueToothService.FAILED_CONNECT:
						dialog.dismiss();
						Toast.makeText(getApplicationContext(),"Not success connected", Toast.LENGTH_LONG).show();
						break;
					case BlueToothService.LOSE_CONNECT:
						dialog.dismiss();
						Toast.makeText(getApplicationContext(),"Lost connection", Toast.LENGTH_LONG).show();
						break;
					}
					break;
				case MESSAGE_READ:
					break;
				case MESSAGE_WRITE:
					break;

				}
			}
		};
		private OnItemClickListener itemClickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dialog = ProgressDialog.show(ShowInvoice.this, ""," Please wait...", true);
				if (!mBTService.IsOpen()) {
					mBTService.OpenDevice();
					return;
				}
				if (mBTService.GetScanState() == BlueToothService.STATE_SCANING) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				}
				if (mBTService.getState() == BlueToothService.STATE_CONNECTING) {
					return;
				}
				String info = ((TextView) arg1).getText().toString();
				String address = info.substring(info.length() - 17);
				mBTService.DisConnected();
				mBTService.ConnectToDevice(address);
				mBluetoothDeviceDialog.dismiss();
			}
		};
		
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
}
