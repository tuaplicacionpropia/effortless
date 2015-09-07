package org.effortless.orm.impl;

import java.sql.ResultSet;

import org.effortless.orm.AbstractIdEntity;
import org.effortless.orm.DbManager;
import org.effortless.orm.AbstractFilter;
import org.effortless.orm.Entity;
import org.effortless.orm.MySession;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.definition.EnumPropertyColumn;
import org.effortless.orm.definition.PropertyEntity;
import org.effortless.orm.definition.ReferencePropertyColumn;
import org.effortless.orm.definition.SinglePropertyColumn;

public class EntityFilter extends AbstractFilter {

	protected EntityFilter () {
		super();
	}
	
	public EntityFilter (String table, DbManager db, PropertiesLoader loader, String selectClausule, Class<?>... selectTypes) {
		super(table, db, selectClausule, selectTypes);
		this._loader = loader;
	}
	
	public EntityFilter (EntityDefinition def) {
		super();
		this._initialize(def);
	}
	
	protected void _initialize (EntityDefinition def) {
		DbManager.trySetup(def);
		DbManager db = MySession.getDb();
		String table = def.getTableName();
		PropertiesLoader loader = def.getDefaultLoader();
		String columns = def.getPrimaryKey().getColumnName() + ", " + loader.getColumns();//"ID, NOMBRE"
		Class<?>[] types = null;//decoder.getTypes();//Long.class, String.class

		setPageIndex(0);
		setPageSize(25);
		orderBy(def.getDefaultOrderBy());
			
		super._init(table, db, columns, types);
		this._loader = loader;
		this._def = def;
	}
	
	protected Class<?> doGetFilterClass () {
		Class<?> result = null;
//		String entityClass = this._def.getEntityClass();
//		result = org.effortless.core.ClassUtils.loadClassRE(entityClass);
		result = this._def.getEntityClass();
		return result;
	}
	
	
	public static EntityFilter buildEntityFilter (EntityDefinition def) {
		EntityFilter result = null;
		result = new EntityFilter(def);
//		result = EntityFilter.buildEntityFilter(def, def.getDefaultLoader());
		return result;
	}

	public static EntityFilter buildEntityFilter (EntityDefinition def, PropertiesLoader loader) {
		EntityFilter result = null;
		result = new EntityFilter(def);
		result._loader = loader;
//		String columns = def.getPrimaryKey().getColumnName() + ", " + loader.getColumns();//"ID, NOMBRE"
//		Class<?>[] types = null;//decoder.getTypes();//Long.class, String.class
//		DbManager db = MySession.getDb();
//		result = new EntityFilter(def.getTableName(), db, loader, columns, types);
//		result.setPageIndex(0);
//		result.setPageSize(25);
//		result.orderBy(def.getDefaultOrderBy());
		return result;
	}
	
	protected EntityDefinition _def;

	protected PropertiesLoader _loader;
	
	public PropertiesLoader loadDecoder () {
		return this._loader;
	}
	
	public void setupDecoder (PropertiesLoader newValue) {
		this._loader = newValue;
	}
	
	protected void processItem(ResultSet rs) {
		Object decodeItem = decodeItem(rs);
		this._page.add(decodeItem);
	}
	
	@Override
	protected Object decodeItem(ResultSet rs) {
		Object result = null;
		Object item = this._loader.newItem(this._db, rs);
		this._loader.load(item, this._db, rs);
		result = item;
		return result;
	}
	
	protected void applyListener (Entity o) {
		if (o != null && !o.containsPropertyChangeListener(this)) {
			o.addPropertyChangeListener(this);
		}
	}

	protected void doCallOnRead(Entity item) {
		if (item != null) {
			Boolean callOnRead = (Boolean)item.getAttribute(Entity.CALL_ON_READ, Boolean.FALSE);
			if (callOnRead == null || callOnRead.booleanValue() == false) {
				item.onRead();
				item.setAttribute(Entity.CALL_ON_READ, Boolean.TRUE);
			}
		}
	}

	protected void _persistItem (Entity item, boolean validate) {
		if (item.hasBeenCreated()) {
			item.create(validate);
		}
		else if (item.hasBeenDeleted()) {
			item.delete(validate);
		}
		else if (item.hasBeenChanged()) {
			item.update(validate);
		}
	}

	protected void _persistItem (Entity item) {
		item.persist();
	}

	protected void _deleteItem (Entity item) {
		item.delete();
	}

	protected void _eraseItem (Entity item) {
		item.erase();
	}

	protected String _decodeProperty (String name, Object param) {
		String result = null;
		result = "o." + name;
		PropertyEntity property = this._def.getProperty(name);
		try {
			SinglePropertyColumn single = (SinglePropertyColumn)property;
			String columnName = single.getColumnName();
			result = "o." + columnName;
		}
		catch (ClassCastException e) {
		}
		return result;
	}
	
	protected Object _decodeParam (String name, Object param) {
		Object result = null;
		result = param;
		PropertyEntity property = this._def.getProperty(name);
		try {
			ReferencePropertyColumn ref = (ReferencePropertyColumn)property;
			if (ref != null) {
				AbstractIdEntity entity = (AbstractIdEntity)param;
				result = entity.getId();
			}
		}
		catch (ClassCastException e) {
		}
		try {
			EnumPropertyColumn enumProperty = (EnumPropertyColumn)property;
			if (enumProperty != null) {
				Enum<?> value = (Enum<?>)param;
				result = value.name();
			}
		}
		catch (ClassCastException e) {
		}
		return result;
	}

	public Class targetClass() {
		return this._def.getEntityClass();
	}

}
