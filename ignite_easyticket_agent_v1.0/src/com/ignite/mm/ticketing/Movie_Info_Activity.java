package com.ignite.mm.ticketing;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.ignite.mm.ticketing.custom.listview.adapter.PDFBusAdapter;
import com.ignite.mm.ticketing.custom.listview.adapter.PDFMovieAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllBusObject;
import com.ignite.mm.ticketing.sqlite.database.model.AllMovieObject;

public class Movie_Info_Activity extends SherlockActivity{
	private TextView actionBarTitle, movieName, cinemaName, movieTime, price, seats, totalTickets, amount;
	private ActionBar actionBar;
	private ImageButton actionBarBack;
	
	private String TicketTypeID,MovieId, MovieTitle, DateId, Date, CinemaId, Cinema, MovieTimeId, MovieTime, SelectedSeat, YourSelectedSeat = "",SelectedPrice= "",upStairSelectedPrice="";
	public static int no_of_ticket;
	public static int totalValue;
	Boolean upstair = true;
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_ticket_list);
		
		actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_bar);
		actionBarTitle = (TextView) actionBar.getCustomView().findViewById(
				R.id.action_bar_title);
		actionBarBack = (ImageButton) actionBar.getCustomView().findViewById(
				R.id.action_bar_back);
		actionBarTitle.setText("MOVIE");

		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		Bundle bundle = getIntent().getExtras();
		//TicketTypeID = bundle.getString("ticketTypeId");
		MovieId = bundle.getString("movie_id");
		MovieTitle = bundle.getString("movie_title");
		DateId = bundle.getString("date_id");
		Date = bundle.getString("date");
		CinemaId = bundle.getString("cinema_id");
		Cinema = bundle.getString("cinema");
		MovieTimeId = bundle.getString("movie_time_id");
		MovieTime = bundle.getString("movie_time");
		SelectedSeat = bundle.getString("selected_seat");
	
		setData();
		bluetoothPrint();
	
	}
	
	private void bluetoothPrint() {
		// TODO Auto-generated method stub
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

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}
			
		}
	};
	private LinearLayout ticketContainer;
	private TextView mCinema;
	private TextView mDate;
	private TextView mTime;
	private TextView mMovieName;
	private ArrayList<AllMovieObject> allMovieObject;
	private static ListView lvMovie;
	private String SeatNo;
	private String seat;
	
	private void setData()
	{
		View invoice = LayoutInflater.from(this).inflate(R.layout.activity_movie_invoice,null,false);
		ticketContainer = (LinearLayout)invoice.findViewById(R.id.ticket_item_container);
		mCinema=(TextView)invoice.findViewById(R.id.txt_cinema);
		mMovieName = (TextView)invoice.findViewById(R.id.txt_movie_name);
		mDate=(TextView)invoice.findViewById(R.id.txt_date);
		mTime=(TextView)invoice.findViewById(R.id.txt_time);
		amount=(TextView)invoice.findViewById(R.id.txt_grand_total);
		
		mCinema.setText("á€›á€¯á€•á€¹á€›á€½á€„á€¹á€›á€¯á€¶: "+Cinema);
		mMovieName.setText("á€›á€¯á€•á€¹á€›á€½á€„á€¹: "+MovieTitle);
		mDate.setText("á€±á€”á‚•á€…á€¼á€²: "+Date);
		mTime.setText("á€»á€•á€žá€™á€Šá€¹á€·á€¡á€�á€ºá€­á€”á€¹: "+MovieTime);
		int GrandTotal = 0;
		String[] seletedSeat = SelectedSeat.split(",");
		allMovieObject = new ArrayList<AllMovieObject>();
		for (int i = 0; i < seletedSeat.length; i++) {
			View ticket = LayoutInflater.from(this).inflate(R.layout.list_item_ticket,null,false);
			((TextView)ticket.findViewById(R.id.txt_ticket_no)).setText(SelectingSeatActivity.Seat_List.get(Integer.valueOf(seletedSeat[i])).getSeatNo());
			((TextView)ticket.findViewById(R.id.txt_ticket_price)).setText(SelectingSeatActivity.Seat_List.get(Integer.valueOf(seletedSeat[i])).getPrice());
			ticketContainer.addView(ticket);
			GrandTotal += Integer.valueOf(SelectingSeatActivity.Seat_List.get(Integer.valueOf(seletedSeat[i])).getPrice());
			SeatNo = SelectingSeatActivity.Seat_List.get(Integer.valueOf(seletedSeat[i])).getSeatNo();
			//no_of_ticket = seletedSeat.length;
			allMovieObject.add ( new AllMovieObject(MovieId, MovieTitle, DateId, Date, CinemaId, Cinema, MovieTimeId, MovieTime ,SeatNo, seat));
		}
		lvMovie=(ListView)findViewById(R.id.lvMovie);
		lvMovie.addHeaderView(invoice);
		lvMovie.setAdapter(new PDFMovieAdapter(this,allMovieObject));
		amount.setText(String.valueOf("á€…á€¯á€…á€¯á€±á€•á€«á€„á€¹á€¸: "+GrandTotal + " MMK"));
		
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
	private final static String PDF_FILE_PATH = "/sdcard/external_sd/movie_ticketing/";
	public static Bitmap getWholeListViewItemsToBitmap() {

	    ListView listview    = lvMovie;
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

	    storeImage(bigbitmap, PDF_FILE_PATH, "movieticket.png");
	    return bigbitmap;
	}
	
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
			dialog = ProgressDialog.show(Movie_Info_Activity.this, ""," Please wait...", true);
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
