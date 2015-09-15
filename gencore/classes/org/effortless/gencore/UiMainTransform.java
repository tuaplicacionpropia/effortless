package org.effortless.gencore;

import org.effortless.jast.transforms.StageTransform;
import org.effortless.core.ObjectUtils;
import org.effortless.jast.transforms.AbstractTransform;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.orm.Entity;
import org.effortless.zkstrap.AdminApp;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;

/**
 * 

public class MainUi extends AdminApp {

	public MainUi () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
	}
	
	protected void buildApp() {
		super.buildApp();
		setAdminPage("login");
		setLabel("Mi aplicación");
//		setAttribute("INC", Integer.valueOf(0));
		
		addMenu("menuInicio");
		addMenu("menuFinder");
	}

	public void menuInicio (Event evt) {
		Integer inc = (Integer)getAttribute("INC");
		inc = Integer.valueOf(inc.intValue() + 1);
		setAttribute("INC", inc);
		Label label = new Label();
		label.setValue("" + inc.intValue() + ". HOLA DON PEPITO");
		appendChild(label);
	}
	
}


 *
 */
@StageTransform(value="runViewApp", ignore=false)
public class UiMainTransform extends AbstractTransform {

	public UiMainTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GApp app = (GApp)node;
		
		String newName = "MainUI";

		
		
		GClass cg = app.newClass(newName);
		app.addUnit(cg.getUnit());
		cg.setSuperClass(AdminApp.class);
		app.setAttribute("_MAIN_UI_", cg);
		
//		String oldPackageName = cg.getUnit().getPackageName();
//		String newPackageName = oldPackageName + ".ui.zk";
//		cg.getUnit().setPackageName(newPackageName);
//		cg.getUnit().addImport(oldPackageName + ".*");

////		clazz.getApplication().addUnit(cg.getUnit());
//		cg.setSuperClass(Object.class);
//		cg.addInterface(UiManager.class);
		
		GMethod mg = cg.addConstructor();
		mg.add(mg.callSuper());
//		System.out.println("CREANDO " + newName);
		
		mg = cg.addMethod("initiate").setProtected(true);
		mg.add(mg.call(mg.cteSuper(), "initiate"));
		
		mg = cg.addMethod("buildApp").setProtected(true);
		mg.add(mg.call(mg.cteSuper(), "buildApp"));
		mg.add(mg.call("setAdminPage", mg.cte("login")));
		mg.add(mg.call("setLabel", mg.cte("Mi aplicación")));
		mg.add(mg.call("setAttribute", mg.cte("INC"), mg.callStatic(Integer.class, "valueOf", mg.cte((int)0))));
		
		mg.add(mg.call("addMenu", mg.cte("menuInicio")));

		java.util.List classes = app.getClasses();
		int length = (classes != null ? classes.size() : 0);
		for (int i = 0; i < length; i++) {
			GClass clazz = (GClass)classes.get(i);
			if (clazz.isType(Entity.class)) {
//			if (!ObjectUtils.equals(clazz.getName(), cg.getName())) {
				String menuName = "menu" + clazz.getNameWithoutPackage();
				mg.add(mg.call("addMenu", mg.cte(menuName)));
			}
		}
		
		mg = cg.addMethod("menuInicio").setPublic(true).addParameter(Event.class, "evt");
		mg.declVariable(Integer.class, "inc", mg.cast(Integer.class, mg.call("getAttribute", mg.cte("INC"))));
		mg.add(mg.assign(mg.var("inc"), mg.callStatic(Integer.class, "valueOf", mg.plus(mg.call(mg.var("inc"), "intValue"), mg.cte((int)1)))));
		mg.add(mg.call("setAttribute", mg.cte("INC"), mg.var("inc")));
		mg.declVariable(Label.class, "label", mg.newObject(Label.class));
		mg.add(mg.call(mg.var("label"), "setValue", mg.plus(mg.cte(""), mg.plus(mg.call(mg.var("inc"), "intValue"), mg.cte(". HOLA DON PEPITO")))));
		mg.add(mg.call("appendChild", mg.var("label")));
	}

}
