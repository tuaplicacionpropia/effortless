package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class SubtractResourceSet extends ListResourceSet implements ResourceDefinition {

	public SubtractResourceSet () {
		super();
	}
	
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
