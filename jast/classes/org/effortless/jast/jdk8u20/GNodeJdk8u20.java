package org.effortless.jast.jdk8u20;

import java.util.List;
import java.util.Map;

import org.effortless.core.Dt;
import org.effortless.core.ClassUtils;
import org.effortless.core.EnumString;
import org.effortless.core.JsonUtils;
import org.effortless.core.PropertyUtils;
import org.effortless.core.StringUtils;
import org.effortless.jast.Expression;
import org.effortless.jast.GAnnotation;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GNode;
import org.effortless.jast.jdk8u20.util.Factory;

public class GNodeJdk8u20 extends Object implements GNode {

	public GNodeJdk8u20 () {
		super();
		initiate();
	}
	
	protected void initiate () {
		
	}
	
	public GApp getApp () {
		return null;
	}
	
	protected String _doGetKeyAttributes () {
		return "GNode:node_attributes";
	}
	
	protected Map getAnnotationAttributes () {
		Map result = null;
		GAnnotation[] annotations = this.getAnnotations(Dt.class);
		int length = (annotations != null ? annotations.length : 0);
		String attrs = "";
		for (int i = 0; i < length; i++) {
			GAnnotation annotation = (GAnnotation)annotations[i];
			String value = annotation.getValue();
			value = (value != null ? value.trim() : "");
			attrs += (attrs.length() > 0 && value.length() > 0 ? ", " : "") + value;
		}
		result = (attrs.length() > 0 ? JsonUtils.toMap(attrs) : null);
		result = (result != null ? result : new java.util.HashMap());
		return result;
	}

	public Object getData (String name) {
		Object result = null;
		Map annotationAttributes = getAnnotationAttributes();
		result = PropertyUtils.getProperty(annotationAttributes, name);
		return result;
	}

	public GNode setData (String name, Object value) {
		Map annotationAttributes = getAnnotationAttributes();
		annotationAttributes.put(name, value);
		writeAnnotationAttributes(annotationAttributes);
		return this;
	}

	public boolean hasDataClass (String clazz) {
		boolean result = false;
		clazz = StringUtils.nullNotAllow(clazz);
		if (clazz.length() > 0) {
			java.util.List classes = (java.util.List)this.getData("classes");
			if (classes != null && classes.contains(clazz)) {
				result = true;
			}
		}
		return result;
	}

	public boolean addDataClass (String clazz) {
		boolean result = false;
		clazz = StringUtils.nullNotAllow(clazz);
		if (clazz.length() > 0) {
			java.util.List classes = (java.util.List)this.getData("classes");
			classes = (classes != null ? classes : new java.util.ArrayList());
			result = !classes.contains(clazz);
			classes.add(clazz);
			setData("classes", classes);
		}
		return result;
	}

	public boolean removeDataClass (String clazz) {
		boolean result = false;
		clazz = StringUtils.nullNotAllow(clazz);
		if (clazz.length() > 0) {
			java.util.List classes = (java.util.List)this.getData("classes");
			if (classes != null && classes.contains(clazz)) {
				result = true;
				classes.remove(clazz);
				setData("classes", classes);
			}
		}
		return result;
	}

	public java.util.List getDataClasses () {
		java.util.List result = null;
		result = (java.util.List)this.getData("classes");
		result = (result != null ? result : new java.util.ArrayList());
		return result;
	}

	protected void writeAnnotationAttributes (Map map) {
		this.removeAllAnnotations(Dt.class);
		if (map != null) {
			java.util.Set keySet = (map != null ? map.keySet() : null);
			java.util.Iterator itKeys = (keySet != null ? keySet.iterator() : null);
			while (itKeys != null && itKeys.hasNext()) {
				Map mapKey = new java.util.HashMap();
				Object key = itKeys.next();
				Object value = map.get(key);
				mapKey.put(key, value);
				String jsonValue = StringUtils.nullNotAllow(JsonUtils.toJson(mapKey));
				if (jsonValue.length() > 0) {
					this.addAnnotation(Dt.class, jsonValue);
				}
			}
		}
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("name", "Pushkin");
//		Yaml yaml = new Yaml();
//		String output = yaml.dump(map);
//		System.out.println(output);
	}
	
