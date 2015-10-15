package org.effortless.orm.definition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.effortless.orm.impl.ChangeRegistry;
import org.effortless.orm.impl.ChangeRegistryItem;
import org.effortless.orm.impl.ColumnDecoder;
import org.effortless.orm.impl.ColumnEncoder;
import org.effortless.orm.impl.ColumnExtraType;
import org.effortless.orm.impl.PropertiesLoader;
import org.effortless.orm.impl.TableIndex;

public class EntityDefinition extends Object {

	public EntityDefinition () {
		super();
		initiate();
	}
	
//	public EntityDefinition (String tableName) {
//		this();
//		setTableName(tableName);
//	}
	
//	public EntityDefinition (String tableName, String sequenceName) {
//		this();
//		setTableName(tableName);
//		setSequenceName(sequenceName);
//	}
	
	protected void initiate () {
		initiateTableName();
		initiateSequenceName();
		initiateProperties();
		initiatePrimaryKey();
		initiateDefaultOrderBy();
		initiateIndexes();
		initiateEntityClass();
		initiateFinderProperties();
		this._decoders = new java.util.HashMap<String, ColumnDecoder>();
		this._encoders = new java.util.HashMap<String, ColumnEncoder>();
		this._loaders = new java.util.HashMap<String, PropertiesLoader>();
		this._mapToColumnName = new java.util.HashMap<String, String>();
		this._mapColumns = new java.util.HashMap<String, SinglePropertyColumn>();
	}
	
	protected String finderProperties;
	
	protected void initiateFinderProperties () {
		this.finderProperties = null;
	}
	
	public String getFinderProperties () {
		return this.finderProperties;
	}
	
	public void setFinderProperties (String newValue) {
		this.finderProperties = newValue;
	}
	
	protected String tableName;
	
	protected void initiateTableName () {
		this.tableName = null;
	}
	
	public String getTableName () {
		return this.tableName;
	}
	
	public EntityDefinition setTableName (String newValue) {
		this.tableName = newValue;
		return this;
	}
	
	protected String sequenceName;
	
	protected void initiateSequenceName () {
		this.sequenceName = null;
	}
	
	public String getSequenceName () {
		return this.sequenceName;
	}
	
	public EntityDefinition setSequenceName (String newValue) {
		this.sequenceName = newValue;
		return this;
	}
	
	protected java.util.List<TableIndex> indexes;
	
	protected void initiateIndexes () {
		this.indexes = new java.util.ArrayList<TableIndex>();
	}
	
	public java.util.List<TableIndex> getIndexes () {
		return this.indexes;
	}
	
	public EntityDefinition setIndexes (java.util.List<TableIndex> newValue) {
		this.indexes = newValue;
		return this;
	}
	
	protected java.util.List<PropertyEntity> properties;
	protected java.util.Map<String, SinglePropertyColumn> _mapColumns;
	
	protected void initiateProperties () {
		this.properties = new java.util.ArrayList<PropertyEntity>();
	}
	
	public java.util.List<PropertyEntity> getProperties () {
		return this.properties;
	}
	
	public EntityDefinition setProperties (java.util.List<PropertyEntity> newValue) {
		this.properties = newValue;
		return this;
	}
	
	public EntityDefinition addListProperty(String pName, String tableName, String ownerColumnName, String itemColumnName, Class<?> type, String loaderName) {
		ListPropertyEntity tableColumn = new ListPropertyEntity();
		tableColumn.setPropertyName(pName);
		tableColumn.setTableName(tableName);
		tableColumn.setOwnerColumnName(ownerColumnName);
		tableColumn.setItemColumnName(itemColumnName);
		tableColumn.setType(type);
		tableColumn.setLoaderName(loaderName);
		System.out.println("ownerColumnName = " + ownerColumnName);
		this.properties.add(tableColumn);
		tableColumn.setDefinition(this);
		return this;
	}
	
	public EntityDefinition setupListProperty (String pName, boolean inner) {
		ListPropertyEntity property = (ListPropertyEntity)this.getProperty(pName);
		property.setInner(inner);
		return this;
	}
	
	public EntityDefinition setupProperty (String pName, String name, Object value) {
		PropertyEntity property = this.getProperty(pName);
		property.setAttribute(name, value);
		return this;
	}
	
