package com.ignite.mm.ticketing.connection.detector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
 
public class ConnectionDetector {
     
    private Context _context;
     
    public ConnectionDetector(Context context){
        this._context = context;
    }
 
    public boolean isConnectingToInternet(){
    	boolean status = false;
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                    	  status = true;
                      }
 
          }
          return status;
    }
    public void showErrorDialog(){
    	showAlertDialog(this._context);
    }

	public void showAlertDialog(Context context) {
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
      	 
        // Setting Dialog Title
        alertDialog.setTitle("No Internet Connection.");
 
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	_context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    	
    }
}
