package org.effortless.gencore.javabean;

import java.util.List;

import org.effortless.jast.transforms.StageTransform;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;
import org.effortless.orm.AbstractIdEntity;

//@StageTransform("runModel")
public class CompareMethodTransform extends Object implements Transform {

	public CompareMethodTransform () {
		super();
	}
	
	/*
	 * 


	protected int doCompareTo (Object obj) {
		int result = 0;
		AbstractIdEntity _obj = (AbstractIdEntity)obj;
		result = (result == 0 ? super.doCompareTo(_obj) : result);
		result = (result == 0 ? _doCompareTo(this.id, _obj.id) : result);
		result = (result == 0 ? _doCompareTo(this.version, _obj.version) : result);
		result = (result == 0 ? _doCompareTo(this.deleted, _obj.deleted) : result);
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
				GMethod mg = clazz.addMethod("doCompareTo").setProtected(true).setReturnType("int").addParameter(Object.class, "obj");
				mg.declVariable("int", "result", mg.cte((int)0));
				mg.declVariable(clazz, "_obj", mg.cast(clazz, mg.var("obj")));
				mg.add(mg.assign(mg.var("result"), mg.triple(mg.eq(mg.var("result"), mg.cte((int)0)), mg.call(mg.cteSuper(), "doCompareTo", mg.var("_obj")), mg.var("result"))));
				for (GField field : fields) {
					String fName = field.getName();
					mg.add(mg.assign(mg.var("result"), mg.triple(mg.eq(mg.var("result"), mg.cte((int)0)), mg.call("_doCompareTo", mg.field(field), mg.property(mg.var("_obj"), fName)), mg.var("result"))));
				}
			}
		}
	}
	
	
	
	
	
///*
//
//	protected void doCompare(org.apache.commons.lang.builder.CompareToBuilder cpBuilder, Object obj) {
//		super.doCompare(cpBuilder, obj);
//		cpBuilder.append(this.getNombre(), ((Ciudad)obj).getNombre());
//	}
//
//
// *
// */
//	
//	@Override
//	public void process(GNode node) {
//		GClass clazz = (GClass)node;
//		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
//			List<GField> fields = clazz.getProperties();
//	
//	//		GMethod mg = null;//clazz.addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang3.builder.CompareToBuilder.class, "cpBuilder").addParameter(Object.class, "obj");
//			GMethod mg = clazz.addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang.builder.CompareToBuilder.class, "builder").addParameter(Object.class, "obj");
//			mg.add(mg.call(mg.cteSuper(), "doCompare", "builder", "obj"));
//			for (GField field : fields) {
//				String fName = field.getName();
//	//			mg.add(mg.call("builder", "append", mg.field(fName), mg.property("obj", fName)));
//				String getterName = field.getGetterName();
//				mg.add(mg.call("builder", "append", mg.call(getterName), mg.call(mg.cast(clazz, "obj"), getterName)));
//			}
//		}
//	}

}
