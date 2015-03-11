package com.ignite.pos;

import java.util.ArrayList;
import java.util.List;
import com.ignite.pos.database.controller.BusTicketSaleController;
import com.ignite.pos.database.util.DatabaseManager;
import com.ignite.pos.model.BusTicketSale;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

public class BusTicketDataActivity extends Activity{

	private List<Object> busTicketList;
	private DatabaseManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getBusTicket(); //Get Bus Ticket Sale data & Save to DB
	}

	/**
	 *  Get Bus Ticket Sale Data
	 */
	private void getBusTicket() {
		// TODO Auto-generated method stub
		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		
		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				saveBusTicket(intent);
			}else {
				Log.i("", "Data is not Text!");
			}
		}else {
			Log.i("", "Data Can't receive!");
		}
	}

	private void saveBusTicket(Intent intent) {
		// TODO Auto-generated method stub
		
		Bundle bundle = intent.getExtras();
		
		if (bundle != null) {
			String BarcodeNo = bundle.getString("sale_order_no");
			String CustomerName = bundle.getString("CustomerName");
			String OperatorName = bundle.getString("Operator_Name");
			String Trip = bundle.getString("Bus_Trip");
			String Trip_Date = bundle.getString("Trip_Date");
			String Trip_Time = bundle.getString("Trip_Time");
			String Bus_Class = bundle.getString("Bus_Class");
			String Selected_Seats = bundle.getString("Selected_Seats");
			String SeatCount = bundle.getString("SeatCount");
			String Price = bundle.getString("Price");
			String ConfirmDate = bundle.getString("ConfirmDate");
			
			Log.i("", "rBarcode: "+BarcodeNo+", rCustomerName: "+CustomerName+", rOperator: "+OperatorName);
			
			busTicketList = new ArrayList<Object>();
		    busTicketList.add(new BusTicketSale(BarcodeNo, CustomerName, OperatorName, Trip
		    		, Trip_Date, Trip_Time, Bus_Class, Selected_Seats, Integer.valueOf(SeatCount), Integer.valueOf(Price), ConfirmDate));
		}

        Log.i("", "Bus Ticket List Recevie: "+busTicketList.toString());
        
        if (busTicketList != null && busTicketList.size() > 0) {
        	dbManager = new BusTicketSaleController(BusTicketDataActivity.this);
        	BusTicketSaleController busControl = (BusTicketSaleController)dbManager;
    		busControl.save(busTicketList);
    		finish();
    		//setOrientation();
		}
	}
	
	protected void setOrientation() {
		
	    int current = getRequestedOrientation();
	    Log.i("", "Check Orientation: "+current);
	    // only switch the orientation if not in portrait
	    if ( current != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ) {
	    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
	        finish();
	    }
	  
	}
}
