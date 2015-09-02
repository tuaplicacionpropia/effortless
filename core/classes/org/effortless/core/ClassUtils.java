package org.effortless.core;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassUtils extends Object {

	protected ClassUtils () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}

	public static Class<?> loadClass (String className) throws ClassNotFoundException {
		Class<?> result = null;
		if (className != null) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			result = (cl != null ? cl.loadClass(className) : null);
		}
		return result;
	}
	
	public static Class<?> tryLoadClass (String className) {
		Class<?> result = null;
		try {
			result = loadClass(className);
		} catch (ClassNotFoundException e) {
		}
		return result;
	}
	
	public static Class<?> loadClassRE (String className) {
		Class<?> result = null;
		try {
			result = loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new UnusualException(e);
		}
		return result;
	}
	
	public static Object newInstance (String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Object result = null;
		Class<?> type = loadClass(className);
		result = (type != null ? type.newInstance() : null);
		return result;
	}
	
	public static Object tryNewInstance (String className) {
		Object result = null;
		try {
			result = newInstance(className);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return result;
	}
	
	public static Object newInstanceRE (String className) {
		Object result = null;
		try {
			result = newInstance(className);
		} catch (ClassNotFoundException e) {
			throw new UnusualException(e);
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		}
		return result;
	}
	
	public static Object newInstance (Class<?> type) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Object result = null;
		result = (type != null ? type.newInstance() : null);
		return result;
	}
	
	public static Object newInstance (Class<?> type, Long param) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object result = null;
		result = (type != null ? type.getConstructor(Long.class).newInstance(param) : null);
		return result;
	}
	
	public static Object newInstance (String className, Long param) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Object result = null;
		Class<?> type = loadClass(className);
		result = newInstance(type, param);
		return result;
	}
	
	public static Object newInstance (Class<?> type, Serializable param) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object result = null;
		result = (type != null ? type.getConstructor(Serializable.class).newInstance(param) : null);
		return result;
	}
	
	public static Object newInstance (String className, Serializable param) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Object result = null;
		Class<?> type = loadClass(className);
		result = newInstance(type, param);
		return result;
	}
	
	public static Object newInstanceRE (Class<?> type, Long param) {
		Object result = null;
		try {
			result = newInstance(type, param);
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		} catch (IllegalArgumentException e) {
			throw new UnusualException(e);
		} catch (InvocationTargetException e) {
			throw new UnusualException(e);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		}
		return result;
	}
	
	public static Object newInstanceRE (String className, Long param) {
		Object result = null;
		try {
			result = newInstance(className, param);
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		} catch (IllegalArgumentException e) {
			throw new UnusualException(e);
		} catch (InvocationTargetException e) {
			throw new UnusualException(e);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		} catch (ClassNotFoundException e) {
			throw new UnusualException(e);
		}
		return result;
	}
	
	public static Object newInstanceRE (Class<?> type, Serializable param) {
		Object result = null;
		try {
			result = newInstance(type, param);
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		} catch (IllegalArgumentException e) {
			throw new UnusualException(e);
		} catch (InvocationTargetException e) {
			throw new UnusualException(e);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		}
		return result;
	}
	
	public static Object newInstanceRE (String className, Serializable param) {
		Object result = null;
		try {
			result = newInstance(className, param);
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		} catch (IllegalArgumentException e) {
			throw new UnusualException(e);
		} catch (InvocationTargetException e) {
			throw new UnusualException(e);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		} catch (ClassNotFoundException e) {
			throw new UnusualException(e);
		}
		return result;
	}
	
	public static Object tryNewInstance (Class<?> type) {
		Object result = null;
		try {
			result = newInstance(type);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return result;
	}
	
	public static Object newInstanceRE (Class<?> type) {
		Object result = null;
		try {
			result = newInstance(type);
		} catch (ClassNotFoundException e) {
			throw new UnusualException(e);
		} catch (InstantiationException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    public static Class<?> getClass(Type type) {
    	Class<?> result = null;
        if (type instanceof Class) {
            result = (Class) type;
        } 
        else if (type instanceof ParameterizedType) {
            result = getClass(((ParameterizedType) type).getRawType());
        } 
        else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                result = Array.newInstance(componentClass, 0).getClass();
            } 
            else {
                result = null;
            }
        } 
        else {
            result = null;
        }
        return result;
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic
     * base class.
     *
     * @param baseClass the base class
     * @param childClass the child class
     * @return a list of the raw classes for the actual type arguments.
     */
    public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
        // resolve types by chasing down type variables.
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }
    
    public static String getName (Class<?> type) {
    	String result = null;
    	result = (type != null ? type.getName() : null);
    	if (result != null && result.startsWith("[L")) {
    		result = type.getCanonicalName();
    	}
    	return result;
    }
    
    public static Object runStaticMethod (String className, String methodName, Class[] parameterTypes, Object[] parameters) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Object result = null;
		Class<?> type = loadClass(className);
//		Method method = type.getMethod(methodName, parameterTypes);
		Method method = null;
		
		Method[] methods = type.getMethods();
		int length = (methods != null ? methods.length : 0);
		for (int i = 0; i < length; i++) {
			Method item = methods[i];
			String itemName = (item != null ? item.getName() : null);
			if (methodName.equals(itemName)) {
				method = item;
				break;
			}
		}
		
		result = method.invoke(null, parameters);
		return result;
    }

    public static Object runStaticMethodRE (String className, String methodName, Class[] parameterTypes, Object[] parameters) {
    	Object result = null;
		try {
			result = runStaticMethod(className, methodName, parameterTypes, parameters);
		} catch (ClassNotFoundException e) {
			throw new UnusualException(e);
		} catch (NoSuchMethodException e) {
			throw new UnusualException(e);
		} catch (SecurityException e) {
			throw new UnusualException(e);
		} catch (IllegalAccessException e) {
			throw new UnusualException(e);
		} catch (IllegalArgumentException e) {
			throw new UnusualException(e);
		} catch (InvocationTargetException e) {
			throw new UnusualException(e);
		}
		return result;
    }

    public static Object tryRunStaticMethod (String className, String methodName, Class[] parameterTypes, Object[] parameters) {
    	Object result = null;
		try {
			result = runStaticMethod(className, methodName, parameterTypes, parameters);
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		return result;
    }

}
