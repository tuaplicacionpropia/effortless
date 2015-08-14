package org.effortless.zkstrap;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Label;

public class MainUi extends org.zkoss.zk.ui.GenericRichlet {

	public void service(Page page) throws Exception {
		String requestPath = page.getRequestPath();
		AdminApp app = new AdminApp();
		if (app != null) {
			buildApp(app);
			app.setPage(page);
		}
	}

	protected void buildApp(AdminApp _app) {
		final MainUi _main = this;
		final AdminApp app = _app;
		app.setLabel("Mi aplicación");
		app.setAttribute("INC", Integer.valueOf(0));
		{
			MenuBs menuHome = new MenuBs();
			menuHome.setLabel("Inicio");
			menuHome.addEventListener(Events.ON_CLICK, new EventListener() {

				public void onEvent(Event evt) throws Exception {
					Integer inc = (Integer)app.getAttribute("INC");
					boolean flag = ((inc.intValue() % 3) != 0);
					if (flag) {
						inc = Integer.valueOf(inc.intValue() + 1);
						app.setAttribute("INC", inc);
						Label label = new Label();
						label.setValue("" + inc.intValue() + ". HOLA DON PEPITO");
						app.appendChild(label);
					}
					else {
						_main.buildContent(evt, app);
					}
				}
				
			});
			app.appendChild(menuHome);
		}
		// TODO Auto-generated method stub
		
	}

	protected void buildContent(Event evt, AdminApp app) {
		MyBean obj = new MyBean();
		obj.setName("Mi nombre");
		obj.addPropertyChangeListener("name", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				MyBean src = (MyBean)evt.getSource();
				String surnames = (String)evt.getOldValue();
				src.setSurnames(surnames);
			}
			
		});
		
		if (false) {
			PageBuilder b = PageBuilder.createEditor(app, obj);
			b.addText("name");
			b.addText("surnames");
			b.addBtn("ejecutar");
			b.addBtn("descargar");
		}
		else {
			PageBuilder b = PageBuilder.createFinder(app, obj);
			b.addBtn("ejecutar");
			b.addBtn("descargar");
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

	     private String name;

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

	     private String surnames;

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
	     
	     public void ejecutar () {
	    	 System.out.println("Ejecutando: " + this.name);
	     }

	     public void descargar () {
	    	 System.out.println("Descargando: " + this.name);
	     }

	 }	

}
