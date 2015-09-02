package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class AdditionResourceSet extends ListResourceSet implements ResourceDefinition {

	public AdditionResourceSet () {
		super();
	}
	
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}

}
