package org.effortless.zkstrap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class Finder extends BaseEditor {

	public Finder () {
		super();
//		org.zkoss.zk.ui.util.Template t = null;
//		
//		t.	
//		div filter
//		div top_buttons
//		div list
//		div bottom buttons
	}

	protected Object selection;
	
	public Object getSelection () {
		return selection;
	}
	
	public void setSelection (Object newValue) {
		this.selection = newValue;
	}
	
	public void search () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this._value);
		data.put("selection", this.selection);
		data.put("op", "search");
		Event evt = new Event("onSearch", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder search " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void create () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "create");
		Event evt = new Event("onCreate", this, data);
		ObjectAccess.execAppAction(evt);
//		ObjectAccess.close(this);
	}
	
	public void read () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "read");
		Event evt = new Event("onRead", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder read " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void update () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this.selection);
		data.put("op", "update");
		Event evt = new Event("onUpdate", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder update " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void delete () {
//		System.out.println("finder delete " + this._value);

		if (this.selection != null) {
			java.util.Map data = new java.util.HashMap();
			data.put("name", this.name);
			data.put("value", this.selection);
			data.put("op", "delete");
			Event evt = new Event("onDelete", this, data);
			ObjectAccess.execAppAction(evt);
		}
		
		
//		ObjectAccess.close(this);
	}
	
	public void onSelect (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object select = data.get("value");
		setSelection(select);
	}
	
	public void executeCustom (String action) {
		if (this.selection != null) {
			ObjectAccess.runMethodDirectly(this.selection, action);
			this.invalidate();
		}
	}
	
}
