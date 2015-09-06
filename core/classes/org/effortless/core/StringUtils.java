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
	
	public static String concat (String value1, String value2, String separator) {
		String result = null;
		value1 = (value1 != null ? value1.trim() : "");
		value2 = (value2 != null ? value2.trim() : "");
		result = value1 + (value1.length() > 0 && value2.length() > 0 ? separator : "") + value2;
		return result;
	}
	
}
