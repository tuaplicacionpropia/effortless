package org.effortless.orm;

import java.sql.ResultSet;

public abstract class AbstractEnabledPersistEntity extends AbstractIdEntity implements EnabledPersistEntity {

	public AbstractEnabledPersistEntity () {
		super();
	}

	public AbstractEnabledPersistEntity (Long id) {
		super(id);
	}
	
	protected void initiate () {
		super.initiate();
		initiateEnabled();
	}
	
	@Override
	public void create(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		Boolean enabled = isEnabled();
		if (enabled != null && enabled.booleanValue()) {
			super.create(validate, enableOnPre, enableOnPost);
		}
	}

	@Override
	public void update(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		Boolean enabled = isEnabled();
		if (enabled != null && enabled.booleanValue()) {
			setDeleted(Boolean.FALSE);
			super.update(properties, validate, enableOnPre, enableOnPost);
		}
		else {
			super.delete(validate, enableOnPre, enableOnPost);
		}
	}
	

	protected transient Boolean enabled;
	
	protected void initiateEnabled () {
		this.enabled = null;
	}
	
	public Boolean isEnabled () {
		return this.enabled;
	}
	
	public Boolean getEnabled () {
		return this.enabled;
	}
	
	public void setEnabled (Boolean newValue) {
		_setProperty("enabled", this.enabled, this.enabled = newValue);
	}

	protected void _loadFieldDelete(AbstractIdEntity result) {
		AbstractEnabledPersistEntity item = (AbstractEnabledPersistEntity)result;
		boolean deleted = (item.deleted != null && item.deleted.booleanValue());
		Boolean enabled = Boolean.valueOf(!deleted);
		item.enabled = enabled;
		item._setupLoaded("enabled");
	}
	
}
