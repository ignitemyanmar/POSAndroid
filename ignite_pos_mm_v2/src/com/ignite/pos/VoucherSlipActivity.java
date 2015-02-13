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
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.RemoteControlClient;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.ignite.pos.adapter.SaleVoucherSlipAdapter;
import com.ignite.pos.adapter.VoucherSlipListViewAdapter;
import com.ignite.pos.model.BundleListObjet;
import com.ignite.pos.model.SaleVouncher;
import com.smk.skalertmessage.SKToastMessage;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucher_slip);
		
		setData();
		
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
				SKToastMessage.showMessage(VoucherSlipActivity.this, "Print not set up..!!", SKToastMessage.ERROR);
				
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
		//txt_buyer.setText(saleVoucherList.get(0).getCusname());
		
		//Footer View
		invoiceFooter = LayoutInflater.from(this).inflate(R.layout.activity_sale_slip_footer, null, false);
		txt_grand_total = (TextView) invoiceFooter.findViewById(R.id.txt_grand_total);
		txt_discount = (TextView) invoiceFooter.findViewById(R.id.txt_discount);
		txt_net_amount = (TextView) invoiceFooter.findViewById(R.id.txt_net_amount);
		
/*		Integer grandTotal = 0; 
		
		for (int i = 0; i < saleVoucherList.size(); i++) {
			
			grandTotal += Integer.valueOf(saleVoucherList.get(i).getItemtotal());
		}*/
		
		txt_grand_total.setText(saleVoucherList.get(0).getTotal());
		//txt_discount.setText(String.valueOf((grandTotal * Integer.valueOf(saleVoucherList.get(0).getDiscount())) / 100)+"");
		txt_discount.setText(saleVoucherList.get(0).getDiscount());
		
		if (saleVoucherList.get(0).getDiscount() == null) {
			
			Integer discount = 0;
			Integer net_amt = Integer.valueOf(saleVoucherList.get(0).getTotal()) - discount;
			txt_net_amount.setText(net_amt+"");
		}else {
			
			Integer net_amt = Integer.valueOf(saleVoucherList.get(0).getTotal()) - Integer.valueOf(saleVoucherList.get(0).getDiscount());
			txt_net_amount.setText(net_amt+"");
		}		
		
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
	    
	    storeImage(bigbitmap, PRINT_FILE_PATH, "SaleVoucher.png");
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
}
