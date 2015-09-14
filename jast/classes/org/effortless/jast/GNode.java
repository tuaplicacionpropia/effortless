package org.effortless.jast;

import java.util.List;
import java.util.Map;

import org.effortless.core.ClassUtils;
import org.effortless.jast.jdk8u20.ExpressionJdk8u20;
import org.effortless.jast.jdk8u20.util.Factory;

public interface GNode {
	
//	public Map getAnnotationAttributes ();

	public Object getData (String name);

	public GNode setData (String name, Object value);
	
	public boolean hasDataClass (String clazz);
	public boolean addDataClass (String clazz);
	public boolean removeDataClass (String clazz);
	public java.util.List getDataClasses ();
	
	
	public boolean isType (Class<?> type);
	public boolean isType (String type);
	
	public boolean checkSameType(Class type);
	public boolean checkSameType(String type);
	
	public boolean checkImplements (Class<?> type);
	public boolean checkImplements (String type);

	public boolean checkSuperClass (Class<?> type);
	public boolean checkSuperClass (String type);

	public boolean isString ();
	
	public boolean isTime ();

	public boolean isTimestamp ();
	
	public boolean isDate ();

	public boolean isBoolean ();
	
	public boolean isInteger ();
	
	public boolean isDouble ();
	
	public boolean isEnum ();
	
	public boolean isRealEnum ();
	
	public boolean isFile ();
	
	public boolean isCollection ();
	
	public boolean isList ();
	
	public GAnnotation addAnnotation (Class<?> annotation);
	
	public GAnnotation addAnnotation (String annotation);
	public GAnnotation addAnnotation (GClass annotation);
	
	
	public GNode addAnnotation(GAnnotation annotation);
	
	public GNode removeAnnotation(GAnnotation annotation);
	
	public GNode removeAllAnnotations (Class annType);
	

	public GAnnotation addAnnotation (Class<?> annotation, String value);
	
	public GAnnotation addAnnotation (Class<?> annotation, Expression value);
	
	public GAnnotation addAnnotation (GClass annotation, String value);

	public GAnnotation addAnnotation (Class<?> annotation, String property, String value);

	public GAnnotation addAnnotation (Class<?> annotation, String property, boolean value);

	
	
	public GAnnotation addAnnotation (GClass annotation, String property, String value);

	public GAnnotation addAnnotation (GClass annotation, String property, Expression expr);

	public GAnnotation addAnnotation (Class<?> annotation, String property, Expression value);

	public GAnnotation addAnnotation(String annotation, String[] properties, String... values);

	public GAnnotation addAnnotation(Class<?> annotation, String[] properties, String... values);

	public GAnnotation addAnnotation(GClass annotation, String[] properties, String... values);

	public GAnnotation addAnnotation (String annotation, String[] properties, Expression... values);
	
	public GAnnotation addAnnotation (Class<?> annotation, String[] properties, Expression... values);
	
	public GAnnotation addAnnotation (GClass annotation, String[] properties, Expression... values);
	
	
	
	
	public Expression cteNull ();
	
	public Expression cte (Object value);
	
	public Expression cte (boolean value);
	
	public Expression cte (int value);
	
	public Expression cteInteger(int value);
	
	public Expression cte (short value);
	
	public Expression cte (long value);
	
	public Expression cte (double value);
	
	public Expression cte (byte value);
	
	public Expression cte (char value);
	
	public Expression cteSuper ();
	
	public Expression cteThis ();
	
	public Expression cteTrue();
	
	public Expression cteFalse();

	public Expression cteTRUE();
	
	public Expression cteFALSE();

	public Expression cteClass(Class<?> type);
	
	public Expression cteClass(String type);
	
	public Expression cteClass(GClass cg);
	

	public GAnnotation createAnnotation (String type);
	
	public GAnnotation createAnnotation (Class<?> type);
	
	public GAnnotation createAnnotation(GClass type);
	
	
	public Expression cteAnnotation (GAnnotation annotation);

	public Expression cteArray(Class<?> type);
	
	public Expression cteArray(String type);

	public Expression cteArray(Class<?> type, Expression... params);
	
	public Expression cteArray(String type, Expression... params);

	
	public GAnnotation getAnnotation (Class<?> clazz);

	public GAnnotation getAnnotation (String clazz);
	
	public GAnnotation[] getAnnotations (Class<?> clazz);

	public GAnnotation[] getAnnotations (String clazz);
	
	public boolean hasAnnotation (Class<?> clazz);

	public boolean hasAnnotation (String clazz);

	
	public Expression debug (String msg);
	
	public Expression debug (Expression msg);
	
	
	

	public Expression plus(Expression left, Expression right);
	public Expression minus(Expression left, Expression right);
	public Expression div(Expression left, Expression right);
	public Expression mult(Expression left, Expression right);
	public Expression mod(Expression left, Expression right);
	public Expression and(Expression left, Expression right);
	public Expression or(Expression left, Expression right);
	public Expression postDec(Expression expr);
	public Expression postInc(Expression expr);
	public Expression preDec(Expression expr);
	public Expression preInc(Expression expr);
	
