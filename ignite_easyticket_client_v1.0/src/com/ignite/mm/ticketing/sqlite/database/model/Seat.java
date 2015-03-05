package com.ignite.mm.ticketing.sqlite.database.model;

public class Seat {
	private String SeatID;
	private String SeatNo;
	private String Price;
	private String Status;
	public Seat() {
		super();
	}
	public Seat(String seatID,String seatNo, String price, String status) {
		super();
		SeatID = seatID;
		SeatNo=seatNo;
		Price = price;
		Status = status;
	}
	public String getSeatID() {
		return SeatID;
	}
	public void setSeatID(String seatID) {
		SeatID = seatID;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getSeatNo() {
		return SeatNo;
	}
	public void setSeatNo(String seatNo) {
		SeatNo = seatNo;
	}
	@Override
	public String toString() {
		return "Seat [SeatID=" + SeatID + ", SeatNo=" + SeatNo + ", Price="
				+ Price + ", Status=" + Status + "]";
	}
	
	
}
