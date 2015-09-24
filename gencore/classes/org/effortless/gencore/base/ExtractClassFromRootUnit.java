package org.effortless.gencore.base;

import java.util.List;

import org.effortless.core.Collections;
import org.effortless.gencore.Transforms;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GNode;
import org.effortless.jast.GUnit;
import org.effortless.jast.transforms.Transform;

//@StageTransform("preStartApp")
public class ExtractClassFromRootUnit extends Object implements Transform {

	public ExtractClassFromRootUnit () {
		super();
	}
	
	public void process(GNode node) {
		GApp app = (GApp)node;
		if (app != null) {
			GUnit rootUnit = app.getRootUnit();
			GClass mainClass = rootUnit.getMainClass();
			String packageName = rootUnit.getPackageName();
			packageName = (packageName != null ? packageName : rootUnit.getApp().getPackageName());
			if (rootUnit.getPackageName() == null && packageName != null) {
				rootUnit.setPackageName(packageName);
			}
			List classes = Collections.copyList(mainClass.getInnerClasses());
			int lengthClasses = (classes != null ? classes.size() : 0);
			for (int i = 0; i < lengthClasses; i++) {
				GClass clazz = (GClass)classes.get(i);
				if (clazz != null) {
					extractClass(app, clazz, false);
//					clazz.setPublic(true);
//					String className = clazz.getNameWithoutPackage();
//					GUnit newUnit = app.addUnit(packageName, className);
//					newUnit.removeClass(newUnit.getMainClass());
//					newUnit.addClass(clazz);
//					mainClass.removeClass(clazz);
//					
//					java.util.List innerClasses = clazz.getInnerClasses();
//					int lengthInnerClasses = (innerClasses != null ? innerClasses.size() : 0);
//					for (int j = 0; j < lengthInnerClasses; j++) {
//						
//					}
				}
			}
		}
	}
	
	protected void extractClass (GApp app, GClass clazz, boolean inner) {
		if (app != null && clazz != null) {
			clazz.setAttribute(Transforms.ATTR_INNER_ENTITY, Boolean.valueOf(inner));
			
			GUnit rootUnit = app.getRootUnit();
			GClass mainClass = rootUnit.getMainClass();
			String packageName = rootUnit.getPackageName();
			packageName = (packageName != null ? packageName : rootUnit.getApp().getPackageName());

			clazz.setPublic(true);
			String className = clazz.getNameWithoutPackage();
			GUnit newUnit = app.addUnit(packageName, className);
			newUnit.addClass(clazz);
			clazz.setUnit(newUnit);
			newUnit.removeClass(newUnit.getMainClass());
//			if (!inner) {
//				mainClass.removeClass(clazz);
//			}
//			else {
				clazz.getContainerClass().removeClass(clazz);
//			}
			
			List innerClasses = Collections.copyList(clazz.getInnerClasses());
			int lengthInnerClasses = (innerClasses != null ? innerClasses.size() : 0);
			for (int j = 0; j < lengthInnerClasses; j++) {
				GClass innerClass = (GClass)innerClasses.get(j);
				if (innerClass != null) {
					innerClass.isInner();
					innerClass.isInner(clazz);
					extractClass(app, innerClass, true);
					app.setClassAttribute(innerClass.getName(), Transforms.OWNER_INFO, clazz);
					innerClass.setAttribute(Transforms.OWNER_INFO, clazz);
				}
			}
		}
	}

}
