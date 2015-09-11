package org.effortless.gencore;

import org.effortless.jast.transforms.StageTransform;
import org.effortless.gencore.mapping.*;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

@StageTransform(value="runModel", after="JavaBeanTransform")
public class MappingTransform implements Transform {

	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		if (clazz != null && clazz.isType(org.effortless.orm.Entity.class)) {

//			{
//				DeclareDefinitionTransform step = new DeclareDefinitionTransform();
//				step.process(node);
//			}
			
			{
				MappingCtesTransform step = new MappingCtesTransform();
				step.process(node);
			}
			
			{
				DefinitionTransform step = new DefinitionTransform();
				step.process(node);
			}
			
			{
				AllParameterChangesTransform step = new AllParameterChangesTransform();
				step.process(node);
			}
			
			{
				ColumnsEagerTransform step = new ColumnsEagerTransform();
				step.process(node);
			}
			
			{
				ColumnsLazyTransform step = new ColumnsLazyTransform();
				step.process(node);
			}
			
			{
				LoadEagerTransform step = new LoadEagerTransform();
				step.process(node);
			}
			
			{
				LoadLazyTransform step = new LoadLazyTransform();
				step.process(node);
			}
			
			{
				NewInstanceTransform step = new NewInstanceTransform();
				step.process(node);
			}
			
			{
				PersistTransform step = new PersistTransform();
				step.process(node);
			}
			
			{
				GetEntityDefinitionTransform step = new GetEntityDefinitionTransform();
				step.process(node);
			}
	
		}
	}

}
