package com.ignite.mm.ticketing.application;

import com.ignite.mm.ticketing.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookingCodeDialog extends Dialog {

	private Button btn_cancel;
	private Button btn_save;
	private Callback mCallback;
	private EditText edt_book_code;

	public BookingCodeDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_booking_code);
		edt_book_code = (EditText) findViewById(R.id.edt_book_code);
		
		btn_save = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		setTitle("Booking Code");
	}
	
		
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_cancel){
				if(mCallback != null){
					dismiss();
					mCallback.onCancel();
				}
			}
			if(v == btn_save){
				if(mCallback != null){
					mCallback.onSearch(edt_book_code.getText().toString());					
				}
			}
		}
	};

	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	
	public interface Callback{
		void onSearch(String code);
		void onCancel();
	}

}
