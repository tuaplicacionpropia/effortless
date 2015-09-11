package org.effortless.gencore.entity;

import org.effortless.jast.GClass;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

public class SetupEntityTransform implements Transform {

	@Override
	public void process(GNode _node) {
		GClass node = (GClass)_node;
		if (node != null && !node.isType(org.effortless.orm.Entity.class) && !node.isEnum()) {
			node.setSuperClass(org.effortless.orm.AbstractIdEntity.class);
		}
	}

}
