package org.effortless.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodUtils extends Object {

	public static Class<?> getReturnType (Class<?> clazz, String methodName) {
		Class<?> result = null;
		try {
			Method method = clazz.getMethod(methodName, (Class<?>[])null);
			result = (method != null ? method.getReturnType() : null);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		}
//		Method method = org.apache.commons.beanutils.MethodUtils.getAccessibleMethod(clazz, methodName, (Class<?>)null);
		return result;
	}
	
	public static Object run (Object bean, String methodName, Object[] params) {
		Object result = null;
		try {
//			Class clazz = bean.getClass();
//			Method[] methods = clazz.getMethods();
//			result = org.apache.commons.beanutils.MethodUtils.invokeExactMethod(bean, methodName, params);
			result = org.apache.commons.beanutils.MethodUtils.invokeMethod(bean, methodName, params);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		} catch (InvocationTargetException e) {
			throw new UnusualException(e);
		}
		return result;
	}
	
	public static Object[] EMPTY_PARAMS = new Object[]{};
	
	public static Object run (Object bean, String methodName) {
		return run(bean, methodName, EMPTY_PARAMS);
	}

	public static Object runStatic (Class<?> clazz, String methodName, Object[] params) {
		Object result = null;
		try {
			result = org.apache.commons.beanutils.MethodUtils.invokeStaticMethod(clazz, methodName, params);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		} catch (InvocationTargetException e) {
			throw new UnusualException(e);
		}
		return result;
	}

	public static Method getGetterMethod (Class<?> clazz, String methodName) {
		Method result = null;
		try {
			result = clazz.getMethod(methodName, (Class<?>[])null);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		}
		return result;
	}

	public static <T extends Annotation> T getGetterAnnotation (Class<?> clazz, String methodName, Class<T> annotation) {
		T result = null;
		try {
			Method m = getGetterMethod(clazz, methodName);
			result = m.getAnnotation(annotation);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		}
		return result;
	}

}
