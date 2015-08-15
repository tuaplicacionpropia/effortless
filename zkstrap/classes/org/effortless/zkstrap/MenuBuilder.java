package org.effortless.zkstrap;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.MethodUtils;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

public class MenuBuilder extends PageBuilder {

	protected MenuBuilder () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.ctrl = null;
	}
	
	public static MenuBuilder create (AdminApp app, Object ctrl) {
		MenuBuilder result = null;
		result = new MenuBuilder();
		
		result.cmpRoot = app;
		result.parentBuilder = null;
		result.ctrl = ctrl;
		
		return result;
	}
	
	protected Object ctrl;

	public MenuBs addMenu (String label, EventListener listener) {
		MenuBs result = new MenuBs();
		result.setLabel(label);
		result.addEventListener(Events.ON_CLICK, listener);
		this.cmpRoot.appendChild(result);
		return result;
	}
	
	public void addMenu (String name) {
		final MenuBs menu = new MenuBs();
		menu.setName(name);
		final MenuBuilder _this = this;
		menu.addEventListener(Events.ON_CLICK, new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				// TODO Auto-generated method stub
				_this.clickMenu(evt, menu.getName());
			}
			
		});
		this.cmpRoot.appendChild(menu);
	}
	
	protected void clickMenu (Event evt, String menu) {
		String method = menu;
		try {
			MethodUtils.invokeExactMethod(this.ctrl, method, new Object[] {evt}, new Class[] {Event.class});
		} catch (NoSuchMethodException e) {
			throw new UiException(e);
		} catch (IllegalAccessException e) {
			throw new UiException(e);
		} catch (InvocationTargetException e) {
			throw new UiException(e);
		}
//		ObjectAccess.runMethodDirectly(this.ctrl, method);
	}
	
}
