package org.effortless.orm;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Locale;

import org.effortless.core.ClassUtils;
import org.effortless.core.StringUtils;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.impl.ChangeRegistry;
import org.effortless.orm.impl.EntityFilter;
import org.effortless.orm.util.ToLabel;

//@Entity
//@Table(name="APP_LOGS")
//@javax.persistence.SequenceGenerator(name="sequence_id", sequenceName="app_logs")
public class LogData extends AbstractIdEntity {

	public LogData () {
		super();
	}
	
	public static final String KEY_CLASS_NEEDS = "needs_" + LogData.class.getName() + "_class";
	public static final String KEY_APP_NEEDS = "needs_" + LogData.class.getName() + "_app";
	
	protected void initiate () {
		super.initiate();
		initiateType();
		initiateTargetCode();
		initiateAuthorCode();
		initiateTargetType();
		initiateTargetId();
		initiateAuthorId();
		initiateTarget();
		initiateAuthor();
		initiateDate();

		initiateLocationKeyFrom();
		initiateLocationDescriptionFrom();
		initiateLocationAliasFrom();
		initiateComment();
		initiateExecutionTime();
		initiatePending();
		
		initiateExtraData();
	}
	
	public boolean doCheckLog() {
		return false;
	}
	
	protected String type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	public String getType() {
		_loadOnDemand("type", this.type, __DEFINITION__);
		return this.type;
	}
	
	public void setType(String newValue) {
		_loadOnDemand("type", this.type, __DEFINITION__);
		_setProperty("type", this.type, this.type = newValue);
	}

	protected String targetCode;
	
	protected void initiateTargetCode () {
		this.targetCode = null;
	}
	
	public String getTargetCode() {
		_loadOnDemand("targetCode", this.targetCode, __DEFINITION__);
		return this.targetCode;
	}

	public void setTargetCode(String newValue) {
		_loadOnDemand("targetCode", this.targetCode, __DEFINITION__);
		_setProperty("targetCode", this.targetCode, this.targetCode = newValue);
	}

	protected String authorCode;
	
	protected void initiateAuthorCode () {
		this.authorCode = null;
	}
	
	public String getAuthorCode() {
		_loadOnDemand("authorCode", this.authorCode, __DEFINITION__);
		return this.authorCode;
	}

	public void setAuthorCode(String newValue) {
		_loadOnDemand("authorCode", this.authorCode, __DEFINITION__);
		_setProperty("authorCode", this.authorCode, this.authorCode = newValue);
	}
	
	protected String authorType;
	
	protected void initiateAuthorType () {
		this.authorType = null;
	}
	
	public String getAuthorType() {
		_loadOnDemand("authorType", this.authorType, __DEFINITION__);
		return this.authorType;
	}

	public void setAuthorType(String newValue) {
		_loadOnDemand("authorType", this.authorType, __DEFINITION__);
		_setProperty("authorType", this.authorType, this.authorType = newValue);
	}

	protected String targetType;
	
	protected void initiateTargetType () {
		this.targetType = null;
	}
	
	public String getTargetType() {
		_loadOnDemand("targetType", this.targetType, __DEFINITION__);
		return this.targetType;
	}

	public void setTargetType(String newValue) {
		_loadOnDemand("targetType", this.targetType, __DEFINITION__);
		_setProperty("targetType", this.targetType, this.targetType = newValue);
	}

	protected Long targetId;
	
	protected void initiateTargetId () {
		this.targetId = null;
	}
	
	public Long getTargetId() {
		_loadOnDemand("targetId", this.targetId, __DEFINITION__);
		return this.targetId;
	}

	public void setTargetId(Long newValue) {
		_loadOnDemand("targetId", this.targetId, __DEFINITION__);
		_setProperty("targetId", this.targetId, this.targetId = newValue);
	}

	protected Long authorId;
	
	protected void initiateAuthorId () {
		this.authorId = null;
	}
	
	public Long getAuthorId() {
		_loadOnDemand("authorId", this.authorId, __DEFINITION__);
		return this.authorId;
	}

	public void setAuthorId(Long newValue) {
		_loadOnDemand("authorId", this.authorId, __DEFINITION__);
		_setProperty("authorId", this.authorId, this.authorId = newValue);
	}

