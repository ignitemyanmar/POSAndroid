
package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchOperatorSeat {

    @SerializedName("total_permitseats")
    @Expose
    private int totalPermitseats;
    @SerializedName("permitseat_soldtotal")
    @Expose
    private int permitseatSoldtotal;
    @SerializedName("available_seats")
    @Expose
    private int availableSeats;
    @Expose
    private List<Trip> trips = new ArrayList<Trip>();

    /**
     * 
     * @return
     *     The totalPermitseats
     */
    public int getTotalPermitseats() {
        return totalPermitseats;
    }

    /**
     * 
     * @param totalPermitseats
     *     The total_permitseats
     */
    public void setTotalPermitseats(int totalPermitseats) {
        this.totalPermitseats = totalPermitseats;
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
     *     The availableSeats
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * 
     * @param availableSeats
     *     The available_seats
     */
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    /**
     * 
     * @return
     *     The trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * 
     * @param trips
     *     The trips
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

	@Override
	public String toString() {
		return "SearchOperatorSeat [totalPermitseats=" + totalPermitseats
				+ ", permitseatSoldtotal=" + permitseatSoldtotal
				+ ", availableSeats=" + availableSeats + ", trips=" + trips
				+ "]";
	}
    
    

}
