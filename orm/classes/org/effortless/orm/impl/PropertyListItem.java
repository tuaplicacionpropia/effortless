package org.effortless.orm.impl;

public class PropertyListItem<Type extends Object> extends Object {

	public PropertyListItem () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateValue();
		initiateIndex();
		initiateStatus();
	}
	
	protected Type value;
	
	protected void initiateValue () {
		this.value = null;
	}
	
	public Type getValue () {
		return this.value;
	}
	
	public void setValue (Type newValue) {
		this.value = newValue;
	}
	
	protected Integer index;
	
	protected void initiateIndex () {
		this.index = null;
	}
	
	public Integer getIndex () {
		return this.index;
	}
	
	public void setIndex (Integer newValue) {
		this.index = newValue;
	}
	
	protected PropertyListItemStatus status;
	
	protected void initiateStatus () {
		this.status = null;
	}
	
	public PropertyListItemStatus getStatus () {
		return this.status;
	}
	
	public void setStatus (PropertyListItemStatus newValue) {
		this.status = newValue;
	}
	
}
