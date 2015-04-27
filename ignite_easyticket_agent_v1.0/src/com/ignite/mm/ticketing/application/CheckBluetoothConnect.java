package com.ignite.mm.ticketing.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ignite.mm.ticketing.agent.PDFBusActivity;
import com.ignite.mm.ticketing.sqlite.database.model.Device;
import com.zkc.helper.printer.PrintService;
import com.zkc.helper.printer.PrinterClass;
import com.zkc.helper.printer.PrinterClassFactory;

public class CheckBluetoothConnect extends Activity {

	//Check Device connected
	public static boolean checkState = true;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	Handler mhandler=null;
	Handler handler = null;
	
	private void oncreate() {
		// TODO Auto-generated method stub

		checkBluetoothConnect();
	}
	
	public void checkBluetoothConnect() {
		// TODO Auto-generated method stub
		
		mhandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MESSAGE_READ:
					 byte[] readBuf = (byte[]) msg.obj;
					 Log.i("", "readBuf:"+readBuf[0]);
					 if(readBuf[0]==0x13)
					 {
						 PrintService.isFUll=true;
					 }
					 else if(readBuf[0]==0x11)
					 {
						 PrintService.isFUll=false;
					 }
					 else{
		                // construct a string from the valid bytes in the buffer
		                String readMessage = new String(readBuf, 0, msg.arg1);
		                /*Toast.makeText(CheckBluetoothConnect.this,readMessage,
	                               Toast.LENGTH_SHORT).show();*/
					 }
					break;
				case MESSAGE_STATE_CHANGE:// è“�ç‰™è¿žæŽ¥çŠ¶
					switch (msg.arg1) {
					case PrinterClass.STATE_CONNECTED:// å·²ç»�è¿žæŽ¥
						break;
					case PrinterClass.STATE_CONNECTING:// æ­£åœ¨è¿žæŽ¥
						break;
					case PrinterClass.STATE_LISTEN:
					case PrinterClass.STATE_NONE:
						break;
					case PrinterClass.SUCCESS_CONNECT:
						break;
					case PrinterClass.FAILED_CONNECT:
						break;
					case PrinterClass.LOSE_CONNECT:
						Log.i("", "LOSE_CONNECT");
					}
					break;
				case MESSAGE_WRITE:

					break;
				}
				super.handleMessage(msg);
			}
		};
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					break;
				case 1:// æ‰«æ��å®Œæ¯•
					Device d=(Device)msg.obj;
					if(d!=null)
					{
						if(PDFBusActivity.deviceList == null)
						{
							PDFBusActivity.deviceList = new ArrayList<Device>();
						}
						
						if(!checkData(PDFBusActivity.deviceList, d))
						{
							PDFBusActivity.deviceList.add(d);
							Log.i("", "Devlice list: "+PDFBusActivity.deviceList.toString());
						}
					}
					break;
				case 2:// å�œæ­¢æ‰«æ��
					break;
				}
			}
		};
		
		if(checkState)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (PDFBusActivity.printerClass != null) {
				if (PDFBusActivity.printerClass.getState() == PrinterClass.STATE_CONNECTED) {
					Toast.makeText(CheckBluetoothConnect.this, "connected device", Toast.LENGTH_LONG).show();	
					//CheckBluetoothConnect.checkState=true;
				}else if (PDFBusActivity.printerClass.getState() == PrinterClass.STATE_CONNECTING) {
					
					Toast.makeText(CheckBluetoothConnect.this, "connecting device ...", Toast.LENGTH_LONG).show();
					
				}else if(PDFBusActivity.printerClass.getState() == PrinterClass.LOSE_CONNECT
						|| PDFBusActivity.printerClass.getState() == PrinterClass.FAILED_CONNECT){
					
					checkState = false;
					Toast.makeText(CheckBluetoothConnect.this, "Not connect device yet!", Toast.LENGTH_LONG).show();
					
				}else{
					checkState = false;
					Toast.makeText(CheckBluetoothConnect.this, "Not connect device yet!", Toast.LENGTH_LONG).show();
				}
			}
		}
		
		//Connect Bluetooth Service
		try {
			PDFBusActivity.printerClass = PrinterClassFactory.create(0, this, mhandler, handler);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(CheckBluetoothConnect.this, "Fail Bluetooth Service!", Toast.LENGTH_SHORT);
		}
		
	}
	
    private boolean checkData(List<Device> list,Device d)
    {
    	for (Device device : list) {
			if(device.getDeviceAddress().equals(d.getDeviceAddress()))
			{
				return true;
			}
		}
    	return false;
    } 
    
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	checkState=true;
    	super.onRestart();
    }
}
