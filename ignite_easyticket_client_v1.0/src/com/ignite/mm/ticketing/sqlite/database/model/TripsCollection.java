package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class TripsCollection {
	
	@Expose
	private String from_id;
	@Expose
	private String from;
	@Expose
	private String to_id;
	@Expose
	private String to;
	
	public TripsCollection(String from_id, String from, String to_id, String to) {
		super();
		this.from_id = from_id;
		this.from = from;
		this.to_id = to_id;
		this.to = to;
	}

	public String getFrom_id() {
	return from_id;
	}
	
	public void setFrom_id(String from_id) {
	this.from_id = from_id;
	}
	
	public String getFrom() {
	return from;
	}
	
	public void setFrom(String from) {
	this.from = from;
	}
	
	public String getTo_id() {
	return to_id;
	}
	
	public void setTo_id(String to_id) {
	this.to_id = to_id;
	}
	
	public String getTo() {
	return to;
	}
	
	public void setTo(String to) {
	this.to = to;
	}

	@Override
	public String toString() {
		return "TripsCollection [from_id=" + from_id + ", from=" + from
				+ ", to_id=" + to_id + ", to=" + to + "]";
	}

	
}