package org.effortless.orm.impl;

import java.sql.ResultSet;

public interface ColumnDecoder {

	public Object decode (String columnName, ResultSet rs);
	
}
