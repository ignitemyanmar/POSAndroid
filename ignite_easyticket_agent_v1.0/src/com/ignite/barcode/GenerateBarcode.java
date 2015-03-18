package com.ignite.barcode;

import java.util.EnumMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public class GenerateBarcode {
	/**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     * 
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */
	private int bitmapWidth;
	private int bitmapHeight;
	private String barcode_data;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    
    public GenerateBarcode() {
		super();
	}

	public GenerateBarcode(String data,int width,int height){
    	this.barcode_data = data;
    	this.bitmapWidth = width;
    	this.bitmapHeight = height;
    }
    
    public Bitmap generateBarCode() throws WriterException{
		com.google.zxing.Writer c9 = new Code128Writer();	
		BitMatrix bm = c9.encode(barcode_data,BarcodeFormat.CODE_128,bitmapWidth, bitmapHeight);
		Bitmap ImageBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Config.ARGB_8888);
		
		for (int i = 0; i < bitmapWidth; i++) {
			for (int j = 0; j < bitmapHeight; j++) {
				
				ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
			}
		}

		return ImageBitmap;
    }

    public Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
    String contentsToEncode = contents;
    if (contentsToEncode == null) {
        return null;
    }
    Map<EncodeHintType, Object> hints = null;
    String encoding = guessAppropriateEncoding(contentsToEncode);
    if (encoding != null) {
        hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, encoding);
    }
    MultiFormatWriter writer = new MultiFormatWriter();
    BitMatrix result;
    try {
        result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
    } catch (IllegalArgumentException iae) {
        // Unsupported format
        return null;
    }
    int width = result.getWidth();
    int height = result.getHeight();
    int[] pixels = new int[width * height];
    for (int y = 0; y < height; y++) {
        int offset = y * width;
        for (int x = 0; x < width; x++) {
        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
        }
    }

    Bitmap bitmap = Bitmap.createBitmap(width, height,
        Bitmap.Config.ARGB_8888);
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
    return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
    // Very crude at the moment
    for (int i = 0; i < contents.length(); i++) {
        if (contents.charAt(i) > 0xFF) {
        return "UTF-8";
        }
    }
    return null;
    }
}
