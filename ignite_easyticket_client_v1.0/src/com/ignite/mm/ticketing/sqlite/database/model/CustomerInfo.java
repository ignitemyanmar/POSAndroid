package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfo {

	@SerializedName("order_id")
	@Expose
	private String orderId;
	@SerializedName("ticket_no")
	@Expose
	private String ticketNo;
	@Expose
	private String name;
	@Expose
	private String phone;
	@SerializedName("nrc_no")
	@Expose
	private String nrcNo;
	@SerializedName("agent_id")
	@Expose
	private Integer agentId;
	@SerializedName("agent_name")
	@Expose
	private String agentName;
	
	/**
	* 
	* @return
	* The orderId
	*/
	public String getOrderId() {
	return orderId;
	}
	
	/**
	* 
	* @param orderId
	* The order_id
	*/
	public void setOrderId(String orderId) {
	this.orderId = orderId;
	}
	
	/**
	* 
	* @return
	* The ticketNo
	*/
	public String getTicketNo() {
	return ticketNo;
	}
	
	/**
	* 
	* @param ticketNo
	* The ticket_no
	*/
	public void setTicketNo(String ticketNo) {
	this.ticketNo = ticketNo;
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
	* The phone
	*/
	public String getPhone() {
	return phone;
	}
	
	/**
	* 
	* @param phone
	* The phone
	*/
	public void setPhone(String phone) {
	this.phone = phone;
	}
	
	/**
	* 
	* @return
	* The nrcNo
	*/
	public String getNrcNo() {
	return nrcNo;
	}
	
	/**
	* 
	* @param nrcNo
	* The nrc_no
	*/
	public void setNrcNo(String nrcNo) {
	this.nrcNo = nrcNo;
	}
	
	/**
	* 
	* @return
	* The agentId
	*/
	public Integer getAgentId() {
	return agentId;
	}
	
	/**
	* 
	* @param agentId
	* The agent_id
	*/
	public void setAgentId(Integer agentId) {
	this.agentId = agentId;
	}
	
	/**
	* 
	* @return
	* The agentName
	*/
	public String getAgentName() {
	return agentName;
	}
	
	/**
	* 
	* @param agentName
	* The agent_name
	*/
	public void setAgentName(String agentName) {
	this.agentName = agentName;
	}

}