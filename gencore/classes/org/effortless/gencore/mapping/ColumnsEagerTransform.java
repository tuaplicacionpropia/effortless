package org.effortless.gencore.mapping;

import java.util.List;

import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;

public class ColumnsEagerTransform extends ColumnsLevelsTransform {

	public ColumnsEagerTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_columnsEager(clazz);
	}

	/*

	protected String _columnsEager () {
		return _concatPropertiesLoader("NOMBRE, PROVINCIA_ID", super._columnsEager());
	}

	 */
	protected void add_columnsEager (GClass cg) {
		add_columnsEagerLazy(cg, true);
	}
	
	
	
}
