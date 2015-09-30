package org.effortless.gencore.mapping;

import java.util.List;

import org.effortless.orm.AbstractIdEntity;
import org.effortless.orm.EagerPropertiesLoader;
import org.effortless.orm.Entity;
import org.effortless.orm.FileEntity;
import org.effortless.orm.LazyPropertiesLoader;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.impl.ColumnExtraType;
import org.effortless.core.StringUtils;
import org.effortless.gencore.InfoModel;
import org.effortless.jast.Expression;
import org.effortless.jast.GAnnotation;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GField;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transforms;

public class DefinitionTransform extends AbstractBaseTransform {

	public DefinitionTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		addDefinition(clazz);
	}


	/*

	protected static final EntityDefinition __DEFINITION__ = new EntityDefinition()
		.setTableName(_TABLE)
		.setSequenceName(_SEQ)
		.addParent(AbstractIdEntity.__DEFINITION__)
		.addProperty("nombre", "NOMBRE", String.class, "255", ColumnExtraType.UNIQUE_NOT_NULL, "EAGER")
		.addProperty("comentario", "CCOMMENT", String.class, "3072", null, "LAZY")
		.addReferenceProperty("provincia", "PROVINCIA_ID", Provincia.class, null, "EAGER")
		.setDefaultOrderBy("nombre ASC, id ASC")
		.setDefaultLoader(new EagerPropertiesLoader(Ciudad._pivot))
		.addLoader(new LazyPropertiesLoader(Ciudad._pivot));

	 */
	protected void addDefinition (GClass cg) {
		Expression expr = null;
		expr = cg.newObject(EntityDefinition.class);
		String varName = "__DEFINITION__";
if (false) {
		expr = cg.call(expr, "setTableName", cg.property(cg.cteClass(cg), "_TABLE"));
		expr = cg.call(expr, "setEntityClass", cg.cte(cg.getName()));
		expr = cg.call(expr, "setSequenceName", cg.property(cg.cteClass(cg), "_SEQ"));
		expr = cg.call(expr, "addParent", cg.property(cg.cteClass(AbstractIdEntity.class), varName));
		
		List fields = cg.getProperties();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			expr = declareDefinitionField(cg, expr, field);
		}

		expr = cg.call(expr, "setDefaultOrderBy", cg.cte("id ASC"));
		expr = cg.call(expr, "setDefaultLoader", cg.newObject(EagerPropertiesLoader.class, cg.property(cg.cteClass(cg), "_pivot")));
		expr = cg.call(expr, "addLoader", cg.newObject(LazyPropertiesLoader.class, cg.property(cg.cteClass(cg), "_pivot")));
}
		cg.addField(EntityDefinition.class, varName, expr).setPublic(true).setStatic(true).setFinal(true);
		
if (true) {
		GCode staticBlock = cg.addStaticBlock();
//		expr = staticBlock.var(varName);
		expr = staticBlock.realProperty(staticBlock.clazz(cg), varName);		

		staticBlock.add(cg.call(expr, "setTableName", cg.property(cg.clazz(cg), "_TABLE")));
//		staticBlock.add(cg.call(expr, "setEntityClass", cg.cte(cg.getName())));
		staticBlock.add(cg.call(expr, "setSequenceName", cg.property(cg.clazz(cg), "_SEQ")));
		staticBlock.add(cg.call(expr, "setEntityClass", cg.cteClass(cg)));
		staticBlock.add(cg.call(expr, "addParent", cg.property(cg.clazz(AbstractIdEntity.class), varName)));
		
		List fields = cg.getProperties();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			staticBlock.add(declareDefinitionField(cg, expr, field));
		}

		staticBlock.add(cg.call(expr, "setDefaultOrderBy", cg.cte("id ASC")));
		staticBlock.add(cg.call(expr, "setDefaultLoader", cg.newObject(EagerPropertiesLoader.class, cg.property(cg.clazz(cg), "_pivot"))));
		staticBlock.add(cg.call(expr, "addLoader", cg.newObject(LazyPropertiesLoader.class, cg.property(cg.clazz(cg), "_pivot"))));
		cg.setAttribute(Transforms.DEFINITION_CODE, staticBlock);
}
	}
	
	
	
	
	
	
	
	protected Expression declareDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	result = expr;
	if (field.isString()) {
		result = textDefinitionField(cg, expr, field);
	}
	else if (field.isDate()) {
		result = dateDefinitionField(cg, expr, field);
	}
	else if (field.isBoolean()) {
		result = boolDefinitionField(cg, expr, field);
	}
	else if (field.isInteger()) {
		result = countDefinitionField(cg, expr, field);
	}
	else if (field.isDouble()) {
		result = numberDefinitionField(cg, expr, field);
	}
	else if (field.isEnum()) {
		result = enumDefinitionField(cg, expr, field);
	}
	else if (field.isFile()) {
		result = fileDefinitionField(cg, expr, field);
	}
	else if (field.isType(FileEntity.class)) {
//		result = fileDefinitionField(cg, expr, field);
		result = refDefinitionField(cg, expr, field);
	}
	else if (field.isCollection()) {
if (true) {
		result = listDefinitionField(cg, expr, field);
}
	}
	else if (field.isList()) {
if (true) {
		result = listDefinitionField(cg, expr, field);
}
	}
	else if (field.isType(Entity.class)) {//(field.isEntity()) {
		result = refDefinitionField(cg, expr, field);
	}
	else {
		result = refDefinitionField(cg, expr, field);
	}
	return result;
}

