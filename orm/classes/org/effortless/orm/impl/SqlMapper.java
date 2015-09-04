package org.effortless.orm.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.effortless.core.UnusualException;

public class SqlMapper {

	public static int[] classes2SqlType (Class<?>[] classes) {
		int[] result = null;
		if (classes != null) {
			result = new int[classes.length];
			for (int i = 0; i < classes.length; i++) {
				result[i] = class2SqlType(classes[i]);
			}
		}
		return result;
	}
	
	public static ColumnEncoder[] classes2Encoders (Class<?>[] classes) {
		ColumnEncoder[] result = null;
		if (classes != null) {
			result = new ColumnEncoder[classes.length];
			for (int i = 0; i < classes.length; i++) {
				result[i] = classToColumnEncoder(classes[i]);
			}
		}
		return result;
	}
	
	public static ColumnDecoder[] classes2Decoders (Class<?>[] classes) {
		ColumnDecoder[] result = null;
		if (classes != null) {
			result = new ColumnDecoder[classes.length];
			for (int i = 0; i < classes.length; i++) {
				result[i] = classToColumnDecoder(classes[i]);
			}
		}
		return result;
	}
	
	public static int obj2SqlType (Object obj) {
		int result = java.sql.Types.NULL;
		if (obj == null) {
			result = java.sql.Types.NULL;
		}
		else {
			result = class2SqlType(obj.getClass());
		}
		return result;
	}
	
	protected static void _throwException (Exception e) {
		throw new UnusualException(e);
	}
	
	public static final ColumnDecoder STRING_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = rs.getString(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
			return result;
		}
		
	};
	
	public static final ColumnEncoder STRING_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
//			stm.setString(index, (String)value);
			try {
				stm.setObject(index, value, java.sql.Types.VARCHAR);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder BOOLEAN_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = (Boolean)rs.getObject(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
//			return Boolean.valueOf(rs.getBoolean(columnName));
			return result;
		}
		
	};
	
	public static final ColumnEncoder BOOLEAN_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
