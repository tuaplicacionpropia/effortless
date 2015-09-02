package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class MenuResourceSet extends ScopeResourceSet implements ResourceDefinition {

	public MenuResourceSet() {
		super();
	}
	
	
	public MenuResourceSet(String module) {
		this();
		setModule(module);
	}


	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
		
}
