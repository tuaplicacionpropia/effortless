package org.effortless.orm;

import java.beans.PropertyChangeListener;

public interface Bindable {

	public void addPropertyChangeListener (String name, PropertyChangeListener listener);
	
	public void addPropertyChangeListener (PropertyChangeListener listener);
	
}
