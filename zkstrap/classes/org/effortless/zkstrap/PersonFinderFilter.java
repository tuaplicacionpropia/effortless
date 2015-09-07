package org.effortless.zkstrap;

import org.effortless.orm.impl.EntityFilter;

public class PersonFinderFilter extends EntityFilter {

	public PersonFinderFilter () {
		super(Person.__DEFINITION__, Person.__DEFINITION__.getDefaultLoader());
	}
	
	protected void initiate () {
		super.initiate();
		initiateName();
	}
	
	protected String name;
	
	protected void initiateName () {
		this.name = null;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		_setProperty("name", this.name, this.name = newValue);
	}
	
	protected String _buildQuery (boolean count) {
		String result = null;
		
		eq("deleted", Boolean.FALSE);
		ilk("name", this.name);
		
		result = super._buildQuery(count);
		return result;
	}

	
	
}
