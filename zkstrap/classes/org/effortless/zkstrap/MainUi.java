package org.effortless.zkstrap;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Label;

public class MainUi extends UiCtrl {

	public MainUi () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
	}
	
	protected void buildApp() {
		super.buildApp();
//		final MainUi _main = this;
		this.app.setLabel("Mi aplicación");
		this.app.setAttribute("INC", Integer.valueOf(0));
		
//		MenuBuilder b = MenuBuilder.create(app, this);
//		b.addMenu("Inicio", new EventListener() { public void onEvent(Event evt) throws Exception {
//			_main.menuInicio(evt, app);
//		}});
//		b.addMenu("Editor", new EventListener() { public void onEvent(Event evt) throws Exception {
//			_main.menuEditor(evt, app);
//		}});
//			
//		b.addMenu("Finder", new EventListener() { public void onEvent(Event evt) throws Exception {
//			_main.menuFinder(evt, app);
//		}});
		
		
		MenuBuilder b = MenuBuilder.create(this.app, this);
		b.addMenu("menuInicio");
//		b.addMenu("menuEditor");
		b.addMenu("menuFinder");
	}

	protected java.util.List list = new FilterList();
	
	public static class FilterList extends java.util.ArrayList {
		
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
//			result = super.size();
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
//			result = super.get(index);
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
	
	protected MyBean buildMyBean (String name) {
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
	
	protected java.util.List buildListMyBean () {
		java.util.List result = null;
		result = new java.util.ArrayList();
		
		result.add(buildMyBean("Mi nombre"));
		result.add(buildMyBean("España"));
		result.add(buildMyBean("Canarias"));
		
		return result;
	}
	
	public void menuInicio (Event evt) {
		Integer inc = (Integer)this.app.getAttribute("INC");
		inc = Integer.valueOf(inc.intValue() + 1);
		this.app.setAttribute("INC", inc);
		Label label = new Label();
		label.setValue("" + inc.intValue() + ". HOLA DON PEPITO");
		this.app.appendChild(label);
	}
	
	public void menuEditor (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");

		MyBean obj = (value != null ? (MyBean)value : buildMyBean("Mi nombre"));
		
		PageBuilder b = PageBuilder.createEditor(this.app, obj, "myEditor");
		b.addText("name");
		b.addText("surnames");
		b.addBtn("ejecutar");
		b.addBtn("descargar");
	}
	
	public void myFinder$onSearch (Event evt) {
		System.out.println("myFinder$onSearch");
		java.util.Map data = (java.util.Map)evt.getData();
		java.util.List value = (java.util.List)data.get("value");

		
		this.app.reopen("myFinder");
	}

	public void myFinder$onCreate (Event evt) {
		System.out.println("myFinder$onCreate");
		
		MyBean obj = buildMyBean("New");
		
		PageBuilder b = PageBuilder.createEditor(this.app, obj, "myEditor");
		b.addText("name");
		b.addText("surnames");
		b.addBtn("ejecutar");
		b.addBtn("descargar");
	}

	public void myEditor$onSave (Event evt) {
		System.out.println("myEditor$onSave");
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");
		if (!this.list.contains(value)) {
			this.list.add(value);
		}
		ObjectAccess.close(evt.getTarget());
	}

	public void myFinder$onRead (Event evt) {
		System.out.println("myFinder$onRead");
		menuEditor(evt);
	}
	
	public void myFinder$onUpdate (Event evt) {
		System.out.println("myFinder$onUpdate");
		menuEditor(evt);
	}
	
	public void myFinder$onDelete (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");
		PageBuilder.createConfirm(this.app, "myConfirm", "delete", value);
		System.out.println("myFinder$onDelete");
	}
	
	public void myConfirm (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");
		String op = (String)data.get("op");
		if ("ok".equals(op)) {
			if (this.list.contains(value)) {
				this.list.remove(value);
			}
//			this.app.reopen("myFinder");
		}
		ObjectAccess.close(evt.getTarget());
	}
	
	public void menuFinder (Event evt) {
		if (this.list.size() <= 0) {
			java.util.List obj = buildListMyBean();
			if (obj != null) {
				this.list.addAll(obj);
			}
		}
		
		if (!this.app.reopen("myFinder")) {
			Finder b = new Finder(this.app, this.list, "myFinder");
			b.addText("name");
			b.addBtn("@ejecutar");
			b.addBtn("@descargar");
			b.setProperties("name,surnames");
		}
	}

	
	
//	protected void buildContent3(Event evt, AdminApp app) {
//		Pojo obj = new Pojo();
//		obj.setName("Mi nombre");
//		
//		PageBuilder b = PageBuilder.createBaseEditor(app, obj);
//		b.addText("name");
//	}
//	
//	protected void buildContent2(Event evt, AdminApp app) {
//		java.util.Map obj = new java.util.HashMap();
//		obj.put("name", "Mi nombre");
//		obj.put("age", null);
//		
//		PageBuilder b = PageBuilder.createBaseEditor(app, obj);
//		b.addText("name");
//		b.addInteger("age");
//		b.addBoolean("checkbox");
//		b.addPhone("phone");
//		b.addIp("ip");
//		b.addNumber("number");
//		b.addCurrency("currency");
//		b.addEmail("email");
//		b.addColor("color");
//		b.addSelect("select");
//		b.addRadio("radio");
//		b.addPassword("password");
//		b.addTextArea("comment");
//		b.addDate("date");
//		b.addTime("time");
//		b.addDateTime("datetime");
//	}
//	
//	public class Pojo extends Object {
//		
//		protected String name;
//		
//		public String getName () {
//			return this.name;
//		}
//		
//		public void setName (String newValue) {
//			this.name = newValue;
//		}
//		
//	}
	
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

	 }	

}
