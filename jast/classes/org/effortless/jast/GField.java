package org.effortless.jast;

public interface GField extends GNode {

	public GClass getClazz ();
	
	public void setClazz (GClass newValue);
	
	public String getType ();

	public String getName ();

	public String getGetterName ();
	
	public String getInitiateName ();
	
	public String getSetterName ();

	public GMethod getGetterMethod();

	public GMethod getSetterMethod();
	
	public GApp getApplication ();

	public boolean isPublic ();
	
	public boolean isProtected ();
	
	public boolean isPrivate ();
	
	public boolean isStatic ();
	
	public boolean isFinal ();

	public boolean isProperty();

	public GField setPublic (boolean enabled);
	
	public GField setProtected (boolean enabled);

	public GField setPrivate (boolean enabled);
	
	public GField setStatic(boolean enabled);
	
	public GField setFinal(boolean enabled);
	
	
	public GField setPublic ();
	
	public GField setProtected ();

	public GField setPrivate ();
	
	public GField setStatic();
	
	public GField setFinal();
	
	
	
	public boolean isViewProperty();
	
	public GField setInitialValue(Object value);

	public Expression getInitialValue();
	
	public GField setInitialValue(Expression value);

	public String getTypeWithoutPackage();

	public GField setType(Class<?> newValue);

	public GField setType(GClass newValue);

	public GField setType(String newValue);

	public GApp getApp();

	public GField setName(String newValue);

	public String getImportType();

}
