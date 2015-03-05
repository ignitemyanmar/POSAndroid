package com.smk.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button{
	public CustomButton(Context context){
		super(context);
		if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/ZawgyiOne2008.ttf"));
	}
	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/ZawgyiOne2008.ttf"));
	}
	
	

}
