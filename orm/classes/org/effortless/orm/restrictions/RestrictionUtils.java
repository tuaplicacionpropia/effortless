package org.effortless.orm.restrictions;

import org.effortless.core.UnusualException;

public class RestrictionUtils {

	protected RestrictionUtils () {
	}
	
	public static void throwList (java.util.List list) {
		int sizeExceptions = list.size();
		if (sizeExceptions > 0) {
			UnusualException firstException = (UnusualException)list.get(0);
			if (sizeExceptions == 1) {
				throw firstException;
			}
			else if (sizeExceptions > 1) {
				throw firstException;
//				UnusualException e = new UnusualException();
//				throw listModelException;
			}
		}
	}
	
	public static UnusualException createFieldException (Object data, Object property, String error, String propertyName) {
		UnusualException result = null;
//		String propertyName = getPropertyName();
//		propertyName = (propertyName != null ? propertyName.trim().toLowerCase() + "." : "");
		String key = (propertyName != null ? propertyName.trim().toLowerCase() + "." : "") + error;
		String msg = "ERROR: propiedad no válida";//I18nManager.resolve(key, new Object[] {data, property});
		UnusualException e = new UnusualException(msg);
		e.setAttribute("type", "FieldException");
		e.setAttribute("data", data);
		e.setAttribute("propertyName", propertyName);
		e.setAttribute("propertyValue", property);
		result = e;
		return e;
	}

	public static void throwFieldException (Object data, Object property, String error, String propertyName) {
		UnusualException e = createFieldException(data, property, error, propertyName);
		throw e;
	}
	
	
	
}
