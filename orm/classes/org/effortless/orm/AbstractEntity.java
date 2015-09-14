package org.effortless.orm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.effortless.core.ClassUtils;
import org.effortless.core.Collections;
import org.effortless.core.MethodUtils;
import org.effortless.core.StringUtils;
import org.effortless.core.UnusualException;
import org.effortless.core.ObjectUtils;
import org.effortless.core.PropertyUtils;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.definition.PropertyEntity;
import org.effortless.orm.impl.ChangeRegistry;
import org.effortless.orm.impl.ColumnEncoder;
import org.effortless.orm.impl.PropertiesLoader;
import org.effortless.orm.impl.PropertyList;
import org.effortless.orm.restrictions.Restriction;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

//import jejbutils.filter.Filter;
//import jejbutils.security.resources.PropertyResourceMode;

//@org.hibernate.annotations.Tuplizer(impl = org.effortless.model.CustomEntityTuplizer.class)
//@MappedSuperclass
public abstract class AbstractEntity extends Object implements Entity, Comparable {

	public static final String ERROR = "error_";
	protected static final String CREATE = "create";
	protected static final String ERROR_CREATE = ERROR + CREATE;
	
	public AbstractEntity () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateAttributes();
		this._changeRegistry = new ChangeRegistry(this);
		this._loadOnDemandEnabled = true;
	}

	protected java.util.Map attributes;
	
	protected void initiateAttributes() {
		this.attributes = null;
	}

	public java.util.Map getAttributes () {
		return this.attributes;
	}
	
	public void setAttributes (java.util.Map newValue) {
		Map oldValue = getAttributes();
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this.attributes = newValue;
			firePropertyChange("attributes", oldValue, newValue);
		}
	}
	
	public Object getAttribute (String name) {
		return getAttribute(name, null);
	}
	
	public Object getAttribute (String name, Object defaultValue) {
		Object result = defaultValue;
		Map attributes = getAttributes();
		if (attributes != null && attributes.containsKey(name)) {
			result = attributes.get(name);
		}
		return result;
	}
	
	public Object setAttribute (String attribute, Object newValue) {
		Object result = null;
		Object oldValue = getAttribute(attribute);
		if (!ObjectUtils.equals(oldValue, newValue)) {
			Map attributes = getAttributes();
			if (attributes == null) {
				attributes = new HashMap();
				setAttributes(attributes);
			}
			attributes.put(attribute, newValue);
			firePropertyChange(attribute, oldValue, newValue);
		}
		result = oldValue;
		return result;
	}
	
	public boolean existsAttribute (String name) {
		boolean result = false;
		Map attributes = getAttributes();
		result = (attributes != null && attributes.containsKey(name));
		return result;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	protected void _doPersist (EntityDefinition def, String[] columnNames, Object[] pValues) {
		Serializable id = this.doGetIdentifier();
		boolean update = (id != null);
		ColumnEncoder[] encoders = def.getColumnEncoders(columnNames);
		DbManager db = this.loadDbManager();
		if (update) {
			db.update(def.getTableName(), columnNames, this, encoders);
		}
		else {
			id = db.getNextValSequence(def.getSequenceName());
			doSetIdentifier(id);
			pValues[pValues.length - 1] = id;
			db.insert(def.getTableName(), columnNames, this, encoders);
			
			this._markLoadedColumns(columnNames);
		}
		db.apply(pValues);
		db.commit();
		this._changeRegistry.clear();
	}
	
	protected void _markLoadedColumns (String[] columns) {
		EntityDefinition def = this._loadDefinition();
		int length = (columns != null ? columns.length : 0);
		for (int i = 0; i < length; i++) {
			String column = columns[i];
			PropertyEntity property = def.getPropertyFromColumn(column);
			String propertyName = property.getPropertyName();
			this._changeRegistry.setupLoaded(propertyName);
		}
	}

	protected void _doPersist (PropertyList list) {
		if (list != null) {
			list.persist();
		}
	}
	
	protected void _doPersist (List list) {
		if (list != null) {
			PropertyList propertyList = null;
			try { propertyList = (PropertyList)list; } catch (ClassCastException e) {}
			if (propertyList != null) {
				_doPersist(propertyList);
			}
			else {
				int length = (list != null ? list.size() : 0);
				for (int i = 0; i < length; i++) {
					Entity entity = (Entity)list.get(i);
					if (entity != null) {
						entity.persist();
					}
				}
			}
		}
	}
	
	protected void _doPersist (Entity entity) {
		if (entity != null) {
			entity.persist();
		}
	}
	
	protected void _doCreate (EntityDefinition def) {
			Object[] parameterChanges = _getAllParameterChanges();
			if (parameterChanges != null && parameterChanges.length >= 2) {
				String[] columnNames = (String[])parameterChanges[0];
				Object[] pValues = (Object[])parameterChanges[1];
				_doPersist(def, columnNames, pValues);
			}
//			if (parameterChanges.length > 2) {
//				Object[] extra = (Object[])parameterChanges[2];
//				for (int i = 0; i < extra.length; i += 2) {
//					String listColumnName = (String)extra[0];
//					Object listValue = (Object)extra[1];
//					ColumnEncoder[] encoders = def.getColumnEncoders(new String[] {listColumnName});
//					try {
//						encoders[0].encode(this, null, 1, listValue);
//					} catch (SQLException e) {
//						throw new RuntimeException(e);
//					}
//				}
//			}
	}
	
	protected void _doUpdate (EntityDefinition def) {
			Object[] parameterChanges = def.getParameterChanges(this._changeRegistry, true);
			if (parameterChanges != null && parameterChanges.length >= 2) {
				Serializable id = doGetIdentifier();
				String[] columnNames = (String[])parameterChanges[0];
				Object[] pValues = (Object[])parameterChanges[1];
				int length = (columnNames != null ? columnNames.length : 0);
				if (length > 1) {
	//				columnNames.set(columnNames.size() - 1, "ID");
	//				pValues.set(pValues.size() - 1,  id);

//					columnNames = Collections.add(columnNames, "ID");
//					pValues = Collections.add(pValues, id);
					
					columnNames[length - 1] = "ID";
					pValues[length - 1] = id;

//					columnNames.add("ID");
//					pValues.add(id);
					
					_doPersist(def, columnNames, pValues);
				}
			}
	}
	
	protected void _doPersist (EntityDefinition def) {
		Serializable id = doGetIdentifier();
		boolean update = (id != null);
		if (update) {
			if (this.hasChanges()) {
				_doUpdate(def);
			}
		}
		else {
			_doCreate(def);
		}
	}
	
	protected abstract Object[] _getAllParameterChanges();

	protected void _doErase (EntityDefinition def) {
		Serializable id = doGetIdentifier();
		if (id != null) {
			String pkColumnName = def.getPrimaryKey().getColumnName();
			DbManager db = this.loadDbManager();
			db.delete(def.getTableName(), pkColumnName, this, def.getColumnEncoder(pkColumnName));
			db.apply(new Object[] {id});
			db.commit();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	protected ChangeRegistry _changeRegistry;
	
	public boolean hasChanges () {
		return this._changeRegistry.hasChanges();
	}
	
	public boolean hasChanges(String properties) {// throws ModelException {
		return this._changeRegistry.hasChanges(properties);
	}

	public void addChange (String propertyName, Object newValue) {
		this._changeRegistry.add(propertyName, newValue);
	}
	
	//_setProperty("comentario", this.comentario, this.comentario = newValue);
	protected boolean _setProperty (String propertyName, Object oldValue, Object newValue) {
		boolean result = false;
		if (!ObjectUtils.equals(oldValue, newValue)) {
			_doChangeProperty(propertyName, oldValue, newValue);
			this._changeRegistry.add(propertyName, oldValue, newValue);
			this._changeRegistry.firePropertyChange(propertyName, oldValue, newValue);
			result = true;
		}
		return result;
	}
	
	protected void _doChangeProperty (String propertyName, Object oldValue, Object newValue) {
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//this.comentario = (String)_loadOnDemand("comentario", this.comentario);
	protected void _loadOnDemand(String propertyName, Object currentValue) {
		EntityDefinition def = this._loadDefinition();
		DbManager.trySetup(def);
		this._loadOnDemand(propertyName, currentValue, def);
	}

	protected void _loadOnDemand(String propertyName, Object currentValue, EntityDefinition def) {
		if (this._loadOnDemandEnabled) {
			Object id = this.doGetIdentifier();
			if (id != null && !_checkLoaded(propertyName)) {
				PropertiesLoader loader = def.getPropertiesLoader(propertyName);
				_loadProperties(def, loader);
			}
		}
	}

	protected boolean _checkLoaded(String propertyName) {
		boolean result = false;
		result = this._changeRegistry.checkLoaded(propertyName);
		return result;
	}

	//result._setupLoaded("comentario");
	protected void _setupLoaded(String propertyName) {
		this._changeRegistry.setupLoaded(propertyName);
	}

	protected void _loadProperties(EntityDefinition def, PropertiesLoader loader) {
		String columns = loader.getColumns();//"ID, NOMBRE"
		DbManager db = this.loadDbManager();
		Object id = this.doGetIdentifier();
		ResultSet rs = db.loadProperties(def.getTableName(), def.getPrimaryKey().getColumnName(), id, columns);
		if (rs == null) {
			throw new UnusualException("OBJECT NOT FOUND!");
		}
		_disableLoadOnDemand();
		loader.load(this, db, rs);
		_enableLoadOnDemand();
		db.closeAndStm(rs);
	}

	protected boolean _loadOnDemandEnabled;
	
	protected void _disableLoadOnDemand() {
		this._loadOnDemandEnabled = false;
	}

	protected void _enableLoadOnDemand() {
		this._loadOnDemandEnabled = true;
	}

	
	
	
	
	
	public DbManager loadDbManager() {
//		return this._db;
		return MySession.getDb();
	}
	
	public java.sql.Connection loadConnection() {
		java.sql.Connection result = null;
		DbManager db = this.loadDbManager();
		result = (db != null ? db.getConnection() : null);
//		result = (this._db != null ? this._db.getConnection() : null);
		return result;
	}

	public String loadSchema() {
		String result = null;
		DbManager db = this.loadDbManager();
		result = (db != null ? db.getCurrentSchema() : null);
//		result = (this._db != null ? this._db.getCurrentSchema() : null);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this._changeRegistry.addPropertyChangeListener(listener);
	}
	
	public boolean containsPropertyChangeListener(PropertyChangeListener listener) {
		return this._changeRegistry.containsPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this._changeRegistry.addPropertyChangeListener(propertyName, listener);
	}

	public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		return this._changeRegistry.containsPropertyChangeListener(propertyName, listener);
	}
	
	public void fireIndexedPropertyChange(String propertyName, int index, boolean oldValue, boolean newValue) {
		this._changeRegistry.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	public void fireIndexedPropertyChange(String propertyName, int index, int oldValue, int newValue) {
		this._changeRegistry.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
		this._changeRegistry.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	public void firePropertyChange(PropertyChangeEvent event) {
		this._changeRegistry.firePropertyChange(event);
	}

	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
		this._changeRegistry.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(String propertyName, int oldValue, int newValue) {
		this._changeRegistry.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		this._changeRegistry.firePropertyChange(propertyName, oldValue, newValue);
	}

	public PropertyChangeListener[] getPropertyChangeListenersArray() {
		return this._changeRegistry.getPropertyChangeListenersArray();
	}
	
	public List getPropertyChangeListeners() {
		return this._changeRegistry.getPropertyChangeListeners();
	}
	
	public PropertyChangeListener[] getPropertyChangeListenersArray(String propertyName) {
		return this._changeRegistry.getPropertyChangeListenersArray(propertyName);
	}

	public List getPropertyChangeListeners(String propertyName) {
		return this._changeRegistry.getPropertyChangeListeners(propertyName);
	}
	
	public boolean hasListeners(String propertyName) {
		return this._changeRegistry.hasListeners(propertyName);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener != null) {
			this._changeRegistry.removePropertyChangeListener(listener);
		}
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (propertyName != null && listener != null) {
			this._changeRegistry.removePropertyChangeListener(propertyName, listener);
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	
	protected abstract EntityDefinition _loadDefinition ();

	public abstract Serializable doGetIdentifier ();
	
	public abstract void doSetIdentifier (Serializable newValue);
//		Serializable result = null;
//		Session session = doGetSession();
//		result = session.getIdentifier(this);
//		return result;
//	}
	
	
	
	
	
	
	
	
	
	
	
	protected Restriction doGetCreateRestriction () {
		return null;
	}
	
	protected Restriction doGetUpdateRestriction () {
		return doGetCreateRestriction();
	}
	
	protected Restriction doGetEraseRestriction () {
		return null;
	}
	
	protected Restriction doGetDeleteRestriction () {
		return null;
	}
	
	protected Restriction doGetUndeleteRestriction () {
		return null;
	}
	
	protected Restriction doGetPersistRestriction () {
		return null;
	}
	
	
	
	
	
	
	
	
	public void checkCreate() {// throws ModelException {
		checkCreate(null);
	}

	public void checkCreate(String properties) {// throws ModelException {
		Restriction restriction = doGetCreateRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public void checkUpdate() {// throws ModelException {
		checkUpdate(null);
	}

	public void checkUpdate(String properties) {// throws ModelException {
		Restriction restriction = doGetUpdateRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public boolean checkAccessible() {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	public void checkErase() {// throws ModelException {
		checkErase(null);
	}

	@Override
	public void checkErase(String properties) {// throws ModelException {
		Restriction restriction = doGetEraseRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public void checkDelete() {// throws ModelException {
		checkDelete(null);
	}

	@Override
	public void checkDelete(String properties) {// throws ModelException {
		Restriction restriction = doGetDeleteRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	public void checkUndelete() {// throws ModelException {
		checkUndelete(null);
	}

	@Override
	public void checkUndelete(String properties) {// throws ModelException {
		Restriction restriction = doGetUndeleteRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	@Override
	//@javax.persistence.Transient
	public Boolean isReadonly() {
		// TODO Auto-generated method stub
		return null;
	}

	public void checkPersist() {// throws ModelException {
		checkPersist(null);
	}

	@Override
	public void checkPersist(String properties) {// throws ModelException {
		Restriction restriction = doGetPersistRestriction();
		if (restriction != null) {
			restriction.check(this);
		}
	}

	
//	public static class LogChangesEntityListener {
//
//	    @PrePersist
//	    public void prePersist(Object object) {
//	        if(object instanceof Timestamped){
//	            Timestamped timestamped = (Timestamped) object;
//	            Date now = new Date();
//	            timestamped.setCreated(now);
//	            timestamped.setUpdated(now);
//	        }
//	    }
//
//	    @PreUpdate
//	    public void preUpdate(Object object) {
//	        if(object instanceof Timestamped){
//	            Timestamped timestamped = (Timestamped) object;
//	            timestamped.setUpdated(new Date());
//	        }
//	    }
//	}	
	
	
	
	
	@Override
	public boolean doCanSaveFinalData(boolean check, boolean create,
			boolean update) {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doCanLoadFinalData() {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	@Override
	public boolean checkForUpdate() {// throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	public void checkCreateCopy() {// throws ModelException {
		checkCreateCopy(null);
	}

	@Override
	public void checkCreateCopy(String properties) {// throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkPropertyLoadSecurity(String propertyName) {//throws SecurityException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPropertySaveSecurity(String propertyName) {// throws SecurityException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPropertySecurity(String propertyName) {// throws SecurityException {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean checkPropertySecurity(String propertyName, PropertyResourceMode mode) {// throws SecurityException {
//		// TODO Auto-generated method stub
//		return false;
//	}

	
	
	
	@Override
	public Object onPreSaveReplace() {// throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enableNotifyChanges() {
		this._changeRegistry.enableNotifyChanges();
	}

	@Override
	public void enableDisableChangeEvents() {
		this._changeRegistry.enableDisableChangeEvents();
	}

	@Override
	public void disableDisableChangeEvents() {
		this._changeRegistry.disableDisableChangeEvents();
	}

	@Override
	public void disableNotifyChanges() {
		this._changeRegistry.disableNotifyChanges();
	}

	
	
	
	
	public void onPreCreate() {// throws ModelException {
	}
	public void onPostCreate() {// throws ModelException {
	}

	public void onPreRead() {// throws ModelException {
	}
	public void onPostRead() {// throws ModelException {
	}

	public void onPreUpdate() {// throws ModelException {
	}
	public void onPostUpdate() {// throws ModelException {
	}

	public void onPreDelete() {// throws ModelException {
	}
	public void onPostDelete() {// throws ModelException {
	}

	public void onPrePersist() {// throws ModelException {
	}
	public void onPostPersist() {// throws ModelException {
	}

	public void onPreUndelete() {// throws ModelException {
	}
	public void onPostUndelete() {// throws ModelException {
	}
	
	public void onPreErase() {// throws ModelException {
	}
	public void onPostErase() {// throws ModelException {
	}

	public void onPreDeleteErase() {// throws ModelException {
	}
	public void onPostDeleteErase() {// throws ModelException {
	}


	
	
	
//	protected boolean _equals (Object value1, Object value2) {
//		return ObjectUtils.equals(value1, value2);
//	}
	
	protected boolean _doRunCheckLoaded () {
		return hasId();
	}

	
	public boolean checkLoaded(String property, boolean save) {
		boolean result = false;
		if (_doRunCheckLoaded()) {
			result = this._changeRegistry.checkLoaded(property, save);
		}
		else {
			this._changeRegistry.checkLoaded(property, save);
			result = true;
		}
		return result;
	}

	public boolean unloadProperty(String property) {
		return this._changeRegistry.unloadProperty(property);
	}

	public boolean unloadProperty(String property, Object oldValue, Object newValue,
			boolean notify) {
		return this._changeRegistry.unloadProperty(property, oldValue, newValue, notify);
	}

	public boolean unloadProperty(String property, boolean notify) {
		return this._changeRegistry.unloadProperty(property, notify);
	}

	public void unloadProperties() {
		this._changeRegistry.unloadProperties();
	}

	public String doGetLoadedProperties() {
		return this._changeRegistry.doGetLoadedProperties();
	}

	public boolean checkRead(String property, boolean force) {
		return this._changeRegistry.checkRead(property, force);
	}

	public void unreadProperty(String property) {
		this._changeRegistry.unreadProperty(property);
	}

	public void unreadProperties() {
		this._changeRegistry.unreadProperties();
	}

	public String doGetReadProperties() {
		return this._changeRegistry.doGetReadProperties();
	}

	
	
	
	
	
	
	
	
	@Override
	public boolean doCheckLogCreate() {
		return true;
	}

	@Override
	public boolean doCheckLogRead() {
		return false;
	}

	@Override
	public boolean doCheckLogUpdate() {
		return true;
	}

	@Override
	public boolean doCheckLogUpdateChanges() {
		return true;
	}

	@Override
	public boolean doCheckLogDelete() {
		return true;
	}

	@Override
	public boolean doCheckLogUndelete() {
		return true;
	}

	@Override
	public boolean doCheckLogErase() {
		return true;
	}

	@Override
	public boolean doCheckLog() {
		return false;
	}

	
	
	
	
	protected LogData _newInstanceLogData () {
		return new LogData();
	}
	
	protected LogData _createLogData () {
		LogData result = null;
		
		Entity author = MySession.getCurrentUser();
		String locationKeyFrom = MySession.getLogLocationKeyFrom();
		String locationAliasFrom = MySession.getLogLocationAliasFrom();
		String locationDescriptionFrom = MySession.getLogLocationDescriptionFrom();
		String targetType = getClass().getSimpleName();
		java.util.Date currentDate = new java.util.Date();
		
		LogData log = _newInstanceLogData();

		log.setAuthor(author);
		
		log.setDate(currentDate);

		log.setComment(null);
		log.setExecutionTime(null);
		log.setLocationAliasFrom(locationAliasFrom);
		log.setLocationDescriptionFrom(locationDescriptionFrom);
		log.setLocationKeyFrom(locationKeyFrom);
		log.setPending(Boolean.FALSE);
		
		log.setTarget(this);

		
		log.setTargetType(targetType);
		log.setType(null);
		
		result = log;

		try {
			Long authorId = (Long)(author != null ? author.doGetIdentifier() : null);
			result.setAuthorId(authorId);
		}
		catch (ClassCastException e) {
		}
		
		try {
			Referenciable ref = (Referenciable)author;
			if (ref != null) {
				result.setAuthorCode(ref.toLabel());
			}
		}
		catch (ClassCastException e) {
		}
		
		try {
			Long targetId = (Long)this.doGetIdentifier();
			result.setTargetId(targetId);
		}
		catch (ClassCastException e) {
		}
		
		try {
			Referenciable ref = (Referenciable)this;
			if (ref != null) {
				result.setTargetCode(ref.toLabel());
			}
		}
		catch (ClassCastException e) {
		}
		
		
		return result;
	}
	
	@Override
	public void saveLogException(Throwable e, String actionName) {// throws ModelException {
		saveLogException(e, actionName, null);
	}

	public void saveLogException(Throwable e, String actionName, Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogCreate()) {
			String comment = (e != null ? e.getMessage() : null);
			_saveLog(actionName, comment, time);
		}
	}

	@Override
	public void saveActionLog(String actionName) {// throws ModelException {
		saveActionLog(actionName, null, null);
	}

	@Override
	public void saveActionLog(String actionName, String comment) {// throws ModelException {
		saveActionLog(actionName, comment, null);
	}

	@Override
	public void saveActionLog(String actionName, Long time) {// throws ModelException {
		_saveLog(actionName, null, time);
	}

	@Override
	public void saveActionLog(String actionName, String comment, Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogCreate()) {
			_saveLog(actionName, comment, time);
		}
	}

	public static final String ACTION_CREATE_LOG = "create";
	public static final String ACTION_READ_LOG = "read";
	public static final String ACTION_UPDATE_LOG = "update";
	public static final String ACTION_DELETE_LOG = "delete";
	public static final String ACTION_UNDELETE_LOG = "undelete";
	public static final String ACTION_ERASE_LOG = "erase";
	
//	@Override
	protected void saveCreateLog (Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogCreate()) {
			_saveLog(ACTION_CREATE_LOG, null, time);
		}
	}

//	@Override
	protected void saveReadLog(Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogRead()) {
			_saveLog(ACTION_READ_LOG, null, time);
		}
	}

//	@Override
	protected void saveUpdateLog(Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogUpdate()) {
			String comment = (this._changeRegistry != null ? this._changeRegistry.toString() : null);
			_saveLog(ACTION_UPDATE_LOG, comment, time);
			this._changeRegistry.clear();
		}
	}

//	@Override
//	public void saveUpdateLogChanges(LogChanges changes) throws ModelException {
//		if (this.doCheckLog() && this.doCheckLogUpdate()) {
//			_saveLog(ACTION_UPDATE_LOG);
//		}
//	}
//
//	@Override
//	public void trySaveUpdateLogChanges(LogChanges changes)
//			throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
	protected void saveDeleteLog(Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogDelete()) {
			_saveLog(ACTION_DELETE_LOG, null, time);
		}
	}

//	@Override
	protected void saveUndeleteLog(Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogUndelete()) {
			_saveLog(ACTION_UNDELETE_LOG, null, time);
		}
	}

//	@Override
	protected void saveEraseLog(Long time) {// throws ModelException {
		if (this.doCheckLog() && this.doCheckLogErase()) {
			_saveLog(ACTION_ERASE_LOG, null, time);
		}
	}
	
	protected void _saveLog (String actionName, String comment, Long time) {// throws ModelException {
		if (this.doCheckLog()) {
			LogData log = _createLogData();
			log.setType(actionName);
			log.setComment(comment);
			log.setExecutionTime(time);
			log.persist();
		}
	}

	protected String getMetaEntityName () {
		return getClass().getSimpleName();
	}

	protected Integer _loadDefaultPageSize () {
//		Integer pageSize = SessionManager.loadDefaultPageSize(this);
		return Integer.valueOf(25);
	}
	
	protected List _listLogs(String type) {// throws ModelException {
		List result = null;
		if (doCheckLog()) {
			String targetType = getMetaEntityName();
			Serializable targetId = null;
			Integer pageSize = _loadDefaultPageSize();
	
			Filter filter = LogData.listAll().eq("type", type).eq("targetType", targetType).eq("targetId", targetId).sortBy("date DESC");
	//		GenericLogDataFilter filter = new GenericLogDataFilter(targetType, targetId, type);
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
			filter.setPageSize(pageSize);
			
			result = filter;
		}
		return result;
	}
	
	protected LogData _loadLog(String type, boolean last) {// throws ModelException {
		LogData result = null;
		if (doCheckLog()) {
			String targetType = getMetaEntityName();
			Serializable targetId = null;
	
			Filter filter = LogData.listAll().eq("type", type).eq("targetType", targetType).eq("targetId", targetId).sortBy("date DESC");
	//		GenericLogDataFilter filter = new GenericLogDataFilter(targetType, targetId, type);
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
			filter.setPageSize(Integer.valueOf((last ? 1 : 2)));
			try {
				result = (LogData)filter.get((last ? 0 : 1));
			}
			catch (Exception e) {
				
			}
		}
		return result;
	}
	
	
	
	
	public LogData getLogCreation() {// throws ModelException {
		return _loadLog(ACTION_CREATE_LOG, true);
	}

	public List<LogData> getLogChanges() {// throws ModelException {
		return _listLogs(ACTION_UPDATE_LOG);
	}
	
	public LogData getLogLastChange() {// throws ModelException {
		return _loadLog(ACTION_UPDATE_LOG, true);
	}

	public LogData getLogPrevLastChange() {// throws ModelException {
		return _loadLog(ACTION_UPDATE_LOG, false);
	}

	public LogData getLogLastAction(String actionName) {// throws ModelException {
		return _loadLog(actionName, true);
	}

	public LogData getLogPrevLastAction(String actionName) {// throws ModelException {
		return _loadLog(actionName, false);
	}

	public List<LogData> getLogActions(String actionName) {// throws ModelException {
		return _listLogs(actionName);
	}

	public List<LogData> getLogDeletes() {// throws ModelException {
		return _listLogs(ACTION_DELETE_LOG);
	}

	public LogData getLogLastDelete() {// throws ModelException {
		return _loadLog(ACTION_DELETE_LOG, true);
	}

	public LogData getLogPrevLastDelete() {// throws ModelException {
		return _loadLog(ACTION_DELETE_LOG, false);
	}

	public List<LogData> getLogReads() {// throws ModelException {
		return _listLogs(ACTION_READ_LOG);
	}

	public LogData getLogLastRead() {// throws ModelException {
		return _loadLog(ACTION_READ_LOG, true);
	}

	public LogData getLogPrevLastRead(Object data) {// throws ModelException {
		return _loadLog(ACTION_READ_LOG, false);
	}

//	@Override
//	public Number countLogBy(Filter filter) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<LogData> listLogBy(Filter filter) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public LogData findLogBy(Filter filter) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	
	
	
	
	
	
	public void refresh() {// throws ModelException {
		this.refresh(null);
	}

	public void refresh(String properties) {// throws ModelException {
		if (this._changeRegistry != null) {
			this._changeRegistry.enableDisableChangeEvents();
			properties = (properties != null ? properties.trim() : "");
			if (properties.length() > 0) {
				String[] arrayProperties = properties.split(",");
				for (String itemProperty : arrayProperties) {
					this._changeRegistry.restore(itemProperty);
				}
			}
			else {
				this._changeRegistry.restore();
				this._changeRegistry.clear();
			}
		}
	}
	
//	@Override
//	public void refresh(boolean forceRefresh) {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void refresh(String properties, boolean forceRefresh) {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

	
	
	
	
	
//	@Override
//	public long doSaveProperties(String properties, boolean check, boolean create, boolean update, LogChanges changes) {// throws ModelException {
//		// TODO Auto-generated method stub
//		return 0;
//	}

//	@Override
//	public boolean isSameVersion() {// throws ModelException {
//		// TODO Auto-generated method stub
//		return false;
//	}

	
	
	
	public void createIfNotExists() {// throws ModelException {
		if (!hasId()) {
			create();
		}
	}

	
	public void create() {// throws ModelException {
		create(true, true, true);
	}

	public void create(boolean validate) {// throws ModelException {
		create(validate, true, true);
	}

	public void create(boolean validate, boolean enableOnPre) {// throws ModelException {
		create(validate, enableOnPre, true);
	}

	protected Long __startExecutionTime () {
//		return (doCheckLog() ? MySession.startExecutionTime() : null);
		return (doCheckLog() ? Long.valueOf(0) : null);
	}
	
	protected Long __stopExecutionTime () {
//		return (doCheckLog() ? MySession.stopExecutionTime() : null);
		return (doCheckLog() ? Long.valueOf(0) : null);
	}

	public void create(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (ModelSecurity.checkSecurityAction(this, CREATE)) {
			__startExecutionTime();
			boolean __stopExecutionTime = false;
			Long _executionTime = null;
			String txId = null;
			try {
//				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkCreate();
					this.checkPersist();
				}
				if (enableOnPre) {
					this.onPreCreate();
					this.onPrePersist();
				}
				this.doSavePreviousProperties(null, validate, true);
				this.doCreate();
				this.doSaveProperties(null, validate, true);
				this.doSaveFinalProperties(null, validate, true);
				this.endSaveProperties(null, validate, true);
		//		Session session = MySession.loadSession(this);
		//		session.clear();
				if (enableOnPost) {
					this.onPostCreate();
					this.onPostPersist();
				}
//				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveCreateLog(_executionTime);
			}
			catch (UnusualException e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR_CREATE, _executionTime);
				throw e;
			}
//			catch (org.hibernate.exception.ConstraintViolationException e) {
////				SessionManager.rollbackTransaction(this, txId);
//				if (!__stopExecutionTime) {
//					_executionTime = __stopExecutionTime();
//				}
//				this.trySaveLogException(e, ERROR_CREATE, _executionTime);
//				throw new ModelException(e);
//			}
			catch (Exception e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR_CREATE, _executionTime);
				throw new UnusualException(e);
			}
		}
	}

	protected boolean doCheckErrorSaveLog () {
		return false;
	}
	
	protected void trySaveLogException(Throwable e, String actionName, Long time) {
		try {
			saveLogException(e, actionName, time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}

	protected void trySaveActionLog(String actionName, String comment, Long time) {// throws ModelException {
		try {
			saveActionLog(actionName, comment, time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}
	
	protected void trySaveCreateLog(Long time) {
		try {
			saveCreateLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}

	protected void doCreate() {// throws ModelException {
		EntityDefinition def = _loadDefinition();
		DbManager.trySetup(def);
		_doCreate(def);
	}
		
	public AbstractEntity createCopy() {// throws ModelException {
		return createCopy(null);
	}

	public AbstractEntity createCopy(String properties) {// throws ModelException {
		AbstractEntity result = null;
		try {
			result = (AbstractEntity) this.getClass().newInstance();
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		}
		copy(result, properties);
		return result;
	}

	public void copy(AbstractEntity source, String properties) {// throws ModelException {
		if (source != null) {
			properties = (properties != null ? properties.trim() : "");
			if (properties.length() > 0) {
				String[] arrayProperties = (properties != null ? properties.split(",") : null);
				for (String property : arrayProperties) {
					PropertyUtils.setProperty(source, property, PropertyUtils.getProperty(this, property));
				}
			}
			else {
				PropertyUtils.copyProperties(source, this);
			}
		}
	}

	public void copy(AbstractEntity source) {// throws ModelException {
		copy(source, null);
	}

	
	
	public void erase() {// throws ModelException {
		this.erase(true, true, true);
	}

	public void erase(boolean validate) {// throws ModelException {
		this.erase(validate, true, true);
	}

	public void erase(boolean validate, boolean enableOnPre) {// throws ModelException {
		erase(validate, enableOnPre, true);
	}

	public void erase(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (ModelSecurity.checkSecurityAction(this, "erase")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
//				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkErase();
				}
				if (enableOnPre) {
					this.onPreErase();
					this.onPreDeleteErase();
				}
				this.doEraseProperties();
				this.doErase();
				if (enableOnPost) {
					this.onPostErase();
					this.onPostDeleteErase();
				}
//				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveEraseLog(_executionTime);
			}
			catch (UnusualException e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw e;
			}
			catch (Exception e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new UnusualException(e);
			}
		}
	}
	
	protected void trySaveEraseLog(Long time) {
		try {
			saveEraseLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}

	protected void doErase() {// throws ModelException {
		EntityDefinition def = this._loadDefinition();
		DbManager.trySetup(def);
		_doErase(def);
	}
	
	
	protected void doEraseProperties() {// throws ModelException {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public void delete() {// throws ModelException {
		// TODO Auto-generated method stub
		this.delete(true, true, true);
	}

	public void delete(boolean validate) {// throws ModelException {
		this.delete(validate, true, true);
	}

	public void delete(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.delete(validate, enableOnPre, true);
	}

	public void delete(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (ModelSecurity.checkSecurityAction(this, "delete")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
//				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkDelete();
				}
				if (enableOnPre) {
					this.onPreDelete();
					this.onPreDeleteErase();
				}
				this.doDeleteProperties();
				this.doDelete();
				if (enableOnPost) {
					this.onPostDelete();
					this.onPostDeleteErase();
				}
//				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveDeleteLog(_executionTime);
			}
			catch (UnusualException e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw e;
			}
			catch (Exception e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new UnusualException(e);
			}
		}
	}
	
	protected void trySaveDeleteLog(Long time) {
		try {
			saveDeleteLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}

	protected void doDelete() {// throws ModelException {
		this.doErase();
	}

	protected void doDeleteProperties() {// throws ModelException {
		// TODO Auto-generated method stub
		
	}

	protected void deleteCollection (List<Entity> list) {// throws ModelException {
		if (list != null) {
			int length = list.size();
			for (int i = length - 1; i >= 0; i--) {
				Entity entity = list.get(i);
				entity.delete();
			}
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	public void undelete() {// throws ModelException {
		this.undelete(true, true, true);
	}

	public void undelete(boolean validate) {// throws ModelException {
		this.undelete(validate, true, true);
	}

	public void undelete(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.undelete(validate, enableOnPre, true);
	}

	public void undelete(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (ModelSecurity.checkSecurityAction(this, "undelete")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
//				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkUndelete();
				}
				if (enableOnPre) {
					this.onPreUndelete();
				}
				this.doUndelete();
	
				if (enableOnPost) {
					this.onPostUndelete();
				}
//				SessionManager.endTransaction(this, txId);
				_executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				this.trySaveUndeleteLog(_executionTime);
			}
			catch (UnusualException e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw e;
			}
			catch (Exception e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new UnusualException(e);
			}
		}
	}
	
	protected void trySaveUndeleteLog(Long time) {
		try {
			saveUndeleteLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}

	protected void doUndelete() {// throws ModelException {
	}

	
	
	
//	@Override
//	public void saveDelete(boolean save) {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void deleteBind() {// throws ModelException {
//		// TODO Auto-generated method stub
//		
//	}

	
	public void cancelChanges () {
		cancelChanges(null);
	}
	
	public void cancelChanges (String properties) {
		if (this._changeRegistry != null ) {
			if (properties == null) {
				this._changeRegistry.restore();
			}
			else {
				String[] arrayProperties = properties.split(",");
				for (String property : arrayProperties) {
					this._changeRegistry.restore(property);
				}
			}
		}
	}
	
	public void restore() {// throws ModelException {
		restore((Date)null);
	}

	public void restore(Date date) {// throws ModelException {
		LogData log = null;
		if (date != null) {
			String targetType = getMetaEntityName();
			Serializable targetId = null;
			
			Filter filter = LogData.listAll().eq("type", AbstractEntity.ACTION_UPDATE_LOG).eq("targetType", targetType).eq("targetId", targetId).le("date", date).sortBy("date DESC");
			
//			GenericLogDataChangeByDateFilter filter = new GenericLogDataChangeByDateFilter(targetType, targetId, date);
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
			filter.setPageSize(Integer.valueOf(1));
			try {
				log = (LogData)filter.get(0);
			}
			catch (Exception e) {
				
			}
		}
		else {
			log = this.getLogLastChange();
		}
		if (log != null) {
			restore(log);
		}
	}

	public void restore(LogData log) {// throws ModelException {
		if (log != null) {
			if (ModelSecurity.checkSecurityAction(this, "restore")) {
				ChangeRegistry changes = (ChangeRegistry)log.getExtraData();
				changes.restore();
			}
		}
	}
	
	public boolean hasId () {
		return exists();
	}
	
	public boolean exists() {// throws ModelException {
		boolean result = false;
		Serializable id = doGetIdentifier();
		result = (id != null ? doExists(id) : false);
		return result;
	}

	protected boolean doExists (Serializable id) {
		boolean result = false;
		if (id != null) {
			EntityDefinition def = this._loadDefinition();
			DbManager.trySetup(def);

			String pkColumn = def.getPrimaryKey().getColumnName();
			DbManager db = this.loadDbManager();

			result = db.exists(def.getTableName(), pkColumn);
		}
		return result;
	}

//	@Override
//	@javax.persistence.Transient
//	public Integer getDefaultPageSize() {// throws ModelException {
//		Integer result = null;
//		result = MySession.loadDefaultPageSize(this);
//		return result;
//	}

	
	
	
	public AbstractEntity reload() {// throws ModelException {
		AbstractEntity result = null;
		Serializable id = doGetIdentifier();
		if (id != null) {
//			Class<Type> clazz = doLoadRealClass();
			String entityName = this.getMetaEntityName();
			result = (AbstractEntity)ClassUtils.newInstanceRE(entityName, id);
		}
		return result;
	}

//	@Override
	protected boolean doSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		// TODO Auto-generated method stub
		return result;
	}

	protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		// TODO Auto-generated method stub
		return result;
	}

	
//	@Override
	protected boolean doSaveFinalProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		// TODO Auto-generated method stub
		return result;
	}

	protected boolean endSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		return result;
	}
		
	
	public void persist() {// throws ModelException {
		this.persist(null, true, true, true);
	}

	public void persist(boolean validate) {// throws ModelException {
		this.persist(null, validate, true, true);
	}

	public void persist(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.persist(null, validate, enableOnPre, true);
	}

	public void persist(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		this.persist(null, validate, enableOnPre, enableOnPost);
	}

	public void persist(String properties) {// throws ModelException {
		this.persist(properties, true, true, true);
	}

	public void persist(String properties, boolean validate) {// throws ModelException {
		this.persist(properties, validate, true, true);
	}

	public void persist(String properties, boolean validate, boolean enableOnPre) {// throws ModelException {
		this.persist(properties, validate, enableOnPre, true);
	}

	public void persist(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (hasId()) {
			update(properties, validate, enableOnPre, enableOnPost);
		}
		else {
			create(validate, enableOnPre, enableOnPost);
		}
	}
	
	
	
	public void update() {// throws ModelException {
		this.update(null, true, true, true);
	}

	public void update(boolean validate) {// throws ModelException {
		this.update(null, validate, true, true);
	}

	public void update(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.update(null, validate, enableOnPre, true);
	}

	public void update(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		this.update(null, validate, enableOnPre, enableOnPost);
	}

	public void update(String properties) {// throws ModelException {
		this.update(properties, true, true, true);
	}

	public void update(String properties, boolean validate) {// throws ModelException {
		this.update(properties, validate, true, true);
	}

	public void update(String properties, boolean validate, boolean enableOnPre) {// throws ModelException {
		this.update(properties, validate, enableOnPre, true);
	}

	public void update(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (ModelSecurity.checkSecurityAction(this, "update")) {
			__startExecutionTime();
			Long _executionTime = null;
			boolean __stopExecutionTime = false;
			String txId = null;
			try {
//				txId = SessionManager.beginTransaction(this);
				if (validate) {
					this.checkUpdate(properties);
				}
				if (enableOnPre) {
					this.onPreUpdate();
					this.onPrePersist();
				}
				if (this.hasChanges(properties)) {
					this.doSavePreviousProperties(properties, validate, false);
					this.doUpdate();
					this.doSaveProperties(properties, validate, false);
					this.doSaveFinalProperties(null, validate, false);
					this.endSaveProperties(null, validate, false);
					if (enableOnPost) {
						this.onPostUpdate();
						this.onPostPersist();
					}
//					SessionManager.endTransaction(this, txId);
					_executionTime = __stopExecutionTime();
					__stopExecutionTime = true;
					this.trySaveUpdateLog(_executionTime);
				}
				else {
//					SessionManager.endTransaction(this, txId);
					__stopExecutionTime();
					__stopExecutionTime = true;
				}
			}
			catch (UnusualException e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR_CREATE, _executionTime);
				throw e;
			}
//			catch (org.hibernate.exception.ConstraintViolationException e) {
////				SessionManager.rollbackTransaction(this, txId);
//				if (!__stopExecutionTime) {
//					_executionTime = __stopExecutionTime();
//				}
//				this.trySaveLogException(e, ERROR_CREATE, _executionTime);
//				throw new ModelException(e);
//			}
			catch (Exception e) {
//				SessionManager.rollbackTransaction(this, txId);
				if (!__stopExecutionTime) {
					_executionTime = __stopExecutionTime();
				}
				throw new UnusualException(e);
			}
		}
	}
	
	protected void trySaveUpdateLog(Long time) {
		try {
			saveUpdateLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}

	protected void doUpdate() {// throws ModelException {
		EntityDefinition def = this._loadDefinition();
		DbManager.trySetup(def);
		_doUpdate(def);
	}

//	public static <Type extends Object> List<Type> listBy (Class<Type> clazz, String methodName, Object... parameters) {
//		List<Type> result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		result = (List<Type>)filter;
//		return result;
//	}

//	public static <Type extends AbstractEntity> AbstractFilter listBy (Class<Type> clazz) {
//		AbstractFilter<Type> result = null;
//		EntityDefinition def = AbstractEntity._loadDefinition(clazz);
//		result = _doListBy(def);
//		return result;
//	}
	
//	public static AbstractFilter listBy (Class<?> clazz) {
//		AbstractFilter result = null;
//		EntityDefinition def = AbstractEntity._loadDefinition(clazz);
//		result = _doListBy(def);
//		return result;
//	}
	
	
	
//	protected static <Type extends AbstractEntity> AbstractFilter<Type> _doListBy (EntityDefinition def) {
//		AbstractFilter<Type> result = null;
//		result = _doListBy(def, def.getDefaultLoader());
//		return result;
//	}
	
//	protected static AbstractFilter _doListBy (EntityDefinition def) {
//		AbstractFilter result = null;
//		result = _doListBy(def, def.getDefaultLoader());
//		return result;
//	}
	
//	protected static <Type extends AbstractEntity> AbstractFilter<Type> _doListBy (EntityDefinition def, PropertiesLoader loader) {
//		AbstractFilter<Type> result = null;
//		String columns = def.getPrimaryKey().getColumnName() + ", " + loader.getColumns();//"ID, NOMBRE"
//		Class<?>[] types = null;//decoder.getTypes();//Long.class, String.class
//		DbManager db = MySession.getDb();
//		result = new EntityFilter<Type>(def.getTableName(), db, loader, columns, types);
//		result.setPageIndex(0);
//		result.setPageSize(25);
//		result.orderBy(def.getDefaultOrderBy());
//		return result;
//	}
//	
//	protected static AbstractFilter _doListBy (EntityDefinition def, PropertiesLoader loader) {
//		AbstractFilter result = null;
//		result = EntityFilter.buildEntityFilter(def, loader);
////		String columns = def.getPrimaryKey().getColumnName() + ", " + loader.getColumns();//"ID, NOMBRE"
////		Class<?>[] types = null;//decoder.getTypes();//Long.class, String.class
////		DbManager db = MySession.getDb();
////		result = new EntityFilter(def.getTableName(), db, loader, columns, types);
////		result.setPageIndex(0);
////		result.setPageSize(25);
////		result.orderBy(def.getDefaultOrderBy());
//		return result;
//	}
	
	protected static void _doCreateDb (DbManager db, EntityDefinition def) {
		db.createEntity(def);
	}
	
	protected static void _doResetDb (DbManager db, EntityDefinition def) {
		db.deleteEntity(def);
	}
	
	
	
	
//	public static <Type extends Object> List<Type> listBy (Class<Type> clazz, String methodName, String orderBy, Object... parameters) {
//		List<Type> result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, orderBy, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		result = (List<Type>)filter;
//		return result;
//	}

//	public static <Type extends Object> Type findBy (Class<Type> clazz, String methodName, Object... parameters) {
//		Type result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		try {
//			result = (Type)filter.get(0);
//		}
//		catch (ModelException e) {
//		}
//
//		return result;
//	}
	
//	public static <Type extends Object> Type findBy (Class<Type> clazz, String methodName, String orderBy, Object... parameters) {
//		Type result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, orderBy, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		try {
//			result = (Type)filter.get(0);
//		}
//		catch (ModelException e) {
//		}
//
//		return result;
//	}
	
//	public static <Type extends Object> Long countBy (Class<Type> clazz, String methodName, Object... parameters) {
//		Long result = null;
//		
//		GenericMethodFilter filter = new GenericMethodFilter(clazz, methodName, parameters);
//		filter.setPaginated(Boolean.TRUE);
//		filter.setPageIndex(Integer.valueOf(0));
//		filter.setPageSize(MySession.loadDefaultPageSize(clazz));
//		
//		result = Long.valueOf(filter.size());
//
//		return result;
//	}

	public boolean hasBeenChanged() {
		boolean result = true;
		result = result && !hasBeenCreated();
		result = result && !hasBeenDeleted();
		result = result && this._changeRegistry != null;
		result = result && this._changeRegistry.hasChanges();
		return result;
	}
	
	public boolean hasBeenCreated() {
		return !hasId();
	}
	
	public boolean hasBeenDeleted() {
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void resync() {// throws ModelException {
		this.resync(null, true, true, true);
	}

	public void resync(boolean validate) {// throws ModelException {
		this.resync(null, validate, true, true);
	}

	public void resync(boolean validate, boolean enableOnPre) {// throws ModelException {
		this.resync(null, validate, enableOnPre, true);
	}

	public void resync(boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		this.resync(null, validate, enableOnPre, enableOnPost);
	}

	public void resync(String properties) {// throws ModelException {
		this.resync(properties, true, true, true);
	}

	public void resync(String properties, boolean validate) {// throws ModelException {
		this.resync(properties, validate, true, true);
	}

	public void resync(String properties, boolean validate, boolean enableOnPre) {// throws ModelException {
		this.resync(properties, validate, enableOnPre, true);
	}

	public void resync(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {// throws ModelException {
		if (ModelSecurity.checkSecurityAction(this, "resync")) {
			persist(properties, validate, enableOnPre, enableOnPost);
		}
	}

	
	
    protected Class<AbstractEntity> doLoadRealClass() {
    	Class result = null;
//    	java.lang.reflect.Type type = getClass().getGenericSuperclass();
//        ParameterizedType paramType = (ParameterizedType) type;
//        result = (Class<Type>)(paramType.getActualTypeArguments()[0]);
        result = getClass();
        return result;
    }
	
	public AbstractEntity doClone () {
		return doClone(null);
	}

	public AbstractEntity doClone (String properties) {
		AbstractEntity result = null;
		try {
			Class<AbstractEntity> clazz = doLoadRealClass();
			result = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		}
		this.copyTo(properties, result);
		return result;
	}
	
	public void copyTo (AbstractEntity target) {
		copyTo(null, target);
	}

	public void copyTo (String properties, AbstractEntity target) {
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void onRead () {
		trySaveReadLog(null);		
	}

	protected void trySaveReadLog(Long time) {
		try {
			saveReadLog(time);
		}
		catch (RuntimeException e1) {
			if (doCheckErrorSaveLog()) {
				throw e1;
			}
		}
		catch (Throwable e2) {
			if (doCheckErrorSaveLog()) {
				throw new UnusualException(e2);
			}
		}
	}
	

	
	public String toLabel () {
		return toLabel(null);
	}
	
	public String toDescription () {
		return toDescription(null);
	}
	
	public String toImage () {
		return toImage(null);
	}

	public String toLabel (Locale locale) {
		return null;
	}
	
	public String toDescription (Locale locale) {
		return null;
	}
	
	public String toImage (Locale locale) {
		return null;
	}

	
//	public String doAction(String text, Integer ammount) {// throws ModelException {
//		String result = null;
//		Class<?>[] paramTypes = new Class[] {String.class, Integer.class};
//		Object[] paramValues = new Object[] {text, ammount};
//		Object[] paramNamesValues = new Object[] {"text", text, "ammount", ammount};
//		if (this._checkSecurityAction("doAction", String.class, paramTypes, paramValues)) {
//			__startExecutionTime();
//			boolean __stopExecutionTime = false;
//			Long __executionTime = null;
//			boolean __saveLog = true;
//			String __commentLog = _toCommentLog(paramNamesValues);
//			try {
//				System.out.println("EJECUTANDO doAction");
//				__executionTime = __stopExecutionTime();
//				__stopExecutionTime = true;
//				if (__saveLog) {
//					this.trySaveActionLog("doAction", __commentLog, __executionTime);
//				}
//			}
//			catch (ModelException e) {
//				if (!__stopExecutionTime) {
//					__executionTime = __stopExecutionTime();
//				}
//				this.trySaveLogException(e, ERROR + "doAction", __executionTime);
//				throw e;
//			}
//			catch (Exception e) {
//				if (!__stopExecutionTime) {
//					__executionTime = __stopExecutionTime();
//				}
//				this.trySaveLogException(e, ERROR + "methodName", __executionTime);
//				throw new ModelException(e);
//			}
//		}
//		return result;
//	}

	protected String __toCommentLog(Object[] objects) {
		String result = null;
		if (objects != null) {
			result = "";
			for (int i = 0; i < objects.length; i += 2) {
				Object name = objects[i];
				Object value = objects[i + 1];

				String nameText = (name != null ? name.toString() : "");
				String valueText = (value != null ? value.toString() : "");
				
				nameText = (nameText != null ? nameText : "");
				valueText = (valueText != null ? valueText : "");
				
				String property = nameText + ":" + valueText;
				property = (property != null ? property : "");
				result += (result.length() > 0 && property.length() > 0 ? ", " : "") + property;
			}
		}
		return result;
	}

//	public int hashCode () {
//		int result = 0;
////		result = 17;
////		int mult = 37;
////		result = (result * mult) + "propertyName".hashCode();
////		result = (result * mult) + "propertyValue".hashCode();
//		
//		Serializable identifier = this.doGetIdentifier();
//		org.apache.commons.lang.builder.HashCodeBuilder hcBuilder = new org.apache.commons.lang.builder.HashCodeBuilder();
//		if (identifier != null) {
//			hcBuilder.append(identifier);
//		}
//		else {
//			doHashCode(hcBuilder);
//		}
//		result = hcBuilder.toHashCode();
//		return result;
//	}
//
//	protected void doHashCode(org.apache.commons.lang.builder.HashCodeBuilder hcBuilder) {
//		// TODO Auto-generated method stub
//	}

	public int hashCode () {
		int result = 0;
//		result = 17;
//		int mult = 37;
//		result = (result * mult) + "propertyName".hashCode();
//		result = (result * mult) + "propertyValue".hashCode();
		
		int mult = 37;
		result = 17;
		String className = this.getClass().getName();
		result = (result * mult) + className.hashCode();
		result = doHashCode(result, mult);

		return result;
	}

	protected int doHashCode(int hash, int mult) {
		return hash;
	}
	
	protected int _doHashCode(int hash, int mult, String propertyName, Object propertyValue) {
		int result = 0;
		result = hash;
		result = (result * mult) + propertyName.hashCode();
		if (this._checkLoaded(propertyName)) {
			result = (result * mult) + (propertyValue != null ? propertyValue.hashCode() : 0);
		}
		return result;
	}
	
	
	
	
//	public boolean equals (Object o) {
//		boolean result = false;
//		result = (o == this);
//		Object id = this.doGetIdentifier();
//		if (!result && id != null) {
//			AbstractEntity<Type> entity = null;
//			try { entity = (AbstractEntity<Type>)o; } catch (ClassCastException e) {}
//			result = (entity != null ? id.equals(entity.doGetIdentifier()) : false);
//		}
//		return result;
//	}
	
//	public boolean equals(Object obj) {
//		boolean result = false;
//		AbstractEntity obj2 = null;
//		try { obj2 = ((AbstractEntity)obj); } catch (ClassCastException e) {}
//		if (obj2 == null) {
//			result = false;
//		}
//		else if (this == obj) {
//			result = true;
//		}
//		else {
//			Serializable id1 = this.doGetIdentifier();
//			Serializable id2 = obj2.doGetIdentifier();
//			org.apache.commons.lang.builder.EqualsBuilder eqBuilder = new org.apache.commons.lang.builder.EqualsBuilder();
//			if (id1 != null && id2 != null) {
//				eqBuilder.append(id1, id2);
//			}
//			else {
//				this.doEquals(eqBuilder, obj);
//			}
//			result = eqBuilder.isEquals();
//		}
//		return result;
//	}
	
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj == null) {
			result = false;
		}
		else if (obj == this) {
			result = true;
		}
		else if (obj.getClass() !=  getClass()) {
			result = false;
		}
		else {
			result = doEquals(obj);
		}
		return result;
	}

	protected abstract boolean doEquals(Object obj);

	protected boolean _doEquals (String propertyName, Object propertyValue1, AbstractEntity obj, Object propertyValue2) {
		boolean result = false;
		if (_checkLoaded(propertyName) && obj._checkLoaded(propertyName)) {
			result = ObjectUtils.equals(propertyValue1, propertyValue2);
		}
		return result;
	}

//	protected void doEquals(org.apache.commons.lang.builder.EqualsBuilder eqBuilder, Object obj) {
////		super.doEquals(eqBuilder, obj);
////		eqBuilder.append(this.name, obj.name);
////        .appendSuper(super.equals(obj))
////        .append(field1, rhs.field1)
////        .append(field2, rhs.field2)
////        .append(field3, rhs.field3)
////        .isEquals();
//	}
	
	public int compareTo(Object obj) {
		int result = -1;
		if (obj == null) {
			result = -1;
		}
		else if (obj == this) {
			result = 0;
		}
		else if (obj.getClass() != getClass()) {
			result = -1;
		}
		else {
			result = doCompareTo(obj);
		}
		return result;
//		int result = -1;
//		AbstractEntity obj2 = null;
//		try { obj2 = ((AbstractEntity)obj); } catch (ClassCastException e) {}
//		if (obj2 == null) {
//			result = -1;
//		}
//		else if (this == obj) {
//			result = 0;
//		}
//		else {
//			Serializable id1 = this.doGetIdentifier();
//			Serializable id2 = obj2.doGetIdentifier();
//			org.apache.commons.lang.builder.CompareToBuilder compareBuilder = new org.apache.commons.lang.builder.CompareToBuilder();
//			if (id1 != null && id2 != null) {
//				compareBuilder.append(id1, id2);
//			}
//			else {
//				this.doCompare(compareBuilder, obj);
//			}
//			result = compareBuilder.toComparison();
//		}
//		return result;
	}

//	protected void doCompare(org.apache.commons.lang.builder.CompareToBuilder cpBuilder, Object obj) {
//		// TODO Auto-generated method stub
//		
//	}
	protected int doCompareTo (Object obj) {
		return 0;
	}

	protected int _doCompareTo (Object value1, Object value2) {
		int result = 0;
		if (value1 == null && value2 == null) {
			result = 0;
		}
		else if (value1 == null && value2 != null) {
			result = +1;
		}
		else if (value1 != null && value2 == null) {
			result = -1;
		}
		else if (ObjectUtils.equals(value1, value2)) {
			result = 0;
		}
		else {
			result = 0;
			Comparable cmp1 = null; try { cmp1 = (Comparable)value1; } catch (ClassCastException e) {}
			Comparable cmp2 = null; try { cmp2 = (Comparable)value2; } catch (ClassCastException e) {}
			result = cmp1.compareTo(cmp2);
		}
		return result;
	}

	// Person@7f54[name=Stephen,age=29,smoker=false]
	public String toString () {
		String result = null;
		
		String className = getClass().getSimpleName();
		int idHashCode = System.identityHashCode(this);
		String properties = StringUtils.forceNotNull(doToString(), true);

		result = "";
		result += className + "@" + idHashCode;
		result += "[";
		result += properties;
		result += "]";
		
//		org.apache.commons.lang.builder.ToStringBuilder toStringBuilder = new org.apache.commons.lang.builder.ToStringBuilder(this, org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE);
//		doToString(toStringBuilder);

//		result = toStringBuilder.toString();
		
//		if (result == null) {
//			result = toLabel();
//			result = (result != null ? result : super.toString());
//		}
		
		return result;
	}

//	protected void doToString(org.apache.commons.lang.builder.ToStringBuilder toStringBuilder) {
//		
//	}
	protected String doToString() {
		return null;
	}
	
	protected String _addToString(String text, String propertyName, Object propertyValue) {
		String result = null;
		result = StringUtils.forceNotNull(text, true);
		if (this._checkLoaded(propertyName)) {
			String propertyValueText = (propertyValue != null ? propertyValue.toString() : "(null)");
			String property = StringUtils.forceNotNull(propertyName, true) + "=" + propertyValueText;
			result += (result.length() > 0 ? "," : "") + property;
		}
		return result;
	}

	public Boolean getCheckHasId () {
		Boolean result = null;
		Serializable id = doGetIdentifier();
		result = Boolean.valueOf((id != null));
		return result;
	}
	
	protected Kryo _doGetKryo () {
		Kryo result = null;
//		result = MySession.get
		if (result == null) {
			result = new Kryo();
		}
		return result;
	}
	
	public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
		Kryo kryo = _doGetKryo();
		ObjectOutputStream oos = (ObjectOutputStream)output;
		
//		ByteBuffer buf = ByteBuffer.allocate(1024);
//		ByteBufferOutputStream byteBufferOutputStream = new ByteBufferOutputStream(buf);		
		
		Output kryoOutput = new Output(oos);
//		Output kryoOutput = new Output(byteBufferOutputStream);
		doWrite(kryo, kryoOutput);
//		output.close();
	}
	  
	protected void doWrite(Kryo kryo, Output out) {
		kryo.writeObjectOrNull(out, this._changeRegistry, ChangeRegistry.class);
		kryo.writeObjectOrNull(out, this.attributes, Map.class);
	}

	public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
		Kryo kryo = _doGetKryo();
	
		ObjectInputStream ois = (ObjectInputStream)input;
		Input kryoInput = new Input(ois);
		doRead(kryo, kryoInput);
//		input.close();		
	}

	protected void doRead(Kryo kryo, Input in) {
		this._changeRegistry = kryo.readObjectOrNull(in, ChangeRegistry.class);
		this.attributes = kryo.readObjectOrNull(in, Map.class);
	}
	
	public byte[] toBytes () throws IOException {
		byte[] result = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(baos);
		o.writeObject(this);
		o.close();
		result = baos.toByteArray();
		return result;
	}
	
//	public byte[] fromBytes (byte[] bytes) throws IOException {
//		byte[] result = null;
//		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//		ObjectInputStream o = new ObjectInputStream(bais);
//		o.readObject();
//		o.close();
//		result = baos.toByteArray();
//		return result;
//	}
	
	public Object clone () {
		AbstractEntity result = null;
		if (ModelSecurity.checkSecurityAction(this, CREATE)) {
			result = createClone();
			copyTo(result);
		}
		return result;
	}
	
	protected AbstractEntity createClone () {
		return null;
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected abstract String _columnsEager ();

	protected abstract String _columnsLazy ();
	
	protected abstract Object _newInstance ();

	protected abstract void _loadPk (Object target, DbManager db, ResultSet rs);
	
	protected abstract void _loadEager (Object target, DbManager db, ResultSet rs);
	
	protected abstract void _loadLazy (Object target, DbManager db, ResultSet rs);

	
	
	
	
	
	
	
	
	
	
	@Override
	public void copy(Entity source, String properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copy(Entity source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyTo(Entity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyTo(String properties, Entity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		this.persist();
	}

	@Override
	public void save(boolean validate) {
		this.persist(validate);
	}

	@Override
	public void save(boolean validate, boolean enableOnPre) {
		this.persist(validate, enableOnPre);
	}

	@Override
	public void save(boolean validate, boolean enableOnPre, boolean enableOnPost) {
		this.persist(validate, enableOnPre, enableOnPost);
	}

	@Override
	public void save(String properties) {
		this.persist(properties);
	}

	@Override
	public void save(String properties, boolean validate) {
		this.persist(properties, validate);
	}

	@Override
	public void save(String properties, boolean validate, boolean enableOnPre) {
		this.persist(properties, validate, enableOnPre);
	}

	@Override
	public void save(String properties, boolean validate, boolean enableOnPre, boolean enableOnPost) {
		this.persist(properties, validate, enableOnPre, enableOnPost);
	}
	
	protected static Object _runStatic (String className, String method) {
		Object result = null;
		Class clazz = null;
		try {
//			int indexOf = className.indexOf(".");
//			className = (indexOf < 0 ? this.getClass().getPackage().getName() + "." + className : className);
			clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		result = MethodUtils.runStatic(clazz, method, null);
		return result;
	}
	
	protected static Object _runStatic (String className, String method, Object... params) {
		Object result = null;
		Class clazz = null;
		try {
//			int indexOf = className.indexOf(".");
//			className = (indexOf < 0 ? this.getClass().getPackage().getName() + "." + className : className);
			clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		result = MethodUtils.runStatic(clazz, method, params);
		return result;
	}
	
}
