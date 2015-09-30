package org.effortless.gencore;

import org.effortless.gencore.ui.FinderFilterTransform;
import org.effortless.jast.GClass;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.StageTransform;
import org.effortless.jast.transforms.Transform;
import org.effortless.orm.Entity;


//import org.effortless.ann.StageTransform;
//import org.effortless.fastsql.Entity;
//import org.effortless.gmodel.Transforms;
//import org.effortless.gweb.transforms.ui.BottomActionsFinderTemplateTransform;
//import org.effortless.gweb.transforms.ui.EditorWindowTransform;
//import org.effortless.gweb.transforms.ui.FinderFilterWindowTransform;
//import org.effortless.gweb.transforms.ui.FinderWindowTransform;
//import org.effortless.gweb.transforms.ui.InfoWindowTransform;
//import org.effortless.gweb.transforms.ui.ListFieldTemplateTransform;
//import org.effortless.jast.GClass;
//import org.effortless.jast.GMethod;
//import org.effortless.jast.GNode;
//import org.effortless.jast.Transform;
//import org.effortless.ui.zk.UiManager;

@StageTransform("preView")
public class UiManagerTransform extends Object implements Transform {

	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(Entity.class) && !clazz.isInner()) {
			{
				FinderFilterTransform step = new FinderFilterTransform();
				step.process(clazz);
			}
		}
	}
	
}
