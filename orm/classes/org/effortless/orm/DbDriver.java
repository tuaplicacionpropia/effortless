package org.effortless.orm;

public interface DbDriver {

	public String loadDriverClass();

	public void backup(DbManager db);

	public void restore(DbManager db);

	
	public String buildCreateUserSql (String login, String pass);

	public String buildDropUserSql (String login);

	public String buildCreateSchemaSql (String schemaName, String user);

	public String buildDropSchemaSql (String schemaName);

	public String buildCreateSequenceSql (String fullSeqName);

	public String buildDropSequenceSql (String fullSeqName);
	
	public String buildSelectSequenceSql (String fullSeqName);

	public String buildCreateTableSql (String fullTableName, String columnsStm);

	public String buildExistsSchemaSql (String schemaName);

	public String buildExistsIndexSql (String tableName, String indexName, String schemaName);

	public String buildExistsTableSql (String tableName, String schemaName);

	public String buildExistsSequenceSql (String seqName, String schemaName);

	public String buildDropTableSql (String fullTableName);

	public String buildCreateIndexSql (boolean unique, String fullIndexName, String fullTableName, String columnsStm);

	public String buildDropIndexSql (String fullIndexName);

	public String buildCreateViewSql (String fullViewName, String query);
	
	public String buildLoadColumnsSql (String tableName, String schemaName);

	public String buildAddColumnSql (String fullTableName, String column);

	public String[] buildAddConstraintsColumnSql (String fullTableName, String column, String constraints);
	
}
