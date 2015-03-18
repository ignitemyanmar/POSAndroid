package com.ignite.pos.model;

public class BusTicketSale {
	
	private Integer id;
	private String barcodeNo;
	private String customerName;
	private String operatorName;
	private String trip;
	private String date;
	private String time;
	private String busClass;
	private String seatNo;
	private Integer seatCount;
	private Integer seatPrice;
	private String confirmDate;
	private String buyerName;
	private String buyerPhone;
	private String buyerNRC;
	
	
	public BusTicketSale() {
		super();
		// TODO Auto-generated constructor stub
	}
	



	public BusTicketSale(Integer id, String barcodeNo, String customerName,
			String operatorName, String trip, String date, String time,
			String busClass, String seatNo, Integer seatCount,
			Integer seatPrice, String confirmDate, String buyerName,
			String buyerPhone, String buyerNRC) {
		super();
		this.id = id;
		this.barcodeNo = barcodeNo;
		this.customerName = customerName;
		this.operatorName = operatorName;
		this.trip = trip;
		this.date = date;
		this.time = time;
		this.busClass = busClass;
		this.seatNo = seatNo;
		this.seatCount = seatCount;
		this.seatPrice = seatPrice;
		this.confirmDate = confirmDate;
		this.buyerName = buyerName;
		this.buyerPhone = buyerPhone;
		this.buyerNRC = buyerNRC;
	}




	public BusTicketSale(String barcodeNo, String customerName,
			String operatorName, String trip, String date, String time,
			String busClass, String seatNo, Integer seatCount,
			Integer seatPrice, String confirmDate, String buyerName,
			String buyerPhone, String buyerNRC) {
		super();
		this.barcodeNo = barcodeNo;
		this.customerName = customerName;
		this.operatorName = operatorName;
		this.trip = trip;
		this.date = date;
		this.time = time;
		this.busClass = busClass;
		this.seatNo = seatNo;
		this.seatCount = seatCount;
		this.seatPrice = seatPrice;
		this.confirmDate = confirmDate;
		this.buyerName = buyerName;
		this.buyerPhone = buyerPhone;
		this.buyerNRC = buyerNRC;
	}




	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getBarcodeNo() {
		return barcodeNo;
	}


	public void setBarcodeNo(String barcodeNo) {
		this.barcodeNo = barcodeNo;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getOperatorName() {
		return operatorName;
	}


	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}


	public String getTrip() {
		return trip;
	}


	public void setTrip(String trip) {
		this.trip = trip;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getBusClass() {
		return busClass;
	}


	public void setBusClass(String busClass) {
		this.busClass = busClass;
	}


	public String getSeatNo() {
		return seatNo;
	}


	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}


	public Integer getSeatCount() {
		return seatCount;
	}


	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}


	public Integer getSeatPrice() {
		return seatPrice;
	}


	public void setSeatPrice(Integer seatPrice) {
		this.seatPrice = seatPrice;
	}


	public String getConfirmDate() {
		return confirmDate;
	}


	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}




	public String getBuyerName() {
		return buyerName;
	}




	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}




	public String getBuyerPhone() {
		return buyerPhone;
	}




	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}




	public String getBuyerNRC() {
		return buyerNRC;
	}




	public void setBuyerNRC(String buyerNRC) {
		this.buyerNRC = buyerNRC;
	}




	@Override
	public String toString() {
		return "BusTicketSale [id=" + id + ", barcodeNo=" + barcodeNo
				+ ", customerName=" + customerName + ", operatorName="
				+ operatorName + ", trip=" + trip + ", date=" + date
				+ ", time=" + time + ", busClass=" + busClass + ", seatNo="
				+ seatNo + ", seatCount=" + seatCount + ", seatPrice="
				+ seatPrice + ", confirmDate=" + confirmDate + ", buyerName="
				+ buyerName + ", buyerPhone=" + buyerPhone + ", buyerNRC="
				+ buyerNRC + "]";
	}




}
