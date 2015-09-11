package org.effortless.gencore.mapping;

import org.effortless.orm.EagerPropertiesLoader;
import org.effortless.orm.FileEntity;
import org.effortless.orm.LazyPropertiesLoader;
import org.effortless.jast.Expression;
import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;
import org.effortless.orm.definition.EntityDefinition;

public class FastFileEntityTransform extends Object {

	public FastFileEntityTransform () {
		super();
	}
	
	public void process(GClass cg) {
//		FileEntity
//		AbstractBaseTransform.add_doGetEntityDefinition(cg);		
		
		cg.setSuperClass(FileEntity.class);
		
		GMethod mg = null;
		
		mg = cg.addConstructor().setPublic(true);
		mg.add(mg.callSuper());

		mg = cg.addConstructor().setPublic(true).addParameter(Long.class, "id");
		mg.add(mg.callSuper(mg.var("id")));
		
		
/*
	protected Object _newInstance () {
		return new FileEntity();
	}
 */
		mg = cg.addMethod("_newInstance").setProtected(true).setReturnType(Object.class);
		mg.addReturn(mg.newObject(cg));
		

/*

	protected static final FileEntity _pivot = new FileEntity();
*/
	
		cg.addField(cg, "_pivot", cg.newObject(cg)).setProtected(true).setStatic(true).setFinal(true);
		
		
/*
	protected static final String _TABLE = "FILES";
	protected static final String _SEQ = _TABLE + "_SEQ";
	public static final EntityDefinition __DEFINITION__ = new EntityDefinition()
		.setTableName(_TABLE)
		.setSequenceName(_SEQ)
		.addParent(FileEntity.__DEFINITION__)
		.setDefaultLoader(new EagerPropertiesLoader(FileEntity._pivot))
		.addLoader(new LazyPropertiesLoader(FileEntity._pivot));
*/
		
		
		{
			Expression expr = null;
			expr = cg.newObject(EntityDefinition.class);
			
			String cgName = cg.getSimpleName();
			cg.addField(String.class, "_TABLE", cg.cte(cgName)).setProtected(true).setStatic(true).setFinal(true);
			
			cg.addField(String.class, "_SEQ", cg.cte(cgName + "_SEQ")).setProtected(true).setStatic(true).setFinal(true);
			
	if (true) {
			expr = cg.call(expr, "setTableName", cg.property(cg.cteClass(cg), "_TABLE"));
			expr = cg.call(expr, "setSequenceName", cg.property(cg.cteClass(cg), "_SEQ"));
			expr = cg.call(expr, "addParent", cg.property(cg.cteClass(FileEntity.class), "__DEFINITION__"));
			
			expr = cg.call(expr, "setDefaultLoader", cg.newObject(EagerPropertiesLoader.class, cg.property(cg.cteClass(cg), "_pivot")));
			expr = cg.call(expr, "addLoader", cg.newObject(LazyPropertiesLoader.class, cg.property(cg.cteClass(cg), "_pivot")));
	}
			cg.addField(EntityDefinition.class, "__DEFINITION__", expr).setPublic(true).setStatic(true).setFinal(true);
		}		
		
/*
		
		
	
	public static Filter<FileEntity> listAll () {
		return (Filter<FileEntity>)EntityFilter.buildEntityFilter(__DEFINITION__);
	}
	
 */
		
	}
	
}
