package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class User {
	
	@Expose
	private String id;
	
	@Expose
	private String operatorgroup_id;
	
	@Expose
	private String user_id;
		
	@Expose
	private String name;
	
	@Expose
	private String type;
	
	public String getId() {
	return id;
	}
	
	public void setId(String id) {
	this.id = id;
	}
	
	public String getOperatorgroup_id() {
		return operatorgroup_id;
	}

	public void setOperatorgroup_id(String operatorgroup_id) {
		this.operatorgroup_id = operatorgroup_id;
	}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}