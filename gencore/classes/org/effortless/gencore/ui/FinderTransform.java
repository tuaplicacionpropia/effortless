package org.effortless.gencore.ui;

import org.effortless.gencore.InfoModel;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.AbstractTransform;
import org.effortless.zkstrap.Finder;
import org.effortless.zkstrap.PersonFinderFilter;
import org.zkoss.zk.ui.event.Event;

/**
 * 

	public void menuFinder (Event evt) {
		if (!reopen("myFinder")) {
//			FilterList list = new FilterList();
//			list.add(MyBean.buildMyBean("Mi nombre"));
//			list.add(MyBean.buildMyBean("España"));
//			list.add(MyBean.buildMyBean("Canarias"));
//			Person person = new Person();
//			person.setName("Jesús María");
//			person.persist();
			
			java.util.List list = new PersonFinderFilter();//Person.listAll();

			Finder b = new Finder(this, list, "myFinder");
			b.addText("name");
			b.addBoolean("enabled");
			b.addBtn("@ejecutar");
			b.addBtn("@descargar");
			b.setProperties("name,surnames");
		}
	}




 *
 */
public class FinderTransform extends AbstractTransform {

	public FinderTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		GClass cg = (GClass)clazz.getApplication().getAttribute("_MAIN_UI_");
		if (cg != null) {

//			public void menuFinder (Event evt) {
			String screenName = "menuFinder" + clazz.getNameWithoutPackage();
			String screenEditorName = "menuEditor" + clazz.getNameWithoutPackage();
			GMethod mg = cg.addMethod(screenName).setPublic(true).addParameter(Event.class, "evt");
//				if (!reopen("myFinder")) {
			GCode mIf = mg.addIf(mg.not(mg.call("reopen", mg.cte(screenName))));
				
//					java.util.List list = new PersonFinderFilter();//Person.listAll();
			mIf.declVariable(java.util.List.class, "list", mIf.newObject(clazz.getNameWithoutPackage() + "FinderFilter"));

//					Finder b = new Finder(this, list, "myFinder");
			mIf.declVariable(Finder.class, "b", mIf.newObject(Finder.class, mIf.cteThis(), mIf.var("list"), mIf.cte(screenEditorName)));
			{
				java.util.List fields = InfoModel.listNotNullUnique(clazz);
				int length = (fields != null ? fields.size() : 0);
				for (int i = 0; i < length; i++) {
					GField field = (GField)fields.get(i);
					if (!field.isStatic() && !field.isFinal()) {
						if (field.isBoolean()) {
//							b.addBoolean("enabled");
							mIf.add(mIf.call(mIf.var("b"), "addBoolean", mIf.cte(field.getName())));
						}
						else if (field.isString()) {
//							b.addText("name");
							mIf.add(mIf.call(mIf.var("b"), "addText", mIf.cte(field.getName())));
						}
					}
				}
			}
			
			{
//					b.addBtn("@ejecutar");
//					b.addBtn("@descargar");
				java.util.List methods = InfoModel.getCustomActions(clazz);
				int length = (methods != null ? methods.size() : 0);
				for (int i = 0; i < length; i++) {
					GMethod method = (GMethod)methods.get(i);
					if (method != null) {
						String mName = method.getName();
						mIf.add(mIf.call(mIf.var("b"), "addBtn", mIf.cte("@" + mName)));
					}
				}
			}
			
			{
				java.util.List fields = InfoModel.listNotNullUnique(clazz);
				int length = (fields != null ? fields.size() : 0);
				String properties = "";
				for (int i = 0; i < length; i++) {
					GField field = (GField)fields.get(i);
					if (!field.isStatic() && !field.isFinal()) {
						String fName = field.getName();
						properties += (properties.length() > 0 && fName.length() > 0 ? "," : "") + fName;
					}
				}
				if (properties.length() > 0) {
//					b.setProperties("name,surnames");
					mIf.add(mIf.call(mIf.var("b"), "setProperties", mIf.cte(properties)));
				}
			}
//				}
//			}
			
			
			
		}
	}

}
