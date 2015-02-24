package com.ignite.purchasecostcalculator.custom.view;

import android.content.Context;  
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class CustomAutocompleteTextView extends AutoCompleteTextView {

    public CustomAutocompleteTextView(Context context) {
        super(context);
    }

    public CustomAutocompleteTextView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(arg0.getAssets(),"fonts/ZawgyiOne2008.ttf"));
    }

    public CustomAutocompleteTextView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        if(!isInEditMode())
			setTypeface(Typeface.createFromAsset(arg0.getAssets(),"fonts/ZawgyiOne2008.ttf"));
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
            Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            performFiltering(getText(), 0);
        }
    }

}