	public java.util.Map getAttributes () {
		java.util.Map result = null;
		GApp app = getApp();
		Object nativeNode = getNativeNode();
		String key = _doGetKeyAttributes();
		java.util.Map attr = (java.util.Map)app.getAttribute(key);
		result = (attr != null ? (java.util.Map)(attr.get(nativeNode)) : null);
		if (result == null) {
			result = new java.util.HashMap();
			attr = (attr != null ? attr : new java.util.HashMap());
			attr.put(nativeNode, result);
			app.setAttribute(key, attr);
		}
		return result;
	}
	
	public void setAttributes (java.util.Map newValue) {
		GApp app = getApp();
		Object nativeNode = getNativeNode();
		String key = _doGetKeyAttributes();
		java.util.Map attr = (java.util.Map)app.getAttribute(key);
		attr = (attr != null ? attr : new java.util.HashMap());
		attr.put(nativeNode, newValue);
		app.setAttribute(key, attr);
	}
	
	public Object getAttribute (String name) {
		Object result = null;
		java.util.Map attributes = getAttributes();
		if (attributes != null && name != null) {
			result = attributes.get(name);
		}
		return result;
	}
	
	public Object setAttribute (String name, Object value) {
		Object result = null;
		java.util.Map attributes = getAttributes();
		if (name != null && attributes != null) {
			result = attributes.put(name, value);
		}
		return result;
	}
	
	public Object getNativeNode () {
		return null;
	}
	
	public void removeAttribute (String name) {
		java.util.Map attributes = getAttributes();
		if (attributes != null && name != null) {
			attributes.remove(name);
		}
	}
	
	public void clearAttributes () {
		java.util.Map attributes = getAttributes();
		if (attributes != null) {
			attributes.clear();
		}
	}

	@Override
	public boolean isType(Class<?> type) {
		return isType(ClassUtils.getName(type));
	}

	@Override
	public boolean isType(String type) {
		boolean result = false;
		result = result || checkSameType(type);
		result = result || checkSuperClass(type);
		result = result || checkImplements(type);
		return result;
	}

	public boolean checkSameType(Class type) {
		return checkSameType(ClassUtils.getName(type));
	}

	public boolean checkSameType(String type) {
		return false;
	}

	@Override
	public boolean checkImplements(Class<?> type) {
		return checkImplements(ClassUtils.getName(type));
	}

	@Override
	public boolean checkImplements(String type) {
		return false;
	}

	@Override
	public boolean checkSuperClass(Class<?> type) {
		return checkSuperClass(ClassUtils.getName(type));
	}

