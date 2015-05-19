package com.ignite.mm.ticketing.application;

import com.ignite.mm.ticketing.agent.BusOperatorActivity;
import com.ignite.mm.ticketing.agent.PDFBusActivity;
import com.ignite.mm.ticketing.agent.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BluetoothDeviceDialog extends Dialog {
	private ListView lstDevice;
	private Callback mCallback;

	public BluetoothDeviceDialog(Context context) {
		super(context);
		setTitle("Please Select Device");
		setContentView(R.layout.dialog_bluetooth_device);
		getListView().setOnItemClickListener(clicklistener);
	}
	
	private OnItemClickListener clicklistener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.onDeviceChoose(position);
				dismiss();
				
			}
		}
	};
	
	public ListView getListView(){
		lstDevice = (ListView)findViewById(R.id.lst_devices);
		return lstDevice;
	}
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	public interface Callback{
		void onDeviceChoose(int position);
	}
}
