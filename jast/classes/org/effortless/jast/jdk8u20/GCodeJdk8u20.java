package org.effortless.jast.jdk8u20;

import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.jast.jdk8u20.util.Factory;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;

import org.effortless.jast.Expression;
import org.effortless.jast.GAnnotation;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GMethod;
import org.effortless.jast.Parameter;

public class GCodeJdk8u20 extends GModifiersJdk8u20 implements GCode {

	public GCodeJdk8u20 () {
		super();
		initiate();
	}
	
	protected void initiate () {
		
	}

	protected String _doGetKeyAttributes () {
		return "GCode:node_attributes";
	}
	
	protected JCTree.JCStatement blockCode;
	
	public JCTree.JCStatement getBlockCode () {
		return this.blockCode;
	}
	
	public void setBlockCode(JCTree.JCStatement newValue) {
		this.blockCode = newValue;
	}
	
	public Object getNativeNode () {
		return this.blockCode;
	}
	
	public GApp getApp () {
		return this.getClazz().getApplication();
	}
	
	
	
	
//	protected JCTree.JCBlock blockCode;
//	
//	public JCTree.JCBlock getBlockCode () {
//		return this.blockCode;
//	}
//	
//	public void setBlockCode(JCTree.JCBlock newValue) {
//		this.blockCode = newValue;
//	}
	
//	protected List<JCTree.JCStatement> blockCode;
//	
//	public List<JCTree.JCStatement> getBlockCode () {
//		return this.blockCode;
//	}
//	
//	public void setBlockCode(List<JCTree.JCStatement> newValue) {
//		this.blockCode = newValue;
//	}
	

	
	protected GClass clazz;
	
	public GClass getClazz () {
		return this.clazz;
	}

	public void setClazz(GClass newValue) {
		this.clazz = newValue;
	}
	
	public GCode addDeclVariable (String type, String name) {
		Factory.addDeclVariable(this, type, name);
		return this;
	}
	
	@Override
	public GCode gPrintln(String msg) {
		Factory.gPrintln(this, msg);
		return this;
	}

	@Override
	public GCode gPrintln(Expression msg) {
		Factory.gPrintln(this, (ExpressionJdk8u20)msg);
		return null;
	}

	@Override
	public GCode declVariable(String type, String name) {
		return declVariable(type, name, null);
	}

	@Override
	public GCode declVariable(Class<?> type, String name) {
		return declVariable(ClassUtils.getName(type), name, null);
	}

	@Override
	public GCode declVariable(GClass type, String name) {
		return declVariable(type.getFullName(), name, null);
	}

	@Override
	public GCode declVariable(String type, String name,	Expression defaultValue) {
		Factory.declVariable(this, 0L, type, name, (ExpressionJdk8u20)defaultValue);
		return this;
	}

	@Override
	public GCode declVariable(Class<?> type, String name, Expression defaultValue) {
		return declVariable(ClassUtils.getName(type), name, defaultValue);
	}

	@Override
	public GCode declVariable(GClass type, String name, Expression defaultValue) {
		return declVariable(type.getFullName(), name, defaultValue);
	}

	@Override
	public GCode declFinalVariable(String type, String name) {
		return declFinalVariable(type, name, null);
	}

	@Override
	public GCode declFinalVariable(Class<?> type, String name) {
		return declFinalVariable(type.getName(), name, null);
	}

	@Override
	public GCode declFinalVariable(GClass type, String name) {
		return declFinalVariable(type.getFullName(), name, null);
	}

	@Override
	public GCode declFinalVariable(String type, String name, Expression defaultValue) {
		Factory.declVariable(this, Flags.FINAL, type, name, (ExpressionJdk8u20)defaultValue);
		return this;
	}

	@Override
	public GCode declFinalVariable(Class<?> type, String name, Expression defaultValue) {
		return declFinalVariable(type.getName(), name, defaultValue);
	}

	@Override
	public GCode declFinalVariable(GClass type, String name, Expression defaultValue) {
		return declFinalVariable(type.getFullName(), name, defaultValue);
	}

