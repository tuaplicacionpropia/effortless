package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public interface ResourceDefinition {

	public boolean contains (Resource resource, Visitor visitor);
	
}
