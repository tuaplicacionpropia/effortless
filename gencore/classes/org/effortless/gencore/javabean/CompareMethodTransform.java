package org.effortless.gencore.javabean;

import java.util.List;

import org.effortless.jast.transforms.StageTransform;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

//@StageTransform("runModel")
public class CompareMethodTransform extends Object implements Transform {

	public CompareMethodTransform () {
		super();
	}
	
	
/*

	protected void doCompare(org.apache.commons.lang.builder.CompareToBuilder cpBuilder, Object obj) {
		super.doCompare(cpBuilder, obj);
		cpBuilder.append(this.getNombre(), ((Ciudad)obj).getNombre());
	}


 *
 */
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
			List<GField> fields = clazz.getProperties();
	
	//		GMethod mg = null;//clazz.addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang3.builder.CompareToBuilder.class, "cpBuilder").addParameter(Object.class, "obj");
			GMethod mg = clazz.addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang.builder.CompareToBuilder.class, "builder").addParameter(Object.class, "obj");
			mg.add(mg.call(mg.cteSuper(), "doCompare", "builder", "obj"));
			for (GField field : fields) {
				String fName = field.getName();
	//			mg.add(mg.call("builder", "append", mg.field(fName), mg.property("obj", fName)));
				String getterName = field.getGetterName();
				mg.add(mg.call("builder", "append", mg.call(getterName), mg.call(mg.cast(clazz, "obj"), getterName)));
			}
		}
	}

}
