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
public class ToStringMethodTransform extends Object implements Transform {

	public ToStringMethodTransform () {
		super();
	}
	
	/*


	protected void doToString(org.apache.commons.lang.builder.ToStringBuilder toStringBuilder) {
		super.doToString(toStringBuilder);
		toStringBuilder.append("nombre", this.getNombre());
	}


	 */
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
			List<GField> fields = InfoModel.listNotNullUnique(clazz);
			
	//		GMethod mg = null;//clazz.addMethod("doToString").setProtected(true).addParameter(org.apache.commons.lang3.builder.ToStringBuilder.class, "toStringBuilder");
			GMethod mg = clazz.addMethod("doToString").setProtected(true).addParameter(org.apache.commons.lang.builder.ToStringBuilder.class, "builder");
			
			mg.add(mg.call(mg.cteSuper(), "doToString", "builder"));
			for (GField field : fields) {
				String fName = field.getName();
				String getterName = field.getGetterName();
				mg.add(mg.call("builder", "append", mg.cte(fName), mg.call(getterName)));
			}
		}
	}

}
