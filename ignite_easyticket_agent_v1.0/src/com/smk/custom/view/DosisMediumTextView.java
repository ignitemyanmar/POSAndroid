package com.smk.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class DosisMediumTextView extends TextView{
	public DosisMediumTextView(Context context){
		super(context);
		if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/DosisMedium.ttf"));
	}
	public DosisMediumTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/DosisMedium.ttf"));
	}
	
	

}
