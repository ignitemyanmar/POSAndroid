package com.ignite.mm.ticketing.application;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.zip.GZIPInputStream;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;

public class DecompressGZIP {
	@SuppressWarnings("deprecation")
	public static <T> T fromBody (TypedInput body, Type type)
		  {
		    String charset = "UTF-8";
		    if (body.mimeType() != null) {
		      charset = MimeUtil.parseCharset(body.mimeType());
		    }
		    InputStreamReader reader = null;
	        try {
	        	//GZIPInputStream gzis = new GZIPInputStream(body.in());
	        	reader = new InputStreamReader(body.in(), charset);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					body.in().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	       
	        
	        try {
	        	return new Gson().fromJson(reader, type);
			} catch (JsonSyntaxException e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			} catch (IllegalStateException e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		  }
}
