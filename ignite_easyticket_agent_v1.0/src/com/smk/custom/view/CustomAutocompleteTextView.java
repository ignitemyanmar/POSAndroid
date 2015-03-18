package com.smk.custom.view;

import android.content.Context;  
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class CustomAutocompleteTextView extends AutoCompleteTextView {

    public CustomAutocompleteTextView(Context context) {
        super(context);
    }

    public CustomAutocompleteTextView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public CustomAutocompleteTextView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
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
        	try{
        		performFiltering(getText(), 0);
        	}catch(NullPointerException e){
        		//e.printStackTrace();
        	}
        }
    }

}