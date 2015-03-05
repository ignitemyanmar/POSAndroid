package com.ignite.mm.ticketing.application;

import com.ignite.mm.ticketing.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ListView;

public class BluetoothDeviceDialog extends Dialog {
	private ListView lstDevice;

	public BluetoothDeviceDialog(Context context) {
		super(context);
		setTitle("Please Select Device");
		setContentView(R.layout.dialog_bluetooth_device);
	}
	public ListView getListView(){
		lstDevice = (ListView)findViewById(R.id.lst_devices);
		return lstDevice;
	}
	

}
