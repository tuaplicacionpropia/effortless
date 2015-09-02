package org.effortless.orm.security;


import java.io.Serializable;

public interface SecuritySystem extends Serializable {

	public void check (Resource resource);
	
	public Object login (String loginName, String loginPassword);
	
	public void setupSession (Object user); 
	
}
