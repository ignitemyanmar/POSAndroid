package com.ignite.pos.model;

public class Buyer {
	
	private Integer buyerId;
	private String buyerName;
	private String buyerCity;
	private String buyerPhone;
	private String buyerAddress;
	
	public Buyer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Buyer(Integer buyerId, String buyerName, String buyerCity,
			String buyerPhone, String buyerAddress) {
		super();
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerCity = buyerCity;
		this.buyerPhone = buyerPhone;
		this.buyerAddress = buyerAddress;
	}

	public Buyer(String buyerName, String buyerCity, String buyerPhone,
			String buyerAddress) {
		super();
		this.buyerName = buyerName;
		this.buyerCity = buyerCity;
		this.buyerPhone = buyerPhone;
		this.buyerAddress = buyerAddress;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	@Override
	public String toString() {
		return "Buyer [buyerId=" + buyerId + ", buyerName=" + buyerName
				+ ", buyerCity=" + buyerCity + ", buyerPhone=" + buyerPhone
				+ ", buyerAddress=" + buyerAddress + "]";
	}
	
	
	
}
