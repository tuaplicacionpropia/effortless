package org.effortless.core;

import java.io.Serializable;

public class EnumString extends Object implements Serializable, java.lang.Comparable<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1508194688346160294L;

	protected EnumString () {
		super();
		initiate();
	}
	
	protected EnumString (String name, int ordinal) {
		this(name, ordinal, null);
		setName(name);
		setOrdinal(ordinal);
		System.out.println(">>>>>>>>>>>>> constructor EnumString");
		_register();
	}
	
	protected EnumString (String name, int ordinal, EnumGroupString parent) {
		this();
		setName(name);
		setOrdinal(ordinal);
		System.out.println(">>>>>>>>>>>>> constructor EnumString");
		_register();
		if (parent != null) {
			parent.addChild(this);
		}
	}
	
	
	protected void initiate () {
		initiateName();
		initiateOrder();
	}
	
	protected String name;
	
	protected void initiateName () {
		this.name = null;
	}
	
	protected String getName () {
		return this.name;
	}
	
	protected void setName (String newValue) {
		this.name = newValue;
	}
	
	protected int ordinal;
	
	protected void initiateOrder () {
		this.ordinal = 0;
	}
	
	protected int getOrdinal () {
		return this.ordinal;
	}
	
	protected void setOrdinal (int newValue) {
		this.ordinal = newValue;
	}
	
	public String name () {
		return this.name;
	}
	
	public int ordinal () {
		return this.ordinal;
	}
	
	public int hashCode () {
		int result = 0;
		org.apache.commons.lang.builder.HashCodeBuilder hcBuilder = new org.apache.commons.lang.builder.HashCodeBuilder();
		hcBuilder.append(getClass().getName());
		hcBuilder.append(this.name);
		result = hcBuilder.toHashCode();
		return result;
	}
	
	public int compareTo(Object obj) {
		int result = -1;
		EnumString obj2 = null;
		try { obj2 = ((EnumString)obj); } catch (ClassCastException e) {}
		if (obj2 == null) {
			result = -1;
		}
		else if (this == obj) {
			result = 0;
		}
		else {
			result = this.ordinal - obj2.ordinal;
			result = (this.ordinal < obj2.ordinal ? -1 : (this.ordinal == obj2.ordinal ? 0 : +1));
		}
		return result;
	}

	public String toString () {
		return this.name;
	}
	
	public boolean equals(Object obj) {
		boolean result = false;
		EnumString obj2 = null;
		try { obj2 = ((EnumString)obj); } catch (ClassCastException e) {}
		if (obj2 == null) {
			result = false;
		}
		else if (this == obj) {
			result = true;
		}
		else {
			result = true;
			result = result && this.name.equals(obj2.name);
			result = result && this.getClass().equals(obj2.getClass());
		}
		return result;
	}
	
	protected void _register() {
//		EnumString.values.put(this.name, this);
	}

//	protected static java.util.Map<String, EnumString> values = new java.util.HashMap<String, EnumString>();
//	
//	public static EnumString valueOf (String name) {
//		return EnumString.values.get(name);
//	}

}
