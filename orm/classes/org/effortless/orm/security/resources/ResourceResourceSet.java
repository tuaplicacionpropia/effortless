package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class ResourceResourceSet extends Object implements ResourceDefinition {

	public ResourceResourceSet () {
		super();
	}

	public ResourceResourceSet (ResourceDefinition resourceSet) {
		this();
		setResourceSet(resourceSet);
	}

	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
	
	protected ResourceDefinition resourceSet;

	protected void initiateResourceSet() {
		this.resourceSet = null;
	}

	public ResourceDefinition getResourceSet() {
		return this.resourceSet;
	}

	public void setResourceSet(ResourceDefinition newValue) {
		this.resourceSet = newValue;
	}
	
}
