package com.ignite.pos.model;

import java.util.ArrayList;
import java.util.List;

public class BundleListObjet {
	List<SaleVouncher> saleVouncher = new ArrayList<SaleVouncher>();

	public List<SaleVouncher> getSaleVouncher() {
		return saleVouncher;
	}

	public void setSaleVouncher(List<SaleVouncher> saleVouncher) {
		this.saleVouncher = saleVouncher;
	}
	
}
