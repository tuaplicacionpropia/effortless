package org.effortless.orm.impl;

import org.effortless.core.PropertyUtils;
import org.effortless.orm.util.CvtrUtils;

public class ChangeRegistryItem extends Object implements Comparable<Object> {

	protected ChangeRegistryItem () {
		super();
		initiate();
	}
	
	public ChangeRegistryItem(String propertyName, Object oldValue, Integer numberId) {
		this();
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.numberId = numberId;
	}

	protected void initiate () {
		initiatePropertyName();
		initiateOldValue();
		initiateNewValue();
		initiateNumberId();
		initiateText();
	}
	
	protected String propertyName;
	
	protected void initiatePropertyName () {
		this.propertyName = null;
	}
	
	public String getPropertyName () {
		return this.propertyName;
	}
	
	protected void setPropertyName (String newValue) {
		this.propertyName = newValue;
		this.text = null;
	}
	
	protected Object oldValue;
	
	public void initiateOldValue () {
		this.oldValue = null;
	}
	
	public Object getOldValue () {
		return this.oldValue;
	}
	
	protected void setOldValue (Object newValue) {
		this.oldValue = newValue;
		this.text = null;
	}
	
	protected Object newValue;
	
	protected void initiateNewValue () {
		this.newValue = null;
	}
	
	public Object getNewValue () {
		return this.newValue;
	}
	
	public void setNewValue (Object newValue) {
		this.newValue = newValue;
		this.text = null;
	}
	
	protected Integer numberId;
	
	protected void initiateNumberId () {
		this.numberId = null;
	}
	
	public Integer getNumberId () {
		return this.numberId;
	}
	
	protected void setNumberId (Integer newValue) {
		this.numberId = newValue;
		this.text = null;
	}

	public boolean hasChanged() {
		boolean result = false;
		PropertyList propertyList = null;
		try { propertyList = (PropertyList)this.newValue; } catch (ClassCastException e) {}
		if (this.oldValue == null && propertyList != null) {
			result = propertyList.hasChanges();
		}
		else {
			result = !org.effortless.core.ObjectUtils.equals(this.oldValue, this.newValue);
		}
		return result;
	}

	@Override
	public int compareTo(Object obj) {
		int result = 0;
		ChangeRegistryItem item = (ChangeRegistryItem)obj;
		result = this.numberId.compareTo(item.numberId);
		return result;
	}
	
	protected String toText (Object value) {
		String result = null;
		result = CvtrUtils.getInstance().toText(value);
//		result = (value != null ? value.toString() : null);
		result = (result == null ? "(null)" : result);
		return result;
	}	

	protected String text;
	
	protected void initiateText () {
		this.text = null;
	}
	
	protected String getText () {
		return this.text;
	}
	
	protected void setText (String newValue) {
		this.text = newValue;
	}
	
	public String toText () {
		if (this.text == null) {
			String result = null;
			String propertyName = toText(getPropertyName());
			String oldValue = toText(getOldValue());
			String newValue = toText(getNewValue());
			result = "";
			result += propertyName + "_old=" + oldValue;
			result += "\n";
			result += propertyName + "_new=" + newValue;
			this.text = result;
		}
		return this.text;
	}

	public void restore(Object target) {
		if (target != null) {
			PropertyUtils.setProperty(target, this.propertyName, this.oldValue);
		}
	}
	
}
