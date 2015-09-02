package org.effortless.orm.security;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;

//import org.effortless.core.I18nManager;
//import org.effortless.fastsql.MySession;

import org.effortless.orm.security.resources.NormalVisitor;
import org.effortless.orm.security.resources.RoleResourceSet;

public abstract class AbstractSecuritySystem extends Object implements SecuritySystem {

	public AbstractSecuritySystem() {
		super();
		initiate();
	}

	protected void initiate() {
		initiateEnabled();
	}
	
	protected boolean enabled;
	
	protected void initiateEnabled () {
		this.enabled = true;
	}
	
	public boolean isEnabled () {
		return this.enabled;
	}
	
	public void setEnabled (boolean newValue) {
		this.enabled = newValue;
	}
	
	public void check (Resource resource) {
		if (resource != null) {
			if (this.enabled) {
				boolean allow = false;
				List<?> allResources = loadAllResourcesSet();
				NormalVisitor visitor = new NormalVisitor();
				List<RoleResourceSet> testResourcesSet = new ArrayList<RoleResourceSet>();
				if (allResources != null) {
					int length = allResources.size();
					for (int i = 0; i < length; i++) {
						RoleResourceSet rs = (RoleResourceSet)allResources.get(i);
						if (rs != null && rs.contains(resource, visitor)) {
							testResourcesSet.add(rs);
						}
					}
				}
				if (testResourcesSet != null && testResourcesSet.size() > 0) {
					allow = true;
					for (RoleResourceSet rs : testResourcesSet) {
						if (rs != null) {
							boolean constainsRole = constainsRole(rs.getRole());
							if (!constainsRole) {
								allow = false;
//								break;
							}
							else {
								allow = true;
								break;
							}
						}
					}
				}
//				allow = true;
				resource.setAllow(Boolean.valueOf(allow));
//				if (!allow) {
//					setupErrorResource(resource);
//				}
			}
			else {
				NoneSecuritySystem.getInstance().check(resource);
			}
		}
	}

	
//	protected void setupErrorResource (Resource resource) {
//		Locale lang = getUserLocale();
//		String exLabel = I18nManager.resolve(I18nSecurity.class, lang, "error.security.inacesible.label");
//		String exDescription = I18nManager.resolve(I18nSecurity.class, lang, "error.security.inacesible.description");
//		resource.setErrorTitle(exLabel);
//		resource.setErrorDescription(exDescription);
//	}
//	
//	public static final String DEFAULT_EXCEPTION_KEY_SUFFIX = "exception.msg";
//
//	protected String doGetBaseI18n () {
//		return "i18n.security";
//	}
//	
//	protected String getUserLocaleText () {
//		String result = null;
//		Locale locale = getUserLocale();
//		result = (locale != null ? locale.toString() : null);
//		return result;
//	}
//	
//	protected Locale getUserLocale () {
//		Locale result = null;
//		result = MySession.getUserLocale();
//		return result;
//	}
	
	public abstract Object login (String loginName, String loginPassword);

	public void setupSession (Object user) {
	}
	
	protected abstract boolean constainsRole (Object role);
	
	protected abstract List loadAllResourcesSet ();
	
}
