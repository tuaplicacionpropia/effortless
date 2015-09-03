package org.effortless.orm;

//import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import org.effortless.core.UnusualException;
import org.effortless.orm.DbManager;
import org.effortless.orm.AbstractFilter;
import org.effortless.orm.NativeFilter;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.definition.ListPropertyEntity;
import org.effortless.orm.definition.PropertyEntity;
import org.effortless.orm.impl.ColumnEncoder;
import org.effortless.orm.impl.DbStatement;
import org.effortless.orm.impl.SqlMapper;
import org.effortless.orm.impl.TableIndex;

//import com.esotericsoftware.reflectasm.MethodAccess;

//import java.sql.*;

/*
http://www.tutorialspoint.com/jdbc/index.htm
 * 
 */
public abstract class DbManager extends Object {

	public DbManager () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateConnection();
		initiateDbUrl();
		initiateDbUser();
		initiateDbPass();
		initiateCurrentSchema();
//		initiateMetaDb();
	}
	
//	protected DbManager metaDb;
//	
//	protected void initiateMetaDb () {
//		this.metaDb = null;
//	}
//	
//	public DbManager getMetaDb () {
//		return this.metaDb;
//	}
//	
//	public void setMetaDb (DbManager newValue) {
//		this.metaDb = newValue;
//	}
	
	protected void initiateConnection () {
		this.conn = null;
	}
	
