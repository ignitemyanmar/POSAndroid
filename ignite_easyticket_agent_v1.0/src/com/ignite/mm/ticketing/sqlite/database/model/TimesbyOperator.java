package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

	public class TimesbyOperator {
	
	@Expose
	private String time;
	
	public TimesbyOperator(String time) {
		super();
		this.time = time;
	}

	public String getTime() {
	return time;
	}
	
	public void setTime(String time) {
	this.time = time;
	}

	@Override
	public String toString() {
		return time;
	}
	
}