package com.ignite.mm.ticketing.sqlite.database.model;

public class UserList {
	private String UserID;
	private String UserName;
	private String Password;
	
	public UserList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserList(String userID, String userName, String password) {
		super();
		setUserID(userID);
		setUserName(userName);
		setPassword(password);
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
