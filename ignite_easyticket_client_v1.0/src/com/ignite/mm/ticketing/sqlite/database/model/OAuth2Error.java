package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class OAuth2Error {
	
	@Expose
	private String error;
	@Expose
	private String error_description;
	
	public String getError() {
	return error;
	}
	
	public void setError(String error) {
	this.error = error;
	}
	
	public String getError_description() {
	return error_description;
	}
	
	public void setError_description(String error_description) {
	this.error_description = error_description;
	}

}