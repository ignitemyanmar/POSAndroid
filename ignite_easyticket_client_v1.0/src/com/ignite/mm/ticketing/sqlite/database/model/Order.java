package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.List;

public class Order {
	private Integer operator_id;
	private Integer agent_id;
	private Integer from_city;
	private Integer to_city;
	private List<SelectSeat> seat_list;
	public Order(Integer operator_id, Integer agent_id, Integer from_city,
			Integer to_city, List<SelectSeat> seat_list) {
		super();
		this.operator_id = operator_id;
		this.agent_id = agent_id;
		this.from_city = from_city;
		this.to_city = to_city;
		this.seat_list = seat_list;
	}
	public Integer getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(Integer operator_id) {
		this.operator_id = operator_id;
	}
	public Integer getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Integer agent_id) {
		this.agent_id = agent_id;
	}
	public Integer getFrom_city() {
		return from_city;
	}
	public void setFrom_city(Integer from_city) {
		this.from_city = from_city;
	}
	public Integer getTo_city() {
		return to_city;
	}
	public void setTo_city(Integer to_city) {
		this.to_city = to_city;
	}
	public List<SelectSeat> getSeat_list() {
		return seat_list;
	}
	public void setSeat_list(List<SelectSeat> seat_list) {
		this.seat_list = seat_list;
	}
}
