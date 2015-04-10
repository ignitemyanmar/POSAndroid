package com.ignite.mm.ticketing.application;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LoginUtil {

	private String AccessToken;
	private String TokenType;
	private long Expires;
	private long ExpiresIn;
	private String RefreshToken;
	private String TodayString;
	private long TodayLong;
	private Context ctx;
	private String ID;
	private String Name;
	private String Type;
	private Integer Role;
	private String Gender;
	private String Address;
	private String Phone;
	private String Email;
	

	public LoginUtil(Context ctx){
		this.ctx = ctx;
		TodayString = getTodayString();
		TodayLong = changeFrom(TodayString);
		
		SharedPreferences pref = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
		AccessToken = pref.getString("access_token", null);
		TokenType = pref.getString("token_type", null);
		Expires = pref.getLong("expires", 0);
		ExpiresIn = pref.getLong("expires_in", 0);
		RefreshToken = pref.getString("refresh_token", null);
		ID = pref.getString("user_id", null);
		Name = pref.getString("user_name", null);
		Type = pref.getString("user_type", null);
		Role = pref.getInt("user_role", 0);
		Gender = pref.getString("user_gender", null);
		Address = pref.getString("user_address", null);
		Phone = pref.getString("user_phone", null);
		Email = pref.getString("user_email", null);
		
        
		Log.d("","Today ---> "+" Long is "+TodayLong +" and "+ convertTime(TodayLong));
		Log.d("","Expires ---> "+" Long is "+Expires +" and "+ convertServerTime(Expires));
	}
	
	public boolean isExpires(){
		boolean is = false;
		Date today = getTodayDate();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Expires * 1000);
        Date expireDate = cal.getTime();
		if(expireDate.compareTo(today) < 0){
			is = true;
			SharedPreferences ref = ctx.getSharedPreferences("User",Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = ref.edit();
			editor.clear();
			editor.commit();
		}
		return is;
	}
	
	public void login(){
		SharedPreferences sharedPreferences = ctx.getSharedPreferences("User",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.clear();
		editor.commit();
		
		editor.putString("access_token", getAccessToken());
		editor.putString("token_type", getTokenType());
		editor.putLong("expires", getExpires());
		editor.putLong("expires_in", getExpiresIn());
		editor.putString("refresh_token", getRefreshToken());
		editor.putString("user_id", getID());
		editor.putString("user_name", getName());
		editor.putString("user_type", getType());
		editor.putInt("user_role", getRole());
		editor.putString("user_gender", getGender());
		editor.putString("user_address", getAddress());
		editor.putString("user_phone", getPhone());
		editor.putString("user_email", getEmail());
		
		editor.commit();
	}
	
	public boolean isLogin(){
		boolean isLogin = false;
		if(ID != null && Email != null){
			isLogin = true;
		}
		return isLogin;
	}
	
	public void logout(){
		SharedPreferences ref = ctx.getSharedPreferences("User",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = ref.edit();
		editor.clear();
		editor.commit();
	}
		
	private String getTodayString(){
		Calendar c = Calendar.getInstance(Locale.getDefault());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}
	
	private Date getTodayDate(){
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.getTime();
	}
	
	private long changeFrom(String str){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
			return df.parse(str).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
    }
	
	public String convertServerTime(long time){
    	
    	Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
        Date date = cal.getTime();
    	
        Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        return format.format(date);
    }
    
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}

	public String getTokenType() {
		return TokenType;
	}

	public void setTokenType(String tokenType) {
		TokenType = tokenType;
	}

	public long getExpires() {
		return Expires;
	}

	public void setExpires(long expires) {
		Expires = expires;
	}

	public long getExpiresIn() {
		return ExpiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		ExpiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return RefreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}

	public String getID() {
		return ID;
	}

	public void setID(String userID) {
		ID = userID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String userName) {
		Name = userName;
	}

	public String getType() {
		return Type;
	}

	public void setType(String userType) {
		Type = userType;
	}

	public void setTodayString(String todayString) {
		TodayString = todayString;
	}

	public long getTodayLong() {
		return TodayLong;
	}

	public void setTodayLong(long todayLong) {
		TodayLong = todayLong;
	}

	public Integer getRole() {
		return Role;
	}

	public void setRole(Integer userRole) {
		Role = userRole;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}   
}
