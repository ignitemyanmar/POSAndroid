package com.ignite.mm.ticketing.http.connection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class HttpConnection extends AsyncTask<Void, Void, Void>  {
	private Handler h;
	private String address;
	private String method;
	private List<NameValuePair> params;
	private InputStream is = null;

	public HttpConnection(Handler h, String method, String address, List<NameValuePair> params) {
		this.h = h;
		this.method = method;
		this.address = address;
		this.params = params;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		if(method.equals("POST")){
			postData();
		}
		if(method.equals("GET")){
			getData();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);			
	}
	public void postData(){
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(address);
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (Exception e) {
			// TODO: handle exception
		}
		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {

		}
		Bundle b = new Bundle();
		b.putString("data", result);
		Message msg = h.obtainMessage();
		msg.setData(b);
		h.sendMessage(msg);
	}
	public void getData() {

		try {
			HttpClient httpclient = new DefaultHttpClient();
			address += params != null ? "?"+URLEncodedUtils.format(params, "UTF-8"):"";
			HttpGet httpGet = new HttpGet(address);
			
			HttpResponse response = httpclient.execute(httpGet);
			is = response.getEntity().getContent();
		} catch (Exception e) {
		}

		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {

		}
		Bundle b = new Bundle();
		b.putString("data", result);
		Message msg = h.obtainMessage();
		msg.setData(b);
		h.sendMessage(msg);

	}

}
