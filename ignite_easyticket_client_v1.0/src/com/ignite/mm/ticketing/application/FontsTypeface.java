package com.ignite.mm.ticketing.application;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontsTypeface {
	private Context ctx;
		
	public FontsTypeface(Context ctx) {
		this.ctx = ctx;
	}
	public void setTypeface(TextView tv, String style){
		Typeface tf = null;
		if(style.equals("Bold")){
		}
		if(style.equals("BoldItalic")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-BoldItalic.ttf");
		}
		if(style.equals("ExtraBold")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-ExtraBold.ttf");
		}
		if(style.equals("ExtraBoldItalic")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-ExtraBoldItalic.ttf");
		}
		if(style.equals("Italic")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-Italic.ttf");
		}
		if(style.equals("Light")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-Light.ttf");
		}
		if(style.equals("LightItalic")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-LightItalic.ttf");
		}
		if(style.equals("Regular")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-Regular.ttf");
		}
		if(style.equals("Semibold")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-Semibold.ttf");
		}
		if(style.equals("SemiboldItalic")){
			tf = Typeface.createFromAsset(ctx.getAssets(),"fonts/OpenSans-SemiboldItalic.ttf");
		}
		
		tv.setTypeface(tf);
		
	}
}
