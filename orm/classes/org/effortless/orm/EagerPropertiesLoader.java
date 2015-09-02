package org.effortless.orm;

import java.sql.ResultSet;

import org.effortless.orm.impl.PropertiesLoader;

public class EagerPropertiesLoader extends Object implements PropertiesLoader {

	public EagerPropertiesLoader (AbstractEntity e) {
		super();
		this._pivot = e;
	}
	
	protected AbstractEntity _pivot;
	
	public String getName () {
		return "EAGER";
	}
		
	public String getColumns () {
		return this._pivot._columnsEager();
	}
		
	public Object newItem (DbManager db, ResultSet rs) {
		Object result = null;
		result = (Object)this._pivot._newInstance();
		this._pivot._loadPk(result, db, rs);
		return result;
	}
		
	public void load(Object res, DbManager db, ResultSet rs) {
		this._pivot._loadEager(res, db, rs);
	}
	
}
