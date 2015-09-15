package org.effortless.zkstrap;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

import org.effortless.orm.FilterList;
import org.effortless.orm.MyBean;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.RichletConfig;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.metainfo.LanguageDefinition;
import org.zkoss.zul.Label;

public class MyApp2 extends AdminApp {

	public MyApp2 () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
	}
	
	public void buildApp() {
		super.buildApp();
		setAdminPage("login");
		setLabel("Mi aplicación2");
//		setAttribute("INC", Integer.valueOf(0));
		
		addMenu("menuInicio2");
	}

	public void menuInicio2 (Event evt) {
		Integer inc = (Integer)getAttribute("INC");
		inc = Integer.valueOf(inc.intValue() + 1);
		setAttribute("INC", inc);
		Label label = new Label();
		label.setValue("" + inc.intValue() + ". HOLA DON PEPITO");
		appendChild(label);
	}
	
}
