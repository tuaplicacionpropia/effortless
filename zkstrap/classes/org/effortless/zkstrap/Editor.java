package org.effortless.zkstrap;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;

public class Editor extends Screen {

	public Editor () {
		super();
//		org.zkoss.zk.ui.util.Template t = null;
//		
//		t.	
//		div filter
//		div top_buttons
//		div list
//		div bottom buttons
	}

	public Editor (Component parent, Object value, String name) {
		super(parent, value, name);
	}
	
	
	
	public void save () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this._value);
		
		Event evt = new Event("onSave", this, data);
		try {
			ObjectAccess.execAppAction(evt);
		}
		catch (UiException e) {
			Object _cause = e.getCause();
			NoSuchMethodException cause = null; try { cause = (NoSuchMethodException)_cause; } catch (ClassCastException e2) {}
			if (cause != null) {
				Object caller = getAttribute("CALLER");

				String method = this.name +"$" + "onSave";
				try {
					MethodUtils.invokeExactMethod(caller, method, new Object[] {evt}, new Class[] {Event.class});
				} catch (NoSuchMethodException e1) {
					throw new UiException(e1);
				} catch (IllegalAccessException e1) {
					throw new UiException(e1);
				} catch (InvocationTargetException e1) {
					throw new UiException(e1);
				}
			}
			else {
				throw e;
			}
		}
//		
//		System.out.println("GUARDANDO " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void cancel () {
		System.out.println("CANCELANDO " + this._value);
		ObjectAccess.close(this);
	}

	protected Component buildSkeleton(Component parent) {
		Component result = null;

		result = this;//super.buildSkeleton(parent);
		
		buildLayoutContent(result);
		buildLayoutButtons(result);
		
		setStatus(CONTENT);
		result = this.layoutContent;
		
		return result;
	}
	
	protected void buildLayoutContent (Component parent) {
		Layout layoutContent = null;
		{
			setStatus(INIT);
			
			layoutContent = new Layout();
			parent.appendChild(layoutContent);
			this.layoutContent = layoutContent;
			setStatus(CONTENT);
		}
	}
	
	protected void buildLayoutButtons (Component parent) {
		Layout layoutButtons = null;
		{
			setStatus(INIT);
			
			layoutButtons = new Layout();
			parent.appendChild(layoutButtons);
			this.layoutButtons = layoutButtons;
			setStatus(BUTTONS);
			
//			addBtn("#save");
//			Component btnSave = this.lastCmp;
//			this.addBtn("#cancel");
//			this.btnSave = btnSave;
		}
	}
	
	

	protected void updateCmpRoot() {
		if (this.status == INIT) {
			this.cmpRoot = this.getFirstChild();
		}
		else if (this.status == CONTENT) {
			this.cmpRoot = this.layoutContent;
		}
		else if (this.status == BUTTONS) {
			this.cmpRoot = this.layoutButtons;
		}
	}

	protected boolean doInsertBefore(Component child, Component refChild) {
		boolean result = false;
		
		Btn btn = null; try { btn = (Btn)child; } catch (ClassCastException e) {}
		
		if (btn != null) {
			setStatus(BUTTONS);
//			refChild = (refChild != null ? refChild : this.btnSave);
			refChild = (refChild != null ? refChild : this.lastBtn);
			this.lastBtn = child;
		}
		else {
			setStatus(CONTENT);
		}
		
		result = super.doInsertBefore(child, refChild);

		return result;
	}
	
	
	
	protected Component layoutContent;
	protected Component layoutButtons;
//	protected Component btnSave;
	protected Component lastBtn;
	
	protected static final byte CONTENT = 1;
	protected static final byte BUTTONS = 2;

	static {
		addClientEvent(Finder.class, "onReq", CE_IMPORTANT|CE_REPEAT_IGNORE);
	}
	
	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
		final Component comp = request.getComponent();
		final Map data = request.getData();
		final String cmd = (String)data.get("command");
		System.out.println(">>>>>>>>> command = " + cmd);
		if ("ok".equals(cmd)) {
			this.save();
		}
		else if ("cancel".equals(cmd)) {
			this.cancel();
		}
		else {
			super.service(request, everError);
		}
	}

	public void onSelect (Event evt) {
//		Input _list = null; try { _list = (Input)this.listTable; } catch (ClassCastException e) {}
//		_list.onSelect(evt);
		java.util.Map data = (java.util.Map)evt.getData();
		Object select = data.get("value");
		System.out.println("SELECCIONADO " + select);
	}
	
	
}
