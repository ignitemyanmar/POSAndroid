package com.ignite.pos.model;

public class Buyer {
//	private int Id;
	
	private String cusOldName;
	private String cusName;
	private String cusCity;
	private String cusPh;
	private String cusAddress;
	private String cusCredit;
	
	public Buyer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Buyer(String cusName, String cusCity, String cusPh,
			String cusAddress) {
		super();
		//Id = id;
		this.cusName = cusName;
		this.cusCity = cusCity;
		this.cusPh = cusPh;
		this.cusAddress = cusAddress;
	}
	
	

	/*public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}*/

	public Buyer(String cusOldName, String cusName, String cusCity,
			String cusPh, String cusAddress) {
		super();
		this.cusOldName = cusOldName;
		this.cusName = cusName;
		this.cusCity = cusCity;
		this.cusPh = cusPh;
		this.cusAddress = cusAddress;
	}

	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getCusCity() {
		return cusCity;
	}
	public void setCusCity(String cusCity) {
		this.cusCity = cusCity;
	}
	public String getCusPh() {
		return cusPh;
	}
	public void setCusPh(String cusPh) {
		this.cusPh = cusPh;
	}
	public String getCusAddress() {
		return cusAddress;
	}
	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}
	public String getCusCredit() {
		return cusCredit;
	}
	public void setCusCredit(String cusCredit) {
		this.cusCredit = cusCredit;
	}
	
	public String getCusOldName() {
		return cusOldName;
	}

	public void setCusOldName(String cusOldName) {
		this.cusOldName = cusOldName;
	}

	@Override
	public String toString() {
		return "Buyer [cusOldName=" + cusOldName + ", cusName=" + cusName
				+ ", cusCity=" + cusCity + ", cusPh=" + cusPh + ", cusAddress="
				+ cusAddress + "]";
	}

	
}
