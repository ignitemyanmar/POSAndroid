package com.ignite.mm.ticketing.application;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;

public class DeviceUtil {
	private static DeviceUtil instance;
	private int Width;
	private int Height;
	private Display display;
	private Point size;
	private String ID = null;
	private Context context;
	
	public DeviceUtil(Activity aty){
		context = aty.getApplicationContext();
		this.display = aty.getWindowManager().getDefaultDisplay();
		this.size = getDisplaySize(display);
		setWidth(this.size.x);
		setHeight(this.size.y);
	}
	
	public static DeviceUtil getInstance(Activity mActivity) {
    	if(instance == null){
    		instance = new DeviceUtil(mActivity);
		}
		return instance;
	}
	
	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}

	@SuppressWarnings("deprecation")
	private Point getDisplaySize(final Display display) {
	    final Point point = new Point();
	    try {
	        display.getSize(point);
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        point.x = display.getWidth();
	        point.y = display.getHeight();
	    }
	    return point;
	}
	
	// return a cached unique ID for each device
	public String getID() {
		// if the ID isn't cached inside the class itself
		if (ID == null) {
			//get it from database / settings table (implement your own method here)
			SharedPreferences pref = context.getApplicationContext().getSharedPreferences("Device", Activity.MODE_PRIVATE);
			ID = pref.getString("DeviceID", "0");
		}
 
		// if the saved value was incorrect
		if (ID.equals("0")) {
			// generate a new ID
			ID = generateID();
 
			if (ID != null) {
				// save it to database / setting (implement your own method here)
				SharedPreferences sharedPreferences = context.getSharedPreferences("Device", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("DeviceID",ID);
				editor.commit();
			}
		}
		
		return ID;
	}

	// generate a unique ID for each device
	// use available schemes if possible / generate a random signature instead 
	private String generateID() {
		// use the ANDROID_ID constant, generated at the first device boot
		String deviceId = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
		Log.i("","Physical Device ID = "+deviceId);
		// in case known problems are occured
		if ("9774d56d682e549c".equals(deviceId) || deviceId == null) {

			// get a unique deviceID like IMEI for GSM or ESN for CDMA phones
			// don't forget:
			// <uses-permission android:name="android.permission.READ_PHONE_STATE" />
			deviceId = ((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();

			// if nothing else works, generate a random number
			if (deviceId == null) {

				Random tmpRand = new Random();
				deviceId = String.valueOf(tmpRand.nextLong());
			}
			
			// any value is hashed to have consistent format
		}
		
		return getHash(deviceId);
	}

	// generates a SHA-1 hash for any string
	private String getHash(String stringToHash) {

	 	MessageDigest digest = null;
			try {
				digest = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
	
	 	byte[] result = null;
	
			try {
				result = digest.digest(stringToHash.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	
	 	StringBuilder sb = new StringBuilder();
	
	 	for (byte b : result)
	 	{
	 	    sb.append(String.format("%02X", b));
	 	}
	
	 	String messageDigest = sb.toString();
	 	return messageDigest;
	}
}