	public EntityDefinition addReferenceProperty(String pName, String columnName, Class<?> type, ColumnExtraType extraType, String loaderName) {
		ReferencePropertyColumn tableColumn = new ReferencePropertyColumn();
		tableColumn.setPropertyName(pName);
		tableColumn.setColumnName(columnName);
		tableColumn.setType(type);
		tableColumn.setPrecision("255");
		tableColumn.setExtraType(extraType);
		tableColumn.setLoaderName(loaderName);
		addProperty(tableColumn);
		return this;
	}
	
	public EntityDefinition addEnumProperty(String pName, String columnName, Class<?> type, ColumnExtraType extraType, String loaderName) {
		EnumPropertyColumn tableColumn = new EnumPropertyColumn();
		tableColumn.setPropertyName(pName);
		tableColumn.setColumnName(columnName);
		tableColumn.setType(type);
		tableColumn.setPrecision("255");
		tableColumn.setExtraType(extraType);
		tableColumn.setLoaderName(loaderName);
		addProperty(tableColumn);
		return this;
	}
	
	public EntityDefinition addFileProperty(String pName, String columnName, Class<?> type, ColumnExtraType extraType, String loaderName) {
		FilePropertyColumn tableColumn = new FilePropertyColumn();
		tableColumn.setPropertyName(pName);
		tableColumn.setColumnName(columnName);
		tableColumn.setType(type);
		tableColumn.setPrecision(null);
		tableColumn.setExtraType(extraType);
		tableColumn.setLoaderName(loaderName);
		addProperty(tableColumn);
		return this;
	}
	
	
	
	public EntityDefinition addProperty (String pName, String columnName, Class<?> type, String precision, ColumnExtraType extraType, String loaderName) {
		SinglePropertyColumn tableColumn = new SinglePropertyColumn();
		tableColumn.setPropertyName(pName);
		tableColumn.setColumnName(columnName);
		tableColumn.setType(type);
		tableColumn.setPrecision(precision);
		tableColumn.setExtraType(extraType);
		tableColumn.setLoaderName(loaderName);
		addProperty(tableColumn);
		return this;
	}

	public EntityDefinition addIndex (String name, Boolean unique, String... columns) {
		TableIndex tableIndex = new TableIndex(name, columns);
		tableIndex.setUnique(unique);
		addIndex(tableIndex);
		return this;
	}
	
	public EntityDefinition addIndex (TableIndex index) {
		if (index != null) {
			this.indexes.add(index);
		}
		return this;
	}
	
	public EntityDefinition addProperty(SinglePropertyColumn tableColumn) {
		if (tableColumn != null) {
			this.properties.add(tableColumn);
			
			String columnName = tableColumn.getColumnName();
			String propertyName = tableColumn.getPropertyName();
			
			this._mapToColumnName = (this._mapToColumnName != null ? this._mapToColumnName : new java.util.HashMap<String, String>());
			this._mapToColumnName.put(propertyName, columnName);
			
			this._mapColumns.put(columnName, tableColumn);
			
			ColumnDecoder columnDecoder = tableColumn.getColumnDecoder();
			if (columnDecoder != null) {
				this._decoders.put(columnName, columnDecoder);
			}

			ColumnEncoder columnEncoder = tableColumn.getColumnEncoder();
			if (columnEncoder != null) {
				this._encoders.put(columnName, columnEncoder);
			}
			
if (false) {
			ColumnExtraType extraType = tableColumn.getExtraType();
			if (ColumnExtraType.UNIQUE.equals(extraType) || ColumnExtraType.UNIQUE_NOT_NULL.equals(extraType)) {
				String indexName = "IDX_" + this.tableName + "_" + columnName;
				addIndex(indexName, Boolean.TRUE, columnName);
			}
}
		}
		return this;
	}

	
	
	protected SinglePropertyColumn primaryKey;
	
	protected void initiatePrimaryKey () {
		this.primaryKey = null;
	}
	
	public SinglePropertyColumn getPrimaryKey () {
		if (this.primaryKey == null && this.parent != null) {
			this.primaryKey = this.parent.getPrimaryKey();
		}
		return this.primaryKey;
	}
	
	public EntityDefinition setPrimaryKey (SinglePropertyColumn newValue) {
		this.primaryKey = newValue;
		addProperty(newValue);
		return this;
	}
	
