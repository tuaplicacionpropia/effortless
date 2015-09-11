package org.effortless.gencore.javabean;

import java.util.List;

import org.effortless.jast.transforms.StageTransform;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

//@StageTransform("runModel")
public class InitiateMethodTransform extends Object implements Transform {

	public InitiateMethodTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {
		List fields = clazz.getProperties();
		int fieldsSize = (fields != null ? fields.size() : 0);
		if (fieldsSize > 0) {
			GMethod mg = clazz.addMethod("initiate").setProtected(true);
			
			mg.add(mg.call(mg.cteSuper(), "initiate"));
			for (int i = 0; i < fieldsSize; i++) {
				GField field = (GField)fields.get(i);
				String methodName = field.getInitiateName();
//if (!methodName.equals("initiatePerfiles")) {
				mg.add(mg.call(mg.cteThis(), methodName));
//}
			}
		}
		}
	}

}
