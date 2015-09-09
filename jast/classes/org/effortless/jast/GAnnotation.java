package org.effortless.jast;

public interface GAnnotation extends GNode {

	public String getMemberString(String name);
	
	public Boolean getMemberBoolean (String name);
	
	public String getMemberTypeName (String name);

	public String getValue();

	public GAnnotation addMember(String attr, Expression value);

}
