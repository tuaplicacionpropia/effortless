package org.effortless.gencore.mapping;

import org.effortless.orm.definition.EntityDefinition;
import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;

public class GetEntityDefinitionTransform extends AbstractBaseTransform {

	public GetEntityDefinitionTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_doGetEntityDefinition(clazz);
	}


	/*

	protected EntityDefinition _doGetEntityDefinition() {
		return FileEntity.__DEFINITION__;
	}



	 */
	
	public void add_doGetEntityDefinition (GClass clazz) {
		GMethod mg = clazz.addMethod("_doGetEntityDefinition").setProtected(true).setReturnType(EntityDefinition.class);
		mg.addReturn(mg.property(mg.clazz(clazz), "__DEFINITION__"));
//		mg.addReturn(mg.property(clazz.getNameWithoutPackage(), "__DEFINITION__"));
	}
	
	
}
