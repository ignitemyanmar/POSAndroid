package com.ignite.pos.application;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class DeviceUtil {
	private static DeviceUtil instance;
	private int Width;
	private int Height;
	private Display display;
	private Point size;
	
	public DeviceUtil(Activity aty){
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
}
