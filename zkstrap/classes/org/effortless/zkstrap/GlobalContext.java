package org.effortless.zkstrap;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class GlobalContext extends Object {

	public static final String SERVLET_CONTEXT_ATTR = "javax.servlet.ServletContext";
	public static final String SECURITY_ROLES = "SECURITY_ROLES";
	
	public static final String ROOT_CONTEXT = "ROOT_CONTEXT";
	public static final String HTTP_REQUEST = "HTTP_REQUEST";
	public static final String HTTP_RESPONSE = "HTTP_RESPONSE";
	public static final String HTTP_SESSION = "HTTP_SESSION";
	public static final String APP_ID = "APP_ID";
	public static final String APP_REALID = "APP_REALID";
	public static final String COMPANY_ID = "COMPANY_ID";
	public static final String DB_ID = "DB_ID";
	public static final String CURRENT_USER = "CURRENT_USER";
	
	protected static final ThreadLocal<Map<String, Object>> tl = new ThreadLocal<Map<String, Object>>();

	public static final String I18N = "I18N";

	public static final String SECURITY_CONTEXT = "SECURITY_CONTEXT";

	public static final String DEFAULT_PAGE_SIZE = "DEFAULT_PAGE_SIZE";
	public static final String DBA = "DBA";

	
	public static Session getSession () {
		return Sessions.getCurrent();
	}

	public static void initSession() {
		Session session = getSession();
		if (session != null) {
			Map<String, Object> map = get();
			Set<String> keys = (map != null ? map.keySet() : null);
			if (keys != null) {
				for (String key : keys) {
					Object value = map.get(key);
					session.setAttribute(key, value);
				}
			}
		}
	}

	
	
	public static void set (String key, Object value) {
		if (key != null) {
			Session session = getSession();
			if (session != null) {
				session.setAttribute(key, value);
			}
			else {
				getNotNull().put(key, value);
			}
		}
	}
	
	public static Object remove (String key) {
		Object result = null;
		if (key != null) {
			Session session = getSession();
			if (session != null) {
				session.removeAttribute(key);
			}
			Map<String, Object> map = get();
			result = (map != null ? map.remove(key) : null);
		}
		return result;
	}

	public static Object get (String key) {
		return get(key, Object.class, null);
	}

	public static <T extends Object> T get (String key, Class<T> clazz) {
		return get(key, clazz, null);
	}

	public static <T extends Object> T get (String key, Class<T> clazz, T defaultValue) {
		T result = defaultValue;
		if (key != null) {
			Session session = getSession();
			if (session != null) {
				Object value = session.getAttribute(key);
				result = clazz.cast(value);
			}
			else {
				Map<String, Object> map = get();
				result = (map != null ? clazz.cast(map.get(key)) : defaultValue);
			}
		}
		return result;
	}

	public static void clear() {
		unset();
	}
	
	public static void unset() {
		Session session = getSession();
		if (session != null) {
			session.invalidate();
		}
		tl.remove();
	}

	protected static void set(Map<String, Object> attributes) {
		tl.set(attributes);
	}
	
	protected static Map<String, Object> get() {
//		println "LOAD MAP THREADLOCAL " + tl.get()
		return tl.get();
	}
	
	protected static Map<String, Object> getNotNull() {
		Map<String, Object> result = null;
		result = get();
		if (result == null) {
			result = new HashMap<String, Object>();
			set(result);
		}
		return result;
	}

//	public static final String SERVLET_CONTEXT_ATTR = "javax.servlet.ServletContext";
//
//	public static javax.servlet.ServletContext getServletContext () {
//		return get(SERVLET_CONTEXT_ATTR, javax.servlet.ServletContext.class, null);
//	}
//	
//	public static void setServletContext (javax.servlet.ServletContext newValue) {
//		set(SERVLET_CONTEXT_ATTR, newValue);
//	}
//	
//	public static String getRootContext () {
//		String result = null;
////		javax.servlet.ServletContext servletContext = getServletContext()
////		result = servletContext.getRealPath("/");
//		result = get("root_context", String.class, null);
//		result = (result != null ? result : getDefaultRootContext());
//		return result;
//	}
//	
//	public static String getDefaultRootContext () {
//		String result = null;
//		result = System.getProperty("user.dir");
//		result = (result != null ? result : System.getProperty("user.home"));
//		return result;
//	}
//	
//	public static void setRootContext (String newValue) {
//		set("root_context", newValue);
//	}
//	
//	
//	
//	
//	
//	public static interface CodeRunnable {
//		
//		public Object run () throws Exception;
//		
//	}
	
	
	public static <Type> Map<String, Type> getCache (Class<Type> type, String key) {
		Map<String, Type> result = null;
		Map<String, Object> map = get();
		Session session = getSession();
		if (session != null) {
			result = (Map<String, Type>)session.getAttribute(key);
			if (result == null) {
				result = new HashMap<String, Type>();
				session.setAttribute(key, result);
			}
		}
		else {
			result = get(key, Map.class, null);
			if (result == null) {
				result = new HashMap<String, Type>();
				set(key, result);
			}
		}
		return result;
	}

//	public static String i18n(Class<I18nSecurity> clazz, Locale lang,
//			String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public static String i18n(Class<I18nSecurity> clazz, Locale lang,
//			String key, Object[] params) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public static String i18n(String string) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public static String getLogLocationKeyFrom() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public static String getLogLocationAliasFrom() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public static String getLogLocationDescriptionFrom() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public static I18nProperties getI18n (String appId) {
//		I18nProperties result = null;
////		String appId = GlobalContext.get(GlobalContext.APP_ID, String.class);//WebUtils.appId(appName, ServerContext.getRootContext());
//		Map<String, I18nProperties> container = GlobalContext.getCache(I18nProperties.class, "i18n_container");
//		I18nProperties i18n = container.get(appId);
//		i18n = (i18n != null ? i18n : I18nProperties.dev(appId));
//		container.put(appId, i18n);
//		return result;
//	}
//	
//	public static String i18n (String appId, String key) {
//		String result = null;
//		I18nProperties i18n = getI18n(appId);
//		result = i18n.getKey(key, true);
//		return result;
//	}
//
//	public static I18nProperties getI18n () {
//		I18nProperties result = null;
//		String appId = GlobalContext.get(GlobalContext.APP_ID, String.class);//WebUtils.appId(appName, ServerContext.getRootContext());
//		result = getI18n(appId);
//		return result;
//	}
//	
//	public static String i18n (String key) {
//		String result = null;
//		I18nProperties i18n = getI18n();
//		result = i18n.getKey(key, true);
//		return result;
//	}
	
	public static java.util.Map getRoles () {
		return GlobalContext.get(GlobalContext.SECURITY_ROLES, java.util.Map.class, null);
	}
	
	public static void setRoles (java.util.Map newValue) {
		GlobalContext.set(GlobalContext.SECURITY_ROLES, newValue);
	}
	
	public static javax.servlet.ServletContext getServletContext () {
		return GlobalContext.get(GlobalContext.SERVLET_CONTEXT_ATTR, javax.servlet.ServletContext.class, null);
	}
	
	public static void setServletContext (javax.servlet.ServletContext newValue) {
		GlobalContext.set(GlobalContext.SERVLET_CONTEXT_ATTR, newValue);
	}
	
	public static HttpServletRequest getHttpRequest () {
		return GlobalContext.get(GlobalContext.HTTP_REQUEST, HttpServletRequest.class, null);
	}
	
	public static void setHttpRequest (HttpServletRequest newValue) {
		GlobalContext.set(GlobalContext.HTTP_REQUEST, newValue);
	}
	
	public static HttpServletResponse getHttpResponse () {
		return GlobalContext.get(GlobalContext.HTTP_RESPONSE, HttpServletResponse.class, null);
	}
	
	public static void setHttpResponse (HttpServletResponse newValue) {
		GlobalContext.set(GlobalContext.HTTP_RESPONSE, newValue);
	}
	
	public static Session getHttpSession () {
		return GlobalContext.get(GlobalContext.HTTP_SESSION, Session.class, null);
	}
	
	public static void setHttpSession (Session newValue) {
		GlobalContext.set(GlobalContext.HTTP_SESSION, newValue);
	}
	
	public static String getRootContext () {
		String result = null;
//		javax.servlet.ServletContext servletContext = getServletContext()
//		result = servletContext.getRealPath("/");
		result = GlobalContext.get(GlobalContext.ROOT_CONTEXT, String.class, null);
		result = (result != null ? result : getDefaultRootContext());
		return result;
	}
	
	public static String getDefaultRootContext () {
		String result = null;
		result = System.getProperty("user.dir");
		result = (result != null ? result : System.getProperty("user.home"));
		return result;
	}
	
	public static void setRootContext (String newValue) {
		GlobalContext.set(GlobalContext.ROOT_CONTEXT, newValue);
	}
	
//	public static final String APPLICATIONS = "APPLICATIONS";
//
//	public static void addApplication(String absolutePath) {
//		Map<String, String> cache = GlobalContext.getCache(String.class, GlobalContext.APPLICATIONS);
//		String appName = absolutePath;
//		cache.put(appName, absolutePath);
//	}
//	
//	public static Map<String, String> getApplications () {
//		return GlobalContext.getCache(String.class, GlobalContext.APPLICATIONS);
//	}

}
