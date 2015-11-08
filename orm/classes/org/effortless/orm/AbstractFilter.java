package org.effortless.orm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;




import org.effortless.core.EnumString;
//import org.apache.commons.lang.ClassUtils;
//import org.effortless.core.GlobalContext;
//import org.effortless.core.ModelException;
import org.effortless.core.ObjectUtils;
import org.effortless.core.UnusualException;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.definition.ListPropertyEntity;
import org.effortless.orm.impl.ChangeRegistry;
import org.effortless.orm.impl.SqlMapper;
//import org.effortless.orm.util.BeanMapLoadOnDemand;
import org.effortless.orm.util.FileHashes;
import org.effortless.orm.AbstractIdEntity;
import org.effortless.orm.DbManager;
import org.effortless.orm.AbstractFilter;
//import org.effortless.security.Resource;
//import org.effortless.security.SecuritySystem;
import org.zkoss.zk.ui.Sessions;

public abstract class AbstractFilter extends AbstractList implements Filter, PropertyChangeListener {

	protected AbstractFilter () {
		super();
		initiate();
	}

	public AbstractFilter (String table, DbManager db, String selectClausule, Class<?>... selectTypes) {
		this();
		_init(table, db, selectClausule, selectTypes);
	}
	
	protected void _init (String table, DbManager db, String selectClausule, Class<?>... selectTypes) {
		this._table = table;
		this._db = db;
		setupSelectClausule(selectClausule, selectTypes);
	}
	
	protected void setupSelectClausule (String newValue, Class<?>... selectTypes) {
		this._selectClausule = newValue;
		this._selectTypes = selectTypes;
	}

	protected void initiate () {
		this._table = null;
		this._db = null;
		this._size = -1;
		this._conditions = new java.util.ArrayList<String>();
		this._joins = new java.util.ArrayList<String>();
		this._params = new java.util.ArrayList<Object>();
//		this._types = new java.util.ArrayList<Integer>();
		this._pageIndex = 0;
		this._pageSize = 25;
		this._selectClausule = null;
		this._cr = new ChangeRegistry(this);
		this._count = null;
	}
	
	protected Boolean _count;
	
	protected DbManager _loadDb () {
		if (this._db == null) {
//			this._db = (DbManager)Sessions.getCurrent().getAttribute(GlobalContext.DBA);
		}
//		this._db = (this._db != null ? this._db : MySession.getDb());
		return this._db;
	}
	
	protected String _table;
	protected DbManager _db;

	protected int _size;
	protected java.util.List<String> _conditions;
	protected java.util.List<String> _joins;
	protected java.util.List<Object> _params;
//	protected java.util.List<Integer> _types;
	protected Integer _pageIndex;
	protected Integer _pageSize;
	protected PreparedStatement _ps;
	protected String _selectClausule;
	protected Class<?>[] _selectTypes;

	
	
	
	
	
	
	
	
	
	
	

	public Integer getPageIndex () {
		return this._pageIndex;
	}
	
	public Filter setPageIndex (Integer newValue) {
		_setProperty("pageIndex", this._pageIndex, this._pageIndex = newValue);
		return this;
	}
	
	public Integer getPageSize () {
		return this._pageSize;
	}
	
	public Filter setPageSize (Integer newValue) {
		_setProperty("pageSize", this._pageSize, this._pageSize = newValue);
		return this;
	}
	
	public Boolean getPaginated() {
		return Boolean.valueOf(_checkPaginated());
	}
	
	protected boolean _checkPaginated () {
		return !(this._pageIndex == null || this._pageSize == null || this._pageIndex.intValue() < 0 || this._pageSize.intValue() < 0);
	}
	
	public Filter setPaginated (Boolean newValue) {
//		_setProperty("paginated", this._pageIndex, this._pageIndex = newValue);
		
		boolean newPaginated = (newValue != null && newValue.booleanValue());
		boolean oldPaginated = _checkPaginated();
		if (newPaginated != oldPaginated) {
			if (newPaginated) {
				setPageIndex(Integer.valueOf(0));
			}
			else {
				setPageIndex(null);
			}
		}
		return this;
	}
	
	protected String _buildQuery (boolean count) {
		String result = null;
		_adaptQuery(count);
		result = "";
		result += "SELECT ";
		result += (count ? "COUNT(*)" : _getClausuleSelect());
		String fullTableName = _loadDb().applyCurrentSchema(this._table);
		result += " FROM " + fullTableName + " o";
		result += _getClausuleJoins();
		String conditions = _getClausuleWhere();
		result += (conditions.length() > 0 ? " WHERE " + conditions : "");
		if (!count) {
			String orderBy = _getOrderBy();
			result += (orderBy.length() > 0 ? " ORDER BY " + orderBy : "");
		}
		if (!count && _checkPaginated()) {
			int offset = this._pageIndex * this._pageSize;
			result += " LIMIT " + this._pageSize + " OFFSET " + offset;
		}
		return result;
	}
	
	protected void _adaptQuery(boolean count) {
	}

	protected String _getClausuleJoins() {
		String result = "";
		int length = (this._joins != null ? this._joins.size() : 0);
		for (int i = 0; i < length; i++) {
			String join = this._joins.get(i);
			result += (join.length() > 0 ? " " : "") + join;
		}
		return result;
	}

	protected String _getOrderBy() {
		String result = "";
		if (this._orderBy != null) {
			String[] array = this._orderBy.split(",");
			int length = (array != null ? array.length : 0);
			for (int i = 0; i < length; i++) {
				String item = array[i].trim();
				String[] parts = item.split(" ");
				String property = (parts.length > 0 ? parts[0].trim() : "");
				String orderItem = (parts.length > 1 ? " " + parts[1].trim() : "");
				
				String column = _decodeProperty(property, null);
				
//				result += (result.length() > 0 ? ", " : "") + "o." + item;
				result += (result.length() > 0 ? ", " : "") + column + orderItem;
			}
		}
		return result;
	}

	protected String reverseOrderBy(String orderBy) {
		String result = null;
		if (orderBy != null) {
			result = "";
			String[] parts = orderBy.split(",");
			int length = (parts != null ? parts.length : 0);
			for (int i = 0; i < length; i++) {
				String part = parts[i];
				String[] propertyOrder = part.split(" ");
				int lengthPropertyOrder = (propertyOrder != null ? propertyOrder.length : 0);
				String name = (lengthPropertyOrder > 0 ? propertyOrder[0] : null);
				String order = (lengthPropertyOrder > 1 ? propertyOrder[1] : null);
				order = (order != null ? order.trim().toUpperCase() : "");
				order = (order.length() > 0 ? order : "ASC");
				order = ("ASC".equals(order) ? "DESC" : "ASC");
				result += (result.length() > 0 ? ", " : "") + name + " " + order;
			}
		}
		return result;
	}	
	
	protected String _getClausuleWhere () {
		String result = "";
		int length = (this._conditions != null ? this._conditions.size() : 0);
		for (int i = 0; i < length; i++) {
			String cond = this._conditions.get(i);
			result += (result.length() > 0 && cond.length() > 0 ? " AND " : "") + cond;
		}
		return result;
	}
		
	protected String _getClausuleSelect() {
		String result = null;
		result = this._selectClausule;
		if (this._selectClausule != null) {
			result = "";
			String[] array = this._selectClausule.split(",");
			int length = (array != null ? array.length : 0);
			for (int i = 0; i < length; i++) {
				result += (result.length() > 0 ? ", " : "") + "o." + array[i].trim();
			}
		}
		result = (result != null ? result : "o.*");
		
		return result;
	}