	public GCode addReturn (String name) {
		return addReturn(var(name));
	}

	@Override
	public GCode addReturn(Expression expr) {
		Factory.addReturn(this, (ExpressionJdk8u20)expr);
		return this;
	}

	@Override
	public GCode add(Expression expr) {
		Factory.add(this, (ExpressionJdk8u20)expr);
		return this;
	}

	@Override
	public GCode addFor(Expression collectionExpression) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GCode addFor(String index, String length) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GCode addWhile(Expression condition) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GCode addTryCatch(GCode tryBlock, Object[]... catchs) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GCode throwException(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GCode throwException(Expression expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GClass addAnonymousClass(Class<?> superClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GClass addAnonymousClass(GClass superClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GClass addAnonymousClass(String superClass) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GCode newBlock() {
		return Factory.newBlock(this);
	}

	@Override
	public GCode addIf(Expression condition) {
		GCodeJdk8u20 result = null;
		result = (GCodeJdk8u20)newBlock();
		Factory.addIf(this, (ExpressionJdk8u20)condition, result, null);
		return result;
	}

	@Override
	public GCode addIf(Expression condition, GCode thenCode) {
		Factory.addIf(this, (ExpressionJdk8u20)condition, (GCodeJdk8u20)thenCode, null);
		return this;
	}

	@Override
	public GCode addIf(Expression condition, GCode thenCode, GCode elseCode) {
		Factory.addIf(this, (ExpressionJdk8u20)condition, (GCodeJdk8u20)thenCode, (GCodeJdk8u20)elseCode);
		return this;
	}

	
	
	
	
	
	
	@Override
	public Expression newObject(String type) {
		return newObject(type, (ExpressionJdk8u20[])null);
	}

	@Override
	public Expression newObject(String type, String... arguments) {
		return newObject(type, Factory.toIdentExpressionList(this, arguments));
	}

	@Override
	public Expression newObject(String type, String argument) {
		return newObject(type, Factory.toIdentExpression(this, argument));
	}

	@Override
	public Expression newObject(String type, Expression argument) {
		return newObject(type, new ExpressionJdk8u20[] {(ExpressionJdk8u20)argument});
	}

	@Override
	public Expression newObject(String type, Expression... arguments) {
		return Factory.newObject(this, type, Factory.toExpressionList(this, arguments));
	}


	
	@Override
	public Expression newObject(Class<?> type) {
		return newObject(ClassUtils.getName(type));
	}

	@Override
	public Expression newObject(Class<?> type, String... arguments) {
		return newObject(ClassUtils.getName(type), arguments);
	}

	@Override
	public Expression newObject(Class<?> type, String argument) {
		return newObject(ClassUtils.getName(type), argument);
	}

	@Override
	public Expression newObject(Class<?> type, Expression argument) {
		return newObject(ClassUtils.getName(type), argument);
	}

	@Override
	public Expression newObject(Class<?> type, Expression... arguments) {
		return newObject(ClassUtils.getName(type), arguments);
	}
	
	
	@Override
	public Expression newObject(GClass type) {
		return newObject(type.getFullName());
	}

	@Override
	public Expression newObject(GClass type, String... arguments) {
		return newObject(type.getFullName(), arguments);
	}

	@Override
	public Expression newObject(GClass type, String argument) {
		return newObject(type.getFullName(), argument);
	}

	@Override
	public Expression newObject(GClass type, Expression argument) {
		return newObject(type.getFullName(), argument);
	}

	@Override
	public Expression newObject(GClass type, Expression... arguments) {
		return newObject(type.getFullName(), arguments);
	}
	
	public boolean equals (Object obj) {
		boolean result = false;
		result = this.blockCode.equals(((GCodeJdk8u20)obj).blockCode);
		return result;
	}
	
	public String toString () {
		return (this.blockCode != null ? this.blockCode.toString() : super.toString());
	}
	
	public GCode add(GCode block) {
		Factory.addBlock(this, (GCodeJdk8u20)block);
		return this;
	}
	
}
