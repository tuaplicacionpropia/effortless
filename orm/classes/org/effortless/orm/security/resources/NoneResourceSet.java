package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class NoneResourceSet extends Object implements ResourceDefinition {

	public NoneResourceSet () {
		super();
	}
	
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
