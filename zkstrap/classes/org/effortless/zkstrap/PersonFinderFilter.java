package org.effortless.zkstrap;

import org.effortless.orm.impl.EntityFilter;

public class PersonFinderFilter extends EntityFilter {

	public PersonFinderFilter () {
		super(Person.__DEFINITION__, Person.__DEFINITION__.getDefaultLoader());
	}
	
	protected void initiate () {
		super.initiate();
		initiateName();
		initiateEnabled();
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
	
	protected Boolean enabled;
	
	protected void initiateEnabled () {
		this.enabled = Boolean.TRUE;
	}
	
	public Boolean getEnabled () {
		return this.enabled;
	}
	
	public void setEnabled (Boolean newValue) {
		_setProperty("enabled", this.enabled, this.enabled = newValue);
	}
	
	protected String _buildQuery (boolean count) {
		String result = null;
		
		eq("deleted", Boolean.FALSE);
		
		eq("enabled", this.enabled);
		ilk("name", this.name);
		
		result = super._buildQuery(count);
		return result;
	}

	
	
}
