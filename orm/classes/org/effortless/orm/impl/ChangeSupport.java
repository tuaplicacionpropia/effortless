package org.effortless.orm.impl;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeSupport extends Object {//java.beans.PropertyChangeSupport {

	public ChangeSupport(Object src) {
		super();
		initiate();
		this.src = src;
	}

	protected void initiate () {
		this.listeners = new HashMap<String, List<PropertyChangeListener>>();
	}
	
	
	protected transient Map<String, List<PropertyChangeListener>> listeners;

	protected Object src;
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(null);
			if (listeners == null) {
				listeners = new ArrayList<PropertyChangeListener>();
				this.listeners.put(null, listeners);
			}
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
	}

	public boolean containsPropertyChangeListener(PropertyChangeListener listener) {
		boolean result = false;
		if (listener != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(null);
			result = (listeners != null && listeners.contains(listener));
		}
		return result;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(null);
			if (listeners != null) {
				listeners.remove(listener);
			}
		}
	}

	public List<PropertyChangeListener> getPropertyChangeListeners() {
		List<PropertyChangeListener> result = null;
		result = this.listeners.get(null);
		return result;
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener != null && propertyName != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(propertyName);
			if (listeners == null) {
				listeners = new ArrayList<PropertyChangeListener>();
				this.listeners.put(propertyName, listeners);
			}
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener != null && propertyName != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(propertyName);
			if (listeners != null) {
				listeners.remove(listener);
			}
		}
	}

	public List<PropertyChangeListener> getPropertyChangeListeners(String propertyName) {
		List<PropertyChangeListener> result = null;
		result = (propertyName != null ? this.listeners.get(propertyName) : null);
		return result;
	}

	public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		boolean result = false;
		if (listener != null && propertyName != null) {
			List<PropertyChangeListener> listeners = this.listeners.get(propertyName);
			result = (listeners != null && listeners.contains(listener));
		}
		return result;
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		firePropertyChange(new PropertyChangeEvent(this.src, propertyName, oldValue, newValue));
	}

	public void firePropertyChange(PropertyChangeEvent evt) {
//		Object oldValue = evt.getOldValue();
//		Object newValue = evt.getNewValue();
		String propertyName = evt.getPropertyName();
		List<PropertyChangeListener> common = getPropertyChangeListeners();
		List<PropertyChangeListener> named = (propertyName != null	? getPropertyChangeListeners(propertyName) : null);

		fire(common, evt);
		fire(named, evt);
	}

	protected void fire(List<PropertyChangeListener> listeners, PropertyChangeEvent event) {
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				listener.propertyChange(event);
			}
		}
	}

	public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
		firePropertyChange(new IndexedPropertyChangeEvent(this.src, propertyName, oldValue, newValue, index));
	}

	public boolean hasListeners(String propertyName) {
		boolean result = false;
		
		List<PropertyChangeListener> common = getPropertyChangeListeners();
		result = (common != null && common.size() > 0);
		if (!result) {
			List<PropertyChangeListener> named = (propertyName != null	? getPropertyChangeListeners(propertyName) : null);
			result = (named != null && named.size() > 0);
		}
		
		return result;
	}

	public PropertyChangeListener[] getPropertyChangeListenersArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public PropertyChangeListener[] getPropertyChangeListenersArray(
			String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
