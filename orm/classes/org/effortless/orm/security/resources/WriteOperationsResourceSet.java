package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class WriteOperationsResourceSet extends ScopeResourceSet implements ResourceDefinition {

	public WriteOperationsResourceSet () {
		super();
	}
	
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