	public Expression parens(Expression expr);
	
	
	

	public Expression eq (Expression left, Expression right);

	public Expression notNull (Expression left);
	
	public Expression isNull (Expression left);
	
	public Expression notNull (String name);
	
	public Expression isNull (String name);
	
	public Expression ne (Expression left, Expression right);
	
	public Expression gt (Expression left, Expression right);
	
	public Expression lt (Expression left, Expression right);
	
	public Expression ge (Expression left, Expression right);
	
	public Expression le (Expression left, Expression right);

	public Expression assign (String left, Expression right);
	
	public Expression assign (String left, String right);
	
	public Expression assign (Expression left, String right);
	
	public Expression assign (Expression left, Expression right);

	
	public Expression assignOp(Expression left, String op, Expression right);
	
	public Expression assignOp(String left, String op, Expression right);

	public Expression assignOp(String left, String op, String right);

	public Expression assignOp(Expression left, String op, String right);
	
	
	public Expression field (String fieldName);
	
	public Expression field (GField field);

	public Expression property (String property);
	
	public Expression property (String obj, String property);
	
	public Expression property (Expression obj, String property);

	public Expression realProperty (Expression obj, String property);

	public Expression triple (Expression condition, Expression exprTrue, Expression exprFalse);
	
	public Expression triple (Expression condition, String varTrue, Expression exprFalse);
	
	public Expression triple (Expression condition, Expression exprTrue, String varFalse);

	public Expression triple (Expression condition, String varTrue, String varFalse);

	public Expression triple (String variable, Expression exprTrue, Expression exprFalse);

	public Expression triple (String variable, String varTrue, Expression exprFalse);
	
	public Expression triple (String variable, Expression exprTrue, String varFalse);

	public Expression triple (String variable, String varTrue, String varFalse);

	
	
	public Expression call (String obj, String method);
	
	public Expression call (String obj, String method, String... variables);
	
	public Expression call (String obj, String method, Expression... arguments);
	
	public Expression call (Expression obj, String method, String... variables);
	
	public Expression call (Expression obj, String method, Expression... arguments);

	public Expression call (String method, Expression... arguments);
	
	public Expression call (Expression obj, String method);
	
	public Expression call (String method);

	
	
	public Expression callStatic (String type, String method);
	
	public Expression callStatic (String type, String method, String... variables);
	
	public Expression callStatic (String type, String method, Expression... arguments);


	public Expression callStatic (Class<?> type, String method);
	
	public Expression callStatic (Class<?> type, String method, String... variables);
	
	public Expression callStatic (Class<?> type, String method, Expression... arguments);
	
	
	public Expression callStatic (GClass type, String method);
	
	public Expression callStatic (GClass type, String method, String... variables);
	
	public Expression callStatic (GClass type, String method, Expression... arguments);
	
	
	
	public Expression and(String... expr);

	public Expression or(String... expr);
	
	public Expression and (Expression... expr);
	
	public Expression or (Expression... expr);

	public Expression var (String name);
	
	public Expression not (String variable);
	
	public Expression not (Expression expr);
	
	public Expression enumValue (Class<?> type, String enumValue);

	public Expression enumValue (String type, String enumValue);


	public Expression cast(String type, Expression value);

	public Expression cast(Class<?> type, Expression value);
	
	public Expression cast(GClass clazz, Expression var);
	
	public Expression cast(String type, String var);

	public Expression cast(Class<?> type, String var);
	
	public Expression cast(GClass clazz, String var);



	public Expression clazz (Class<?> type);
	
	public Expression clazz (String type);
	
	public Expression clazz(GClass clazz);

	
	
	public Expression array (Expression... expressions);
	
//	public Expression arrayTypes (Parameter... params);
//	
//	public Expression arrayParameters (Parameter... params);
//	
//	public Expression arrayNameValue (Parameter... params);
//
//	
//
//	public ClassNode createGenericType (Class<?> clazz, Class<?>[] types);
//	
//	public ClassNode createGenericType (Class<?> clazz, Class<?> type);
//	
//	public ClassNode createGenericType (ClassNode clazz, Class<?>[] types);
//	
//	public ClassNode createGenericType (Class<?> clazz, ClassNode type);
//	
//	public ClassNode createGenericType (ClassNode clazz, ClassNode[] types);
//	
//	public ClassNode createGenericType (ClassNode clazz, ClassNode type);
//
//	public ArgumentListExpression newArgumentListExpression();

	public abstract Object getAttribute (String name);
	
	public abstract Object setAttribute (String name, Object value);

	
	public Expression parameterizeType(String type, String... params);
	
	public Expression parameterizeType(String type, Class<?>... params);
	
	public Expression parameterizeType(String type, GClass... params);

	public Expression parameterizeType(Class<?> type, String... params);
	
	public Expression parameterizeType(Class<?> type, Class<?>... params);
	
	public Expression parameterizeType(Class<?> type, GClass... params);
	
	public Expression parameterizeType(GClass type, String... params);
	
	public Expression parameterizeType(GClass type, Class<?>... params);
	
	public Expression parameterizeType(GClass type, GClass... params);
	
}
