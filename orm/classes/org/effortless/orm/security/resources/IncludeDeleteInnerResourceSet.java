package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class IncludeDeleteInnerResourceSet extends ScopeResourceSet implements ResourceDefinition {

	public IncludeDeleteInnerResourceSet(String module) {
		// TODO Auto-generated constructor stub
	}

	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
	
}
