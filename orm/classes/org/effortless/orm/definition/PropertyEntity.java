package org.effortless.orm.definition;

//import org.effortless.fastsql.impl.ColumnDecoder;
//import org.effortless.fastsql.impl.ColumnEncoder;

public interface PropertyEntity {

	public String getLoaderName();

	public String getPropertyName();
	
	public Object getAttribute (String name);
	
	public void setAttribute (String name, Object value);
	
}
