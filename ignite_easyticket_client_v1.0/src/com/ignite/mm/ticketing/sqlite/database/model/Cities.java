package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

	public class Cities {
	
	private List<From> from = new ArrayList<From>();
	private List<To> to = new ArrayList<To>();
	
	public List<From> getFrom() {
	return from;
	}
	
	public void setFrom(List<From> from) {
	this.from = from;
	}
	
	public List<To> getTo() {
	return to;
	}
	
	public void setTo(List<To> to) {
	this.to = to;
	}

	@Override
	public String toString() {
		return "{\"Cities\": [\"from\":" + from + ", \"to\":" + to + "]}";
	}

}
