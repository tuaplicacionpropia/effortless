package org.effortless.jast;

import java.util.List;

public interface GApp extends GNode {

	public String getRootFile ();
	
	public void setRootFile (String newValue);

	public GUnit getRootUnit();
	
	public void setRootUnit(GUnit newValue);

	public List getUnits();

	public GApp removeUnit(GUnit unit);
	
	public String getId();

	public String getBase();

	public String getName ();
	
	public void setName (String newValue);
	
	public String getAlias ();

	
	public List getClasses ();

	public GClass getClassByNameWithoutPackage (String classNameWithoutPackage);
	
	public GClass getClassByName (String className);
	
	public void addClass (GClass clazz);

	public void removeClass (GClass clazz);

	public List getModules ();

	public List getOptions (String module);
	
	public GClass getLogClass ();
	
	public void setLogClass (GClass newValue);

	public GClass getFileClass ();
	
	public void setFileClass (GClass newValue);

	public GUnit getSourceUnit ();
	
	public List getUnitNames ();

	public GUnit getGUnit (String unitName);
	
	public List getClassesByUnit (String unitName);	
//	protected static final ClassNode ENTITY_CLASS = ClassNodeHelper.toClassNode(Entity.class);
	
	public List getEntitiesByUnit (String unitName);
	
	public boolean containsUnit (GUnit unit);

	public GClass getSettingsClass();
	
	public void setSettingsClass(GClass newValue);

	public GClass newClass(String name);

	public GClass getUserProfileClass();

	public void setUserProfileClass(GClass newValue);

	public GClass getUserClass();

	public void setUserClass(GClass newValue);

	public String getClassNames();

	public String getClassNamesByType(Class type);

	//"org.effortless.sampleapp"
	public String getPackageName();

	public GClass getSecuritySystemClass();

	public void setSecuritySystemClass(GClass newValue);

	public GClass getEnumPermissionClass();

	public void setEnumPermissionClass(GClass newValue);

	public GClass getUserProfilePermissionClass();

	public void setUserProfilePermissionClass(GClass newValue);

	public GClass newEnumClass(String name);

	public void addUnit(GUnit rootUnit);

	public GUnit addUnit(String packageName, String className);

	public Object getClassAttribute(String className, String nameAttr);

	public Object setClassAttribute(String className, String nameAttr, Object valueAttr);
	
}
