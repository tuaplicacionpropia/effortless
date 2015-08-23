package org.effortless.orm;

import java.beans.PropertyChangeListener;

public interface IBean {

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

}
