package org.effortless.orm.impl;

public class TableIndex extends Object {
	
	protected TableIndex () {
		super();
		initiate();
	}
	
	public TableIndex (String name, String... columns) {
		this();
		this.name = name;
		this.columns = columns;
	}
	
	protected void initiate () {
		initiateName();
		initiateColumns();
	}
	
	protected String name;
	
	protected void initiateName () {
		this.name = null;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		this.name = newValue;
	}
	
	protected String[] columns;
	
	protected void initiateColumns () {
		this.columns = null;
	}
	
	public String[] getColumns () {
		return this.columns;
	}
	
	public void setColumns (String[] newValue) {
		this.columns = newValue;
	}

	protected Boolean unique;
	
	protected void initiateUnique () {
		this.unique = Boolean.FALSE;
	}
	
	public Boolean getUnique () {
		return this.unique;
	}
	
	public void setUnique (Boolean newValue) {
		this.unique = newValue;
	}
	
	public boolean checkUnique() {
		return (this.unique != null && this.unique.booleanValue());
	}
	
}