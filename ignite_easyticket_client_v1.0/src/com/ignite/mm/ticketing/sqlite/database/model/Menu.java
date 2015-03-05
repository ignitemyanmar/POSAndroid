package com.ignite.mm.ticketing.sqlite.database.model;

public class Menu {

	private String ticketTypeId;
	private String ticketTypeName;
	private int Image;

	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public Menu(String ticketTypeid, String ticketTypename , Integer image) {
		super();
		setTicketTypeId(ticketTypeid);
		setTicketTypeName(ticketTypename);
		this.Image=image;
	}	

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	public int getImage() {
		return Image;
	}

	public void setImage(int image) {
		Image = image;
	}
	
}
