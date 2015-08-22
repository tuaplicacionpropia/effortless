package org.effortless.zkstrap.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.effortless.zkstrap.IBean;
import org.effortless.zkstrap.ObjectAccess;

public class MyBean extends Object implements IBean {
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

    protected String surnames;

    public String getSurnames() {
        return this.surnames;
    }

    public void setSurnames(String newValue) {
        String oldValue = this.surnames;
        if (!ObjectAccess.equals(oldValue, newValue)) {
	         this.surnames = newValue;
	         this.pcs.firePropertyChange("surnames", oldValue, newValue);
        }
    }
    
    protected java.util.List listado;

    public java.util.List getListado() {
        return this.listado;
    }

    public void setListado(java.util.List newValue) {
   	 java.util.List oldValue = this.listado;
        if (!ObjectAccess.equals(oldValue, newValue)) {
	         this.listado = newValue;
	         this.pcs.firePropertyChange("listado", oldValue, newValue);
        }
    }
    
    
    
    public void ejecutar () {
   	 System.out.println("Ejecutando: " + this.name);
   	 this.name += "1";
    }

    public void descargar () {
   	 System.out.println("Descargando: " + this.name);
    }

    
    
    
    
	public static MyBean buildMyBean (String name) {
		MyBean result = new MyBean();
		result.setName(name);
		result.addPropertyChangeListener("name", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				MyBean src = (MyBean)evt.getSource();
				String surnames = (String)evt.getOldValue();
				src.setSurnames(surnames);
			}
			
		});
		java.util.List listado = new java.util.ArrayList();
		result.setListado(listado);
		
		{
			MyBean item = new MyBean();
			item.setName(name + " Escobar");
			item.setSurnames(name + " Manolo el cantante");
			listado.add(item);
		}
		
		{
			MyBean item = new MyBean();
			item.setName(name + " Cepilla");
			item.setSurnames(name + " Fregona Patente");
			listado.add(item);
		}
		
		return result;
	}

}	
