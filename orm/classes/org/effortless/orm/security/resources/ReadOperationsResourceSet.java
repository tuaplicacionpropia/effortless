package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class ReadOperationsResourceSet extends ScopeResourceSet implements ResourceDefinition {

	public ReadOperationsResourceSet(String module) {
		super();
		setModule(module);
	}

	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
