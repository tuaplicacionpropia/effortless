package org.effortless.orm.security;

/*
 *Object
 *Entidad Class FullName
 *ActionName
 *
 *
 *
 *
 */
public class Resource extends Object {

	public Resource () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateObject();
		initiateClassName();
		initiateAction();
		
		
		initiateAllow();
//		initiateErrorTitle();
//		initiateErrorDescription();
	}
	
	protected Object object;//Entity, Filter, View, Class
	
	protected void initiateObject () {
		this.object = null;
	}
	
	public Object getObject () {
		return this.object;
	}
	
	public void setObject (Object newValue) {
		this.object = newValue;
	}
	
	protected String className;
	
	protected void initiateClassName () {
		this.className = null;
	}
	
	public String getClassName () {
		return this.className;
	}
	
	public void setClassName (String newValue) {
		this.className = newValue;
	}
	
	protected String action;
	
	protected void initiateAction () {
		this.action = null;
	}
	
	public String getAction () {
		return this.action;
	}
	
	public void setAction (String newValue) {
		this.action = newValue;
	}
	
	
	
	

	
	
	
	protected Boolean allow;
	
	protected void initiateAllow () {
		this.allow = null;
	}
	
	public Boolean isAllow() {
		return this.allow;
	}
	
	public void setAllow (Boolean newValue) {
		this.allow = newValue;
	}

	
	
	
	
//	protected String errorTitle;
//	
//	protected void initiateErrorTitle () {
//		this.errorTitle = null;
//	}
//	
//	public String getErrorTitle () {
//		return this.errorTitle;
//	}
//	
//	public void setErrorTitle (String newValue) {
//		this.errorTitle = newValue;
//	}
//	
//	protected String errorDescription;
//	
//	protected void initiateErrorDescription () {
//		this.errorDescription = null;
//	}
//	
//	public String getErrorDescription () {
//		return this.errorDescription;
//	}
//	
//	public void setErrorDescription (String newValue) {
//		this.errorDescription = newValue;
//	}

}
