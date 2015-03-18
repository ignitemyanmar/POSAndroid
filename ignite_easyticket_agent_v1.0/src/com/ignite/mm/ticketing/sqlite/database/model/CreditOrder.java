package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditOrder {
	
	@Expose
	private String id;
	@Expose
	private String orderdate;
	@SerializedName("agent_id")
	@Expose
	private Integer agentId;
	@SerializedName("operator_id")
	@Expose
	private Integer operatorId;
	@Expose
	private String customer;
	@Expose
	private String phone;
	@Expose
	private String operator;
	@Expose
	private String agent;
	@Expose
	private String trip;
	@Expose
	private String time;
	@Expose
	private String classes;
	@Expose
	private String date;
	@SerializedName("total_ticket")
	@Expose
	private Integer totalTicket;
	@Expose
	private Integer price;
	@Expose
	private Integer amount;
	@Expose
	private List<Saleitem> saleitems = new ArrayList<Saleitem>();
	
	public String getId() {
	return id;
	}
	
	public void setId(String id) {
	this.id = id;
	}
	
	public String getOrderdate() {
	return orderdate;
	}
	
	public void setOrderdate(String orderdate) {
	this.orderdate = orderdate;
	}
	
	public Integer getAgentId() {
	return agentId;
	}
	
	public void setAgentId(Integer agentId) {
	this.agentId = agentId;
	}
	
	public Integer getOperatorId() {
	return operatorId;
	}
	
	public void setOperatorId(Integer operatorId) {
	this.operatorId = operatorId;
	}
	
	public String getCustomer() {
	return customer;
	}
	
	public void setCustomer(String customer) {
	this.customer = customer;
	}
	
	public String getPhone() {
	return phone;
	}
	
	public void setPhone(String phone) {
	this.phone = phone;
	}
	
	public String getOperator() {
	return operator;
	}
	
	public void setOperator(String operator) {
	this.operator = operator;
	}
	
	public String getAgent() {
	return agent;
	}
	
	public void setAgent(String agent) {
	this.agent = agent;
	}
	
	public String getTrip() {
	return trip;
	}
	
	public void setTrip(String trip) {
	this.trip = trip;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTotalTicket() {
	return totalTicket;
	}
	
	public void setTotalTicket(Integer totalTicket) {
	this.totalTicket = totalTicket;
	}
	
	public Integer getPrice() {
	return price;
	}
	
	public void setPrice(Integer price) {
	this.price = price;
	}
	
	public Integer getAmount() {
	return amount;
	}
	
	public void setAmount(Integer amount) {
	this.amount = amount;
	}
	
	public List<Saleitem> getSaleitems() {
	return saleitems;
	}
	
	public void setSaleitems(List<Saleitem> saleitems) {
	this.saleitems = saleitems;
	}

}