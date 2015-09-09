package org.effortless.jast.jdk8u20;

import org.effortless.jast.Expression;
import org.effortless.jast.GAnnotation;
import org.effortless.jast.GApp;
import org.effortless.jast.jdk8u20.util.Factory;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

public class GAnnotationJdk8u20 extends GNodeJdk8u20 implements GAnnotation {

	protected GAnnotationJdk8u20 () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateParent();
		initiateNode();
	}
	
	protected String _doGetKeyAttributes () {
		return "GAnnotation:node_attributes";
	}
	
	
	
	public GAnnotationJdk8u20(GNodeJdk8u20 parent, JCAnnotation node) {
		this();
		this.parent = parent;
		this.node = node;
	}

	@Override
	public String getMemberString(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getMemberBoolean(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMemberTypeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GAnnotation addMember(String attr, Expression value) {
		Factory.addMember(this, attr, (ExpressionJdk8u20)value);
		return this;
	}

	protected GNodeJdk8u20 parent;
	
	protected void initiateParent () {
		this.parent = null;
	}
	
	public GNodeJdk8u20 getParent() {
		return this.parent;
	}
	
	public void setParent(GNodeJdk8u20 newValue) {
		this.parent = newValue;
	}

	protected JCAnnotation node;
	
	protected void initiateNode () {
		this.node = null;
	}
	
	public JCAnnotation getNode() {
		return this.node;
	}
	
	public void setNode(JCAnnotation newValue) {
		this.node = newValue;
	}
	
	public Object getNativeNode () {
		return this.node;
	}
	
	public GApp getApp () {
		return null;
	}
	


	public boolean equals (Object obj) {
		boolean result = false;
		result = this.node.equals(((GAnnotationJdk8u20)obj).node);
		return result;
	}
	
	public String toString () {
		return (this.node != null ? this.node.toString() : super.toString());
	}
	
}
