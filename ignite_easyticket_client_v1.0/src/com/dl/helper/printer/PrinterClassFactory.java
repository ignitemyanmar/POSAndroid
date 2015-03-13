package com.dl.helper.printer;

import com.dl.helper.printer.bt.BtService;

import android.content.Context;
import android.os.Handler;

public class PrinterClassFactory {
	public static PrinterClass create(int type,Context _context,Handler _mhandler,Handler _handler){
        if(type==0){
               return new BtService(_context,_mhandler, _handler); 
        }else if(type==1){
             // return new WifiService(_context,_mhandler, _handler); 
        }
		return null;
  }

}
