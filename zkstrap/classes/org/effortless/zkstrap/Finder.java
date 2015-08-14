package org.effortless.zkstrap;

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
	
	public void search () {
		System.out.println("finder search " + this._value);
		ObjectAccess.close(this);
	}
	
	public void create () {
		System.out.println("finder create " + this._value);
		ObjectAccess.close(this);
	}
	
	public void read () {
		System.out.println("finder read " + this._value);
		ObjectAccess.close(this);
	}
	
	public void update () {
		System.out.println("finder update " + this._value);
		ObjectAccess.close(this);
	}
	
	public void delete () {
		System.out.println("finder delete " + this._value);
		ObjectAccess.close(this);
	}
	
}
