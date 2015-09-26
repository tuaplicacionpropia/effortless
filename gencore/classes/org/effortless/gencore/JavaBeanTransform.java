package org.effortless.gencore;

import org.effortless.jast.transforms.StageTransform;
import org.effortless.gencore.entity.SetupOwnerInnerEntity;
import org.effortless.gencore.javabean.*;
import org.effortless.jast.GClass;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

@StageTransform(value="runModel", after="InitTransform")
public class JavaBeanTransform implements Transform {

	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;

		{
			SetupOwnerInnerEntity step = new SetupOwnerInnerEntity();
			step.process(clazz);
		}
		
		{
			ActionsTransform step = new ActionsTransform();
			step.process(clazz);
		}
		
		{
			FastSqlPropertiesTransform step = new FastSqlPropertiesTransform();
			step.process(clazz);
		}

		{
			SetupParentTransform step = new SetupParentTransform();
			step.process(clazz);
		}
		
		{
			InitiateMethodTransform step = new InitiateMethodTransform();
			step.process(clazz);
		}
		
		{
			ToStringMethodTransform step = new ToStringMethodTransform();
			step.process(clazz);
		}
		
		{
			CompareMethodTransform step = new CompareMethodTransform();
			step.process(clazz);
		}
		
		{
			EqualsMethodTransform step = new EqualsMethodTransform();
			step.process(clazz);
		}
		
		{
			HashCodeMethodTransform step = new HashCodeMethodTransform();
			step.process(clazz);
		}
		
		{
			ReferenciableTransform step = new ReferenciableTransform();
			step.process(clazz);
		}
		
/*
 *

public class ActionsTransform extends Object implements Transform {
public class AlterActionsTransform extends Object implements Transform {
public class CloneMethodTransform extends Object implements Transform {
@StageTransform("runModel")
public class CompareMethodTransform extends Object implements Transform {
@StageTransform("runModel")
public class EqualsMethodTransform extends Object implements Transform {
//@StageTransform("postStart")
public class FastSqlEntityTransform extends Object implements Transform {
@StageTransform("runModel")
public class FastSqlPropertiesTransform extends AbstractPropertiesTransform {
@StageTransform("runModel")
public class HashCodeMethodTransform extends Object implements Transform {
@StageTransform("runModel")
public class InitiateMethodTransform extends Object implements Transform {

//public class InstanceMethodTransform extends Object implements Transform {

public class KryoTransform extends Object implements Transform {

public class PropertiesTransform extends AbstractPropertiesTransform {

@StageTransform("runModel")
public class ReferenciableTransform extends Object implements Transform {

@StageTransform("runModel")
public class ToStringMethodTransform extends Object implements Transform {

 * 
 */
		
	}

}
