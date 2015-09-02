package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class PropertyResourceSet extends Object implements ResourceDefinition {

	public PropertyResourceSet () {
		super();
	}
	
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
