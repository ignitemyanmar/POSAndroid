package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class Agent {
	
	@Expose
	private String id;
	@Expose
	private String agentgroup_id;
	@Expose
	private String agentgroup;
	@Expose
	private String name;
	@Expose
	private String phone;
	@Expose
	private String address;
	@Expose
	private String commission_id;
	@Expose
	private String commissiontype;
	@Expose
	private String commission;
	
	public Agent(String id, String agentgroup_id, String agentgroup,
			String name, String phone, String address, String commission_id,
			String commissiontype, String commission) {
		super();
		this.id = id;
		this.agentgroup_id = agentgroup_id;
		this.agentgroup = agentgroup;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.commission_id = commission_id;
		this.commissiontype = commissiontype;
		this.commission = commission;
	}

	public String getId() {
	return id;
	}
	
	public void setId(String id) {
	this.id = id;
	}
	
	public String getAgentgroup_id() {
	return agentgroup_id;
	}
	
	public void setAgentgroup_id(String agentgroup_id) {
	this.agentgroup_id = agentgroup_id;
	}
	
	public String getAgentgroup() {
	return agentgroup;
	}
	
	public void setAgentgroup(String agentgroup) {
	this.agentgroup = agentgroup;
	}
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	this.name = name;
	}
	
	public String getPhone() {
	return phone;
	}
	
	public void setPhone(String phone) {
	this.phone = phone;
	}
	
	public String getAddress() {
	return address;
	}
	
	public void setAddress(String address) {
	this.address = address;
	}
	
	public String getCommission_id() {
	return commission_id;
	}
	
	public void setCommission_id(String commission_id) {
	this.commission_id = commission_id;
	}
	
	public String getCommissiontype() {
	return commissiontype;
	}
	
	public void setCommissiontype(String commissiontype) {
	this.commissiontype = commissiontype;
	}
	
	public String getCommission() {
	return commission;
	}
	
	public void setCommission(String commission) {
	this.commission = commission;
	}

	@Override
	public String toString() {
		return name;
	}
}