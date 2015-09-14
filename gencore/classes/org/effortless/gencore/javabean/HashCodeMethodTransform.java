package org.effortless.gencore.javabean;

import java.util.List;

import org.effortless.gencore.InfoModel;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;
import org.effortless.jast.transforms.StageTransform;
//import org.effortless.gmodel.InfoModel;

//@StageTransform("runModel")
public class HashCodeMethodTransform extends Object implements Transform {

	public HashCodeMethodTransform () {
		super();
	}
	
	/*

	protected int doHashCode(int hash, int mult) {
		int result = hash;
		result = super.doHashCode(result, mult);
		result = _doHashCode(result, mult, "id", this.id);
		result = _doHashCode(result, mult, "version", this.version);
		result = _doHashCode(result, mult, "deleted", this.deleted);
		return result;
	}

	 */
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
			List<GField> fields = InfoModel.listNotNullUnique(clazz);
			if (fields != null && fields.size() > 0) {
//		GMethod mg = null;//clazz.addMethod("doHashCode").setProtected(true).addParameter(org.apache.commons.lang3.builder.HashCodeBuilder.class, "hcBuilder");
				GMethod mg = clazz.addMethod("doHashCode").setProtected(true).setReturnType("int").addParameter("int", "hash").addParameter("int", "mult");
				mg.declVariable("int", "result", mg.var("hash"));
				mg.add(mg.assign("result", mg.call(mg.cteSuper(), "doHashCode", mg.var("result"), mg.var("mult"))));
				for (GField field : fields) {
					String fName = field.getName();
					mg.add(mg.assign("result", mg.call("_doHashCode", mg.var("result"), mg.var("mult"), mg.cte(fName), mg.field(field))));
//					mg.add(mg.call("builder", "append", mg.cte(fName)));
//					mg.add(mg.call("builder", "append", mg.field(fName)));
				}
				mg.addReturn("result");
			}
		}
	}
	
//	/*
//
//	protected void doHashCode(org.apache.commons.lang.builder.HashCodeBuilder hcBuilder) {
//		super.doHashCode(hcBuilder);
//		hcBuilder.append(this.getNombre());
//	}
//
//
//	 */
//	
//	@Override
//	public void process(GNode node) {
//		GClass clazz = (GClass)node;
//		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
//		List<GField> fields = InfoModel.listNotNullUnique(clazz);
//			
////		GMethod mg = null;//clazz.addMethod("doHashCode").setProtected(true).addParameter(org.apache.commons.lang3.builder.HashCodeBuilder.class, "hcBuilder");
//		GMethod mg = clazz.addMethod("doHashCode").setProtected(true).addParameter(org.apache.commons.lang.builder.HashCodeBuilder.class, "builder");
//		mg.add(mg.call(mg.cteSuper(), "doHashCode", "builder"));
//		for (GField field : fields) {
//			String fName = field.getName();
//			mg.add(mg.call("builder", "append", mg.cte(fName)));
//			mg.add(mg.call("builder", "append", mg.field(fName)));
//		}
//		}
//	}

}
