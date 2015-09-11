package org.effortless.gencore.mapping;

import java.sql.ResultSet;

import org.effortless.orm.DbManager;
import org.effortless.orm.Entity;
import org.effortless.orm.FileEntity;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;

public abstract class LoadLevelsTransform extends AbstractBaseTransform {

	public LoadLevelsTransform () {
		super();
	}
	
	protected void add_loadEagerLazy (GClass cg, boolean eager) {
		String methodName = (eager ? "_loadEager" : "_loadLazy");
		
		GMethod mg = cg.addMethod(methodName).setProtected(true).addParameter(Object.class, "target").addParameter(DbManager.class, "db").addParameter(ResultSet.class, "rs");
		mg.add(mg.call(mg.cteSuper(), methodName, mg.var("target"), mg.var("db"), mg.var("rs")));
		
		mg.declVariable(cg, "result", mg.cast(cg, mg.var("target")));
		
		java.util.List fields = cg.getProperties();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			boolean fieldEager = isEager(field);
			if (eager == fieldEager) {
				loadEagerLazyField(mg, cg, field, eager);
			}
		}
	}
	
	
	protected void loadEagerLazyField(GMethod mg, GClass cg, GField field, boolean eager) {
		if (field.isString()) {
			textLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isDate()) {
			dateLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isBoolean()) {
			boolLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isInteger()) {
			countLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isDouble()) {
			numberLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isEnum()) {
			enumLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isFile()) {
			fileLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isType(FileEntity.class)) {
			refLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isCollection()) {
			listLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isList()) {
			listLoadEagerLazyField(mg, cg, field, eager);
		}
		else if (field.isType(Entity.class)) {//(field.isEntity()) {
			refLoadEagerLazyField(mg, cg, field, eager);
		}
		else {
			refLoadEagerLazyField(mg, cg, field, eager);
		}
	}

	
	/*
		result.nombre = (String)__DEFINITION__.loadValue("NOMBRE", rs);
		result._setupLoaded("nombre");
	 * 
	 */
	protected void textLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		simpleLoadEagerLazyField(String.class, mg, cg, field, eager);
	}

	protected void simpleLoadEagerLazyField (Class<?> castType, GMethod mg, GClass cg, GField field, boolean eager) {
		simpleLoadEagerLazyField(castType.getName(), mg, cg, field, eager);
	}
	
	protected void simpleLoadEagerLazyField (String castType, GMethod mg, GClass cg, GField field, boolean eager) {
		String fieldName = field.getName();
		String columnName = _getColumnName(field);
		mg.add(mg.call(mg.var("result"), "_setupLoaded", mg.cte(fieldName)));
		mg.add(mg.assign(mg.property(mg.var("result"), fieldName), mg.cast(castType, mg.call(mg.property(mg.clazz(cg), "__DEFINITION__"), "loadValue", mg.cte(columnName), mg.var("rs")))));
	}
	
	/*
		result.fechaAlta = (java.sql.Date)__DEFINITION__.loadValue("FECHA_ALTA", rs);
		result._setupLoaded("fechaAlta");
	 */
	protected void dateLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		simpleLoadEagerLazyField(java.sql.Date.class, mg, cg, field, eager);
	}

	/*
	result.activado = (Boolean)__DEFINITION__.loadValue("ACTIVADO", rs);
	result._setupLoaded("activado");
 */
	protected void boolLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		simpleLoadEagerLazyField(Boolean.class, mg, cg, field, eager);
	}

	/*
	result.posicion = (Integer)__DEFINITION__.loadValue("POSICION", rs);
	result._setupLoaded("posicion");
 */
	protected void countLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		simpleLoadEagerLazyField(Integer.class, mg, cg, field, eager);
	}

	/*
	result.densidad = (Double)__DEFINITION__.loadValue("DENSIDAD", rs);
	result._setupLoaded("densidad");
 */
	protected void numberLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		simpleLoadEagerLazyField(Double.class, mg, cg, field, eager);
	}

	/*
	result.continente = (Continente)__DEFINITION__.loadValue("CONTINENTE", rs);
	result._setupLoaded("continente");
 */
	protected void enumLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		simpleLoadEagerLazyField(field.getType(), mg, cg, field, eager);
	}

	/*
		result.fichero = (java.io.File)__DEFINITION__.loadValue("FICHERO", rs);
		result._setupLoaded("fichero");
	 */
	protected void fileLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		simpleLoadEagerLazyField(java.io.File.class, mg, cg, field, eager);
	}

	/*
//		result.provincias = PropertyList.create(Provincia.class, result, (ListPropertyEntity)__DEFINITION__.getProperty("provincias"), Provincia.listBy(db).on(result, "provincias"));
//		result._setupLoaded("provincias");
	 * 
	 */
	protected void listLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
	}

	/*
		Long _provinciaId = (Long)__DEFINITION__.loadValue("PROVINCIA_ID", rs);
		result.provincia = (_provinciaId != null ? new Provincia(_provinciaId) : null);
		result._setupLoaded("provincia");
	 */
	protected void refLoadEagerLazyField(GMethod mg, GClass cg, GField field,
			boolean eager) {
		String fieldName = field.getName();
		String columnName = _getColumnName(field);
		String _varId = "_" + fieldName + "Id";
		//Long _provinciaId = (Long)__DEFINITION__.loadValue("PROVINCIA_ID", rs);
		mg.declVariable(Long.class, _varId, mg.cast(Long.class, mg.call(mg.property(mg.clazz(cg), "__DEFINITION__"), "loadValue", mg.cte(columnName), mg.var("rs"))));
if (false) {
		mg.add(mg.debug("LOADING REF " + columnName + " -> id = "));
		mg.add(mg.debug(mg.var(_varId)));
}
		//result._setupLoaded("provincia");
		mg.add(mg.call(mg.var("result"), "_setupLoaded", mg.cte(fieldName)));
		//result.provincia = (_provinciaId != null ? new Provincia(_provinciaId) : null);
		mg.add(mg.assign(mg.property(mg.var("result"), fieldName), mg.triple(mg.notNull(mg.var(_varId)), mg.newObject(field.getType(), mg.var(_varId)), mg.cteNull())));
	}

}
