package org.effortless.core;

import java.io.Serializable;

public class EnumGroupString extends EnumString implements Serializable, java.lang.Comparable {

	protected EnumGroupString () {
		super();
	}
	
	protected EnumGroupString (String name, int ordinal) {
		super(name, ordinal);
	}
	
	protected EnumGroupString (String name, int ordinal, EnumGroupString parent) {
		super(name, ordinal, parent);
		this.parent = parent;
	}
	
	public EnumGroupString addChild(Object child) {
		if (child != null) {
			this.children = (this.children != null ? this.children : new java.util.ArrayList());
			this.children.add(child);
			EnumGroupString childGroup = null; try { childGroup = (EnumGroupString)child; } catch (ClassCastException e) {}
			if (childGroup != null) {
				childGroup.parent = this;
			}
		}
		return this;
	}
	
//	public static final EnumGroupString AAAAAA = new EnumGroupString("", 1, null).addChild(child).addC;

	protected void initiate () {
		super.initiate();
		initiateParent();
		initiateChildren();
	}
	
	protected EnumGroupString parent;
	
	protected void initiateParent () {
		this.parent = null;
	}
	
	protected EnumGroupString getParent () {
		return this.parent;
	}
	
	protected void setParent (EnumGroupString newValue) {
		this.parent = newValue;
	}
	
	protected java.util.List children;
	
	protected void initiateChildren () {
		this.children = null;
	}
	
	public java.util.List getChildren () {
		return this.children;
	}
	
	protected void setChildren (java.util.List newValue) {
		this.children = newValue;
	}
	
	public static void buildRootValues (java.util.Collection values, java.util.List targetList) {
		if (values != null && targetList != null) {
			java.util.Iterator iterator = values.iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					Object item = iterator.next();
					EnumGroupString group = null; try { group = (EnumGroupString)item; } catch (ClassCastException e) {}
					if (group != null && group.parent == null) {
						targetList.add(targetList);
					}
				}
			}
		}
	}
	
//	protected void _register() {
////		EnumString.values.put(this.name, this);
//	}
//
////	protected static java.util.Map<String, EnumString> values = new java.util.HashMap<String, EnumString>();
////	
////	public static EnumString valueOf (String name) {
////		return EnumString.values.get(name);
////	}

}
