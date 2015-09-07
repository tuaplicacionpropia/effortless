package org.effortless.orm;

import java.sql.ResultSet;

import org.effortless.orm.impl.ColumnDecoder;
import org.effortless.orm.impl.SqlMapper;
import org.effortless.orm.DbManager;
import org.effortless.orm.AbstractFilter;

public class NativeFilter extends AbstractFilter {

	protected NativeFilter () {
		super();
	}
	
	public NativeFilter (String table, DbManager db, String selectClausule, Class<?>... selectTypes) {
		super(table, db, selectClausule, selectTypes);
		this.columns = selectClausule.split(",");
		this.decoders = SqlMapper.classes2Decoders(selectTypes);
	}
	
	protected void initiate () {
		super.initiate();
		this.decoders = null;
		this.columns = null;
	}

	protected String[] columns;
	protected ColumnDecoder[] decoders;
	
	@Override
	protected Object[] decodeItem(ResultSet rs) {
		Object[] result = null;

		int size = this.decoders.length;
		result = new Object[size];
		for (int i = 0; i < size; i++) {
			Object data = this.decoders[i].decode(this.columns[i], rs);
//					Object data = SqlMapper.loadObject(rs, i + 1, this._selectTypes[i]);
			result[i] = data;
		}
//				Type decodeItem = decodeItem(item);
//				this._page.add(decodeItem);

//throw new java.lang.Oper		
		return result;
	}

	public Class targetClass() {
		return Object.class;
	}

}