/*
 * 

	.addProperty("nombre", "NOMBRE", String.class, "255", ColumnExtraType.UNIQUE_NOT_NULL, "EAGER")
	.addProperty("comentario", "CCOMMENT", String.class, "3072", null, "LAZY")

	.addProperty("posicion", "POSICION", Integer.class, null, null, "EAGER")
	.addProperty("activado", "ACTIVADO", Boolean.class, null, null, "EAGER")
	.addProperty("fechaAlta", "FECHA_ALTA", java.sql.Date.class, null, null, "EAGER")
	.addProperty("densidad", "DENSIDAD", Double.class, null, null, "EAGER")
	.addReferenceProperty("capital", "CAPITAL_ID", Ciudad.class, null, "EAGER")
	.addEnumProperty("continente", "CONTINENTE", Continente.class, null, "EAGER")
	.addFileProperty("fichero", "FICHERO", java.io.File.class, null, "LAZY")
    INTERNAS: .addListProperty("provincias", "PAIS_PROVINCIAS", "PAIS_ID", "PROVINCIA_ID", Provincia.class, "LAZY")
	EXTERNAS: .addListProperty("ciudades", Ciudad.__DEFINITION__.getTableName(), "PROVINCIA_ID", null, Ciudad.class, "LAZY")
	.addProperty("comentario", "CCOMMENT", String.class, "3072", null, "LAZY")

 * 
 */


/*

	.addProperty("nombre", "NOMBRE", String.class, "255", ColumnExtraType.UNIQUE_NOT_NULL, "EAGER")
	.addProperty("comentario", "CCOMMENT", String.class, "3072", null, "LAZY")

 */
