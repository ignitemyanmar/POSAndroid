package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorGroupUser {
	@Expose
	private Integer id;
	@SerializedName("operator_id")
	@Expose
	private Integer operatorId;
	@SerializedName("user_id")
	@Expose
	private Integer userId;
	@Expose
	private Integer color;
	@Expose
	private String username;
	@Expose
	private String operatorname;
	
	public Integer getId() {
	return id;
	}
	
	public void setId(Integer id) {
	this.id = id;
	}
	
	public Integer getOperatorId() {
	return operatorId;
	}
	
	public void setOperatorId(Integer operatorId) {
	this.operatorId = operatorId;
	}
	
	public Integer getUserId() {
	return userId;
	}
	
	public void setUserId(Integer userId) {
	this.userId = userId;
	}
	
	public Integer getColor() {
	return color;
	}
	
	public void setColor(Integer color) {
	this.color = color;
	}
	
	public String getUsername() {
	return username;
	}
	
	public void setUsername(String username) {
	this.username = username;
	}
	
	public String getOperatorname() {
	return operatorname;
	}
	
	public void setOperatorname(String operatorname) {
	this.operatorname = operatorname;
	}

	@Override
	public String toString() {
		return "OperatorGroupUser [id=" + id + ", operatorId=" + operatorId
				+ ", userId=" + userId + ", color=" + color + ", username="
				+ username + ", operatorname=" + operatorname + "]";
	}
	
	

}