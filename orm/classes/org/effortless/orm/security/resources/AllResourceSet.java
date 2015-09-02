package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class AllResourceSet extends Object implements ResourceDefinition {

	public AllResourceSet () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
