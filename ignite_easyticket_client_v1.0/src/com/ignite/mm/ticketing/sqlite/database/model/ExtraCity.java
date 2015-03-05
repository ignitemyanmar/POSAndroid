package com.ignite.mm.ticketing.sqlite.database.model;

public class ExtraCity {
	private Integer id;
	private Integer trip_id;
	private Integer city_id;
	private Integer local_price;
	private Integer foreign_price;
	private String  city_name;
	
	public ExtraCity(Integer id, Integer trip_id, Integer city_id,
			Integer local_price, Integer foreign_price, String city_name) {
		super();
		this.id = id;
		this.trip_id = trip_id;
		this.city_id = city_id;
		this.local_price = local_price;
		this.foreign_price = foreign_price;
		this.city_name = city_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(Integer trip_id) {
		this.trip_id = trip_id;
	}
	public Integer getCity_id() {
		return city_id;
	}
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	public Integer getLocal_price() {
		return local_price;
	}
	public void setLocal_price(Integer local_price) {
		this.local_price = local_price;
	}
	public Integer getForeign_price() {
		return foreign_price;
	}
	public void setForeign_price(Integer foreign_price) {
		this.foreign_price = foreign_price;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	
}
