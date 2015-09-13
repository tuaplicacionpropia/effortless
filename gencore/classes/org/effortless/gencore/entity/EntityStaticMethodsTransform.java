package org.effortless.gencore.entity;

import org.effortless.jast.transforms.StageTransform;
import org.effortless.core.ClassUtils;
import org.effortless.orm.AbstractEntity;
import org.effortless.orm.Filter;
import org.effortless.orm.impl.EntityFilter;
import org.effortless.jast.Expression;
import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

//@StageTransform("runModel")
public class EntityStaticMethodsTransform extends Object implements Transform {

	public EntityStaticMethodsTransform () {
		super();
	}
	
	/**
	 * 
	public static Filter<Ciudad> listAll () {
		return Ciudad._doListBy(__DEFINITION__);
	}

	public static Ciudad find (String value) {
		return Ciudad.listAll().eq("nombre", value).first();
	}
	 * 
	 * @param clazz
	 * @param sourceUnit
	 */
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
		addListAll(clazz);
		
		
//		String methodName = "listBy";
//		
//		Expression arguments = new ArgumentListExpression(new Expression[] {new ClassExpression(clazz)});
//		StaticMethodCallExpression call = new StaticMethodCallExpression(new ClassNode(AbstractIdEntity.class), "listBy", arguments);
//		ReturnStatement returnCode = new ReturnStatement(call);
//		ClassNode returnType = new ClassNode(Filter.class);
////		returnType.setUsingGenerics(true);
////		returnType.setGenericsTypes(new GenericsType[] {new GenericsType(clazz)});
//		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, returnType, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, returnCode);
//
//		clazz.addMethod(method);
		}
	}

//	public static org.effortless.model.Filter<org.effortless.model.AbstractIdEntity> listBy () {
//		
//		return AbstractIdEntity.listBy(AllBasicProperties.class);
//	}

	/*
	public static Filter listAll () {
		return EntityFilter.buildEntityFilter(Person.__DEFINITION__, Person.__DEFINITION__.getDefaultLoader());
	}
		
	 */
	protected void addListAll(GClass cg) {
		String returnType = ClassUtils.getName(Filter.class);
//		Expression returnType = cg.parameterizeType(Filter.class, cg);
//		GMethod mg = cg.addMethod("listAll").setPublic(true).setStatic(true).setReturnType(cg.createGenericType(Filter.class, cg.getClassNode()));
		
		GMethod mg = cg.addMethod("listAll").setPublic(true).setStatic(true).setReturnType(returnType);
//		mg.addReturn(mg.callStatic(cg.getClassNode(), "_doListBy", mg.property(mg.cteClass(cg.getClassNode()), "__DEFINITION__")));
//		mg.addReturn(mg.callStatic(AbstractEntity.class, "_doListBy", mg.realProperty(mg.cteClass(cg.getClassNode()), "__DEFINITION__")));

		mg.addReturn(mg.callStatic(EntityFilter.class, "buildEntityFilter", mg.realProperty(mg.clazz(cg), "__DEFINITION__"), mg.call(mg.realProperty(mg.clazz(cg), "__DEFINITION__"), "getDefaultLoader")));
//		mg.addReturn(mg.cast(returnType, mg.call(mg.clazz(AbstractEntity.class), "_doListBy", mg.realProperty(mg.clazz(cg), "__DEFINITION__"))));
	}

//	/*
//	public static Filter<Ciudad> listAll () {
//		return Ciudad._doListBy(__DEFINITION__);
//	}
// */
//protected void addListAll(GClass cg) {
//	String returnType = ClassUtils.getName(Filter.class) + "<" + cg.getPackageName() + "." + cg.getName() + ">";
////	Expression returnType = cg.parameterizeType(Filter.class, cg);
////	GMethod mg = cg.addMethod("listAll").setPublic(true).setStatic(true).setReturnType(cg.createGenericType(Filter.class, cg.getClassNode()));
//	
//	GMethod mg = cg.addMethod("listAll").setPublic(true).setStatic(true).setReturnType(returnType);
////	mg.addReturn(mg.callStatic(cg.getClassNode(), "_doListBy", mg.property(mg.cteClass(cg.getClassNode()), "__DEFINITION__")));
////	mg.addReturn(mg.callStatic(AbstractEntity.class, "_doListBy", mg.realProperty(mg.cteClass(cg.getClassNode()), "__DEFINITION__")));
//	
//	mg.addReturn(mg.cast(returnType, mg.call(mg.clazz(AbstractEntity.class), "_doListBy", mg.realProperty(mg.clazz(cg), "__DEFINITION__"))));
//}
	
}
