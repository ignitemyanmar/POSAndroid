package com.dl.helper.printer.bt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.dl.helper.printer.BlueToothService;
import com.dl.helper.printer.BlueToothService.OnReceiveDataHandleEvent;
import com.dl.helper.printer.PrintService;
import com.dl.helper.printer.PrinterClass;
import com.ignite.mm.ticketing.PDFBusActivity;
import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.Device;

public class BtService extends PrintService implements PrinterClass {

	Context context;
	Handler mhandler, handler;
	public static BlueToothService mBTService = null;

	public BtService(Context _context, Handler _mhandler, Handler _handler) {
		context = _context;
		mhandler = _mhandler;
		handler = _handler;
		mBTService = new BlueToothService(context, mhandler);

		mBTService.setOnReceive(new OnReceiveDataHandleEvent() {
			public void OnReceive(final BluetoothDevice device) {
				// TODO Auto-generated method stub
				if (device != null) {
					Device d = new Device();
					d.deviceName = device.getName();
					d.deviceAddress = device.getAddress();
					Message msg = new Message();
					msg.what = 1;
					msg.obj = d;
					handler.sendMessage(msg);
					setState(STATE_SCANING);
				} else {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
				}
			}
		});
	}

	public boolean open(Context context) {
		// TODO Auto-generated method stub
		mBTService.OpenDevice();
		return true;
	}

	public boolean close(Context context) {
		// TODO Auto-generated method stub
		mBTService.CloseDevice();
		return false;
	}

	public void scan() {
		// TODO Auto-generated method stub
		if (!mBTService.IsOpen()) {// �ж������Ƿ��
			mBTService.OpenDevice();
			return;
		}
		if (mBTService.getState() == STATE_SCANING)
			return;

		new Thread() {
			public void run() {
				mBTService.ScanDevice();
			}
		}.start();
	}

	public boolean connect(String device) {
		// TODO Auto-generated method stub
		if (mBTService.getState() == STATE_SCANING) {
			stopScan();
		}
		if (mBTService.getState() == STATE_CONNECTING) {
			return false;
		}
		if(mBTService.getState() == STATE_CONNECTED)
		{
			mBTService.DisConnected();
		}
		mBTService.ConnectToDevice(device);// ��������
		return true;
	}

	public boolean disconnect() {
		// TODO Auto-generated method stub
		mBTService.DisConnected();
		return true;
	}

	public int getState() {
		// TODO Auto-generated method stub
		return mBTService.getState();
	}

	public boolean write(byte[] bt) {
		if(getState()!= PrinterClass.STATE_CONNECTED)
		{
			Toast toast = Toast.makeText(context, "Connection Lost!", Toast.LENGTH_SHORT);
	        toast.show();
			return false;
		}
		mBTService.write(bt);
		return true;
	}

	public boolean printText(String textStr) {
		// TODO Auto-generated method stub

			byte[] buffer = getText(textStr);
	
			if (buffer.length <= 100) {
				write(buffer);
			}
			int sendSize = 100;
			int issendfull=0;
			for (int j = 0; j < buffer.length; j += sendSize) {
	
				byte[] btPackage = new byte[sendSize];
				if (buffer.length - j < sendSize) {
					btPackage = new byte[buffer.length - j];
				}
				System.arraycopy(buffer, j, btPackage, 0, btPackage.length);
				write(btPackage);
	
				try {
					Thread.sleep(86);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				if (PrintService.isFUll) {
					Log.i("BUFFER", "BUFFER FULL");
					int index = 0;
					while (index++ < 500) {
						if (!PrintService.isFUll) {
							issendfull=0;
							Log.i("BUFFER", "BUFFER NULL"+index);
							break;
						}
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
	
			return true;
		////////return write(getText(textStr));
	}

	public boolean printImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		return write(getImage(bitmap));
		// return write(new byte[]{0x0a});
	}

	@Override
	public boolean printUnicode(String textStr) {
		// TODO Auto-generated method stub
		return write(getTextUnicode(textStr));
	}

	public boolean IsOpen() {
		// TODO Auto-generated method stub
		return mBTService.IsOpen();
	}

	public void stopScan() {
		// TODO Auto-generated method stub
		if (PDFBusActivity.printerClass.getState() == PrinterClass.STATE_SCANING) {
			mBTService.StopScan();
			mBTService.setState(PrinterClass.STATE_SCAN_STOP);
		}
	}

	public void setState(int state) {
		// TODO Auto-generated method stub
		mBTService.setState(state);
	}

	public List<Device> getDeviceList() {
		List<Device> devList = new ArrayList<Device>();
		// TODO Auto-generated method stub
		Set<BluetoothDevice> devices = mBTService.GetBondedDevice();
		for (BluetoothDevice bluetoothDevice : devices) {
			Device d = new Device();
			d.deviceName = bluetoothDevice.getName();
			d.deviceAddress = bluetoothDevice.getAddress();
			devList.add(d);
		}
		return devList;
	}
}
