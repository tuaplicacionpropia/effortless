package org.effortless.orm.impl;

import java.sql.PreparedStatement;

public interface ColumnEncoder {

	public void encode (Object owner, PreparedStatement stm, int index, Object value); 
	
}
