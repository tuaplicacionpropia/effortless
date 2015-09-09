package org.effortless.jast;

import org.effortless.jast.jdk8u20.ExpressionJdk8u20;
import org.effortless.jast.jdk8u20.util.Factory;

public interface GCode extends GNode {

	public GClass getClazz ();

	public void setClazz(GClass newValue);

	public GCode addDeclVariable (String type, String name);
	
	public GCode gPrintln(String msg);

	public GCode gPrintln(Expression msg);

	public GCode declVariable(Class<?> type, String name);

	public GCode declVariable(GClass type, String name);

	public GCode declVariable(String type, String name);

	public GCode declVariable(Class<?> type, String name, Expression defaultValue);

	public GCode declVariable(GClass type, String name,	Expression defaultValue);

	public GCode declVariable(String type, String name, Expression defaultValue);

	public GCode declFinalVariable(Class<?> type, String name);

	public GCode declFinalVariable(GClass type, String name);

	public GCode declFinalVariable(String type, String name);

	public GCode declFinalVariable(Class<?> type, String name, Expression defaultValue);

	public GCode declFinalVariable(GClass type, String name, Expression defaultValue);

	public GCode declFinalVariable(String type, String name, Expression defaultValue);

	public GCode addReturn (String name);

	public GCode addReturn(Expression expr);

	public GCode add(Expression expr);

	public GCode addFor(Expression collectionExpression);

	public GCode addFor(String index, String length);

	public GCode addWhile(Expression condition);

	public GCode addTryCatch(GCode tryBlock, Object[]... catchs);

	public Expression plus(Expression left, Expression right);

	public GCode throwException(String name);

	public GCode throwException(Expression expr);

	public GClass addAnonymousClass(Class<?> superClass);

	public GClass addAnonymousClass(GClass superClass);

	public GClass addAnonymousClass(String superClass);

	public GCode newBlock();

	public GCode addIf(Expression condition);
	
	public GCode addIf(Expression condition, GCode thenCode);

	public GCode addIf(Expression condition, GCode thenCode, GCode elseCode);

	
	
	
	
	
	
	
	
	
	
	public Expression newObject(String type);

	public Expression newObject(String type, String... arguments);

	public Expression newObject(String type, String argument);

	public Expression newObject(String type, Expression argument);

	public Expression newObject(String type, Expression... arguments);


	public Expression newObject(Class<?> type);

	public Expression newObject(Class<?> type, String... arguments);

	public Expression newObject(Class<?> type, String argument);
	
	public Expression newObject(Class<?> type, Expression argument);

	public Expression newObject(Class<?> type, Expression... arguments);

	
	
	public Expression newObject(GClass type);

	public Expression newObject(GClass type, String... arguments);

	public Expression newObject(GClass type, String argument);

	public Expression newObject(GClass type, Expression argument);

	public Expression newObject(GClass type, Expression... arguments);
	
	
	public GCode add(GCode block);
	
	

}
