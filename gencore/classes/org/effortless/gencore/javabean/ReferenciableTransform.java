package org.effortless.gencore.javabean;

import java.util.List;
import java.util.Locale;

import org.effortless.gencore.InfoModel;
import org.effortless.jast.transforms.StageTransform;
import org.effortless.orm.Referenciable;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;
//import org.effortless.gmodel.InfoModel;

/**
 *
 * Implements

	public String toLabel (java.util.Locale locale) {
		String result = null
		org.effortless.util.ToLabel toLabel = new org.effortless.util.ToLabel()
		toLabel.add(getName())
		toLabel.add(getDescription())
		result = toLabel.getText()
		return result
	}
	
 * 
 * 
 * @author jesus
 *
 */
//@StageTransform("runModel")
public class ReferenciableTransform extends Object implements Transform {

	public ReferenciableTransform () {
		super();
	}
	
	public void process (GNode node) {
		GClass cg = (GClass)node;
		if (cg != null && cg.isType(org.effortless.orm.Entity.class)) {
			List<GField> fields = InfoModel.listNotNullUnique(cg);
			if (fields == null || fields.size() <= 0) {
				fields = InfoModel.listFirstTextField(cg);
			}
			
//			if (true) {
				cg.addInterface(Referenciable.class);
				GMethod mg = null;
				
				//public String toLabel ();
				mg = cg.addMethod("toLabel").setReturnType(String.class);
				mg.declVariable(String.class, "result");//String result = null
				//org.effortless.util.ToLabel toLabel = new org.effortless.util.ToLabel()
				mg.declVariable(org.effortless.orm.util.ToLabel.class, "toLabel", mg.newObject(org.effortless.orm.util.ToLabel.class));
				for (GField field : fields) {
					String getterName = field.getGetterName();
					mg.add(mg.call(mg.var("toLabel"), "add", mg.call(getterName)));//toLabel.add(getName())
				}
				mg.add(mg.assign(mg.var("result"), mg.call(mg.var("toLabel"), "getText")));//result = toLabel.getText()
				mg.addReturn(mg.var("result"));//return result
				
				//public String toDescription ();
				mg = cg.addMethod("toDescription").setReturnType(String.class);
				mg.addReturn(mg.cteNull());
				
				//public String toImage ();
				mg = cg.addMethod("toImage").setReturnType(String.class);
				mg.addReturn(mg.cteNull());

				//public String toLabel (Locale locale);
				mg = cg.addMethod("toLabel").setReturnType(String.class).addParameter(Locale.class, "locale");
				mg.addReturn(mg.cteNull());
				
				//public String toDescription (Locale locale);
				mg = cg.addMethod("toDescription").setReturnType(String.class).addParameter(Locale.class, "locale");
				mg.addReturn(mg.cteNull());
				
				//public String toImage (Locale locale);
				mg = cg.addMethod("toImage").setReturnType(String.class).addParameter(Locale.class, "locale");
				mg.addReturn(mg.cteNull());
				
//			}
//			else {
//				BlockStatement code = new BlockStatement();
//
//				//String result = null
//				VariableExpression varResult = new VariableExpression("result", ClassHelper.STRING_TYPE);
//				DeclarationExpression declResult = new DeclarationExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.NULL);
//				code.addStatement(new ExpressionStatement(declResult));
//
//				//org.effortless.util.ToLabel toLabel = new org.effortless.util.ToLabel()
//				ClassNode clazzToLabel = new ClassNode(org.effortless.util.ToLabel.class);
//				VariableExpression varToLabel = new VariableExpression("toLabel", clazzToLabel);
//				ConstructorCallExpression newToLabel = new ConstructorCallExpression(clazzToLabel, ArgumentListExpression.EMPTY_ARGUMENTS);
//				DeclarationExpression declToLabel = new DeclarationExpression(varToLabel, Token.newSymbol(Types.ASSIGN, -1, -1), newToLabel);
//				code.addStatement(new ExpressionStatement(declToLabel));
//				
//				for (FieldNode field : fields) {
//					//toLabel.add(getName())
//					String getterName = BaseFields.getGetterName(field);
//					MethodCallExpression getProperty = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, getterName, ArgumentListExpression.EMPTY_ARGUMENTS);
//					MethodCallExpression toLabelAdd = new MethodCallExpression(varToLabel, "add", getProperty);
//					code.addStatement(new ExpressionStatement(toLabelAdd));
//				}
//
//				//result = toLabel.getText()
//				MethodCallExpression getText = new MethodCallExpression(varToLabel, "getText", ArgumentListExpression.EMPTY_ARGUMENTS);
//				BinaryExpression assignResult = new BinaryExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), getText);
//				code.addStatement(new ExpressionStatement(assignResult));
//
//				//return result
//				code.addStatement(new ReturnStatement(varResult));
//				
//				Parameter[] parameterTypes = new Parameter [] {new Parameter(new ClassNode(java.util.Locale.class), "locale")};
//				MethodNode method = new MethodNode("toLabel", Opcodes.ACC_PUBLIC, ClassHelper.STRING_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
//				clazz.addMethod(method);
//			}
		}
	}
	
	
	
}
