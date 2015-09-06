package org.effortless.orm;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.effortless.core.ObjectUtils;
import org.effortless.core.StringUtils;
import org.effortless.core.UnusualException;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.impl.ChangeRegistry;
import org.effortless.orm.impl.EntityFilter;

public class AppCfg extends AbstractIdEntity {

	public AppCfg () {
		super();
	}

	protected void initiate () {
		super.initiate();
		initiateCreationDate();
		initiateLastModification();
		initiateEnable();
		initiateAuthor();
		initiateDefaultPageSize();
		initiateComment();
	}

	public void onPreCreate () {
		super.onPreCreate();
		Date creationDate = getCreationDate();
		if (creationDate == null) {
			creationDate = new Date();
			setCreationDate(creationDate);
		}
		Date lastModification = getLastModification();
		if (lastModification == null) {
			setLastModification(creationDate);
		}
		Integer defaultPageSize = getDefaultPageSize();
		if (defaultPageSize == null) {
			defaultPageSize = Integer.valueOf(25);
			setDefaultPageSize(defaultPageSize);
		}
		Boolean enable = getEnable();
		if (enable == null) {
			enable = Boolean.TRUE;
			setEnable(enable);
		}
	}
	
	public void onPreUpdate () {
		super.onPreUpdate();
		Date lastModification = new Date();
		setLastModification(lastModification);
	}
	
	
	protected Date creationDate;

	protected void initiateCreationDate() {
		this.creationDate = null;
	}

	public Date getCreationDate() {
		_loadOnDemand("creationDate", this.creationDate, __DEFINITION__);
		return this.creationDate;
	}

	public void setCreationDate(Date newValue) {
		_loadOnDemand("creationDate", this.creationDate, __DEFINITION__);
		_setProperty("creationDate", this.creationDate, this.creationDate = newValue);
	}
	
	protected Date lastModification;

	protected void initiateLastModification() {
		this.lastModification = null;
	}

	public Date getLastModification() {
		_loadOnDemand("lastModification", this.lastModification, __DEFINITION__);
		return this.lastModification;
	}

	public void setLastModification(Date newValue) {
		_loadOnDemand("lastModification", this.lastModification, __DEFINITION__);
		_setProperty("lastModification", this.lastModification, this.lastModification = newValue);
	}
	
	protected Boolean enable;

	protected void initiateEnable() {
		this.enable = null;
	}

	public Boolean getEnable() {
		_loadOnDemand("enable", this.enable, __DEFINITION__);
		return this.enable;
	}

	public void setEnable(Boolean newValue) {
		_loadOnDemand("enable", this.enable, __DEFINITION__);
		_setProperty("enable", this.enable, this.enable = newValue);
	}
	
	protected String author;

	protected void initiateAuthor() {
		this.author = null;
	}

	public String getAuthor() {
		_loadOnDemand("author", this.author, __DEFINITION__);
		return this.author;
	}

	public void setAuthor(String newValue) {
		_loadOnDemand("author", this.author, __DEFINITION__);
		_setProperty("author", this.author, this.author = newValue);
	}
	
	protected Integer defaultPageSize;

	protected void initiateDefaultPageSize() {
		this.defaultPageSize = null;
	}

	public Integer getDefaultPageSize() {
		_loadOnDemand("defaultPageSize", this.defaultPageSize, __DEFINITION__);
		return this.defaultPageSize;
	}

	public void setDefaultPageSize(Integer newValue) {
		_loadOnDemand("defaultPageSize", this.defaultPageSize, __DEFINITION__);
		_setProperty("defaultPageSize", this.defaultPageSize, this.defaultPageSize = newValue);
	}

	protected String comment;

	protected void initiateComment() {
		this.comment = null;
	}

	public String getComment() {
		_loadOnDemand("comment", this.comment, __DEFINITION__);
		return this.comment;
	}

	public void setComment(String newValue) {
		_loadOnDemand("comment", this.comment, __DEFINITION__);
		_setProperty("comment", this.comment, this.comment = newValue);
	}
	
	protected Map properties;

	protected void initiateProperties() {
		this.properties = new HashMap();
	}

	protected Map getProperties() {
		return this.properties;
	}