	protected org.effortless.orm.Entity target;
	
	protected void initiateTarget () {
		this.target = null;
	}
	
	public org.effortless.orm.Entity getTarget() {
		this.target = (this.target != null ? this.target : loadTarget());
		return this.target;
	}

	public void setTarget(org.effortless.orm.Entity newValue) {
		_setProperty("target", this.target, this.target = newValue);
	}

	protected org.effortless.orm.Entity author;
	
	protected void initiateAuthor () {
		this.author = null;
	}
	
	public org.effortless.orm.Entity getAuthor() {
		this.author = (this.author != null ? this.author : loadAuthor());
		return this.author;
	}

	public void setAuthor(org.effortless.orm.Entity newValue) {
		_setProperty("author", this.author, this.author = newValue);
	}

	protected Date date;
	
	protected void initiateDate () {
		this.date = null;
	}
	
	public Date getDate() {
		_loadOnDemand("date", this.date, __DEFINITION__);
		return this.date;
	}
	
	public void setDate(Date newValue) {
		_loadOnDemand("date", this.date, __DEFINITION__);
		_setProperty("date", this.date, this.date = newValue);
	}
	
	protected String locationKeyFrom;
	
	protected void initiateLocationKeyFrom () {
		this.locationKeyFrom = null;
	}
	
	public String getLocationKeyFrom() {
		_loadOnDemand("locationKeyFrom", this.locationKeyFrom, __DEFINITION__);
		return this.locationKeyFrom;
	}
	
	public void setLocationKeyFrom (String newValue) {
		_loadOnDemand("locationKeyFrom", this.locationKeyFrom, __DEFINITION__);
		_setProperty("locationKeyFrom", this.locationKeyFrom, this.locationKeyFrom = newValue);
	}

	protected String locationDescriptionFrom;
	
	protected void initiateLocationDescriptionFrom () {
		this.locationDescriptionFrom = null;
	}
	
	public String getLocationDescriptionFrom() {
		_loadOnDemand("locationDescriptionFrom", this.locationDescriptionFrom, __DEFINITION__);
		return this.locationDescriptionFrom;
	}
	
	public void setLocationDescriptionFrom (String newValue) {
		_loadOnDemand("locationDescriptionFrom", this.locationDescriptionFrom, __DEFINITION__);
		_setProperty("locationDescriptionFrom", this.locationDescriptionFrom, this.locationDescriptionFrom = newValue);
	}

	protected String locationAliasFrom;
	
	protected void initiateLocationAliasFrom () {
		this.locationAliasFrom = null;
	}
	
	public String getLocationAliasFrom() {
		_loadOnDemand("locationAliasFrom", this.locationAliasFrom, __DEFINITION__);
		return this.locationAliasFrom;
	}
	
	public void setLocationAliasFrom (String newValue) {
		_loadOnDemand("locationAliasFrom", this.locationAliasFrom, __DEFINITION__);
		_setProperty("locationAliasFrom", this.locationAliasFrom, this.locationAliasFrom = newValue);
	}

	protected Long __startExecutionTime () {
		return null;
	}
	
	protected Long __stopExecutionTime () {
		return null;
	}
	
	protected String comment;
	
	protected void initiateComment () {
		this.comment = null;
	}
	
	public String getComment() {
		_loadOnDemand("comment", this.comment, __DEFINITION__);
		return this.comment;
	}
	
	public void setComment (String newValue) {
		_loadOnDemand("comment", this.comment, __DEFINITION__);
		_setProperty("comment", this.comment, this.comment = newValue);
	}

	protected Long executionTime;
	
	protected void initiateExecutionTime () {
		this.executionTime = null;
	}
	
	public Long getExecutionTime () {
		_loadOnDemand("executionTime", this.executionTime, __DEFINITION__);
		return this.executionTime;
	}
	
	public void setExecutionTime (Long newValue) {
		_loadOnDemand("executionTime", this.executionTime, __DEFINITION__);
		_setProperty("executionTime", this.executionTime, this.executionTime = newValue);
	}

	protected Boolean pending;
	
	protected void initiatePending () {
		this.pending = null;
	}
	
	public Boolean getPending () {
		_loadOnDemand("pending", this.pending, __DEFINITION__);
		return this.pending;
	}
	
