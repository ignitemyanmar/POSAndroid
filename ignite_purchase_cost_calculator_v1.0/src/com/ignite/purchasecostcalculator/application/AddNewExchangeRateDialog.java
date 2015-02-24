package com.ignite.purchasecostcalculator.application;

import com.ignite.purchasecostcalculator.ChinaToKyatDetailActivity;
import com.ignite.purchasecostcalculator.R;
import com.ignite.purchasecostcalculator.model.ExchangeRate;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewExchangeRateDialog extends Dialog {

	private Button btn_cancel;
	private Callback mCallback;
	public TextView txt_current_rate;
	public EditText edt_new_rate;
	private Button btn_rate_update;

	public AddNewExchangeRateDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_add_new_exchange_rate);
		
		txt_current_rate = (TextView)findViewById(R.id.txt_current_rate);
		txt_current_rate.setText("Old Rate:   "+String.format("%.5f", ChinaToKyatDetailActivity.chinaExRate));
		edt_new_rate = (EditText)findViewById(R.id.edt_other_cost);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		btn_rate_update = (Button)findViewById(R.id.btn_update);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_rate_update.setOnClickListener(clickListener);
		
		setTitle("Add New (China) Exchange Rate");

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
			if(v == btn_rate_update){
				if(mCallback != null){
					
					if (checkField()) {
						Double newRate = Double.valueOf(edt_new_rate.getText().toString());
						
						mCallback.onUpdate(newRate);
						dismiss();
					}

				}
			}
		}
	};
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}
	
	protected boolean checkField() {
		// TODO Auto-generated method stub
		if (edt_new_rate.getText().toString().length() == 0) {
			edt_new_rate.setError("Enter New Exchange Rate");
			return false;
		}
		if (Double.valueOf(edt_new_rate.getText().toString()) == 0) {
			edt_new_rate.setError("Check Exchange Rate");
			return false;
		}
		return true;
	}

	public interface Callback{
		void onUpdate(Double newRate);
		void onCancel();
	}
	
	

}