package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class NotResourceSet extends Object implements ResourceDefinition {

	public NotResourceSet () {
		super();
	}
	
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
