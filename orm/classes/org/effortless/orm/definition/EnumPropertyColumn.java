package org.effortless.orm.definition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.effortless.core.EnumUtils;
import org.effortless.core.UnusualException;
import org.effortless.orm.impl.ColumnDecoder;
import org.effortless.orm.impl.ColumnEncoder;
import org.effortless.orm.impl.SqlMapper;

public class EnumPropertyColumn extends SinglePropertyColumn {
		
		public EnumPropertyColumn () {
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
						String toSave = EnumUtils.toValue(value);
						try {
							stm.setObject(index, toSave, java.sql.Types.VARCHAR);
						} catch (SQLException e) {
							EnumPropertyColumn._throwException(e);
						}
					}
					
				};
			}
			return this.columnEncoder;
		}

		protected ColumnDecoder columnDecoder;
		
		public ColumnDecoder getColumnDecoder() {
			if (this.columnDecoder == null) {
				final Class<?> fType = (Class<?>)this.type;
				this.columnDecoder = new ColumnDecoder() {

					@Override
					public Object decode(String columnName, ResultSet rs) {
						Object result = null;
						String value = null;
						try {
							value = rs.getString(columnName);
						} catch (SQLException e) {
							EnumPropertyColumn._throwException(e);
						}
						result = EnumUtils.valueOf(fType, value);
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

//		public Class<?> getType () {
//			return String.class;
//		}
//		
		public String getDefinition() {
			String result = null;
			result = "";
			result += this.columnName.trim().toUpperCase();
			result += " ";
			result += SqlMapper.classToTypeDefinition(String.class, this.precision);
			if (this.extraType != null) {
				result += " ";
				result += loadExtraTypeDefinition();
			}
			return result;
		}

	}