package org.effortless.zkstrap;

import java.sql.ResultSet;

import org.effortless.core.StringUtils;
import org.effortless.orm.AbstractIdEntity;
import org.effortless.orm.AppCfg;
import org.effortless.orm.DbManager;
import org.effortless.orm.EagerPropertiesLoader;
import org.effortless.orm.Filter;
import org.effortless.orm.LazyPropertiesLoader;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.impl.EntityFilter;

public class Person extends AbstractIdEntity {

	public Person () {
		super();
	}

	protected void initiate () {
		super.initiate();
		initiateName();
		initiateSurnames();
		initiateComment();
		initiateAge();
	}

	protected String name;

	protected void initiateName() {
		this.name = null;
	}

	public String getName() {
		_loadOnDemand("name", this.name, Person.__DEFINITION__);
		return this.name;
	}

	public void setName(String newValue) {
		_loadOnDemand("name", this.name, Person.__DEFINITION__);
		_setProperty("name", this.name, this.name = newValue);
	}
	
	protected String surnames;

	protected void initiateSurnames() {
		this.surnames = null;
	}

	public String getSurnames() {
		_loadOnDemand("surnames", this.surnames, Person.__DEFINITION__);
		return this.surnames;//	protected String _columnsLazy () {
//		return StringUtils.concat(super._columnsLazy(), "CFG_PROPERTIES", ", ");
//	}

	}

	public void setSurnames(String newValue) {
		_loadOnDemand("surnames", this.surnames, Person.__DEFINITION__);
		_setProperty("surnames", this.surnames, this.surnames = newValue);
	}
	
	protected String comment;

	protected void initiateComment() {
		this.comment = null;
	}

	public String getComment() {
		_loadOnDemand("comment", this.comment, Person.__DEFINITION__);
		return this.comment;
	}

	public void setComment(String newValue) {
		_loadOnDemand("comment", this.comment, Person.__DEFINITION__);
		_setProperty("comment", this.comment, this.comment = newValue);
	}
	
	protected Integer age;

	protected void initiateAge() {
		this.age = null;
	}

	public Integer getAge() {
		_loadOnDemand("age", this.age, Person.__DEFINITION__);
		return this.age;
	}

	public void setAge(Integer newValue) {
		_loadOnDemand("age", this.age, Person.__DEFINITION__);
		_setProperty("age", this.age, this.age = newValue);
	}
	
	protected Boolean enabled;

	protected void initiateEnabled() {
		this.enabled = null;
	}

	public Boolean getEnabled() {
		_loadOnDemand("enabled", this.enabled, Person.__DEFINITION__);
		return this.enabled;
	}

	public void setEnabled(Boolean newValue) {
		_loadOnDemand("enabled", this.enabled, Person.__DEFINITION__);
		_setProperty("enabled", this.enabled, this.enabled = newValue);
	}
	
	protected Object _newInstance () {
		return new Person();
	}

	protected void _loadEager (Object target, DbManager db, ResultSet rs) {
		super._loadEager(target, db, rs);
		Person result = (Person)target;

		result.name = (String)__DEFINITION__.loadValue("NAME", rs);
		result._setupLoaded("name");

		result.surnames = (String)__DEFINITION__.loadValue("SURNAMES", rs);
		result._setupLoaded("surnames");

		result.comment = (String)__DEFINITION__.loadValue("COMMENT", rs);
		result._setupLoaded("comment");

		result.age = (Integer)__DEFINITION__.loadValue("AGE", rs);
		result._setupLoaded("age");

		result.enabled = (Boolean)__DEFINITION__.loadValue("ENABLED", rs);
		result._setupLoaded("enabled");
	}
	
	protected void _loadLazy (Object target, DbManager db, ResultSet rs) {
		super._loadLazy(target, db, rs);
		Person result = (Person)target;
	}
	
	protected static final Person _pivot = new Person();
	
	protected static final String _TABLE = "PERSON";
	protected static final String _SEQ = _TABLE + "_SEQ";
	public static final EntityDefinition __DEFINITION__ = new EntityDefinition()
		.setTableName(_TABLE)
		.setSequenceName(_SEQ)
		.setEntityClass(Person.class)
		.addParent(AbstractIdEntity.__DEFINITION__)
		.addProperty("name", "NAME", String.class, "255", null, "EAGER")
		.addProperty("surnames", "SURNAMES", String.class, "255", null, "EAGER")
		.addProperty("age", "AGE", Integer.class, null, null, "EAGER")
		.addProperty("enabled", "ENABLED", Boolean.class, null, null, "EAGER")
		.addProperty("comment", "COMMENT", String.class, "3072", null, "EAGER")

		.setDefaultOrderBy("surnames ASC, name ASC, id ASC")
		.setDefaultLoader(new EagerPropertiesLoader(Person._pivot))
		.addLoader(new LazyPropertiesLoader(Person._pivot));

		
		
	
	public static Filter listAll () {
		return EntityFilter.buildEntityFilter(Person.__DEFINITION__, Person.__DEFINITION__.getDefaultLoader());
	}

	protected Object[] _getAllParameterChanges() {
		return super._concatAllParameterChanges(
				new Object[] {
					new String[] {"NAME", "SURNAMES", "COMMENT", "AGE", "ENABLED"}, 
					new Object[] {this.name, this.surnames, this.comment, this.age, this.enabled}
				}, 
				super._getAllParameterChanges());
	}

	protected String _columnsEager () {
		return StringUtils.concat(super._columnsEager(), "NAME, SURNAMES, COMMENT, AGE, ENABLED", ", ");
	}

//	protected String _columnsLazy () {
//		return StringUtils.concat(super._columnsLazy(), "CFG_PROPERTIES", ", ");
//	}

	protected EntityDefinition _loadDefinition() {
		return Person.__DEFINITION__;
	}
	
	public void ejecutar () {
		System.out.println("EJECUTAR: NAME = " + getName());
	}
	
	public void descargar () {
		System.out.println("DESCARGAR: NAME = " + getName());
	}
	
}
