package com.ignite.mm.ticketing.sqlite.database.model;

public class PriceList {

	private String ID;
	private String price;
	
	public PriceList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PriceList(String iD, String price) {
		super();
		setID(iD);
		this.setPrice(price);
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
