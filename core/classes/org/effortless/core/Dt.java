package org.effortless.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;

@java.lang.annotation.Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.ANNOTATION_TYPE, 
	ElementType.CONSTRUCTOR, 
	ElementType.FIELD, 
	ElementType.LOCAL_VARIABLE, 
	ElementType.METHOD, 
	ElementType.PACKAGE, 
	ElementType.TYPE, 
	ElementType.TYPE_PARAMETER, 
	ElementType.TYPE_USE})
@Inherited
@Repeatable(DtContainer.class)
public @interface Dt {
/*
 * 
@Target({ElementType.TYPE,ElementType.METHOD,
 ElementType.CONSTRUCTOR,ElementType.ANNOTATION_TYPE,
 ElementType.PACKAGE,ElementType.FIELD,ElementType.LOCAL_VARIABLE}) * 
 */
}
