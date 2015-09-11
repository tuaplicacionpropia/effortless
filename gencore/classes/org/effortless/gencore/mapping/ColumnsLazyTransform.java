package org.effortless.gencore.mapping;

import org.effortless.jast.GClass;
import org.effortless.jast.GNode;

public class ColumnsLazyTransform extends ColumnsLevelsTransform {

	public ColumnsLazyTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_columnsLazy(clazz);
	}

	/*

	protected String _columnsLazy () {
		return _concatPropertiesLoader("CCOMMENT", super._columnsLazy());
	}

	 */
	protected void add_columnsLazy (GClass cg) {
		add_columnsEagerLazy(cg, false);
	}
	
}
