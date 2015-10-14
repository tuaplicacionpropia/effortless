package org.effortless.gencore.ui;

import org.apache.commons.lang.StringUtils;
import org.effortless.gencore.InfoModel;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.AbstractTransform;
import org.effortless.orm.Entity;
import org.effortless.zkstrap.Editor;
import org.effortless.zkstrap.Person;
import org.effortless.zkstrap.Screen;
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
						String fName = field.getName();
						if (org.effortless.core.StringUtils.contains(fName, "comment", "comentario", "observation", "anotacion", "annotation")) {
							mg.add(mg.call(mg.var("b"), "addTextArea", mg.cte(field.getName())));
						}
						else if (org.effortless.core.StringUtils.contains(fName, "phone", "tlf", "mobile", "fax", "movil")) {
							mg.add(mg.call(mg.var("b"), "addPhone", mg.cte(field.getName())));
						}
						else if (org.effortless.core.StringUtils.contains(fName, "ip")) {
							mg.add(mg.call(mg.var("b"), "addIp", mg.cte(field.getName())));
						}
						else if (org.effortless.core.StringUtils.contains(fName, "euro", "dollar")) {
							mg.add(mg.call(mg.var("b"), "addCurrency", mg.cte(field.getName())));
						}
						else if (org.effortless.core.StringUtils.contains(fName, "email", "mail", "correo")) {
							mg.add(mg.call(mg.var("b"), "addEmail", mg.cte(field.getName())));
						}
						else if (org.effortless.core.StringUtils.contains(fName, "color", "colour", "background", "foreground")) {
							mg.add(mg.call(mg.var("b"), "addColor", mg.cte(field.getName())));
						}
						else if (org.effortless.core.StringUtils.contains(fName, "password", "contraseña", "secret", "key")) {
							mg.add(mg.call(mg.var("b"), "addPassword", mg.cte(field.getName())));
						}
						else {
							mg.add(mg.call(mg.var("b"), "addText", mg.cte(field.getName())));
						}
					}
					else if (field.isDate()) {
						mg.add(mg.call(mg.var("b"), "addDate", mg.cte(field.getName())));
					}
					else if (field.isCollection() || field.isList()) {
						mg.add(mg.call(mg.var("b"), "addTable", mg.cte(field.getName())));
					}
					else if (field.isInteger()) {
						mg.add(mg.call(mg.var("b"), "addInteger", mg.cte(field.getName())));
					}
					else if (field.isDouble()) {
						mg.add(mg.call(mg.var("b"), "addNumber", mg.cte(field.getName())));
					}
					else if (field.isTime()) {
						mg.add(mg.call(mg.var("b"), "addTime", mg.cte(field.getName())));
					}
					else if (field.isTimestamp()) {
						mg.add(mg.call(mg.var("b"), "addDateTime", mg.cte(field.getName())));
					}
					else if (field.isEnum() || field.isRealEnum()) {
						mg.add(mg.call(mg.var("b"), "addSelect", mg.cte(field.getName())));
//						mg.add(mg.call(mg.var("b"), "addRadio", mg.cte(field.getName())));
					}
					else if (field.isFile()) {
						mg.add(mg.call(mg.var("b"), "addFile", mg.cte(field.getName())));
					}
					else if (field.isType(Entity.class)) {
						mg.add(mg.call(mg.var("b"), "addSelect", mg.cte(field.getName())));
					}
				}
			}
			
			{
//				b.addBtn("@ejecutar");
//				b.addBtn("@descargar");
				java.util.List methods = InfoModel.getCustomActions(clazz);
				int length = (methods != null ? methods.size() : 0);
				for (int i = 0; i < length; i++) {
					GMethod method = (GMethod)methods.get(i);
					if (method != null) {
						String mName = method.getName();
						mg.add(mg.call(mg.var("b"), "addBtn", mg.cte("" + mName)));
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
