package org.effortless.jast.jdk8u20;

import org.effortless.jast.*;

public class AppTransformerJdk8u20 extends Object implements org.effortless.jast.transforms.AppTransformer {

	public void transform (org.effortless.jast.GApp app) {
		if (app != null) {
			GUnitJdk8u20 rootUnit = (GUnitJdk8u20)app.getRootUnit();
			app.addUnit(rootUnit);
			
			java.util.List<?> items = rootUnit.node.defs;
			for (Object item : items) {
				if (item != null) {
					item.toString();
				}
			}
			
			rootUnit.setPackageName("hola.mundo.atlantida.grecia");
			rootUnit.addImport("java.util.Map");
			rootUnit.addImport("java.util.HashMap");
			rootUnit.setName("PericoDeLosPalotes");
			GClass gClass = rootUnit.getMainClass();
			java.util.List fields = gClass.getFields();
			int fieldsSize = (fields != null ? fields.size() : 0);
			for (int i = 0; i < fieldsSize; i++) {
				GField field = (GField)fields.get(i);
				String name = field.getName();
				String type = field.getType();
				System.out.println(name +":"+ type);
				field.setName(name + "2");
				field.setType("Double");
			}
			GField field = gClass.addField("contador", "Integer");
			gClass.setFinal(true);
			gClass.setFinal(false);
			gClass.setProtected(true);
			
			GMethod gm = gClass.addMethod("JuanSinMieo").setPrivate().setFinal().setStatic().addParameter("text", "java.lang.String").setReturnType("Double");
			gm.addDeclVariable("String", "result");
			gm.addDeclVariable("Integer", "count");
			gm.addReturn("result");
			
			field.setPublic(true);
			field.setStatic(true);
			field.setFinal(true);
			
			
			GUnitJdk8u20 unit = (GUnitJdk8u20)app.addUnit("com.company.appname", "Version");
			gClass = unit.getMainClass();
			gClass.addField("activado", "Boolean");
		}
	}
	
}
