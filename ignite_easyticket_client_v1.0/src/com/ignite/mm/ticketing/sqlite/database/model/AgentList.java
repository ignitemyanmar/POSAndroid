package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class AgentList {
	
	@Expose
	private List<Agent> agents = new ArrayList<Agent>();
	
	public List<Agent> getAgents() {
	return agents;
	}
	
	public void setAgents(List<Agent> agents) {
	this.agents = agents;
	}

}