	protected void setProperties(Map newValue) {
		Map oldValue = getProperties();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.properties = newValue;
			firePropertyChange("properties", oldValue, newValue);
		}
	}

	public boolean existsProperty (String property) {
		boolean result = false;
		Map properties = getProperties();
		result = (properties != null && properties.containsKey(property));
		return result;
	}
	
	//@javax.persistence.Transient
	public Object getProperty (String property) {
		return getProperty(property, null);
	}

	public Object getProperty (String property, Object defaultValue) {
		Object result = defaultValue;
		Map properties = getProperties();
		if (properties != null && properties.containsKey(property)) {
			result = properties.get(property);
		}
		return result;
	}

	public Object setProperty (String property, Object newValue) {
		Object result = null;
		Object oldValue = getProperty(property);
		if (!ObjectUtils.equals(oldValue, newValue) || (newValue != null && oldValue == null)) {
			Map properties = getProperties();
			if (properties == null) {
				properties = new HashMap();
				setProperties(properties);
			}
			properties.put(property, newValue);
			
			_doChangeProperty(property, oldValue, newValue);
			this._changeRegistry.add("properties", oldValue, newValue);
			this._changeRegistry.firePropertyChange("properties", null, properties);
			
			firePropertyChange(property, oldValue, newValue);
		}
		result = oldValue;
		return result;
	}

	public static AppCfg getCurrent () {
		AppCfg result = null;
		result = (AppCfg)AppCfg.listAll().isTrue("enable").sortBy("creationDate DESC").findUnique();
		result = (result != null ? result : (AppCfg)AppCfg._pivot._newInstance());
		return result;
	}

	protected Object _newInstance () {
		return new AppCfg();
	}

	protected void _loadEager (Object target, DbManager db, ResultSet rs) {
		super._loadEager(target, db, rs);
		AppCfg result = (AppCfg)target;

		result.creationDate = (Date)__DEFINITION__.loadValue("CFG_CREATIONDATE", rs);
		result._setupLoaded("creationDate");

		result.lastModification = (Date)__DEFINITION__.loadValue("CFG_LASTMODIFICATION", rs);
		result._setupLoaded("lastModification");

		result.enable = (Boolean)__DEFINITION__.loadValue("CFG_ENABLE", rs);
		result._setupLoaded("enable");

		result.author = (String)__DEFINITION__.loadValue("CFG_AUTHOR", rs);
		result._setupLoaded("author");
		
		result.defaultPageSize = (Integer)__DEFINITION__.loadValue("CFG_DEFAULTPAGESIZE", rs);
		result._setupLoaded("defaultPageSize");

		result.comment = (String)__DEFINITION__.loadValue("CFG_COMMENT", rs);
		result._setupLoaded("comment");
	}
	
	protected void _loadLazy (Object target, DbManager db, ResultSet rs) {
		super._loadLazy(target, db, rs);
		AppCfg result = (AppCfg)target;

		result.properties = (Map)__DEFINITION__.loadValue("CFG_PROPERTIES", rs);
		result._setupLoaded("properties");
	}
	
	protected static final AppCfg _pivot = new AppCfg();
	
	protected static final String _TABLE = "APP_CFG";
	protected static final String _SEQ = _TABLE + "_SEQ";
	public static final EntityDefinition __DEFINITION__ = new EntityDefinition()
		.setTableName(_TABLE)
		.setSequenceName(_SEQ)
		.addParent(AbstractIdEntity.__DEFINITION__)
		.addProperty("creationDate", "CFG_CREATIONDATE", Date.class, null, null, "EAGER")
		.addProperty("lastModification", "CFG_LASTMODIFICATION", Date.class, null, null, "EAGER")
		.addProperty("enable", "CFG_ENABLE", Boolean.class, null, null, "EAGER")
		.addProperty("author", "CFG_AUTHOR", String.class, "255", null, "EAGER")
		.addProperty("defaultPageSize", "CFG_DEFAULTPAGESIZE", Integer.class, null, null, "EAGER")
		.addProperty("comment", "CFG_COMMENT", String.class, "3072", null, "EAGER")
		.addProperty("properties", "CFG_PROPERTIES", Map.class, null, null, "LAZY")

//	@javax.persistence.Column(name="CFG_PROPERTIES")
//	@Lob
//	@javax.persistence.Basic(fetch=javax.persistence.FetchType.EAGER)
//	@org.hibernate.annotations.Type(type="org.effortless.model.MapUserType")
//	protected Map<String, Object> getProperties() {

		
		.setDefaultOrderBy("creationDate DESC, id ASC")
		.setDefaultLoader(new EagerPropertiesLoader(AppCfg._pivot))
		.addLoader(new LazyPropertiesLoader(AppCfg._pivot));

		
		
	
	public static Filter listAll () {
//		return (Filter<AppCfg>)AbstractEntity.listBy(AppCfg.class);
		return EntityFilter.buildEntityFilter(__DEFINITION__, __DEFINITION__.getDefaultLoader());
	}

	@Override
	protected Object[] _getAllParameterChanges() {
		return super._concatAllParameterChanges(
				new Object[] {
					new String[] {"CFG_CREATIONDATE", "CFG_LASTMODIFICATION", "CFG_ENABLE", "CFG_AUTHOR", "CFG_DEFAULTPAGESIZE", "CFG_COMMENT", "CFG_PROPERTIES"}, 
					new Object[] {this.creationDate, this.lastModification, this.enable, this.author, this.defaultPageSize, this.comment, this.properties}
				}, 
				super._getAllParameterChanges());
	}

	protected String _columnsEager () {
		return StringUtils.concat(super._columnsEager(), "CFG_CREATIONDATE, CFG_LASTMODIFICATION, CFG_ENABLE, CFG_AUTHOR, CFG_DEFAULTPAGESIZE, CFG_COMMENT", ", ");
	}

	protected String _columnsLazy () {
		return StringUtils.concat(super._columnsLazy(), "CFG_PROPERTIES", ", ");
	}

	protected EntityDefinition _loadDefinition() {
		return AppCfg.__DEFINITION__;
	}
	
}
