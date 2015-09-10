package org.effortless.jast.transforms;

import java.util.List;

import org.effortless.core.Collections;
import org.effortless.jast.GNode;

public abstract class AbstractTransform extends Object implements Transform {

	protected AbstractTransform () {
		super();
		initiate();
	}
	
	protected void initiate () {
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
	
	protected void runTransform (List nodes, Transform transformer) {
		if (transformer != null) {
			nodes = (nodes != null ? Collections.copyList(nodes) : null);
			int nodesSize = (nodes != null ? nodes.size() : 0);
			for (int i = 0; i < nodesSize; i++) {
				GNode node = (GNode)nodes.get(i);
				if (node != null) {
					transformer.process(node);
				}
			}
		}
	}
	
	

}
