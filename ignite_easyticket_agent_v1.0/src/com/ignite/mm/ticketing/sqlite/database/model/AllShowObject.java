package com.ignite.mm.ticketing.sqlite.database.model;

public class AllShowObject {
	private String ShowID;
	private String ShowName;
	private String TicketID;
	private String price;
	private String Qty;

	public AllShowObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AllShowObject(String showID, String showName,String TicketID, String price ,String qty2) {
		super();
		setShowID(showID);
		setShowName(showName);
		this.setTicketID(TicketID);
		this.setPrice(price);
		this.setQty(qty2);
	}

	public String getShowID() {
		return ShowID;
	}

	public void setShowID(String showID) {
		ShowID = showID;
	}

	public String getShowName() {
		return ShowName;
	}

	public void setShowName(String showName) {
		ShowName = showName;
	}

	public String getTicketID() {
		return TicketID;
	}

	public void setTicketID(String ticketID) {
		TicketID = ticketID;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQty() {
		return Qty;
	}

	public void setQty(String qty) {
		Qty = qty;
	}

	@Override
	public String toString() {
		return "AllShowObject [ShowID=" + ShowID + ", ShowName=" + ShowName
				+ ", TicketID=" + TicketID + ", price=" + price + "]";
	}

}
