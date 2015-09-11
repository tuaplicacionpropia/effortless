package org.effortless.gencore.javabean;

import java.util.List;

import org.effortless.jast.transforms.StageTransform;
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


	protected void doEquals(org.apache.commons.lang.builder.EqualsBuilder eqBuilder, Object obj) {
		super.doEquals(eqBuilder, obj);
		eqBuilder.append(this.getNombre(), ((Ciudad)obj).getNombre());
	}


	 */
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
		List<GField> fields = clazz.getProperties();
		
//		GMethod mg = null;//clazz.addMethod("doEquals").setProtected(true).addParameter(org.apache.commons.lang3.builder.EqualsBuilder.class, "eqBuilder").addParameter(Object.class, "obj");
		GMethod mg = clazz.addMethod("doEquals").setProtected(true).addParameter(org.apache.commons.lang.builder.EqualsBuilder.class, "builder").addParameter(Object.class, "obj");
		mg.add(mg.call(mg.cteSuper(), "doEquals", "builder", "obj"));
		for (GField field : fields) {
			String fName = field.getName();
			String getterName = field.getGetterName();
			mg.add(mg.call("builder", "append", mg.call(getterName), mg.call(mg.cast(clazz, "obj"), getterName)));
		}
		}
	}

}
