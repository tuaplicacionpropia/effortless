package org.effortless.gencore.mapping;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import org.effortless.gencore.InfoModel;
import org.effortless.jast.transforms.StageTransform;
import org.effortless.core.Collections;
import org.effortless.orm.AbstractIdEntity;
import org.effortless.orm.DbManager;
import org.effortless.orm.EagerPropertiesLoader;
import org.effortless.orm.Entity;
import org.effortless.orm.FileEntity;
import org.effortless.orm.Filter;
import org.effortless.orm.LazyPropertiesLoader;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.impl.ColumnExtraType;
import org.effortless.jast.Expression;
import org.effortless.jast.GAnnotation;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

public abstract class AbstractBaseTransform extends Object implements Transform {

	public AbstractBaseTransform () {
		super();
	}
	
	protected boolean _isSimpleColumn(GField field) {
		boolean result = false;
		if (field != null) {
			if (field.isString()) {
				result = true;
			}
			else if (field.isDate()) {
				result = true;
			}
			else if (field.isBoolean()) {
				result = true;
			}
			else if (field.isInteger()) {
				result = true;
			}
			else if (field.isDouble()) {
				result = true;
			}
			else if (field.isEnum()) {
				result = true;
			}
			else if (field.isFile() || field.isType(FileEntity.class)) {
				result = true;
			}
			else if (field.isCollection()) {
				result = false;
			}
			else if (field.isList()) {
				result = false;
			}
			else if (field.isType(Entity.class)) {//(field.isEntity()) {
				result = true;
			}
			else {
				result = true;
			}
		}
		return result;
	}

	protected boolean isEager (GField field) {
		boolean result = false;
		if (field.isString()) {
			if (!InfoModel.checkComment(field)) {
				result = true;
			}
		}
		else if (field.isDate()) {
			result = true;
		}
		else if (field.isBoolean()) {
			result = true;
		}
		else if (field.isInteger()) {
			result = true;
		}
		else if (field.isDouble()) {
			result = true;
		}
		else if (field.isEnum()) {
			result = true;
		}
		else if (field.isFile()) {
			result = false;
		}
		else if (field.isType(FileEntity.class)) {
			result = true;
		}
		else if (field.isCollection()) {
			result = false;
		}
		else if (field.isList()) {
			result = false;
		}
		else if (field.isType(Entity.class)) {//(field.isEntity()) {
			result = true;
		}
		else {
			result = true;
		}
		
		return result;
	}
	
	protected String _getColumnName (GField field) {
		String result = null;
		if (field.isString()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isDate()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isBoolean()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isInteger()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isDouble()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isEnum()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isFile()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isType(FileEntity.class)) {
			String fieldName = field.getName().trim().toUpperCase();
			result = fieldName + "_ID";
		}
		else if (field.isCollection()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isList()) {
			result = field.getName().trim().toUpperCase();
		}
		else if (field.isType(Entity.class)) {//(field.isEntity()) {
			String fieldName = field.getName().trim().toUpperCase();
			result = fieldName + "_ID";
		}
		else {
			result = field.getName().trim().toUpperCase();
		}
		
		return result;
	}
	
}
