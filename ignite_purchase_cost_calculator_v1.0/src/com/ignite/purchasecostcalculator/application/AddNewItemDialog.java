package com.ignite.purchasecostcalculator.application;

import com.ignite.purchasecostcalculator.ChinaToKyatDetailActivity;
import com.ignite.purchasecostcalculator.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewItemDialog extends Dialog {

	public EditText edt_item_name;
	private EditText edt_purchase_price;
	private EditText edt_transport_cost;
	private EditText edt_other_cost;
	private Button btn_cancel;
	private Button btn_save;
	private Callback mCallback;

	public AddNewItemDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_add_new_item);
		
		edt_item_name = (EditText)findViewById(R.id.edt_item_name);
		edt_purchase_price = (EditText)findViewById(R.id.edt_purchase_price);
		edt_transport_cost = (EditText)findViewById(R.id.edt_transport_cost);
		edt_other_cost = (EditText)findViewById(R.id.edt_other_cost);
		
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		btn_save = (Button)findViewById(R.id.btn_save);
		
		btn_cancel.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		
		setTitle("Add New Item (China)");

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
					
					if (checkField()) {
						
						String itemName = edt_item_name.getText().toString();						
						Double purchasePrice = Double.valueOf(edt_purchase_price.getText().toString());
						Double transportCost = Double.valueOf(edt_transport_cost.getText().toString());
						Double otherCost = Double.valueOf(edt_other_cost.getText().toString());
						
						mCallback.onSave(itemName, purchasePrice, transportCost, otherCost);
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
		if (edt_item_name.getText().toString().length() == 0) {
			edt_item_name.setError("Enter Item Name");
			return false;
		}
		if (edt_purchase_price.getText().toString().length() == 0) {
			edt_purchase_price.setError("Enter Purchase Price");
			return false;
		}
		if (edt_transport_cost.getText().toString().length() == 0) {
			edt_transport_cost.setError("Enter Transport Cost");
			return false;
		}
		if (edt_other_cost.getText().toString().length() == 0) {
			edt_other_cost.setError("Enter Other Costs");
			return false;
		}
		return true;
	}

	public interface Callback{
		void onSave(String itemName, Double purchasePrice, Double transportCost, Double otherCost);
		void onCancel();
	}

}


