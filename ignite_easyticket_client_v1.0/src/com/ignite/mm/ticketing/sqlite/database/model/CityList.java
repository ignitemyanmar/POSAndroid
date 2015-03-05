package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

	public class CityList {
	
	@Expose
	private List<City> cities = new ArrayList<City>();
	
	public List<City> getCities() {
	return cities;
	}
	
	public void setCities(List<City> cities) {
	this.cities = cities;
	}
	
}
