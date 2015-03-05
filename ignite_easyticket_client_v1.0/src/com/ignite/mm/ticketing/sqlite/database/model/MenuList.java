package com.ignite.mm.ticketing.sqlite.database.model;

public class MenuList {

	private String Id;
	private String Name;
	private int Image;

	public MenuList() {
		// TODO Auto-generated constructor stub
	}

	public MenuList(String id, String name,int img) {
		super();
		Id = id;
		Name = name;
		Image=img;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getImage() {
		return Image;
	}

	public void setImage(int image) {
		Image = image;
	}
	
}
