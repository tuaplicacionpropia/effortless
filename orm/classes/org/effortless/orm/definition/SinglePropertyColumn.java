package org.effortless.orm.definition;

import org.effortless.orm.impl.ColumnDecoder;
import org.effortless.orm.impl.ColumnEncoder;
import org.effortless.orm.impl.ColumnExtraType;
import org.effortless.orm.impl.SqlMapper;

public class SinglePropertyColumn extends Object implements PropertyEntity {
	
	public SinglePropertyColumn () {
		super();
		initiate();
	}
	
//	public ColumnEncoder getColumnEncoder();
//	
//	public ColumnDecoder getColumnDecoder();
	
	public ColumnEncoder getColumnEncoder() {
		ColumnEncoder result = null;
		result = SqlMapper.classToColumnEncoder(this.type);
		return result;
	}

	public ColumnDecoder getColumnDecoder() {
		ColumnDecoder result = null;
		result = SqlMapper.classToColumnDecoder(this.type);
		return result;
	}

	protected void initiate () {
		initiatePropertyName();
		initiateColumnName();
		initiateType();
		initiatePrecision();
		initiateExtraType();
	}
	
	protected String propertyName;
	
	protected void initiatePropertyName () {
		this.propertyName = null;
	}
	
	public String getPropertyName () {
		return this.propertyName;
	}
	
	public void setPropertyName (String newValue) {
		this.propertyName = newValue;
	}
	
	protected String columnName;
	
	protected void initiateColumnName () {
		this.columnName = null;
	}
	
	public String getColumnName () {
		return this.columnName;
	}
	
	public void setColumnName (String newValue) {
		this.columnName = newValue;
	}
	
	protected Class<?> type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	public Class<?> getType () {
		return this.type;
	}
	
	public void setType (Class<?> newValue) {
		this.type = newValue;
	}
	
	protected String precision;
	
	protected void initiatePrecision () {
		this.precision = null;
	}
	
	public String getPrecision () {
		return this.precision;
	}
	
	public void setPrecision (String newValue) {
		this.precision = newValue;
	}
	
	protected ColumnExtraType extraType;
	
	protected void initiateExtraType () {
		this.extraType = null;
	}
	
	public ColumnExtraType getExtraType () {
		return this.extraType;
	}
	
	public void setExtraType (ColumnExtraType newValue) {
		this.extraType = newValue;
	}

	public String getDefinition() {
		String result = null;
		result = "";
		result += this.columnName.trim().toUpperCase();
		result += " ";
		result += SqlMapper.classToTypeDefinition(this.type, this.precision);
		if (this.extraType != null) {
			result += " ";
			result += loadExtraTypeDefinition();
		}
		return result;
	}
	
	protected String loadExtraTypeDefinition () {
		String result = null;
		if (this.extraType != null) {
			if (ColumnExtraType.NOT_NULL.equals(this.extraType)) {
				result = "NOT NULL";
			}
			else if (ColumnExtraType.PRIMARY_KEY.equals(this.extraType)) {
				result = "PRIMARY KEY";
			}
			else if (ColumnExtraType.UNIQUE.equals(this.extraType)) {
				result = "UNIQUE";
			}
			else if (ColumnExtraType.UNIQUE_NOT_NULL.equals(this.extraType)) {
				result = "NOT NULL UNIQUE";
			}
		}
		return result;
	}

	protected String loaderName;
	
	public String getLoaderName() {
		return this.loaderName;
	}
	
	public void setLoaderName (String newValue) {
		this.loaderName = newValue;
	}

	protected java.util.Map attributes = null;
	
	@Override
	public Object getAttribute(String name) {
		return (this.attributes != null && name != null ? this.attributes.get(name) : null);
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (name != null) {
			this.attributes = (this.attributes != null ? this.attributes : new java.util.HashMap());
			this.attributes.put(name, value);
		}
	}
	
}