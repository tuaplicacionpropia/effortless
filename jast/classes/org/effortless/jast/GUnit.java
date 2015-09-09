package org.effortless.jast;

import java.util.List;

public interface GUnit extends GNode {

	public String getName();

	public void setName(String newPackageName);

	public GApp getApp ();

	public void setApp(GApp newValue);
	
	public String getPackageName();
	
	public void setPackageName (String newValue);

	public GClass getMainClass ();
	
	public List getClasses();

	public GUnit addClass(GClass clazz);

	public GUnit removeClass(GClass clazz);

	public GUnit addImport(String eImport);

	public GUnit removeImport(String eImport);
	
	public boolean containsImport(String eImport);

	public boolean containsImportClassName(String eImport);
	
	public List getImports ();

}
