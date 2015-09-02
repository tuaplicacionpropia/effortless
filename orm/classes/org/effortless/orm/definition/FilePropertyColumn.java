package org.effortless.orm.definition;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.effortless.core.IOUtils;
import org.effortless.core.UnusualException;
import org.effortless.orm.impl.ColumnDecoder;
import org.effortless.orm.impl.ColumnEncoder;

public class FilePropertyColumn extends SinglePropertyColumn {
	
	public FilePropertyColumn () {
		super();
	}
	
	protected static void _throwException (Exception e) {
		throw new UnusualException(e);
	}
	
	protected ColumnEncoder columnEncoder;
	
	public ColumnEncoder getColumnEncoder() {
		if (this.columnEncoder == null) {
			this.columnEncoder = new ColumnEncoder() {

				@Override
				public void encode(Object owner, PreparedStatement stm, int index, Object value) {
					java.io.File file = (java.io.File)value;
					if (file != null && file.exists()) {
						java.io.InputStream is = null;
						try {
							is = new java.io.FileInputStream(file);
						} catch (FileNotFoundException e) {
							FilePropertyColumn._throwException(e);
						}
						long length = file.length();
						try {
							stm.setBlob(index, is, length);
						} catch (SQLException e) {
							FilePropertyColumn._throwException(e);
						}
						try {
							is.close();
						} catch (IOException e) {
							FilePropertyColumn._throwException(e);
						}
					}
					else {
						try {
							stm.setNull(index, java.sql.Types.BLOB);
						} catch (SQLException e) {
							FilePropertyColumn._throwException(e);
						}
					}
				}
				
			};
		}
		return this.columnEncoder;
	}

	protected ColumnDecoder columnDecoder;
	
	public ColumnDecoder getColumnDecoder() {
		if (this.columnDecoder == null) {
			this.columnDecoder = new ColumnDecoder() {

				@Override
				public Object decode(String columnName, ResultSet rs) {
					Object result = null;
					java.sql.Blob value = null;
					try {
						value = rs.getBlob(columnName);
					} catch (SQLException e) {
						FilePropertyColumn._throwException(e);
					}
					java.io.InputStream input = null;
					try {
						input = (value != null ? value.getBinaryStream() : null);
					} catch (SQLException e) {
						FilePropertyColumn._throwException(e);
					}
					try {
						result = (input != null ? IOUtils.toFile(input) : null);
					} catch (IOException e) {
						FilePropertyColumn._throwException(e);
					}
					return result;
				}
				
			};
		}
		return this.columnDecoder;
	}

	protected void initiate () {
		super.initiate();
		this.columnEncoder = null;
		this.columnDecoder = null;
	}

}