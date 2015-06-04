
package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip {

    @Expose
    private String id;
    @Expose
    private String operator;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("from_name")
    @Expose
    private String fromName;
    @SerializedName("to_name")
    @Expose
    private String toName;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("available_day")
    @Expose
    private String availableDay;
    @Expose
    private String price;
    @SerializedName("foreign_price")
    @Expose
    private String foreignPrice;
    @Expose
    private String time;
    @SerializedName("departure_time")
    @Expose
    private String departureTime;
    @Expose
    private String tripdeparturetime;
    @SerializedName("min_availabletime")
    @Expose
    private String minAvailabletime;
    @SerializedName("time_checking")
    @Expose
    private String timeChecking;
    @SerializedName("total_seat")
    @Expose
    private int totalSeat;
    @SerializedName("permitseat_total")
    @Expose
    private int permitseatTotal;
    @SerializedName("permitseat_soldtotal")
    @Expose
    private int permitseatSoldtotal;
    @SerializedName("time_unit")
    @Expose
    private int timeUnit;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 
     * @param operator
     *     The operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 
     * @return
     *     The operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * 
     * @param operatorId
     *     The operator_id
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 
     * @return
     *     The fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * 
     * @param fromName
     *     The from_name
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * 
     * @return
     *     The toName
     */
    public String getToName() {
        return toName;
    }

    /**
     * 
     * @param toName
     *     The to_name
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /**
     * 
     * @return
     *     The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     * 
     * @param _class
     *     The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     * 
     * @return
     *     The availableDay
     */
    public String getAvailableDay() {
        return availableDay;
    }

    /**
     * 
     * @param availableDay
     *     The available_day
     */
    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The foreignPrice
     */
    public String getForeignPrice() {
        return foreignPrice;
    }

    /**
     * 
     * @param foreignPrice
     *     The foreign_price
     */
    public void setForeignPrice(String foreignPrice) {
        this.foreignPrice = foreignPrice;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * 
     * @param departureTime
     *     The departure_time
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 
     * @return
     *     The tripdeparturetime
     */
    public String getTripdeparturetime() {
        return tripdeparturetime;
    }

    /**
     * 
     * @param tripdeparturetime
     *     The tripdeparturetime
     */
    public void setTripdeparturetime(String tripdeparturetime) {
        this.tripdeparturetime = tripdeparturetime;
    }

    /**
     * 
     * @return
     *     The minAvailabletime
     */
    public String getMinAvailabletime() {
        return minAvailabletime;
    }

    /**
     * 
     * @param minAvailabletime
     *     The min_availabletime
     */
    public void setMinAvailabletime(String minAvailabletime) {
        this.minAvailabletime = minAvailabletime;
    }

    /**
     * 
     * @return
     *     The timeChecking
     */
    public String getTimeChecking() {
        return timeChecking;
    }

    /**
     * 
     * @param timeChecking
     *     The time_checking
     */
    public void setTimeChecking(String timeChecking) {
        this.timeChecking = timeChecking;
    }

    /**
     * 
     * @return
     *     The totalSeat
     */
    public int getTotalSeat() {
        return totalSeat;
    }

    /**
     * 
     * @param totalSeat
     *     The total_seat
     */
    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    /**
     * 
     * @return
     *     The permitseatTotal
     */
    public int getPermitseatTotal() {
        return permitseatTotal;
    }

    /**
     * 
     * @param permitseatTotal
     *     The permitseat_total
     */
    public void setPermitseatTotal(int permitseatTotal) {
        this.permitseatTotal = permitseatTotal;
    }

    /**
     * 
     * @return
     *     The permitseatSoldtotal
     */
    public int getPermitseatSoldtotal() {
        return permitseatSoldtotal;
    }

    /**
     * 
     * @param permitseatSoldtotal
     *     The permitseat_soldtotal
     */
    public void setPermitseatSoldtotal(int permitseatSoldtotal) {
        this.permitseatSoldtotal = permitseatSoldtotal;
    }

    /**
     * 
     * @return
     *     The timeUnit
     */
    public int getTimeUnit() {
        return timeUnit;
    }

    /**
     * 
     * @param timeUnit
     *     The time_unit
     */
    public void setTimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

	@Override
	public String toString() {
		return "Trip [id=" + id + ", operator=" + operator + ", operatorId="
				+ operatorId + ", fromName=" + fromName + ", toName=" + toName
				+ ", _class=" + _class + ", availableDay=" + availableDay
				+ ", price=" + price + ", foreignPrice=" + foreignPrice
				+ ", time=" + time + ", departureTime=" + departureTime
				+ ", tripdeparturetime=" + tripdeparturetime
				+ ", minAvailabletime=" + minAvailabletime + ", timeChecking="
				+ timeChecking + ", totalSeat=" + totalSeat
				+ ", permitseatTotal=" + permitseatTotal
				+ ", permitseatSoldtotal=" + permitseatSoldtotal
				+ ", timeUnit=" + timeUnit + "]";
	}
    
    

}