	@Override
	public boolean checkSuperClass(String type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isString() {
		return isType(String.class);
	}

	@Override
	public boolean isTime() {
		return isType(java.sql.Time.class);
	}

	@Override
	public boolean isTimestamp() {
		return isType(java.sql.Timestamp.class);
	}

	@Override
	public boolean isDate() {
		return isType(java.util.Date.class);
	}

	@Override
	public boolean isBoolean() {
		return isType(Boolean.class);
	}

	@Override
	public boolean isInteger() {
		return isType(Integer.class);
	}

	@Override
	public boolean isDouble() {
		return isType(Double.class);
	}

	@Override
	public boolean isEnum() {
		return isType(Enum.class) || isType(EnumString.class);
	}

	@Override
	public boolean isRealEnum() {
		return isType(Enum.class);
	}

	@Override
	public boolean isFile() {
		return isType(java.io.File.class);
	}

	@Override
	public boolean isCollection() {
		return isType(java.util.Collection.class);
	}

	@Override
	public boolean isList() {
		return isType(java.util.List.class);
	}

	@Override
	public GAnnotation addAnnotation(String annotation) {
		return Factory.addAnnotation(this, annotation);
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation) {
		return addAnnotation(ClassUtils.getName(annotation));
	}

	@Override
	public GAnnotation addAnnotation(GClass annotation) {
		return addAnnotation(annotation.getFullName());
	}

	@Override
	public GNode addAnnotation(GAnnotation annotation) {
		Factory.addAnnotation(this, (GAnnotationJdk8u20)annotation);
		return this;
	}

	@Override
	public GNode removeAnnotation(GAnnotation annotation) {
		Factory.removeAnnotation(this, (GAnnotationJdk8u20)annotation);
		return this;
	}
	
	public GNode removeAllAnnotations (Class annType) {
		GAnnotation[] annotations = this.getAnnotations(annType);
		int length = (annotations != null ? annotations.length : 0);
		for (int i = 0; i < length; i++) {
			GAnnotation ann = annotations[i];
			this.removeAnnotation(ann);
		}
		return this;
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation, String value) {
		return addAnnotation(ClassUtils.getName(annotation)).addMember("value", cte(value));
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation, Expression value) {
		return addAnnotation(ClassUtils.getName(annotation)).addMember("value", value);
	}

	@Override
	public GAnnotation addAnnotation(GClass annotation, String value) {
		return addAnnotation(annotation.getFullName()).addMember("value", cte(value));
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation, String property, String value) {
		return addAnnotation(ClassUtils.getName(annotation)).addMember(property, cte(value));
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation, String property, boolean value) {
		return addAnnotation(ClassUtils.getName(annotation)).addMember(property, cte(value));
	}

	@Override
	public GAnnotation addAnnotation(GClass annotation, String property, String value) {
		return addAnnotation(annotation.getFullName()).addMember(property, cte(value));
	}

	@Override
	public GAnnotation addAnnotation(GClass annotation, String property, Expression value) {
		return addAnnotation(annotation.getFullName()).addMember(property, value);
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation, String property, Expression value) {
		return addAnnotation(ClassUtils.getName(annotation)).addMember(property, value);
	}

	@Override
	public GAnnotation addAnnotation(String annotation, String[] properties, String... values) {
		return addAnnotation(annotation, properties, Factory.toValueExpressionList(this, values));
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation, String[] properties, String... values) {
		return addAnnotation(ClassUtils.getName(annotation), properties, Factory.toValueExpressionList(this, values));
	}

	@Override
	public GAnnotation addAnnotation(GClass annotation, String[] properties, String... values) {
		return addAnnotation(annotation.getFullName(), properties, Factory.toValueExpressionList(this, values));
	}

	@Override
	public GAnnotation addAnnotation(String annotation, String[] properties, Expression... values) {
		GAnnotation result = null;
		result = addAnnotation(annotation);
		if (properties != null && values != null && properties.length == values.length) {
			for (int i = 0; i < properties.length; i++) {
				result.addMember(properties[i], values[i]);
			}
		}
		return result;
	}

	@Override
	public GAnnotation addAnnotation(Class<?> annotation, String[] properties, Expression... values) {
		return addAnnotation(ClassUtils.getName(annotation), properties, values);
	}

	@Override
	public GAnnotation addAnnotation(GClass annotation, String[] properties, Expression... values) {
		return addAnnotation(annotation.getFullName(), properties, values);
	}

	@Override
	public Expression cteNull() {
		return Factory.cteNull(this);
	}

	@Override
	public Expression cte(Object value) {
		return Factory.cteObject(this, value);
	}

	@Override
	public Expression cte(boolean value) {
		return Factory.cte_boolean(this, value);
	}

	@Override
	public Expression cte(int value) {
		return Factory.cte_int(this, value);
	}
	
	@Override
	public Expression cteInteger(int value) {
		return callStatic(Integer.class, "valueOf", cte(value));
	}

	@Override
	public Expression cte(short value) {
		return Factory.cte_short(this, value);
	}

	@Override
	public Expression cte(long value) {
		return Factory.cte_long(this, value);
	}

	@Override
	public Expression cte(double value) {
		return Factory.cte_double(this, value);
	}

	@Override
	public Expression cte(byte value) {
		return Factory.cte_byte(this, value);
	}

	@Override
	public Expression cte(char value) {
		return Factory.cte_char(this, value);
	}

	@Override
	public Expression cteSuper() {
		return Factory.cteSuper(this);
	}

	@Override
	public Expression cteThis() {
		return Factory.cteThis(this);
	}

	@Override
	public Expression cteTrue() {
		return Factory.cteTrue(this);
	}

	@Override
	public Expression cteFalse() {
		return Factory.cteFalse(this);
	}

	@Override
	public Expression cteTRUE() {
		return Factory.cteTRUE(this);
	}

	@Override
	public Expression cteFALSE() {
		return Factory.cteFALSE(this);
	}

	@Override
	public Expression cteClass(Class<?> type) {
		return cteClass(ClassUtils.getName(type));
	}

	@Override
	public Expression cteClass(String type) {
		return Factory.cteClass(this, type);
	}

	@Override
	public Expression cteClass(GClass cg) {
		return cteClass(cg.getFullName());
	}

	
	@Override
	public GAnnotation createAnnotation(String type) {
		return Factory.createAnnotation(this, type);
	}

	@Override
	public GAnnotation createAnnotation(Class<?> type) {
		return createAnnotation(ClassUtils.getName(type));
	}

	@Override
	public GAnnotation createAnnotation(GClass type) {
		return createAnnotation(type.getFullName());
	}

	
	@Override
	public Expression cteAnnotation(GAnnotation annotation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression cteArray(Class<?> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression cteArray(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression cteArray(Class<?> type, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression cteArray(String type, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GAnnotation getAnnotation(Class<?> clazz) {
		return getAnnotation(ClassUtils.getName(clazz));
	}

	@Override
	public GAnnotation getAnnotation(String clazz) {
		return Factory.getAnnotation(this, clazz);
	}

	@Override
	public GAnnotation[] getAnnotations(Class<?> clazz) {
		return getAnnotations(ClassUtils.getName(clazz));
	}

	@Override
	public GAnnotation[] getAnnotations(String clazz) {
		return Factory.getAnnotations(this, clazz);
	}

	@Override
	public boolean hasAnnotation(Class<?> clazz) {
		return hasAnnotation(ClassUtils.getName(clazz));
	}

	@Override
	public boolean hasAnnotation(String clazz) {
		return getAnnotation(clazz) != null;
	}

	@Override
	public Expression debug(String msg) {
		return debug(cte(msg));
	}

	@Override
	public Expression debug(Expression msg) {
		return Factory.debug(this, (ExpressionJdk8u20)msg);
	}

	@Override
	public Expression assign(Expression left, Expression right) {
		return Factory.assign(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)right);
	}
	
	@Override
	public Expression assign(String left, Expression right) {
		return assign(var(left), right);
	}

	@Override
	public Expression assign(String left, String right) {
		return assign(var(left), var(right));
	}

	@Override
	public Expression assign(Expression left, String right) {
		return assign(left, var(right));
	}

	@Override
	public Expression field(String fieldName) {
		return Factory.field(this, fieldName);
	}

	@Override
	public Expression field(GField field) {
		return field(field.getName());
	}

	@Override
	public Expression property(String property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression property(String obj, String property) {
		return property(Factory.toIdentExpression(this, obj), property);
	}

	@Override
	public Expression property(Expression obj, String property) {
		return Factory.property(this, (ExpressionJdk8u20)obj, property);
	}

	@Override
	public Expression realProperty(Expression obj, String property) {
		return property(obj, property);
	}

	
	
	@Override
	public Expression triple(Expression condition, Expression exprTrue,	Expression exprFalse) {
		return Factory.triple(this, (ExpressionJdk8u20)condition, (ExpressionJdk8u20)exprTrue, (ExpressionJdk8u20)exprFalse);
	}

	@Override
	public Expression triple(Expression condition, String varTrue, Expression exprFalse) {
		return triple(condition, var(varTrue), exprFalse);
	}

	@Override
	public Expression triple(Expression condition, Expression exprTrue,	String varFalse) {
		return triple(condition, exprTrue, var(varFalse));
	}

	@Override
	public Expression triple(Expression condition, String varTrue, String varFalse) {
		return triple(condition, var(varTrue), var(varFalse));
	}

	@Override
	public Expression triple(String variable, Expression exprTrue, Expression exprFalse) {
		return triple(var(variable), exprTrue, exprFalse);
	}

	@Override
	public Expression triple(String variable, String varTrue, Expression exprFalse) {
		return triple(var(variable), var(varTrue), exprFalse);
	}

	@Override
	public Expression triple(String variable, Expression exprTrue, String varFalse) {
		return triple(var(variable), exprTrue, var(varFalse));
	}

	@Override
	public Expression triple(String variable, String varTrue, String varFalse) {
		return triple(var(variable), var(varTrue), var(varFalse));
	}

	
	
	@Override
	public Expression call(String obj, String method) {
		return Factory.call(this, obj, method);
	}

	@Override
	public Expression call(String obj, String method, String... variables) {
		return Factory.call(this, obj, method, variables);
	}

	@Override
	public Expression call(String obj, String method, Expression... arguments) {
		return Factory.call(this, obj, method, arguments);
	}

	
	@Override
	public Expression call(Expression obj, String method, String... variables) {
		return Factory.call(this, (ExpressionJdk8u20)obj, method, variables);
	}

	@Override
	public Expression call(Expression obj, String method, Expression... arguments) {
		return Factory.call(this, (ExpressionJdk8u20)obj, method, arguments);
	}

	@Override
	public Expression call(String method, Expression... arguments) {
		return Factory.call(this, method, arguments);
	}

	@Override
	public Expression call(Expression obj, String method) {
		return Factory.call(this, (ExpressionJdk8u20)obj, method);
	}

	@Override
	public Expression call(String method) {
		return Factory.call(this, method);
	}

	@Override
	public Expression callStatic(String type, String method) { 
		return callStatic(type, method, (Expression[])null);
	}

	@Override
	public Expression callStatic(String type, String method, String... variables) {
		return callStatic(type, method, Factory.toIdentExpressionList(this, variables));
	}

	@Override
	public Expression callStatic(String type, String method, Expression... arguments) {
		return Factory.callStatic(this, type, method, arguments);
	}

	@Override
	public Expression callStatic(Class<?> type, String method) {
		return callStatic(ClassUtils.getName(type), method, (Expression[])null);
	}

	@Override
	public Expression callStatic(Class<?> type, String method, String... variables) {
		return callStatic(ClassUtils.getName(type), method, Factory.toIdentExpressionList(this, variables));
	}

	@Override
	public Expression callStatic(Class<?> type, String method, Expression... arguments) {
		return callStatic(ClassUtils.getName(type), method, arguments);
	}

	@Override
	public Expression callStatic(GClass type, String method) {
		return callStatic(type.getFullName(), method, (Expression[])null);
	}

	@Override
	public Expression callStatic(GClass type, String method, String... variables) {
		return callStatic(type.getFullName(), method, Factory.toIdentExpressionList(this, variables));
	}

	@Override
	public Expression callStatic(GClass type, String method, Expression... arguments) {
		return callStatic(type.getFullName(), method, arguments);
	}

	

	@Override
	public Expression var(String name) {
		return Factory.toIdentExpression(this, name);
	}

	
	
	
	@Override
	public Expression and(String... expr) {
		return and(Factory.toIdentExpressionList(this, expr));
	}

	@Override
	public Expression or(String... expr) {
		return or(Factory.toIdentExpressionList(this, expr));
	}

	@Override
	public Expression and(Expression... expr) {
		return Factory.and(this, expr);
	}

	@Override
	public Expression or(Expression... expr) {
		return Factory.or(this, expr);
	}

	@Override
	public Expression not(String variable) {
		return not(Factory.toIdentExpression(this, variable));
	}

	@Override
	public Expression not(Expression expr) {
		return Factory.not(this, (ExpressionJdk8u20)expr);
	}


	
	@Override
	public Expression eq(Expression left, Expression right) {
		return Factory.eq(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)right);
	}

	@Override
	public Expression notNull(Expression left) {
		return Factory.ne(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)cteNull());
	}

	@Override
	public Expression isNull(Expression left) {
		return Factory.eq(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)cteNull());
	}

	@Override
	public Expression notNull(String name) {
		return notNull(var(name));
	}

	@Override
	public Expression isNull(String name) {
		return isNull(var(name));
	}

	@Override
	public Expression ne(Expression left, Expression right) {
		return Factory.ne(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)right);
	}

	@Override
	public Expression gt(Expression left, Expression right) {
		return Factory.gt(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)right);
	}

	@Override
	public Expression lt(Expression left, Expression right) {
		return Factory.lt(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)right);
	}

