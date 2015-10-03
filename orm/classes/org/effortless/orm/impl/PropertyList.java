package org.effortless.orm.impl;

//import java.util.AbstractSequentialList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;




//import org.effortless.fastsql.util.CvtrUtils;
import org.effortless.orm.AbstractIdEntity;
import org.effortless.orm.DbManager;
import org.effortless.orm.Entity;
import org.effortless.orm.Filter;
import org.effortless.orm.InnerEntity;
import org.effortless.orm.MySession;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.definition.ListPropertyEntity;

public class PropertyList extends AbstractPropertyList {

	protected PropertyList () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.owner = null;
		this.listPropertyEntity = null;
		this.itemsByIndex = new java.util.HashMap();
		this.itemsByValue = new java.util.HashMap();
//		this.listNew = null;
//		this.listDelete = null;
		this._size = -1;
		this._countAddSize = 0;
		this._countRmSize = 0;
		this._cursor = 0;
		this._changeSupport = null;
		this._entityDefinition = null;
	}
	
	public static PropertyList create (Class clazz, AbstractIdEntity owner, /*Filter<T> filter, */ListPropertyEntity listPropertyEntity, EntityDefinition entityDefinition, Filter _filter) {
		PropertyList result = null;
		result = new PropertyList();
		result.owner = owner;
		result._filter = _filter;
		result.listPropertyEntity = listPropertyEntity;
		result._entityDefinition = entityDefinition;
		result._type = clazz;
		result._addOwnerListener();
		return result;
	}
	
	protected EntityDefinition _entityDefinition;
	
	public EntityDefinition getTargetEntityDefinition () {
		return this._entityDefinition;
	}
	
	protected void _addOwnerListener() {
		if (this.owner != null) {
			final PropertyList _this = this;
			this.addPropertyChangeListener(new PropertyChangeListener() {
	
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					_this._notifyChangeToOwner();
				}
				
			});
		}
	}

	protected void _notifyChangeToOwner () {
		String fProperty = (this.listPropertyEntity != null ? this.listPropertyEntity.getPropertyName() : null);
		if (this.owner != null && fProperty != null) {
			this.owner.addChange(fProperty, this);
		}
	}
	
	
	protected AbstractIdEntity owner;
	protected int _size;
	protected boolean _clear;
	protected int _countAddSize;
	protected int _countRmSize;
	protected int _cursor;
	protected Class _type;
	
	public Class getType () {
		return this._type;
	}
	
	public Class targetClass () {
		return this._type;
	}
	
	
	protected Filter _filter;
	
	
	
//	protected Filter<Type> filter;
	
	protected ListPropertyEntity listPropertyEntity;
	protected java.util.Map itemsByIndex;
	protected java.util.Map itemsByValue;
	protected java.util.List itemsToRemove;
	
	
