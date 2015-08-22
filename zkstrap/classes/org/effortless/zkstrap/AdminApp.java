package org.effortless.zkstrap;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.MethodUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

public class AdminApp extends Screen {

	public AdminApp () {
		super();
		initiate();
	}
	
	protected void initiate () {
//		this.setZclass("label label-default");
//		this.setSclass("default");
	}
	
	public String label = ""; // a data member

	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String newValue) {
		if (!this.label.equals(newValue)) {
			this.label = newValue;
			smartUpdate("label", this.label);
		}
	}

	
	public String skin = ""; // a data member

	public String getSkin() {
		return this.skin;
	}
	
	public void setSkin(String newValue) {
		if (!this.skin.equals(newValue)) {
			this.skin = newValue;
			smartUpdate("skin", this.skin);
		}
	}
	
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);
		render(renderer, "label", this.label);
		render(renderer, "skin", this.skin);
	}

	protected boolean _checkMenu (Component child) {
		boolean result = false;
		MenuBs menu = null; try { menu = (MenuBs)child; } catch (ClassCastException e) {}
		result = (menu != null);
		return result;
	}

	protected void _hideLastChild (Component child) {
		if (!_checkMenu(child)) {
			Component lastChild = this.getLastChild();
			if (lastChild != null && !_checkMenu(lastChild)) {
				lastChild.setVisible(false);
			}
		}
	}
	
	static {
		addClientEvent(AdminApp.class, Events.ON_CLICK, CE_IMPORTANT|CE_REPEAT_IGNORE);
	}


	public Screen getScreen(String name) {
		Screen result = null;
		if (name != null) {
			java.util.List children = this.getChildren();
			int length = (children != null ? children.size() : 0);
			for (int i = 0; i < length; i++) {
				Object child = children.get(i);
				Screen item = null; try { item = (Screen)child; } catch (ClassCastException e) {}
				if (item != null && name.equals(item.getName())) {
					result = item;
					break;
				}
			}
		}
		return result;
	}

	public void close(Component cmp) {
		if (cmp != null) {
			this.removeChild(cmp);
			cmp.detach();
			Component lastChild = getLastChild();
			if (!this._checkMenu(lastChild)) {
				lastChild.setVisible(true);
				lastChild.invalidate();
			}
		}
	}

	public boolean reopen(String name) {
		boolean result = false;
		Finder screen = (Finder)this.getScreen(name);
		if (screen != null) {
			screen.setVisible(true);
			screen.invalidate();
			result = true;
		}
		return result;
	}
	
//	public void service(org.zkoss.zk.au.AuRequest request, boolean
//			everError) {
//			final String cmd = request.getCommand();
////			System.out.println("SSSSSSSSERVICE >>>>>>>>>>>" + cmd);
//			if (cmd.equals(ClearEvent.NAME)) {
//			ClearEvent evt = ClearEvent.getClearEvent(request);
//			_cleared = evt.getCleared();
////			setCleared(!_cleared);
//			Events.postEvent(evt);
//			} else
//			super.service(request, everError);
//			}


	
//	public void setBclass (String newValue) {
//		setZclass("label label-" + newValue);
//	}
	
//	public void setSclass (String newValue) {
//		newValue = "label-" + newValue;
//		super.setSclass(newValue);
//	}
	
	
	
	public MenuBs addMenu (String label, EventListener listener) {
		MenuBs result = new MenuBs();
		result.setLabel(label);
		result.addEventListener(Events.ON_CLICK, listener);
		appendChild(result);
		return result;
	}
	
	public void addMenu (String name) {
		final MenuBs menu = new MenuBs();
		menu.setName(name);
		final AdminApp _this = this;
		menu.addEventListener(Events.ON_CLICK, new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				// TODO Auto-generated method stub
				java.util.Map data = new java.util.HashMap();
				data.put("name", menu.getName());
				data.put("nativeEvent", evt);
				evt = new Event("onClick", menu, data);
				
				ObjectAccess.execAppAction(evt);
//				_this.clickMenu(evt, menu.getName());
			}
			
		});
		appendChild(menu);
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
	
	
	
	public boolean insertBefore (Component child, Component refChild) {
		boolean result = false;
		_hideLastChild(child);
		result = super.insertBefore(child, refChild);
		return result;
	}
	
	protected Component buildSkeleton(Component parent) {
		return this;
	}
	

	protected void updateCmpRoot() {
		this.cmpRoot = this;
	}

	protected Object ctrl;
	
	public Object getCtrl () {
		return this.ctrl;
	}
	
	public void setCtrl(Object newValue) {
		this.ctrl = newValue;
	}

}
