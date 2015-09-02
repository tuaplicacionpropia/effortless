package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class OperationResourceSet extends ScopeResourceSet implements ResourceDefinition {

	public OperationResourceSet() {
		super();
	}

	public OperationResourceSet(String operation) {
		this();
		setOperations(operation);
	}

	public OperationResourceSet(String operation, String module) {
		this();
		setOperations(operation);
		setModule(module);
	}

	public OperationResourceSet(String operation, String module,
			boolean excludeViews) {
		this();
		setOperations(operation);
		setModule(module);
		setExcludeAllViews(excludeViews);
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
