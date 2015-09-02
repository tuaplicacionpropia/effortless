package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class ModuleResourceSet extends ScopeResourceSet implements ResourceDefinition {

	public ModuleResourceSet() {
		super();
	}
	
	public ModuleResourceSet(String module, String excludeActions) {
		this();
		setModule(module);
		setExcludeActions(excludeActions);
	}

	public boolean contains(Resource resource, Visitor visitor) {
		return visitor.contains(resource, this);
	}
	
}
