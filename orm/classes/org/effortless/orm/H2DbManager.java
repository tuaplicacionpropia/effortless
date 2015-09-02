package org.effortless.orm;

import java.sql.SQLException;

import org.effortless.core.UnusualException;
import org.effortless.orm.DbManager;

public class H2DbManager extends DbManager {

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
	
	
	protected String loadDriverClass () {
        return "org.h2.Driver";
	}

	protected void _throwException (Exception e) {
		throw new UnusualException(e);
	}
	
	public void backup () {
//		java org.h2.tools.Script -url jdbc:h2:~/test -user sa -script test.zip -options compression zip
		try {
			org.h2.tools.Script.main("-url", this.dbUrl, "-user", this.dbUser, "-script", "test.zip", "-options", "compression", "zip");
		} catch (SQLException e) {
			_throwException(e);
		}
	}
	
	public void restore () {
//		java org.h2.tools.RunScript -url jdbc:h2:~/test -user sa -script test.zip -options compression zip//
		try {
			org.h2.tools.RunScript.main("-url", this.dbUrl, "-user", this.dbUser, "-script", "test.zip", "-options", "compression", "zip//");
		} catch (SQLException e) {
			_throwException(e);
		}
	}

	
	
	
	
	protected String _upper (String value) {
		return (value != null ? value.toUpperCase() : value);
	}
	
	protected String buildCreateUserSql (String login, String pass) {
		return "CREATE USER IF NOT EXISTS " + _upper(login) + " PASSWORD '" + pass + "'";
	}

	protected String buildDropUserSql (String login) {
		return "DROP USER IF EXISTS " + _upper(login);
	}

	protected String buildCreateSchemaSql (String schemaName, String user) {
		return "CREATE SCHEMA IF NOT EXISTS " + _upper(schemaName) + " AUTHORIZATION " + _upper(user);
	}

	protected String buildDropSchemaSql (String schemaName) {
		return "DROP SCHEMA IF EXISTS " + _upper(schemaName);
	}

	protected String buildCreateSequenceSql (String fullSeqName) {
		return "CREATE SEQUENCE IF NOT EXISTS " + _upper(fullSeqName) + " START WITH 1 INCREMENT BY 1";
	}

	protected String buildDropSequenceSql (String fullSeqName) {
		return "DROP SEQUENCE IF EXISTS " + _upper(fullSeqName);
	}

	protected String buildSelectSequenceSql (String fullSeqName) {
		return "SELECT " + _upper(fullSeqName) + ".nextval";
	}
	
	protected String buildCreateTableSql (String fullTableName, String columnsStm) {
		return "CREATE TABLE IF NOT EXISTS " + _upper(fullTableName) + "(" + columnsStm + ")";
	}

	protected String buildExistsSchemaSql (String schemaName) {
		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE UPPER(SCHEMA_NAME) = UPPER(?)";
	}

	protected String buildExistsIndexSql (String schemaName, String tableName, String indexName) {
		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.INDEXES WHERE UPPER(TABLE_SCHEMA) = UPPER(?) AND UPPER(TABLE_NAME) = UPPER(?) AND UPPER(INDEX_NAME) = UPPER(?)";
	}

	protected String buildExistsTableSql (String schemaName, String tableName) {
		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE UPPER(TABLE_SCHEMA) = UPPER(?) AND UPPER(TABLE_NAME) = UPPER(?)";
	}

	protected String buildExistsSequenceSql (String schemaName, String seqName) {
		return "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SEQUENCES WHERE UPPER(SEQUENCE_SCHEMA) = UPPER(?) AND UPPER(SEQUENCE_NAME) = UPPER(?)";
	}

	protected String buildDropTableSql (String fullTableName) {
		return "DROP TABLE IF EXISTS " + _upper(fullTableName);
	}

	protected String buildCreateIndexSql (boolean unique, String fullIndexName, String fullTableName, String columnsStm) {
		return "CREATE " + (unique ? "UNIQUE" : "HASH") + " INDEX IF NOT EXISTS " + _upper(fullIndexName) + " ON " + _upper(fullTableName) + "(" + columnsStm + ")";
	}

	protected String buildDropIndexSql (String fullIndexName) {
		return "DROP INDEX IF EXISTS " + _upper(fullIndexName);
	}

	protected String buildCreateViewSql (String fullViewName, String query) {
		return "CREATE OR REPLACE FORCE VIEW " + _upper(fullViewName) + " AS " + query;
	}

	protected String buildLoadColumnsSql (String schemaName, String tableName) {
		return "SELECT COLUMN_NAME, TYPE_NAME, CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS WHERE UPPER(TABLE_SCHEMA) = UPPER(?) AND UPPER(TABLE_NAME) = UPPER(?)";
	}

	protected String buildAddColumnSql (String fullTableName, String column) {
		return "ALTER TABLE " + _upper(fullTableName) + " ADD IF NOT EXISTS " + column;
	}

}
