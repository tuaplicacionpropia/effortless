package org.effortless.zkstrap;

public class Editor extends BaseEditor {

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
		System.out.println("GUARDANDO " + this._value);
		ObjectAccess.close(this);
	}
	
	public void cancel () {
		System.out.println("CANCELANDO " + this._value);
		ObjectAccess.close(this);
	}
	
}
