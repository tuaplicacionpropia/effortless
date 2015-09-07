package org.effortless.zkstrap;

import java.sql.ResultSet;

import org.effortless.core.StringUtils;
import org.effortless.orm.AbstractIdEntity;
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
		.addParent(AbstractIdEntity.__DEFINITION__)
		.addProperty("name", "NAME", String.class, "255", null, "EAGER")
		.addProperty("surnames", "SURNAMES", String.class, "255", null, "EAGER")
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
					new String[] {"NAME", "SURNAMES", "COMMENT"}, 
					new Object[] {this.name, this.surnames, this.comment}
				}, 
				super._getAllParameterChanges());
	}

	protected String _columnsEager () {
		return StringUtils.concat(super._columnsEager(), "NAME, SURNAMES, COMMENT", ", ");
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
