package org.effortless.jast.jdk8u20;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.effortless.core.ClassUtils;
import org.effortless.jast.jdk8u20.util.Factory;

import com.sun.tools.javac.tree.JCTree;

import org.effortless.jast.Expression;
import org.effortless.jast.GAnnotation;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GMethod;
import org.effortless.jast.Parameter;

public class GMethodJdk8u20 extends GCodeJdk8u20 implements GMethod {

	public GMethodJdk8u20 () {
		super();
		initiate();
	}
	
	protected void initiate () {
		super.initiate();
	}
	
	protected String _doGetKeyAttributes () {
		return "GMethod:node_attributes";
	}
	
	protected JCTree.JCMethodDecl node;
	
	public JCTree.JCMethodDecl getNode () {
		return this.node;
	}
	
	public void setNode(JCTree.JCMethodDecl newValue) {
		this.blockCode = (newValue != null ? newValue.body : null);
		this.node = newValue;
	}

	public Object getNativeNode () {
		return this.node;
	}
	
	public GApp getApp () {
		return this.getClazz().getApplication();
	}

	public String getName () {
		return Factory.getName(this);
	}

	public GMethod setName (String newValue) {
		Factory.setName(this, newValue);
		return this;
	}

	
	
	
	public GMethod setPublic (boolean enabled) {
		this.node.mods = setPublic(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GMethod setPublic () {
		return setPublic(true);
	}
	
	public boolean isPublic () {
		return isPublic(this.node.mods);
	}
	
	public GMethod setProtected (boolean enabled) {
		this.node.mods = setProtected(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GMethod setProtected () {
		return setProtected(true);
	}
	
	public boolean isProtected () {
		return isProtected(this.node.mods);
	}
	
	public GMethod setPrivate (boolean enabled) {
		this.node.mods = setPrivate(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GMethod setPrivate () {
		return setPrivate(true);
	}
	
	public boolean isPrivate () {
		return isPrivate(this.node.mods);
	}
	
	public GMethod setFinal (boolean enabled) {
		this.node.mods = setFinal(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GMethod setFinal () {
		return setFinal(true);
	}
	
	public boolean isFinal () {
		return isFinal(this.node.mods);
	}
	
	public GMethod setStatic (boolean enabled) {
		this.node.mods = setStatic(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GMethod setStatic () {
		return setStatic(true);
	}
	
	public boolean isStatic () {
		return isStatic(this.node.mods);
	}

	
	public GMethod setAbstract (boolean enabled) {
		this.node.mods = setAbstract(this.getClazz().getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GMethod setAbstract () {
		return setAbstract(true);
	}
	
	@Override
	public boolean isAbstract() {
		return isAbstract(this.node.mods);
	}
	
	
	
	public String getReturnType () {
		String result = null;
		result = Factory.getReturnType(this);
		return result;
	}
	
	public GMethod setReturnType (String newValue) {
		Factory.setReturnType(this, newValue);
		return this;
	}
	
	@Override
	public GMethod setReturnType(Class<?> type) {
		return setReturnType(ClassUtils.getName(type));
	}

	@Override
	public GMethod setReturnType(GClass type) {
		return setReturnType(type.getFullName());
	}

	public GMethod addParameter (String type, String name) {
		Factory.addParameter(this, type, name);
		return this;
	}
	
	@Override
	public GMethod addParameter(Class<?> type, String name) {
		return addParameter(ClassUtils.getName(type), name);
	}

	@Override
	public GMethod addParameter(GClass type, String name) {
		return addParameter(type.getFullName(), name);
	}

	@Override
	public GMethod addParameter(GClass type, String name, GAnnotation ann) {
		return addParameter(type.getFullName(), name, ann);
	}

	@Override
	public GMethod addParameter(Class<?> type, String name, GAnnotation ann) {
		return addParameter(type.getName(), name, ann);
	}

	@Override
	public GMethod addParameter(String type, String name, GAnnotation ann) {
		// TODO Auto-generated method stub
		return this;
	}

//	@Override
//	public void addPreviousCode() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public int getPreviousCodeLength() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void preserveCode() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public GMethod addFirstPreserveCode() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public GMethod addLastPreserveCode() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
	@Override
	public boolean checkSingleAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkPublic() {
		return this.isPublic();
	}

	@Override
	public boolean checkOnlyPublic() {
		boolean result = false;
		result = true;
		result = result && isPublic();
		result = result && !isStatic();
		result = result && !isFinal();
		return result;
	}

	@Override
	public boolean checkSameClass() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkReturnVoid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkNoParams() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkBaseMethod() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReturnType(GClass type) {
		return isReturnType(type.getFullName());
	}

	@Override
	public boolean isReturnType(Class<?> type) {
		return isReturnType(ClassUtils.getName(type));
	}

	@Override
	public boolean isReturnType(String type) {
		boolean result = false;
		String returnType = this.getReturnType();
		result = ObjectUtils.equals(returnType, type);
		return result;
	}

	@Override
	public int getNumParameters() {
		int result = 0;
		Parameter[] parameters = getParameters();
		result = (parameters != null ? parameters.length : 0);
		return result;
	}

	@Override
	public boolean isVoid() {
		boolean result = false;
		String returnType = getReturnType();
		result = "void".equals(returnType);
		return result;
	}

	@Override
	public boolean checkParameterType(int idx, GClass type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkParameterType(int idx, Class<?> type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkParameterType(int idx, String type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Parameter[] getParameters() {
		return Factory.getParameters(this);
	}

	@Override
	public Expression arrayTypes(Parameter[] parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression arrayParameters(Parameter[] parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression arrayNameValue(Parameter[] parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
	
	
	
	
	
	
	
	@Override
	public Expression callSuper() {
		return callSuper((ExpressionJdk8u20[])null);
	}

	@Override
	public Expression callSuper(String... arguments) {
		return callSuper(Factory.toIdentExpressionList(this, arguments));
	}

	@Override
	public Expression callSuper(String argument) {
		return callSuper(Factory.toIdentExpression(this, argument));
	}

	@Override
	public Expression callSuper(Expression argument) {
		return callSuper(new ExpressionJdk8u20[] {(ExpressionJdk8u20)argument});
	}

	@Override
	public Expression callSuper(Expression... arguments) {
		return Factory.callSuper(this, Factory.toExpressionList(this, arguments));
	}

	@Override
	public GMethod setReturnType(Expression returnType) {
		Factory.setReturnType(this, (ExpressionJdk8u20)returnType);
		return this;
	}

	public boolean equals (Object obj) {
		boolean result = false;
		result = this.node.equals(((GMethodJdk8u20)obj).node);
		return result;
	}
	
	public String toString () {
		return (this.node != null ? this.node.toString() : super.toString());
	}

	public GMethod add(GCode block) {
		Factory.addBlock(this, (GCodeJdk8u20)block);
		return this;
	}
	
	public GCode addBlock() {
		return Factory.addBlock(this);
	}
	
	
}
