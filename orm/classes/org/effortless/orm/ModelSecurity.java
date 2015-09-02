package org.effortless.orm;

import org.effortless.orm.security.Resource;
import org.effortless.orm.security.SecuritySystem;

public class ModelSecurity extends Object {

	
	
	public static boolean checkSecurityAction (Object item, String actionName) {
		return checkSecurityAction((item != null ? item.getClass() : null), item, actionName);
	}
	
	public static boolean checkSecurityAction (Class<?> clazz, Object item, String actionName) {
		boolean result = true;
		SecuritySystem securitySystem = MySession.getSecuritySystem();
		if (securitySystem != null) {
			Resource resource = new Resource();
			resource.setObject(item);
			resource.setClassName(clazz.getSimpleName());
			resource.setAction(actionName);
			
			securitySystem.check(resource);
			throwException(resource);
		}
		return result;
	}
	

	
	protected static void throwException(Resource resource) {
		if (resource != null && !resource.isAllow()) {
			String title = "ERROR";//resource.getErrorTitle();
			String description = "Permisos insuficientes";//resource.getErrorDescription();
			String msg = title + description;
			throw new RuntimeException(msg);
		}
	}

//	public static boolean checkSecuritySystem (Filter filter) {
//		boolean result = true;
//		SecuritySystem securitySystem = MySession.getSecuritySystem();
//		if (securitySystem != null) {
//			Resource resource = new Resource();
//			resource.setObject(filter);
//			
//			securitySystem.check(resource);
//			throwException(resource);
//		}
//		return result;
//	}

}
