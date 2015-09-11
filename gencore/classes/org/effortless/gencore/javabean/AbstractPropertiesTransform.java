package org.effortless.gencore.javabean;

import org.effortless.orm.Entity;
import org.effortless.orm.FileEntity;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

public abstract class AbstractPropertiesTransform extends Object implements Transform {

	public AbstractPropertiesTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		processField((GField)node);
	}

	public void processField (GField field) {
		if (field.isString()) {
			textProcessField(field);
		}
		else if (field.isDate()) {
			dateProcessField(field);
		}
		else if (field.isBoolean()) {
			boolProcessField(field);
		}
		else if (field.isInteger()) {
			countProcessField(field);
		}
		else if (field.isDouble()) {
			numberProcessField(field);
		}
		else if (field.isEnum()) {
			enumProcessField(field);
		}
		else if (field.isFile() || field.isType(FileEntity.class)) {
			fileProcessField(field);
		}
		else if (field.isCollection()) {
if (true) {
			listProcessField(field);
}
		}
		else if (field.isList()) {
if (true) {
			listProcessField(field);
}
		}
		else if (field.isType(Entity.class)) {// (field.isEntity()) {
			refProcessField(field);
		}
		else {
			refProcessField(field);
		}
	}

	protected abstract void textProcessField (GField field);
	
	protected abstract void dateProcessField (GField field);
	
	protected abstract void boolProcessField (GField field);
	
	protected abstract void countProcessField (GField field);
	
	protected abstract void numberProcessField (GField field);
	
	protected abstract void enumProcessField (GField field);

	protected abstract void fileProcessField (GField field);
	
	protected abstract void listProcessField (GField field);
	
	protected abstract void refProcessField (GField field);
	
}
