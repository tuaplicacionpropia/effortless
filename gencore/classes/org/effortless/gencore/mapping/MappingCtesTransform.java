package org.effortless.gencore.mapping;

import org.effortless.jast.GClass;
import org.effortless.jast.GNode;

public class MappingCtesTransform extends AbstractBaseTransform {

	public MappingCtesTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		addCtes(clazz);
	}


	/*
	 * 
	protected static final Ciudad _pivot = new Ciudad();
	
	protected static final String _TABLE = "CIUDAD";
	protected static final String _SEQ = _TABLE + "_SEQ";
	 * 
	 */
	protected void addCtes (GClass cg) {
		String cgName = cg.getSimpleName();
		cg.addField(cg, "_pivot", cg.newObject(cg)).setProtected(true).setStatic(true).setFinal(true);
//		cg.addField(cg.getClassNode(), "_pivot", cg.cteNull());
		
		cg.addField("String", "_TABLE", cg.cte(cgName)).setProtected(true).setStatic(true).setFinal(true);
		
		cg.addField("String", "_SEQ", cg.cte(cgName + "_SEQ")).setProtected(true).setStatic(true).setFinal(true);
	}
	
	
}
