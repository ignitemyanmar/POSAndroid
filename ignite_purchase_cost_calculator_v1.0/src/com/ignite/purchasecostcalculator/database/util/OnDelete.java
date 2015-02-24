package com.ignite.purchasecostcalculator.database.util;

import java.util.List;

public interface OnDelete {
	public void deleteRecord();
	public List<Object> deleteRecord(Object obj);
	public boolean deleteRecord(String arg0);
	public boolean deleteRecord(List<Object> objList);
}
