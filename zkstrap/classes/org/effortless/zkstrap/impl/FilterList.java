package org.effortless.zkstrap.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

import org.effortless.zkstrap.ObjectAccess;

public class FilterList extends java.util.ArrayList {
	
	public FilterList () {
		super();
	}
	
     private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

     public void addPropertyChangeListener(PropertyChangeListener listener) {
         this.pcs.addPropertyChangeListener(listener);
     }

     public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
         this.pcs.addPropertyChangeListener(propertyName, listener);
     }

     public void removePropertyChangeListener(PropertyChangeListener listener) {
         this.pcs.removePropertyChangeListener(listener);
     }

     protected String name;

     public String getName() {
         return this.name;
     }

     public void setName(String newValue) {
         String oldValue = this.name;
         if (!ObjectAccess.equals(oldValue, newValue)) {
	         this.name = newValue;
	         this.pcs.firePropertyChange("name", oldValue, newValue);
         }
     }

	public int size () {
		int result = 0;
		int _size = super.size();
		for (int i = 0; i < _size; i++) {
			Object item = super.get(i);
			if (item != null && _checkFilter(item)) {
				result += 1;
			}
		}
//		result = super.size();
		return result;
	}
	
	protected boolean _checkFilter(Object item) {
		boolean result = false;
		if (item != null) {
			MyBean bean = (MyBean)item;
			String name = (this.name != null ? this.name.trim(): "");
			String beanName = bean.getName();
			beanName = (beanName != null ? beanName.trim() : "");
			
			result = result || name.length() <= 0;
			result = result || beanName.contains(name);
		}
		return result;
	}

	public Object get (int index) {
		Object result = null;
//		result = super.get(index);
		do {
			Object item = super.get(index);
			if (_checkFilter(item)) {
				result = item;
			}
			else {
				index += 1;
			}
		} while (result == null);

		return result;
	}
	
	public Iterator iterator () {
		Iterator result = null;
		final FilterList _this = this;
		final int _size = this.size();
		result = new Iterator () {

			protected int idx = 0;
			
			@Override
			public boolean hasNext() {
				boolean result = false;
				result = (this.idx < _size);
				return result;
			}

			@Override
			public Object next() {
				Object result = null;
				result = _this.get(this.idx);
				this.idx += 1;
				return result;
			}
			
		};
		return result;
	}
	
	
}
