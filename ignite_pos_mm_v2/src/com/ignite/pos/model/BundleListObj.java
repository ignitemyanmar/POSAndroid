package com.ignite.pos.model;

import java.util.ArrayList;
import java.util.List;

public class BundleListObj {

	List<SaleReturn> listObj = new ArrayList<SaleReturn>();

	public List<SaleReturn> getListObj() {
		return listObj;
	}

	public void setListObj(List<SaleReturn> listObj) {
		this.listObj = listObj;
	}
}
