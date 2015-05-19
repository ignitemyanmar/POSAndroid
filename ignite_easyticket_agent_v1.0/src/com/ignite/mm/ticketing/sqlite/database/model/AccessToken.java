package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {

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

	/**
	* 
	* @return
	* The id
	*/
	public String getId() {
	return id;
	}
	
	/**
	* 
	* @param id
	* The id
	*/
	public void setId(String id) {
	this.id = id;
	}
	
	/**
	* 
	* @return
	* The name
	*/
	public String getName() {
	return name;
	}
	
	/**
	* 
	* @param name
	* The name
	*/
	public void setName(String name) {
	this.name = name;
	}
	
	/**
	* 
	* @return
	* The email
	*/
	public String getEmail() {
	return email;
	}
	
	/**
	* 
	* @param email
	* The email
	*/
	public void setEmail(String email) {
	this.email = email;
	}
	
	/**
	* 
	* @return
	* The codeNo
	*/
	public String getCodeNo() {
	return codeNo;
	}
	
	/**
	* 
	* @param codeNo
	* The code_no
	*/
	public void setCodeNo(String codeNo) {
	this.codeNo = codeNo;
	}
	
	/**
	* 
	* @return
	* The role
	*/
	public int getRole() {
	return role;
	}
	
	/**
	* 
	* @param role
	* The role
	*/
	public void setRole(int role) {
	this.role = role;
	}
	
	/**
	* 
	* @return
	* The agentgroupId
	*/
	public int getAgentgroupId() {
	return agentgroupId;
	}
	
	/**
	* 
	* @param agentgroupId
	* The agentgroup_id
	*/
	public void setAgentgroupId(int agentgroupId) {
	this.agentgroupId = agentgroupId;
	}
	
	/**
	* 
	* @return
	* The groupBranch
	*/
	public int getGroupBranch() {
	return groupBranch;
	}
	
	/**
	* 
	* @param groupBranch
	* The group_branch
	*/
	public void setGroupBranch(int groupBranch) {
	this.groupBranch = groupBranch;
	}
	
	/**
	* 
	* @return
	* The accessToken
	*/
	public String getAccessToken() {
	return accessToken;
	}
	
	/**
	* 
	* @param accessToken
	* The access_token
	*/
	public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
	}
	
	/**
	* 
	* @return
	* The createdAt
	*/
	public String getCreatedAt() {
	return createdAt;
	}
	
	
	/**
	* 
	* @param createdAt
	* The created_at
	*/
	public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
	}
	
	/**
	* 
	* @return
	* The updatedAt
	*/
	public String getUpdatedAt() {
	return updatedAt;
	}
	
	/**
	* 
	* @param updatedAt
	* The updated_at
	*/
	public void setUpdatedAt(String updatedAt) {
	this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "LoginUser [AccessToken=" + accessToken +"]" ;
	}
	
}