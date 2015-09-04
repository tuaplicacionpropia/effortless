package org.effortless.core;

public class StringUtils {

	protected StringUtils() {
		super();
	}
	
	public static String emptyNotAllow (String value) {
		String result = null;
		result = value;
		result = (result != null ? result.trim() : "");
		result = (result.length() > 0 ? result : null);
		return result;
	}
	
	public static String nullNotAllow (String value) {
		String result = null;
		result = value;
		result = (result != null ? result.trim() : "");
		return result;
	}
	
}
