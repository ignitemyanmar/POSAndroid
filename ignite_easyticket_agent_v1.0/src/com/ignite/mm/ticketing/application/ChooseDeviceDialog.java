package com.ignite.mm.ticketing.application;


import com.ignite.mm.ticketing.client.PDFBusActivity;
import com.ignite.mm.ticketing.client.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseDeviceDialog extends Dialog {

	public EditText edt_item_name;
	public EditText edt_purchase_price;
	public EditText edt_transport_cost;
	public EditText edt_other_cost;
	private Button btn_cancel;
	private Button btn_update;
	private Callback mCallback;
	private Button btn_search_device;
	private TextView txt_status;
	private ListView lv_device;
	private Button btn_print;
	private TextView txt_bluetooth;
	private TextView txt_wifi;
	
	//Check Device connected
	public static boolean checkState=true;
	private Thread tv_update;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	Handler mhandler=null;
	Handler handler = null;

	public ChooseDeviceDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_choose_device);
		
/*		txt_bluetooth = (TextView)findViewById(R.id.txt_bluetooth);
		txt_wifi = (TextView)findViewById(R.id.txt_wifi);*/
		btn_search_device = (Button)findViewById(R.id.btn_search_device);
		txt_status = (TextView)findViewById(R.id.txt_status);
		lv_device = (ListView)findViewById(R.id.lv_device);
		btn_print = (Button)findViewById(R.id.btn_print);
		
/*		txt_bluetooth.setOnClickListener(clickListener);
		txt_wifi.setOnClickListener(clickListener);*/
		
		btn_search_device.setOnClickListener(clickListener);
		btn_print.setOnClickListener(clickListener);
		
		setTitle("Print - Code No: "+PDFBusActivity.Bar_Code_No);		
		
	}


	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
/*			if (v == txt_bluetooth) {
				txt_bluetooth.setBackgroundResource(R.color.golden_brown_light2);
				txt_wifi.setBackgroundResource(R.color.gray_light);
				
				
			}
			if (v == txt_wifi) {
				txt_wifi.setBackgroundResource(R.color.golden_brown_light2);
				txt_bluetooth.setBackgroundResource(R.color.gray_light);
			}*/
			if(v == btn_search_device){
				
				txt_status.setVisibility(View.VISIBLE);
				txt_status.setText("Scanning, pls wait ... ");
			}
			if(v == btn_print){
/*				if(mCallback != null){
					
					String itemName = edt_item_name.getText().toString();						
					Double purchasePrice = Double.valueOf(edt_purchase_price.getText().toString());
					Double transportCost = Double.valueOf(edt_transport_cost.getText().toString());
					Double otherCost;
					if (edt_other_cost.getText().toString().equals("")) {
						String others = "0";
						otherCost = Double.valueOf(others);
					}else {
						otherCost = Double.valueOf(edt_other_cost.getText().toString());
					}
					
					//mCallback.onUpdate(itemName, purchasePrice, transportCost, otherCost);
					dismiss();
				}*/
			}
		}
	};
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}

	public interface Callback{
		void onPrint();
	}

}


