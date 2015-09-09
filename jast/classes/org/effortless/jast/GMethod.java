package org.effortless.jast;

public interface GMethod extends GCode {

	public GMethod setReturnType (Class<?> type);

	public GMethod setReturnType (GClass type);

	public GMethod setReturnType (String type);

	public String getReturnType ();
	
	public GMethod addParameter (Class<?> type, String name);

	public GMethod addParameter (GClass type, String name);

	public GMethod addParameter (String type, String name);

	public GMethod addParameter (GClass type, String name, GAnnotation ann);

	public GMethod addParameter (Class<?> type, String name, GAnnotation ann);

	public GMethod addParameter (String type, String name, GAnnotation ann);

	
//	public List<Statement> getCode ();
	

	public GCode addBlock();

//	public void addPreviousCode ();
//	
//	public int getPreviousCodeLength ();
//	
//	public void preserveCode();
//
//	public GMethod addFirstPreserveCode();
//
//	public GMethod addLastPreserveCode();

	
	
	
	
	public boolean checkSingleAction ();
	
	public boolean checkPublic();

	public boolean checkOnlyPublic();

	public boolean checkSameClass();

	public boolean checkReturnVoid ();
	
	public boolean checkNoParams ();

	public String getName();
	
	public boolean checkBaseMethod();

	public boolean isReturnType(GClass type);

	public boolean isReturnType(Class<?> type);

	public boolean isReturnType(String type);

	public int getNumParameters();

	public boolean isVoid();

	public boolean checkParameterType(int idx, GClass type);

	public boolean checkParameterType(int idx, Class<?> type);

	public boolean checkParameterType(int idx, String type);

	public GMethod setPublic (boolean enabled);
	
	public GMethod setProtected (boolean enabled);

	public GMethod setPrivate (boolean enabled);
	
	public GMethod setStatic(boolean enabled);
	
	public GMethod setFinal(boolean enabled);

	public GMethod setAbstract(boolean enabled);

	public boolean isPublic ();
	
	public boolean isProtected ();
	
	public boolean isPrivate ();
	
	public boolean isStatic ();
	
	public boolean isFinal ();
	
	public boolean isAbstract ();
	
	

	public GMethod setPublic ();
	
	public GMethod setProtected ();

	public GMethod setPrivate ();
	
	public GMethod setStatic();
	
	public GMethod setFinal();

	public GMethod setAbstract();
	
	
	public Parameter[] getParameters();

	public Expression arrayTypes(Parameter[] parameters);

	public Expression arrayParameters(Parameter[] parameters);

	public Expression arrayNameValue(Parameter[] parameters);
	
	
	public Expression callSuper ();
	
	public Expression callSuper (String var);
	
	public Expression callSuper (String... arguments);
	
	public Expression callSuper (Expression expr);
	
	public Expression callSuper (Expression... arguments);

	public GMethod setReturnType(Expression returnType);

	public GMethod add(GCode block);

	
	
	

}
