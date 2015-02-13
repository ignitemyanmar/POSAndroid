package com.smk.skscalableview;

import com.ignite.pos.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.TextView;

public class ScalableTextView extends TextView {

	private Display display;
	private Point size;
	private int VWidth, VHeight;
	private int AttriWidth, AttriHeith;
	private int ScaleWidth = 0 ;
	private int ScaleHeight = 0;

	public ScalableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		       
		if(!isInEditMode()){
			setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/ZawgyiOne2008.ttf"));
			//return;
		}

		this.display = ((Activity) context).getWindowManager().getDefaultDisplay();
		this.size = getDisplaySize(display);
		VWidth = this.size.x;
		VHeight = this.size.y;
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomLinearLayout);
        
        AttriWidth = a.getInt(R.styleable.CustomLinearLayout_scale_width, 0);
        AttriHeith = a.getInt(R.styleable.CustomLinearLayout_scale_height, 0);
        
        if(AttriWidth != 0)
        	ScaleWidth = VWidth / AttriWidth;
        
        if(AttriHeith != 0)
        	ScaleHeight = VHeight / AttriHeith;
        
        a.recycle();
        
	}
	
	@Override
	protected void onMeasure(int VWidthMeasureSpec, int VHeightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(VWidthMeasureSpec, VHeightMeasureSpec);
		this.setMeasuredDimension(getScaleWidth() != 0 ? getScaleWidth() : VWidthMeasureSpec, getScaleHeight() != 0 ? getScaleHeight() : VHeightMeasureSpec);
	}
	
	public int getScaleWidth() {
		return ScaleWidth;
	}

	public void setScaleWidth(int VScaleWidth) {
		ScaleWidth = VScaleWidth;
		invalidate();
		requestLayout();

	}

	public int getScaleHeight() {
		return ScaleHeight;
	}

	public void setScaleHeight(int VScaleHeight) {
		ScaleHeight = VScaleHeight;
		invalidate();
		requestLayout();

	}

	@SuppressWarnings("deprecation")
	private Point getDisplaySize(final Display display) {
	    final Point point = new Point();
	    try {
	        display.getSize(point);
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        point.x = display.getWidth();
	        point.y = display.getHeight();
	    }
	    return point;
	}

}
