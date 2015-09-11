package org.effortless.gencore;

import org.effortless.gencore.base.ExtractClassFromRootUnit;
import org.effortless.gencore.entity.SetupEntityTransform;
import org.effortless.jast.transforms.StageTransform;
import org.effortless.jast.transforms.AbstractTransform;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;


/*
		this.stages.add("preStartApp");
		this.stages.add("preStart");
		this.stages.add("runStartApp");
		this.stages.add("runStart");
		this.stages.add("postStart");
		this.stages.add("postStartApp");
		
		this.stages.add("preModelApp");
		this.stages.add("preModel");
		this.stages.add("runModelApp");
		this.stages.add("runModel");
		this.stages.add("postModel");
		this.stages.add("postModelApp");
		
		this.stages.add("preViewApp");
		this.stages.add("preView");
		this.stages.add("runViewApp");
		this.stages.add("runView");
		this.stages.add("postView");
		this.stages.add("postViewApp");
 */

@StageTransform(value="preStartApp")
public class InitTransform extends AbstractTransform {

	@Override
	public void process(GNode node) {
		GApp app = (GApp)node;

		{
			ExtractClassFromRootUnit step = new ExtractClassFromRootUnit();
			step.process(app);
		}
		
		{
			SetupEntityTransform step = new SetupEntityTransform();
			runTransform(app.getClasses(), step);
		}
		
	}

}
