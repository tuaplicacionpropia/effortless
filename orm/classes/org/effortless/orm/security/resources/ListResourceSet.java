package org.effortless.orm.security.resources;

import java.util.ArrayList;
import java.util.List;

public abstract class ListResourceSet extends Object implements ResourceDefinition {

	protected ListResourceSet () {
		super();
		initiate();
	}
	
	protected void initiate() {
		initiateItems();
	}
	
	protected List<ResourceDefinition> items;
	
	protected void initiateItems () {
		this.items = null;
	}
	
	protected List<ResourceDefinition> getItems () {
		return this.items;
	}
	
	protected void setItems (List<ResourceDefinition> newValue) {
		this.items = newValue;
	}
	
	public ListResourceSet add (ResourceDefinition resourceSet) {
		if (resourceSet != null) {
			this.items = (this.items != null ? this.items : new ArrayList<ResourceDefinition>());
			this.items.add(resourceSet);
		}
		return this;
	}

}
