package org.effortless.gencore.ui;

import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.AbstractTransform;
import org.effortless.zkstrap.Editor;
import org.effortless.zkstrap.Person;
import org.zkoss.zk.ui.event.Event;

/**
 * 

	public void menuEditor (Event evt) {
		java.util.Map data = (java.util.Map)evt.getData();
		Object value = data.get("value");

//		MyBean obj = (value != null ? (MyBean)value : MyBean.buildMyBean("Mi nombre"));
		Person obj = (value != null ? (Person)value : new Person());
		
		Editor b = new Editor(this, obj, "myEditor");
		b.setAttribute("CALLER", data.get("CALLER"));
		b.addText("name");
		b.addText("surnames");
		b.addTextArea("comment");
		b.addInteger("age");
		b.addBoolean("enabled");
		b.addBtn("ejecutar");
		b.addBtn("descargar");
	}


 *
 */
public class EditorTransform extends AbstractTransform {

	public EditorTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		GClass cg = (GClass)clazz.getApplication().getAttribute("_MAIN_UI_");
		if (cg != null) {
//			public void menuEditor (Event evt) {
			GMethod mg = cg.addMethod("menuEditor" + clazz.getNameWithoutPackage()).setPublic(true).addParameter(Event.class, "evt");
			
//				java.util.Map data = (java.util.Map)evt.getData();
			mg.declVariable(java.util.Map.class, "data", mg.cast(java.util.Map.class, mg.call(mg.var("evt"), "getData")));
//				Object value = data.get("value");
			mg.declVariable(Object.class, "value", mg.call(mg.var("data"), "get", mg.cte("value")));

//				Person obj = (value != null ? (Person)value : new Person());
			mg.declVariable(clazz, "obj", mg.triple(mg.ne(mg.var("value"), mg.cteNull()), mg.cast(clazz, mg.var("value")), mg.newObject(clazz)));

//				Editor b = new Editor(this, obj, "myEditor");
			mg.declVariable(Editor.class, "b", mg.newObject(Editor.class, mg.cteThis(), mg.var("obj"), mg.cte("myEditor")));
//				b.setAttribute("CALLER", data.get("CALLER"));
			mg.add(mg.call(mg.var("b"), "setAttribute", mg.cte("CALLER"), mg.call(mg.var("data"), "get", mg.cte("CALLER"))));
			
			java.util.List fields = clazz.getFields();
			int lengthFields = (fields != null ? fields.size() : 0);
			for (int i = 0; i < lengthFields; i++) {
				GField field = (GField)fields.get(i);
				if (!field.isStatic() && !field.isFinal()) {
					if (field.isBoolean()) {
						mg.add(mg.call(mg.var("b"), "addBoolean", mg.cte(field.getName())));
					}
					else if (field.isString()) {
						mg.add(mg.call(mg.var("b"), "addText", mg.cte(field.getName())));
					}
					else if (field.isDate()) {
						mg.add(mg.call(mg.var("b"), "addDate", mg.cte(field.getName())));
					}
				}
			}
			
//				b.addText("name");
//				b.addText("surnames");
//				b.addTextArea("comment");
//				b.addInteger("age");
//				b.addBoolean("enabled");
//				b.addBtn("ejecutar");
//				b.addBtn("descargar");
//			}

			
			
		}

		// TODO Auto-generated method stub
		
	}

}
