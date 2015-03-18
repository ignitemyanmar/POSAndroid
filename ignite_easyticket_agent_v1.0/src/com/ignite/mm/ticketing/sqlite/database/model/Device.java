package com.ignite.mm.ticketing.sqlite.database.model;

public class Device {

	public String deviceName;
	public String deviceAddress;
	public Device() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Device(String deviceName, String deviceAddress) {
		super();
		this.deviceName = deviceName;
		this.deviceAddress = deviceAddress;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceAddress() {
		return deviceAddress;
	}
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	@Override
	public String toString() {
		return "Device [deviceName=" + deviceName + ", deviceAddress="
				+ deviceAddress + "]";
	}
	
	
}
