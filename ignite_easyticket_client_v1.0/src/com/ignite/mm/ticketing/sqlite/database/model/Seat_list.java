package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class Seat_list {
	
	@Expose
	private Integer id;
	@Expose
	private String seat_no;
	@Expose
	private Integer status;
	@Expose
	private Integer booking;
	@Expose
	private Integer remark_type;
	@Expose
	private String remark;
	@Expose
	private Integer operatorgroup_id;
	@Expose
	private CustomerInfo customer_info;
	
	public Seat_list(Integer id, String seat_no, Integer status) {
		super();
		this.id = id;
		this.seat_no = seat_no;
		this.status = status;
	}

	public Integer getId() {
	return id;
	}
	
	public void setId(Integer id) {
	this.id = id;
	}
	
	public String getSeat_no() {
	return seat_no;
	}
	
	public void setSeat_no(String seat_no) {
	this.seat_no = seat_no;
	}
	
	public Integer getStatus() {
	return status;
	}
	
	public void setStatus(Integer status) {
	this.status = status;
	}
	
	public Integer getBooking() {
		return booking;
	}

	public void setBooking(Integer booking) {
		this.booking = booking;
	}
	
	public Integer getRemark_type() {
		return remark_type;
	}

	public void setRemark_type(Integer remark_type) {
		this.remark_type = remark_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOperatorgroup_id() {
		return operatorgroup_id;
	}

	public void setOperatorgroup_id(Integer operatorgroup_id) {
		this.operatorgroup_id = operatorgroup_id;
	}

	public CustomerInfo getCustomerInfo() {
		return customer_info;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customer_info = customerInfo;
	}

	@Override
	public String toString() {
		return "Seat_list [id=" + id + ", seat_no=" + seat_no + ", status="
				+ status + ", booking=" + booking + ", remark_type="
				+ remark_type + ", remark=" + remark + ", operatorgroup_id="
				+ operatorgroup_id + ", customer_info=" + customer_info + "]";
	}
	
	
	
}