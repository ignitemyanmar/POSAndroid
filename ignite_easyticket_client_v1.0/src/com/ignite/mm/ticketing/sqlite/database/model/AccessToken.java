package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class AccessToken {
	
	@Expose
	private String access_token;
	@Expose
	private String token_type;
	@Expose
	private Integer expires;
	@Expose
	private Integer expires_in;
	@Expose
	private String refresh_token;
	@Expose
	private User user;

	public String getAccess_token() {
	return access_token;
	}

	public void setAccess_token(String access_token) {
	this.access_token = access_token;
	}

	public String getToken_type() {
	return token_type;
	}

	public void setToken_type(String token_type) {
	this.token_type = token_type;
	}

	public Integer getExpires() {
	return expires;
	}

	public void setExpires(Integer expires) {
	this.expires = expires;
	}

	public Integer getExpires_in() {
	return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
	this.expires_in = expires_in;
	}

	public String getRefresh_token() {
	return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
	this.refresh_token = refresh_token;
	}

	public User getUser() {
	return user;
	}

	public void setUser(User user) {
	this.user = user;
	}

}