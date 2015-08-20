package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;

public class AdminApp extends org.zkoss.zk.ui.HtmlBasedComponent {

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

	public boolean appendChild (Component child) {
		boolean result = false;
		_hideLastChild(child);
		result = super.appendChild(child);
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
	
	public boolean insertBefore (Component child, Component refChild) {
		boolean result = false;
		_hideLastChild(child);
		result = super.insertBefore(child, refChild);
		return result;
	}
	
	
	static {
		addClientEvent(AdminApp.class, Events.ON_CLICK, CE_IMPORTANT|CE_REPEAT_IGNORE);
	}


	public BaseEditor getScreen(String name) {
		BaseEditor result = null;
		if (name != null) {
			java.util.List children = this.getChildren();
			int length = (children != null ? children.size() : 0);
			for (int i = 0; i < length; i++) {
				Object child = children.get(i);
				BaseEditor item = null; try { item = (BaseEditor)child; } catch (ClassCastException e) {}
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
	
}
