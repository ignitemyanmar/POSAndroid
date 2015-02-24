package com.ignite.purchasecostcalculator.application;

import com.ignite.purchasecostcalculator.R;
import com.ignite.purchasecostcalculator.ThaiToKyatDetailActivity;
import com.ignite.purchasecostcalculator.adapter.DetailLvAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends Activity {

	public static ActionBar actionbar;
	private Callback mCallback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionbar = getActionBar();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//EasyTracker.getInstance().activityStart(this);
	}
	@Override
	public void onStop() {
	    super.onStop();
	   // The rest of your onStop() code.
	    //EasyTracker.getInstance().activityStop(this);  // Add this method.
	}
	
	public void alertDialog(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Info");
		alert.setMessage(message+"!");
		alert.show();
		alert.setCancelable(true);
	}
	
	/**
	 * Remove Item from List View
	 * @param position ListView's Item ID you want to delete
	 * @param selected_item_name Name you want to delete
	 */
    protected void removeItemFromList(int position, String selected_item_name) {
    	
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        
		View dialogView = View.inflate(this, R.layout.list_item_choice_dialog, null);
		TextView dialogTitle = (TextView) dialogView.findViewById(R.id.txt_choice);
		dialogTitle.setText("Are you sure to delete - "+selected_item_name+" ?");
		alert.setCustomTitle(dialogView);        
        
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.onDelete();
				}
			}
		});
       
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
      
        alert.show();
        alert.setCancelable(true);
    }

	public void setCallbackListener(Callback mCallback) {
		this.mCallback = mCallback;
	}

	public interface Callback {
    	void onDelete();
    }
}
