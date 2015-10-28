package org.effortless.orm;

import java.io.Serializable;
import java.sql.ResultSet;

import org.effortless.core.Collections;
//import org.hibernate.Session;

import org.effortless.orm.definition.EntityDefinition;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public abstract class AbstractIdEntity extends AbstractEntity implements IdEntity {

	public AbstractIdEntity () {
//		this(null, null);
		this(null);
	}
	
	protected void initiate () {
		super.initiate();
		initiateId();
		initiateVersion();
		initiateDeleted();
	}
	
//	public AbstractIdEntity (DbManager db) {
//		this(db, null);
//	}
	
	public AbstractIdEntity (/*DbManager db, */Long id) {
		super();
//		this._db = (db != null ? db : loadDefaultDb());
		this.id = id;
	}
	
	protected String doToString() {
		String result = "";
		result += super.doToString();
		result += _addToString(result, "id", this.id);
		result += _addToString(result, "version", this.version);
		result += _addToString(result, "deleted", this.deleted);
		return result;
	}

	protected int doUniqueHashCode(int hash, int mult) {
		int result = hash;
		result = super.doUniqueHashCode(result, mult);
		result = _doHashCode(result, mult, "id", this.id);
		return result;
	}
	
	protected int doHashCode(int hash, int mult) {
		int result = hash;
		result = super.doHashCode(result, mult);
//		result = _doHashCode(result, mult, "id", this.id);
		result = _doHashCode(result, mult, "version", this.version);
		result = _doHashCode(result, mult, "deleted", this.deleted);
		return result;
	}

	protected boolean doEquals(Object obj) {
		boolean result = true;
		AbstractIdEntity _obj = (AbstractIdEntity)obj;
		result = result && _doEquals("id", this.id, _obj, _obj.id);
//		result = result && _doEquals("version", this.version, _obj, _obj.version);
//		result = result && _doEquals("deleted", this.deleted, _obj, _obj.deleted);
		if (result == false) {
			result = runDoEquals(_obj);
		}
		return result;
	}

	protected boolean runDoEquals(Object obj) {
		boolean result = false;
		return result;
	}

	protected int doCompareTo (Object obj) {
		int result = 0;
		AbstractIdEntity _obj = (AbstractIdEntity)obj;
		result = (result == 0 ? super.doCompareTo(_obj) : result);
		result = (result == 0 ? _doCompareTo(this.id, _obj.id) : result);
		result = (result == 0 ? _doCompareTo(this.version, _obj.version) : result);
		result = (result == 0 ? _doCompareTo(this.deleted, _obj.deleted) : result);
		return result;
	}
	
	public boolean hasId () {
		return this.id != null;
	}
	
	
	protected Long id;
	
	protected void initiateId () {
		this.id = null;
	}
	
	public Long getId () {
		return this.id;
	}
	
	protected void setId (Long newValue) {
		this.id = newValue;
	}

	@Override
	public Serializable doGetIdentifier() {
		return this.id;
	}

	@Override
	public void doSetIdentifier(Serializable newValue) {
		this.id = (Long)newValue;
	}

	protected Object[] _getAllParameterChanges() {
//		return new Object[] {Collections.asList("DELETED", "VERSION", "ID"), Collections.asList(this.deleted, this.version, this.id)};
		return new Object[] {new String[] {"DELETED", "VERSION", "ID"}, new Object[] {this.deleted, this.version, this.id}};
	}
	
	protected Object[] _concatAllParameterChanges (Object[] target, Object[] concat) {
//		if (concat[0] != null) {
//			((java.util.List)target[0]).addAll((java.util.Collection)concat[0]);
//		}
//		if (concat[1] != null) {
//			((java.util.List)target[1]).addAll((java.util.Collection)concat[1]);
//		}
//		return target;
		return new Object[] {Collections.addAll(((String[])target[0]), ((String[])concat[0])), Collections.addAll(((Object[])target[1]), ((Object[])concat[1]))};
	}
	
	protected String _concatPropertiesLoader (String properties, String concat) {
		String result = null;
		properties = (properties != null ? properties.trim() : "");
		concat = (concat != null ? concat.trim() : "");
		result = properties + (properties.length() > 0 && concat.length() > 0 ? ", " : "") + concat;
		return result;
	}
	
	
	
	
	
	
	
	
	
	

	
	
	public boolean isSameVersion() {// throws ModelException {
		return false;
	}
	
	public boolean hasBeenDeleted() {
		boolean result = true;
		result = result && this._changeRegistry != null;
		result = result && this._changeRegistry.hasChanges("deleted");
		result = result && this.deleted != null && this.deleted.booleanValue();
		return result;
	}

	protected Integer version;
	
	protected void initiateVersion () {
		this.version = Integer.valueOf(0);
	}
	
	public Integer getVersion() {
		_loadOnDemand("version", this.version, __DEFINITION__);
		return this.version;
	}

	protected void setVersion(Integer newValue) {
		_loadOnDemand("version", this.version, __DEFINITION__);
		_setProperty("version", this.version, this.version = newValue);
	}

	protected Boolean deleted;
	
	protected void initiateDeleted () {
		this.deleted = Boolean.FALSE;
	}
	
	public Boolean getDeleted() {
		_loadOnDemand("deleted", this.deleted, __DEFINITION__);
		return this.deleted;
	}

//	@Override
	protected void setDeleted(Boolean newValue) {
		_loadOnDemand("deleted", this.deleted, __DEFINITION__);
		_setProperty("deleted", this.deleted, this.deleted = newValue);
	}

//	protected void _applyDelete (Session session) {// throws ModelException {
//		this.setDeleted(Boolean.TRUE);
//		session.update(this);
//	}
//	
//	protected void _applyUndelete (Session session) {// throws ModelException {
//		this.setDeleted(Boolean.FALSE);
//		session.update(this);
//	}
	
	protected void doWrite(Kryo kryo, Output out) {
		super.doWrite(kryo, out);
		kryo.writeObjectOrNull(out, this.id, Long.class);
		kryo.writeObjectOrNull(out, this.version, Integer.class);
		kryo.writeObjectOrNull(out, this.deleted, Boolean.class);
	}
	
	protected void doRead(Kryo kryo, Input in) {
		super.doRead(kryo, in);
		this.id = kryo.readObjectOrNull(in, Long.class);
		this.version = kryo.readObjectOrNull(in, Integer.class);
		this.deleted = kryo.readObjectOrNull(in, Boolean.class);
	}
	
	public AbstractIdEntity clone () {
		AbstractIdEntity result = null;
		result = (AbstractIdEntity)super.clone();
//		result._changes = this._changes;
//		result._fcs = this._fcs;
//		result.attributes = this.attributes;
		result.deleted = this.deleted;
//		result.id = this.id;
//		result.version = this.version;
		return result;
	}
	
	

	
	
	
	
	protected String _columnsEager () {
		return "VERSION, DELETED";
	}

	protected String _columnsLazy () {
		return "";
	}
	
	protected abstract Object _newInstance ();

	protected void _loadPk (Object target, DbManager db, ResultSet rs) {
		AbstractIdEntity result = (AbstractIdEntity)target;
		result.id = (Long)__DEFINITION__.loadValue("ID", rs);
		result._setupLoaded("id");
	}
	
	protected void _loadEager (Object target, DbManager db, ResultSet rs) {
		AbstractIdEntity result = (AbstractIdEntity)target;

		result.version = (Integer)__DEFINITION__.loadValue("VERSION", rs);
		result._setupLoaded("version");
		
		result.deleted = (Boolean)__DEFINITION__.loadValue("DELETED", rs);
		result._setupLoaded("deleted");
		_loadFieldDelete(result);
	}
	
	protected void _loadFieldDelete(AbstractIdEntity result) {
		// TODO Auto-generated method stub
		
	}

	protected void _loadLazy (Object target, DbManager db, ResultSet rs) {
		AbstractIdEntity result = (AbstractIdEntity)target;
	}

//	protected static final String _TABLE = "CIUDAD";
//	protected static final String _SEQ = _TABLE + "_SEQ";
	public static final EntityDefinition __DEFINITION__ = new EntityDefinition()
//		.setTableName(_TABLE)
//		.setSequenceName(_SEQ)
		.setPrimaryKey("id", "ID", Long.class)
		.addProperty("version", "VERSION", Integer.class, null, null, "EAGER")
		.addProperty("deleted", "DELETED", Boolean.class, null, null, "EAGER")
		.setDefaultOrderBy("ID ASC");
//		.setDefaultLoader(_LOADER_EAGER)
//		.addLoader(_LOADER_LAZY);
	
	protected void doDelete() {// throws ModelException {
		if (this.deleted != null && this.deleted.booleanValue()) {
			this.doErase();
		}
		else {
			this.setDeleted(Boolean.TRUE);
			this.setVersion(Integer.valueOf(getVersion().intValue() + 1));
			this.doUpdate();
		}
	}
	
	
}
