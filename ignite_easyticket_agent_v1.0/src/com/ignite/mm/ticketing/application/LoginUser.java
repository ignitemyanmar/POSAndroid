package com.ignite.mm.ticketing.application;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LoginUser {

	@Expose
	private String AccessToken;
	@Expose
	private String TokenType;
	@Expose
	private long Expires;
	@Expose
	private long ExpiresIn;
	@Expose
	private String RefreshToken;
	@Expose
	private String UserID;
	@Expose
	private String UserGroupID;
	@Expose
	private String LoginUserID;
	@Expose
	private String UserName;
	@Expose
	private String UserType;
	@Expose
	private String TodayString;
	@Expose
	private long TodayLong;
	@Expose
	private Context ctx;
	@Expose
	private String Email;
	@Expose
	private String NRC;
	@Expose
	private String Address;
	@Expose
	private String id;
	@Expose
	private String name;
	@Expose
	private String email;
	@SerializedName("code_no")
	@Expose
	private String codeNo;
	@Expose
	private int role;
	@SerializedName("agentgroup_id")
	@Expose
	private int agentgroupId;
	@SerializedName("group_branch")
	@Expose
	private int groupBranch;
	@SerializedName("access_token")
	@Expose
	private String accessToken;
	@SerializedName("created_at")
	@Expose
	private String createdAt;
	@SerializedName("updated_at")
	@Expose
	private String updatedAt;
	

	public LoginUser(Context ctx){
		this.ctx = ctx;
		TodayString = getTodayString();
		TodayLong = changeFrom(TodayString);
		
		SharedPreferences pref = ctx.getSharedPreferences("User", Context.MODE_PRIVATE);
		AccessToken = pref.getString("access_token", null);
		TokenType = pref.getString("token_type", null);
		Expires = pref.getLong("expires", 0);
		ExpiresIn = pref.getLong("expires_in", 0);
		RefreshToken = pref.getString("refresh_token", null);
		id = pref.getString("user_id", null);
		UserGroupID = pref.getString("usergroup_id", null);
		LoginUserID = pref.getString("login_user_id", null);
		UserName = pref.getString("user_name", null);
		UserType = pref.getString("user_type", null);
		Email = pref.getString("email", null);
		NRC = pref.getString("NRC", null);
		Address = pref.getString("Address", null);
        
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
		editor.putString("user_id", getId());
		editor.putString("user_name", getName());
		editor.putString("email", getEmail());
		editor.putString("code_no", getCodeNo());
		editor.putInt("role", getRole());
		editor.putInt("agent_group_id", getAgentgroupId());
		editor.putInt("group_branch", getGroupBranch());
		editor.putString("create_at", getCreatedAt());
		editor.putString("update_at", getUpdatedAt());
		
		editor.commit();
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

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}
	
	public String getUserGroupID() {
		return UserGroupID;
	}

	public void setUserGroupID(String userGroupID) {
		UserGroupID = userGroupID;
	}
	
	public String getLoginUserID() {
		return LoginUserID;
	}

	public void setLoginUserID(String loginUserID) {
		LoginUserID = loginUserID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
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

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getNRC() {
		return NRC;
	}

	public void setNRC(String nRC) {
		NRC = nRC;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
	
	

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getAgentgroupId() {
		return agentgroupId;
	}

	public void setAgentgroupId(int agentgroupId) {
		this.agentgroupId = agentgroupId;
	}

	public int getGroupBranch() {
		return groupBranch;
	}

	public void setGroupBranch(int groupBranch) {
		this.groupBranch = groupBranch;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "LoginUser [AccessToken=" + AccessToken + ", TokenType="
				+ TokenType + ", Expires=" + Expires + ", ExpiresIn="
				+ ExpiresIn + ", RefreshToken=" + RefreshToken + ", UserID="
				+ UserID + ", UserGroupID=" + UserGroupID + ", LoginUserID="
				+ LoginUserID + ", UserName=" + UserName + ", UserType="
				+ UserType + ", TodayString=" + TodayString + ", TodayLong="
				+ TodayLong + ", ctx=" + ctx + ", Email=" + Email + ", NRC="
				+ NRC + ", Address=" + Address + ", id=" + id + ", name="
				+ name + ", email=" + email + ", codeNo=" + codeNo + ", role="
				+ role + ", agentgroupId=" + agentgroupId + ", groupBranch="
				+ groupBranch + ", accessToken=" + accessToken + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
