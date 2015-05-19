package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;

public class BundleListObject {
	List<OnlineSalePermitTrips> onlineSalePermitTrips = new ArrayList<OnlineSalePermitTrips>();

	public List<OnlineSalePermitTrips> getOnlineSalePermitTrips() {
		return onlineSalePermitTrips;
	}

	public void setOnlineSalePermitTrips(
			List<OnlineSalePermitTrips> onlineSalePermitTrips) {
		this.onlineSalePermitTrips = onlineSalePermitTrips;
	}
	
}
