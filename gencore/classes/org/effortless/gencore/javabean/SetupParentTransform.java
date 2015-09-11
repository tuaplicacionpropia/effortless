package org.effortless.gencore.javabean;

import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

//@StageTransform("runModel")
public class SetupParentTransform extends Object implements Transform {

	public SetupParentTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass cg = (GClass)node;
		if (true && cg != null && !cg.checkEnum()) {
			GMethod mg = null;
			{
/*
	public Ciudad () {
		super();
	}
	
*/
	
				mg = cg.addConstructor().setPublic(true);
				mg.add(mg.callSuper());

			}
		}
	}
	
}
