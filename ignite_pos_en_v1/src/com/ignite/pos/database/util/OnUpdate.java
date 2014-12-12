package com.ignite.pos.database.util;

import java.util.List;

public interface OnUpdate {
	public void updateRecord(Object obj);
	public void updateRecord(List<Object> objList);
}