	public EntityDefinition setPrimaryKey (String pName, String columnName, Class<?> type) {
		SinglePropertyColumn tableColumn = new SinglePropertyColumn();
		tableColumn.setPropertyName(pName);
		tableColumn.setColumnName(columnName);
		tableColumn.setType(type);
		tableColumn.setExtraType(ColumnExtraType.PRIMARY_KEY);
		setPrimaryKey(tableColumn);
		return this;
	}

	protected java.util.Map<String, ColumnDecoder> _decoders;
	
	public ColumnDecoder getDecoder (String columnName) {
		return this._decoders.get(columnName);
	}
	
	public Object loadValue (String columnName, ResultSet rs) {
		Object result = null;
		ColumnDecoder decoder = getDecoder(columnName);
		if (decoder == null && this.parent != null) {
			decoder = this.parent.getDecoder(columnName);
		}
		if (decoder == null) {
			throw new NullPointerException();
		}
		result = decoder.decode(columnName, rs);
		return result;
	}

	protected java.util.Map<String, ColumnEncoder> _encoders;
	
	public ColumnEncoder getColumnEncoder(String columnName) {
		ColumnEncoder result = null;
		result = (columnName != null ? this._encoders.get(columnName) : null);
		if (result == null && this.parent != null) {
			result = this.parent.getColumnEncoder(columnName);
		}
		return result;
	}
	
	public ColumnEncoder[] getColumnEncoders(String[] columns) {
		ColumnEncoder[] result = null;
		int length = (columns != null ? columns.length : 0);
		result = new ColumnEncoder[length];
		for (int i = 0; i < length; i++) {
			ColumnEncoder encoder = getColumnEncoder(columns[i]);
			result[i] = encoder;
		}
		return result;
	}

	
	
	public void saveValue (Object owner, PreparedStatement stm, String columnName, int index, Object value) {
		ColumnEncoder encoder = this._encoders.get(columnName);
		if (encoder == null) {
			throw new NullPointerException();
		}
		encoder.encode(owner, stm, index, value);
	}
	
	protected String defaultOrderBy;
	
	protected void initiateDefaultOrderBy () {
		this.defaultOrderBy = null;
	}
	
	public String getDefaultOrderBy () {
		return this.defaultOrderBy;
	}
	
	public EntityDefinition setDefaultOrderBy(String newValue) {
		this.defaultOrderBy = newValue;
		return this;
	}

	public java.util.List<String> getColumnDefinitions() {
		java.util.List<String> result = null;
		result = new java.util.ArrayList<String>();
		if (this.parent != null) {
			java.util.List<String> parentColumns = this.parent.getColumnDefinitions();
			if (parentColumns != null) {
				result.addAll(parentColumns);
			}
		}
		int columnsSize = (this.properties != null ? this.properties.size() : 0);
		for (int i = 0; i < columnsSize; i++) {
			PropertyEntity property = this.properties.get(i);
			SinglePropertyColumn column = null;
			try { column = (SinglePropertyColumn)property; } catch (ClassCastException e) {}
			if (column != null) {
				String columnDefinition = (column != null ? column.getDefinition() : null);
				result.add(columnDefinition);
			}
		}
		return result;
	}

	protected Object[] _swapFirstLast (Object[] array) {
		if (array != null && array.length > 1) {
			int lastIdx = array.length - 1;
			Object first = array[0];
			Object last = array[lastIdx];
			array[0] = last;
			array[lastIdx] = first;
		}
		return array;
	}
	
	protected Object[] _removeFirst (Object[] array) {
		Object[] result = null;
		int length = (array != null ? array.length : 0);
		result = new Object[length];
		for (int i = 1; i < length; i++) {
			result[i] = array[i];
		}
		return result;
	}
	
	protected java.util.Map<String, String> _mapToColumnName;
	
	public String toColumnName (String propertyName) {
		String result = null;
		result = this._mapToColumnName.get(propertyName);
		if (result == null && this.parent != null) {
			result = this.parent._mapToColumnName.get(propertyName);
		}
		return result;
	}
	
