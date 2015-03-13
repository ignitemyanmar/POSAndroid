package com.dl.helper.printer;

import android.util.Log;

public class PrinterLib {
	
    static
    {
        try
        {
            // Load necessary library.
        	System.loadLibrary("printer");
        }
        catch(UnsatisfiedLinkError e)
        {
            Log.i("", "Native code library error!");
            e.printStackTrace();
        }
    }

	
	public static native byte[] getBitmapData(int[] bitdata,int w,int h);
	
	public static native int[] imgToGray(int[] buf, int w, int h);
}
