package org.effortless.zkstrap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class ConfirmScreen extends Screen {

	public ConfirmScreen () {
		super();
//		org.zkoss.zk.ui.util.Template t = null;
//		
//		t.	
//		div filter
//		div top_buttons
//		div list
//		div bottom buttons
	}

	public ConfirmScreen (Component parent, Object value, String name) {
		super(parent, value, name);
	}

	public void ok () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this._value);
		data.put("op", "ok");
		
		Event evt = new Event("onOk", this, data);
		ObjectAccess.execAppAction(evt);
//		
//		System.out.println("GUARDANDO " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void cancel () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this._value);
		data.put("op", "cancel");
		
		Event evt = new Event("onCancel", this, data);
		ObjectAccess.execAppAction(evt);
	}

	protected Component buildSkeleton(Component parent) {
		Component result = null;

		result = super.buildSkeleton(parent);
		
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
			
			addBtn("#ok");
			addBtn("#cancel");
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
		}
		else {
			setStatus(CONTENT);
		}
		
		result = super.doInsertBefore(child, refChild);

		return result;
	}
	
	
	
	protected Component layoutContent;
	protected Component layoutButtons;
	
	protected static final byte CONTENT = 1;
	protected static final byte BUTTONS = 2;

}
