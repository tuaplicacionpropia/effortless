package org.effortless.gencore.mapping;

import java.util.List;

import org.effortless.orm.FileEntity;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;

public class PersistTransform extends AbstractBaseTransform {

	public PersistTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_Persist(clazz);
	}

	/*

	protected boolean doSaveProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		// TODO Auto-generated method stub
		return result;
	}


	public void persist () {
		super._doPersist(__DEFINITION__);
		super._doPersist(this.ciudades);
	}
	
 */
	protected void add_Persist(GClass clazz) {
		{
			GMethod mg = clazz.addMethod("doSavePreviousProperties").setProtected(true).setReturnType("boolean").addParameter("String", "properties").addParameter("boolean", "validate").addParameter("boolean", "create");
			mg.declVariable("boolean", "result", mg.cteTrue());
			
			mg.debug("doSavePreviousProperties");
			
			List fields = clazz.getProperties();
			int fieldsSize = (fields != null ? fields.size() : 0);
			for (int i = 0; i < fieldsSize; i++) {
				GField field = (GField)fields.get(i);
				if (field != null && (field.isType(FileEntity.class))) {
					//super._doPersist(this.ciudades);
					mg.add(mg.call(clazz.cteSuper(), "_doPersist", mg.field(field)));
				}
			}
			
			mg.addReturn(mg.var("result"));
		}
		
		{
			GMethod mg = clazz.addMethod("doSaveProperties").setProtected(true).setReturnType("boolean").addParameter("String", "properties").addParameter("boolean", "validate").addParameter("boolean", "create");
			mg.declVariable("boolean", "result", mg.cteTrue());
			
			mg.debug("doSaveProperties");
			
			List fields = clazz.getProperties();
			int fieldsSize = (fields != null ? fields.size() : 0);
			for (int i = 0; i < fieldsSize; i++) {
				GField field = (GField)fields.get(i);
				if (field != null && (field.isCollection() || field.isList())) {
					//super._doPersist(this.ciudades);
					mg.add(mg.call(clazz.cteSuper(), "_doPersist", mg.field(field)));
				}
			}
			
			mg.addReturn(mg.var("result"));
		}
	}

}
