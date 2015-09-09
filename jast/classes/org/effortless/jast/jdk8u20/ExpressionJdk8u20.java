package org.effortless.jast.jdk8u20;

import org.effortless.jast.Expression;

import com.sun.tools.javac.tree.JCTree.JCExpression;

public class ExpressionJdk8u20 extends GNodeJdk8u20 implements Expression {

	public ExpressionJdk8u20 () {
		super();
	}
	
	protected JCExpression node;
	
	public JCExpression getNode () {
		return this.node;
	}
	
	public void setNode (JCExpression newValue) {
		this.node = newValue;
	}
	
	public boolean equals (Object obj) {
		boolean result = false;
		result = this.node.equals(((ExpressionJdk8u20)obj).node);
		return result;
	}
	
	public String toString () {
		return (node != null ? node.toString() : super.toString());
	}
	
}
