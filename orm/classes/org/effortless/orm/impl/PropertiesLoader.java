package org.effortless.orm.impl;

import java.sql.ResultSet;

import org.effortless.orm.DbManager;

public interface PropertiesLoader {

	public String getName();
	
	public String getColumns();
	
	public Object newItem (DbManager db, ResultSet rs);
	
	public void load (Object entity, DbManager db, ResultSet rs);
	
}
