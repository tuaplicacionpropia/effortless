package org.effortless.gencore;

import org.effortless.gencore.ui.EditorTransform;
import org.effortless.gencore.ui.FinderFilterTransform;
import org.effortless.gencore.ui.FinderTransform;
import org.effortless.jast.GClass;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.StageTransform;
import org.effortless.jast.transforms.Transform;
import org.effortless.orm.Entity;


@StageTransform("runView")
public class ScreensUiTransform extends Object implements Transform {

	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(Entity.class)) {
			{
				FinderTransform step = new FinderTransform();
				step.process(clazz);
			}
			{
				EditorTransform step = new EditorTransform();
				step.process(clazz);
			}
		}
	}
	
}
