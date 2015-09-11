package org.effortless.gencore.mapping;

import org.effortless.jast.GClass;
import org.effortless.jast.GNode;

public class LoadLazyTransform extends LoadLevelsTransform {

	public LoadLazyTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_loadLazy(clazz);
	}

	/*

	protected void _loadLazy (Object target, DbManager db, ResultSet rs) {
		super._loadLazy(target, db, rs);
		Ciudad result = (Ciudad)target;

		result.comentario = (String)__DEFINITION__.loadValue("CCOMMENT", rs);
		result._setupLoaded("comentario");
	}

	 */
	protected void add_loadLazy (GClass cg) {
		add_loadEagerLazy(cg, false);
	}



}
