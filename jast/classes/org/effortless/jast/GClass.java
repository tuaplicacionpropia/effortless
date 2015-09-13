package org.effortless.jast;

import java.util.List;

public interface GClass extends GCode {

//	public static GClass createEnum (String name, SourceUnit sourceUnit);
//	
//	public static GClass createGroupEnum (String name, SourceUnit sourceUnit);
	
	public GClass addEnumCte (String name);

	public GClass addEnumCte (String name, Expression parent);
	
	public GClass addEnumCte (String name, String parent, java.util.List children);

	public GApp getApplication ();
	
	public void setApplication (GApp newValue);
	
	public void detach ();
	
	public GField addField (Class<?> type, String name);

	public GField addField (GClass type, String name);
	
	public GField addField (String type, String name);

	public GField addField (Class<?> type, String name, Expression initialValue);
	
	public GField addField (GClass type, String name, Expression initialValue);
	
	public GField addField (String type, String name, Expression initialValue);

	public GField addField (GField field);

	public GClass removeField (String name);

	public GClass removeField (GField field);
	
	public GClass removeMethod (GMethod method);
	
	public GClass setPackageName (String newValue);

	public GClass setSuperClass (String superClass);

	public GClass setSuperClass (Class<?> superClass);
	
	public GClass setSuperClass (GClass superClass);
	
	public GClass addInterface (Class<?> iface);
	
	public GClass addInterface (GClass iface);
	
	public GClass addInterface (String iface);

	public GClass addInterfaces (Class<?>... ifaces);
	
	public GClass addInterfaces (GClass... ifaces);
	
	public GClass addInterfaces (String... ifaces);
	
	public GClass addInnerClass (String name);

	public int getInnerClassesSize ();
	
	public GMethod addConstructor ();
	
	public GMethod addMethod (String name);

	public GCode addStaticBlock ();

	public String queryAnnotation(Class<?> annotation, String member);
	
	public String queryAnnotation(String annotation, String member);
	
	public String queryAnnotation(Class<?> annotation, String member, String defaultValue);

	public String queryAnnotation(String annotation, String member, String defaultValue);

	public String queryAnnotation(String clazz, Class<?> annotation, String member);

	public String queryAnnotation(String clazz, Class<?> annotation, String member, String defaultValue);
	
	public String queryAnnotation(String clazz, String annotation, String member, String defaultValue);

	public GClass addCte(GClass type, String name, Object value);

	public GClass addCte(Class<?> type, String name, Object value);
	
	public GClass addCte(String type, String name, Object value);
	
	public boolean checkEnum ();

	public String getSimpleName();

	public String getFullName();

	public String getNameWithoutPackage();

	public String getPackageName();

	public String getName();

	public List getAllFields();
	
	public int getAllFieldsSize ();

	public List getFieldsByType (String type);

	public List getCteFields();
	
	public List getCteFieldsByType (String type);
	
	public List getFields();
	
	public int getFieldsSize ();

	public GField getField(String name);

	public GField getProperty (String name);
	
	public List getProperties(String[] names);

	public GMethod getMethod (String name);

	public List getListMethods (String name);
	
	public List getAllDeclaredMethods();

	public List getMethods();

	public List listRefFields(Class<?> type);
	
	public List listFields (Class<?> type);

	public List getProperties();

	public int getPropertiesSize();
	
	
	public List getViewProperties();

	
	public List getPublicMethods();

	public String getModuleName ();

	public boolean isPublic ();
	
	public boolean isProtected ();
	
	public boolean isPrivate ();
	
	public boolean isStatic ();
	
	public boolean isFinal ();
	
	public boolean isAbstract ();
	
	public GClass setPublic (boolean enabled);
	
	public GClass setPublic ();
	
	public GClass setProtected (boolean enabled);

	public GClass setProtected ();

	public GClass setPrivate (boolean enabled);
	
	public GClass setPrivate ();
	
	public GClass setStatic(boolean enabled);
	
	public GClass setStatic();
	
	public GClass setFinal(boolean enabled);
	
	public GClass setFinal();
	
	public GClass setAbstract(boolean enabled);
	
	public GClass setAbstract();
	
	public boolean checkInner(GClass clazz);

	public boolean checkInner(String className);

	public boolean isInner();

	public boolean isInner (GClass owner);

	public String getOwnerName();
	
	public String getOwnerTypeName();

	public GClass getOwnerType();

	public boolean containsField(String name);

	public GClass setName(String newName);

	public String getSuperClass();

	public GClass newClass(String cName);

	
	public GUnit getUnit ();
	
	public void setUnit (GUnit newValue);

	
	
	
	
	
	public java.util.List getInnerClasses ();

	public GClass getContainerClass ();
	
	public void setContainerClass(GClass newValue);
	
	public boolean isInnerClass ();

	public GClass addClass(GClass clazz);
	
	public GClass removeClass(GClass clazz);

	public GClass setSuperClass(Expression superClass);

	public GClass addInterface(Expression interfaceClass);
	
}
