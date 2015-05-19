package com.ignite.mm.ticketing.sqlite.database.model;

	public class Operator {
	
	private String id;
	private String name;
	private String address;
	private String phone;
	private String cli_operator_id;
	
	public Operator(String id, String name, String address, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
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
	
	public String getAddress() {
	return address;
	}
	
	public void setAddress(String address) {
	this.address = address;
	}
	
	public String getPhone() {
	return phone;
	}
	
	public void setPhone(String phone) {
	this.phone = phone;
	}

	
	public String getCli_operator_id() {
		return cli_operator_id;
	}

	public void setCli_operator_id(String cli_operator_id) {
		this.cli_operator_id = cli_operator_id;
	}

	@Override
	public String toString() {
		return "Operator [id=" + id + ", name=" + name + ", address=" + address
				+ ", phone=" + phone + ", cli_operator_id=" + cli_operator_id
				+ "]";
	}

	
}
