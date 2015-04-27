package com.ignite.mm.ticketing.application;

public class SecureKey {
	static {
		System.loadLibrary("securekey"); // hello.dll (Windows) or libhello.so (Unixes)
	}
	
	// Declare native method
	public static native String getGrant();
	
	// Declare native method
	public static native String getId();
	 
	// Declare native method
	public static native String getKey();
	
	// Declare native method
	public static native String getScope();
		 
	// Declare native method
	public static native String getState();
	
	// Declare native method
	public static native String getPEMKey();
	
	// Declare native method
	public static native String getIV();
	
	// Declare native method
	public static native String getAESKey();
}
