package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

	public class From {
	
	@Expose
	private String id;
	@Expose
	private String name;
	
	public From(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return name;
	}
	
	

}
