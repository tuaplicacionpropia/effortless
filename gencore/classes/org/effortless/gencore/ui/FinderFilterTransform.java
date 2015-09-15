package org.effortless.gencore.ui;

import org.effortless.core.StringUtils;
import org.effortless.gencore.InfoModel;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.AbstractTransform;
import org.effortless.orm.impl.EntityFilter;

/*
 * 

package org.effortless.zkstrap;

import org.effortless.orm.impl.EntityFilter;

public class PersonFinderFilter extends EntityFilter {

	public PersonFinderFilter () {
		super(Person.__DEFINITION__, Person.__DEFINITION__.getDefaultLoader());
	}
	
	protected void initiate () {
		super.initiate();
		initiateName();
		initiateEnabled();
	}
	
	protected String name;
	
	protected void initiateName () {
		this.name = null;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String newValue) {
		_setProperty("name", this.name, this.name = newValue);
	}
	
	protected Boolean enabled;
	
	protected void initiateEnabled () {
		this.enabled = Boolean.TRUE;
	}
	
	public Boolean getEnabled () {
		return this.enabled;
	}
	
	public void setEnabled (Boolean newValue) {
		_setProperty("enabled", this.enabled, this.enabled = newValue);
	}
	
	protected String _buildQuery (boolean count) {
		String result = null;
		
		eq("deleted", Boolean.FALSE);
		
		eq("enabled", this.enabled);
		ilk("name", this.name);
		
		result = super._buildQuery(count);
		return result;
	}

}

 * 
 */
public class FinderFilterTransform extends AbstractTransform {

	public FinderFilterTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;

		//public class PersonFinderFilter extends EntityFilter {
		String newName = clazz.getNameWithoutPackage() + "FinderFilter";
		GClass cg = clazz.newClass(newName);
		cg.setSuperClass(EntityFilter.class);

//			public PersonFinderFilter () {
//				super(Person.__DEFINITION__, Person.__DEFINITION__.getDefaultLoader());
//			}
		GMethod mg = cg.addConstructor();
		mg.add(mg.callSuper(mg.property(mg.clazz(clazz), "__DEFINITION__"), mg.call(mg.property(mg.clazz(clazz), "__DEFINITION__"), "getDefaultLoader")));
		
		
		java.util.List fields = InfoModel.listNotNullUnique(clazz);
		int lengthFields = (fields != null ? fields.size() : 0);
		
//		protected void initiate () {
//			super.initiate();
//			initiateName();
//			initiateEnabled();
//		}
		mg = cg.addMethod("initiate").setProtected(true);
		mg.add(mg.call(mg.cteSuper(), "initiate"));
		for (int i = 0; i < lengthFields; i++) {
			GField field = (GField)fields.get(i);
			String initField = "initiate" + StringUtils.capFirst(field.getName());
			mg.add(mg.call(initField));
		}
		
		
		for (int i = 0; i < lengthFields; i++) {
			GField field = (GField)fields.get(i);
//		protected String name;
			cg.addField(field).setProtected(true);
		
//		protected void initiateName () {
//			this.name = null;
//		}
			mg = cg.addMethod(field.getInitiateName()).setProtected(true);
			mg.add(mg.assign(mg.field(field.getName()), mg.cteNull()));
		
//		public String getName () {
//			return this.name;
//		}
			mg = cg.addMethod(field.getGetterName()).setPublic(true).setReturnType(field.getClazz());
			mg.addReturn(mg.field(field.getName()));
		
//		public void setName (String newValue) {
//			_setProperty("name", this.name, this.name = newValue);
//		}
			mg = cg.addMethod(field.getSetterName()).setPublic(true).addParameter(field.getClazz(), "newValue");
			mg.add(mg.call("_setProperty", mg.field(field.getName()), mg.assign(mg.field(field.getName()), mg.var("newValue"))));
		}
		
		

		
//		protected String _buildQuery (boolean count) {
		mg = cg.addMethod("_buildQuery").setProtected(true).setReturnType(String.class).addParameter("boolean", "count");
//			String result = null;
		mg.declVariable(String.class, "result", mg.cteNull());
			
		for (int i = 0; i < lengthFields; i++) {
			GField field = (GField)fields.get(i);
//			eq("deleted", Boolean.FALSE);
			mg.add(mg.call("eq", mg.cte("deleted"), mg.property(mg.clazz(Boolean.class), "FALSE")));
		
			if (field.isBoolean()) {
//			eq("enabled", this.enabled);
				mg.add(mg.call("eq", mg.cte(field.getName()), mg.field(field.getName())));
			}
			else if (field.isString()) {
//			ilk("name", this.name);
				mg.add(mg.call("ilk", mg.cte(field.getName()), mg.field(field.getName())));
			}
		}
			
//			result = super._buildQuery(count);
		mg.add(mg.assign(mg.var("result"), mg.call(mg.cteSuper(), "_buildQuery", mg.var("count"))));
//			return result;
		mg.addReturn(mg.var("result"));
//		}

		
		

		
	}

}