	public Object[] getParameterChanges(ChangeRegistry registry, boolean forUpdate) {
		Object[] result = null;
		if (registry != null && registry.hasChanges()) {
			java.util.List<ChangeRegistryItem> changes = registry.getChangesList();
			if (changes != null) {
				int changesSize = changes.size();
				
//				java.util.List columnNames = new java.util.ArrayList();
//				java.util.List pValues = new java.util.ArrayList();

//				String[] columnNames = new String[changesSize + 1];
//				Object[] pValues = new Object[changesSize + 1];
				java.util.List columnNames = new java.util.ArrayList();
				java.util.List pValues = new java.util.ArrayList();

//				int inc = (forUpdate ? 1 : 0);
//				int realSize = changesSize + inc;
//				columnNames = new String[realSize];
//				pValues = new Object[realSize];
				for (int i = 0; i < changesSize; i++) {
					ChangeRegistryItem change = changes.get(i);
					String pName = change.getPropertyName();
					String columnName = toColumnName(pName);
					if (columnName != null) {
	//					columnNames[i] = columnName;
	//					pValues[i] = change.getNewValue();

//						columnNames.add(columnName);
//						pValues.add(change.getNewValue());

//						columnNames[i] = columnName;
//						pValues[i] = change.getNewValue();

						columnNames.add(columnName);
						pValues.add(change.getNewValue());
					}
				}
				
				if (changesSize > 0) {
					columnNames.add(null);
					pValues.add(null);
				}
				
				result = new Object[2];
//				result[0] = columnNames.toArray(new String[0]);
//				result[1] = pValues.toArray(new Object[0]);
				result[0] = columnNames.toArray(new String[0]);
				result[1] = pValues.toArray(new Object[0]);
			}

		}
		return result;
	}

	protected PropertiesLoader defaultLoader;
	
	public PropertiesLoader getDefaultLoader () {
		return this.defaultLoader;
	}
	
	public EntityDefinition setDefaultLoader(PropertiesLoader defaultLoader) {
		this.defaultLoader = defaultLoader;
		addLoader(defaultLoader);
		return this;
	}

	public EntityDefinition addLoader(PropertiesLoader loader) {
		this._loaders.put(loader.getName(), loader);
		return this;
	}

	protected java.util.Map<String, PropertiesLoader> _loaders;

	public PropertyEntity getProperty (String propertyName) {
		PropertyEntity result = null;
		String columnName = this.toColumnName(propertyName);
		result = this._mapColumns.get(columnName);
		if (result == null && propertyName != null) {
			int length = (this.properties != null ? this.properties.size() : 0);
			for (int i = 0; i < length; i++) {
				PropertyEntity item = this.properties.get(i);
				if (item != null && propertyName.equals(item.getPropertyName())) {
					result = item;
					break;
				}
			}
		}
		if (result == null && propertyName != null && this.parent != null) {
			result = this.parent.getProperty(propertyName);
		}
		return result;
	}
	
	public PropertiesLoader getPropertiesLoader(String propertyName) {
		PropertiesLoader result = null;
		PropertyEntity tColumn = getProperty(propertyName);
		String loaderName = tColumn.getLoaderName();
		result = this._loaders.get(loaderName);
		if (result == null && propertyName != null && this.parent != null) {
			result = this.parent.getPropertiesLoader(propertyName);
		}
		return result;
	}

	protected EntityDefinition parent;
	
	public EntityDefinition addParent(EntityDefinition definition) {
		this.parent = definition;
		return this;
	}

	protected Class entityClass;
	
	protected void initiateEntityClass () {
		this.entityClass = null;
	}
	
	public Class getEntityClass () {
		return this.entityClass;
	}
	
	public EntityDefinition setEntityClass (Class newValue) {
		this.entityClass = newValue;
		return this;
	}


	protected java.util.Map attributes = null;
	
	public Object getAttribute(String name) {
		return (this.attributes != null && name != null ? this.attributes.get(name) : null);
	}

	public void setAttribute(String name, Object value) {
		if (name != null) {
			this.attributes = (this.attributes != null ? this.attributes : new java.util.HashMap());
			this.attributes.put(name, value);
		}
	}

	public PropertyEntity getPropertyFromColumn(String column) {
		PropertyEntity result = null;
		result = (PropertyEntity)this._mapColumns.get(column);
		if (result == null && this.parent != null) {
			result = this.parent.getPropertyFromColumn(column);
		}
		return result;
	}
	
}
