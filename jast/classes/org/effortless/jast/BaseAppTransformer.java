package org.effortless.jast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.atteo.classindex.ClassIndex;
import org.effortless.core.ClassUtils;

public class BaseAppTransformer extends GeneralAppTransformer implements AppTransformer {

	protected void initiateStages () {
		super.initiateStages();
		this.stages = new ArrayList();
		
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
	}

	protected void initiateTransforms () {
		super.initiateTransforms();

		this.reorderAfter = new java.util.ArrayList();
		this.reorderBefore = new java.util.ArrayList();
		
		for (Class clazz : ClassIndex.getAnnotated(StageTransform.class)) {
			StageTransform ann = (StageTransform)clazz.getAnnotation(StageTransform.class);
			boolean ignore = ann.ignore();
			if (!ignore) {
				String stage = ann.value();
				Transform transform = null;
				try {
					transform = (Transform)clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (transform != null) {
					String afterTransform = ann.after();
					afterTransform = (afterTransform != null ? afterTransform.trim() : "");
					if (afterTransform.length() > 0 && stage != null && transform != null) {
						this.reorderAfter.add(new Object[] {afterTransform, stage, transform});
					}
					
					String beforeTransform = ann.before();
					beforeTransform = (beforeTransform != null ? beforeTransform.trim() : "");
					if (beforeTransform.length() > 0 && stage != null && transform != null) {
						this.reorderBefore.add(new Object[] {beforeTransform, stage, transform});
					}
					
					
					this.addTransform(stage, transform);
				}
			}
		}
		
		reorderTransforms();
		
		
		if (true) {
			printTransforms();
		}
		
	}
	
	protected java.util.List reorderAfter;
	protected java.util.List reorderBefore;
	
	
	protected void reorderTransforms () {
		{
			int length = (this.reorderAfter != null ? this.reorderAfter.size() : 0);
			for (int i = 0; i < length; i++) {
				Object[] params = (Object[])this.reorderAfter.get(i);
				reorderTransform(params, true);
			}
			this.reorderAfter = null;
		}
		
		{
			int length = (this.reorderBefore != null ? this.reorderBefore.size() : 0);
			for (int i = 0; i < length; i++) {
				Object[] params = (Object[])this.reorderBefore.get(i);
				reorderTransform(params, false);
			}
			this.reorderBefore = null;
		}
	}
	
	protected void reorderTransform(Object[] params, boolean after) {
		if (params != null && params.length >= 3) {
			String afterTransform = (String)params[0];
			String stage = (String)params[1];
			Transform transform = (Transform)params[2];
			
			List list = (List)this.transforms.get(stage);
			
			int srcIndex = -1;
			int pvtIndex = -1;
			int length = (list != null ? list.size() : 0);
			for (int i = 0; i < length; i++) {
				Transform item = (Transform)list.get(i);
				String itemName = (item != null ? item.getClass().getSimpleName() : null);
				itemName = (itemName != null ? itemName.trim() : "");
				
				if (item == transform) {
					srcIndex = i;
				}
				else if (itemName.equals(afterTransform) || itemName.endsWith("." + afterTransform)) {
					pvtIndex = i;
				}
				
				if (srcIndex >= 0 && pvtIndex >= 0) {
					break;
				}
			}
			
			if (srcIndex >= 0 && pvtIndex >= 0) {
				if (after && pvtIndex > srcIndex) {
					list.add(pvtIndex + 1, transform);
					list.remove(srcIndex);
				}
				else if (!after && pvtIndex < srcIndex) {
					list.remove(srcIndex);
					list.add(pvtIndex - 1, transform);
				}
			}
		}
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
	
	
	
	protected void printTransforms () {
		int length = (this.stages != null ? this.stages.size() : 0);
		for (int i = 0; i < length; i++) {
			String stage = (String)this.stages.get(i);
			if (stage != null) {
				printStageTransforms(i, stage);
			}
		}
	}

	protected void printStageTransforms (int idx, String stage) {
		if (stage != null) {
			System.out.println("" + (idx + 1) + ". " + stage + " (BEGIN)");
			
			List transforms = (this.transforms != null ? (List)this.transforms.get(stage) : null);
			int length = (transforms != null ? transforms.size() : 0);
			for (int i = 0; i < length; i++) {
				Transform transform = (Transform)transforms.get(i);
				if (transform != null) {
					String className = ClassUtils.getName(transform.getClass());
					System.out.println(" " + (idx + 1) + "." + (i + 1) + ". " + className);
				}
			}
			
			System.out.println("" + (idx + 1) + ". " + stage + " (END)");
		}
	}
	
}
