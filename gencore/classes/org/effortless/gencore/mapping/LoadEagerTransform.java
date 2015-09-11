package org.effortless.gencore.mapping;

import org.effortless.jast.GClass;
import org.effortless.jast.GNode;

public class LoadEagerTransform extends LoadLevelsTransform {

	public LoadEagerTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_loadEager(clazz);
	}


	/*

	protected void _loadEager (Object target, DbManager db, ResultSet rs) {
		super._loadEager(target, db, rs);
		Ciudad result = (Ciudad)target;

		result.nombre = (String)__DEFINITION__.loadValue("NOMBRE", rs);
		result._setupLoaded("nombre");
		
		Long _provinciaId = (Long)__DEFINITION__.loadValue("PROVINCIA_ID", rs);
		result.provincia = (_provinciaId != null ? new Provincia(_provinciaId) : null);
		result._setupLoaded("provincia");
	}

	 */
	protected void add_loadEager (GClass cg) {
		add_loadEagerLazy(cg, true);
	}


}
