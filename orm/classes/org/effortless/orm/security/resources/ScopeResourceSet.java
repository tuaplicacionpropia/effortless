package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public abstract class ScopeResourceSet extends Object implements ResourceDefinition {

	public ScopeResourceSet () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateModule();
		initiateUnit();
		initiateEntity();
		initiateExcludeEntities();
		initiateExcludeAllViews();
		initiateExcludeActions();
	}
	
	protected String excludeActions;

	protected void initiateExcludeActions() {
		this.excludeActions = null;
	}

	public String getExcludeActions() {
		return this.excludeActions;
	}

	public void setExcludeActions(String newValue) {
		this.excludeActions = newValue;
	}
	
	protected boolean excludeAllViews;

	protected void initiateExcludeAllViews() {
		this.excludeAllViews = false;
	}

	public boolean getExcludeAllViews() {
		return this.excludeAllViews;
	}

	public void setExcludeAllViews(boolean newValue) {
		this.excludeAllViews = newValue;
	}
	
	protected String excludeEntities;

	protected void initiateExcludeEntities() {
		this.excludeEntities = null;
	}

	public String getExcludeEntities() {
		return this.excludeEntities;
	}

	public void setExcludeEntities(String newValue) {
		this.excludeEntities = newValue;
	}

	protected String module;

	protected void initiateModule() {
		this.module = null;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String newValue) {
		this.module = newValue;
	}

	protected String unit;

	protected void initiateUnit() {
		this.unit = null;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String newValue) {
		this.unit = newValue;
	}
	
	protected String entity;

	protected void initiateEntity() {
		this.entity = null;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String newValue) {
		this.entity = newValue;
	}
	
}
