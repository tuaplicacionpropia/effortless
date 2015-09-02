package org.effortless.orm;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.effortless.core.UnusualException;

public class CompleteListEnums extends Object {

	public static void complete (Class groupClass, java.util.List groupList, Class enumClass, String property, Class itemClass, java.util.List list) {
		complete(enumClass, property, itemClass, list);
		if (groupClass != null && groupList != null) {
			java.util.List groupValues = org.effortless.core.EnumUtils.loadEnumDefaultValues(groupClass);
			int length = (groupValues != null ? groupValues.size() : 0);
			for (int i = 0; i < length; i++) {
				Object groupValue = groupValues.get(i);
				if (groupValue != null) {
//					j
				}
			}
		}
	}

	public static void complete (Class enumClass, String property, Class itemClass, java.util.List list) {
		if (list != null && enumClass != null && property != null && itemClass != null) {
			java.util.List enumValues = org.effortless.core.EnumUtils.loadEnumDefaultValues(enumClass);
			int length = (enumValues != null ? enumValues.size() : 0);
			for (int i = 0; i < length; i++) {
				Object enumValue = enumValues.get(i);
				if (enumValue != null) {
					Object item = newItem(enumValue, property, itemClass);
					if (!list.contains(item)) {
						list.add(item);
					}
				}
			}
		}
	}

	public static Object newItem(Object enumValue, String property, Class itemClass) {
		Object result = null;
		if (enumValue != null && property != null && itemClass != null) {
			try {
				result = itemClass.newInstance();
			} catch (InstantiationException e) {
				throw new UnusualException(e);
			} catch (IllegalAccessException e) {
				throw new UnusualException(e);
			}
			try {
				PropertyUtils.setProperty(result, property, enumValue);
			} catch (IllegalAccessException e) {
				throw new UnusualException(e);
			} catch (InvocationTargetException e) {
				throw new UnusualException(e);
			} catch (NoSuchMethodException e) {
				throw new UnusualException(e);
			}
		}
		return result;
	}
	
	
	
}
