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

	protected void doHashCode(org.apache.commons.lang.builder.HashCodeBuilder hcBuilder) {
		super.doHashCode(hcBuilder);
		hcBuilder.append(this.getNombre());
	}


	 */
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
		List<GField> fields = InfoModel.listNotNullUnique(clazz);
			
//		GMethod mg = null;//clazz.addMethod("doHashCode").setProtected(true).addParameter(org.apache.commons.lang3.builder.HashCodeBuilder.class, "hcBuilder");
		GMethod mg = clazz.addMethod("doHashCode").setProtected(true).addParameter(org.apache.commons.lang.builder.HashCodeBuilder.class, "builder");
		mg.add(mg.call(mg.cteSuper(), "doHashCode", "builder"));
		for (GField field : fields) {
			String fName = field.getName();
			mg.add(mg.call("builder", "append", mg.cte(fName)));
			mg.add(mg.call("builder", "append", mg.field(fName)));
		}
		}
	}

}