//	protected java.util.List<Type> listNew;
//	protected java.util.List<Type> listDelete;
//	protected java.util.List<Type> listChange;
//	protected java.util.List<Type> listPage;

	@Override
	public void clear() {
		this._clear = true;
		this.itemsByIndex.clear();
		this.itemsByValue.clear();
		this._countAddSize = 0;
		this._countRmSize = 0;
		this._cursor = 0;
//		this.listNew = null;
//		this.listDelete = null;
	}

	@Override
	public boolean contains(Object o) {
		boolean result = false;
		AbstractIdEntity item = (AbstractIdEntity)o;
		if (item != null) {
			result = (!result ? this.itemsByValue.get(item) != null : result);
			result = (!result && item.getId() != null ? existsOnDb(item) : result);
		}
		return result;
	}

	@Override
	public boolean remove(Object o) {
		boolean result = false;
		if (o != null && contains(o)) {
			AbstractIdEntity item = (AbstractIdEntity)o;
			Integer index = (Integer)this.itemsByValue.get(item);
			PropertyListItem element = (index != null ? (PropertyListItem)this.itemsByIndex.get(index) : null);
			if (element != null) {
				PropertyListItemStatus status = element.getStatus();
				if (PropertyListItemStatus.NEW.equals(status)) {
					this.itemsByValue.remove(item);
					this.itemsByIndex.remove(index);
					adjustIndexes(index);
					this._countAddSize--;
					if (this._size > -1) {
						this._size--;
					}
					_notifyChange();
					result = true;
				}
				else if (PropertyListItemStatus.CHANGELESS.equals(status) || PropertyListItemStatus.MODIFIED.equals(status)) {
					element.setStatus(PropertyListItemStatus.REMOVE);
					this.itemsByValue.remove(item);
					this.itemsByIndex.remove(index);
					this.itemsToRemove = (this.itemsToRemove != null ? this.itemsToRemove : new java.util.ArrayList());
					this.itemsToRemove.add(element);
					adjustIndexes(index);
					this._filter.exclude(item);
					if (this._size > -1) {
						this._size--;
					}
					_notifyChange();
					result = true;
				}
			}
		}
		return result;
	}

	protected void adjustIndexes(Integer index) {
		if (index != null) {
			int i = index.intValue();
			java.util.Collection values = this.itemsByIndex.values();
			java.util.Iterator iterator = (values != null ? values.iterator() : null);
			if (iterator != null) {
				java.util.List items = new java.util.ArrayList();
				while (iterator.hasNext()) {
					PropertyListItem item = (PropertyListItem)iterator.next();
					Integer itemIndex = item.getIndex();
					if (itemIndex != null && itemIndex.intValue() > i) {
						items.add(item);
					}
				}
				for (Object listItem : items) {
					PropertyListItem item = (PropertyListItem)listItem;
					Integer itemIndex = item.getIndex();
					AbstractIdEntity itemValue = (AbstractIdEntity)item.getValue();
					this.itemsByIndex.remove(itemIndex);
					this.itemsByValue.remove(itemValue);
					Integer newIndex = Integer.valueOf(itemIndex.intValue() - 1);
					this.itemsByIndex.put(newIndex, item);
					this.itemsByValue.put(itemValue , newIndex);
					item.setIndex(newIndex);
				}
			}
			
		}
	}

	@Override
	public void add(int index, Object e) {
		if (e != null && !contains(e)) {
			int size = size();
			if (index >= 0 && index <= size) {
				Integer newIndex = Integer.valueOf(index);
				PropertyListItem element = new PropertyListItem();
				element.setIndex(newIndex);
				element.setStatus(PropertyListItemStatus.NEW);
				element.setValue(e);
				this.itemsByIndex.put(newIndex, element);
				this.itemsByValue.put(e, newIndex);
				this._countAddSize++;
				if (this._size > -1) {
					this._size++;
				}
				_notifyChange();
			}
			else {
				throw new java.lang.ArrayIndexOutOfBoundsException();
			}
		}
	}

	@Override
	public int indexOf(Object o) {
		int result = -1;
		if (o != null) {
			AbstractIdEntity item = (AbstractIdEntity)o;
			Integer index = (Integer)this.itemsByValue.get(item);
			result = (index != null ? index.intValue() : -1);
			if (contains(o)) {
				do {
					_loadPage();
					index = (Integer)this.itemsByValue.get(item);
					result = (index != null ? index.intValue() : -1);
					this._cursor += (result > -1 ? 0 : +1);
				} while (result > -1);
			}
		}
		return result;
	}
	
	@Override
	public Object get(int index) {
		Object result = null;
		PropertyListItem item = (PropertyListItem)this.itemsByIndex.get(Integer.valueOf(index));
		if (item == null) {
			this._cursor = index;
			_loadPage();
			item = (PropertyListItem)this.itemsByIndex.get(Integer.valueOf(index));
		}
		if (item == null) {
			throw new java.lang.ArrayIndexOutOfBoundsException();
		}
		result = (item != null ? item.getValue() : null);
		return result;
	}

	@Override
	public int size() {
		if (this._size <= -1) {
			int dbSize = _countDbSize();
			this._size = dbSize + this._countAddSize - this._countRmSize;
		}
		return this._size;
	}

	protected void _loadPage() {
		if (!this._clear) {
			int index = this._cursor;
			Boolean paginated = this._filter.getPaginated();
			Integer _pageSize = this._filter.getPageSize();
			int pageSize = (_pageSize != null ? _pageSize.intValue() : 0);
			if (paginated && pageSize > 0) {
				int newPageIndex = index / pageSize;
				this._filter.setPageIndex(Integer.valueOf(newPageIndex));
			}
			int size = size();
			int endIdx = (paginated && pageSize > 0 ? index + pageSize : size);
			endIdx = Math.min(endIdx, size);
			for (int i = index; i < endIdx; i++) {
				AbstractIdEntity item = (AbstractIdEntity)this._filter.get(i);

				PropertyListItem element = new PropertyListItem();
				Integer newIndex = Integer.valueOf(i);
				element.setIndex(newIndex);
				element.setStatus(PropertyListItemStatus.CHANGELESS);
				element.setValue(item);
				item.addPropertyChangeListener(_doGetChangeListener());
				this.itemsByIndex.put(newIndex, element);
				this.itemsByValue.put(item, newIndex);
			}
		}
	}
	
	protected PropertyChangeListener _changeListener;
	
	protected PropertyChangeListener _doGetChangeListener () {
		if (this._changeListener == null) {
			final PropertyList _this = this;
			this._changeListener = new PropertyChangeListener () {

				public void propertyChange(PropertyChangeEvent event) {
					Integer index = (Integer)_this.itemsByValue.get(event.getSource());
					PropertyListItem item = (index != null ? (PropertyListItem)_this.itemsByIndex.get(index) : null);
					if (item != null) {
						PropertyListItemStatus oldStatus = item.getStatus();
						if (!PropertyListItemStatus.MODIFIED.equals(oldStatus)) {
							item.setStatus(PropertyListItemStatus.MODIFIED);
						}
						else {
							boolean hasChanges = ((AbstractIdEntity)item.getValue()).hasChanges();
							if (!hasChanges) {
								item.setStatus(PropertyListItemStatus.CHANGELESS);
							}
						}
						_this._notifyChangeToOwner();
					}
				}
				
			};
		}
		return this._changeListener;
	}

	protected boolean existsOnDb(AbstractIdEntity item) {
		boolean result = false;
		result = (!this._clear && this.owner != null && this.owner.getId() != null ? this.existsOnDbOwner(item) : false);
		return result;
	}
	
	protected boolean existsOnDbOwner (AbstractIdEntity item) {
		boolean result = false;
		if (this.listPropertyEntity == null) {
			result = this._filter.contains(item);
		}
		else {
			String itemColumnName = this.listPropertyEntity.getItemColumnName();//ITEM_ID
			String ownerColumnName = this.listPropertyEntity.getOwnerColumnName();//OWNER_ID
			String tableName = this.listPropertyEntity.getTableName();//TASK_ATTACHMENTS
			Long ownerId = this.owner.getId();
			Long itemId = item.getId();
			DbManager db = MySession.getDb();
			if (itemColumnName == null) {
				itemColumnName = "ID";
				tableName = tableName;
			}
//			if (itemColumnName != null) {
				String schemaTableName = db.applyCurrentSchema(tableName);
				String query = "";
				query += "SELECT COUNT(*) FROM " + schemaTableName + " ";
				query += "WHERE " + ownerColumnName + " = ? AND " + itemColumnName + " = ?";
				java.sql.ResultSet rs = db.query(query, new Object[]{ownerId, itemId});
				try {
					if (rs != null && rs.next()) {
						Number number = (Number)rs.getObject(1);
						result = (number != null && number.longValue() > 0L);
					}
				}
				catch (Exception e) {
					throw new org.effortless.core.UnusualException(e);
				}
				db.closeAndStm(rs);
//			}
		}
		return result;
	}

	protected int _countDbSize() {
		int result = 0;
		result = (!this._clear && this.owner != null && this.owner.getId() != null ? this._filter.size() : 0);
		return result;
	}

	protected java.util.List _toListNew () {
		return _toListByStatus(PropertyListItemStatus.NEW);
	}

	protected java.util.List _toListDelete () {
//		return _toListByStatus(PropertyListItemStatus.REMOVE, this.itemsToRemove);
		return this.itemsToRemove;
	}

	protected java.util.List _toListModified () {
		return _toListByStatus(PropertyListItemStatus.MODIFIED);
	}

	protected java.util.List _toListByStatus (PropertyListItemStatus status) {
		java.util.List result = null;
		java.util.Collection values = this.itemsByIndex.values();
		result = _toListByStatus(status, values);
		return result;
	}

	protected java.util.List _toListByStatus (PropertyListItemStatus status, java.util.Collection values) {
		java.util.List result = null;
		java.util.Iterator iterator = (values != null ? values.iterator() : null);
		if (iterator != null) {
			result = new java.util.ArrayList();
			while (iterator.hasNext()) {
				PropertyListItem item = (PropertyListItem)iterator.next();
				if (item != null && status.equals(item.getStatus())) {
					result.add(item);
				}
			}
		}
		return result;
	}

	public void persist() {
		try {
			if (this.owner != null && this.owner.getId() != null && this.listPropertyEntity != null) {
				Long ownerId = this.owner.getId();
				String schema = this.owner.loadSchema();
				Connection connection = this.owner.loadConnection();

				final String tableRelation = this.listPropertyEntity.getTableName();//"PAIS_PROVINCIAS";
				final String ownerColumn = this.listPropertyEntity.getOwnerColumnName();//"PAIS_ID";
				final String itemColumn = this.listPropertyEntity.getItemColumnName();//"PROVINCIA_ID";

				if (itemColumn != null) {
					if (this._clear) {
						String sql = "DELETE FROM " + schema + "." + tableRelation + " WHERE " + ownerColumn + " = ? ";
						PreparedStatement ps = connection.prepareStatement(sql);
						ps.setLong(1, ownerId);
						ps.executeUpdate();
						ps.close();
					}
					
					{
						java.util.List list = _toListNew();
									
						if (list != null && list.size() > 0) {
							String sql = "INSERT INTO " + schema + "." + tableRelation + " (" + ownerColumn + ", " + itemColumn + ") VALUES (?, ?)";
							PreparedStatement ps = connection.prepareStatement(sql);
										 
							final int batchSize = 1000;
							int count = 0;
										 
							for (Object listItem: list) {
								PropertyListItem item = (PropertyListItem)listItem;
								AbstractIdEntity value = (AbstractIdEntity)item.getValue();
								Long itemId = value.getId();
							    ps.setLong(1, ownerId);
							    ps.setLong(2, itemId);
							    ps.addBatch();
											     
							    if(++count % batchSize == 0) {
							        ps.executeBatch();
							    }
							}
							ps.executeBatch(); // insert remaining records
							ps.close();
							
							for (Object listItem: list) {
								PropertyListItem item = (PropertyListItem)listItem;
								item.setStatus(PropertyListItemStatus.CHANGELESS);
							}
						}
					}

					if (!this._clear) {
						java.util.List list = _toListDelete();
									
						if (list != null && list.size() > 0) {
							String sql = "DELETE FROM " + schema + "." + tableRelation + " WHERE " + ownerColumn + " = ? AND " + itemColumn + " = ?";
							PreparedStatement ps = connection.prepareStatement(sql);
										 
							final int batchSize = 1000;
							int count = 0;
										 
							for (Object listItem: list) {
								PropertyListItem item = (PropertyListItem)listItem;
								AbstractIdEntity value = (AbstractIdEntity)item.getValue();
								Long itemId = value.getId();
								ps.setLong(1, ownerId);
								ps.setLong(2, itemId);
							    ps.addBatch();
											     
							    if(++count % batchSize == 0) {
							        ps.executeBatch();
							    }
							}
							ps.executeBatch(); // insert remaining records
							ps.close();
							
							if (false) {
								for (Object listItem: list) {
									PropertyListItem item = (PropertyListItem)listItem;
									AbstractIdEntity value = (AbstractIdEntity)item.getValue();
									Integer index = (Integer)this.itemsByValue.remove(value);
									this.itemsByIndex.remove(index);
								}
							}
							else {
								this.itemsToRemove = null;
							}
						}
					}
				}
				else {
					if (this._clear) {
						String sql = "DELETE FROM " + schema + "." + tableRelation + " WHERE " + ownerColumn + " = ? ";
						PreparedStatement ps = connection.prepareStatement(sql);
						ps.setLong(1, ownerId);
						ps.executeUpdate();
						ps.close();
					}
					
					{
						java.util.List list = _toListNew();
									
						if (list != null && list.size() > 0) {
							for (Object listItem: list) {
								PropertyListItem item = (PropertyListItem)listItem;
								AbstractIdEntity value = (AbstractIdEntity)item.getValue();
								((InnerEntity)value).setupOwner(this.owner);
								value.create();
								item.setStatus(PropertyListItemStatus.CHANGELESS);
								value.addPropertyChangeListener(_doGetChangeListener());
								this.itemsByValue.put(value, item.getIndex());
							}
						}
					}

					if (!this._clear) {
						java.util.List list = _toListDelete();
									
						if (list != null && list.size() > 0) {
							for (Object listItem: list) {
								PropertyListItem item = (PropertyListItem)listItem;
								AbstractIdEntity value = (AbstractIdEntity)item.getValue();
								((InnerEntity)value).setupOwner(this.owner);
								value.delete();

								if (false) {
									Integer index = (Integer)this.itemsByValue.remove(value);
									this.itemsByIndex.remove(index);
								}
								
								value.removePropertyChangeListener(this._changeListener);
							}
						}
						this.itemsToRemove = null;
					}

					{
						java.util.List list = _toListModified();
									
						if (list != null && list.size() > 0) {
							for (Object listItem: list) {
								PropertyListItem item = (PropertyListItem)listItem;
								AbstractIdEntity value = (AbstractIdEntity)item.getValue();
								((InnerEntity)value).setupOwner(this.owner);
								value.update();
								item.setStatus(PropertyListItemStatus.CHANGELESS);
								this.itemsByValue.put(value, item.getIndex());
							}
						}
					}
				}
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	
//	public String toString () {
//		return toText();
//	}
//	
//	protected String text;
//	
//	protected String toText (Object value) {
//		String result = null;
//		result = CvtrUtils.getInstance().toText(value);
////		result = (value != null ? value.toString() : null);
//		result = (result == null ? "(null)" : result);
//		return result;
//	}
//	
//	@Override
//	public String toText() {
//		if (this.text == null && this.persistList != null && this.persistList.size() > 0) {
//			String result = null;
//	
//			String propertyName = toText(this.propertyName);
//			
//				String changes = toText(false, false, true);
//				String deletes = toText(false, true, false);
//				String creates = toText(true, false, false);
//				
//				if (changes.length() > 0 || deletes.length() > 0 || creates.length() > 0 || this.clear) {
//					String strClear = (this.clear ? propertyName + "_clear" : "");
//					String strChanges = (changes != null ? propertyName + "_changes=" + changes : "");
//					String strDeletes = (deletes != null ? propertyName + "_deletes=" + deletes : "");
//					String strCreates = (creates != null ? propertyName + "_creates=" + creates : "");
//					boolean flagChanges = (strChanges != null && strChanges.length() > 0);
//					boolean flagDeletes = (strDeletes != null && strDeletes.length() > 0);
//					boolean flagCreates = (strCreates != null && strCreates.length() > 0);
//					result = "";
//					result += strClear;
//					result += (this.clear && flagChanges ? "\n": "");
//					result += strChanges;
//					result += ((flagChanges || this.clear) && flagDeletes ? "\n": "");
//					result += strDeletes;
//					result += ((flagChanges || flagDeletes || this.clear) && flagCreates ? "\n": "");
//					result += strCreates;
//				}
//				
//			this.text = result;
//		}
//		return this.text;
//	}
	
	protected ChangeSupport _changeSupport;
	
	protected ChangeSupport _doGetChangeSupport () {
		if (this._changeSupport == null) {
			this._changeSupport = new ChangeSupport(this.owner);
		}
		return this._changeSupport;
	}
	
	protected void _notifyChange() {
		_doGetChangeSupport().firePropertyChange(this.listPropertyEntity.getPropertyName(), this, this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		_doGetChangeSupport().addPropertyChangeListener(listener);
	}
	
	public boolean containsPropertyChangeListener(PropertyChangeListener listener) {
		return _doGetChangeSupport().containsPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_doGetChangeSupport().removePropertyChangeListener(listener);
	}
	
	public List<PropertyChangeListener> getPropertyChangeListeners() {
		return _doGetChangeSupport().getPropertyChangeListeners();
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		_doGetChangeSupport().addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		_doGetChangeSupport().removePropertyChangeListener(propertyName, listener);
	}
	
	public List<PropertyChangeListener> getPropertyChangeListeners(String propertyName) {
		return _doGetChangeSupport().getPropertyChangeListeners(propertyName);
	}
	
	public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		return _doGetChangeSupport().containsPropertyChangeListener(propertyName, listener);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		_doGetChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public void firePropertyChange(PropertyChangeEvent evt) {
		_doGetChangeSupport().firePropertyChange(evt);
	}
	
	public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
		_doGetChangeSupport().fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}
	
	public boolean hasListeners(String propertyName) {
		return _doGetChangeSupport().hasListeners(propertyName);
	}
	
	public PropertyChangeListener[] getPropertyChangeListenersArray() {
		return _doGetChangeSupport().getPropertyChangeListenersArray();
	}

	public PropertyChangeListener[] getPropertyChangeListenersArray(String propertyName) {
		return _doGetChangeSupport().getPropertyChangeListenersArray(propertyName);
	}

	public boolean hasChanges() {
		boolean result = false;
		java.util.List list = null;

		result = (this._clear);
		
		list = (result ? null : _toListModified());
		result = (result ? result : (list != null && list.size() > 0));

		list = (result ? null : _toListNew());
		result = (result ? result : (list != null && list.size() > 0));

		list = (result ? null : _toListDelete());
		result = (result ? result : (list != null && list.size() > 0));

		return result;
	}

}
