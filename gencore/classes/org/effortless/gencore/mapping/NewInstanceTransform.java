package org.effortless.gencore.mapping;

import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;

public class NewInstanceTransform extends AbstractBaseTransform {

	public NewInstanceTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_newInstance(clazz);
	}

	/*

	protected Object _newInstance () {
		return new Ciudad();
	}

	 */
	protected void add_newInstance (GClass cg) {
		GMethod mg = cg.addMethod("_newInstance").setProtected(true).setReturnType(Object.class);
		mg.addReturn(mg.newObject(cg));
//		mg.addReturn(mg.cteNull());
	}


	
}