	@Override
	public Expression ge(Expression left, Expression right) {
		return Factory.ge(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)right);
	}

	@Override
	public Expression le(Expression left, Expression right) {
		return Factory.le(this, (ExpressionJdk8u20)left, (ExpressionJdk8u20)right);
	}

	
	@Override
	public Expression cast(String type, Expression expr) {
		return Factory.cast(this, type, (ExpressionJdk8u20)expr);
	}

	@Override
	public Expression cast(Class<?> type, Expression expr) {
		return cast(ClassUtils.getName(type), expr);
	}

	@Override
	public Expression cast(GClass clazz, Expression expr) {
		return cast(clazz.getFullName(), expr);
	}

	@Override
	public Expression cast(String type, String var) {
		return cast(type, Factory.toIdentExpression(this, var));
	}

	@Override
	public Expression cast(Class<?> type, String var) {
		return cast(ClassUtils.getName(type), Factory.toIdentExpression(this, var));
	}

	@Override
	public Expression cast(GClass clazz, String var) {
		return cast(clazz.getFullName(), Factory.toIdentExpression(this, var));
	}


	
	
	
	
	
	
	@Override
	public Expression enumValue(Class<?> type, String enumValue) {
		return enumValue(ClassUtils.getName(type), enumValue);
	}

	@Override
	public Expression enumValue(String type, String enumValue) {
		return Factory.enumValue(this, type, enumValue);
	}

	@Override
	public Expression clazz(String type) {
		return Factory.clazz(this, type);
	}

	@Override
	public Expression clazz(Class<?> type) {
		return clazz(ClassUtils.getName(type));
	}

	@Override
	public Expression clazz(GClass clazz) {
		return clazz(clazz.getFullName());
	}

	
	
	@Override
	public Expression array(Expression... expressions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression parameterizeType(String type, String... params) {
		return Factory.parameterizeType(this, type, params);
	}
	
	@Override
	public Expression parameterizeType(String type, Class<?>... params) {
		return parameterizeType(type, toArrayNames(params));
	}
	
	@Override
	public Expression parameterizeType(String type, GClass... params) {
		return parameterizeType(type, toArrayNames(params));
	}
	
	protected String[] toArrayNames (GClass... classes) {
		String[] result = null;
		if (classes != null) {
			result = new String[classes.length];
			for (int i = 0; i < classes.length; i++) {
				result[i] = classes[i].getFullName();
			}
		}
		return result;
	}
	
	protected String[] toArrayNames (Class<?>... classes) {
		String[] result = null;
		if (classes != null) {
			result = new String[classes.length];
			for (int i = 0; i < classes.length; i++) {
				result[i] = ClassUtils.getName(classes[i]);
			}
		}
		return result;
	}
	
	
	@Override
	public Expression parameterizeType(Class<?> type, String... params) {
		return parameterizeType(ClassUtils.getName(type), params);
	}
	
	@Override
	public Expression parameterizeType(Class<?> type, Class<?>... params) {
		return parameterizeType(ClassUtils.getName(type), params);
	}
	
	@Override
	public Expression parameterizeType(Class<?> type, GClass... params) {
		return parameterizeType(ClassUtils.getName(type), params);
	}
	
	@Override
	public Expression parameterizeType(GClass type, String... params) {
		return parameterizeType(type.getFullName(), params);
	}
	
	@Override
	public Expression parameterizeType(GClass type, Class<?>... params) {
		return parameterizeType(type.getFullName(), params);
	}
	
	@Override
	public Expression parameterizeType(GClass type, GClass... params) {
		return parameterizeType(type.getFullName(), params);
	}
	
}
