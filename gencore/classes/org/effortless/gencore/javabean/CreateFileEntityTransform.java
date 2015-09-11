package org.effortless.gencore.javabean;

import org.effortless.orm.FileEntity;
import org.effortless.gencore.InfoModel;
import org.effortless.gencore.mapping.FastFileEntityTransform;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

public class CreateFileEntityTransform extends Object implements Transform  {

	public CreateFileEntityTransform () {
		super();
	}
	
	protected GClass result;
	
	public GClass getResult() {
		return this.result;
	}
	
	public void process (GNode node) {
		GField field = (GField)node;
		GClass result = null;
		
		result = field.getApplication().getFileClass();
		if (result == null && InfoModel.checkCreateFileEntity()) {
			String newName = field.getApp().getName() + "Files";
			result = field.getApp().newClass(newName);
			result.setSuperClass(FileEntity.class);
			
//			result.addAnnotation(Module.class, "others");
			result.setData("module", "others");

//			FileEntityTransform transform = new FileEntityTransform();
			FastFileEntityTransform transform = new FastFileEntityTransform();
			transform.process(result);

			
			field.getApplication().setFileClass(result);
		}
		this.result = result;
	}

}
