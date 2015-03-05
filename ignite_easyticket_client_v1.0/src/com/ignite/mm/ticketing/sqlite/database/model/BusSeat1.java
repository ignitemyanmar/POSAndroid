package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;

public class BusSeat1 {
	private String NoOfSeat;
	private String ColAmount;
	private String RowAmount;
	private String SeatTypeId;
	private String SupplierDetailName;
	private String SupplierDetailId;
	private ArrayList<Seat> Seat;
	
	public BusSeat1(String noOfSeat, String colAmount, String rowAmount,
			String seatTypeId, String supplierDetailName,
			String supplierDetailId,
			ArrayList<com.ignite.mm.ticketing.sqlite.database.model.Seat> seat) {
		super();
		NoOfSeat = noOfSeat;
		ColAmount = colAmount;
		RowAmount = rowAmount;
		SeatTypeId = seatTypeId;
		SupplierDetailName = supplierDetailName;
		SupplierDetailId = supplierDetailId;
		Seat = seat;
	}
	public String getNoOfSeat() {
		return NoOfSeat;
	}
	public void setNoOfSeat(String noOfSeat) {
		NoOfSeat = noOfSeat;
	}
	public String getColAmount() {
		return ColAmount;
	}
	public void setColAmount(String colAmount) {
		ColAmount = colAmount;
	}
	public String getRowAmount() {
		return RowAmount;
	}
	public void setRowAmount(String rowAmount) {
		RowAmount = rowAmount;
	}
	public String getSeatTypeId() {
		return SeatTypeId;
	}
	public void setSeatTypeId(String seatTypeId) {
		SeatTypeId = seatTypeId;
	}
	public String getSupplierDetailName() {
		return SupplierDetailName;
	}
	public void setSupplierDetailName(String supplierDetailName) {
		SupplierDetailName = supplierDetailName;
	}
	public String getSupplierDetailId() {
		return SupplierDetailId;
	}
	public void setSupplierDetailId(String supplierDetailId) {
		SupplierDetailId = supplierDetailId;
	}
	public ArrayList<Seat> getSeat() {
		return Seat;
	}
	public void setSeat(ArrayList<Seat> seat) {
		Seat = seat;
	}
	
	

}
