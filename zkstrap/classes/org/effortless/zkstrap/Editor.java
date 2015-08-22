package org.effortless.zkstrap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

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

	public void save () {
		java.util.Map data = new java.util.HashMap();
		data.put("name", this.name);
		data.put("value", this._value);
		
		Event evt = new Event("onSave", this, data);
		ObjectAccess.execAppAction(evt);
//		
//		System.out.println("GUARDANDO " + this._value);
//		ObjectAccess.close(this);
	}
	
	public void cancel () {
		System.out.println("CANCELANDO " + this._value);
		ObjectAccess.close(this);
	}
	
}