	protected void _closePs () throws SQLException {
		if (this._ps != null) {
			if (!this._ps.isClosed()) {
				this._ps.close();
			}
			this._ps = null;
		}
	}
	
	protected void _init (boolean count) throws SQLException {
		_closePs();
		if (this._count != null && this._count.booleanValue() != count) {
			this._doResetQuery();
		}
		this._count = Boolean.valueOf(count);
		String query = _buildQuery(count);
		System.out.println("[DEBUG] SQL = " + query);
		this._ps = _loadDb().getConnection().prepareStatement(query);
		
		if (this._checkPaginated()) {
			this._ps.setFetchSize(this._pageSize);
			this._ps.setMaxRows(this._pageSize);
		}

		this._ps.setFetchDirection(ResultSet.FETCH_FORWARD);
		
		for (int i = 0; i < this._params.size(); i++) {
//			this._ps.setObject(i + 1, this._params.get(i), this._types.get(i).intValue());
			Object param = this._params.get(i);
			this._ps.setObject(i + 1, param, SqlMapper.obj2SqlType(param));
		}
	}

	protected String _decodeProperty (String name, Object param) {
		String result = null;
		result = "o." + name;
		return result;
	}
	
	protected Object _decodeParam (String name, Object param) {
		return param;
	}
	
	
	
	
	
	
	
	
	
	
	public Filter lk (String name, Object param) {
		this._conditions.add(_decodeProperty(name, param) + " LIKE ?");
		this._params.add(param);
//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		return this;
	}
	
	public Filter nlk (String name, Object param) {
		this._conditions.add(_decodeProperty(name, param) + " NOT LIKE ?");
		this._params.add(param);
//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		return this;
	}
	
