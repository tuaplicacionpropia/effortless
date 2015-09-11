package org.effortless.gencore.entity;

import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;
import org.effortless.orm.AbstractEnabledPersistEntity;
import org.effortless.orm.AbstractIdEntity;

//@StageTransform("runModel")
public class SetupParentTransform extends Object implements Transform {

	public SetupParentTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass cg = (GClass)node;
		if (true && cg != null && cg.isType(org.effortless.orm.Entity.class) && !cg.checkEnum()) {
			setupParent(cg);
			GMethod mg = null;
			{
/*
	public Ciudad (Long id) {
		super(id);
	} 
*/
	
				if (true) {
				mg = cg.addConstructor().setPublic(true).addParameter(Long.class, "id");
				mg.add(mg.callSuper(mg.var("id")));
				}
			}
		}
	}
	
	 //extends AbstractIdEntity<AllBasicProperties>		
	protected void setupParent (GClass cg) {
		String superClass = cg.getSuperClass();
		if (superClass == null || "java.lang.Object".equals(superClass)) {
			if (cg.hasDataClass("EnabledEntity")) {
				superClass = AbstractEnabledPersistEntity.class.getName();
			}
			else {
				superClass = AbstractIdEntity.class.getName();
			}
			cg.setSuperClass(superClass);
		}		
	}
	
}
