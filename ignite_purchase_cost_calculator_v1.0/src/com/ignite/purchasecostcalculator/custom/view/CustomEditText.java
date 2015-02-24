package com.ignite.purchasecostcalculator.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends EditText{
	public CustomEditText(Context context){
		super(context);
		if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Zawgyi-One.ttf"));
	}
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Zawgyi-One.ttf"));
	}
	
	

}
