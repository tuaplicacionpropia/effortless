package org.effortless.core;

public class UnusualException extends RuntimeException {

	public UnusualException () {
		this(null, null);
	}
	
	public UnusualException (String msg) {
		this(msg, null);
	}
	
	public UnusualException (Throwable cause) {
		this(null, cause);
	}
	
	public UnusualException (String msg, Throwable cause) {
		super(msg, cause);
		initiate();
	}
	
	protected void initiate () {
		initiateAttributes();
	}
	
	protected java.util.Map attributes;
	
	protected void initiateAttributes () {
		this.attributes = null;
	}
	
	protected java.util.Map doGetAttributes () {
		if (this.attributes == null) {
			this.attributes = new java.util.HashMap();
		}
		return this.attributes;
	}
	
	public Object getAttribute (String name) {
		Object result = null;
		result = (name != null ? doGetAttributes().get(name) : null);
		return result;
	}
	
	public Object setAttribute (String name, Object newValue) {
		Object result = null;
		result = (name != null ? doGetAttributes().put(name, newValue) : null);
		return result;
	}
	
}
