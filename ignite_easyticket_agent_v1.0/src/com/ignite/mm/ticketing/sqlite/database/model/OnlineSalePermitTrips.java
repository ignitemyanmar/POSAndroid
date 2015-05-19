package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineSalePermitTrips {

@Expose
private String id;
@SerializedName("operator_id")
@Expose
private String operatorId;
@SerializedName("from_id")
@Expose
private String fromId;
@SerializedName("to_id")
@Expose
private String toId;
@SerializedName("trip_id")
@Expose
private String tripId;
@SerializedName("time_checking")
@Expose
private String timeChecking;
@Expose
private String soldpermit;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@Expose
private String time;



public OnlineSalePermitTrips(String id, String operatorId, String fromId,
		String toId, String tripId, String timeChecking, String soldpermit,
		String createdAt, String updatedAt, String time) {
	super();
	this.id = id;
	this.operatorId = operatorId;
	this.fromId = fromId;
	this.toId = toId;
	this.tripId = tripId;
	this.timeChecking = timeChecking;
	this.soldpermit = soldpermit;
	this.createdAt = createdAt;
	this.updatedAt = updatedAt;
	this.time = time;
}


public OnlineSalePermitTrips(String fromId, String toId) {
	super();
	this.fromId = fromId;
	this.toId = toId;
}


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
* The fromId
*/
public String getFromId() {
return fromId;
}

/**
* 
* @param fromId
* The from_id
*/
public void setFromId(String fromId) {
this.fromId = fromId;
}

/**
* 
* @return
* The toId
*/
public String getToId() {
return toId;
}

/**
* 
* @param toId
* The to_id
*/
public void setToId(String toId) {
this.toId = toId;
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
* The timeChecking
*/
public String getTimeChecking() {
return timeChecking;
}

/**
* 
* @param timeChecking
* The time_checking
*/
public void setTimeChecking(String timeChecking) {
this.timeChecking = timeChecking;
}

/**
* 
* @return
* The soldpermit
*/
public String getSoldpermit() {
return soldpermit;
}

/**
* 
* @param soldpermit
* The soldpermit
*/
public void setSoldpermit(String soldpermit) {
this.soldpermit = soldpermit;
}

/**
* 
* @return
* The createdAt
*/
public String getCreatedAt() {
return createdAt;
}

/**
* 
* @param createdAt
* The created_at
*/
public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

/**
* 
* @return
* The updatedAt
*/
public String getUpdatedAt() {
return updatedAt;
}

/**
* 
* @param updatedAt
* The updated_at
*/
public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
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


@Override
public String toString() {
	return "OnlineSalePermitTrips [id=" + id + ", operatorId=" + operatorId
			+ ", fromId=" + fromId + ", toId=" + toId + ", tripId=" + tripId
			+ ", timeChecking=" + timeChecking + ", soldpermit=" + soldpermit
			+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
			+ ", time=" + time + "]";
}



}