protected Expression textDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	
	if (InfoModel.checkComment(field)) {
		String fName = field.getName();
		String columnName = _getColumnName(field);

		result = cg.call(expr, "addProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(String.class), cg.cte("3072"), cg.cteNull(), cg.cte("LAZY"));
	}
	else {
		String fName = field.getName();
		String columnName = _getColumnName(field);

		result = cg.call(expr, "addProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(String.class), cg.cte("255"), cg.enumValue(ColumnExtraType.class, "UNIQUE_NOT_NULL"), cg.cte("EAGER"));
	}
	
	
	return result;
}

/*
	.addProperty("fechaAlta", "FECHA_ALTA", java.sql.Date.class, null, null, "EAGER")
 */
protected Expression dateDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	String fName = field.getName();
	String columnName = _getColumnName(field);
	result = cg.call(expr, "addProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(java.sql.Date.class), cg.cteNull(), cg.cteNull(), cg.cte("EAGER"));
	return result;
}

/*
	.addProperty("activado", "ACTIVADO", Boolean.class, null, null, "EAGER")
 */
protected Expression boolDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	String fName = field.getName();
	String columnName = _getColumnName(field);
	result = cg.call(expr, "addProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(Boolean.class), cg.cteNull(), cg.cteNull(), cg.cte("EAGER"));
	return result;
}

/*
	.addProperty("posicion", "POSICION", Integer.class, null, null, "EAGER")
 */
protected Expression countDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	String fName = field.getName();
	String columnName = _getColumnName(field);
	result = cg.call(expr, "addProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(Integer.class), cg.cteNull(), cg.cteNull(), cg.cte("EAGER"));
	return result;
}

/*
	.addProperty("densidad", "DENSIDAD", Double.class, null, null, "EAGER")
 */
protected Expression numberDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	String fName = field.getName();
	String columnName = _getColumnName(field);
	result = cg.call(expr, "addProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(Double.class), cg.cteNull(), cg.cteNull(), cg.cte("EAGER"));
	return result;
}

/*
	.addEnumProperty("continente", "CONTINENTE", Continente.class, null, "EAGER")
 */
protected Expression enumDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	String fName = field.getName();
	String columnName = _getColumnName(field);
	result = cg.call(expr, "addEnumProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(field.getType()), cg.cteNull(), cg.cte("EAGER"));
	return result;
}

/*
	.addFileProperty("fichero", "FICHERO", java.io.File.class, null, "LAZY")
 */
protected Expression fileDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	String fName = field.getName();
	String columnName = _getColumnName(field);
	result = cg.call(expr, "addFileProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(java.io.File.class), cg.cteNull(), cg.cte("LAZY"));
	return result;
}

/*
    INTERNAS: .addListProperty("provincias", "PAIS_PROVINCIAS", "PAIS_ID", "PROVINCIA_ID", Provincia.class, "LAZY")
	EXTERNAS: .addListProperty("ciudades", Ciudad.__DEFINITION__.getTableName(), "PROVINCIA_ID", null, Ciudad.class, "LAZY")
 */
protected Expression listDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	
////	ClassNode itemClass = field.getType().getGenericsTypes()[0].getType();
//	GAnnotation ann = field.getAnnotation(FieldType.class);
//	String typeName = (ann != null ? ann.getMemberString("value") : null);
	String typeName = StringUtils.emptyNotAllow((String)field.getData("type"));
	if (typeName == null) {
		typeName = field.getType();
		int idx = typeName.indexOf("<");
		typeName = (idx >= 0 ? typeName.substring(idx + 1, typeName.length() - 1) : typeName);
	}
	GClass itemClass = field.getClazz().getApplication().getClassByName(typeName);
//	ClassNode itemClass = field.getType();
//	itemClass = itemClass.redirect();
//	itemClass = itemClass.getPlainNodeReference();
//	itemClass = cg.getApplication().getCompiledClass(itemClass);
	boolean innerList = itemClass.isInner(cg);
	String fName = field.getName();
	if (innerList) {
		//INTERNAS
		String ownerPropertyName = itemClass.getOwnerName();
//		String ownerColumnName = cg.getNameWithoutPackage() + "_ID";
		String ownerColumnName = ownerPropertyName + "_ID";
		if (itemClass == cg) {
			result = cg.call(expr, "addListProperty", cg.cte(fName), cg.property(cg.clazz(cg), "_TABLE"), cg.cte(ownerColumnName), cg.cteNull(), cg.cteClass(itemClass), cg.cte("LAZY"));
		}
		else {
			result = cg.call(expr, "addListProperty", cg.cte(fName), cg.call(cg.property(cg.clazz(itemClass), "__DEFINITION__"), "getTableName"), cg.cte(ownerColumnName), cg.cteNull(), cg.cteClass(itemClass), cg.cte("LAZY"));
		}
	}
	else {
		//EXTERNAS
		String tableName = cg.getNameWithoutPackage() + "_" + fName;
		String ownerColumnName = "OWNER" + "_ID";
		String itemColumnName = "ITEM" + "_ID";
		result = cg.call(expr, "addListProperty", cg.cte(fName), cg.cte(tableName), cg.cte(ownerColumnName), cg.cte(itemColumnName), cg.cteClass(itemClass), cg.cte("LAZY"));
	}
	
	//.setupListProperty("list", true)
	result = cg.call(result, "setupListProperty", cg.cte(fName), (innerList ? cg.cteTrue() : cg.cteFalse()));
	
	
	Object attrValue = field.getAttribute("ExtraInfoProperty_childrenProperty");
	if (attrValue != null) {
		result = cg.call(result, "setupProperty", cg.cte(fName), cg.cte("ExtraInfoProperty_childrenProperty"), cg.cte(attrValue));
	}
	
	return result;
}

/*
	.addReferenceProperty("capital", "CAPITAL_ID", Ciudad.class, null, "EAGER")
 */
protected Expression refDefinitionField (GClass cg, Expression expr, GField field) {
	Expression result = null;
	String fName = field.getName();
	String columnName = _getColumnName(field);
	result = cg.call(expr, "addReferenceProperty", cg.cte(fName), cg.cte(columnName), cg.cteClass(field.getType()), cg.cteNull(), cg.cte("EAGER"));
	return result;
}
	
	

	
}
