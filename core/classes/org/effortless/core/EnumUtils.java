package org.effortless.core;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class EnumUtils extends Object {

	public static List<?> loadEnumDefaultValues (Class<?> type) {
		List<?> result = null;
		if (type != null) {
			if (org.effortless.core.EnumString.class.isAssignableFrom(type)) {
				result = coreLoadEnumDefaultValues((Class<EnumString>)type);
			}
			else if (java.lang.Enum.class.isAssignableFrom(type)) {
				result = nativeLoadEnumDefaultValues((Class<Enum>)type);
			}
		}
		return result;
	}

	public static List<?> coreLoadEnumDefaultValues (Class<EnumString> enumType) {
		List<?> result = null;
		if (enumType != null) {
			result = (List)MethodUtils.runStatic(enumType, "values", null);
		}
		return result;
	}
	
	public static List<?> nativeLoadEnumDefaultValues (Class<Enum> enumType) {
		List<?> result = null;
		EnumSet values = (enumType != null ? EnumSet.allOf(enumType) : null);
		if (values != null) {
			result = new ArrayList();
			result.addAll(values);
		}
		return result;
	}

	public static Object valueOf(Class<?> fType, String value) {
		Object result = null;
		if (value != null) {
			Class<EnumString> enumType = null; try { enumType = (Class<EnumString>)fType; } catch (Throwable t) {}
			result = (enumType != null ? MethodUtils.runStatic(enumType, "valueOf", new Object[] {value}) : null);
			if (enumType == null) {
				result = (value != null ? Enum.valueOf((Class<Enum>)fType, value) : null);
			}
		}
		return result;
	}

	public static String toValue(Object value) {
		String result = null;
		if (value != null) {
			EnumString enumValue = null; try { enumValue = (EnumString)value; } catch (Throwable t) {}
			result = (enumValue != null ? enumValue.name() : null);
			if (enumValue == null) {
				result = value.toString();
			}
		}
		return result;
	}
	
}
