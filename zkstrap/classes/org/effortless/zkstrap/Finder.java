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
	
	protected String name;
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		this.name = newValue;
	}
	
	public void search () {
		System.out.println("finder search " + this._value);
		ObjectAccess.close(this);
	}
	
	public void create () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		Event evt = new Event("onCreate", this, data);
		ObjectAccess.execAppAction(evt);
//		ObjectAccess.close(this);
	}
	
	public void read () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", "SELECTED");
		Event evt = new Event("onRead", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder read " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void update () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", "SELECTED");
		Event evt = new Event("onUpdate", this, data);
		ObjectAccess.execAppAction(evt);
//		System.out.println("finder update " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void delete () {
		System.out.println("finder delete " + this._value);
		ObjectAccess.close(this);
	}
	
}
