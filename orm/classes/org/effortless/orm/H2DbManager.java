package org.effortless.orm;

import java.sql.SQLException;

import org.effortless.core.StringUtils;
import org.effortless.core.UnusualException;
import org.effortless.orm.DbManager;

public class H2DbManager extends Object implements DbDriver {

	public H2DbManager () {
		super();
	}

	//jdbc:h2:file:/usr/databases/db1/${DATABASE_NAME};USER=${USER_NAME};PASSWORD=${PASSWORD};IFEXISTS=TRUE;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE
	//String url = "jdbc:h2:/data/sample;IFEXISTS=TRUE";
	//jdbc:h2:file:/usr/databases/db1/${DATABASE_NAME};IFEXISTS=TRUE;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE
		
	/*
ENCRYPT DB: Encrypting or Decrypting a Database
Class.forName("org.h2.Driver");
String url = "jdbc:h2:~/test;CIPHER=AES";
String user = "sa";
String pwds = "filepwd userpwd";
conn = DriverManager.
    getConnection(url, user, pwds);

java -cp h2*.jar org.h2.tools.ChangeFileEncryption -dir ~ -db test -cipher AES -encrypt filepwd
	 */
	
	
	public String loadDriverClass () {
        return "org.h2.Driver";
	}

	protected void _throwException (Exception e) {
		throw new UnusualException(e);
	}
	
	public void backup (DbManager db) {
//		java org.h2.tools.Script -url jdbc:h2:~/test -user sa -script test.zip -options compression zip
		try {
			org.h2.tools.Script.main("-url", db.dbUrl, "-user", db.dbUser, "-script", "test.zip", "-options", "compression", "zip");
		} catch (SQLException e) {
			_throwException(e);
		}
	}
	
	public void restore (DbManager db) {
//		java org.h2.tools.RunScript -url jdbc:h2:~/test -user sa -script test.zip -options compression zip//
		try {
			org.h2.tools.RunScript.main("-url", db.dbUrl, "-user", db.dbUser, "-script", "test.zip", "-options", "compression", "zip//");
		} catch (SQLException e) {
			_throwException(e);
		}
	}

	
	
	
	
	protected String _upper (String value) {
		return (value != null ? value.toUpperCase() : value);
	}
	
	public String buildCreateUserSql (String login, String pass) {
		return "CREATE USER IF NOT EXISTS " + _upper(login) + " PASSWORD '" + pass + "'";
	}

	public String buildDropUserSql (String login) {
		return "DROP USER IF EXISTS " + _upper(login);
	}

	public String buildCreateSchemaSql (String schemaName, String user) {
		return "CREATE SCHEMA IF NOT EXISTS " + _upper(schemaName) + " AUTHORIZATION " + _upper(user);
	}

	public String buildDropSchemaSql (String schemaName) {
		return "DROP SCHEMA IF EXISTS " + _upper(schemaName);
	}

	public String buildCreateSequenceSql (String fullSeqName) {
		return "CREATE SEQUENCE IF NOT EXISTS " + _upper(fullSeqName) + " START WITH 1 INCREMENT BY 1";
	}

	public String buildDropSequenceSql (String fullSeqName) {
		return "DROP SEQUENCE IF EXISTS " + _upper(fullSeqName);
	}

	public String buildSelectSequenceSql (String fullSeqName) {
		return "SELECT " + _upper(fullSeqName) + ".nextval";
	}
	
	public String buildCreateTableSql (String fullTableName, String columnsStm) {
		return "CREATE TABLE IF NOT EXISTS " + _upper(fullTableName) + "(" + columnsStm + ")";
	}

	public String buildExistsSchemaSql (String schemaName) {
		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE UPPER(SCHEMA_NAME) = UPPER(?)";
	}

	public String buildExistsIndexSql (String tableName, String indexName, String schemaName) {
		String result = null;
		result = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.INDEXES WHERE UPPER(TABLE_NAME) = UPPER(?) AND UPPER(INDEX_NAME) = UPPER(?)";
		if (schemaName != null) {
			result += " AND UPPER(TABLE_SCHEMA) = UPPER(?)";
		}
		return result;
	}

	public String buildExistsTableSql (String tableName, String schemaName) {
		String result = null;
		result = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE UPPER(TABLE_NAME) = UPPER(?)";
		if (schemaName != null) {
			result += " AND UPPER(TABLE_SCHEMA) = UPPER(?)";
		}
		return result;
	}

	public String buildExistsSequenceSql (String seqName, String schemaName) {
		String result = null;
		result = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SEQUENCES WHERE UPPER(SEQUENCE_NAME) = UPPER(?)";
		if (schemaName != null) {
			result += " AND UPPER(SEQUENCE_SCHEMA) = UPPER(?)";
		}
		return result;
	}

	public String buildDropTableSql (String fullTableName) {
		return "DROP TABLE IF EXISTS " + _upper(fullTableName);
	}

	public String buildCreateIndexSql (boolean unique, String fullIndexName, String fullTableName, String columnsStm) {
		return "CREATE " + (unique ? "UNIQUE" : "HASH") + " INDEX IF NOT EXISTS " + _upper(fullIndexName) + " ON " + _upper(fullTableName) + "(" + columnsStm + ")";
	}

	public String buildDropIndexSql (String fullIndexName) {
		return "DROP INDEX IF EXISTS " + _upper(fullIndexName);
	}

	public String buildCreateViewSql (String fullViewName, String query) {
		return "CREATE OR REPLACE FORCE VIEW " + _upper(fullViewName) + " AS " + query;
	}

	public String buildLoadColumnsSql (String tableName, String schemaName) {
		String result = null;
		result = "SELECT COLUMN_NAME, TYPE_NAME, CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_NAME) = UPPER(?)";
		if (schemaName != null) {
			result += " AND UPPER(TABLE_SCHEMA) = UPPER(?)";
		}
		return result;
	}

	public String buildAddColumnSql (String fullTableName, String column) {
		return "ALTER TABLE " + _upper(fullTableName) + " ADD COLUMN IF NOT EXISTS " + column;
//		return "ALTER TABLE " + _upper(fullTableName) + " ADD " + column;
	}

	public String[] buildAddConstraintsColumnSql(String fullTableName, String column, String constraints) {
		String[] result = null;
		boolean unique = constraints.contains("UNIQUE");
		boolean notnull = constraints.contains("NOT NULL");
//		int size = (unique && notnull ? 2 : ((unique || notnull) ? 1 : 0));
		int size = (unique ? 2 : 1);
		result = (size > 0 ? new String[size] : null);
		int idx = 0;
		if (unique) {
			String constraintName = StringUtils.upperCase(column + "_" + "UNIQUE");
			String stm = "ALTER TABLE " + fullTableName + " ADD CONSTRAINT " + constraintName + " " + "UNIQUE(" + column + ")" + " NOCHECK";
			result[idx++] = stm;
		}
		if (notnull) {
			String stm = "ALTER TABLE " + fullTableName + " ALTER COLUMN " + column + " SET " + "NOT NULL";
			result[idx++] = stm;
		}
		else {
			String stm = "ALTER TABLE " + fullTableName + " ALTER COLUMN " + column + " SET " + "NULL";
			result[idx++] = stm;
		}
		return result;
	}

}
