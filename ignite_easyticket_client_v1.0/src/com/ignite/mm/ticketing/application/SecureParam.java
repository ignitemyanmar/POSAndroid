package com.ignite.mm.ticketing.application;

public class SecureParam {
	static {
		System.loadLibrary("secureparam"); // hello.dll (Windows) or libhello.so (Unixes)
	}
	
	// Declare native method
	public static native String getAccessTokenParam(String grand_type, String app_id, String securet_key, String username, String password, String scope, String state);
	
	// Declare native method
	public static native String getSeatPlanParam(String token, String op_id,String f_c,String t_c, String c_id, String d, String t);
	
	// Declare native method
	public static native String getTripsParam(String token, String op_id);
	
	// Declare native method
	public static native String getTimesParam(String token, String op_id, String f_c, String t_c, String d);
	
    // Declare native method
 	public static native String postSaleParam(String token, String op_id, String ag_id, String name, String ph, String rmk_type, String rmk, String gp_op_id, String lst_seat, String trip_id, String date, String f_c, String t_c, String usr_id, String dev, String bking);
	
	// Declare native method
	public static native String postSaleConfirmParam(String token,String order_id, String ref_id, String ag_id, String ag_name, String name, String ph, String nrc, String rmk_type,String rmk, String ext_dest_id, String lst_seat, String cash, String nation, String or_date, String dev, String bking);

	// Declare native method
	public static native String deleteSaleOrderParam(String token);
	
	// Declare native method
	public static native String deleteAllOrderParam(String token);
	
	// Declare native method
	public static native String deleteOrderItemParam(String token, String ids);
	
	// Declare native method
	public static native String getAllAgentParam(String token, String op_id);
	
	// Declare native method
	public static native String getBookingOrderParam(String token, String op_id, String d_date, String from, String to, String times, String b_code);
	
	// Declare native method
	public static native String editSeatInfoParam(String token, String id, String date, String seat, String name, String ph, String nrc, String ticket);
	
	// Declare native method
	public static native String deleteTicketParam(String token, String id,String date, String seat,String user);
	// >>>>>>>>>>>>>>>>
	// Declare native method
	public static native String getOperatorGroupUserParam(String token, String id);
	
	// Declare native method
	public static native String getExtraDestinationParam(String token);

	// Declare native method
	public static native String getNotiBookingParam(String token, String date);
	
	// Declare native method
	public static native String getCitybyOperatorParam(String token, String id);
	
	// Declare native method
	public static native String getTimebyOperatorParam(String token, String id);
	
}
