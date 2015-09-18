package org.effortless.jast.jdk8u20;

import org.effortless.jast.Parameter;
import org.effortless.jast.jdk8u20.util.Factory;

import com.sun.tools.javac.tree.JCTree;

public class GParameterJdk8u20 extends GNodeJdk8u20 implements Parameter {

	public GParameterJdk8u20 () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
	}

	public String getType() {
		return Factory.getParameterType(this);
	}

	public void setType(String newValue) {
		Factory.setParameterType(this, newValue);
	}

	public String getName() {
		return Factory.getParameterName(this);
	}

	public void setName(String newValue) {
		Factory.setParameterName(this, newValue);
	}
	
	protected String _doGetKeyAttributes () {
		return "GParameter:node_attributes";
	}
	
	protected JCTree.JCVariableDecl node;
	
	public JCTree.JCVariableDecl getNode () {
		return this.node;
	}
	
	public void setNode (JCTree.JCVariableDecl newValue) {
		this.node = newValue;
	}

	public Object getNativeNode () {
		return this.node;
	}
	
}
