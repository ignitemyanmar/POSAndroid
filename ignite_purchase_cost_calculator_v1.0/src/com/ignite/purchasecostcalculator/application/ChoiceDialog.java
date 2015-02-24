package com.ignite.purchasecostcalculator.application;

import com.ignite.purchasecostcalculator.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ChoiceDialog extends Dialog {

	private Callback mCallback;
	private TextView txt_update;
	private TextView txt_delete;

	public ChoiceDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_choice);
		//getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		txt_update = (TextView)findViewById(R.id.txt_update);
		txt_delete = (TextView)findViewById(R.id.txt_delete);
		
		txt_update.setOnClickListener(clickListener);
		txt_delete.setOnClickListener(clickListener);
		
		//getWindow().setLayout(300, 200);
		
	}
	
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == txt_update){
				
				if(mCallback != null){
					mCallback.onUpdate();
					dismiss();
				}
			}
			if(v == txt_delete){
				if(mCallback != null){
					mCallback.onDelete();
					dismiss();
				}
			}
		}
	};
	
	public void setCallbackListener(Callback mCallback){
		this.mCallback = mCallback;
	}

	public interface Callback{
		void onUpdate();
		void onDelete();
	}
}