//			stm.setBoolean(index, (value != null ? ((Boolean)value).booleanValue() : false));
			try {
				stm.setObject(index, value, java.sql.Types.BOOLEAN);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder INTEGER_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = (Integer)rs.getObject(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
//			return Integer.valueOf(rs.getInt(columnName));
			return result;
		}
		
	};
	
	public static final ColumnEncoder INTEGER_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
			try {
				stm.setObject(index, value, java.sql.Types.INTEGER);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder DOUBLE_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = (Double)rs.getObject(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
//			return Double.valueOf(rs.getDouble(columnName));
			return result;
		}
		
	};
	
	public static final ColumnEncoder DOUBLE_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
			try {
				stm.setObject(index, value, java.sql.Types.DOUBLE);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder DATE_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = (java.sql.Date)rs.getObject(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
//			return rs.getDate(columnName);
			return result;
		}
		
	};
	
	public static final ColumnEncoder DATE_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
			try {
				stm.setObject(index, value, java.sql.Types.DATE);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder TIME_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = (java.sql.Time)rs.getObject(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
			return result;
//			return rs.getTime(columnName);
		}
		
	};
	
	public static final ColumnEncoder TIME_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
			try {
				stm.setObject(index, value, java.sql.Types.TIME);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder TIMESTAMP_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = (java.sql.Timestamp)rs.getObject(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
//			return rs.getTimestamp(columnName);
			return result;
		}
		
	};
	
	public static final ColumnEncoder TIMESTAMP_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
			try {
				stm.setObject(index, value, java.sql.Types.TIMESTAMP);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder LONG_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
			try {
				result = (Long)rs.getObject(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
			return result;
//			return Long.valueOf(rs.getLong(columnName));
		}
		
	};
	
	public static final ColumnEncoder LONG_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
			try {
				stm.setObject(index, value, java.sql.Types.BIGINT);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static final ColumnDecoder BLOB_DECODER = new ColumnDecoder () {

		public Object decode(String columnName, ResultSet rs) {
			Object result = null;
//			return rs.getObject(columnName, java.sql.Blob.class);
			try {
				result = rs.getBlob(columnName);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
			return result;
		}
		
	};
	
	public static final ColumnEncoder BLOB_ENCODER = new ColumnEncoder () {

		public void encode(Object owner, PreparedStatement stm, int index, Object value) {
			try {
				stm.setObject(index, value, java.sql.Types.BLOB);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		
	};
	
	public static Object loadObject (ResultSet rs, int index, Class<?> type) {
		Object result = null;
		if (type == null) {
			result = null;
		}
		else if (String.class.equals(type)) {
			try {
				result = rs.getString(index);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.lang.Boolean.class.equals(type)) {
			try {
				result = Boolean.valueOf(rs.getBoolean(index));
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.lang.Integer.class.equals(type)) {
			try {
				result = Integer.valueOf(rs.getInt(index));
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.lang.Double.class.equals(type)) {
			try {
				result = Double.valueOf(rs.getDouble(index));
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.sql.Date.class.equals(type)) {
			try {
				result = rs.getDate(index);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.sql.Time.class.equals(type)) {
			try {
				result = rs.getTime(index);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.sql.Timestamp.class.equals(type)) {
			try {
				result = rs.getTimestamp(index);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.util.Date.class.equals(type)) {
			try {
				result = rs.getTimestamp(index);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (Long.class.equals(type)) {
			try {
				result = Long.valueOf(rs.getLong(index));
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else if (java.io.File.class.equals(type)) {
			try {
				result = rs.getBlob(index);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		else {
			try {
				result = rs.getBlob(index);
			} catch (SQLException e) {
				SqlMapper._throwException(e);
			}
		}
		return result;
	}
	
	public static int class2SqlType (Class<?> type) {
		int result = java.sql.Types.NULL;
		if (type == null) {
			result = java.sql.Types.NULL;
		}
		else if (String.class.equals(type)) {
			result = java.sql.Types.VARCHAR;
		}
		else if (java.lang.Boolean.class.equals(type)) {
			result = java.sql.Types.BOOLEAN;
		}
		else if (java.lang.Integer.class.equals(type)) {
			result = java.sql.Types.INTEGER;
		}
		else if (java.lang.Double.class.equals(type)) {
			result = java.sql.Types.DOUBLE;
		}
		else if (java.sql.Date.class.equals(type)) {
			result = java.sql.Types.DATE;
		}
		else if (java.sql.Time.class.equals(type)) {
			result = java.sql.Types.TIME;
		}
		else if (java.sql.Timestamp.class.equals(type)) {
			result = java.sql.Types.TIMESTAMP;
		}
		else if (java.util.Date.class.equals(type)) {
			result = java.sql.Types.TIMESTAMP;
		}
		else if (Long.class.equals(type)) {
			result = java.sql.Types.BIGINT;
		}
		else if (java.io.File.class.equals(type)) {
			result = java.sql.Types.BLOB;
		}
		else {
			result = java.sql.Types.BLOB;
		}
		return result;
	}

	public static String classToTypeDefinition(Class<?> type, String precision) {
		String result = null;
		precision = (precision != null ? precision.trim() : "");
		precision = (precision.length() > 0 ? "(" + precision + ")" : precision);
		if (String.class.equals(type)) {
			result = "VARCHAR" + precision;
		}
		else if (java.lang.Boolean.class.equals(type)) {
			result = "BOOLEAN";
		}
		else if (java.lang.Integer.class.equals(type)) {
			result = "INTEGER";
		}
		else if (java.lang.Double.class.equals(type)) {
			result = "DOUBLE" + precision;
		}
		else if (java.sql.Date.class.equals(type)) {
			result = "DATE";
		}
		else if (java.sql.Time.class.equals(type)) {
			result = "TIME";
		}
		else if (java.sql.Timestamp.class.equals(type)) {
			result = "TIMESTAMP";
		}
		else if (java.util.Date.class.equals(type)) {
			result = "TIMESTAMP";
		}
		else if (Long.class.equals(type)) {
			result = "BIGINT";
		}
		else if (java.io.File.class.equals(type) || type == null) {
			result = "BLOB" + precision;
		}
		else {
			result = "BLOB" + precision;
		}
		return result;
	}

	public static ColumnDecoder classToColumnDecoder(Class<?> type) {
		ColumnDecoder result = null;
		if (String.class.equals(type)) {
			result = STRING_DECODER;
		}
		else if (java.lang.Boolean.class.equals(type)) {
			result = BOOLEAN_DECODER;
		}
		else if (java.lang.Integer.class.equals(type)) {
			result = INTEGER_DECODER;
		}
		else if (java.lang.Double.class.equals(type)) {
			result = DOUBLE_DECODER;
		}
		else if (java.sql.Date.class.equals(type)) {
			result = DATE_DECODER;
		}
		else if (java.sql.Time.class.equals(type)) {
			result = TIME_DECODER;
		}
		else if (java.sql.Timestamp.class.equals(type)) {
			result = TIMESTAMP_DECODER;
		}
		else if (java.util.Date.class.equals(type)) {
			result = TIMESTAMP_DECODER;
		}
		else if (Long.class.equals(type)) {
			result = LONG_DECODER;
		}
		else if (java.io.File.class.equals(type) || type == null) {
			result = BLOB_DECODER;
		}
		else {
			result = BLOB_DECODER;
		}
		return result;
	}
	
	public static ColumnEncoder classToColumnEncoder(Class<?> type) {
		ColumnEncoder result = null;
		if (String.class.equals(type)) {
			result = STRING_ENCODER;
		}
		else if (java.lang.Boolean.class.equals(type)) {
			result = BOOLEAN_ENCODER;
		}
		else if (java.lang.Integer.class.equals(type)) {
			result = INTEGER_ENCODER;
		}
		else if (java.lang.Double.class.equals(type)) {
			result = DOUBLE_ENCODER;
		}
		else if (java.sql.Date.class.equals(type)) {
			result = DATE_ENCODER;
		}
		else if (java.sql.Time.class.equals(type)) {
			result = TIME_ENCODER;
		}
		else if (java.sql.Timestamp.class.equals(type)) {
			result = TIMESTAMP_ENCODER;
		}
		else if (java.util.Date.class.equals(type)) {
			result = TIMESTAMP_ENCODER;
		}
		else if (Long.class.equals(type)) {
			result = LONG_ENCODER;
		}
		else if (java.io.File.class.equals(type) || type == null) {
			result = BLOB_ENCODER;
		}
		else {
			result = BLOB_ENCODER;
		}
		return result;
	}

	public static String fnUpper(String param) {
		String result = null;
		result = "upper(" + param + ")";
		return result;
	}
	
}
