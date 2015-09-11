package org.effortless.gencore;

import org.effortless.gencore.entity.EntityStaticMethodsTransform;
import org.effortless.gencore.entity.SavePropertiesTransform;
import org.effortless.gencore.entity.SetupOwnerInnerEntity;
import org.effortless.gencore.entity.SetupParentTransform;
import org.effortless.jast.transforms.StageTransform;
import org.effortless.jast.GClass;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

@StageTransform(value="runModel", after="MappingTransform")
public class EntityTransform implements Transform {

	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;

		{
			SetupOwnerInnerEntity step = new SetupOwnerInnerEntity();
			step.process(clazz);
		}
		
		{
			EntityStaticMethodsTransform step = new EntityStaticMethodsTransform();
			step.process(clazz);
		}
		
		{
			SavePropertiesTransform step = new SavePropertiesTransform();
			step.process(clazz);
		}
		
		{
			SetupParentTransform step = new SetupParentTransform();
			step.process(clazz);
		}
		
/*
 * 

@StageTransform("preModel")
public class SetupOwnerInnerEntity extends Object implements Transform {

@StageTransform("runModel")
public class EntityStaticMethodsTransform extends Object implements Transform {

@StageTransform("runModel")
public class SavePropertiesTransform extends Object implements Transform {

@StageTransform("runModel")
public class SetupEntityTransform extends Object implements Transform {


 * 
 * 		
 */
		
		
	}

}