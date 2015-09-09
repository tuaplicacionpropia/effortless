package org.effortless.jast.jdk8u20;

import java.util.List;

import org.effortless.jast.jdk8u20.util.Factory;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.TreeMaker;

import org.effortless.jast.*;

public class GUnitJdk8u20 extends GNodeJdk8u20 implements GUnit {

	public GUnitJdk8u20 () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public GUnitJdk8u20(JCCompilationUnit unit) {
		this();
		this.node = unit;
		initUnit();
	}

	protected void initUnit() {
	}

	protected String _doGetKeyAttributes () {
		return "GUnit:node_attributes";
	}
	
	protected JCCompilationUnit node;
	
	public JCCompilationUnit getNode () {
		return this.node;
	}

	public void setNode (JCCompilationUnit newValue) {
		this.node = newValue;
	}
	
	public Object getNativeNode () {
		return this.node;
	}
	
	protected GApp app;
	
	public GApp getApp() {
		return this.app;
	}

	public void setApp(GApp newValue) {
		this.app = newValue;
		this.tm = TreeMaker.instance(((GAppJdk8u20)this.app).ctx);
		this.jc = JavacElements.instance(((GAppJdk8u20)this.app).ctx);
	}

	
	public String getName() {
		return getMainClass().getName();
	}

	public void setName (String newValue) {
		getMainClass().setName(newValue);
	}

	public String getPackageName() {
		return Factory.getPackageName(this);
	}
	
	protected TreeMaker tm;
	
	public TreeMaker getTm () {
		return this.tm;
	}
	
	protected JavacElements jc;
	
	public JavacElements getJc () {
		return this.jc;
	}
	
	public void setPackageName (String newValue) {
		this.node.pid = Factory.generatePackage(this, newValue);
	}

	public GClass getMainClass () {
		GClass result = null;
		java.util.List<GClass> classes = getClasses();
		result = (classes != null && classes.size() > 0 ? classes.get(0) : null);
		return result;
	}
	
	public java.util.List getClasses () {
		java.util.List result = null;
		result = new java.util.ArrayList();
		java.util.List defs = this.node.defs;
		int defsSize = (defs != null ? defs.size() : 0);
		for (int i = 0; i < defsSize; i++) {
			JCTree def = (JCTree)defs.get(i);
			JCTree.JCClassDecl clazzDecl = null; try { clazzDecl = (JCTree.JCClassDecl)def; } catch (ClassCastException e) { clazzDecl = null; }
			if (clazzDecl != null) {
				GClassJdk8u20 resultClass = new GClassJdk8u20();
				resultClass.setNode(clazzDecl);
				resultClass.setUnit(this);
				result.add(resultClass);
			}
		}
		return result;
	}

	public GUnit addClass(GClass clazz) {
		Factory.addClass(this, clazz);
		return this;
	}
	
	public GUnit removeClass(GClass clazz) {
		Factory.removeClass(this, clazz);
		return this;
	}

	public GUnit addImport(String clazz) {
		Factory.addImport(this, clazz);
		return this;
	}
	
	public GUnit removeImport(String clazz) {
		Factory.removeImport(this, clazz);
		return this;
	}
	
	public boolean containsImport(String clazz) {
		return Factory.containsImport(this, clazz);
	}
	
	public boolean containsImportClassName(String clazz) {
		return Factory.containsImportClassName(this, clazz);
	}
	
	
	public boolean equals (Object obj) {
		boolean result = false;
		result = this.node.equals(((GUnitJdk8u20)obj).node);
		return result;
	}
	
	public String toString () {
		return (this.node != null ? this.node.toString() : super.toString());
	}

	public List getImports () {
		return Factory.getListImports(this);
	}
	
}
