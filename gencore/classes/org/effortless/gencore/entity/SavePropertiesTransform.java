package org.effortless.gencore.entity;

import java.util.List;

import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

import org.effortless.orm.FileEntity;

/**
 *
 * Implements


	protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		if (this.fileProperty != null) {
			this.fileProperty.persist()
		}

		return result;
	}

 * 
 * 
 * @author jesus
 *
 */
//@StageTransform("runModel")
public class SavePropertiesTransform extends Object implements Transform {

	public SavePropertiesTransform () {
		super();
	}
	
	public void process (GNode node) {
		GClass cg = (GClass)node;
		if (cg != null) {
			
			List fields = cg.listFields(FileEntity.class);//InfoModel.listFileFields(cg);
			
			if (fields != null && fields.size() > 0) {
				GMethod mg = null;
				
				//protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
				mg = cg.addMethod("doSavePreviousProperties").setProtected(true).setReturnType("boolean").addParameter(String.class, "properties").addParameter("boolean", "validate").addParameter("boolean", "create");
				mg.declVariable("boolean", "result", mg.cteTrue());//boolean result = true;
				
				for (int i = 0; i < fields.size(); i++) {
					GField field = (GField)fields.get(i);
					GCode ifCode = mg.newBlock();
					mg.addIf(mg.notNull(mg.field(field)), ifCode);//if (this.fileProperty != null) {
					ifCode.add(mg.call(mg.field(field), "persist"));//this.fileProperty.persist()
				}
				mg.addReturn(mg.var("result"));//return result
			}
		}
	}
	
	
	
}
