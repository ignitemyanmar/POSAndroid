package com.smk.activate;

public class Activation {
	static {
		System.loadLibrary("activation"); //
	}
	
	// Declare native method
	public static native boolean activate(long code, long time);
}
