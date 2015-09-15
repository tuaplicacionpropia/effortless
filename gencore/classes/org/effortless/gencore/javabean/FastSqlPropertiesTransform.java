package org.effortless.gencore.javabean;

import java.util.List;

//import org.effortless.ann.FieldType;
import org.effortless.jast.transforms.StageTransform;
import org.effortless.core.Collections;
import org.effortless.core.StringUtils;
import org.effortless.orm.Filter;
import org.effortless.orm.definition.ListPropertyEntity;
import org.effortless.orm.impl.EntityFilter;
import org.effortless.orm.impl.PropertyList;
import org.effortless.jast.Expression;
import org.effortless.jast.GAnnotation;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.objectweb.asm.Opcodes;

//@StageTransform("runModel")
public class FastSqlPropertiesTransform extends AbstractPropertiesTransform {

	public FastSqlPropertiesTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass cg = (GClass)node;
		List fields = cg.getProperties();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			cg.removeField(field);
			adjustField(field);
			cg.addField(field);
			processField(field);
		}
	}

	
	
/*

	protected String comentario;
	
	public String getComentario () {
		_loadOnDemand("comentario", this.comentario, __DEFINITION__);
		return this.comentario;
	}
	
	public void setComentario (String newValue) {
		_loadOnDemand("comentario", this.comentario, __DEFINITION__);
		_setProperty("comentario", this.comentario, this.comentario = newValue);
	}


 */
	
	protected void adjustField(GField field) {
		String type = field.getType();
		if ("Date".equals(type)) {
			field.setType(java.util.Date.class);
		}
	}

	protected void textProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void dateProcessField (GField field) {
		protectField(field);
		addInitiateDefaultDate(field);
		addEntityGetter(field);
		addEntitySetter(field);
//		GMethod mg = addEntitySetter(field);
//		mg.add(mg.debug("debug set " + field.getName()));
	}
	
	protected void boolProcessField (GField field) {
		protectField(field);
		addInitiateDefaultBoolean(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void countProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void numberProcessField (GField field) {
		protectField(field);
		addInitiateDefaultNumber(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void enumProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}

	protected void fileProcessField (GField field) {
		CreateFileEntityTransform feT = new CreateFileEntityTransform();
		feT.process(field);
		GClass fileClazz = feT.getResult();

		//	protected FileEntity fichero;
		field.setProtected(true);//setModifiers(Opcodes.ACC_PROTECTED);
		field.setType(fileClazz);
		
		addInitiate(field);
		
		String getterName = field.getGetterName();
		String setterName = field.getSetterName();
		
		
		
		addEntityGetter(field);

//		@javax.persistence.Transient
//		public java.io.File getFicheroFile () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getContent() : null);
//		}
		GMethod mg = field.getClazz().addMethod(getterName + "File").setPublic(true).setReturnType(java.io.File.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContent"), mg.cteNull()));
		
		
//		@javax.persistence.Transient
//		public String getFicheroPath () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getPath() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "Path").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getPath"), mg.cteNull()));
		
		addEntitySetter(field);
		
//		public void setFichero (java.io.File newValue) {
//			if (newValue != null) {
//				FileEntity entity = getFichero();
//				entity = (entity != null ? entity : new FileEntity());
//				entity.setContent(newValue);
//				setFichero(entity);
//			}
//			else {
//				setFichero((FileEntity)null);
//			}
//		}
		mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(java.io.File.class, "newValue");
		GCode ifCode = mg.newBlock();
		ifCode.declVariable(fileClazz, "entity", mg.call(getterName));
		ifCode.add(mg.assign(mg.var("entity"), mg.triple(mg.notNull("entity"), mg.var("entity"), mg.newObject(fileClazz))));
		ifCode.add(mg.call("entity", "setContent", mg.var("newValue")));
		ifCode.add(mg.call(setterName, mg.var("entity")));
		GCode elseCode = mg.newBlock();
		elseCode.add(mg.call(setterName, mg.cast(fileClazz, mg.cteNull())));
		mg.addIf(mg.notNull("newValue"), ifCode, elseCode);
		
		
//		public void setFichero (String newValue) {
//			setFichero(new java.io.File(newValue));
//		}
		mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(String.class, "newValue");
		mg.add(mg.call(setterName, mg.newObject(java.io.File.class, mg.var("newValue"))));
		
		
//		@javax.persistence.Transient
//		public String getFicheroName () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getName() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "Name").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getName"), mg.cteNull()));
		
//		@javax.persistence.Transient
//		public String getFicheroFormat () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getFormat() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "Format").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getFormat"), mg.cteNull()));

//		@javax.persistence.Transient
//		public String getFicheroContentType () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getContentType() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "ContentType").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContentType"), mg.cteNull()));
	}
	
	protected void listProcessField (GField field) {
////		ClassNode c = ClassHelper.makeReference();//field.getType()
//		ClassNode c0 = field.getType();
//		ClassNode c1 = c0.redirect();
//		field.setType(c1);
//		ClassNode c2 = ClassHelper.make(c0.getName());
//		field.setType(c2);
//		ClassNode c3 = new ClassNode(c0.getTypeClass());
//		field.setType(c3);
if (true) {
		transformListField(field);
}
if (true) {
		protectListField(field);
		addListInitiate(field);
		addListEntityGetter(field);
		addListEntitySetter(field);
}
//		System.out.println("field =" + field);
	}
	
//	protected PropertyList<Ciudad> ciudades;
	protected void protectListField(GField field) {
		protectField(field);
	}


	protected void addListInitiate(GField field) {
		addInitiate(field);
		// TODO Auto-generated method stub
		
//		protected void _initCiudades () {
//			this.ciudades = (this.ciudades != null ? this.ciudades : PropertyList.create(Ciudad.class, this, (ListPropertyEntity)__DEFINITION__.getProperty("ciudades"), Ciudad.listAll(/*this._db*/).on(this, "ciudades")));
//		}
//
		
//		GAnnotation ann = field.getAnnotation(FieldType.class);
//		String typeName = (ann != null ? ann.getMemberString("value") : null);
		String typeName = StringUtils.emptyNotAllow((String)field.getData("type"));
		if (typeName == null) {
			typeName = field.getType();
			int idx = typeName.indexOf("<");
			typeName = (idx >= 0 ? typeName.substring(idx + 1, typeName.length() - 1) : typeName);
		}
		
		GClass itemType = field.getClazz().getApplication().getClassByName(typeName);
		
		String methodName = "_init" + StringUtils.capFirst(field.getName());
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
//		mg.cteNull()
		//PropertyList.create(Ciudad.class, this, (ListPropertyEntity)__DEFINITION__.getProperty("ciudades"), Ciudad.listAll(/*this._db*/).on(this, "ciudades"))

//		mg.gPrintln("INIT BEGIN LIST PROPERTY ");
		
		//Ciudad.listAll(/*this._db*/).on(this, "ciudades")
//		Expression _listAll = mg.call(mg.callStatic(itemType, "listAll"), "on", mg.cteThis(), mg.cte(field.getName()));
			//
		//org.effortless.fastsql.impl.EntityFilter<?>.buildEntityFilter(DEF)
		Expression _listAll = mg.call(mg.callStatic(org.effortless.orm.impl.EntityFilter.class, "buildEntityFilter", mg.property(itemType.getNameWithoutPackage(), "__DEFINITION__")), "on", mg.cteThis(), mg.cte(field.getName()));
		if (true) {
			_listAll = mg.call(_listAll, "isFalse", mg.cte("deleted"));
		}
//		mg.declVariable(Filter.class, "_listAll", _listAll);
//		mg.gPrintln("INIT BEGIN2 LIST PROPERTY ");
		Expression _loadDef = mg.cast(ListPropertyEntity.class, mg.call(mg.property(field.getClazz().getNameWithoutPackage(), "__DEFINITION__"), "getProperty", mg.cte(field.getName())));
//		mg.declVariable(ListPropertyEntity.class, "_loadDef", _loadDef);
//		mg.gPrintln("INIT BEGIN3 LIST PROPERTY ");
		Expression createList = mg.callStatic(PropertyList.class, "create", mg.cteClass(itemType), mg.cteThis(), _loadDef, _listAll);
//		mg.declVariable(PropertyList.class, "_list", createList);
//		mg.gPrintln("INIT BEGIN3 LIST PROPERTY ");
		Expression expr = mg.triple(mg.notNull(mg.field(field)), mg.field(field), createList);
		mg.add(mg.assign(mg.field(field), expr));
		
//		mg.gPrintln("INIT LIST PROPERTY " + field.getName() + " " + mg.triple(mg.notNull(mg.field(field)), mg.cte("NO NULO"), mg.cte("NULL")));
		
	}


//	
//	public java.util.List<Ciudad> getCiudades () {
//		_loadOnDemand("ciudades", this.ciudades, __DEFINITION__);
//		_initCiudades();
//		return this.ciudades;
//	}
//	
	protected GMethod addListEntityGetter(GField field) {
		GMethod result = null;
		String methodName = field.getGetterName();
		
//		GAnnotation ann = field.getAnnotation(FieldType.class);
//		String typeName = (ann != null ? ann.getMemberString("value") : null);
		String typeName = StringUtils.emptyNotAllow((String)field.getData("type"));
		if (typeName == null) {
			typeName = field.getType();
			int idx = typeName.indexOf("<");
			typeName = (idx >= 0 ? typeName.substring(idx + 1, typeName.length() - 1) : typeName);
		}
		
		GClass itemClass = field.getClazz().getApplication().getClassByName(typeName);
		boolean innerList = itemClass.isInner(field.getClazz());
		
		
		GMethod mg = field.getClazz().addMethod(methodName).setPublic(true).setReturnType(field.getType());
		
//		GAnnotation gAnn = mg.addAnnotation(ExtraInfoProperty.class);
		field.setAttribute("ExtraInfoProperty_inner", (innerList ? Boolean.TRUE : Boolean.FALSE));
		field.setAttribute("ExtraInfoProperty_item", itemClass.getName());
//		field.setAttribute("ExtraInfoProperty_inner", (innerList ? Boolean.TRUE : Boolean.FALSE));
//		ExtraInfoProperty_inner
//		gAnn = gAnn.addMember("inner", (innerList ? mg.cteTrue() : mg.cteFalse()));
//		gAnn = gAnn.addMember("item", mg.cteClass(itemClass.getClassNode()));
//		gAnn = gAnn.addMember("childrenProperty", mg.cte("tmp"));
//		a.properties()
		
//		mg.gPrintln("GET LIST PROPERTY " + field.getName());
//		mg.gPrintln("GET LIST PROPERTY2 " + field.getName());
//		mg.add(mg.call("_loadOnDemand", mg.cte(field.getName()), mg.field(field), mg.property(mg.cteClass(field.getClazz().getClassNode()), "__DEFINITION__")));
//		mg.gPrintln("GET LIST PROPERTY3 " + field.getName());
		mg.add(mg.call("_init" + StringUtils.capFirst(field.getName())));
//		mg.gPrintln("GET LIST PROPERTY4 " + field.getName());
//		mg.gPrintln("GET AFTER INIT LIST PROPERTY " + field.getName());
		mg.addReturn(mg.field(field));
		result = mg;
		return result;
	}


//	public void setCiudades (java.util.List<Ciudad> newValue) {
//	getCiudades().addAll(newValue);
//}

	protected GMethod addListEntitySetter(GField field) {
		GMethod result = null;
		String setterName = field.getSetterName();
		String getterName = field.getGetterName();
		GMethod mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(field.getType(), "newValue");
		mg.call(mg.call(getterName), "addAll", mg.var("newValue"));
		result = mg;
		return result;
	}


	
	
	
	
	protected void refProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	

	
	
	protected void transformListField (GField field) {
		String typeName = field.getTypeWithoutPackage();
		if (typeName.endsWith("List")) {
			typeName = typeName.substring(0, typeName.length() - "List".length());
			GClass itemClass = field.getClazz().getApplication().getClassByName(typeName);

			if (itemClass != null) {
//				ClassNode newListClass = field.getClazz().createGenericType(java.util.List.class, itemClass.getClassNode());
				field.setType(PropertyList.class);
				
//				field.addAnnotation(field.createAnnotation(FieldType.class).addMember("value", field.cte(typeName)));
				field.setData("type", typeName);
			}
		}
		
//		if (false) {
//			GenericsType gt = type.getGenericsTypes()[0];
//			
//			ClassNode itemClass = gt.getType();
//	//		itemClass = itemClass.getPlainNodeReference();
//			itemClass = itemClass.redirect();
//			gt.setType(itemClass);
//		}
//		
//		if (false) {
////			ClassNode itemClass = type.getComponentType();
//			ClassNode newListClass = field.getClazz().createGenericType(java.util.List.class, itemClass);
//			
//			newListClass = GenericsUtils.parameterizeType(ClassNodeHelper.toClassNode(java.util.List.class), itemClass);
//
//			newListClass = GenericsUtils.parameterizeType(itemClass, ClassNodeHelper.toClassNode(java.util.List.class));
//			
//			field.getField().setType(newListClass);
//		}
	}
	
	
	protected void protectField (GField field) {
		field.setProtected(true);
		field.setInitialValue(null);
	}

	protected void addInitiate (GField field) {
		String methodName = field.getInitiateName();
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		mg.add(mg.assign(mg.field(field), mg.cteNull()));
	}
	

	
	/*
	 * 
	public String getComentario () {
		_loadOnDemand("comentario", this.comentario, __DEFINITION__);
		return this.comentario;
	}
	 * 
	 */
	public GMethod addEntityGetter (GField field) {
		GMethod result = null;
		String methodName = field.getGetterName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setPublic(true).setReturnType(field.getType());
//		mg.addAnnotation(groovy.transform.CompileStatic.class);
if (false) {
			mg.add(mg.debug("GET property = " + methodName + " on class -> " + field.getClazz().getSimpleName() + ", parameter type = " + field.getTypeWithoutPackage()));
			mg.add(mg.callStatic(org.effortless.core.Exceptions.class, "whereAmI"));
}
//		mg.add(mg.call("_loadOnDemand", mg.cte(field.getName()), mg.field(field), mg.property(mg.cteClass(field.getClazz().getClassNode()), "__DEFINITION__")));
		mg.add(mg.call("_loadOnDemand", mg.cte(field.getName()), mg.field(field), mg.realProperty(mg.clazz(field.getClazz()), "__DEFINITION__")));
		mg.addReturn(mg.field(field));
		result = mg;
		return result;
	}
	
	
	/*


	public void setComentario (String newValue) {
//		_loadOnDemand("comentario", this.comentario, __DEFINITION__);
//		_setProperty("comentario", this.comentario, this.comentario = newValue);
		_setProperty("comentario", getComentario(), this.comentario = newValue);
	}



	 */
	public GMethod addEntitySetter (GField field) {
		GMethod result = null;
		String setterName = field.getSetterName();
		String fName = field.getName();
		String getterName = field.getGetterName();
		GMethod mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(field.getType(), "newValue");
//		mg.addAnnotation(groovy.transform.CompileStatic.class);
if (false) {
		mg.add(mg.debug("SET property = " + setterName + " on class -> " + field.getClazz().getSimpleName() + ", parameter type = " + field.getTypeWithoutPackage()));
		mg.add(mg.callStatic(org.effortless.core.Exceptions.class, "whereAmI"));
}
//		mg.add(mg.call("_loadOnDemand", mg.cte(field.getName()), mg.field(field), mg.property(mg.cteClass(field.getClazz().getClassNode()), "__DEFINITION__")));
if (true) {
		mg.add(mg.call("_loadOnDemand", mg.cte(field.getName()), mg.field(field), mg.realProperty(mg.clazz(field.getClazz()), "__DEFINITION__")));
		mg.add(mg.call("_setProperty", mg.cte(fName), mg.field(field), mg.assign(mg.field(field), mg.var("newValue"))));//this._setProperty('text', this.text, this.text = newValue);
}
else {
		mg.add(mg.assign(mg.field(field), mg.var("newValue")));//this.text = newValue;
}
//		mg.add(mg.call("_setProperty", mg.cte(fName), mg.call(getterName), mg.assign(mg.field(field), "newValue")));//this._setProperty('text', this.text, this.text = newValue);
		result = mg;
		return result;
	}

	
	
	protected void addInitiateDefaultBoolean (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		boolean defaultValue = loadDefaultBooleanValue(field);
		Expression defaultExpression = (defaultValue ? mg.cteTRUE() : mg.cteFALSE());
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
	}
	
	public static final String[] BOOL_DEFAULT_TRUE = {"activo", "enabled"};
	
	protected boolean loadDefaultBooleanValue (GField field) {
		boolean result = false;
		String fieldName = (field != null ? field.getName() : null);
		result = Collections.like(BOOL_DEFAULT_TRUE, fieldName);
		return result;
	}

	protected void addInitiateDefaultDate (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		boolean defaultValue = loadDefaultDateValue(field);
//		Expression defaultExpression = (defaultValue ? mg.callConstructor(java.util.Date.class) : mg.cteNull());
//		Expression defaultExpression = mg.callConstructor(java.util.Date.class);
//		Expression defaultExpression = mg.callStatic(org.effortless.core.DateUtils.class, "getCurrentDate");
		Expression defaultExpression = (defaultValue ? mg.callStatic(org.effortless.core.DateUtils.class, "getCurrentDate") : mg.cteNull());
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
//		mg.add(mg.debug("inicializando " + field.getName()));
	}
	
	public static final String[] DATE_CURRENT_DEFAULT_TRUE = {"alta", "create"};
	public static final String[] DATE_CURRENT_DEFAULT_TRUE_EQUALS = {"fecha", "date"};
	
	protected boolean loadDefaultDateValue (GField field) {
		boolean result = false;
		String fieldName = (field != null ? field.getName() : null);
		result = Collections.like(DATE_CURRENT_DEFAULT_TRUE, fieldName);
		result = result || Collections.contains(DATE_CURRENT_DEFAULT_TRUE_EQUALS, fieldName);
		return result;
	}
	
	protected void addInitiateDefaultNumber (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		Expression defaultExpression = mg.callStatic(Double.class, "valueOf", mg.cte(0.0));
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
	}

}