	public void setPending (Boolean newValue) {
		_loadOnDemand("pending", this.pending, __DEFINITION__);
		_setProperty("pending", this.pending, this.pending = newValue);
	}

	
	
	protected org.effortless.orm.Entity loadObject(Long id, String entityName) {
		org.effortless.orm.Entity result = null;
		result = (id != null && entityName != null ? (org.effortless.orm.Entity)ClassUtils.newInstanceRE(entityName, id) : null);
		return result;
	}
	
	protected org.effortless.orm.Entity loadTarget() {
		return loadObject(this.targetId, this.targetType);
	}

	protected org.effortless.orm.Entity loadAuthor() {
		return loadObject(this.authorId, this.authorType);
	}

	protected Object extraData;

	protected void initiateExtraData() {
		this.extraData = null;
	}

	public Object getExtraData() {
		return this.extraData;
	}

	public void setExtraData(Object newValue) {
		_setProperty("extraData", this.extraData, this.extraData = newValue);
	}

	public String toLabel (Locale locale) {
		String result = null;
		ToLabel tl = new ToLabel(null);
		tl.add(getDate());
		tl.add(getType());
		tl.add(getAuthorCode());
		tl.add(getTargetType());
		tl.add(getTargetCode());
		result = tl.getText();
		return result;
	}

	
	
	
	
	
	
	
	protected Object _newInstance () {
		return new LogData();
	}
	
	protected void _loadEager (Object target, DbManager db, ResultSet rs) {
		super._loadEager(target, db, rs);
		LogData result = (LogData)target;

		result.type = (String)__DEFINITION__.loadValue("LOG_TYPE", rs);
		result._setupLoaded("type");

		result.targetCode = (String)__DEFINITION__.loadValue("LOG_TARGET_CODE", rs);
		result._setupLoaded("targetCode");
		
		result.authorCode = (String)__DEFINITION__.loadValue("LOG_AUTHOR_CODE", rs);
		result._setupLoaded("authorCode");
		
		result.authorType = (String)__DEFINITION__.loadValue("LOG_AUTHOR_TYPE", rs);
		result._setupLoaded("authorType");
		
		result.targetType = (String)__DEFINITION__.loadValue("LOG_TARGET_TYPE", rs);
		result._setupLoaded("targetType");
		
		result.targetId = (Long)__DEFINITION__.loadValue("LOG_TARGET", rs);
		result._setupLoaded("targetId");
		
		result.authorId = (Long)__DEFINITION__.loadValue("LOG_AUTHOR", rs);
		result._setupLoaded("authorId");
		
		result.date = (Date)__DEFINITION__.loadValue("LOG_DATE", rs);
		result._setupLoaded("date");

		result.locationKeyFrom = (String)__DEFINITION__.loadValue("LOG_LOCATION_KEY_FROM", rs);
		result._setupLoaded("locationKeyFrom");
		
		result.locationDescriptionFrom = (String)__DEFINITION__.loadValue("LOG_LOCATION_DESCRIPTION_FROM", rs);
		result._setupLoaded("locationDescriptionFrom");
		
		result.locationAliasFrom = (String)__DEFINITION__.loadValue("LOG_LOCATION_ALIAS_FROM", rs);
		result._setupLoaded("locationAliasFrom");
		
		result.comment = (String)__DEFINITION__.loadValue("LOG_COMMENT", rs);
		result._setupLoaded("comment");
		
		result.executionTime = (Long)__DEFINITION__.loadValue("LOG_EXECUTION_TIME", rs);
		result._setupLoaded("executionTime");
		
		result.pending = (Boolean)__DEFINITION__.loadValue("LOG_PENDING", rs);
		result._setupLoaded("pending");
	}
	
	protected void _loadLazy (Object target, DbManager db, ResultSet rs) {
		super._loadLazy(target, db, rs);
		LogData result = (LogData)target;

		result.extraData = __DEFINITION__.loadValue("LOG_EXTRA", rs);
		result._setupLoaded("extraData");
	}

	protected static final LogData _pivot = new LogData();
	
