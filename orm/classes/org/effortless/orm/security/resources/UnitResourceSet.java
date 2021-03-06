package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class UnitResourceSet extends ScopeResourceSet implements ResourceDefinition {

	public UnitResourceSet () {
		super();
	}

	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
	
	protected String operations;

	protected void initiateOperations() {
		this.operations = null;
	}

	public String getOperations() {
		return this.operations;
	}

	public void setOperations(String newValue) {
		this.operations = newValue;
	}
	
}
