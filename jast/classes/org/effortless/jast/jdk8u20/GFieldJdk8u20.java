package org.effortless.jast.jdk8u20;

import org.effortless.core.ClassUtils;
import org.effortless.core.StringUtils;
import org.effortless.jast.jdk8u20.util.Factory;

import com.sun.tools.javac.tree.JCTree;

import org.effortless.jast.Expression;
import org.effortless.jast.GApp;
import org.effortless.jast.GField;
import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;

public class GFieldJdk8u20 extends GModifiersJdk8u20 implements GField {

	public GFieldJdk8u20 () {
		super();
	}

	protected String _doGetKeyAttributes () {
		return "GField:node_attributes";
	}
	
	protected JCTree.JCVariableDecl node;

	public JCTree.JCVariableDecl getNode () {
		return this.node;
	}
	
	public void setNode(JCTree.JCVariableDecl newValue) {
		this.node = newValue;
	}

	public Object getNativeNode () {
		return this.node;
	}
	
	protected GClass clazz;
	
	public GClass getClazz () {
		return this.clazz;
	}
	
	public void setClazz(GClass newValue) {
		this.clazz = newValue;
	}
	
	public String getName () {
		return Factory.getName(this);
	}
	
	public GField setName (String newValue) {
		Factory.setName(this, newValue);
		return this;
	}
	
	public String getType () {
		return Factory.getType(this);
	}
	
	public GField setPublic (boolean enabled) {
		this.node.mods = setPublic(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GField setPublic () {
		return setPublic(true);
	}
	
	public boolean isPublic () {
		return isPublic(this.node.mods);
	}
	
	public GField setProtected (boolean enabled) {
		this.node.mods = setProtected(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GField setProtected () {
		return setProtected(true);
	}
	
	public boolean isProtected () {
		return isProtected(this.node.mods);
	}
	
	public GField setPrivate (boolean enabled) {
		this.node.mods = setPrivate(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GField setPrivate () {
		return setPrivate(true);
	}
	
	public boolean isPrivate () {
		return isPrivate(this.node.mods);
	}
	
	public GField setFinal (boolean enabled) {
		this.node.mods = setFinal(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GField setFinal () {
		return setFinal(true);
	}
	
	public boolean isFinal () {
		return isFinal(this.node.mods);
	}
	
	public GField setStatic (boolean enabled) {
		this.node.mods = setStatic(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GField setStatic () {
		return setStatic(true);
	}
	
	public boolean isStatic () {
		return isStatic(this.node.mods);
	}

	@Override
	public String getGetterName() {
		String result = null;
		result = "get" + StringUtils.capFirst(this.getName());
		return result;
	}

	@Override
	public String getInitiateName() {
		String result = null;
		result = "initiate" + StringUtils.capFirst(this.getName());
		return result;
	}

	@Override
	public String getSetterName() {
		String result = null;
		result = "set" + StringUtils.capFirst(this.getName());
		return result;
	}

	@Override
	public GMethod getGetterMethod() {
		GMethod result = null;
		String methodName = getGetterName();
		result = getClazz().getMethod(methodName);
		return result;
	}

	@Override
	public GMethod getSetterMethod() {
		GMethod result = null;
		String methodName = getSetterName();
		result = getClazz().getMethod(methodName);
		return result;
	}

	@Override
	public GApp getApplication() {
		return this.getClazz().getUnit().getApp();
	}

	@Override
	public boolean isProperty() {
		boolean result = false;
		result = true;
//		result = result && (isProtected() || isPrivate());
		result = result && !isFinal();
		result = result && !isStatic();
		return result;
	}

	@Override
	public boolean isViewProperty() {
		boolean result = false;
		result = true;
		result = result && isProperty();
		return result;
	}

	@Override
	public GField setInitialValue(Object value) {
		return setInitialValue(cte(value));
	}

	@Override
	public Expression getInitialValue() {
		return Factory.getInitialValue(this);
	}

	@Override
	public GField setInitialValue(Expression value) {
		Factory.setInitialValue(this, (ExpressionJdk8u20)value);
		return this;
	}

	@Override
	public String getTypeWithoutPackage() {
		String result = null;
		String type = getType();
		int indexOf = (type != null ? type.lastIndexOf(".") : -1);
		result = (indexOf > -1 ? type.substring(indexOf + 1) : type);
		return result;
	}

	public GField setType (String type) {
		Factory.setType(this, type);
		return this;
	}

	@Override
	public GField setType(GClass type) {
		return setType(type.getFullName());
	}

	@Override
	public GField setType(Class<?> type) {
		return setType(ClassUtils.getName(type));
	}

	@Override
	public GApp getApp() {
		return this.getClazz().getUnit().getApp();
	}
	
	public boolean equals (Object obj) {
		boolean result = false;
		result = this.node.equals(((GFieldJdk8u20)obj).node);
		return result;
	}
	
	public String toString () {
		return (this.node != null ? this.node.toString() : super.toString());
	}
	
	public boolean checkSameType(String type) {
		return Factory.checkSameType(this, type);
	}

//	@Override
//	public boolean isType(String type) {
//		boolean result = false;
//		result = result || checkSameType(type);
//		result = result || checkSuperClass(type);
//		result = result || checkImplements(type);
//		return result;
//	}

	public boolean checkImplements(String type) {
		return Factory.checkImplements(this, type);
	}

	public boolean checkSuperClass(String type) {
		return Factory.checkSuperClass(this, type);
	}

	public String getImportType() {
		String result = null;
		result = Factory.getImportType(this);
		return result;
	}

}
