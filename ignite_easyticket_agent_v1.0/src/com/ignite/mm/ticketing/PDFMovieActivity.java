package com.ignite.mm.ticketing;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ignite.barcode.GenerateBarcode;
import com.ignite.mm.ticketing.custom.listview.adapter.PDFMovieAdapter;
import com.ignite.mm.ticketing.sqlite.database.model.AllMovieObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFMovieActivity extends SherlockActivity {
	private final static String PDF_FILE_PATH = "/sdcard/external_sd/movie_ticketing/";
	private ActionBar actionBar;
	private TextView actionBarTitle;
	private ImageButton actionBarBack;
	public static ListView lvMovie;

	
	private  ArrayList<AllMovieObject> allMovieObject;
	private String seat;
	private String SelectedSeatID;
	

	public static String MovieId, MovieTitle, DateId, Date, CinemaId, Cinema, MovieTimeId, MovieTime,SeatNo, SelectedSeat, YourSelectedSeat = "";

	//private TextView cinemaName, movieName, ticketNo, price, time , Address;
	
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

		/*
		 * actionBarProceed = (Button) actionBar.getCustomView().findViewById(
		 * R.id.action_bar_Button_Proceed);
		 */

		actionBarTitle.setText("Print");
		// actionBarProceed.setVisibility(View.INVISIBLE);

		actionBarBack.setOnClickListener(clickListener);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		Bundle bundle = getIntent().getExtras();
		MovieId = bundle.getString("movie_id");
		MovieTitle = bundle.getString("movie_title");
		DateId = bundle.getString("date_id");
		Date = bundle.getString("date");
		CinemaId = bundle.getString("cinema_id");
		Cinema = bundle.getString("cinema");
		MovieTimeId = bundle.getString("movie_time_id");
		MovieTime = bundle.getString("movie_time");
		
		SelectedSeat = bundle.getString("selected_seat");
		SelectedSeatID = bundle.getString("selected_seat_id");
		lvMovie=(ListView)findViewById(R.id.lvMovie);
		getData();
		
	}

	private void getData() {
		String[] seletedSeat = SelectedSeatID.split(",");
		allMovieObject = new ArrayList<AllMovieObject>();
		for (int i = 0; i < seletedSeat.length; i++) {
			//Log.i("","SelectedSeat :"+ SelectingSeatActivity.Seat_List.get(Integer.valueOf(seletedSeat[i])).getSeatNo());
			SeatNo = SelectingSeatActivity.Seat_List.get(Integer.valueOf(seletedSeat[i])).getSeatNo();
			allMovieObject.add ( new AllMovieObject(MovieId, MovieTitle, DateId, Date, CinemaId, Cinema, MovieTimeId, MovieTime ,SeatNo, seat));
		}		
		
		lvMovie.setAdapter(new PDFMovieAdapter(this,allMovieObject));
		
	}	
	
	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v == actionBarBack) {
				finish();
			}

		}
	};

	/* Create to folder in external sd card */
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
		IfExistPDF("ticket.pdf");
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(PDF_FILE_PATH	+ "ticket.pdf"));
			document.open();
			
			for (int i = 0; i < Integer.valueOf(Movie_Info_Activity.no_of_ticket); i++) {
				Image image1 = Image.getInstance(PDF_FILE_PATH + "ticket" + (i + 1) + ".png");
				image1.scalePercent(20);
				document.add(image1);
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		goToMail("ticket.png");
	}
	
	private void goToMail(String pathName) {
		
		File file = new File(PDF_FILE_PATH + pathName);
		Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:")); // it's
																				// not
																				// ACTION_SEND
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Ticketing ");
		intent.putExtra(Intent.EXTRA_TEXT, "");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such// that when user// returns to your app,// your app is// displayed, instead of// the email app.
		startActivity(intent);
	}

	private View ticketView;
	private Bitmap bmTicketView;

	public boolean printTicket() {
		boolean printed = false;
		/*ticketView = (View) findViewById(R.id.ticket_layout);
		ticketView.setDrawingCacheEnabled(false);
		ticketView.setDrawingCacheEnabled(true);*/
		bmTicketView =getWholeListViewItemsToBitmap();
		if (storeImage(bmTicketView, PDF_FILE_PATH, "ticket.png")) {
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
				changePDF();
				Toast.makeText(
						this,
						"Your ticket is prined to " + PDF_FILE_PATH
								+ " Directory.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "Can't printed your ticket.",
						Toast.LENGTH_LONG).show();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	        storeImage(bmp, PDF_FILE_PATH, "ticket"+(i+1)+".png");
	        bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
	        iHeight+=bmp.getHeight();

	        bmp.recycle();
	        bmp=null;
	    }


	    return bigbitmap;
	}

	
}
