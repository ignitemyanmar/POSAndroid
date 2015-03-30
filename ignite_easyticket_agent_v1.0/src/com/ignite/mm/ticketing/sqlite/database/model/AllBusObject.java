package com.ignite.mm.ticketing.sqlite.database.model;

import android.graphics.Bitmap;

public class AllBusObject {
	
	private String Trip;
	private String Date;
	private String OperatorID;
	private String OperatorName;
	private String Time;
	private String SeatID;
	private String SeatNo;
	private String Price;
	private String Status;
	private String UserName;
	private String BusClass;
	private String todayDate;
	private String currentTime;
	private String Barcode;
	private Bitmap barcode_img;
	private String CustomerName;
	private String phone;
	private String ticketNo;
	private String seatCount;
	private Integer discount;
	private Integer amount;

	
	public AllBusObject(String trip, String date, String operatorID,
			String operatorName, String time, String seatID, String seatNo,
			String price, String status, String userName, String busClass,
			String todayDate, String currentTime, String barcode,
			Bitmap barcode_img, String customerName, String phone,
			String ticketNo, String seatCount, Integer discount, Integer amount) {
		super();
		Trip = trip;
		Date = date;
		OperatorID = operatorID;
		OperatorName = operatorName;
		Time = time;
		SeatID = seatID;
		SeatNo = seatNo;
		Price = price;
		Status = status;
		UserName = userName;
		BusClass = busClass;
		this.todayDate = todayDate;
		this.currentTime = currentTime;
		Barcode = barcode;
		this.barcode_img = barcode_img;
		CustomerName = customerName;
		this.phone = phone;
		this.ticketNo = ticketNo;
		this.seatCount = seatCount;
		this.discount = discount;
		this.amount = amount;
	}

	public AllBusObject(String trip, String date, String operatorID,
			String operatorName, String time, String seatID, String seatNo,
			String price, String status, String userName, String busClass,
			String todayDate, String currentTime, String barcode,
			Bitmap barcode_img, String customerName) {
		super();
		Trip = trip;
		Date = date;
		OperatorID = operatorID;
		OperatorName = operatorName;
		Time = time;
		SeatID = seatID;
		SeatNo = seatNo;
		Price = price;
		Status = status;
		UserName = userName;
		BusClass = busClass;
		this.todayDate = todayDate;
		this.currentTime = currentTime;
		Barcode = barcode;
		this.barcode_img = barcode_img;
		CustomerName = customerName;
	}
	
	public AllBusObject(String trip, String date, String operatorID,
			String operatorName, String time, String seatID, String seatNo,
			String price, String status, String userName, String busClass,
			String todayDate, String currentTime, String barcode) {
		super();
		Trip = trip;
		Date = date;
		OperatorID = operatorID;
		OperatorName = operatorName;
		Time = time;
		SeatID = seatID;
		SeatNo = seatNo;
		Price = price;
		Status = status;
		UserName = userName;
		BusClass = busClass;
		this.todayDate = todayDate;
		this.currentTime = currentTime;
		Barcode = barcode;
	}
	
	public String getTrip() {
		return Trip;
	}
	public void setTrip(String trip) {
		Trip = trip;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getOperatorID() {
		return OperatorID;
	}
	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}
	public String getOperatorName() {
		return OperatorName;
	}
	public void setOperatorName(String operatorName) {
		OperatorName = operatorName;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getSeatID() {
		return SeatID;
	}
	public void setSeatID(String seatID) {
		SeatID = seatID;
	}
	public String getSeatNo() {
		return SeatNo;
	}
	public void setSeatNo(String seatNo) {
		SeatNo = seatNo;
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

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getBusClass() {
		return BusClass;
	}

	public void setBusClass(String busClass) {
		BusClass = busClass;
	}

	
	public String getTodayDate() {
		return todayDate;
	}
	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	
	public String getBarcode() {
		return Barcode;
	}
	public void setBarcode(String barcode) {
		Barcode = barcode;
	}
	
	
	public Bitmap getBarcode_img() {
		return barcode_img;
	}

	public void setBarcode_img(Bitmap barcode_img) {
		this.barcode_img = barcode_img;
	}

	
	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(String seatCount) {
		this.seatCount = seatCount;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "AllBusObject [Trip=" + Trip + ", Date=" + Date
				+ ", OperatorID=" + OperatorID + ", OperatorName="
				+ OperatorName + ", Time=" + Time + ", SeatID=" + SeatID
				+ ", SeatNo=" + SeatNo + ", Price=" + Price + ", Status="
				+ Status + ", UserName=" + UserName + ", BusClass=" + BusClass
				+ ", todayDate=" + todayDate + ", currentTime=" + currentTime
				+ ", Barcode=" + Barcode + ", barcode_img=" + barcode_img
				+ ", CustomerName=" + CustomerName + ", phone=" + phone
				+ ", ticketNo=" + ticketNo + ", seatCount=" + seatCount
				+ ", discount=" + discount + ", amount=" + amount + "]";
	}
}
