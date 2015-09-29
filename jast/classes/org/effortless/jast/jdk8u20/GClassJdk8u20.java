package org.effortless.jast.jdk8u20;

import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.core.Collections;
import org.effortless.core.EnumString;
import org.effortless.core.StringUtils;
import org.effortless.jast.jdk8u20.util.Factory;
import org.effortless.jast.transforms.Transforms;

import com.sun.tools.javac.tree.JCTree;

import org.effortless.jast.Expression;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GUnit;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;

public class GClassJdk8u20 extends GCodeJdk8u20 implements GClass {

	public GClassJdk8u20 () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.unit = null;
	}
	
	protected String _doGetKeyAttributes () {
		return "GClass:node_attributes";
	}
	
	protected GUnit unit;
	
	public GUnit getUnit () {
		return this.unit;
	}
	
	public void setUnit (GUnit newValue) {
		this.unit = newValue;
	}
	
	protected JCTree.JCClassDecl node;
	
	public JCTree.JCClassDecl getNode () {
		return this.node;
	}
	
	public void setNode (JCTree.JCClassDecl newValue) {
		this.node = newValue;
	}

	public Object getNativeNode () {
		return this.node;
	}
	
	public String getName() {
		return Factory.getClassName(this);
	}
	
	public GClassJdk8u20 setName(String newValue) {
		Factory.setClassName(this, newValue);
		return this;
	}
	
	public java.util.List getFields () {
		java.util.List result = null;
		result = new java.util.ArrayList<GField>();
		java.util.List defs = this.node.defs;
		int defsSize = (defs != null ? defs.size() : 0);
		for (int i = 0; i < defsSize; i++) {
			JCTree def = (JCTree)defs.get(i);
			JCTree.JCVariableDecl variableDecl = null; try { variableDecl = (JCTree.JCVariableDecl)def; } catch (ClassCastException e) { variableDecl = null; }
			if (variableDecl != null) {
				GFieldJdk8u20 resultField = new GFieldJdk8u20();
				resultField.setNode(variableDecl);
				resultField.setClazz(this);
				result.add(resultField);
			}
		}
		return result;
	}
	
	public GField addField(String type, String name) {
		return addField(type, name, (Expression)null);
	}
	
	@Override
	public GField addField(Class<?> type, String name) {
		return addField(ClassUtils.getName(type), name, (Expression)null);
	}

	@Override
	public GField addField(GClass type, String name) {
		return addField(type.getFullName(), name, (Expression)null);
	}

	@Override
	public GField addField(Class<?> type, String name, Expression initialValue) {
		return addField(ClassUtils.getName(type), name, initialValue);
	}

	@Override
	public GField addField(GClass type, String name, Expression initialValue) {
		return addField(type.getFullName(), name, initialValue);
	}

	@Override
	public GField addField(String type, String name, Expression initialValue) {
		return Factory.addField(this, type, name, (ExpressionJdk8u20)initialValue);
	}

	@Override
	public GField addField(GField field) {
		return Factory.addField(this, field.getType(), field.getName(), (ExpressionJdk8u20)field.getInitialValue());
	}

	@Override
	public GClass removeField(String name) {
		Factory.removeField(this, name);
		return this;
	}

	@Override
	public GClass removeField(GField field) {
		Factory.removeField(this, field.getName());
		return this;
	}

	public GClass removeMethod (GMethod method) {
		Factory.removeMethod(this, method);
		return this;
	}

	public GClass setPublic (boolean enabled) {
		this.node.mods = setPublic(this.getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GClass setPublic () {
		return setPublic(true);
	}
	
	public boolean isPublic () {
		return isPublic(this.node.mods);
	}
	
	public GClass setProtected (boolean enabled) {
		this.node.mods = setProtected(this.getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GClass setProtected () {
		return setProtected(true);
	}
	
	public boolean isProtected () {
		return isProtected(this.node.mods);
	}
	
	public GClass setPrivate (boolean enabled) {
		this.node.mods = setPrivate(this.getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GClass setPrivate () {
		return setPrivate(true);
	}
	
	public boolean isPrivate () {
		return isPrivate(this.node.mods);
	}
	
	public GClass setFinal (boolean enabled) {
		this.node.mods = setFinal(this.getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GClass setFinal () {
		return setFinal(true);
	}
	
	public boolean isFinal () {
		return isFinal(this.node.mods);
	}
	
	public GClass setStatic (boolean enabled) {
		this.node.mods = setStatic(this.getUnit(), this.node.mods, enabled);
		return this;
	}
	
	public GClass setStatic () {
		return setStatic(true);
	}
	
	public boolean isStatic () {
		return isStatic(this.node.mods);
	}
	
	public boolean isAbstract () {
		return isAbstract(this.node.mods);
	}
	
	@Override
	public GClass setAbstract(boolean enabled) {
		this.node.mods = setAbstract(this.getUnit(), this.node.mods, enabled);
		return this;
	}

	@Override
	public GClass setAbstract() {
		return setAbstract(true);
	}
	

	public java.util.List getMethods () {
		java.util.List result = null;
		result = new java.util.ArrayList();
		java.util.List defs = this.node.defs;
		int defsSize = (defs != null ? defs.size() : 0);
		for (int i = 0; i < defsSize; i++) {
			JCTree def = (JCTree)defs.get(i);
			JCTree.JCMethodDecl methodDecl = null; try { methodDecl = (JCTree.JCMethodDecl)def; } catch (ClassCastException e) { methodDecl = null; }
			if (methodDecl != null) {
				GMethodJdk8u20 resultMethod = new GMethodJdk8u20();
				resultMethod.setNode(methodDecl);
				resultMethod.setClazz(this);
				result.add(resultMethod);
			}
		}
		return result;
	}
	
	public GMethod addMethod(String name) {
		GMethod result = null;
		result = Factory.addMethod(this, name);
		return result;
	}

	@Override
	public GClass addEnumCte(String name) {
		return addEnumCte(name, null, null);
	}

	@Override
	public GClass addEnumCte (String name, Expression parent) {
		Factory.addEnumStringCte(this, name, (ExpressionJdk8u20)parent);
		return this;
	}
	
	
	@Override
	public GClass addEnumCte(String name, String parent, List children) {
		if (isType(EnumString.class)) {
			Factory.addEnumStringCte(this, name, parent, children);
		}
		else {
			Factory.addJavaEnumCte(this, name, parent, children);
		}
		return this;
	}
	
	public GApp getApp () {
		return getUnit().getApp();
	}
	

	@Override
	public GApp getApplication() {
		return getUnit().getApp();
	}

	@Override
	public void setApplication(GApp newValue) {
		getUnit().setApp(newValue);
	}

	@Override
	public void detach() {
		this.getUnit().removeClass(this);
		setUnit(null);
	}

	@Override
	public GClass setSuperClass(String superClass) {
		Factory.setSuperClass(this, superClass);
		return this;
	}

	@Override
	public GClass setSuperClass(Class<?> superClass) {
		return setSuperClass(ClassUtils.getName(superClass));
	}

	@Override
	public GClass setSuperClass(GClass superClass) {
		return setSuperClass(superClass.getFullName());
	}

	@Override
	public GClass setSuperClass(Expression superClass) {
		Factory.setSuperClass(this, (ExpressionJdk8u20)superClass);
		return this;
	}
	
	
	@Override
	public GClass addInterface(Expression iface) {
		Factory.addInterface(this, (ExpressionJdk8u20)iface);
		return this;
	}

	@Override
	public GClass addInterface(String iface) {
		Factory.addInterface(this, iface);
		return this;
	}

	@Override
	public GClass addInterface(Class<?> iface) {
		return addInterface(ClassUtils.getName(iface));
	}

	@Override
	public GClass addInterface(GClass iface) {
		return addInterface(iface.getFullName());
	}

	@Override
	public GClass addInterfaces(Class<?>... ifaces) {
		if (ifaces != null) {
			for (Class<?> iface : ifaces) {
				addInterface(iface);
			}
		}
		return this;
	}

	@Override
	public GClass addInterfaces(GClass... ifaces) {
		if (ifaces != null) {
			for (GClass iface : ifaces) {
				addInterface(iface);
			}
		}
		return this;
	}

	@Override
	public GClass addInterfaces(String... ifaces) {
		if (ifaces != null) {
			for (String iface : ifaces) {
				addInterface(iface);
			}
		}
		return this;
	}

	
	
	@Override
	public GClass addInnerClass(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInnerClassesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GMethod addConstructor() {
		return Factory.addConstructor(this);
	}

	@Override
	public GCode addStaticBlock() {
		return Factory.addStaticBlock(this);
	}

	@Override
	public String queryAnnotation(Class<?> annotation, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAnnotation(String annotation, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAnnotation(Class<?> annotation, String member,
			String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAnnotation(String annotation, String member,
			String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAnnotation(String clazz, Class<?> annotation,
			String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAnnotation(String clazz, Class<?> annotation,
			String member, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAnnotation(String clazz, String annotation,
			String member, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GClass addCte(GClass type, String name, Object value) {
		return addCte(type.getFullName(), name, value);
	}

	@Override
	public GClass addCte(Class<?> type, String name, Object value) {
		return addCte(ClassUtils.getName(type), name, value);
	}

	@Override
	public GClass addCte(String type, String name, Object value) {
		Factory.addCte(this, type, name, value);
		return this;
	}

	@Override
	public boolean checkEnum() {
		boolean result = false;
		result = isType(Enum.class);
		return result;
	}

	@Override
	public String getSimpleName() {
		return getNameWithoutPackage();
	}

	@Override
	public String getFullName() {
		String result = null;
		String packageName = this.getPackageName();
		String className = this.getNameWithoutPackage();
		result = (packageName != null ? packageName + "." : "") + className;
		return result;
	}

	
	@Override
	public String getNameWithoutPackage() {
		return this.getName();
	}

	@Override
	public String getPackageName() {
		return this.getUnit().getPackageName();
	}

	@Override
	public GClass setPackageName (String newValue) {
		this.getUnit().setPackageName(newValue);
		return this;
	}

	@Override
	public List getAllFields() {
		List result = null;
		result = getFields();
		return result;
	}

	@Override
	public int getAllFieldsSize() {
		int result = 0;
		List fields = getAllFields();
		result = (fields != null ? fields.size() : 0);
		return result;
	}

	@Override
	public List getFieldsByType(String type) {
		List result = null;
		result = new java.util.ArrayList();
		if (type != null) {
			List list = getFields();
			int listSize = (list != null ? list.size() : 0);
			for (int i = 0; i < listSize; i++) {
				GField field = (GField)list.get(i);
				if (field != null && type.equals(field.getType())) {
					result.add(field);
				}
			}
		}
		return result;
	}

	@Override
	public List getCteFields() {
		List result = null;
		result = new java.util.ArrayList();
		List list = getFields();
		int listSize = (list != null ? list.size() : 0);
		for (int i = 0; i < listSize; i++) {
			GField field = (GField)list.get(i);
			if (field != null && field.isPublic() && field.isStatic() && field.isFinal()) {
				result.add(field);
			}
		}
		return result;
	}

	@Override
	public List getCteFieldsByType(String type) {
		List result = null;
		result = new java.util.ArrayList();
		if (type != null) {
			List list = getFields();
			int listSize = (list != null ? list.size() : 0);
			for (int i = 0; i < listSize; i++) {
				GField field = (GField)list.get(i);
				if (field != null && type.equals(field.getType())) {
					result.add(field);
				}
			}
		}
		return result;
	}

	@Override
	public int getFieldsSize() {
		int result = 0;
		java.util.List fields = this.getFields();
		result = (fields != null ? fields.size() : 0);
		return result;
	}

	@Override
	public GField getField(String name) {
		return Factory.getField(this, name);
	}

	@Override
	public GField getProperty(String name) {
		GField result = null;
		if (name != null) {
			GField field = getField(name);
			if (field != null && field.isProperty()) {
				result = field;
			}
		}
		return result;
	}

	@Override
	public List getProperties(String[] names) {
		List result = null;
		result = new java.util.ArrayList();
		if (names != null) {
			for (String name : names) {
				GField property = getProperty(name);
				if (property != null) {
					result.add(property);
				}
			}
		}
		return result;
	}

	@Override
	public GMethod getMethod(String name) {
		return Factory.getMethod(this, name);
	}

	@Override
	public List getListMethods(String name) {
		List result = null;
		if (name != null) {
			result = new java.util.ArrayList();
			List all = getAllDeclaredMethods();
			int allSize = (all != null ? all.size() : 0);
			for (int i = 0; i < allSize; i++) {
				GMethod item = (GMethod)all.get(i);
				if (item != null && name.equals(item.getName())) {
					result.add(item);
				}
			}
		}
		return result;
	}

	@Override
	public List getAllDeclaredMethods() {
		return Factory.getAllDeclaredMethods(this);
	}

	@Override
	public List listRefFields(Class<?> type) {
		List result = null;
		result = new java.util.ArrayList();
		java.util.List fields = getFields();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			if (field != null && (field.isType(type))) {
				result.add(field);
			}
		}
		return result;
	}

	@Override
	public List listFields(Class<?> type) {
		List result = null;
		result = new java.util.ArrayList();
		java.util.List fields = getFields();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			if (field != null && (field.isCollection() || field.isList())) {
				result.add(field);
			}
		}
		return result;
	}

	@Override
	public List getProperties() {
		List result = null;
		result = new java.util.ArrayList();
		java.util.List fields = getFields();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			if (field != null && field.isProperty()) {
				result.add(field);
			}
		}
		return result;
	}

	@Override
	public int getPropertiesSize() {
		int result = 0;
		List properties = getProperties();
		result = (properties != null ? properties.size() : 0);
		return result;
	}
	
	
	@Override
	public List getViewProperties() {
		List result = null;
		result = new java.util.ArrayList();
		java.util.List fields = getFields();
		int fieldsSize = (fields != null ? fields.size() : 0);
		for (int i = 0; i < fieldsSize; i++) {
			GField field = (GField)fields.get(i);
			if (field != null && field.isViewProperty()) {
				result.add(field);
			}
		}
		return result;
	}

	@Override
	public List getPublicMethods() {
		List result = null;
		result = new java.util.ArrayList();
		java.util.List methods = getMethods();
		int methodsSize = (methods != null ? methods.size() : 0);
		for (int i = 0; i < methodsSize; i++) {
			GMethod method = (GMethod)methods.get(i);
			if (method != null && method.isPublic()) {
				result.add(method);
			}
		}
		return result;
	}

	@Override
	public String getModuleName() {
		String result = null;
		String packageName = this.getPackageName();
		packageName = (packageName != null ? packageName.trim() : "");
		if (packageName.length() > 0) {
			int indexOf = packageName.lastIndexOf(".");
			result = (indexOf > -1 ? packageName.substring(indexOf + 1) : null);
		}
		return result;
	}

	
	
	
	@Override
	public boolean checkInner(GClass clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkInner(String className) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInner() {
		boolean result = false;
		
		GClass ownerClazz = (GClass)this.getAttribute(Transforms.OWNER_INFO);
		result = (ownerClazz != null);
		if (result == false) {
			java.util.List classes = this.getUnit().getClasses();
			int length = (classes != null ? classes.size() : 0);
			if (length > 1) {
				result = true;
				for (int i = 0; i < length; i++) {
					GClass clazz = (GClass)classes.get(i);
					if (clazz != null && checkSameClass(clazz, this)) {
						result = false;
						break;
					}
				}
			}
		}

		return result;
	}
	
	protected boolean checkSameClass (GClass clazz1, GClass clazz2) {
		boolean result = false;
		String name1 = (clazz1 != null ? clazz1.getFullName() : null);
		String name2 = (clazz2 != null ? clazz2.getFullName() : null);
		result = name1 != null && name1.length() > 0 && name1.equals(name2);
		return result;
	}

	@Override
	public boolean isInner(GClass owner) {
		boolean result = false;
		
		GClass ownerClazz = (GClass)this.getAttribute(Transforms.OWNER_INFO);
		result = (ownerClazz != null && owner != null && owner.getFullName().equals(ownerClazz.getFullName()));
		if (result == false && owner != null) {
			java.util.List classes = owner.getInnerClasses();
			int length = (classes != null ? classes.size() : 0);
			for (int i = 0; i < length; i++) {
				GClass clazz = (GClass)classes.get(i);
				if (clazz != null && checkSameClass(clazz, this)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	@Override
	public String getOwnerName() {
		String result = null;
		result = (String)this.getAttribute(Transforms.OWNER_NAME);
//		result = (result != null ? result : StringUtils.uncapFirst(StringUtils.forceNotNull(getOwnerTypeName())));
		result = StringUtils.forceNotNull(result);
		if (result.length() <= 0) {
			GClass ownerType = this.getOwnerType();
			result = StringUtils.emptyNotAllow((ownerType != null ? StringUtils.uncapFirst(ownerType.getNameWithoutPackage()) : null));
		}
		return result;
	}

	@Override
	public String getOwnerTypeName() {
		String result = null;
		GClass ownerType = getOwnerType();
		result = (ownerType != null ? ownerType.getName() : null);
		return result;
	}

	@Override
	public GClass getOwnerType() {
		GClass result = null;
		result = (GClass)this.getAttribute(Transforms.OWNER_INFO);
		if (result == null) {
			java.util.List classes = this.getUnit().getClasses();
			int length = (classes != null ? classes.size() : 0);
			for (int i = 0; i < length && result == null; i++) {
				GClass clazz = (GClass)classes.get(i);
				if (clazz != null) {
					if (checkSameClass(clazz, this)) {
						break;
					}
					else {
						result = _doGetOwnerType(clazz);
					}
				}
			}
		}
		return result;
	}
	
	protected GClass _doGetOwnerType (GClass clazz) {
		GClass result = null;
		java.util.List innerClasses = clazz.getInnerClasses();
		int lengthInner = (innerClasses != null ? innerClasses.size() : 0);
		for (int j = 0; j < lengthInner && result == null; j++) {
			GClass innerClass = (GClass)innerClasses.get(j);
			if (innerClass != null) {
				if (checkSameClass(innerClass, this)) {
					result = clazz;
				}
				else {
					result = _doGetOwnerType(innerClass);
				}
			}
		}
		return result;
	}

	@Override
	public boolean containsField(String name) {
		boolean result = false;
		GField field = this.getField(name);
		result = (field != null);
		return result;
	}

	@Override
	public String getSuperClass() {
		return Factory.getSuperClass(this);
	}

	@Override
	public GClass newClass(String cName) {
		GClass result = null;
		GApp app = getUnit().getApp();
		result = app.newClass(cName);
		if (result != null) {
			app.addUnit(result.getUnit());
		}
		return result;
	}





	
	public java.util.List getInnerClasses () {
		return Factory.getInnerClasses(this);
	}

	protected GClass containerClass;
	
	protected void initiateContainerClass () {
		this.containerClass = null;
	}

	public GClass getContainerClass () {
		return this.containerClass;
	}
	
	public void setContainerClass(GClass newValue) {
		this.containerClass = newValue;
	}
	
	public boolean isInnerClass () {
		return this.containerClass != null;
	}
	
	public GClass addClass(GClass clazz) {
		Factory.addClass(this, clazz);
		return this;
	}
	
	public GClass removeClass(GClass clazz) {
		Factory.removeClass(this, clazz);
		return this;
	}


	
	
	@Override
	public boolean checkImplements(String type) {
		return Factory.checkImplements(this, type);
	}

	@Override
	public boolean checkSuperClass(String type) {
		return Factory.checkSuperClass(this, type);
	}

	public boolean equals (Object obj) {
		boolean result = false;
		result = this.node.equals(((GClassJdk8u20)obj).node);
		return result;
	}
	
	public String toString () {
		return (this.node != null ? this.node.toString() : super.toString());
	}

}
