package com.ignite.purchasecostcalculator.database.util;

import java.util.List;

public interface OnSave {
	public void saveRecord(Object obj);
	public void saveRecord(List<Object> objList);
}
