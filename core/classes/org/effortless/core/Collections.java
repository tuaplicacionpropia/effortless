package org.effortless.core;

import java.util.ArrayList;
import java.util.List;

public class Collections {

	public static void addAll (List target, List toAdd) {
		addAll(target, toAdd, false);
	}
	
	public static void addAll (List target, List toAdd, boolean allowRepeat) {
		if (toAdd != null && target != null) {
			for (Object item : toAdd) {
				if (item != null && (allowRepeat || !target.contains(item))) {
					target.add(item);
				}
			}
		}
	}

	public static List<String> asList (String... array) {
		List<String> result = null;
		if (array != null) {
			result = new ArrayList<String>();
			int length = array.length;
			for (int i = 0; i < length; i++) {
				result.add(array[i]);
			}
		}
		return result;
	}

//	public static List<Object> asList (Object... array) {
//		List<Object> result = null;
//		if (array != null) {
//			result = new ArrayList<Object>();
//			int length = array.length;
//			for (int i = 0; i < length; i++) {
//				result.add(array[i]);
//			}
//		}
//		return result;
//	}
//
	
	public static <T> List<T> asList (T item) {
		List<T> result = null;
		if (item != null) {
			result = new ArrayList<T>();
			result.add(item);
		}
		return result;
	}

	public static <T> List<T> asList (T... array) {
		List<T> result = null;
		if (array != null) {
			result = new ArrayList<T>();
			int length = array.length;
			for (int i = 0; i < length; i++) {
				result.add(array[i]);
			}
		}
		return result;
	}

	public static Object[] asArray (Object item) {
		Object[] result = null;
		if (item != null) {
			result = new Object[1];
			result[1] = item;
		}
		return result;
	}

	public static Object[] asArray (Object... array) {
		Object[] result = null;
		int length = (array != null ? array.length : 0);
		if (length > 0) {
			result = new Object[length];
			for (int i = 0; i < length; i++) {
				result[i] = array[i];
			}
		}
		return result;
	}

	public static boolean like (String[] values, String value) {
		return Collections.like(values, value, true);
	}
	
	public static boolean contains (String[] values, String value) {
		return Collections.contains(values, value, true);
	}
	
	public static boolean like (String[] values, String value, boolean insensitive) {
		boolean result = false;
		value = (value != null && insensitive ? value.trim().toLowerCase() : value);
		int length = (values != null ? values.length : 0);
		for (int i = 0; i < length; i++) {
			String item = values[i];
			item = (item != null && insensitive ? item.trim().toLowerCase() : item);
			if ((item == null && value == null) || (value != null && value.contains(item))) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static boolean contains (String[] values, String value, boolean insensitive) {
		boolean result = false;
		value = (value != null && insensitive ? value.trim().toLowerCase() : value);
		int length = (values != null ? values.length : 0);
		for (int i = 0; i < length; i++) {
			String item = values[i];
			item = (item != null && insensitive ? item.trim().toLowerCase() : value);
			if ((item == null && value == null) || (value != null && value.equals(item))) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static <T extends Object> List<T> copyList(Class<T> clazz, List<T> list) {
		List<T> result = null;
		if (list != null) {
			result = new java.util.ArrayList<T>();
			result.addAll(list);
		}
		return result;
	}
	
	public static List<Object> copyList(List<Object> list) {
		List<Object> result = null;
		if (list != null) {
			result = new java.util.ArrayList<Object>();
			result.addAll(list);
		}
		return result;
	}
	
	public static java.util.Map toMap (java.util.List list) {
		return toMap(list, null);
	}
	
	public static java.util.Map toMap (java.util.List list, String propertyName) {
		java.util.Map result = null;
		result = new java.util.HashMap();
		int length = (list != null ? list.size() : 0);
		for (int i = 0; i < length; i++) {
			Object item = list.get(i);
			item = (item != null && propertyName != null ? PropertyUtils.getProperty(item, propertyName) : item);
			if (item != null) {
				result.put(item, Boolean.TRUE);
			}
		}
		return result;
	}
	
	public static Object[] add (Object[] array, Object element) {
		Object[] result = null;
		int length = (array != null ? array.length : 0);
		result = new Object[length + 1];
		System.arraycopy(array, 0, result, 0, length);
		result[length] = element;
		return result;
	}
	
	public static String[] add (String[] array, String element) {
		String[] result = null;
		int length = (array != null ? array.length : 0);
		result = new String[length + 1];
		System.arraycopy(array, 0, result, 0, length);
		result[length] = element;
		return result;
	}
	
}
