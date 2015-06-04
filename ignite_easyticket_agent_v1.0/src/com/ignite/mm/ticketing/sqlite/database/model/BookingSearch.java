package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingSearch {

@Expose
private String id;
@Expose
private String date;
@SerializedName("departure_date")
@Expose
private String departureDate;
@Expose
private String from;
@Expose
private String to;
@SerializedName("extra_destination_id")
@Expose
private String extraDestinationId;
@Expose
private String time;
@SerializedName("class")
@Expose
private String _class;
@SerializedName("trip_id")
@Expose
private String tripId;
@SerializedName("arrival_date")
@Expose
private String arrivalDate;
@SerializedName("customer_id")
@Expose
private String customerId;
@SerializedName("customer_name")
@Expose
private String customerName;
@SerializedName("customer_nrc")
@Expose
private String customerNrc;
@SerializedName("customer_phone")
@Expose
private String customerPhone;
@SerializedName("agent_id")
@Expose
private String agentId;
@SerializedName("agent_code_no")
@Expose
private String agentCodeNo;
@SerializedName("operator_id")
@Expose
private String operatorId;
@SerializedName("seller_id")
@Expose
private String sellerId;
@SerializedName("total_amount")
@Expose
private String totalAmount;
@SerializedName("booking_status")
@Expose
private String bookingStatus;
@SerializedName("booking_expired")
@Expose
private String bookingExpired;
@SerializedName("order_id")
@Expose
private Object orderId;
@Expose
private String trip;
@Expose
private String operator;
@SerializedName("ticket_no")
@Expose
private String ticketNo;
@SerializedName("seat_no")
@Expose
private String seatNo;
@Expose
private String price;
@SerializedName("ticket_qty")
@Expose
private int ticketQty;
@Expose
private String app_operator_id;

/**
* 
* @return
* The id
*/
public String getId() {
return id;
}

/**
* 
* @param id
* The id
*/
public void setId(String id) {
this.id = id;
}

/**
* 
* @return
* The date
*/
public String getDate() {
return date;
}

/**
* 
* @param date
* The date
*/
public void setDate(String date) {
this.date = date;
}

/**
* 
* @return
* The departureDate
*/
public String getDepartureDate() {
return departureDate;
}

/**
* 
* @param departureDate
* The departure_date
*/
public void setDepartureDate(String departureDate) {
this.departureDate = departureDate;
}

/**
* 
* @return
* The from
*/
public String getFrom() {
return from;
}

/**
* 
* @param from
* The from
*/
public void setFrom(String from) {
this.from = from;
}

/**
* 
* @return
* The to
*/
public String getTo() {
return to;
}

/**
* 
* @param to
* The to
*/
public void setTo(String to) {
this.to = to;
}

/**
* 
* @return
* The extraDestinationId
*/
public String getExtraDestinationId() {
return extraDestinationId;
}

/**
* 
* @param extraDestinationId
* The extra_destination_id
*/
public void setExtraDestinationId(String extraDestinationId) {
this.extraDestinationId = extraDestinationId;
}

/**
* 
* @return
* The time
*/
public String getTime() {
return time;
}

/**
* 
* @param time
* The time
*/
public void setTime(String time) {
this.time = time;
}

/**
* 
* @return
* The _class
*/
public String getClass_() {
return _class;
}

/**
* 
* @param _class
* The class
*/
public void setClass_(String _class) {
this._class = _class;
}

/**
* 
* @return
* The tripId
*/
public String getTripId() {
return tripId;
}

/**
* 
* @param tripId
* The trip_id
*/
public void setTripId(String tripId) {
this.tripId = tripId;
}

/**
* 
* @return
* The arrivalDate
*/
public String getArrivalDate() {
return arrivalDate;
}

/**
* 
* @param arrivalDate
* The arrival_date
*/
public void setArrivalDate(String arrivalDate) {
this.arrivalDate = arrivalDate;
}

/**
* 
* @return
* The customerId
*/
public String getCustomerId() {
return customerId;
}

/**
* 
* @param customerId
* The customer_id
*/
public void setCustomerId(String customerId) {
this.customerId = customerId;
}

/**
* 
* @return
* The customerName
*/
public String getCustomerName() {
return customerName;
}

/**
* 
* @param customerName
* The customer_name
*/
public void setCustomerName(String customerName) {
this.customerName = customerName;
}

/**
* 
* @return
* The customerNrc
*/
public String getCustomerNrc() {
return customerNrc;
}

/**
* 
* @param customerNrc
* The customer_nrc
*/
public void setCustomerNrc(String customerNrc) {
this.customerNrc = customerNrc;
}

/**
* 
* @return
* The customerPhone
*/
public String getCustomerPhone() {
return customerPhone;
}

/**
* 
* @param customerPhone
* The customer_phone
*/
public void setCustomerPhone(String customerPhone) {
this.customerPhone = customerPhone;
}

/**
* 
* @return
* The agentId
*/
public String getAgentId() {
return agentId;
}

/**
* 
* @param agentId
* The agent_id
*/
public void setAgentId(String agentId) {
this.agentId = agentId;
}

/**
* 
* @return
* The agentCodeNo
*/
public String getAgentCodeNo() {
return agentCodeNo;
}

/**
* 
* @param agentCodeNo
* The agent_code_no
*/
public void setAgentCodeNo(String agentCodeNo) {
this.agentCodeNo = agentCodeNo;
}

/**
* 
* @return
* The operatorId
*/
public String getOperatorId() {
return operatorId;
}

/**
* 
* @param operatorId
* The operator_id
*/
public void setOperatorId(String operatorId) {
this.operatorId = operatorId;
}

/**
* 
* @return
* The sellerId
*/
public String getSellerId() {
return sellerId;
}

/**
* 
* @param sellerId
* The seller_id
*/
public void setSellerId(String sellerId) {
this.sellerId = sellerId;
}

/**
* 
* @return
* The totalAmount
*/
public String getTotalAmount() {
return totalAmount;
}

/**
* 
* @param totalAmount
* The total_amount
*/
public void setTotalAmount(String totalAmount) {
this.totalAmount = totalAmount;
}

/**
* 
* @return
* The bookingStatus
*/
public String getBookingStatus() {
return bookingStatus;
}

/**
* 
* @param bookingStatus
* The booking_status
*/
public void setBookingStatus(String bookingStatus) {
this.bookingStatus = bookingStatus;
}

/**
* 
* @return
* The bookingExpired
*/
public String getBookingExpired() {
return bookingExpired;
}

/**
* 
* @param bookingExpired
* The booking_expired
*/
public void setBookingExpired(String bookingExpired) {
this.bookingExpired = bookingExpired;
}

/**
* 
* @return
* The orderId
*/
public Object getOrderId() {
return orderId;
}

/**
* 
* @param orderId
* The order_id
*/
public void setOrderId(Object orderId) {
this.orderId = orderId;
}

/**
* 
* @return
* The trip
*/
public String getTrip() {
return trip;
}

/**
* 
* @param trip
* The trip
*/
public void setTrip(String trip) {
this.trip = trip;
}

/**
* 
* @return
* The operator
*/
public String getOperator() {
return operator;
}

/**
* 
* @param operator
* The operator
*/
public void setOperator(String operator) {
this.operator = operator;
}

/**
* 
* @return
* The ticketNo
*/
public String getTicketNo() {
return ticketNo;
}

/**
* 
* @param ticketNo
* The ticket_no
*/
public void setTicketNo(String ticketNo) {
this.ticketNo = ticketNo;
}

/**
* 
* @return
* The seatNo
*/
public String getSeatNo() {
return seatNo;
}

/**
* 
* @param seatNo
* The seat_no
*/
public void setSeatNo(String seatNo) {
this.seatNo = seatNo;
}

/**
* 
* @return
* The price
*/
public String getPrice() {
return price;
}

/**
* 
* @param price
* The price
*/
public void setPrice(String price) {
this.price = price;
}

/**
* 
* @return
* The ticketQty
*/
public int getTicketQty() {
return ticketQty;
}

/**
* 
* @param ticketQty
* The ticket_qty
*/
public void setTicketQty(int ticketQty) {
this.ticketQty = ticketQty;
}



public String getApp_operator_id() {
	return app_operator_id;
}

public void setApp_operator_id(String app_operator_id) {
	this.app_operator_id = app_operator_id;
}

@Override
public String toString() {
	return "BookingSearch [id=" + id + ", date=" + date + ", departureDate="
			+ departureDate + ", from=" + from + ", to=" + to
			+ ", extraDestinationId=" + extraDestinationId + ", time=" + time
			+ ", _class=" + _class + ", tripId=" + tripId + ", arrivalDate="
			+ arrivalDate + ", customerId=" + customerId + ", customerName="
			+ customerName + ", customerNrc=" + customerNrc
			+ ", customerPhone=" + customerPhone + ", agentId=" + agentId
			+ ", agentCodeNo=" + agentCodeNo + ", operatorId=" + operatorId
			+ ", sellerId=" + sellerId + ", totalAmount=" + totalAmount
			+ ", bookingStatus=" + bookingStatus + ", bookingExpired="
			+ bookingExpired + ", orderId=" + orderId + ", trip=" + trip
			+ ", operator=" + operator + ", ticketNo=" + ticketNo + ", seatNo="
			+ seatNo + ", price=" + price + ", ticketQty=" + ticketQty
			+ ", app_operator_id=" + app_operator_id + "]";
}



}