	public Filter eq (String name, Object param) {
		if (_notNull(param)) {
			this._conditions.add(_decodeProperty(name, param) + " = ?");
			this._params.add(_decodeParam(name, param));
//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		}
		return this;
	}

	public Filter eq (String name, java.sql.Time param) {
		if (param != null) {
			String fnHour = "HOUR";
			String fnMinute = "MINUTE";
			
			this._conditions.add("" + fnHour + "(" + _decodeProperty(name, param) + ") = ?");
			this._params.add(_decodeParam(name, org.effortless.core.DateUtils.getHour(param)));

			this._conditions.add("" + fnMinute + "(" + _decodeProperty(name, param) + ") = ?");
			this._params.add(_decodeParam(name, org.effortless.core.DateUtils.getMinute(param)));
		}
		return this;
	}
	
	public Filter ne (String name, java.sql.Time param) {
		if (param != null) {
			not();
			eq(name, param);
			end();
		}
		return this;
	}
	

	public Filter eq (String name, java.sql.Timestamp param) {
		if (param != null) {
			String fnDay = "DAY_OF_MONTH";
			String fnMonth = "MONTH";
			String fnYear = "YEAR";

			String fnHour = "HOUR";
			String fnMinute = "MINUTE";
			
			this._conditions.add("" + fnDay + "(" + _decodeProperty(name, param) + ") = ?");
			this._params.add(_decodeParam(name, org.effortless.core.DateUtils.getDayOfMonth(param)));

			this._conditions.add("" + fnMonth + "(" + _decodeProperty(name, param) + ") = ?");
			this._params.add(_decodeParam(name, org.effortless.core.DateUtils.getMonthOfYear(param) + 1));

			this._conditions.add("" + fnYear + "(" + _decodeProperty(name, param) + ") = ?");
			this._params.add(_decodeParam(name, org.effortless.core.DateUtils.getYear(param)));

			this._conditions.add("" + fnHour + "(" + _decodeProperty(name, param) + ") = ?");
			this._params.add(_decodeParam(name, org.effortless.core.DateUtils.getHour(param)));

			this._conditions.add("" + fnMinute + "(" + _decodeProperty(name, param) + ") = ?");
			this._params.add(_decodeParam(name, org.effortless.core.DateUtils.getMinute(param)));
		}
		return this;
	}
	
	public Filter ne (String name, java.sql.Timestamp param) {
		if (param != null) {
			not();
			eq(name, param);
			end();
		}
		return this;
	}
	
	
	
	
	public Filter eq (String name, AbstractIdEntity param) {
		if (param != null) {
			eq(name, param.getId());
		}
		return this;
	}
	
	public Filter ne (String name, AbstractIdEntity param) {
		if (param != null) {
			ne(name, param.getId());
		}
		return this;
	}
	
	public Filter eq (String name, EnumString param) {
		if (param != null) {
			eq(name, param.name());
		}
		return this;
	}
	
	public Filter eq (String name, Enum param) {
		if (param != null) {
			eq(name, param.name());
		}
		return this;
	}
	
	public Filter ne (String name, EnumString param) {
		if (param != null) {
			ne(name, param.name());
		}
		return this;
	}
	
	public Filter ne (String name, Enum param) {
		if (param != null) {
			ne(name, param.name());
		}
		return this;
	}
	
	public Filter eqOrNull (String name, Object param) {
		if (_notNull(param)) {
			this._conditions.add(_decodeProperty(name, param) + " = ?");
			this._params.add(param);
		}
		else {
			this._conditions.add(_decodeProperty(name, param) + " IS NULL");
		}
		return this;
	}

	public Filter eq (String name, Boolean param) {
		if (param != null) {
			if (param.booleanValue()) {
				String column = _decodeProperty(name, param);
				String cond = "(" + column + " IS NOT NULL OR " + column + " = ?)";
				this._conditions.add(cond);
				this._params.add(Boolean.TRUE);
			}
			else {
				String column = _decodeProperty(name, param);
				String cond = "(" + column + " IS NULL OR " + column + " = ?)";
				this._conditions.add(cond);
				this._params.add(Boolean.FALSE);
			}
		}

		return this;
	}

	public Filter eq (String name, Boolean valueTrue, Boolean valueFalse) {
		boolean _valueTrue = (valueTrue != null && valueTrue.booleanValue());
		boolean _valueFalse = (valueFalse != null && valueFalse.booleanValue());
		if (_valueTrue != _valueFalse) {
			Boolean param = (_valueTrue ? Boolean.TRUE : Boolean.FALSE);
			eq(name, param);
		}
		return this;
	}
	
	public Filter eq (String name, File param) {
		return eq(name, param, null, null, null);
	}
	
	public Filter eq (String name, MediaFileEntity param) {
		Filter result = null;
		File file = (param != null ? param.getContent() : null);
		String hash1 = (param != null ? param.getHash1() : null);
		String hash2 = (param != null ? param.getHash2() : null);
		String hash3 = (param != null ? param.getHash3() : null);
		result = eq(name, file, hash1, hash2, hash3);
		return result;
	}

	public Filter eq (String name, File param, String hash1, String hash2, String hash3) {
		if (param != null) {
			if (hash1 == null && hash2 == null && hash3 == null) {
				String[] hashes = FileHashes.getInstance().tryToHashes(param);
				hash1 = hashes[0];
				hash2 = hashes[1];
				hash3 = hashes[2];
			}
			if (hash1 != null || hash2 != null || hash3 != null) {
				eqOrNull(name + ".hash1", hash1);
				eqOrNull(name + ".hash2", hash2);
				eqOrNull(name + ".hash3", hash3);
				
				Long fileSize = Long.valueOf(param.length());
				eq(name + ".size", fileSize);
			}
		}
		return this;
	}
	
	
	
	
	protected boolean _notNull (Object param) {
		return (param != null && !"".equals(param));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Filter ne (String name, Object param) {
		if (_notNull(param)) {
			this._conditions.add(_decodeProperty(name, param) + " <> ?");
			this._params.add(param);
	//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		}
		return this;
	}
	
	public Filter gt (String name, Object param) {
		if (_notNull(param)) {
			this._conditions.add(_decodeProperty(name, param) + " > ?");
			this._params.add(param);
	//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		}
		return this;
	}
	
	public Filter ge (String name, Object param) {
		if (_notNull(param)) {
			this._conditions.add(_decodeProperty(name, param) + " >= ?");
			this._params.add(param);
	//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		}
		return this;
	}
	
	public Filter lt (String name, Object param) {
		if (_notNull(param)) {
			this._conditions.add(_decodeProperty(name, param) + " < ?");
			this._params.add(param);
			//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		}
		return this;
	}
	
	public Filter le (String name, Object param) {
		if (_notNull(param)) {
			this._conditions.add(_decodeProperty(name, param) + " <= ?");
			this._params.add(param);
	//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		}
		return this;
	}

	protected String _applyUpper (String param) {
		String result = null;
		result = SqlMapper.fnUpper(param);
		return  result;
	}
	
	public Filter ilk (String name, Object param) {
		if (_notNull(param)) {
			try {
				String text = (String)param;
				if (text.length() > 0) {
					text = "%" + text + "%";

					this._conditions.add(_applyUpper(_decodeProperty(name, param)) + " LIKE " + _applyUpper("?"));
					this._params.add(text);
				}
			}
			catch (ClassCastException e) {
				String text = param.toString();
				if (_notNull(param)) {
					this._conditions.add(_applyUpper(_decodeProperty(name, param)) + " LIKE " + _applyUpper("?"));
					this._params.add(text);
				}
			}
		}
		
//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		return this;
	}
	
	public Filter inlk (String name, Object param) {
		if (_notNull(param)) {
			try {
				String text = (String)param;
				if (text.length() > 0) {
					text = "%" + text + "%";

					this._conditions.add(_applyUpper(_decodeProperty(name, text)) + " NOT LIKE " + _applyUpper("?"));
					this._params.add(param);
				}
			}
			catch (ClassCastException e) {
				String text = param.toString();
				this._conditions.add(_applyUpper(_decodeProperty(name, text)) + " NOT LIKE " + _applyUpper("?"));
				this._params.add(param);
			}
		}
		
//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		return this;
	}

	public Filter ieq (String name, Object param) {
		this._conditions.add(_applyUpper(_decodeProperty(name, param)) + " = " + _applyUpper("?"));
		this._params.add(param);
//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		return this;
	}
	
	public Filter ine (String name, Object param) {
		this._conditions.add(_applyUpper(_decodeProperty(name, param)) + " <> " + _applyUpper("?"));
		this._params.add(param);
//		this._types.add(Integer.valueOf(SqlMapper.obj2SqlType(param)));
		return this;
	}

	protected java.util.List _page;
	
	@Override
	public Entity get(int index) {
		Entity result = null;
		try {
			_loadPage(index);
			int pageIdx = (_checkPaginated() ? (index % this._pageSize.intValue()) : index);
			result = (Entity)this._page.get(pageIdx);
			doCallOnRead(result);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	protected void _loadPage(int index) throws SQLException {
			boolean flag = (this._page == null);
			if (!flag && _checkPaginated()) {
				int start = (this._pageIndex * this._pageSize);
				int end = start + this._pageSize;
				flag = !(index >= start && index < end);
				if (flag) {
					this._pageIndex = index / this._pageSize;
				}
			}
			if (flag) {
				if (ModelSecurity.checkSecurityAction(doGetFilterClass(), this, "read")) {
					_init(false);
					ResultSet rs = this._ps.executeQuery();
					this._page = new java.util.ArrayList();
					while (rs.next()) {
						processItem(rs);
					}
	//				int size = this._selectClausule.split(",").length;
	//				while (rs != null && rs.next()) {
	//					Object[] item = new Object[size];
	//					for (int i = 0; i < size; i++) {
	//						Object data = SqlMapper.loadObject(rs, i + 1, this._selectTypes[i]);
	//						item[i] = data;
	//					}
	//					Type decodeItem = decodeItem(item);
	//					this._page.add(decodeItem);
	//				}
					
					java.sql.Statement stm = rs.getStatement();
					rs.close();
					stm.close();
				}
			}
	}

	protected void processItem(ResultSet rs) {
		Object decodeItem = decodeItem(rs);
		this._page.add(decodeItem);
	}
	
	protected abstract Object decodeItem(ResultSet rs);

	@Override
	public int size() {
		if (this._size < 0) {
			try {
				_init(true);
				ResultSet rs = this._ps.executeQuery();
				this._size = (rs.next() ? rs.getInt(1) : 0);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return this._size;
	}

	protected String _orderBy;
	
	public Filter orderBy(String newValue) {
		this._orderBy = newValue;
		return this;
	}

	//left join PAIS_PROVINCIAS p ON p.item_id = o.id AND p.owner_id = ?
	public Filter on(Entity result, String propertyName) {
		AbstractIdEntity _result = (AbstractIdEntity)result;
		if (_result.getId() != null) {
if (false) {
			if (true) {
				String schema = "EMPRESA1_APP1";
				String tableRelation = "PAIS_PROVINCIAS";
				String alias = propertyName;//"p"
				String itemColumn = "PROVINCIA_ID";
				String ownerColumn = "PAIS_ID";
				this._joins.add("INNER JOIN " + schema + "." + tableRelation + " " + alias + " ON " + alias + "." + itemColumn + "=" + "o.ID" + " AND " + alias + "." + ownerColumn + " = " + _result.getId().longValue());
			}
			else {
				String ownerColumn = "PAIS_ID";
				this._conditions.add("o." + ownerColumn + " = ?");
				this._params.add(_result.getId());
			}
}
else {
		try {
			Class<?> type = result.getClass();
			EntityDefinition value = _loadDefinition(type);
//			Field reqField = type.getDeclaredField("__DEFINITION__");
//			reqField.setAccessible(true);
//			EntityDefinition value = (EntityDefinition)reqField.get(null);
			_on(_result, propertyName, (ListPropertyEntity)value.getProperty(propertyName));
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
}
		}
		// TODO Auto-generated method stub
		return this;
	}
	
	protected EntityDefinition _loadDefinition (Class<?> type) {
		EntityDefinition result = null;
		Field reqField = null;
		try {
			reqField = type.getDeclaredField("__DEFINITION__");
		} catch (NoSuchFieldException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		}
		reqField.setAccessible(true);
		try {
			result = (EntityDefinition)reqField.get(null);
		} catch (IllegalArgumentException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		}
		return result;
	}

	protected Filter _on(AbstractIdEntity result, String propertyName, ListPropertyEntity property) {
		if (result.getId() != null) {
			String itemColumnName = property.getItemColumnName();
			
			if (itemColumnName != null) {
				
				String schema = _loadDb().getCurrentSchema();//"EMPRESA1_APP1";
				String tableRelation = property.getTableName();//"PAIS_PROVINCIAS";
				String alias = propertyName;//"p"
				String itemColumn = itemColumnName;//"PROVINCIA_ID";
				String ownerColumn = property.getOwnerColumnName();//"PAIS_ID";
				String join = "INNER JOIN " + schema + "." + tableRelation + " " + alias + " ON " + alias + "." + itemColumn + "=" + "o.ID" + " AND " + alias + "." + ownerColumn + " = " + result.getId().longValue();
				this._joins.add(join);
//				this._joins.add("INNER JOIN " + schema + "." + tableRelation + " " + alias + " ON " + alias + "." + ownerColumn + " = " + result.getId().longValue());
			}
			else {
				String ownerColumn = property.getOwnerColumnName();//"PAIS_ID";
				String condition = "o." + ownerColumn + " = ?";
				this._conditions.add(condition);
				this._params.add(result.getId());
			}
		}
		// TODO Auto-generated method stub
		return this;
	}
	
	public void exclude(AbstractIdEntity o) {
		if (o != null && o.getId() != null) {
			String condition = "o." + "ID" + " = ?";
			this._conditions.add(condition);
			this._params.add(o.getId());
		}
	}
	
//	public Type first () {
//		Type result = null;
//		try {
//			Integer oldPageSize = this._pageSize;
//			this._pageSize = Integer.valueOf(0);
//			result = this.get(0);
//			this._pageSize = oldPageSize;
//		}
//		catch (Throwable t) {
//		}
//		return result;
//	}

	
	public Entity first () {
		return nth(0);
	}
	
	public Entity second () {
		return nth(1);
	}

	public Entity third () {
		return nth(2);
	}

	public Entity antepenultimate () {
		Entity result = null;
		int size = size();
		result = (size > 2 ? nth(size - 3) : null);
		return result;
	}
	
	public Entity penultimate () {
		Entity result = null;
		int size = size();
		result = (size > 1 ? nth(size - 2) : null);
		return result;
	}
	
	public Entity last () {
		Entity result = null;
		int size = size();
		result = (size > 0 ? nth(size - 1) : null);
		return result;
	}
	
	public Entity nth (int index) {
		Entity result = null;
		try {
			result = get(index);
		}
		catch (Throwable t) {
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	public Filter sortBy(String orderBy) {
		this._orderBy = orderBy;
		return this;
	}

	@Override
	public Filter previousPage() {
		Integer pageIndex = getPageIndex();
		if (pageIndex != null) {
			int idx = (pageIndex.intValue());
			if (idx > 0) {
				setPageIndex(Integer.valueOf(idx - 1));
			}
		}
		return this;
	}

	@Override
	public Filter nextPage() {
		Integer pageIndex = getPageIndex();
		if (pageIndex != null) {
			int idx = (pageIndex.intValue());
			Integer _totalPages = getTotalPages();
			int totalPages = (_totalPages != null ? _totalPages.intValue() : 0);
			if (idx < (totalPages - 1)) {
				setPageIndex(Integer.valueOf(idx + 1));
			}
		}
		return this;
	}

	@Override
	public Filter offset(int index) {
		setPageIndex(Integer.valueOf(index));
		return this;
	}
	
	protected int _loadOffset () {
		int result = 0;
		
		Integer _pageIndex = getPageIndex();
		int pageIndex = (_pageIndex != null ? _pageIndex.intValue() : 0);

		Integer _pageSize = getPageSize();
		int pageSize = (_pageSize != null ? _pageSize.intValue() : 0);
		
		result = pageIndex * pageSize;
		result = (result >= 0 ? result : 0);
		
		return result;
	}

	@Override
	public Filter limit(int limit) {
		if (limit >= 0) {
			setPageSize(Integer.valueOf(limit));
		}
		else {
			setPaginated(Boolean.FALSE);
			setPageSize(Integer.MAX_VALUE);
		}
		return this;
	}

	@Override
	public Integer getTotalPages() {
		Integer result = null;
		int total = 0;
		int size = size();
		if (size > 0) {
			Boolean paginated = this.getPaginated();
			if (paginated != null && paginated.booleanValue()) {
				Integer _pageSize = this.getPageSize();
				int pageSize = (_pageSize != null ? _pageSize.intValue() : 0);
				if (pageSize > 0) {
					total = (size / pageSize) + ((size % pageSize) != 0 ? 1 : 0);
				}
			}
			else {
				total = (size > 0 ? 1 : 0);
			}
		}
		result = Integer.valueOf(total);
		return result;
	}

	@Override
	public Filter setTotalPages(Integer newValue) {
		int totalPages = (newValue != null ? newValue.intValue() : 0);
		if (totalPages == 1) {
			this.setPaginated(Boolean.FALSE);
		}
		else {
			int size = (totalPages > 0 ? size() : 0);
			if (size > 0) {
				this.setPaginated(Boolean.TRUE);
				this.setPageIndex(Integer.valueOf(0));
				
				int pageSize = ((int)(size / totalPages));
				pageSize += ((pageSize * totalPages) == size ? 0 : 1);

				this.setPageSize(Integer.valueOf(pageSize));
			}
		}
		
		return this;
	}

	@Override
	public Integer getSize() {
		Integer result = null;
		result = Integer.valueOf(this.size());
		return result;
	}

	protected Boolean deleted;
	
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	@Override
	public Filter setDeleted(Boolean newValue) {
		this._setProperty("deleted", this.deleted, this.deleted = newValue);
		return this;
	}

	
	
	
	
	
	@Override
	public Filter lk(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter nlk(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter ilk(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter inlk(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter eq(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter ieq(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter ne(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter ine(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter gt(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter ge(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter lt(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter le(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter bt(String name, Object lo, Object hi) {
		if (_notNull(lo) && _notNull(hi)) {
			ge(name, lo);
			le(name, hi);
		}
		else if (_notNull(lo) && !_notNull(hi)) {
			ge(name, lo);
		}
		else if (!_notNull(lo) && _notNull(hi)) {
			le(name, hi);
		}
		return this;
	}

	@Override
	public Filter bt(String name, Filter lo, Filter hi) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter nbt(String name, Object lo, Object hi) {
		if (_notNull(lo) && _notNull(hi)) {
			this.or();
			lt(name, lo);
			gt(name, hi);
			this.end();
		}
		else if (_notNull(lo) && !_notNull(hi)) {
			lt(name, lo);
		}
		else if (!_notNull(lo) && _notNull(hi)) {
			gt(name, hi);
		}
		return this;
	}

	@Override
	public Filter nbt(String name, Filter lo, Filter hi) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter isTrue(String name) {
		eq(name, Boolean.TRUE);
		return this;
	}
	
	@Override
	public Filter isFalse(String name) {
		eq(name, Boolean.FALSE);
		return this;
	}

	@Override
	public Filter isZero(String name) {
		eq(name, Integer.valueOf(0));
		return this;
	}

	@Override
	public Filter isNotZero(String name) {
		ne(name, Integer.valueOf(0));
		return this;
	}

	@Override
	public Filter isPositive(String name) {
		gt(name, Integer.valueOf(0));
		return this;
	}

	@Override
	public Filter isNegative(String name) {
		lt(name, Integer.valueOf(0));
		return this;
	}

	@Override
	public Filter in(String name, Object... param) {
		if (param != null && param.length > 0) {
			String column = _decodeProperty(name, param);
			String condParams = "";
			for (Object item : param) {
				condParams += (condParams.length() > 0 ? ", " : "") + "?";
				Object itemParam = null;
				AbstractIdEntity entity = null;
				try { entity = (AbstractIdEntity)item; itemParam = entity.getId(); } catch (ClassCastException e) { itemParam = item; }
				this._params.add(itemParam);
			}
			String cond = column + " IN (" + condParams + ")";
			this._conditions.add(cond);
		}
		return this;
	}

	@Override
	public Filter in(String name, Collection param) {
		if (param != null && param.size() > 0) {
			String column = _decodeProperty(name, param);
			String condParams = "";
			for (Object item : param) {
				condParams += (condParams.length() > 0 ? ", " : "") + "?";
				Object itemParam = null;
				AbstractIdEntity entity = null;
				try { entity = (AbstractIdEntity)item; itemParam = entity.getId(); } catch (ClassCastException e) { itemParam = item; }
				this._params.add(itemParam);
			}
			String cond = column + " IN (" + condParams + ")";
			this._conditions.add(cond);
		}
		return this;
	}

	@Override
	public Filter in(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter nin(String name, Object... param) {
		if (param != null && param.length > 0) {
			String column = _decodeProperty(name, param);
			String condParams = "";
			for (Object item : param) {
				condParams += (condParams.length() > 0 ? ", " : "") + "?";
				Object itemParam = null;
				AbstractIdEntity entity = null;
				try { entity = (AbstractIdEntity)item; itemParam = entity.getId(); } catch (ClassCastException e) { itemParam = item; }
				this._params.add(itemParam);
			}
			String cond = column + " NOT IN (" + condParams + ")";
			this._conditions.add(cond);
		}
		return this;
	}

	@Override
	public Filter nin(String name, Collection param) {
		if (param != null && param.size() > 0) {
			String column = _decodeProperty(name, param);
			String condParams = "";
			for (Object item : param) {
				condParams += (condParams.length() > 0 ? ", " : "") + "?";
				Object itemParam = null;
				AbstractIdEntity entity = null;
				try { entity = (AbstractIdEntity)item; itemParam = entity.getId(); } catch (ClassCastException e) { itemParam = item; }
				this._params.add(itemParam);
			}
			String cond = column + " NOT IN (" + condParams + ")";
			this._conditions.add(cond);
		}
		return this;
	}

	@Override
	public Filter nin(String name, Filter param) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter isEmpty(String name) {
		String column = _decodeProperty(name, null);
		String cond = "(" + column + "IS NULL OR " + _applyLength(column) + " <= 0)";
		this._conditions.add(cond);
		return this;
	}

	protected String _applyLength(String column) {
		return "LENGTH(" + column + ")";
	}

	@Override
	public Filter isNotEmpty(String name) {
		String column = _decodeProperty(name, null);
		String cond = _applyLength(column) + " > 0";
		this._conditions.add(cond);
		return this;
	}

	@Override
	public Filter isNotNull(String name) {
		String column = _decodeProperty(name, null);
		this._conditions.add(column + " IS NOT NULL");
		return this;
	}

	@Override
	public Filter isNull(String name) {
		String column = _decodeProperty(name, null);
		this._conditions.add(column + " IS NULL");
		return this;
	}

	@Override
	public Filter sizeEq(String name, int size) {
		this._conditions.add(_loadCondition_SizeCollection(name, "=", size));
		return this;
	}

	protected String _loadCondition_SizeCollection (String name, String op, int size) {
		String result = null;
		result = "(SELECT COUNT(" + _loadItemId_SizeCollection(name) + ") FROM " + _loadItemTable_SizeCollection(name) + ") " + op + " " + size;
		return result;
	}

	protected String _loadItemId_SizeCollection (String name) {
		String result = null;
		result = "appointment1_.Id_Solicitud";
		return result;
	}
	
	protected String _loadItemTable_SizeCollection (String name) {
		String result = null;
		result = "Appointment appointment1_";
		return result;
	}
	
	@Override
	public Filter sizeEq(String name, Filter size) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter sizeGe(String name, int size) {
		this._conditions.add(_loadCondition_SizeCollection(name, ">=", size));
		return this;
	}

	@Override
	public Filter sizeGe(String name, Filter size) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter sizeGt(String name, int size) {
		this._conditions.add(_loadCondition_SizeCollection(name, ">", size));
		return this;
	}

	@Override
	public Filter sizeGt(String name, Filter size) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter sizeLe(String name, int size) {
		this._conditions.add(_loadCondition_SizeCollection(name, "<=", size));
		return this;
	}

	@Override
	public Filter sizeLe(String name, Filter size) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter sizeLt(String name, int size) {
		this._conditions.add(_loadCondition_SizeCollection(name, "<", size));
		return this;
	}

	@Override
	public Filter sizeLt(String name, Filter size) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Filter sizeNe(String name, int size) {
		this._conditions.add(_loadCondition_SizeCollection(name, "<>", size));
		return this;
	}

	@Override
	public Filter sizeNe(String name, Filter size) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getOrderBy() {
		return this._getOrderBy();
	}

	@Override
	public Filter setOrderBy(String order) {
		if (order != null) {
			String newOrder = getOrderBy();
			newOrder = (newOrder != null ? newOrder : "");
			newOrder += (newOrder.length() > 0 && order.length() > 0 ? "," : "") + order;
			setOrderBy(order);
		}
		return this;
	}

	@Override
	public Filter exclude(Entity o) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void reset() {
		_doResetQuery();
		updateFilter();
	}

	@Override
	public Filter batch(EntityProcess process) {
		if (process != null) {
			MySession.newTransaction();
			for (Object entity : this) {
				if (entity != null) {
					process.run(entity);
				}
			}
			MySession.endTransaction();
		}
		return this;
	}

	@Override
	public Filter persistAll(EntityProcess process) {
		if (process != null) {
			MySession.newTransaction();
			for (Object entity : this) {
				if (entity != null) {
					process.run(entity);
					_persistItem(entity);
				}
			}
			MySession.endTransaction();
		}
		return this;
	}
	
	@Override
	public Filter deleteAll(EntityProcess process) {
		MySession.newTransaction();
		reverse();
		int length = size();
		for (int i = length - 1; i >= 0; i--) {
			Object entity = get(i);
			if (entity != null) {
				Boolean toDelete = (Boolean)process.run(entity);
				if (toDelete != null && toDelete.booleanValue()) {
					_deleteItem(entity);
				}
			}
		}
		reverse();
		MySession.endTransaction();
		return this;
	}

	@Override
	public Filter eraseAll(EntityProcess process) {
		MySession.newTransaction();
		reverse();
		int length = size();
		for (int i = length - 1; i >= 0; i--) {
			Object entity = get(i);
			if (entity != null) {
				Boolean toErase = (Boolean)process.run(entity);
				if (toErase != null) {
					_eraseItem(entity);
				}
			}
		}
		reverse();
		MySession.endTransaction();
		return this;
	}

	@Override
	public List listPage() {
		List result = null;
		Boolean paginated = this.getPaginated();
		if (paginated != null && paginated.booleanValue()) {
			result = new java.util.ArrayList();
			int offset = _loadOffset();
			Integer _pageSize = getPageSize();
			int pageSize = (_pageSize != null ? _pageSize.intValue() : 0);
			
			int size = size();

			int end = Math.min(offset + pageSize, size);

			for (int i = offset; i < end; i++) {
				result.add(get(i));
			}
		}
		else {
			result = this;
		}
		return result;
	}

	@Override
	public boolean exists() {
		return size() > 0;
	}

	@Override
	public Filter negative() {
		return this;
	}

	@Override
	public Filter and() {
		return this;
	}

	@Override
	public Filter or() {
		return this;
	}

	@Override
	public Filter nor() {
		return this;
	}

	@Override
	public Filter nand() {
		return this;
	}

	@Override
	public Filter not() {
		this._conditions.add("NOT (");
		return this;
	}

	@Override
	public Filter end() {
		this._conditions.add(")");
		return this;
	}

	@Override
	public Filter link(Class clazz, String name) {
		Filter result = null;
		
		
		return result;
	}

	protected Number _fnAggregate(String name, String fn) {
		Number result = null;
		
		String column = _decodeProperty(name, null);
		String oldClausule = this._selectClausule;
		this._selectClausule = fn + "(" + column + ")";		
		
//		result = (Number)list.get(0);
		this._selectClausule = oldClausule;
		
		return result;
	}	
	
	@Override
	public Number min(String name) {
		Number result = null;
		result = _fnAggregate(name, "MIN");
		return result;
	}

	@Override
	public Number max(String name) {
		Number result = null;
		result = _fnAggregate(name, "MAX");
		return result;
	}

	@Override
	public Number sum(String name) {
		Number result = null;
		result = _fnAggregate(name, "SUM");
		return result;
	}

	@Override
	public Number avg(String name) {
		Number result = null;
		result = _fnAggregate(name, "AVG");
		return result;
	}

	@Override
	public Number count() {
		return Integer.valueOf(size());
	}

	@Override
	public Filter reverse() {
		String orderBy = this.getOrderBy();
		setOrderBy(reverseOrderBy(orderBy));
		return this;
	}

	
	protected ChangeRegistry _cr;
	
	@Override
	public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
		this._cr.addPropertyChangeListener(name, listener);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this._cr.addPropertyChangeListener(listener);
	}

	
	
	
	
	
	
	
	
	
	protected Class<?> doGetFilterClass () {
		Class<?> result = null;
//		org.effortless.ann.FilterInfo filterInfo = this.getClass().getAnnotation(org.effortless.ann.FilterInfo.class);
//		if (filterInfo != null) {
			String filterTarget = null;//filterInfo.target();
			result = org.effortless.core.ClassUtils.loadClassRE(filterTarget);
//		}
//		Object superClass = getClass().getGenericSuperclass();
//		ParameterizedType parameterizedType = null;
//		parameterizedType = (ParameterizedType)superClass;
//		try {
//			parameterizedType = (ParameterizedType)superClass;
//		}
//		catch (ClassCastException e) {
//			try {
//				Class<?> clazz = (Class)superClass;
//				superClass = clazz.getGenericSuperclass();
//				parameterizedType = (ParameterizedType)superClass;
//			}
//			catch (ClassCastException e1) {
//				
//			}
//			
//		}
//		result = (parameterizedType != null ? (Class) parameterizedType.getActualTypeArguments()[0] : null);
		return result;
	}
	
	protected Object _setProperty (String propertyName, Object oldValue, Object newValue) {
		if (!ObjectUtils.equals(oldValue, newValue)) {
			this._resetQuery(propertyName, oldValue, newValue);
			_doChangeProperty(propertyName, oldValue, newValue);
			firePropertyChange(propertyName, oldValue, newValue);
		}
		return oldValue;
	}
	
	
//	protected boolean _setProperty (String propertyName, Object oldValue, Object newValue) {
//		boolean result = false;
//		if (!ObjectUtils.equals(oldValue, newValue)) {
//			this.reset();
//			_doChangeProperty(propertyName, oldValue, newValue);
//			this._changeRegistry.add(propertyName, oldValue, newValue);
//			this._changeRegistry.firePropertyChange(propertyName, oldValue, newValue);
//			result = true;
//		}
//		return result;
//	}

	

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		this._cr.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void _doChangeProperty (String propertyName, Object oldValue, Object newValue) {
	}
	
	protected void _resetQuery(String propertyName, Object oldValue, Object newValue) {
		_doResetQuery();
	}

	
	protected void _doResetQuery() {
		this._size = -1;
		this._page = null;
		this._pageIndex = Integer.valueOf(0);
		this._conditions = new java.util.ArrayList<String>();
		this._joins = new java.util.ArrayList<String>();
		this._params = new java.util.ArrayList<Object>();		
	}
	
	
//	protected boolean _equals (Object value1, Object value2) {
//		return ObjectUtils.equals(value1, value2);
//	}
	
//	protected Map<String, Object> _map;
//
//	public Map<String, Object> toMap () {
//		if (this._map == null) {
////			this._map = new BeanMap(this);
//			this._map = new BeanMapLoadOnDemand(this);
//		}
//		return this._map;
//	}
	
//	protected Boolean deleted;
//	
//	protected void initiateDeleted () {
//		this.deleted = Boolean.FALSE;
//	}
//	
//	public Boolean getDeleted() {
//		return this.deleted;
//	}
//
//	public void setDeleted(Boolean newValue) {
//		this._setProperty("deleted", this.deleted, this.deleted = newValue);
////		Boolean oldValue = this.deleted;
////		if (!ObjectUtils.equals(oldValue, newValue)) {
////			this.deleted = newValue;
////			updateFilter();
////			this._fcs.firePropertyChange("deleted", oldValue, newValue);
////		}
//	}

	
	
	
	
	
	
	
	
//	protected Boolean paginated;
//	
//	protected void initiatePaginated () {
//		this.paginated = Boolean.TRUE;
//	}
//	
//	public Boolean getPaginated() {
//		return this.paginated;
//	}
//
//	public void setPaginated(Boolean newValue) {
//		this._setProperty("paginated", this.paginated, this.paginated = newValue);
////		Boolean oldValue = this.paginated;
////		if (!ObjectUtils.equals(oldValue, newValue)) {
////			this.paginated = newValue;
////			updateFilter();
////			this._fcs.firePropertyChange("paginated", oldValue, newValue);
////		}
//	}
//
//	protected Integer pageIndex;
//
//	protected void initiatePageIndex () {
//		this.pageIndex = Integer.valueOf(0);
//	}
//	
//	public Integer getPageIndex() {
//		return this.pageIndex;
//	}
//
//	public void setPageIndex(Integer newValue) {
//		this._setProperty("pageIndex", this.pageIndex, this.pageIndex = newValue);
////		Integer oldValue = this.pageIndex;
////		if (!ObjectUtils.equals(oldValue, newValue)) {
////			this.pageIndex = newValue;
////			updateFilter();
////			this._fcs.firePropertyChange("pageIndex", oldValue, newValue);
////		}
//	}
//	
//	protected Integer pageSize;
//	
//	protected void initiatePageSize () {
//		this.pageSize = loadDefaultPageSize();
////		this.pageSize = Integer.valueOf(25);
////		this.pageSize = Integer.valueOf(1);
//	}
//	
//	public static Integer loadDefaultPageSize () {
//		Integer result = null;
//		try { result = GlobalContext.get(GlobalContext.DEFAULT_PAGE_SIZE, Integer.class); }	catch (Throwable t) {}
//		result = (result != null && result.intValue() > 0 ? result : Integer.valueOf(25));
//		return result;
//	}
//	
//	public Integer getPageSize() {
//		return this.pageSize;
//	}
//
//	public void setPageSize(Integer newValue) {
//		this._setProperty("pageSize", this.pageSize, this.pageSize = newValue);
////		Integer oldValue = this.pageSize;
////		if (!ObjectUtils.equals(oldValue, newValue)) {
////			this.pageSize = newValue;
////			updateFilter();
////			this._fcs.firePropertyChange("pageSize", oldValue, newValue);
////		}
//	}
//
//	public Integer getTotalPages () {
//		Integer result = null;
//		int numElements = size();
//		int pageSize = (this.pageSize != null ? this.pageSize.intValue() : 0);
//		pageSize = (pageSize > 0 ? pageSize : numElements);
//		int total = (pageSize > 0 ? (numElements / pageSize) + ((numElements % pageSize > 0) ? 1 : 0) : 0);
//		result = Integer.valueOf(total);
//		return result;
//	}
//	
//	public void setTotalPages (Integer newValue) {
//		int total = (newValue != null ? newValue.intValue() : 0);
//		if (total > 0) {
//			int numElements = size();
//			int pageSize = (numElements / total) + ((numElements % total > 0) ? 1 : 0);
//			setPageSize(Integer.valueOf(pageSize));
//		}
//	}
//	
//	protected String orderBy;
//	
//	protected void initiateOrderBy () {
//		this.orderBy = null;
//	}
//	
//	public String getOrderBy() {
//		return this.orderBy;
//	}
//
//	public void setOrderBy(String newValue) {
//		this._setProperty("orderBy", this.orderBy, this.orderBy = newValue);
////		String oldValue = this.orderBy;
////		if (!ObjectUtils.equals(oldValue, newValue)) {
////			this.orderBy = newValue;
////			updateFilter();
////			this._fcs.firePropertyChange("orderBy", oldValue, newValue);
////		}
//	}
//
	protected void updateFilter() {
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public boolean addAll(Collection<? extends Type> c) {
//		boolean result = false;
//		if (c != null) {
//			Iterator<? extends Type> iterator = c.iterator();
//			if (iterator != null) {
//				result = true;
//				while (iterator.hasNext()) {
//					 Type item = iterator.next();
//					 if (!add(item)) {
//						 result = false;
//					 }
//				}
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public boolean addAll(int index, Collection<? extends Type> c) {
//		boolean result = false;
//		if (c != null) {
//			Iterator<? extends Type> iterator = c.iterator();
//			if (iterator != null) {
//				result = true;
//				while (iterator.hasNext()) {
//					Type item = iterator.next();
//					add(index, item);
//				}
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public boolean containsAll(Collection<?> c) {
//		boolean result = false;
//		if (c != null) {
//			Iterator<?> iterator = c.iterator();
//			if (iterator != null) {
//				while (iterator.hasNext()) {
//					result = true;
//					Object item = iterator.next();
//					if (!contains(item)) {
//						result = false;
//						break;
//					}
//				}
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public int indexOf(Object o) {
//		int result = -1;
//		if (o != null) {
//			Iterator iterator = iterator();
//			if (iterator != null) {
//				while (iterator.hasNext()) {
//					result += 1;
//					Type item = iterator.next();
//					if (o.equals(item)) {
//						break;
//					}
//				}
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public boolean isEmpty() {
////		return false;
//		return size() <= 0;
//	}
//	
//	public Integer getSize () {
//		return Integer.valueOf(size());
//	}
//
//	@Override
//	public int lastIndexOf(Object o) {
//		int result = -1;
//		if (o != null) {
//			Iterator iterator = iterator();
//			if (iterator != null) {
//				int index = -1;
//				while (iterator.hasNext()) {
//					result += 1;
//					Type item = iterator.next();
//					if (o.equals(item)) {
//						index = result;
//					}
//				}
//				result = index;
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public ListIterator listIterator() {
//		return listIterator(0);
//	}
//
//	@Override
//	public Type remove(int index) {
//		Type result = null;
//		result = get(index);
//		if (result != null) {
//			remove(result);
//		}
//		return result;
//	}
//
//	@Override
//	public boolean removeAll(Collection<?> c) {
//		boolean result = false;
//		if (c != null) {
//			Iterator<?> iterator = c.iterator();
//			if (iterator != null) {
//				result = true;
//				while (iterator.hasNext()) {
//					Object item = iterator.next();
//					if (!remove(item)) {
//						result = false;
//					}
//				}
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public boolean retainAll(Collection<?> c) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Object[] toArray() {
//		Object[] result = null;
//		List<Object> list = new ArrayList<Object>();
//		Iterator iterator = iterator();
//		if (iterator != null) {
//			while (iterator.hasNext()) {
//				Type item = iterator.next();
//				list.add(item);
//			}
//		}
//		result = list.toArray();
//		return result;
//	}
//
//	@Override
//	public <T> T[] toArray(T[] a) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//	
//	
//	
//
//	@Override
//	public Type set(int index, Type element) {
//		throw new org.effortless.core.ModelException(new UnsupportedOperationException());
//	}
//
//	@Override
//	public List subList(int fromIndex, int toIndex) {
//		List result = null;
//		if (fromIndex >= 0 && toIndex >= 0 && toIndex > fromIndex) {
//			result = new ArrayList();
//			for (int i = fromIndex; i < toIndex; i++) {
//				Type item = get(i);
//				result.add(item);
//			}
//		}
//		return result;
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
//	public Filter orderAsc (String name) {
//		if (name != null) {
//			this.criteria.addOrder(Order.asc(name));
//		}
//		return this;
//	}
//	
//	public Filter orderDesc (String name) {
//		if (name != null) {
//			this.criteria.addOrder(Order.desc(name));
//		}
//		return this;
//	}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void add(int index, Object element) {
		throw new UnusualException(new UnsupportedOperationException());
	}

	@Override
	public boolean contains(Object o) {
		boolean result = false;
		result = (this.persistList != null ? this.persistList.contains(0) : result);
		if (!result) {
			try {
				AbstractIdEntity entity = (AbstractIdEntity)o;
				this.reset();
				List list = this.eq("id", entity.getId());
				result = (list != null && list.size() > 0);
			}
			catch (ClassCastException e) {
			}
		}
		return result;
	}

	@Override
	public Iterator iterator() {
		return new FilterIterator(this);
	}

	@Override
	public ListIterator listIterator(int index) {
		return new FilterIterator(this, index);
	}

	@Override
	public Object set(int index, Object element) {
		throw new UnusualException(new UnsupportedOperationException());
	}

	
	protected class FilterIterator extends Object implements ListIterator {

		public FilterIterator (AbstractFilter owner) {
			this(owner, 0);
		}
		
		public FilterIterator (AbstractFilter owner, int offset) {
			super();
			this._owner = owner;
			this._offset = offset;
			this._last = null;
		}
		
		protected AbstractFilter _owner;
		protected Object _last;
		protected int _offset;
		
		@Override
		public boolean hasNext() {
			boolean result = false;
			try {
				this._last = this._owner.get(this._offset);
				result = (this._last != null);
				this._offset += 1;
			}
			catch (Throwable t) {
			}
			return result;
		}

		@Override
		public Object next() {
			Object result = null;
			result = this._last;
			this._last = null;
			return result;
		}

		@Override
		public void remove() {
			if (this._last != null) {
				this._owner.remove(this._last);
			}
		}

		@Override
		public void add(Object o) {
			this._owner.add(o);
		}

		@Override
		public boolean hasPrevious() {
			boolean result = false;
			result = (this._offset > 0);
			return result;
		}

		@Override
		public int nextIndex() {
			int result = 0;
			result = this._offset + 1;
			return result;
		}

		@Override
		public Object previous() {
			Object result = null;
			result = (this._offset > 0 ? this._owner.get(this._offset - 1) : null);
			return result;
		}

		@Override
		public int previousIndex() {
			int result = 0;
			result = (this._offset > 0 ? this._offset - 1 : 0);
			return result;
		}

		@Override
		public void set(Object o) {
			this._owner.set(this._offset, o);
		}
		
	}
	
	
	
	public void persist () {
		persist(true);
	}
	
	public boolean persist (boolean validate) {
		boolean result = true;
		if (this.persistList != null) {
			Iterator iterator = this.persistList.iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					Object item = iterator.next();
					if (item != null) {
						_persistItem(item, validate);
					}
				}
			}
			this.persistList = null;
		}
		return result;
	}
	
	
	protected List persistList;
	
	public List toPersist () {
		return this.persistList;
	}
	
	public void clearPersist () {
		this.persistList = null;
	}
	
	protected boolean clear;
	
	public void clear () {
		this.clear = true;
		this.persistList = null;
	}
	
	@Override
	public boolean add(Object o) {
		boolean result = false;
		if (o != null && !contains(o)) {
			applyListener(o);
			this.persistList = (this.persistList != null ? this.persistList : new ArrayList());
			this.persistList.add(o);
		}
		return result;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean result = false;
		if (o != null) {
			if (true) {
				AbstractIdEntity toDelete = null;
				try {
					toDelete = (AbstractIdEntity)o;
				}
				catch (ClassCastException e) {
				}
				if (toDelete != null ) {
					toDelete.setDeleted(Boolean.TRUE);
				}
			}
//			result = super.remove(o);
			result = true;
			if (true) {
				this.persistList = (this.persistList != null ? this.persistList : new ArrayList());
				if (!this.persistList.contains(o)) {
					this.persistList.add((Object)o);
				}
			}
		}
		return result;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null) {
			Object evtSource = (Object)event.getSource();
			if (evtSource != null) {
				if (this.persistList == null || !this.persistList.contains(evtSource)) {
					this.persistList = (this.persistList != null ? this.persistList : new ArrayList());
					this.persistList.add(evtSource);
				}
			}
		}
	}

	
	
	


	
	
	

	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
//	protected void setupDeleted () {
//		if (this.deleted != null) {
//			Class<?> clazz = doGetFilterClass();
//			if (MarkDeleted.class.isAssignableFrom(clazz)) {
//				String pName = "deleted";
//				Junction andOr = null;
//				if (this.deleted.booleanValue()) {
//					andOr = Restrictions.conjunction();
//					andOr.add(Restrictions.isNotNull(pName));
//				}
//				else {
//					andOr = Restrictions.disjunction();
//					andOr.add(Restrictions.isNull(pName));
//				}
//				andOr.add(Restrictions.eq(pName, this.deleted));
////				add(andOr);
//				this.criteria.add(andOr);
//			}
//		}
//	}
	
	protected void applyListener (Object o) {
	}

	protected void doCallOnRead(Object item) {
	}

	protected void _persistItem (Object item, boolean validate) {
	}

	protected void _persistItem (Object item) {
	}

	protected void _deleteItem (Object item) {
	}

	protected void _eraseItem (Object item) {
	}

	@Override
	public Filter like(String name, Object param) {
		return lk(name, param);
	}

	@Override
	public Filter like(String name, Filter param) {
		return lk(name, param);
	}

	@Override
	public Filter notLike(String name, Object param) {
		return nlk(name, param);
	}

	@Override
	public Filter notLike(String name, Filter param) {
		return nlk(name, param);
	}

	@Override
	public Filter ilike(String name, Object param) {
		return ilk(name, param);
	}

	@Override
	public Filter ilike(String name, Filter param) {
		return ilk(name, param);
	}

	@Override
	public Filter inotLike(String name, Object param) {
		return inlk(name, param);
	}

	@Override
	public Filter inotLike(String name, Filter param) {
		return inlk(name, param);
	}

	@Override
	public Filter between(String name, Object lo, Object hi) {
		return bt(name, lo, hi);
	}

	@Override
	public Filter between(String name, java.util.Date date) {
		return bt(name, date);
	}

	@Override
	public Filter notBetween(String name, java.util.Date date) {
		return nbt(name, date);
	}

	@Override
	public Filter bt(String name, java.util.Date date) {
		Filter result = this;
		if (date != null) {
			java.util.Date first = org.effortless.core.DateUtils.firstTime(date);
			java.util.Date last = org.effortless.core.DateUtils.lastTime(date);
			result = bt(name, first, last);
		}
		return result;
	}

	@Override
	public Filter nbt(String name, java.util.Date date) {
		Filter result = this;
		if (date != null) {
			java.util.Date first = org.effortless.core.DateUtils.firstTime(date);
			java.util.Date last = org.effortless.core.DateUtils.lastTime(date);
			result = nbt(name, first, last);
		}
		return result;
	}

	
	@Override
	public Filter between(String name, Double value) {
		return bt(name, value);
	}

	@Override
	public Filter notBetween(String name, Double value) {
		return nbt(name, value);
	}

	@Override
	public Filter bt(String name, Double value) {
		Filter result = this;
		if (value != null) {
			Double first = Double.valueOf(org.effortless.core.NumberUtils.roundDown(value, 4));
			Double last = Double.valueOf(org.effortless.core.NumberUtils.roundUp(value, 4));
			result = bt(name, first, last);
		}
		return result;
	}

	@Override
	public Filter nbt(String name, Double value) {
		Filter result = this;
		if (value != null) {
			Double first = Double.valueOf(org.effortless.core.NumberUtils.roundDown(value, 4));
			Double last = Double.valueOf(org.effortless.core.NumberUtils.roundUp(value, 4));
			result = nbt(name, first, last);
		}
		return result;
	}

	@Override
	public Filter between(String name, Filter lo, Filter hi) {
		return bt(name, lo, hi);
	}

	@Override
	public Filter notBetween(String name, Object lo, Object hi) {
		return nbt(name, lo, hi);
	}

	@Override
	public Filter notBetween(String name, Filter lo, Filter hi) {
		return nbt(name, lo, hi);
	}

	@Override
	public Filter notIn(String name, Object... param) {
		return nin(name, param);
	}

	@Override
	public Filter notIn(String name, Collection param) {
		return nin(name, param);
	}

	@Override
	public Filter notIn(String name, Filter param) {
		return nin(name, param);
	}

	public Object findUnique () {
		Object result = null;
		
		Integer pageIndex = getPageIndex();
		Integer pageSize = getPageSize();
		Boolean paginated = getPaginated();
		
		setPageIndex(Integer.valueOf(0));
		setPageSize(Integer.valueOf(1));
		setPaginated(Boolean.TRUE);
		try {
			result = get(0);
		}
		catch (Throwable t) {
		}
		finally {
			setPageIndex(pageIndex);
			setPageSize(pageSize);
			setPaginated(paginated);
		}
		
		return result;
	}
	
}
