package org.effortless.core;

public class ObjectUtils extends Object {

	protected ObjectUtils () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public static boolean equals(Object value1, Object value2) {
		boolean result = false;
		result = (value1 == value2 || (value1 != null && value2 != null && value1.equals(value2)));
		return result;
	}

}
