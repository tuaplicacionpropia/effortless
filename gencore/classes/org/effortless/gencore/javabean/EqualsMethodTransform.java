package org.effortless.gencore.javabean;

import java.util.List;

import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

//@StageTransform("runModel")
public class EqualsMethodTransform extends Object implements Transform {

	public EqualsMethodTransform () {
		super();
	}
	
	
	/*
	 * 

	protected boolean doEquals(Object obj) {
		boolean result = true;
		AbstractEnabledPersistEntity _obj = (AbstractEnabledPersistEntity)obj;
		result = result && _doEquals("enabled", this.enabled, _obj, _obj.enabled);
		return result;
	}
	


	 * 
	 */
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
			List<GField> fields = clazz.getProperties();
			if (fields != null && fields.size() > 0) {
				GMethod mg = clazz.addMethod("doEquals").setProtected(true).setReturnType("boolean").addParameter(Object.class, "obj");
				mg.declVariable("boolean", "result", mg.cteTrue());
				mg.declVariable(clazz, "_obj", mg.cast(clazz, mg.var("obj")));
				for (GField field : fields) {
					String fName = field.getName();
//					mg.add(mg.assignOp(mg.var("result"), "&=", mg.call("_doEquals", mg.cte(fName), mg.field(field), mg.var("_obj"), mg.property(mg.var("_obj"), fName))));
					mg.add(mg.assign(mg.var("result"), mg.and(mg.var("result"), mg.call("_doEquals", mg.cte(fName), mg.field(field), mg.var("_obj"), mg.property(mg.var("_obj"), fName)))));
					
				}
				mg.addReturn("result");
			}
		}
	}

	
//	/*
//
//
//	protected void doEquals(org.apache.commons.lang.builder.EqualsBuilder eqBuilder, Object obj) {
//		super.doEquals(eqBuilder, obj);
//		eqBuilder.append(this.getNombre(), ((Ciudad)obj).getNombre());
//	}
//
//
//	 */
//	
//	@Override
//	public void process(GNode node) {
//		GClass clazz = (GClass)node;
//		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
//		List<GField> fields = clazz.getProperties();
//		
////		GMethod mg = null;//clazz.addMethod("doEquals").setProtected(true).addParameter(org.apache.commons.lang3.builder.EqualsBuilder.class, "eqBuilder").addParameter(Object.class, "obj");
//		GMethod mg = clazz.addMethod("doEquals").setProtected(true).addParameter(org.apache.commons.lang.builder.EqualsBuilder.class, "builder").addParameter(Object.class, "obj");
//		mg.add(mg.call(mg.cteSuper(), "doEquals", "builder", "obj"));
//		for (GField field : fields) {
//			String fName = field.getName();
//			String getterName = field.getGetterName();
//			mg.add(mg.call("builder", "append", mg.call(getterName), mg.call(mg.cast(clazz, "obj"), getterName)));
//		}
//		}
//	}

}