//	protected Connection openConnection () throws ClassNotFoundException, SQLException {
//		if (this.conn == null) {
//			loadDriver();
//			loadConnection();
//		}
//		return this.conn;
//	}
//	
	public void login () {
		if (this.conn == null) {
			loadDriver();
			loadConnection();
		}
	}
	
	protected void _throwException (Exception e) {
		throw new UnusualException(e);
	}
	
	protected void loadDriver () {
        try {
			Class.forName(loadDriverClass());
		} catch (ClassNotFoundException e) {
			_throwException(e);
		}
	}

	protected abstract String loadDriverClass ();

	protected Connection conn;

	public Connection getConnection () {
		return this.conn;
	}
	
	protected void loadConnection () {
		String dbUrl = getDbUrl();
		String dbUser = getDbUser();
		String dbPass = getDbPass();
        try {
			this.conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
		} catch (SQLException e) {
			_throwException(e);
		}
    }
	
	protected void closeConnection () {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				_throwException(e);
			}
			this.conn = null;
		}
	}
	
	public void close () {
		closeConnection();
	}
	
	protected String dbUrl;
	
	protected void initiateDbUrl () {
		this.dbUrl = null;
	}
	
	public String getDbUrl () {
		return this.dbUrl;
	}
	
	public void setDbUrl (String newValue) {
		this.dbUrl = newValue;
	}
	
	protected String dbUser;
	
	protected void initiateDbUser () {
		this.dbUser = null;
	}
	
	public String getDbUser () {
		return this.dbUser;
	}
	
	public void setDbUser (String newValue) {
		this.dbUser = newValue;
	}
	
	protected String dbPass;
	
	protected void initiateDbPass () {
		this.dbPass = null;
	}
	
	public String getDbPass () {
		return this.dbPass;
	}
	
	public void setDbPass (String newValue) {
		this.dbPass = newValue;
	}
	
	public void backup () {
		
	}
	
	public void restore () {
		
	}
	
	protected String currentSchema;
	
	protected void initiateCurrentSchema () {
		this.currentSchema = null;
	}
	
	public String getCurrentSchema () {
		return this.currentSchema;
	}
	
	public void setCurrentSchema (String newValue) {
		newValue = (newValue != null ? newValue.toUpperCase() : newValue);
		this.currentSchema = newValue;
	}
	
	public String applyCurrentSchema(String name) {
		String result = null;
		String schema = (this.currentSchema != null ? this.currentSchema.trim() : "");
		if (schema.length() > 0 && name != null && name.indexOf(".") < 0) {
			result = schema + "." + name;
		}
		else {
			result = name;
		}
		return result;
	}

	//CREATE USER IF NOT EXISTS EMPRESA2 PASSWORD 'asd';
	protected void execStm (String stm, Object[] params, int[] types) {
		Connection conn = this.conn;
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(stm);
		} catch (SQLException e) {
			_throwException(e);
		}
		int idx = 1;
		int paramsLength = (params != null ? params.length : 0);
		for (int i = 0; i < paramsLength; i++) {
			try {
				ps.setObject(idx++, params[i], types[i]);
			} catch (SQLException e) {
				_throwException(e);
			}
		}
		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			_throwException(e);
		}
		_close(ps);
	}
	
	//CREATE USER IF NOT EXISTS EMPRESA2 PASSWORD 'asd';
	protected PreparedStatement execStm (PreparedStatement ps, int index, boolean last, String stm, Object[] params, int[] types) {
		PreparedStatement result = ps;
		Connection conn = this.conn;
		boolean batch = (ps != null);
		try {
			result = (batch ? result : conn.prepareStatement(stm));
		} catch (SQLException e) {
			_throwException(e);
		}
		int idx = 1;
		int paramsLength = (params != null ? params.length : 0);
		for (int i = 0; i < paramsLength; i++) {
			try {
				result.setObject(idx++, params[i], types[i]);
			} catch (SQLException e) {
				_throwException(e);
			}
		}
		try {
			result.addBatch();
		} catch (SQLException e) {
			_throwException(e);
		}
		if (((index + 1) % 200) == 0 || last) {
			try {
				result.executeBatch();
			} catch (SQLException e) {
				_throwException(e);
			}
			try {
				conn.commit();
			} catch (SQLException e) {
				_throwException(e);
			}
			//result.clearBatch();
		}
		if (false && !batch) {
		_close(result);
		}
		return result;
	}
	
	protected ResultSet execQuery (String stm, Object[] params, int[] types) {
		ResultSet result = null;
		Connection conn = this.conn;
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(stm);
		} catch (SQLException e) {
			_throwException(e);
		}
		int idx = 1;
		int paramsLength = (params != null ? params.length : 0);
		for (int i = 0; i < paramsLength; i++) {
			try {
				ps.setObject(idx++, params[i], types[i]);
			} catch (SQLException e) {
				_throwException(e);
			}
		}
		try {
			result = ps.executeQuery();
		} catch (SQLException e) {
			_throwException(e);
		}

		return result;
	}

	protected ResultSet execQuery (String stm, Object[] params) {
		ResultSet result = null;
		Connection conn = this.conn;
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(stm);
		} catch (SQLException e) {
			_throwException(e);
		}
		int idx = 1;
		int paramsLength = (params != null ? params.length : 0);
		for (int i = 0; i < paramsLength; i++) {
			try {
				ps.setObject(idx++, params[i]);
			} catch (SQLException e) {
				_throwException(e);
			}
		}
		try {
			result = ps.executeQuery();
		} catch (SQLException e) {
			_throwException(e);
		}

		return result;
	}
	
	
	
	protected void _close (Statement stm) {
		boolean notClosed = false;
		try {
			notClosed = (stm != null && !stm.isClosed());
		} catch (SQLException e) {
			_throwException(e);
		}
		if (notClosed) {
			try {
				stm.close();
			} catch (SQLException e) {
				_throwException(e);
			}
			stm = null;
		}
	}
	
	protected void _close (ResultSet rs) {
		boolean notClosed = false;
		try {
			notClosed = (rs != null && !rs.isClosed());
		} catch (SQLException e) {
			_throwException(e);
		}
		if (notClosed) {
			try {
				rs.close();
			} catch (SQLException e) {
				_throwException(e);
			}
			rs = null;
		}
	}
	
	public void closeAndStm (ResultSet rs) {
		Statement stm = null;
		try {
			stm = (rs != null ? rs.getStatement() : null);
		} catch (SQLException e) {
			_throwException(e);
		}
		_close(rs);
		_close(stm);
	}
	
	public void disableAutoCommit () {
		Connection conn = this.conn;
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			_throwException(e);
		}
	}
	
	public void enableAutoCommit () {
		Connection conn = this.conn;
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			_throwException(e);
		}
	}
	
	public DbManager commit () {
		applyStm();
		Connection conn = this.conn;
		try {
			conn.commit();
		} catch (SQLException e) {
			_throwException(e);
		}
		return this;
	}

	protected void applyStm () {
		if (this._currentStm != null) {
			this._currentStm.end();
//			this._currentStm = null;
		}
	}
	
	public Savepoint setSavePoint (String name) {
		Savepoint result = null;
		Connection conn = this.conn;
		try {
			result = (name != null ? conn.setSavepoint(name) : conn.setSavepoint());
		} catch (SQLException e) {
			_throwException(e);
		}
		return result;
	}
	
	public void rollback (Savepoint savepoint) {
		Connection conn = this.conn;
		try {
			conn.rollback(savepoint);
		} catch (SQLException e) {
			_throwException(e);
		}
	}
	
	public void rollback () {
		Connection conn = this.conn;
		try {
			conn.rollback();
		} catch (SQLException e) {
			_throwException(e);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected DbStatement _currentStm;
	
	public void apply (Object[] params) {
		if (this._currentStm != null) {
			this._currentStm.apply(params);
		}
	}
	
	protected void addStm (String stm, Object owner, ColumnEncoder[] encoders) {
		if (this._currentStm == null) {
			Connection conn = this.conn;
			this._currentStm = new DbStatement(conn, stm, owner, encoders);
		}
		else {
			if (encoders == null) {
				this._currentStm.add(stm);
			}
			else {
				this._currentStm.add(stm, encoders);
			}
		}
	}

	//CREATE USER IF NOT EXISTS EMPRESA2 PASSWORD 'asd';
	public DbManager createUser (String login, String pass) {
		String stm = buildCreateUserSql(login, pass);//"CREATE USER IF NOT EXISTS " + login + " PASSWORD '" + pass + "'";
		addStm(stm, null, null);
		return this;
	}
	
	//DROP USER IF EXISTS EMPRESA2;
	public DbManager dropUser (String login) {
		String stm = buildDropUserSql(login);//"DROP USER IF EXISTS " + login;
		addStm(stm, null, null);
		return this;
	}

	//		CREATE SCHEMA IF NOT EXISTS EMPRESA2_APP1 AUTHORIZATION EMPRESA2;
	public DbManager createSchema (String schemaName, String user) {
		addStm("ALTER USER " + user + " ADMIN true", null, null);
		
		String stm = buildCreateSchemaSql(schemaName, user);//"CREATE SCHEMA IF NOT EXISTS " + schemaName + " AUTHORIZATION " + user;
		addStm(stm, null, null);
		return this;
	}

	//		DROP SCHEMA IF EXISTS EMPRESA2_APP1;
	public DbManager dropSchema (String schemaName) {
		String stm = buildDropSchemaSql(schemaName);//"DROP SCHEMA IF EXISTS " + schemaName;
		addStm(stm, null, null);
		return this;
	}
	
	public DbManager dropApp (String login, String appName) {
		String schema = login + "_" + appName;
		dropSchema(schema);
		dropUser(login);
		return this;
	}

	public boolean existsApp (String login, String appName) {
		boolean result = false;
		String schema = login + "_" + appName;
		schema = schema.toUpperCase();
		result = existsSchema(schema);
		return result;
	}
	
	public DbManager createApp (String login, String appName, String pass) {
		createUser(login, pass);
		String schema = login + "_" + appName;
		schema = schema.toUpperCase();
		createSchema(schema, login);
		return this;
	}
	
	//CREATE SEQUENCE IF NOT EXISTS EMPRESA1_APP1.SEQ_1 START WITH 1 INCREMENT BY 1;
	public DbManager createSequence (String seqName) {
		String fullSeqName = applyCurrentSchema(seqName);
		String stm = buildCreateSequenceSql(fullSeqName);//"CREATE SEQUENCE IF NOT EXISTS " + fullSeqName + " START WITH 1 INCREMENT BY 1";
		addStm(stm, null, null);
		return this;
	}
	
	//DROP SEQUENCE IF EXISTS EMPRESA1_APP1.SEQ_1;
	public DbManager dropSequence (String seqName) {
		String fullSeqName = applyCurrentSchema(seqName);
		String stm = buildDropSequenceSql(fullSeqName);//"DROP SEQUENCE IF EXISTS " + fullSeqName;
		addStm(stm, null, null);
		return this;
	}
	
	//select EMPRESA1_APP1.SEQ_1.nextval;
	public Long getNextValSequence (String seqName) {
		Long result = null;
		String fullSeqName = applyCurrentSchema(seqName);
		String stm = buildSelectSequenceSql(fullSeqName);//"SELECT " + fullSeqName + ".nextval";
		ResultSet rs = execQuery(stm, null, null);
		try {
			result = (rs.next() ? Long.valueOf(rs.getLong(1)) : null);
		} catch (SQLException e) {
			_throwException(e);
		}
		closeAndStm(rs);
		return result;
	}

	//CREATE TABLE IF NOT EXISTS SCHEMA.TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))
	public DbManager createTable (String table, java.util.List<String> columns) {
		String fullTableName = applyCurrentSchema(table);
		String columnsStm = "";
		int length = (columns != null ? columns.size() : 0);
		for (int i = 0; i < length; i++) {
			String column = columns.get(i);
			column = (column != null ? column : "");
			columnsStm += (columnsStm.length() > 0 && column.length() > 0 ? ", " : "") + column;
		}
		String stm = buildCreateTableSql(fullTableName, columnsStm);//"CREATE TABLE IF NOT EXISTS " + fullTableName + "(" + columnsStm + ")";
		addStm(stm, null, null);
		System.out.println("SQL: " + stm);
		return this;
	}
	
	
	//SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'EMPRESA1_APP1' AND SCHEMA_OWNER = 'EMPRESA1'
	public boolean existsSchema (String schemaName) {
		boolean result = false;

		String stm = buildExistsSchemaSql(schemaName);//"SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";

		ResultSet rs = execQuery(stm, new Object[] {schemaName}, new int[] {java.sql.Types.VARCHAR});
		try {
			result = (rs.next() ? rs.getLong(1) > 0 : false);
		} catch (SQLException e) {
			_throwException(e);
		}
		closeAndStm(rs);

		return result;
	}
	
	
	//SELECT * FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND INDEX_NAME = ?
	public boolean existsIndex (String tableName, String indexName) {
		boolean result = false;
		String fullTableName = applyCurrentSchema(tableName);
		int idx = fullTableName.indexOf(".");
		String schemaName = (idx > -1 ? fullTableName.substring(0, idx) : this.currentSchema);
		String shortTableName = (idx > -1 ? fullTableName.substring(idx + 1) : tableName);

		String stm = buildExistsIndexSql(schemaName, shortTableName, indexName);//"SELECT COUNT(*) FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND INDEX_NAME = ?";

		ResultSet rs = execQuery(stm, new Object[] {schemaName, shortTableName, indexName}, new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
		try {
			result = (rs.next() ? rs.getLong(1) > 0 : false);
		} catch (SQLException e) {
			_throwException(e);
		}
		closeAndStm(rs);

		return result;
	}
	

	
	
	//SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'EMPRESA1_APP1' AND TABLE_NAME = 'TEST1_SEQ'
	public boolean existsTable (String tableName) {
		boolean result = false;
//		if (this.metaDb != null) {
//			String fullTableName = applyCurrentSchema(tableName);
//			result = this.metaDb.existsTable(fullTableName);
//		}
//		else {
			String fullTableName = applyCurrentSchema(tableName);
			int idx = fullTableName.indexOf(".");
			String schemaName = (idx > -1 ? fullTableName.substring(0, idx) : this.currentSchema);
			String shortTableName = (idx > -1 ? fullTableName.substring(idx + 1) : tableName);

			String stm = buildExistsTableSql(schemaName, shortTableName);//"SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

			ResultSet rs = execQuery(stm, new Object[] {schemaName, shortTableName}, new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
			try {
				result = (rs.next() ? rs.getLong(1) > 0 : false);
			} catch (SQLException e) {
				_throwException(e);
			}
			closeAndStm(rs);
//		}
		return result;
	}
	
	//SELECT COUNT(*) FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA = 'EMPRESA1_APP1' AND SEQUENCE_NAME = 'TEST1_SEQ'
	public boolean existsSequence (String seqName) {
		boolean result = false;
		String fullSeqName = applyCurrentSchema(seqName);
		int idx = fullSeqName.indexOf(".");
		String schemaName = (idx > -1 ? fullSeqName.substring(0, idx) : this.currentSchema);
		String shortSeqName = (idx > -1 ? fullSeqName.substring(idx + 1) : seqName);
		
		String stm = buildExistsSequenceSql(schemaName, shortSeqName);//"SELECT COUNT(*) FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA = ? AND SEQUENCE_NAME = ?";

		ResultSet rs = execQuery(stm, new Object[] {schemaName, shortSeqName}, new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
		try {
			result = (rs.next() ? rs.getLong(1) > 0 : false);
		} catch (SQLException e) {
			_throwException(e);
		}
		closeAndStm(rs);

		return result;
	}
	
	
	
	//DROP TABLE IF EXISTS SCHEMA.TEST
	public DbManager dropTable (String table) {
		String fullTableName = applyCurrentSchema(table);
		String stm = buildDropTableSql(fullTableName);//"DROP TABLE IF EXISTS " + fullTableName;
		System.out.println("SQL: " + stm);
		addStm(stm, null, null);
		return this;
	}
	
	
	
	//CREATE UNIQUE INDEX IF NOT EXISTS SCHEMA.newIndexName ON SCHEMA.tablename(column1, ...)
	//CREATE HASH INDEX IF NOT EXISTS SCHEMA.newIndexName ON SCHEMA.tablename(column1, ...)
	//createIndex("newIndexName", "tablename", true, "column1");
	public DbManager createIndex (String index, String table, boolean unique, String... columns) {
		String fullIndexName = applyCurrentSchema(index);
		String fullTableName = applyCurrentSchema(table);
		String columnsStm = "";
		int length = (columns != null ? columns.length : 0);
		for (int i = 0; i < length; i++) {
			String column = columns[i];
			column = (column != null ? column : "");
			columnsStm += (columnsStm.length() > 0 && column.length() > 0 ? ", " : "") + column;
		}
		String stm = buildCreateIndexSql(unique, fullIndexName, fullTableName, columnsStm);//"CREATE " + (unique ? "UNIQUE" : "HASH") + " INDEX IF NOT EXISTS " + fullIndexName + " ON " + fullTableName + "(" + columnsStm + ")";
		addStm(stm, null, null);
		return this;
	}	

	
	
	//DROP INDEX IF EXISTS SCHEMA.newIndexName
	public DbManager dropIndex (String index) {
		String fullIndexName = applyCurrentSchema(index);
		String stm = buildDropIndexSql(fullIndexName);//"DROP INDEX IF EXISTS " + fullIndexName;
		addStm(stm, null, null);
		return this;
	}	

	
	
	//CREATE OR REPLACE FORCE VIEW TEST_VIEW AS SELECT * FROM TEST WHERE ID < 100
	public DbManager createView (String view, String query) {
		String fullViewName = applyCurrentSchema(view);
		String stm = buildCreateViewSql(fullViewName, query);//"CREATE OR REPLACE FORCE VIEW " + fullViewName + " AS " + query;
		addStm(stm, null, null);
		return this;
	}

	
	public DbManager insert (String table, String[] columns, Object owner, Class[] types) {
		return insert(table, columns, owner, SqlMapper.classes2Encoders(types));
	}
	
	
	
	//INSERT INTO EMPRESA1_APP1.TEST (ID, NAME) VALUES (1, 'Norway');
	public DbManager insert (String table, String[] columns, Object owner, ColumnEncoder[] encoders) {
		String fullTableName = applyCurrentSchema(table);
		String columnsStm = "";
		String valuesStm = "";
		int columnsLength = (columns != null ? columns.length : 0);
		for (int i = 0; i < columnsLength; i++) {
			String column = columns[i];
			column = (column != null ? column : "");
			columnsStm += (columnsStm.length() > 0 && column.length() > 0 ? ", " : "") + column;
			valuesStm += (valuesStm.length() > 0 ? ", " : "") + "?";
		}
		String stm = buildInsertSql(fullTableName, columnsStm, valuesStm);//"INSERT INTO " + fullTableName + " (" + columnsStm + ")" + " VALUES " + "(" + valuesStm + ")";

		addStm(stm, owner, encoders);
		return this;
	}
	
	
	
	//INSERT INTO EMPRESA1_APP1.TEST (ID, NAME) SELECT EMPRESA1_APP1.SEQ_1.nextval, 'Finland1';
	public DbManager insertAutoIncremental (String table, String pkColumn, String seqName, String[] columns, Object owner, ColumnEncoder[] encoders) {
		String fullTableName = applyCurrentSchema(table);
		String columnsStm = "";
		columnsStm += pkColumn;
		int columnsLength = (columns != null ? columns.length : 0);
		String fullSeqName = applyCurrentSchema(seqName);
		String valuesStm = "";
		valuesStm += fullSeqName + ".nextval";
		for (int i = 0; i < columnsLength; i++) {
			String column = columns[i];
			column = (column != null ? column : "");
			columnsStm += (columnsStm.length() > 0 && column.length() > 0 ? ", " : "") + column;
			valuesStm += (valuesStm.length() > 0 ? ", " : "") + "?";
		}
		String stm = buildInserAutoIncrementalSql(fullTableName, columnsStm, valuesStm);//"INSERT INTO " + fullTableName + " (" + columnsStm + ")" + " SELECT " + "" + valuesStm + "";
		addStm(stm, owner, encoders);
		return this;
	}
	
	

	public DbManager update (String table, String[] columns, Object owner, Class[] types) {
		return update(table, columns, owner, SqlMapper.classes2Encoders(types));
	}
	
	//UPDATE EMPRESA1_APP1.TEST SET NAME='Norway1' WHERE ID=1;
	public DbManager update (String table, String[] columns, Object owner, ColumnEncoder[] encoders) {
		String fullTableName = applyCurrentSchema(table);
		String assignStm = "";
		int columnsLength = (columns != null ? columns.length : 0);
		for (int i = 0; i < columnsLength - 1; i++) {
			String column = columns[i];
			column = (column != null ? column : "");
			String assign = column + " = " + "?";
			
			assignStm += (assignStm.length() > 0 && assign.length() > 0 ? ", " : "") + assign;
		}
		String pkColumn = columns[columns.length - 1];
		
		String stm = buildUpdateSql(fullTableName, assignStm, pkColumn);//"UPDATE " + fullTableName + " SET " + assignStm + "" + " WHERE " + pkColumn + " = ?";
		addStm(stm, owner, encoders);
		return this;
	}
	
	

	//DELETE FROM EMPRESA1_APP1.TEST WHERE ID=25;
	public DbManager delete (String table, String pkColumn, Object owner, Class<?> type) {
		return delete(table, pkColumn, owner, SqlMapper.classToColumnEncoder(type));
	}
	
	//DELETE FROM EMPRESA1_APP1.TEST WHERE ID=25;
	public DbManager delete (String table, String pkColumn, Object owner, ColumnEncoder encoder) {
		String fullTableName = applyCurrentSchema(table);

		String stm = buildDeleteSql(fullTableName, pkColumn);//"DELETE FROM " + fullTableName + " WHERE " + pkColumn + " = ?";

		addStm(stm, owner, new ColumnEncoder[] {encoder});
		return this;
	}

	
	
	//DELETE FROM EMPRESA1_APP1.TEST WHERE ID IN (25, 26, 27);
	public DbManager delete (String table, String pkColumn, Object owner, Class<?> type, int length) {
		return delete(table, pkColumn, owner, SqlMapper.classToColumnEncoder(type), length);
	}
	
	//DELETE FROM EMPRESA1_APP1.TEST WHERE ID IN (25, 26, 27);
	public DbManager delete (String table, String pkColumn, Object owner, ColumnEncoder encoder, int length) {
		String fullTableName = applyCurrentSchema(table);
		String extraStm = "";
		ColumnEncoder[] encoders = new ColumnEncoder[length];
		for (int i = 0; i < length; i++) {
			extraStm += (extraStm.length() > 0 ? ", " : "") + "?";
			encoders[i] = encoder;
		}

		String stm = buildDeleteSql(fullTableName, pkColumn, extraStm);//"DELETE FROM " + fullTableName + " WHERE " + pkColumn + " IN (" + extraStm + ")";

		addStm(stm, owner, encoders);
		return this;
	}
	
	//SELECT NAME, SURNAME FROM TABLE WHERE ID = ?
	public Object[] load (String table, String pkColumn, Object pk, String[] columns, int[] types) {
		Object[] result = null;
		String fullTableName = applyCurrentSchema(table);
		String columnsStm = "";
		int length = (columns != null ? columns.length : 0);
		for (int i = 0; i < length; i++) {
			String column = columns[i];
			column = (column != null ? column : "");
			
			columnsStm += (columnsStm.length() > 0 && column.length() > 0 ? ", " : "") + column;
		}
		Object[] params = new Object[] {pk};
		String stm = buildLoadPropertiesSql(columnsStm, fullTableName, pkColumn);//"SELECT " + columnsStm + " FROM " + fullTableName + " WHERE " + pkColumn + " = ?";
		ResultSet rs = execQuery(stm, params, types);
		
		boolean hasNext = false;
		try {
			hasNext = (rs.next());
		} catch (SQLException e) {
			_throwException(e);
		}
		if (hasNext) {
			result = new Object[length];
			for (int i = 0; i < length; i++) {
				try {
					result[i] = rs.getObject(i + 1);
				} catch (SQLException e) {
					_throwException(e);
				}
			}
		}
		
		closeAndStm(rs);
		
		return result;
	}
	
	//SELECT NAME, SURNAME FROM TABLE WHERE ID = ?
	public ResultSet loadProperties (String table, String pkColumn, Object pk, String columns) {
		ResultSet result = null;
		String fullTableName = applyCurrentSchema(table);
		String columnsStm = "";
		String[] arrayColumns = columns.split(",");
		int length = (arrayColumns != null ? arrayColumns.length : 0);
		for (int i = 0; i < length; i++) {
			String column = arrayColumns[i];
			column = (column != null ? column : "");
			
			columnsStm += (columnsStm.length() > 0 && column.length() > 0 ? ", " : "") + column;
		}
		int[] types = new int[] {SqlMapper.class2SqlType(pk.getClass())};
		Object[] params = new Object[] {pk};
		String stm = buildLoadPropertiesSql(columnsStm, fullTableName, pkColumn);//"SELECT " + columnsStm + " FROM " + fullTableName + " WHERE " + pkColumn + " = ?";
		ResultSet rs = execQuery(stm, params, types);
		
		boolean hasNext = false;
		try {
			hasNext = (rs.next());
		} catch (SQLException e) {
			_throwException(e);
		}
		if (hasNext) {
			result = rs;
		}
		else {
			closeAndStm(rs);
		}

		return result;
	}
	
	public Long countAll(String table) {
		Long result = null;
		String fullTableName = applyCurrentSchema(table);
		String stm = buildCountAllSql(fullTableName);//"SELECT count(*) FROM " + fullTableName;

		ResultSet rs = execQuery(stm, null, null);
		try {
			result = (rs.next() ? Long.valueOf(rs.getLong(1)) : Long.valueOf(0));
		} catch (SQLException e) {
			_throwException(e);
		}
		
		closeAndStm(rs);
		
		return result;
	}
	
	
	
	//SELECT NAME, SURNAME FROM TABLE WHERE ID = ?
	public ResultSet query (String query, Object[] params, int[] types) {
		ResultSet result = null;

		result = execQuery(query, params, types);
		
		return result;
	}

	public ResultSet query (String query, Object[] params) {
		ResultSet result = null;

		result = execQuery(query, params);
		
		return result;
	}
	
	public AbstractFilter filter (String table, String selectClausule, Class<?>... selectTypes) {
		AbstractFilter result = null;
		String fullTableName = applyCurrentSchema(table);
		result = new NativeFilter(fullTableName, this, selectClausule, selectTypes);
		return result;
	}

	//SELECT COLUMN_NAME, TYPE_NAME, CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'EMPRESA1_APP1' AND TABLE_NAME = 'PAIS'
	public Object[][] loadColumns (String tableName) {
		Object[][] result = null;
		
		String fullTableName = applyCurrentSchema(tableName);
		int idx = fullTableName.indexOf(".");
		String schemaName = (idx > -1 ? fullTableName.substring(0, idx) : this.currentSchema);
		String shortTableName = (idx > -1 ? fullTableName.substring(idx + 1) : tableName);
		
		String stm = buildLoadColumnsSql(schemaName, shortTableName);//"SELECT COLUMN_NAME, TYPE_NAME, CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

		ResultSet rs = execQuery(stm, new Object[] {schemaName, shortTableName}, new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
		
		java.util.List columns = new java.util.ArrayList<Object[]>();
		try {
			while (rs != null && rs.next()) {
				Object[] column = new Object[3];
				column[0] = rs.getString(1);
				column[1] = rs.getString(2);
				column[2] = Integer.valueOf(rs.getInt(3));
				columns.add(column);
			}
		} catch (SQLException e) {
			_throwException(e);
		}
		closeAndStm(rs);
		int columnsSize = columns.size();
		if (columnsSize > 0) {
			result = new Object[columnsSize][];
			columns.toArray(result);
		}

		return result;
	}

	
	
	//ALTER TABLE SCHEMA.TEST ADD IF NOT EXISTS CREATEDATE TIMESTAMP
	public DbManager addColumn (String tableName, String column) {
		String fullTableName = applyCurrentSchema(tableName);
		String stm = buildAddColumnSql(fullTableName, column);//"ALTER TABLE " + fullTableName + " ADD IF NOT EXISTS " + column;
		addStm(stm, null, null);
		return this;
	}


	
	
	public boolean _doUpdateTable(String tableName, java.util.List<String> columns) {
		boolean result = false;
		Object[][] tableColumns = loadColumns(tableName);
		int tableColumnsSize = (tableColumns != null ? tableColumns.length : 0);
		int columnsSize = (columns != null ? columns.size() : 0);
		for (int i = 0; i < columnsSize; i++) {
			String columnInfo = columns.get(i);
			columnInfo = (columnInfo != null ? columnInfo.trim() : "");
			int idx = columnInfo.indexOf(" ");
			String columnName = columnInfo.substring(0, idx);
			String columnType = columnInfo.substring(idx + 1);
			Object[] tableColumn = null;
			for (int j = 0; j < tableColumnsSize; j++) {
				Object[] itemTableColumn = tableColumns[j];
				if (columnName.equals(itemTableColumn[0])) {
					tableColumn = itemTableColumn;
					break;
				}
			}
			if (tableColumn == null) {
				addColumn(tableName, columnInfo);
				result = true;
			}
		}
		return result;
	}

	public DbManager updateTable(String tableName, java.util.List<String> columns) {
		_doUpdateTable(tableName, columns);
		return this;
	}

	public DbManager createEntity(String tableName, String seqName, java.util.List<String> columns) {
		doCreateEntity(tableName, seqName, true, columns);
		return this;
	}

	public boolean doCreateEntity(String tableName, String seqName, boolean applyCommit, java.util.List<String> columns) {
		boolean result = false;
		String schemaName = this.getCurrentSchema();
		boolean existsSchema = existsSchema(schemaName);
		if (!existsSchema) {
			String user = this.getDbUser();
			createSchema(schemaName, user);
		}
		boolean existsTable = existsTable(tableName);
		if (!existsTable) {
			createTable(tableName, columns);
			result = true;
		}
		else {
			result = _doUpdateTable(tableName, columns);
		}
		if (seqName != null) {
			boolean existsSequence = existsSequence(seqName);
			if (!existsSequence) {
				createSequence(seqName);
				result = true;
			}
		}
		if (applyCommit && result) {
			commit();
		}
		return result;
	}

	public DbManager createEntity(EntityDefinition def) {
		String tableName = def.getTableName();
		String seqName = def.getSequenceName();
		java.util.List<String> columnDefinitions = def.getColumnDefinitions();
		boolean commit = doCreateEntity(tableName, seqName, false, columnDefinitions);
		java.util.List<TableIndex> indexes = def.getIndexes();
		int indexesSize = (indexes != null ? indexes.size() : 0);
		for (int i = 0; i < indexesSize; i++) {
			TableIndex index = indexes.get(i);
			String indexName = (index != null ? index.getName() : null);
			if (!existsIndex(tableName, indexName)) {
				this.createIndex(indexName, tableName, index.checkUnique(), index.getColumns());
				commit = true;
			}
		}
		boolean flag = createExtraTables(def);
		commit = (commit ? commit : flag);
		if (commit) {
			commit();
		}
		return this; 
	}

	
	protected boolean createExtraTables(EntityDefinition def) {
		boolean result = false;
		if (def != null) {
			java.util.List<PropertyEntity> properties = def.getProperties();
			if (properties != null) {
				for (PropertyEntity property : properties) {
					if (property != null) {
						ListPropertyEntity listPropertyEntity = null;
						try { listPropertyEntity = (ListPropertyEntity)property; } catch (ClassCastException e) {}
						if (listPropertyEntity != null && listPropertyEntity.getItemColumnName() != null) {
							boolean flag = createExtraTables(def, listPropertyEntity);
							result = (result ? result : flag);
						}
					}
				}
			}
		}
		return result;
	}

	protected boolean createExtraTables(EntityDefinition def, ListPropertyEntity property) {
		boolean result = false;
		if (def != null && property != null) {
			String itemColumn = property.getItemColumnName();
			if (itemColumn != null) {
				String tableName = property.getTableName();
				String ownerColumn = property.getOwnerColumnName();
				String typeDef = SqlMapper.classToTypeDefinition(Long.class, null);
				String columns = ownerColumn + " " + typeDef + ", " + itemColumn + " " + typeDef;
				java.util.List<String> listColumns = new java.util.ArrayList<String>();
				listColumns.add(columns);
				
				result = this.doCreateEntity(tableName, null, false, listColumns);
			}
			else {
				Class<?> type = property.getType();
				this.reset(type);
//				try {
//					Method method = type.getMethod("createDb", DbManager.class);
//					method.invoke(null, this);
//					result = true;
//				} catch (Exception e) {
//					_throwException(e);
//				}
//				
//				MethodAccess access = MethodAccess.get(type);
//				result = ((Boolean)access.invoke(null, "createDb", this)).booleanValue();
			}
		}
		return result;
	}

	protected boolean deleteExtraTables(EntityDefinition def) {
		boolean result = false;
		if (def != null) {
			java.util.List<PropertyEntity> properties = def.getProperties();
			if (properties != null) {
				for (PropertyEntity property : properties) {
					if (property != null) {
						ListPropertyEntity listPropertyEntity = null;
						try { listPropertyEntity = (ListPropertyEntity)property; } catch (ClassCastException e) {}
						if (listPropertyEntity != null && listPropertyEntity.getItemColumnName() != null) {
							boolean flag = deleteExtraTables(def, listPropertyEntity);
							result = (result ? result : flag);
						}
					}
				}
			}
		}
		return result;
	}

	protected boolean deleteExtraTables(EntityDefinition def, ListPropertyEntity property) {
		boolean result = false;
		if (def != null && property != null) {
			String tableName = property.getTableName();
			
			result = this.doDeleteEntity(tableName, null, false);
		}
		return result;
	}

	public boolean doDeleteEntity(String tableName, String seqName, boolean applyCommit) {
		boolean result = false;
		boolean existsTable = existsTable(tableName);
		if (existsTable) {
			dropTable(tableName);
			result = true;
		}
		if (seqName != null) {
			boolean existsSequence = existsSequence(seqName);
			if (existsSequence) {
				dropSequence(seqName);
				result = true;
			}
		}
		if (applyCommit && result) {
			commit();
		}
		return result;
	}

	
	public DbManager updateEntity(EntityDefinition def) {
		this.createEntity(def);
		return this; 
	}

	
	public DbManager deleteEntity(EntityDefinition def) {
		String tableName = def.getTableName();
		String seqName = def.getSequenceName();
		boolean commit = doDeleteEntity(tableName, seqName, false);
		java.util.List<TableIndex> indexes = def.getIndexes();
		int indexesSize = (indexes != null ? indexes.size() : 0);
		for (int i = 0; i < indexesSize; i++) {
			TableIndex index = indexes.get(i);
			String indexName = (index != null ? index.getName() : null);
			if (existsIndex(tableName, indexName)) {
				this.dropIndex(indexName);
				commit = true;
			}
		}
		boolean flag = deleteExtraTables(def);
		commit = (commit ? commit : flag);
		if (commit) {
			commit();
		}
		return this; 
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected abstract String buildCreateUserSql (String login, String pass);
//		return "CREATE USER IF NOT EXISTS " + login + " PASSWORD '" + pass + "'";
//	}

	protected abstract String buildDropUserSql (String login);
//		return "DROP USER IF EXISTS " + login;
//	}

	protected abstract String buildCreateSchemaSql (String schemaName, String user);
//		return "CREATE SCHEMA IF NOT EXISTS " + schemaName + " AUTHORIZATION " + user;
//	}

	protected abstract String buildDropSchemaSql (String schemaName);
//		return "DROP SCHEMA IF EXISTS " + schemaName;
//	}

	protected abstract String buildCreateSequenceSql (String fullSeqName);
//		return "CREATE SEQUENCE IF NOT EXISTS " + fullSeqName + " START WITH 1 INCREMENT BY 1";
//	}

	protected abstract String buildDropSequenceSql (String fullSeqName);
//		return "DROP SEQUENCE IF EXISTS " + fullSeqName;
//	}

	protected abstract String buildSelectSequenceSql (String fullSeqName);
//		return "SELECT " + fullSeqName + ".nextval";
//	}
	
	protected abstract String buildCreateTableSql (String fullTableName, String columnsStm);
//		return "CREATE TABLE IF NOT EXISTS " + fullTableName + "(" + columnsStm + ")";
//	}

	protected abstract String buildExistsSchemaSql (String schemaName);
//		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
//	}

	protected abstract String buildExistsIndexSql (String schemaName, String tableName, String indexName);
//		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND INDEX_NAME = ?";
//	}

	protected abstract String buildExistsTableSql (String schemaName, String tableName);
//		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
//	}

	protected abstract String buildExistsSequenceSql (String schemaName, String seqName);
//		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA = ? AND SEQUENCE_NAME = ?";
//	}

	protected abstract String buildDropTableSql (String fullTableName);
//		return "DROP TABLE IF EXISTS " + fullTableName;
//	}

	protected abstract String buildCreateIndexSql (boolean unique, String fullIndexName, String fullTableName, String columnsStm);
//		return "CREATE " + (unique ? "UNIQUE" : "HASH") + " INDEX IF NOT EXISTS " + fullIndexName + " ON " + fullTableName + "(" + columnsStm + ")";
//	}

	protected abstract String buildDropIndexSql (String fullIndexName);
//		return "DROP INDEX IF EXISTS " + fullIndexName;
//	}

	protected abstract String buildCreateViewSql (String fullViewName, String query);
//		return "CREATE OR REPLACE FORCE VIEW " + fullViewName + " AS " + query;
//	}

	protected String buildInsertSql (String fullTableName, String columnsStm, String valuesStm) {
		return "INSERT INTO " + fullTableName + " (" + columnsStm + ")" + " VALUES " + "(" + valuesStm + ")";
	}

	protected String buildInserAutoIncrementalSql (String fullTableName, String columnsStm, String valuesStm) {
		return "INSERT INTO " + fullTableName + " (" + columnsStm + ")" + " SELECT " + "" + valuesStm + "";
	}

	protected String buildUpdateSql (String fullTableName, String assignStm, String pkColumn) {
		return "UPDATE " + fullTableName + " SET " + assignStm + "" + " WHERE " + pkColumn + " = ?";
	}

	protected String buildDeleteSql (String fullTableName, String pkColumn) {
		return "DELETE FROM " + fullTableName + " WHERE " + pkColumn + " = ?";
	}

	protected String buildDeleteSql (String fullTableName, String pkColumn, String extraStm) {
		return "DELETE FROM " + fullTableName + " WHERE " + pkColumn + " IN (" + extraStm + ")";
	}

	protected String buildLoadPropertiesSql (String columnsStm, String fullTableName, String pkColumn) {
		return "SELECT " + columnsStm + " FROM " + fullTableName + " WHERE " + pkColumn + " = ?";
	}

	protected String buildCountAllSql (String fullTableName) {
		return "SELECT count(*) FROM " + fullTableName;
	}

	protected abstract String buildLoadColumnsSql (String schemaName, String tableName);
//		return "SELECT COLUMN_NAME, TYPE_NAME, CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
//	}

	protected abstract String buildAddColumnSql (String fullTableName, String column);
//		return "ALTER TABLE " + fullTableName + " ADD IF NOT EXISTS " + column;
//	}

	
	
	public boolean exists (String table, String pkColumn) {
		boolean result = false;
		String fullTableName = applyCurrentSchema(table);
		String stm = buildExistsSql(fullTableName, pkColumn);//"UPDATE " + fullTableName + " SET " + assignStm + "" + " WHERE " + pkColumn + " = ?";
		ResultSet rs = execQuery(stm, null, null);
		Long count = null;
		try {
			count = (rs.next() ? Long.valueOf(rs.getLong(1)) : null);
		} catch (SQLException e) {
			_throwException(e);
		}
		closeAndStm(rs);
		result = (count != null && count.longValue() > 0);
		return result;
	}
	
	protected String buildExistsSql (String fullTableName, String pkColumn) {
		return "SELECT COUNT(*) FROM " + fullTableName + " WHERE " + pkColumn + " = ?";
	}

	

	
	
	
	protected String pkg;
	
	public void setPackage(String pkg) {
		this.pkg = pkg;
	}

	protected String classes;
	
	public void setClasses(String newValue) {
		this.classes = newValue;
	}

	public void reset() {
		String pkg = (this.pkg != null ? this.pkg.trim() : "");
		if (pkg.length() > 0) {
			String[] split = (this.classes != null ? this.classes.split(",") : null);
			int length = (split != null ? split.length : 0);
			for (int i = 0; i < length; i++) {
				String className = split[i];
				String fullClassName = pkg + "." + className;
				Class<?> type = null;
				try {
					type = Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
				} catch (ClassNotFoundException e) {
					_throwException(e);
				}
				reset(type);
			}
		}
	}
	
	protected java.util.Map _defs = null;
	
	protected EntityDefinition _loadDefinition (Class<?> type) {
		EntityDefinition result = null;
		this._defs = (this._defs == null ? new java.util.HashMap() : this._defs);
		result = (EntityDefinition)this._defs.get(type);
		if (result == null) {
			try {
				Field reqField = type.getDeclaredField("__DEFINITION__");
				reqField.setAccessible(true);
				result = (EntityDefinition)reqField.get(null);
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
			this._defs.put(type, result);
		}
		return result;
	}
	
	public void reset (Class<?> type) {
		EntityDefinition def = _loadDefinition(type);
		if (true) {
			this.deleteEntity(def);
			this.commit();
			this.createEntity(def);
		}
		else {
			this.updateEntity(def);
		}
	}
	
	public void update() {
		String pkg = (this.pkg != null ? this.pkg.trim() : "");
		if (pkg.length() > 0) {
			String[] split = (this.classes != null ? this.classes.split(",") : null);
			int length = (split != null ? split.length : 0);
			for (int i = 0; i < length; i++) {
				String className = split[i];
				String fullClassName = pkg + "." + className;
				Class<?> type = null;
				try {
					type = Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
				} catch (ClassNotFoundException e) {
					_throwException(e);
				}
				update(type);
			}
		}
	}
	
	public void update (Class<?> type) {
		EntityDefinition def = _loadDefinition(type);
		this.updateEntity(def);
	}
	


}
