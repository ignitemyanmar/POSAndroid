package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ReturnComfrim {
	
	@Expose
	private String message;
	@Expose
	private Integer sale_order_no;
	@Expose
	private Boolean can_buy;
	@Expose
	private List<Ticket> tickets = new ArrayList<Ticket>();
	
	public String getMessage() {
	return message;
	}
	
	public void setMessage(String message) {
	this.message = message;
	}
	
	public Integer getSale_order_no() {
	return sale_order_no;
	}
	
	public void setSale_order_no(Integer sale_order_no) {
	this.sale_order_no = sale_order_no;
	}
	
	public Boolean getCan_buy() {
	return can_buy;
	}
	
	public void setCan_buy(Boolean can_buy) {
	this.can_buy = can_buy;
	}
	
	public List<Ticket> getTickets() {
	return tickets;
	}
	
	public void setTickets(List<Ticket> tickets) {
	this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "ReturnComfrim [message=" + message + ", sale_order_no="
				+ sale_order_no + ", can_buy=" + can_buy + ", tickets="
				+ tickets + "]";
	}
	
}