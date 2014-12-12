package com.ignite.pos.model;

public class SubCategory {
	private int subCategoryID;
	private String subCategoryName;
	private int categoryID;
	
	public SubCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubCategory(String subCategoryName, int categoryID) {
		super();
		this.subCategoryName = subCategoryName;
		this.categoryID = categoryID;
	}

	public SubCategory(int subCategoryID, String subCategoryName,
			int categoryID) {
		super();
		this.subCategoryID = subCategoryID;
		this.subCategoryName = subCategoryName;
		this.categoryID = categoryID;
	}

	
	public SubCategory(int subCategoryID, String subCategoryName) {
		super();
		this.subCategoryID = subCategoryID;
		this.subCategoryName = subCategoryName;
	}

	public int getSubCategoryID() {
		return subCategoryID;
	}

	public void setSubCategoryID(int subCategoryID) {
		this.subCategoryID = subCategoryID;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	@Override
	public String toString() {
		return "SubCategory [subCategoryID=" + subCategoryID
				+ ", subCategoryName=" + subCategoryName + ", categoryID="
				+ categoryID + "]";
	}

}
