package org.effortless.jast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.effortless.core.Collections;

public class GeneralAppTransformer extends Object implements AppTransformer {

	public GeneralAppTransformer () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateStages();
		initiateTransforms();
	}
	
	protected List stages;
	
	protected void initiateStages () {
		this.stages = null;
	}
	
	public List getStages () {
		return this.stages;
	}
	
	public void setStages (List newValue) {
		this.stages = newValue;
	}
	
	protected Map transforms;
	
	protected void initiateTransforms () {
		this.transforms = null;
	}
	
	public void addTransform (String stage, Transform transform) {
		this.transforms = (this.transforms != null ? this.transforms : new HashMap());
		if (stage != null && transform != null) {
			List list = (List)this.transforms.get(stage);
			list = (list != null ? list : new ArrayList());
			list.add(transform);
			this.transforms.put(stage, list);
		}
	}
	
	@Override
	public void transform(GApp app) {
		if (app != null) {
			int length = (this.stages != null ? this.stages.size() : 0);
			for (int i = 0; i < length; i++) {
				String stage = (String)this.stages.get(i);
				if (stage != null) {
					List transforms = (this.transforms != null ? (List)this.transforms.get(stage) : null);
					boolean stageApp = stage.endsWith("App");
					if (stageApp) {
						runTransforms(app, transforms);
					}
					else {
						List classes = app.getClasses();
						runTransforms(classes, transforms);
					}
				}
			}
		}
	}

	protected void runTransforms (List nodes, List transformers) {
		nodes = (nodes != null ? Collections.copyList(nodes) : null);
		transformers = (transformers != null ? Collections.copyList(transformers) : null);
		int nodesSize = (nodes != null ? nodes.size() : 0);
		int transformersSize = (transformers != null ? transformers.size() : 0);
		for (int i = 0; i < nodesSize; i++) {
			GNode node = (GNode)nodes.get(i);
			if (node != null) {
				for (int j = 0; j < transformersSize; j++) {
					Transform transform = (Transform)transformers.get(j);
					if (transform != null) {
						transform.process(node);
					}
				}
			}
		}
	}
	
	protected void runTransforms (GNode node, List transformers) {
		transformers = (transformers != null ? Collections.copyList(transformers) : null);
		int transformersSize = (transformers != null ? transformers.size() : 0);
		if (node != null) {
			for (int j = 0; j < transformersSize; j++) {
				Transform transform = (Transform)transformers.get(j);
				if (transform != null) {
					transform.process(node);
				}
			}
		}
	}
	
}
