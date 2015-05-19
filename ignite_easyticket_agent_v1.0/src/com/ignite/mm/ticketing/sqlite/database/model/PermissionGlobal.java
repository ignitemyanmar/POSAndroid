package com.ignite.mm.ticketing.sqlite.database.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.annotations.Expose;

public class PermissionGlobal {

	@Expose
	private String ip;
	@Expose
	private String access_token;
	@Expose
	private String operator_id;
	@Expose
	private String operator_group_id;
	@Expose
	private String agent_id;
	@Expose
	private Context ctx;

	
	public PermissionGlobal(Context ctx){
		this.ctx = ctx;
		
		SharedPreferences pref = ctx.getSharedPreferences("Permission", Context.MODE_PRIVATE);
		this.ip = pref.getString("ip", null); 
		this.access_token = pref.getString("access_token", null);
		this.operator_id = pref.getString("operator_id", null);
		this.operator_group_id = pref.getString("operator_group_id", null);
		this.agent_id = pref.getString("agent_id", null);
	}
	
	public void permission(){
		SharedPreferences sharedPreferences = ctx.getSharedPreferences("Permission",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		editor.clear();
		editor.commit();
		
		editor.putString("ip", getIp());
		editor.putString("access_token", getAccess_token());
		editor.putString("operator_id", getOperator_id());
		editor.putString("operator_group_id", getOperator_group_id());
		editor.putString("agent_id", getAgent_id());
		
		editor.commit();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOperator_group_id() {
		return operator_group_id;
	}

	public void setOperator_group_id(String operator_group_id) {
		this.operator_group_id = operator_group_id;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	@Override
	public String toString() {
		return "Permission Global [ip=" + ip + ", access_token=" + access_token
				+ ", operator_id=" + operator_id + ", operator_group_id="
				+ operator_group_id + ", agent_id=" + agent_id + "]";
	}

	
}
