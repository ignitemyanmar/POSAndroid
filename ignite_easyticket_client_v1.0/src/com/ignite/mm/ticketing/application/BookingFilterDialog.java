package com.ignite.mm.ticketing.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.ignite.mm.ticketing.R;
import com.ignite.mm.ticketing.sqlite.database.model.From;
import com.ignite.mm.ticketing.sqlite.database.model.TimesbyOperator;
import com.ignite.mm.ticketing.sqlite.database.model.To;
import com.smk.calender.widget.SKCalender;
import com.smk.calender.widget.SKCalender.Callbacks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class BookingFilterDialog extends Dialog {

	private Button btn_cancel;
	private Button btn_search;
	private Callback mCallback;
	private Context ctx;
	private Button edt_date;
	private Spinner sp_from;
	private Spinner sp_to;
	private Spinner sp_time;

	public BookingFilterDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		ctx = context;
		setContentView(R.layout.dialog_booking_filter);
		edt_date = (Button) findViewById(R.id.edt_departure_date);
		edt_date.setOnClickListener(clickListener);
		sp_from = (Spinner) findViewById(R.id.sp_from);
		sp_to = (Spinner) findViewById(R.id.sp_to);
		sp_time = (Spinner) findViewById(R.id.sp_time);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		
		btn_cancel.setOnClickListener(clickListener);
		btn_search.setOnClickListener(clickListener);
		setTitle("Booking Filter");
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
			if(v == edt_date){
				final SKCalender skCalender = new SKCalender((Activity) ctx);

				  skCalender.setCallbacks(new Callbacks() {

				        public void onChooseDate(String chooseDate) {
				          // TODO Auto-generated method stub
				        	Date formatedDate = null;
				        	try {
								formatedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(chooseDate);
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	String selectedDate = DateFormat.format("yyyy-MM-dd",formatedDate).toString();
				        	edt_date.setText(selectedDate);
				        	skCalender.dismiss();
				        }
				  });

				  skCalender.show();
			}
			if(v == btn_search){
				if(mCallback != null){
					if(edt_date.getText().toString().length() != 0){
						dismiss();
						mCallback.onSave(edt_date.getText().toString(),selectedFromId, selectedToId, selectedTime);
					}else{
						edt_date.setError("Please Enter Departure Date.");
					}
					
				}
			}
		}
	};
	protected String selectedFromId;
	protected String selectedTime;
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	public void setFromCity(List<From> froms){
		
		if (froms != null) {
			ArrayAdapter<From> fromAdapter = new ArrayAdapter<From>(ctx, android.R.layout.simple_dropdown_item_1line, froms);
			sp_from.setAdapter(fromAdapter);
			sp_from.setOnItemSelectedListener(fromSelectedListener);
		}

	}
	
	private OnItemSelectedListener fromSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedFromId = ((From)arg0.getAdapter().getItem(arg2)).getId();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	protected String selectedToId;
	
	public void setToCity(List<To> to){
		
		if (to != null) {
			ArrayAdapter<To> fromAdapter = new ArrayAdapter<To>(ctx, android.R.layout.simple_dropdown_item_1line, to);
			sp_to.setAdapter(fromAdapter);
			sp_to.setOnItemSelectedListener(toSelectedListener);
		}
	}
	
	private OnItemSelectedListener toSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedToId = ((To)arg0.getAdapter().getItem(arg2)).getId();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	public void setTime(List<TimesbyOperator> time){
		
		if (time != null) {
			ArrayAdapter<TimesbyOperator> fromAdapter = new ArrayAdapter<TimesbyOperator>(ctx, android.R.layout.simple_dropdown_item_1line, time);
			sp_time.setAdapter(fromAdapter);
			sp_time.setOnItemSelectedListener(timeSelectedListener);
		}
	}
	
	private OnItemSelectedListener timeSelectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedTime = ((TimesbyOperator)arg0.getAdapter().getItem(arg2)).getTime();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	public interface Callback{
		void onSave(String date, String from_id, String to_id, String time);
		void onCancel();
	}
	
	

}
