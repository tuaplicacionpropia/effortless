package org.effortless.zkstrap;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class ConfirmScreen extends BaseEditor {

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
	
}
