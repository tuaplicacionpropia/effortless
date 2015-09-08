package org.effortless.zkstrap;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

import org.effortless.orm.FilterList;
import org.effortless.orm.MyBean;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Label;

public class MainUi extends AdminApp {

	public MainUi () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
	}
	
	protected void buildApp() {
		super.buildApp();
		setAdminPage("login");
		setLabel("Mi aplicación");
		setAttribute("INC", Integer.valueOf(0));
		
		addMenu("menuInicio");
		addMenu("menuFinder");
	}

	public void menuInicio (Event evt) {
		Integer inc = (Integer)getAttribute("INC");
		inc = Integer.valueOf(inc.intValue() + 1);
		setAttribute("INC", inc);
		Label label = new Label();
		label.setValue("" + inc.intValue() + ". HOLA DON PEPITO");
		appendChild(label);
	}
	
	public void menuFinder (Event evt) {
		if (!reopen("myFinder")) {
//			FilterList list = new FilterList();
//			list.add(MyBean.buildMyBean("Mi nombre"));
//			list.add(MyBean.buildMyBean("España"));
//			list.add(MyBean.buildMyBean("Canarias"));
//			Person person = new Person();
//			person.setName("Jesús María");
//			person.persist();
			
			java.util.List list = new PersonFinderFilter();//Person.listAll();

			Finder b = new Finder(this, list, "myFinder");
			b.addText("name");
			b.addBoolean("enabled");
			b.addBtn("@ejecutar");
			b.addBtn("@descargar");
			b.setProperties("name,surnames");
		}
	}

	public void menuEditor (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");

//		MyBean obj = (value != null ? (MyBean)value : MyBean.buildMyBean("Mi nombre"));
		Person obj = (value != null ? (Person)value : new Person());
		
		Editor b = new Editor(this, obj, "myEditor");
		b.setAttribute("CALLER", data.get("CALLER"));
		b.addText("name");
		b.addText("surnames");
		b.addTextArea("comment");
		b.addInteger("age");
		b.addBoolean("enabled");
		b.addBtn("ejecutar");
		b.addBtn("descargar");
	}
	
//	public void myFinder$onSearch (Event evt) {
//		System.out.println("myFinder$onSearch");
//		java.util.Map data = (java.util.Map)evt.getData();
//		java.util.List value = (java.util.List)data.get("value");
//
//		
//		reopen("myFinder");
//	}

//	public void myFinder$onCreate (Event evt) {
//		System.out.println("myFinder$onCreate");
//		
//		MyBean obj = MyBean.buildMyBean("New");
//		java.util.Map data = (java.util.Map)evt.getData();
//		data.put("value", obj);
//		menuEditor(evt);
//	}

//	public void myEditor$onSave (Event evt) {
//		System.out.println("myEditor$onSave");
//		java.util.Map data = (java.util.Map)evt.getData();
//		Object value = data.get("value");
//		if (!this.list.contains(value)) {
//			this.list.add(value);
//		}
//		ObjectAccess.close(evt.getTarget());
//	}

//	public void myFinder$onRead (Event evt) {
//		System.out.println("myFinder$onRead");
//		menuEditor(evt);
//	}
//	
//	public void myFinder$onUpdate (Event evt) {
//		System.out.println("myFinder$onUpdate");
//		menuEditor(evt);
//	}
	
//	public void myFinder$onDelete (Event evt) {
//		java.util.Map data = (java.util.Map)evt.getData();
//		Object value = data.get("value");
//		new ConfirmScreen(this, value, "myConfirm");
//		System.out.println("myFinder$onDelete");
//	}
	
//	public void myConfirm (Event evt) {
//		java.util.Map data = (java.util.Map)evt.getData();
//		Object value = data.get("value");
//		String op = (String)data.get("op");
//		if ("ok".equals(op)) {
//			if (this.list.contains(value)) {
//				this.list.remove(value);
//			}
////			this.app.reopen("myFinder");
//		}
//		ObjectAccess.close(evt.getTarget());
//	}
	
}
