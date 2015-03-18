package com.ignite.mm.ticketing.sqlite.database.model;

	public class To {
	
	private String id;
	private String name;
	
	
	public To(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
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
	
	

}
