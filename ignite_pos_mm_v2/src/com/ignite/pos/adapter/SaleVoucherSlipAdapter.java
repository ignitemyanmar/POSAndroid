package com.ignite.pos.adapter;

import java.io.FileOutputStream;
import java.io.IOException;
import com.ignite.pos.VoucherSlipActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import android.view.LayoutInflater;

@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.KITKAT)
public class SaleVoucherSlipAdapter extends PrintDocumentAdapter {

	Context context;
	private int pageHeight;
	private int pageWidth;
	public PdfDocument myPdfDocument; 
	public int totalpages = 1;
	private LayoutInflater mInflater;
	private int pageCount;
	
	public SaleVoucherSlipAdapter(Context context)
	{
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	@Override
	public void onLayout(PrintAttributes oldAttributes,
	                 PrintAttributes newAttributes,
	                 CancellationSignal cancellationSignal,
	                 LayoutResultCallback callback,
	                 Bundle metadata) {
		
		// Create the PDF document we'll use later
		myPdfDocument = new PrintedPdfDocument(context, newAttributes);
	    
		pageHeight = newAttributes.getMediaSize().getHeightMils()/1000 * 72;
		pageWidth  = newAttributes.getMediaSize().getWidthMils()/1000 * 72;
		
		if (cancellationSignal.isCanceled() ) {
			callback.onLayoutCancelled();
			return;
		}
		
	    // Prepare the layout.
	    int newPageCount;
	    if(newAttributes.getMediaSize().getHeightMils() < 1000) {
	        newPageCount = 2;
	    } else {
	        newPageCount = 1;
	    }
			  
	    // Has the layout actually changed?
	    boolean layoutChanged = newPageCount != pageCount;
	    pageCount = newPageCount;
	    
	    // Create the doc info to return
		if (totalpages > 0) {
		   PrintDocumentInfo.Builder builder = new PrintDocumentInfo
			  .Builder("print_output.pdf") 
			  .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
			  .setPageCount(pageCount);
			                
		   PrintDocumentInfo info = builder.build();
		   callback.onLayoutFinished(info, layoutChanged);
		} else {
		   callback.onLayoutFailed("Page count is zero.");
		}
	}
	
	
	@Override
	public void onWrite(final PageRange[] pageRanges,
	              final ParcelFileDescriptor destination,
	              final CancellationSignal cancellationSignal,
	              final WriteResultCallback callback) {
		
		// Iterate through the pages
		for (int currentPageNumber = 0; currentPageNumber  < pageCount; currentPageNumber++) {
			
			if (pageInRange(pageRanges, currentPageNumber))
		   	{
			     PageInfo newPage = new PageInfo.Builder(pageWidth, 
	                         pageHeight, currentPageNumber).create();
			    	
			     PdfDocument.Page page = 
	                          myPdfDocument.startPage(newPage);

			     if (cancellationSignal.isCanceled()) {
				  callback.onWriteCancelled();
				  myPdfDocument.close();
				  myPdfDocument = null;
				  return;
			     }

			     // Draw on the page
			     drawPage(page, currentPageNumber);

			     // Finish the page
			     myPdfDocument.finishPage(page);
			}
		}
		    
		try {
			myPdfDocument.writeTo(new FileOutputStream(
			            destination.getFileDescriptor()));
		} catch (IOException e) {
			callback.onWriteFailed(e.toString());
			return;
		} finally {
			myPdfDocument.close();
			myPdfDocument = null;
		}

		callback.onWriteFinished(pageRanges);
	}
	
	//Check page is in specified range or not
	private boolean pageInRange(PageRange[] pageRanges, int pageNumber)
	{
		for (int i = 0; i<pageRanges.length; i++)
		{
			if ((pageNumber >= pageRanges[i].getStart()) && 
                    	                 (pageNumber <= pageRanges[i].getEnd()))
				return true;
		}
		return false;
	}
	
	//Draw PDF for each page
	private void drawPage(PdfDocument.Page page, 
            int pagenumber) {
		
		Canvas canvas = page.getCanvas();

		pagenumber++; // Make sure page numbers start at 1

        // We're putting everything on one page
        Rect imageRect = new Rect(10, 10, canvas.getWidth() - 10, canvas.getHeight() - 10);
        drawImage(VoucherSlipActivity.getWholeListViewItemsToBitmap(), canvas, imageRect);
        
	}
	
	private void drawImage(Bitmap image, Canvas canvas, Rect r) {
		
        if (image != null && !image.isRecycled()) {
    	
        	canvas.drawBitmap(image, null, r, new Paint());
	        
        	//image.recycle();
        	//image = null; 
        }else {
        	Log.i("", "Bitmap is already recycled!");
        }
	}

}
