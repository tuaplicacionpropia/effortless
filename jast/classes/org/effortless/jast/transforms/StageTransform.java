package org.effortless.jast.transforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@java.lang.annotation.Documented
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@org.atteo.classindex.IndexAnnotated
public @interface StageTransform {

	public String value();
	
	public boolean ignore() default false;
	
	public String after() default "";

	public String before() default "";
	
	
//	public boolean inner();
	
}
