package com.ignite.pos.database.util;

import java.util.List;

public interface OnDelete {
	public void deleteRecord();
	public List<Object> deleteRecord(Object obj);
	public boolean deleteRecord(String arg0);
	public List<Object> deleteRecord(List<Object>obj);
}
