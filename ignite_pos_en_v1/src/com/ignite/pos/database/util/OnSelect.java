package com.ignite.pos.database.util;

import java.util.List;

public interface OnSelect {
	public List<Object> selectRecord();
	public List<Object> selectRecord(String arg0 , String arg1);
	public List<Object> selectRecord(String arg0 , String arg1, String arg2);
	public List<Object> selectRecordByBuyer(String arg0 , String arg1, String arg2);
	public List<Object> selectRecord(String arg0);
	public List<Object> selectRecord(Object obj);
}
