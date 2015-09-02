package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class RoleResourceSet extends AdditionResourceSet implements ResourceDefinition {

	protected RoleResourceSet () {
		super();
		initiate();
	}
	
	public RoleResourceSet (Object role) {
		this(role.toString(), role);
	}
	
	public RoleResourceSet (String id, Object role) {
		this();
		this.id = id;
		this.role = role;
	}
	
	protected void initiate() {
		initiateId();
		initiateRole();
	}
	

	
	protected String id;
	
	protected void initiateId () {
		this.id = null;
	}
	
	public String getId () {
		return this.id;
	}
	
	protected void setId (String newValue) {
		this.id = newValue;
	}
	
	protected Object role;
	
	protected void initiateRole () {
		this.id = null;
	}
	
	public Object getRole () {
		return this.role;
	}
	
	protected void setRole (Object newValue) {
		this.role = newValue;
	}
	
	@Override
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}

}