	protected static final String _TABLE = "LOGGER";
	protected static final String _SEQ = _TABLE + "_SEQ";
	public static final EntityDefinition __DEFINITION__ = new EntityDefinition()
		.setTableName(_TABLE)
		.setSequenceName(_SEQ)
		.addParent(AbstractIdEntity.__DEFINITION__)
//		.setPrimaryKey("id", "ID", Long.class)
		.addProperty("type", "LOG_TYPE", String.class, "255", null, "EAGER")
		.addProperty("targetCode", "LOG_TARGET_CODE", String.class, "255", null, "EAGER")
		.addProperty("authorCode", "LOG_AUTHOR_CODE", String.class, "255", null, "EAGER")
		.addProperty("authorType", "LOG_AUTHOR_TYPE", String.class, "255", null, "EAGER")
		.addProperty("targetType", "LOG_TARGET_TYPE", String.class, "255", null, "EAGER")
		.addProperty("targetId", "LOG_TARGET", Integer.class, null, null, "EAGER")
		.addProperty("authorId", "LOG_AUTHOR", Integer.class, null, null, "EAGER")
		.addProperty("date", "LOG_DATE", Date.class, null, null, "EAGER")
		.addProperty("locationKeyFrom", "LOG_LOCATION_KEY_FROM", String.class, "255", null, "EAGER")
		.addProperty("locationDescriptionFrom", "LOG_LOCATION_DESCRIPTION_FROM", String.class, "255", null, "EAGER")
		.addProperty("locationAliasFrom", "LOG_LOCATION_ALIAS_FROM", String.class, "255", null, "EAGER")
		.addProperty("comment", "LOG_COMMENT", String.class, "3072", null, "EAGER")
		.addProperty("executionTime", "LOG_EXECUTION_TIME", Long.class, null, null, "EAGER")
		.addProperty("pending", "LOG_PENDING", Boolean.class, null, null, "EAGER")
		.addProperty("extraData", "LOG_EXTRA", ChangeRegistry.class, null, null, "EAGER")
		.setDefaultOrderBy("date DESC, id ASC")
		.setDefaultLoader(new EagerPropertiesLoader(LogData._pivot))
		.addLoader(new LazyPropertiesLoader(LogData._pivot));

	protected Object[] _getAllParameterChanges() {
//		return new Object[] {new String[] {"LOG_TYPE", "LOG_TARGET_CODE", "LOG_AUTHOR_CODE", "ID"}, new Object[] {this.type, this.targetCode, this.authorCode, this.id}};
		return super._concatAllParameterChanges(
			new Object[] {
				new String[] {"LOG_TYPE", "LOG_TARGET_CODE", "LOG_AUTHOR_CODE", "LOG_AUTHOR_TYPE", "LOG_TARGET_TYPE", "LOG_TARGET", "LOG_AUTHOR", "LOG_DATE", "LOG_LOCATION_KEY_FROM", "LOG_LOCATION_DESCRIPTION_FROM", "LOG_LOCATION_ALIAS_FROM", "LOG_COMMENT", "LOG_EXECUTION_TIME", "LOG_PENDING", "LOG_EXTRA"}, 
				new Object[] {this.type, this.targetCode, this.authorCode, this.authorType, this.targetType, this.targetId, this.authorId, this.date, this.locationKeyFrom, this.locationDescriptionFrom, this.locationAliasFrom, this.comment, this.executionTime, this.pending, this.extraData}
			}, 
			super._getAllParameterChanges());
	}

	protected String _columnsEager () {
		return StringUtils.concat(super._columnsEager(), "LOG_TYPE, LOG_TARGET_CODE, LOG_AUTHOR_CODE, LOG_AUTHOR_TYPE, LOG_TARGET_TYPE, LOG_TARGET, LOG_AUTHOR, LOG_DATE, LOG_LOCATION_KEY_FROM, LOG_LOCATION_DESCRIPTION_FROM, LOG_LOCATION_ALIAS_FROM, LOG_COMMENT, LOG_EXECUTION_TIME, LOG_PENDING", ", ");
	}

	protected String _columnsLazy () {
		return StringUtils.concat(super._columnsLazy(), "LOG_EXTRA", ", ");
	}

	public static Filter listAll () {
		return EntityFilter.buildEntityFilter(__DEFINITION__, __DEFINITION__.getDefaultLoader());
	}

	protected EntityDefinition _loadDefinition() {
		return LogData.__DEFINITION__;
	}
	
}
