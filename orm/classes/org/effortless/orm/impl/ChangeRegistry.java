package org.effortless.orm.impl;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeRegistry extends ChangeSupport implements Serializable {//java.beans.PropertyChangeSupport {

//	protected ChangeRegistry () {
//		super();
//		initiate();
//	}
	
	public ChangeRegistry(Object src) {
		super(src);
	}

	protected void initiate () {
		super.initiate();
		this._loaded = new java.util.ArrayList<String>();
		this._nextNumberId = Integer.valueOf(0);
	}
	
//	protected java.util.List<ChangeRegistryItem> changes;
	protected java.util.Map<String, ChangeRegistryItem> changes;
	
	protected Integer _nextNumberId;
	
	public boolean hasChanges () {
		return (this.changes != null && this.changes.size() > 0);
	}	
	
	public boolean hasChanges(String properties) {// throws ModelException {
		boolean result = false;
		if (this.changes != null) {
			properties = (properties != null ? properties.trim() : "");
			if (properties.length() > 0) {
				String[] arrayProperties = properties.split(",");
				for (String property : arrayProperties) {
					result = this.changes.containsKey(property);
					if (result) {
						break;
					}
				}
			}
			else {
				result = hasChanges();
			}
		}
		return result;
	}
	
	public java.util.List<ChangeRegistryItem> getChangesList () {
		java.util.List<ChangeRegistryItem> result = null;
		java.util.Collection<ChangeRegistryItem> values = (this.changes != null ? this.changes.values() : null);
		result = new java.util.ArrayList<ChangeRegistryItem>();
		if (values != null) {
			result.addAll(values);
		}
		Collections.sort(result);
		return result;
	}

	public void add(String propertyName, Object newValue) {
		this.add(propertyName, null, newValue);
	}
	
	
	public void add(String propertyName, Object oldValue, Object newValue) {
		ChangeRegistryItem item = (this.changes != null ? this.changes.get(propertyName) : null);
		if (item == null) {
			item = new ChangeRegistryItem(propertyName, oldValue, this._nextNumberId);
			this._nextNumberId = Integer.valueOf(this._nextNumberId.intValue() + 1);
			this.changes = (this.changes != null ? this.changes : new java.util.HashMap<String, ChangeRegistryItem>());
			this.changes.put(propertyName, item);
		}
		item.setNewValue(newValue);
		if (!item.hasChanged()) {
			this.changes.remove(propertyName);
		}
	}

	
	
	
	
	
	protected java.util.List<String> _loaded;

	public boolean checkLoaded(String propertyName) {
		return this._loaded.contains(propertyName);
	}

	public void setupLoaded(String propertyName) {
		if (!this._loaded.contains(propertyName)) {
			this._loaded.add(propertyName);
		}
	}

	public void clear() {
		if (this.changes != null) {
			this.changes.clear();
		}
		this._nextNumberId = Integer.valueOf(0);
	}

	
	
	
	public void enableNotifyChanges() {
		// TODO Auto-generated method stub
		
	}

	public void enableDisableChangeEvents() {
		// TODO Auto-generated method stub
		
	}

	public void disableDisableChangeEvents() {
		// TODO Auto-generated method stub
		
	}

	public void disableNotifyChanges() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

	public boolean checkLoaded(String property, boolean save) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean unloadProperty(String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean unloadProperty(String property, Object oldValue,
			Object newValue, boolean notify) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean unloadProperty(String property, boolean notify) {
		// TODO Auto-generated method stub
		return false;
	}

	public void unloadProperties() {
		// TODO Auto-generated method stub
		
	}

	public String doGetLoadedProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkRead(String property, boolean force) {
		// TODO Auto-generated method stub
		return false;
	}

	public void unreadProperty(String property) {
		// TODO Auto-generated method stub
		
	}

	public void unreadProperties() {
		// TODO Auto-generated method stub
		
	}

	public String doGetReadProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public void restore(String itemProperty) {
		// TODO Auto-generated method stub
		
	}

	public void restore() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String toString () {
		return toText();
	}
	
	public String toText () {
		String result = null;
		int length = (this.changes != null ? this.changes.size() : 0);
		boolean flag = false;
		for (int i = 0; i < length; i++) {
			ChangeRegistryItem item = (ChangeRegistryItem)this.changes.get(i);
			if (item != null) {
				String text = item.toText();
				if (text != null) {
					result = (flag ? result + "\n" : "");
					result += text;
					flag = (!flag ? result.length() > 0 : flag);
				}
//				System.out.println(">>>>>>> log text = " + text);
			}
		}
		result = (result != null ? result : "(null)");
		return result;
	}

	public void restore (Object target) {
		if (this.changes != null && target != null) {
//			this.changes.values()
//			for (ChangeRegistryItem item : this.changes) {
//				item.restore(target);
//			}
			this.changes = null;
		}
	}
	
	public void restore (Object target, String property) {
		if (target != null && this.changes != null && property != null) {
			property = property.trim();
			if (property.length() > 0) {
				ChangeRegistryItem toRemove = this.changes.get(property);
				if (toRemove != null) {
					toRemove.restore(target);
					this.changes.remove(toRemove);
				}
			}
		}
	}
	
	
	
}
