package org.effortless.orm.security;

public class NoneSecuritySystem extends Object implements SecuritySystem {

	public NoneSecuritySystem() {
		super();
		initiate();
	}

	protected void initiate() {
	}
	
	protected static NoneSecuritySystem instance;
	
	public static NoneSecuritySystem getInstance () {
		if (instance == null) {
			instance = new NoneSecuritySystem();
		}
		return instance;
	}

	public void check (Resource resource) {
		if (resource != null) {
			resource.setAllow(Boolean.TRUE);
		}
	}
	
	@Override
	public Object login(String loginName, String loginPassword) {
		return loginName;
	}

	@Override
	public void setupSession(Object user) {
	}

}
