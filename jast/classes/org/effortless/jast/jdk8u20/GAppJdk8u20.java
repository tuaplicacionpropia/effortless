package org.effortless.jast.jdk8u20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.effortless.core.Collections;
import org.effortless.core.FilenameUtils;
import org.effortless.jast.jdk8u20.util.Factory;

import com.sun.tools.javac.util.Context;

import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GUnit;

public class GAppJdk8u20 extends GNodeJdk8u20 implements GApp {

	public GAppJdk8u20 () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		this.attributes = null;
	}
	
	protected java.util.Map attributes;

	public java.util.Map getAttributes () {
		return this.attributes;
	}
	
	public void setAttributes (java.util.Map newValue) {
		this.attributes = newValue;
	}
	
	public Object getAttribute (String name) {
		Object result = null;
		result = (this.attributes != null ? this.attributes.get(name) : null);
		return result;
	}
	
	public Object setAttribute (String name, Object value) {
		Object result = null;
		this.attributes = (this.attributes != null ? this.attributes : new java.util.HashMap());
		result = this.attributes.put(name, value);
		return result;
	}
	
	
	protected String rootFile;
	
	public String getRootFile () {
		return this.rootFile;
	}
	
	public void setRootFile (String newValue) {
		this.rootFile = newValue;
	}

	public String getId() {
		String result = null;
		String pkg = this.getPackageName();
		int idx = pkg.lastIndexOf(".");
		result = pkg.substring(idx + 1);
		return result;
	}
	
	protected String base;
	
	public String getBase() {
		return this.base;
	}

	public void setBase(String newValue) {
		this.base = newValue;
	}
	
	protected GUnit rootUnit;
	
	public GUnit getRootUnit() {
		return this.rootUnit;
	}

	public void setRootUnit(GUnit newValue) {
		this.rootUnit = newValue;
		if (this.rootUnit != null) {
			this.rootUnit.setApp(this);
		}
	}

	public GApp getApp () {
		return this;
	}
	
	protected List units;
	
	public List getUnits() {
		return this.units;
	}

	public void setUnits (List newValue) {
		this.units = newValue;
	}
	
	public void addUnit (GUnit unit) {
		this.units = (this.units != null ? this.units : new java.util.ArrayList<GUnit>());
		this.units.add(unit);
		unit.setApp(this);
	}
	
	public GUnit addUnit (String packageName, String className) {
		GUnit result = null;
		GUnit unit = Factory.addUnit(this, packageName, className);
		addUnit(unit);
		result = unit;
		return result;
	}
	
	public GApp removeUnit(GUnit unit) {
		if (unit != null && this.units != null) {
			int length = this.units.size();
			for (int i = 0; i < length; i++) {
				GUnit item = (GUnit)this.units.get(i);
				if (item != null && item == unit) {
					this.units.remove(i);
					break;
				}
			}
		}
		return this;
	}
	

	protected Context ctx;
	
	public Context getCtx () {
		return this.ctx;
	}
	
	public void setCtx(Context newValue) {
		this.ctx = newValue;
	}
	
	protected Map classAttributes;
	
	public Object getClassAttribute(String className, String nameAttr) {
		Object result = null;
		if (this.classAttributes != null && nameAttr != null) {
			Map attributes = (Map)this.classAttributes.get(className);
			result = (attributes != null ? attributes.get(nameAttr) : null);
		}
		return result;
	}

	public Object setClassAttribute(String className, String nameAttr, Object valueAttr) {
		Object result = null;
		if (className != null && nameAttr != null) {
			this.classAttributes = (this.classAttributes != null ? this.classAttributes : new HashMap());
			Map attributes = (Map)this.classAttributes.get(className);
			attributes = (attributes != null ? attributes : new HashMap());
			result = attributes.put(nameAttr, valueAttr);
			this.classAttributes.put(className, attributes);
		}
		return result;
	}
	

	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getClasses() {
		List result = null;
		result = new ArrayList();
		List units = getUnits();
		int unitsLength = (units != null ? units.size() : 0);
		for (int i = 0; i < unitsLength; i++) {
			GUnit unit = (GUnit)units.get(i);
			List unitClasses = unit.getClasses();
			if (unitClasses != null) {
				result.addAll(unitClasses);
			}
		}
if (false) {
		GUnit rootUnit = this.getRootUnit();
		List unitClasses = (rootUnit != null ? rootUnit.getClasses() : null);
		if (unitClasses != null) {
			result.addAll(unitClasses);
		}
}
		return result;
	}

	@Override
	public GClass getClassByNameWithoutPackage(String className) {
		GClass result = null;
		if (className != null) {
			List classes = getClasses();
			int length = (classes != null ? classes.size() : 0);
			for (int i = 0; i < length; i++) {
				GClass clazz = (GClass)classes.get(i);
				if (className.equals(clazz.getNameWithoutPackage())) {
					result = clazz;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public GClass getClassByName(String className) {
		GClass result = null;
		if (className != null) {
			List classes = getClasses();
			int length = (classes != null ? classes.size() : 0);
			for (int i = 0; i < length; i++) {
				GClass clazz = (GClass)classes.get(i);
				if (className.equals(clazz.getFullName()) || className.equals(clazz.getName())) {
					result = clazz;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void addClass(GClass clazz) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeClass(GClass clazz) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getModules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getOptions(String module) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GClass getLogClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogClass(GClass newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GClass getFileClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFileClass(GClass newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GUnit getSourceUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getUnitNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUnit getGUnit(String unitName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getClassesByUnit(String unitName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getEntitiesByUnit(String unitName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsUnit(GUnit unit) {
		// TODO Auto-generated method stub
		return false;
	}

	protected GClass settingsClass;
	
	@Override
	public GClass getSettingsClass() {
		return this.settingsClass;
	}

	@Override
	public void setSettingsClass(GClass newValue) {
		this.settingsClass = newValue;
	}

	@Override
	public GClass newClass(String name) {
		return Factory.newClass(this, name);
	}

	protected GClass userProfileClass;
	
	@Override
	public GClass getUserProfileClass() {
		return this.userProfileClass;
	}

	@Override
	public void setUserProfileClass(GClass newValue) {
		this.userProfileClass = newValue;
	}

	protected GClass userClass;
	
	@Override
	public GClass getUserClass() {
		return this.userClass;
	}

	@Override
	public void setUserClass(GClass newValue) {
		this.userClass = newValue;
	}

	@Override
	public String getClassNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClassNamesByType(Class type) {
		String result = null;
		java.util.List units = this.getUnits();
		int length = (units != null ? units.size() : 0);
		result = "";
		for (int i = 0; i < length; i++) {
			GUnit unit = (GUnit)units.get(i);
			GClass mainClass = (unit != null ? unit.getMainClass() : null);
			if (mainClass != null && mainClass.isType(type)) {
				String name = mainClass.getNameWithoutPackage();
				name = (name != null ? name.trim() : "");
				result += (result.length() > 0 && name.length() > 0 ? "," : "");
				result += name;
			}
		}
		
		result = (result != null ? result.trim() : "");
		result = (result.length() > 0 ? result : null);
		return result;
	}

	@Override
	public String getPackageName() {
		String result = null;
		result = FilenameUtils.getBaseName(this.rootFile);
		result = result.toLowerCase();
		return result;
	}

	protected GClass securitySystemClass;
	
	@Override
	public GClass getSecuritySystemClass() {
		return this.securitySystemClass;
	}

	@Override
	public void setSecuritySystemClass(GClass newValue) {
		this.securitySystemClass = newValue;
	}
	
	protected GClass enumPermissionClass;

	@Override
	public GClass getEnumPermissionClass() {
		return this.enumPermissionClass;
	}

	@Override
	public void setEnumPermissionClass(GClass newValue) {
		this.enumPermissionClass = newValue;
	}

	protected GClass userProfilePermissionClass;
	
	@Override
	public GClass getUserProfilePermissionClass() {
		return this.userProfilePermissionClass;
	}

	@Override
	public void setUserProfilePermissionClass(GClass newValue) {
		this.userProfilePermissionClass = newValue;
	}

	@Override
	public GClass newEnumClass(String name) {
		return Factory.newEnumClass(this, name);
	}

}
