package org.effortless.jast.jdk8u20.util;

import java.util.ArrayList;
import java.util.List;

import org.effortless.core.ClassUtils;
import org.effortless.jast.*;
import org.effortless.jast.jdk8u20.*;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCBinary;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCIf;
import com.sun.tools.javac.tree.JCTree.JCImport;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCTypeApply;
import com.sun.tools.javac.tree.JCTree.JCTypeCast;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCUnary;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.JCTree.Tag;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.ListBuffer;
//import com.sun.tools.javac.util.Name;

public class Factory extends Object {

	public static JCTree.JCExpression generatePackage (GUnitJdk8u20 unit, String packageName) {
		JCTree.JCExpression result = null;
		result = seq(unit, packageName);
		return result;
	}
	
	public static JCTree.JCExpression seq (GNodeJdk8u20 node, String seqName) {
		JCTree.JCExpression result = null;
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		String[] array = (seqName != null ? seqName.split("\\.") : null);
		int length = (array != null ? array.length : 0);
		if (length == 1) {
//			result = tm.Select(tm.Ident(jc.getName(array[0])), (Name)null);
			result = tm.Ident(jc.getName(array[0]));
		}
		else if (length == 2) {
			result = tm.Select(tm.Ident(jc.getName(array[0])), jc.getName(array[1]));
		}
		else if (length > 2) {
			result = tm.Select(tm.Ident(jc.getName(array[0])), jc.getName(array[1]));
			for (int i = 2; i < length; i++) {
				result = tm.Select(result, jc.getName(array[i]));
			}
		}
		return result;
	}
	
	public static JCTree.JCClassDecl getMainClass (GUnitJdk8u20 unit) {
		JCTree.JCClassDecl result = null;
		JCCompilationUnit cu = unit.getNode();
		List<JCTree> defs = cu.defs;
		if (defs != null) {
			for (JCTree def : defs) {
				if (def != null && def instanceof JCTree.JCClassDecl) {
					result = (JCTree.JCClassDecl)def;
					break;
				}
			}
		}
		return result;
	}
	
	public static String getMainClassName (GUnitJdk8u20 unit) {
		String result = null;
		JCTree.JCClassDecl clazz = getMainClass(unit);
		result = clazz.name.toString();
		return result;
	}
	
	public static String getPackageName (GUnitJdk8u20 unit) {
		String result = null;
		JCCompilationUnit cu = unit.getNode();
		result = (cu.pid != null ? cu.pid.toString() : null);
		return result;
	}

	public static List<JCImport> getImports(GUnitJdk8u20 unit) {
		List<JCImport> result = null;
		result = new ArrayList<JCImport>();
		
		JCCompilationUnit cu = unit.getNode();
		
		ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
		defs.appendList(cu.defs);

		JCImport oldImport = null;
		for (JCTree item : defs) {
			try { oldImport = (JCImport)item; } catch (ClassCastException e) { oldImport = null; }
			if (oldImport != null) {
				result.add(oldImport);
			}
		}
		
		return result;
	}
	
	public static List getListImports(GUnitJdk8u20 unit) {
		List result = null;
		result = new ArrayList();
		
		JCCompilationUnit cu = unit.getNode();
		
		ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
		defs.appendList(cu.defs);

		JCImport oldImport = null;
		for (JCTree item : defs) {
			try { oldImport = (JCImport)item; } catch (ClassCastException e) { oldImport = null; }
			if (oldImport != null) {
				result.add(oldImport.qualid.toString());
			}
		}
		
		return result;
	}
	
	public static boolean containsImport(GUnitJdk8u20 unit, String clazz) {
		boolean result = false;
		if (clazz != null) {

			int idx = clazz.indexOf("<");
			clazz = (idx >= 0 ? clazz.substring(0, idx) : clazz);
			
			List<JCImport> imports = getImports(unit);
			if (imports != null) {
				for (JCImport item : imports) {
					if (item != null) {
						if (clazz.equals(item.qualid.toString())) {
							result = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static boolean containsImportClassName(GUnitJdk8u20 unit, String clazz) {
		boolean result = false;
		if (clazz != null) {

			int idx = clazz.indexOf("<");
			clazz = (idx >= 0 ? clazz.substring(0, idx) : clazz);
			idx = clazz.lastIndexOf(".");
			clazz = (idx >= 0 ? clazz.substring(idx + 1) : clazz);
			
			List<JCImport> imports = getImports(unit);
			if (imports != null) {
				for (JCImport item : imports) {
					if (item != null) {
						String itemClassName = item.qualid.toString();
						idx = itemClassName.lastIndexOf(".");
						itemClassName = (idx >= 0 ? itemClassName.substring(idx + 1) : itemClassName);
						if (clazz.equals(itemClassName)) {
							result = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static String getImportClassName(GUnitJdk8u20 unit, String clazz) {
		String result = null;
		if (clazz != null) {
			String srcClazz = clazz;
			
			int idx = clazz.indexOf("<");
			clazz = (idx >= 0 ? clazz.substring(0, idx) : clazz);
			idx = clazz.lastIndexOf(".");
			clazz = (idx >= 0 ? clazz.substring(idx + 1) : clazz);
			
			List<JCImport> imports = getImports(unit);
			if (imports != null) {
				for (JCImport item : imports) {
					if (item != null) {
						String itemClassName = item.qualid.toString();
						idx = itemClassName.lastIndexOf(".");
						itemClassName = (idx >= 0 ? itemClassName.substring(idx + 1) : itemClassName);
						if (clazz.equals(itemClassName)) {
							result = item.qualid.toString();
							break;
						}
					}
				}
			}
			if (result == null) {
				idx = clazz.lastIndexOf(".");
				clazz = (idx >= 0 ? clazz.substring(idx + 1) : clazz);
				if (checkJavaLang(clazz)) {
					result = "java.lang." + clazz;
				}
				else if (idx < 0) {
					result = unit.getPackageName() + "." + clazz;
				}
				else {
					result = srcClazz;
				}
			}
		}
		return result;
	}
	
	
	
	public static String getClassName(GClassJdk8u20 gClass) {
		String result = null;
		JCTree.JCClassDecl clazz = gClass.getNode();
		result = clazz.name.toString();
		return result;
	}

	public static void setClassName(GClassJdk8u20 gClass, String newValue) {
		JCTree.JCClassDecl clazz = gClass.getNode();
		clazz.name = ((GUnitJdk8u20)(gClass.getUnit())).getJc().getName(newValue);
	}

	public static String getName(GFieldJdk8u20 gField) {
		String result = null;
		result = gField.getNode().name.toString();
		return result;
	}

	public static void setName(GFieldJdk8u20 gField, String newValue) {
		gField.getNode().name = ((GUnitJdk8u20)(gField.getClazz().getUnit())).getJc().getName(newValue);
	}

	public static String getType(GFieldJdk8u20 gField) {
		String result = null;
		result = gField.getNode().vartype.toString();
		return result;
	}

	public static String getImportType (GFieldJdk8u20 gField) {
		String result = null;
		String type = getType(gField);
		if (type != null) {
			result = getImportClassName((GUnitJdk8u20)(gField.getClazz().getUnit()), type);
		}
		return result;
	}

	
	
	public static void setType(GFieldJdk8u20 gField, String newValue) {
		TreeMaker tm = _getTm(gField);
		JavacElements jc = _getJc(gField);
		gField.getNode().vartype = createExpressionType(tm, jc, newValue);
	}

	public static GFieldJdk8u20 addField(GClassJdk8u20 gClass, String type, String name, ExpressionJdk8u20 initialValue) {
		GFieldJdk8u20 result = null;

//		if ("_TABLE".equals(name)) 
//			System.out.println("sfsf");
		type = alterType(gClass, type);
		
		JCTree.JCClassDecl cu = gClass.getNode();
		
		TreeMaker tm = _getTm(gClass);
		JavacElements jc = _getJc(gClass);
		
		JCExpression exprType = createExpressionType(tm, jc, type);
		
		JCTree.JCVariableDecl eNewElement = tm.VarDef(tm.Modifiers(Flags.PROTECTED), jc.getName(name), exprType, (initialValue != null ? initialValue.getNode() : null));

		ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
		ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
		oldDefs.appendList(cu.defs);

if (false) {
		JCTree.JCVariableDecl oldElement = null;
		do {
			JCTree oldItem =  oldDefs.peek();
			try { oldElement = (JCTree.JCVariableDecl)oldItem; } catch (ClassCastException e) { oldElement = null; }
			if (oldElement != null) {
				newDefs.append(oldElement);
				oldDefs.remove();
			}
		} while (oldElement != null);

		newDefs.append(eNewElement);
		newDefs.appendList(oldDefs);
}
else {
		newDefs.appendList(oldDefs);
		newDefs.append(eNewElement);
}		

		cu.defs = newDefs.toList();
		
		result = new GFieldJdk8u20();
		result.setClazz(gClass);
		result.setNode(eNewElement);
		result.setInitialValue(initialValue);
		
		return result;
	}
	
	protected static JCExpression createExpressionType(TreeMaker tm, JavacElements jc, String type) {
		JCExpression result = null;
		int idx = type.indexOf("<");
		if (idx < 0) {
			result = tm.Ident(jc.getName(type));
		}
		else {
			String args = type.substring(idx + 1, type.length() - 1);
			String stype = type.substring(0, idx);
			JCExpression base = createExpressionType(tm, jc, stype);
			
			ListBuffer<JCExpression> listArgs = new ListBuffer<JCExpression>();
			String[] array = (args != null ? args.split(",") : null);
			int length = (array != null ? array.length : 0);
			for (int i = 0; i < length; i++) {
				String item = array[i];
				JCExpression exprItem = (item != null ? createExpressionType(tm, jc, item) : null);
				if (exprItem != null) {
					listArgs.append(exprItem);
				}
			}

			result = tm.TypeApply(base, listArgs.toList());
		}
		return result;
	}

	public static void removeField(GClassJdk8u20 node, String name) {
		ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
		defs.appendList(node.getNode().defs);

		ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
		
		JCTree.JCVariableDecl oldElement = null;
		for (JCTree oldItem : defs) {
			try { oldElement = (JCTree.JCVariableDecl)oldItem; } catch (ClassCastException e) { oldElement = null; }
			if (!(oldElement != null && name.equals(oldElement.getName().toString()))) {
				newDefs.append(oldItem);
			}
		}
		
		node.getNode().defs = newDefs.toList();
	}

	public static void removeMethod(GClassJdk8u20 node, GMethod method) {
		ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
		defs.appendList(node.getNode().defs);

		ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
		
		JCTree.JCMethodDecl nodeMethod = ((GMethodJdk8u20)method).getNode(); 
		
		
		Object oldElement = null;
		for (JCTree oldItem : defs) {
			if (oldItem != nodeMethod) {
				newDefs.append(oldItem);
			}
		}
		
		node.getNode().defs = newDefs.toList();
	}

	
	
	
	public static GField getField(GClassJdk8u20 gClass, String name) {
		GFieldJdk8u20 result = null;

		JCTree.JCClassDecl cu = gClass.getNode();
		
		ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
		oldDefs.appendList(cu.defs);

		JCTree.JCVariableDecl oldElement = null;
		for (JCTree oldItem : oldDefs) {
			try { oldElement = (JCTree.JCVariableDecl)oldItem; } catch (ClassCastException e) { oldElement = null; }
			if (oldElement != null && name.equals(oldElement.name.toString())) {
				break;
			}
			oldElement = null;
		}
		if (oldElement != null) {
			result = new GFieldJdk8u20();
			result.setClazz(gClass);
			result.setNode(oldElement);
		}
		
		return result;
	}

	
	

	public static GUnitJdk8u20 addUnit(GAppJdk8u20 app, String packageName, String className) {
		GUnitJdk8u20 result = null;
		result = new GUnitJdk8u20();
		result.setApp(app);
//		app.addUnit(result);
		TreeMaker tm = _getTm(result);
		JavacElements jc = _getJc(result);
		JCTree.JCClassDecl classDecl = tm.ClassDef(tm.Modifiers(Flags.PUBLIC), jc.getName(className), com.sun.tools.javac.util.List.<JCTypeParameter>nil(), null, com.sun.tools.javac.util.List.<JCExpression>nil(), com.sun.tools.javac.util.List.<JCTree>nil());
		ListBuffer<JCTree> content = new ListBuffer<JCTree>();
		content.append(classDecl);
		JCCompilationUnit node = tm.TopLevel(com.sun.tools.javac.util.List.<JCAnnotation>nil(), generatePackage(result, packageName), content.toList());
		result.setNode(node);
		return result;
	}

	public static GMethodJdk8u20 addConstructor(GClassJdk8u20 node) {
		return addMethod(node, "<init>");
	}
	
	public static GMethodJdk8u20 addMethod(GClassJdk8u20 gClass, String name) {
		GMethodJdk8u20 result = null;

		JCTree.JCClassDecl cu = gClass.getNode();
		
		TreeMaker tm = _getTm(gClass);
		JavacElements jc = _getJc(gClass);
		
		JCTree.JCBlock body = tm.Block(0L, com.sun.tools.javac.util.List.<JCStatement>nil());
		JCTree.JCMethodDecl eNewElement = tm.MethodDef(tm.Modifiers(Flags.PUBLIC), 
				jc.getName(name), 
				tm.TypeIdent(TypeTag.VOID), 
				com.sun.tools.javac.util.List.<JCTypeParameter>nil(), 
				com.sun.tools.javac.util.List.<JCVariableDecl>nil(), 
				com.sun.tools.javac.util.List.<JCExpression>nil(), 
				body, 
				null);

		if (true) {
			ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
			defs.appendList(cu.defs);
			defs.append(eNewElement);
			cu.defs = defs.toList();
		}
		else {
			ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
			ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
			oldDefs.appendList(cu.defs);

			JCTree.JCMethodDecl oldElement = null;
			do {
				JCTree oldItem =  oldDefs.peek();
				try { oldElement = (JCTree.JCMethodDecl)oldItem; } catch (ClassCastException e) { oldElement = null; }
				if (oldElement != null) {
					newDefs.append(oldElement);
					oldDefs.remove();
				}
			} while (oldElement != null);

			newDefs.append(eNewElement);
			
			newDefs.appendList(oldDefs);

			cu.defs = newDefs.toList();
		}

		result = new GMethodJdk8u20();
		result.setClazz(gClass);
		result.setNode(eNewElement);
		return result;
	}

	public static String getName(GMethodJdk8u20 gMethod) {
		String result = null;
		result = gMethod.getNode().name.toString();
		return result;
	}

	public static void setName(GMethodJdk8u20 gMethod, String newValue) {
		JavacElements jc = _getJc(gMethod);
		gMethod.getNode().name = jc.getName(newValue);
	}

	public static String alterType (GNodeJdk8u20 node, String type) {
		String result = null;
		int idxGeneric = type.indexOf("<");
		if (idxGeneric < 0) {
			result = type;
			GUnitJdk8u20 unit = _getUnit(node);
			if (true) {
				if (result != null && result.startsWith("java.lang.")) {
					result = result.substring("java.lang.".length());
				}
	
				int idx = type.lastIndexOf(".");
				String typePackageName = (idx >= 0 ? type.substring(0, idx) : null);
				if (typePackageName != null && typePackageName.equals(unit.getPackageName())) {
					result = type.substring(idx + 1);
				}
			}
			if (true) {
				int lastIdx = (result != null ? result.lastIndexOf(".") : -1);
				if (true && lastIdx >= 0) {
					if (false || !containsImportClassName(unit, result)) {
						unit.addImport(result);
					}
					if (containsImport(unit, result)) {
						if (true) {
							result = result.substring(lastIdx + 1);
						}
						System.out.println(">>>>>>>>>>>>>>>>>>>>> type = " + type +  " result = " + result);
					}
				}
			}
		}
		else {
			String base = type.substring(0, idxGeneric);
			String extra = type.substring(idxGeneric + 1, type.length() - 1);
			String[] paramsExtra = extra.split(",");
			result = alterType(node, base);
			int paramsLength = (paramsExtra != null ? paramsExtra.length : 0);
			String params = "";
			for (int i = 0; i < paramsLength; i++) {
				String extraItem = paramsExtra[i];
				extraItem = alterType(node, extraItem);
				params += (params.length() > 0 && extraItem.length() > 0 ? ", " : "") + extraItem;
			}
			result = result + "<" + params + ">";
		}
		return result;
	}
	
	public static void addParameter(GMethodJdk8u20 gMethod, String type, String name) {
		TreeMaker tm = _getTm(gMethod);
		JavacElements jc = _getJc(gMethod);
		
		type = alterType(gMethod, type);
		
		JCExpression exprType = createExpressionType(tm, jc, type);
		
		JCTree.JCVariableDecl parameter = tm.VarDef(tm.Modifiers(Flags.PARAMETER), jc.getName(name), exprType, null);
		
		JCTree.JCMethodDecl node = gMethod.getNode();
		
		ListBuffer<JCTree.JCVariableDecl> newDefs = new ListBuffer<JCTree.JCVariableDecl>();
		newDefs.appendList(node.params);
		newDefs.append(parameter);
		
		node.params = newDefs.toList();
	}

	public static String getReturnType(GMethodJdk8u20 gMethod) {
		String result = null;
		result = gMethod.getNode().restype.toString();
		return result;
	}

	public static void setReturnType(GMethodJdk8u20 gMethod, String returnType) {
		returnType = alterType(gMethod, returnType);
		gMethod.getNode().restype = seq(gMethod, returnType);//tm.Ident(jc.getName(returnType));//?????????????????????
	}

	public static void setReturnType(GMethodJdk8u20 gMethod, ExpressionJdk8u20 returnType) {
		gMethod.getNode().restype = (returnType != null ? returnType.getNode() : null);
	}

	public static JCTree.JCBlock loadBlock (GCodeJdk8u20 node) {
		JCTree.JCBlock result = null;

		JCTree.JCStatement code = node.getBlockCode();
		try { result = (JCTree.JCBlock)code; } catch (ClassCastException e) {}

		if (result == null) {
			TreeMaker tm = _getTm(node);
			
			ListBuffer<JCTree.JCStatement> list = new ListBuffer<JCTree.JCStatement>();
			list.append(code);

			result = tm.Block(0L, list.toList());
			node.setBlockCode(result);
		}
		
		return result;
	}
	
	public static void addDeclVariable(GCodeJdk8u20 node, String type, String name) {
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		
		type = alterType(node, type);
		
		JCExpression exprType = createExpressionType(tm, jc, type);
		
		JCTree.JCVariableDecl eNewElement = tm.VarDef(tm.Modifiers(0), jc.getName(name), exprType, tm.Literal(TypeTag.BOT, null));
		
		JCTree.JCBlock body = loadBlock(node);
		ListBuffer<JCTree.JCStatement> list = new ListBuffer<JCTree.JCStatement>();
		list.appendList(body.stats);
		list.append(eNewElement);
		body.stats = list.toList();
	}

	public static void addReturn(GCodeJdk8u20 node, ExpressionJdk8u20 expr) {
		TreeMaker tm = _getTm(node);
		
		JCTree.JCReturn eNewElement = tm.Return(expr.getNode());
		
		JCTree.JCBlock body = loadBlock(node);
		ListBuffer<JCTree.JCStatement> list = new ListBuffer<JCTree.JCStatement>();
		list.appendList(body.stats);
		list.append(eNewElement);
		body.stats = list.toList();
	}

	public static void addClass(GUnitJdk8u20 unit, GClass clazz) {
		ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
		defs.appendList(unit.getNode().defs);

		JCTree.JCClassDecl classDef = ((GClassJdk8u20)clazz).getNode();
		defs.append(classDef);

		unit.getNode().defs = defs.toList();
	}

	public static void removeClass(GUnitJdk8u20 unit, GClass clazz) {
		ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
		ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
		oldDefs.appendList(unit.getNode().defs);

		JCTree.JCClassDecl classDef = ((GClassJdk8u20)clazz).getNode();
		for (JCTree oldItem : oldDefs) {
			if (oldItem != classDef) {
				newDefs.append(oldItem);
			}
		}

		unit.getNode().defs = newDefs.toList();
	}

	public static void addClass(GClassJdk8u20 parent, GClass clazz) {
		ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
		defs.appendList(parent.getNode().defs);

		JCTree.JCClassDecl classDef = ((GClassJdk8u20)clazz).getNode();
		defs.append(classDef);
		
		java.util.List imports = clazz.getUnit().getImports();
		clazz.getUnit().getApp().removeUnit(clazz.getUnit());
		clazz.setUnit(parent.getUnit());
		
		int length = (imports != null ? imports.size() : 0);
		GUnit parentUnit = parent.getUnit();
		for (int i = 0; i < length; i++) {
			String eImport = (String)imports.get(i);
			if (eImport != null) {
				parentUnit.addImport(eImport);
			}
		}
		
		String importToRemove = clazz.getFullName();
		parentUnit.removeImport(importToRemove);

		parent.getNode().defs = defs.toList();
	}

	public static void removeClass(GClassJdk8u20 parent, GClass clazz) {
		ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
		ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
		oldDefs.appendList(parent.getNode().defs);

		JCTree.JCClassDecl classDef = ((GClassJdk8u20)clazz).getNode();
		for (JCTree oldItem : oldDefs) {
			if (oldItem != classDef) {
				newDefs.append(oldItem);
			}
		}

		parent.getNode().defs = newDefs.toList();
	}

	public static GAnnotationJdk8u20 addAnnotation(GNodeJdk8u20 gNode, String annotation) {
		GAnnotationJdk8u20 result = null;
		
		annotation = alterType(gNode, annotation);
		
		JCTree.JCModifiers mods = _getMods(gNode);
		
		TreeMaker tm = _getTm(gNode);
		JavacElements jc = _getJc(gNode);
		
		JCExpression exprType = createExpressionType(tm, jc, annotation);

		
//		JCTree.JCAnnotation nodeAnn = tm.Annotation(null);
//		nodeAnn.annotationType = tm.Ident(jc.getName(annotation));
		JCTree.JCAnnotation nodeAnn = tm.Annotation(exprType, (new ListBuffer<JCTree.JCExpression>()).toList());
		com.sun.tools.javac.util.List<JCTree.JCAnnotation> annotations = mods.annotations;

		ListBuffer<JCTree.JCAnnotation> newList = new ListBuffer<JCTree.JCAnnotation>();
		
		newList.appendList(annotations);
		newList.append(nodeAnn);
		
		mods.annotations = newList.toList();

		result = new GAnnotationJdk8u20(gNode, nodeAnn);
		
		return result;
	}

	protected static JCTree.JCModifiers _getMods (GNodeJdk8u20 node) {
		JCTree.JCModifiers result = null;
		try { result = (result == null ? ((GClassJdk8u20)node).getNode().mods : result); } catch (ClassCastException e) {}
		try { result = (result == null ? ((GMethodJdk8u20)node).getNode().mods : result); } catch (ClassCastException e) {}
		try { result = (result == null ? ((GFieldJdk8u20)node).getNode().mods : result); } catch (ClassCastException e) {}
		return result;
	}

	protected static TreeMaker _getTm (GNodeJdk8u20 node) {
		TreeMaker result = null;
		result = _getUnit(node).getTm();
		return result;
	}
	
	protected static JavacElements _getJc (GNodeJdk8u20 node) {
		JavacElements result = null;
		result = _getUnit(node).getJc();
		return result;
	}
	
	protected static GUnitJdk8u20 _getUnit (GNodeJdk8u20 node) {
		GUnitJdk8u20 result = null;
//		try {
//			((GAnnotationJdk8u20)node)
//		}
		try { result = (result == null ? (GUnitJdk8u20)(((GClassJdk8u20)node).getUnit()) : result); } catch (ClassCastException e) {}
		try { result = (result == null ? (GUnitJdk8u20)(((GMethodJdk8u20)node).getClazz().getUnit()) : result); } catch (ClassCastException e) {}
		try { result = (result == null ? (GUnitJdk8u20)(((GFieldJdk8u20)node).getClazz().getUnit()) : result); } catch (ClassCastException e) {}
		try { result = (result == null ? (GUnitJdk8u20)(((GCodeJdk8u20)node).getClazz().getUnit()) : result); } catch (ClassCastException e) {}
		try { result = (result == null ? ((GUnitJdk8u20)node) : result); } catch (ClassCastException e) {}
		try { result = (result == null ? _getUnit(((GAnnotationJdk8u20)node).getParent()) : result); } catch (ClassCastException e) {}
		return result;
	}

	public static void addMember(GAnnotationJdk8u20 node, String attr, ExpressionJdk8u20 value) {
		JCAnnotation _node = node.getNode();
		
		com.sun.tools.javac.util.List<JCTree.JCExpression> args = _node.args;
		ListBuffer<JCTree.JCExpression> newList = new ListBuffer<JCTree.JCExpression>();
		newList.appendList(args);
		
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		
		JCTree.JCExpression expr = value.getNode();
		
		JCTree.JCExpression member = tm.Assign(tm.Ident(jc.getName(attr)), expr);
		newList.append(member);
		
		_node.args = newList.toList();
	}

	public static ExpressionJdk8u20 cteNull(GNodeJdk8u20 node) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.BOT, null);
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static ExpressionJdk8u20 cteObject(GNodeJdk8u20 node, Object value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.CLASS, value);
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cte_boolean(GNodeJdk8u20 node, boolean value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.BOOLEAN, Integer.valueOf((value ? 1 : 0)));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cte_int(GNodeJdk8u20 node, int value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.INT, Integer.valueOf(value));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cte_short(GNodeJdk8u20 node, short value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.SHORT, Short.valueOf(value));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cte_long(GNodeJdk8u20 node, long value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.LONG, Long.valueOf(value));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cte_double(GNodeJdk8u20 node, double value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.DOUBLE, Double.valueOf(value));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cte_byte(GNodeJdk8u20 node, byte value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.BYTE, Byte.valueOf(value));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cte_char(GNodeJdk8u20 node, char value) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.CHAR, Integer.valueOf(value));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cteSuper(GNodeJdk8u20 node) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);

		JCExpression expr = tm.Ident(jc.getName("super"));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cteThis(GNodeJdk8u20 node) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);

		JCExpression expr = tm.Ident(jc.getName("this"));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cteTrue(GNodeJdk8u20 node) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.BOOLEAN, Integer.valueOf(1));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cteFalse(GNodeJdk8u20 node) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);

		JCExpression expr = tm.Literal(TypeTag.BOOLEAN, Integer.valueOf(0));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cteTRUE(GNodeJdk8u20 node) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		
		JCExpression left = tm.Ident(jc.getName("Boolean"));

		JCExpression expr = tm.Select(left, jc.getName("TRUE"));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cteFALSE(GNodeJdk8u20 node) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		
		JCExpression left = tm.Ident(jc.getName("Boolean"));

		JCExpression expr = tm.Select(left, jc.getName("FALSE"));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static Expression cteClass(GNodeJdk8u20 node, String type) {
		ExpressionJdk8u20 result = null;

		type = alterType(node, type);
		
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		
		JCExpression left = tm.Ident(jc.getName(type));

//		JCExpression expr = left;//tm.Select(left, jc.getName("class"));
		JCExpression expr = tm.Select(left, jc.getName("class"));
		
		result = new ExpressionJdk8u20();
		result.setNode(expr);

		return result;
	}

	public static GMethod getMethod(GClassJdk8u20 node, String name) {
		GMethodJdk8u20 result = null;
		
		JCTree.JCClassDecl cu = node.getNode();
		
		ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
		oldDefs.appendList(cu.defs);

		JCTree.JCMethodDecl oldElement = null;
		for (JCTree oldItem : oldDefs) {
			try { oldElement = (JCTree.JCMethodDecl)oldItem; } catch (ClassCastException e) { oldElement = null; }
			if (oldElement != null && name.equals(oldElement.name.toString())) {
				break;
			}
			oldElement = null;
		}
		if (oldElement != null) {
			result = new GMethodJdk8u20();
			result.setClazz(node);
			result.setNode(oldElement);
		}
		
		return result;
	}

	public static void declVariable(GCodeJdk8u20 node, long flags, String type, String name, ExpressionJdk8u20 defaultValue) {
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		
		type = alterType(node, type);

		JCExpression exprType = createExpressionType(tm, jc, type);
		
		JCTree.JCExpression initValue = (defaultValue != null ? defaultValue.getNode() : null);//cteNull(node).getNode());
		JCTree.JCVariableDecl varDecl = tm.VarDef(tm.Modifiers(flags), jc.getName(name), exprType, initValue);
		
		addStatement(node, varDecl);
//		@Override
//		public Statement newBlock() {
//			return Factory.newBlock(this);
//		}
	//
//		@Override
//		public Statement addIf(Expression condition) {
//			return Factory.addIf(this, (ExpressionJdk8u20)condition);
//		}
	//
		
		

	}

	public static GCodeJdk8u20 newBlock(GCodeJdk8u20 node) {
		GCodeJdk8u20 result = null;

		TreeMaker tm = _getTm(node);
		
		ListBuffer<JCStatement> stats = new ListBuffer<JCStatement>();
		
		result = new GCodeJdk8u20();
		result.setBlockCode(tm.Block(0L, stats.toList()));
		GClass clazz = null;
		try { clazz = (GClass)node; } catch (ClassCastException e) {}
		clazz = (clazz != null ? clazz : node.getClazz());
		result.setClazz(clazz);
		
//		node.getBlockCode()
		
		
		return result;
	}

	public static void addIf(GCodeJdk8u20 node, ExpressionJdk8u20 expr, GCodeJdk8u20 thenCode, GCodeJdk8u20 elseCode) {
		TreeMaker tm = _getTm(node);
//		JCTree.JCBlock thenCode = tm.Block(0L, com.sun.tools.javac.util.List.<JCStatement>nil());
		JCIf tIf = tm.If(tm.Parens(expr.getNode()), thenCode.getBlockCode(), (elseCode != null ? elseCode.getBlockCode() : null));
		
		addStatement(node, tIf);
	}

	public static void gPrintln(GCodeJdk8u20 node, String msg) {
		gPrintln(node, cteObject(node, msg));
	}
	
	public static void gPrintln(GCodeJdk8u20 node, ExpressionJdk8u20 msg) {
		TreeMaker tm = _getTm(node);

		JCTree.JCExpression arg = msg.getNode();
		JCTree.JCExpression left = seq(node, "System.out.println");
		
		ListBuffer<JCTree.JCExpression> args = new ListBuffer<JCTree.JCExpression>();
		args.append(arg);
		
		JCTree.JCMethodInvocation result = tm.App(left, args.toList());
		
		addStatement(node, result);
	}
	
	public static void addStatement (GCodeJdk8u20 node, JCTree.JCExpression expr) {
		TreeMaker tm = _getTm(node);
		JCTree.JCExpressionStatement exprResult = tm.Exec(expr);
		addStatement(node, exprResult);
	}

	public static void addStatement (GCodeJdk8u20 node, JCTree.JCStatement stm) {
		JCTree.JCBlock block = loadBlock(node);
		ListBuffer<JCTree.JCStatement> stms = new ListBuffer<JCTree.JCStatement>();
		stms.appendList(block.stats);
		stms.append(stm);
		
		block.stats = stms.toList();
	}

	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, String method) {
		return call(node, (ExpressionJdk8u20)null, method, (Expression[])null);
	}
	
	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, String method, Expression... arguments) {
		return call(node, (ExpressionJdk8u20)null, method, arguments);
	}

	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, String obj, String method) {
		return call(node, toIdentExpression(node, obj), method, (Expression[])null);
	}
	
	public static ExpressionJdk8u20 toExpression (JCExpression expr) {
		ExpressionJdk8u20 result = null;
		result = new ExpressionJdk8u20();
		result.setNode(expr);
		return result;
	}

	public static ExpressionJdk8u20 toIdentExpression (GNodeJdk8u20 node, String expr) {
		ExpressionJdk8u20 result = null;
		result = new ExpressionJdk8u20();

		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		JCIdent nodeIdent = tm.Ident(jc.getName(expr));
		result.setNode(nodeIdent);
		return result;
	}
	
	public static ExpressionJdk8u20[] toIdentExpressionList (GNodeJdk8u20 node, String... expr) {
		List<ExpressionJdk8u20> result = null;
		result = new ArrayList<ExpressionJdk8u20>();
		
		if (expr != null) {
			for (String itemExpr : expr) {
				ExpressionJdk8u20 itemAdd = toIdentExpression(node, itemExpr);
				result.add(itemAdd);
			}
		}

		return result.toArray(new ExpressionJdk8u20[0]);
	}
	
	public static ExpressionJdk8u20[] toExpressionList (GNodeJdk8u20 node, Expression... expr) {
		List<ExpressionJdk8u20> result = null;
		result = new ArrayList<ExpressionJdk8u20>();
		
		if (expr != null) {
			for (Expression itemExpr : expr) {
				ExpressionJdk8u20 itemAdd = (ExpressionJdk8u20)itemExpr;
				result.add(itemAdd);
			}
		}

		return result.toArray(new ExpressionJdk8u20[0]);
	}
	
	public static ExpressionJdk8u20[] toValueExpressionList (GNodeJdk8u20 node, String... values) {
		List<ExpressionJdk8u20> result = null;
		result = new ArrayList<ExpressionJdk8u20>();
		
		if (values != null) {
			for (String value : values) {
				ExpressionJdk8u20 itemAdd = cteObject(node, value);
				result.add(itemAdd);
			}
		}

		return result.toArray(new ExpressionJdk8u20[0]);
	}
	
	
	
	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, String obj, String method, String... variables) {
		return call(node, toIdentExpression(node, obj), method, toIdentExpressionList(node, variables));
	}

	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, String obj, String method, Expression... arguments) {
		return call(node, toIdentExpression(node, obj), method, arguments);
	}

	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, ExpressionJdk8u20 obj, String method, String... variables) {
		return call(node, obj, method, toIdentExpressionList(node, variables));
	}

	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, ExpressionJdk8u20 obj, String method) {
		return call(node, obj, method, (Expression[])null);
	}

	public static ExpressionJdk8u20 call(GNodeJdk8u20 node, ExpressionJdk8u20 obj, String method, Expression... arguments) {
		ExpressionJdk8u20 result = null;
		
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		JCExpression methodExpr = (obj != null ? tm.Select(obj.getNode(), jc.getName(method)) : tm.Ident(jc.getName(method)));
		
		ListBuffer<JCExpression> listArgs = new ListBuffer<JCExpression>();
		if (arguments != null) {
			for (Expression _arg : arguments) {
				ExpressionJdk8u20 arg = (ExpressionJdk8u20)_arg;
				if (arg != null && arg.getNode() != null) {
					listArgs.add(arg.getNode());
				}
			}
		}
		
		JCMethodInvocation targetNode = tm.Apply(com.sun.tools.javac.util.List.<JCExpression>nil(), methodExpr, listArgs.toList());
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);

		return result;
	}

	public static ExpressionJdk8u20 callStatic(GNodeJdk8u20 node, String type, String method, Expression[] arguments) {
		ExpressionJdk8u20 result = null;

		type = alterType(node, type);

		TreeMaker tm = _getTm(node);
		
		JCExpression methodExpr = seq(node, type + "." + method);
		
		ListBuffer<JCExpression> listArgs = new ListBuffer<JCExpression>();
		if (arguments != null) {
			for (Expression _arg : arguments) {
				ExpressionJdk8u20 arg = (ExpressionJdk8u20)_arg;
				if (arg != null && arg.getNode() != null) {
					listArgs.add(arg.getNode());
				}
			}
		}
		
		JCMethodInvocation targetNode = tm.Apply(com.sun.tools.javac.util.List.<JCExpression>nil(), methodExpr, listArgs.toList());
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);

		return result;
	}

	public static GAnnotationJdk8u20 createAnnotation(GNodeJdk8u20 node, String annotation) {
		GAnnotationJdk8u20 result = null;
		
		annotation = alterType(node, annotation);
		
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		JCExpression exprType = createExpressionType(tm, jc, annotation);
		
		JCTree.JCAnnotation nodeAnn = tm.Annotation(exprType, (new ListBuffer<JCTree.JCExpression>()).toList());

		result = new GAnnotationJdk8u20(node, nodeAnn);
		
		return result;
	}

	public static void addAnnotation(GNodeJdk8u20 node, GAnnotationJdk8u20 annotation) {
		JCTree.JCModifiers mods = _getMods(node);
		
		JCTree.JCAnnotation nodeAnn = annotation.getNode();
		com.sun.tools.javac.util.List<JCTree.JCAnnotation> annotations = mods.annotations;

		ListBuffer<JCTree.JCAnnotation> newList = new ListBuffer<JCTree.JCAnnotation>();
		
		newList.appendList(annotations);
		newList.append(nodeAnn);
		
		mods.annotations = newList.toList();
	}

	public static void removeAnnotation(GNodeJdk8u20 node, GAnnotationJdk8u20 annotation) {
		JCTree.JCModifiers mods = _getMods(node);
		
		JCTree.JCAnnotation nodeAnn = annotation.getNode();
		com.sun.tools.javac.util.List<JCTree.JCAnnotation> annotations = mods.annotations;

		ListBuffer<JCTree.JCAnnotation> newList = new ListBuffer<JCTree.JCAnnotation>();
		
		newList.appendList(annotations);
		newList.remove(nodeAnn);
		
		mods.annotations = newList.toList();
	}

	public static ExpressionJdk8u20 and(GNodeJdk8u20 node, Expression[] expr) {
		ExpressionJdk8u20 result = null;

		if (expr != null && expr.length > 1) {
			TreeMaker tm = _getTm(node);
			JCBinary targetNode = tm.Binary(Tag.AND, ((ExpressionJdk8u20)(expr[0])).getNode(), ((ExpressionJdk8u20)(expr[1])).getNode());
			for (int i = 2; i < expr.length; i++) {
				targetNode = tm.Binary(Tag.AND, targetNode, ((ExpressionJdk8u20)(expr[i])).getNode());
			}
			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException("and expression length must > 1");
		}
		
		return result;
	}

	public static ExpressionJdk8u20 or(GNodeJdk8u20 node, Expression[] expr) {
		ExpressionJdk8u20 result = null;

		if (expr != null && expr.length > 1) {
			TreeMaker tm = _getTm(node);
			JCBinary targetNode = tm.Binary(Tag.OR, ((ExpressionJdk8u20)(expr[0])).getNode(), ((ExpressionJdk8u20)(expr[1])).getNode());
			for (int i = 2; i < expr.length; i++) {
				targetNode = tm.Binary(Tag.OR, targetNode, ((ExpressionJdk8u20)(expr[i])).getNode());
			}
			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException("or expression length must > 1");
		}
		
		return result;
	}

	public static ExpressionJdk8u20 not(GNodeJdk8u20 node, ExpressionJdk8u20 expr) {
		ExpressionJdk8u20 result = null;

		TreeMaker tm = _getTm(node);
		JCUnary targetNode = tm.Unary(Tag.NOT, expr.getNode());
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);
		return result;
	}

	public static ExpressionJdk8u20 _binary(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right, Tag opTag, String opName) {
		ExpressionJdk8u20 result = null;

		if (left != null && right != null) {
			TreeMaker tm = _getTm(node);
			JCBinary targetNode = tm.Binary(opTag, left.getNode(), right.getNode());
			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException(opName + " expression length must == 2");
		}
		
		return result;
	}

	public static ExpressionJdk8u20 eq(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right) {
		return _binary(node, left, right, Tag.EQ, "eq");
	}

	public static ExpressionJdk8u20 ne(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right) {
		return _binary(node, left, right, Tag.NE, "ne");
	}

	public static ExpressionJdk8u20 gt(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right) {
		return _binary(node, left, right, Tag.GT, "gt");
	}

	public static ExpressionJdk8u20 lt(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right) {
		return _binary(node, left, right, Tag.LT, "lt");
	}

	public static ExpressionJdk8u20 ge(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right) {
		return _binary(node, left, right, Tag.GE, "ge");
	}

	public static ExpressionJdk8u20 le(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right) {
		return _binary(node, left, right, Tag.LE, "le");
	}

	public static ExpressionJdk8u20 cast(GNodeJdk8u20 node, String type, ExpressionJdk8u20 expr) {
		ExpressionJdk8u20 result = null;

		if (expr != null) {
			type = alterType(node, type);

			TreeMaker tm = _getTm(node);
			JavacElements jc = _getJc(node);

			JCExpression clazzType = createExpressionType(tm, jc, type);
			
			JCTypeCast targetNode = tm.TypeCast(clazzType, expr.getNode());
			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException("expr must not null");
		}
		
		return result;
	}

	public static ExpressionJdk8u20 clazz(GNodeJdk8u20 node, String type) {
		ExpressionJdk8u20 result = null;

		if (type != null) {
			type = alterType(node, type);

			JCTree.JCExpression targetNode = seq(node, type/* + ".class"*/);

			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException("type must not null");
		}
		
		return result;
	}

	public static ExpressionJdk8u20 triple(GNodeJdk8u20 node, ExpressionJdk8u20 condition, ExpressionJdk8u20 exprTrue, ExpressionJdk8u20 exprFalse) {
		ExpressionJdk8u20 result = null;

		if (condition != null && exprTrue != null && exprFalse != null) {
			TreeMaker tm = _getTm(node);

			JCExpression triExpr = tm.Conditional(condition.getNode(), exprTrue.getNode(), exprFalse.getNode());
			JCExpression targetNode = tm.Parens(triExpr);

			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException("condition, exprTrue and exprFalse must not null");
		}
		
		return result;
	}

	public static ExpressionJdk8u20 assign(GNodeJdk8u20 node, ExpressionJdk8u20 left, ExpressionJdk8u20 right) {
		ExpressionJdk8u20 result = null;

		if (left != null && right != null) {
			TreeMaker tm = _getTm(node);

			JCExpression targetNode = tm.Assign(left.getNode(), right.getNode());

			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException("left and right must not null");
		}
		
		return result;
	}

	public static ExpressionJdk8u20 assignOp(GNodeJdk8u20 node, ExpressionJdk8u20 left, String op, ExpressionJdk8u20 right) {
		ExpressionJdk8u20 result = null;

		if (left != null && right != null) {
			TreeMaker tm = _getTm(node);
//			left.getNode().
			Tag tag = null;
			tag = (tag == null && "+=".equals(op) ? Tag.PLUS_ASG : tag);
			tag = (tag == null && "-=".equals(op) ? Tag.MINUS_ASG : tag);
			tag = (tag == null && "*=".equals(op) ? Tag.MUL_ASG : tag);
			tag = (tag == null && "/=".equals(op) ? Tag.DIV_ASG : tag);
			tag = (tag == null && "&=".equals(op) ? Tag.BITAND_ASG : tag);
			tag = (tag == null && "|=".equals(op) ? Tag.BITOR_ASG : tag);

			JCExpression targetNode = tm.Assignop(tag, left.getNode(), right.getNode());

			result = new ExpressionJdk8u20();
			result.setNode(targetNode);
		}
		else {
			throw new RuntimeException("left and right must not null");
		}
		
		return result;
	}

	
	
	public static ExpressionJdk8u20 callSuper(GMethodJdk8u20 node, Expression[] arguments) {
		return call(node, (ExpressionJdk8u20)null, "super", arguments);
	}

	public static ExpressionJdk8u20 newObject(GCodeJdk8u20 node, String type, Expression[] arguments) {
		ExpressionJdk8u20 result = null;
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
		
		type = alterType(node, type);
		
		JCExpression exprType = createExpressionType(tm, jc, type);
		
		ListBuffer<JCExpression> args = new ListBuffer<JCExpression>();
		if (arguments != null) {
			for (Expression _arg : arguments) {
				ExpressionJdk8u20 arg = (ExpressionJdk8u20)_arg;
				if (arg != null) {
					args.add(arg.getNode());
				}
			}
		}
		
		JCNewClass targetNode = tm.NewClass(null, com.sun.tools.javac.util.List.<JCExpression>nil(), exprType, args.toList(), null);
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);
		return result;
	}

	public static void add(GCodeJdk8u20 node, ExpressionJdk8u20 expr) {
		addStatement(node, expr.getNode());
	}

	public static GCodeJdk8u20 addStaticBlock(GClassJdk8u20 node) {
		GCodeJdk8u20 result = null;
		result = newBlock(node);
		JCBlock block = ((JCBlock)result.getBlockCode());
		block.flags = Flags.STATIC;
		
		ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
		defs.appendList(node.getNode().defs);
		defs.add(block);
		node.getNode().defs = defs.toList();
		
		return result;
	}

	public static GCodeJdk8u20 addBlock(GMethodJdk8u20 node) {
		GCodeJdk8u20 result = null;
		result = newBlock(node);
		Factory.addBlock(node, result);
		return result;
	}

	public static void setSuperClass(GClassJdk8u20 node, String superClass) {
		superClass = alterType(node, superClass);
		node.getNode().extending = (superClass != null ? seq(node, superClass) : null);
	}

	public static void setSuperClass(GClassJdk8u20 node, ExpressionJdk8u20 superClass) {
		node.getNode().extending = (superClass != null ? superClass.getNode() : null);
	}

	public static void addInterface(GClassJdk8u20 node, String iface) {
		if (iface != null) {
			iface = alterType(node, iface);
			ListBuffer<JCExpression> list = new ListBuffer<JCExpression>();
			
			list.appendList(node.getNode().implementing);
			list.append(seq(node, iface));
			
			node.getNode().implementing = list.toList();
		}
	}

	public static void addInterface(GClassJdk8u20 node, ExpressionJdk8u20 iface) {
		if (iface != null) {
			ListBuffer<JCExpression> list = new ListBuffer<JCExpression>();
			
			list.appendList(node.getNode().implementing);
			list.append(iface.getNode());
			
			node.getNode().implementing = list.toList();
		}
	}

	public static void addCte(GClassJdk8u20 node, String type, String name, Object value) {
		type = alterType(node, type);
		addField(node, type, name, Factory.cteObject(node, value)).setPublic(true).setStatic(true).setFinal(true);
	}

	public static java.util.List<GClass> getInnerClasses(GClassJdk8u20 node) {
		java.util.List<GClass> result = null;
		result = new java.util.ArrayList<GClass>();
		java.util.List<JCTree> defs = node.getNode().defs;
		if (defs != null) {
			for (JCTree def : defs) {
				JCTree.JCClassDecl clazzDecl = null; try { clazzDecl = (JCTree.JCClassDecl)def; } catch (ClassCastException e) { clazzDecl = null; }
				
				if (clazzDecl != null) {
					GClassJdk8u20 resultClass = new GClassJdk8u20();
					resultClass.setNode(clazzDecl);
					resultClass.setContainerClass(node);
					resultClass.setUnit(node.getUnit());
					result.add(resultClass);
				}
			}
		}
		return result;
	}

	public static String getSuperClass(GClassJdk8u20 node) {
		String result = null;
		if (node != null && node.getNode() != null && node.getNode().extending != null) {
			result = node.getNode().extending.toString();
		}
		return result;
	}

	public static java.util.List<GMethod> getAllDeclaredMethods(GClassJdk8u20 node) {
		java.util.List<GMethod> result = null;
		result = new java.util.ArrayList<GMethod>();
		java.util.List<JCTree> defs = node.getNode().defs;
		if (defs != null) {
			for (JCTree def : defs) {
				JCTree.JCMethodDecl decl = null; try { decl = (JCTree.JCMethodDecl)def; } catch (ClassCastException e) { decl = null; }
				if (decl != null) {
					GMethodJdk8u20 resultDecl = new GMethodJdk8u20();
					resultDecl.setNode(decl);
					resultDecl.setClazz(node);
					result.add(resultDecl);
				}
			}
		}
		return result;
	}

	public static void setInitialValue(GFieldJdk8u20 node, ExpressionJdk8u20 initialValue) {
		if (node != null) {
			node.getNode().init = (initialValue != null ? initialValue.getNode() : null);
		}
	}

	public static ExpressionJdk8u20 getInitialValue(GFieldJdk8u20 node) {
		ExpressionJdk8u20 result = null;
		JCTree.JCVariableDecl inode = (node != null ? node.getNode() : null);
		JCExpression expr = (inode != null ? inode.init : null);
		if (expr != null) {
			result = new ExpressionJdk8u20();
			result.setNode(expr);
		}
		return result;
	}

	public static ExpressionJdk8u20 parameterizeType(GNodeJdk8u20 node, String type, String[] params) {
		ExpressionJdk8u20 result = null;
		TreeMaker tm = _getTm(node);
		
		type = alterType(node, type);
		
		ListBuffer<JCExpression> args = new ListBuffer<JCExpression>();
		if (params != null) {
			for (String arg : params) {
				if (arg != null) {
					args.add(seq(node, arg));
				}
			}
		}
		
		JCTypeApply targetNode = tm.TypeApply(seq(node, type), args.toList());
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);
		return result;
	}

	public static Expression field(GNodeJdk8u20 node, String fieldName) {
		ExpressionJdk8u20 result = null;
		
		JCExpression targetNode = seq(node, "this." + fieldName);
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);
		return result;
	}

	public static boolean checkImplements(GClassJdk8u20 node, String type) {
		boolean result = false;
		if (type != null) {
//			type = alterType(node, type);

			ListBuffer<JCExpression> implementing = new ListBuffer<JCExpression>();
			implementing.appendList(node.getNode().implementing);
			for (JCExpression expr : implementing) {
				String ownerType = expr.toString();
				result = Factory.checkOwnerType(node, ownerType, type);
				if (result) {
					break;
				}
			}
		}
		return result;
	}

	public static boolean checkSuperClass(GClassJdk8u20 node, String type) {
		boolean result = false;
		if (type != null) {
//			type = alterType(node, type);

			String superClassFullName = Factory.getSuperClassFullName(node);
			result = Factory.checkOwnerType(node, superClassFullName, type);
		}
		return result;
	}

	public static boolean checkOwnerType(GNodeJdk8u20 node, String ownerType, String type) {
		boolean result = false;
		if (type != null) {
			ownerType = completeType(node, ownerType);
			type = completeType(node, type);
			
			
			result = type.equals(ownerType);
			if (result == false && !"java.lang.Object".equals(ownerType)) {
				Class<?> superClazz = ClassUtils.tryLoadClass(ownerType);
				if (superClazz != null) {
					Class<?> typeClazz = ClassUtils.tryLoadClass(type);
					
					result = (typeClazz != null && superClazz != null && typeClazz.isAssignableFrom(superClazz));
				}
				else {
					GUnitJdk8u20 unit = _getUnit(node);
					String fullClassName = Factory.getImportClassName(unit, ownerType);
					GClass gClass = unit.getApp().getClassByName(fullClassName);
					result = (gClass != null ? gClass.isType(type) : false);
				}
			}
		}
		return result;
	}

	protected static String completeType(GNodeJdk8u20 node, String type) {
		String result = null;
		
		result = type;
		if (node != null && type != null) {
			int idx = result.indexOf("<");
			result = (idx >= 0 ? result.substring(0, idx) : result);
			
			GUnitJdk8u20 unit = _getUnit(node);
			JCCompilationUnit cu = unit.getNode();
			
			ListBuffer<JCTree> defs = new ListBuffer<JCTree>();
			defs.appendList(cu.defs);
	
			JCImport eImport = null;
			for (JCTree item : defs) {
				try { eImport = (JCImport)item; } catch (ClassCastException e) { eImport = null; }
				if (eImport != null) {
					String importTxt = eImport.qualid.toString();
					if (importTxt != null && importTxt.endsWith(type)) {
						result = importTxt;
						break;
					}
				}
			}
		}
		
		return result;
	}

//	public static GClassJdk8u20 loadSuperClass(String superClassFullName) {
//		GClassJdk8u20 result = null;
//		if (superClassFullName != null) {
////			JCClassDecl clazz = null;
//			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(superClassFullName);
//			result = new GClassJdk8u20();
//			result.setNode(clazz);
//		}
//		return result;
//	}

	public static String getSuperClassFullName(GClassJdk8u20 node) {
		String result = null;
		JCClassDecl clazz = (node != null ? node.getNode() : null);
		result = (clazz != null && clazz.extending != null ? clazz.extending.toString() : "java.lang.Object");
		return result;
	}

	public static GClassJdk8u20 newClass(GAppJdk8u20 node, String name) {
		GClassJdk8u20 result = null;
		
		GUnit rootUnit = node.getRootUnit();
		String packageName = rootUnit.getPackageName();
		packageName = (packageName != null ? packageName : rootUnit.getApp().getPackageName());
		
		GUnitJdk8u20 unit = Factory.addUnit(node, packageName, name);
		result = (GClassJdk8u20)unit.getMainClass();
		return result;
	}

	public static ExpressionJdk8u20 property(GNodeJdk8u20 node, ExpressionJdk8u20 obj, String property) {
		ExpressionJdk8u20 result = null;
		
		TreeMaker tm = _getTm(node);
		JavacElements jc = _getJc(node);
	    JCFieldAccess targetNode = tm.Select(obj.getNode(), jc.getName(property));
	    
	    result = new ExpressionJdk8u20();
	    result.setNode(targetNode);
		return result;
	}

	public static ExpressionJdk8u20 debug(GNodeJdk8u20 node, ExpressionJdk8u20 msg) {
		ExpressionJdk8u20 result = null;
		result = Factory.call(node, Factory.toExpression(Factory.seq(node, "System.out")), "println", msg);
		return result;
	}

	public static boolean checkSameType(GFieldJdk8u20 node, String type) {
		boolean result = false;
		String nodeType = node.getType();
		if (nodeType.indexOf(".") < 0) {
			String fullNodeType = Factory.getImportClassName(_getUnit(node), nodeType);
			nodeType = (fullNodeType != null ? fullNodeType : nodeType);
		}
		result = checkSameType(nodeType, type);
		return result;
	}
	
	public static boolean checkImplements(GFieldJdk8u20 node, String type) {
		boolean result = false;
		String nodeType = node.getType();
		if (nodeType.indexOf(".") < 0) {
			String fullNodeType = Factory.getImportClassName(_getUnit(node), nodeType);
			nodeType = (fullNodeType != null ? fullNodeType : nodeType);
		}
		result = Factory.checkOwnerType(node, nodeType, type);
		return result;
	}
	
	public static boolean checkSuperClass(GFieldJdk8u20 node, String type) {
		boolean result = false;
		if (type != null) {
			String nodeType = node.getType();
			if (nodeType.indexOf(".") < 0) {
				String fullNodeType = Factory.getImportClassName(_getUnit(node), nodeType);
				nodeType = (fullNodeType != null ? fullNodeType : nodeType);
			}
			result = Factory.checkOwnerType(node, nodeType, type);
		}
		return result;
	}

	public static boolean checkSameType(String nodeType, String type) {
		boolean result = false;
		result = (type != null && type.equals(nodeType));
		if (!result && nodeType != null) {
			result = result || _checkSameType("java.lang", nodeType, type);
			result = result || _checkSameType("java.util", nodeType, type);
			result = result || _checkSameType("java.sql", nodeType, type);
			result = result || _checkSameType("java.io", nodeType, type);
			result = result || _checkSameType("org.effortless.core", nodeType, type);
			result = result || _checkSameType("org.effortless.fastsql", nodeType, type);
		}
		return result;
	}
	
	public static boolean checkJavaLang (String type) {
		boolean result = false;
		if (type != null) {
			int idx = type.lastIndexOf(".");
			type = (idx >= 0 ? type.substring(idx + 1) : type);

			Class clazz = ClassUtils.tryLoadClass("java.lang." + type);
			result = (clazz != null);
		}
		return result;
	}
	
	protected static boolean _checkSameType (String prefix, String type1, String type2) {
		boolean result = false;
		String _prefix = prefix + ".";
		type1 = (type1.startsWith(_prefix) ? type1.substring(_prefix.length()) : type1);
		type2 = (type2.startsWith(_prefix) ? type2.substring(_prefix.length()) : type2);
		result = (type1 != null && type1.equals(type2));
		return result;
	}

	public static GClass newEnumClass(GAppJdk8u20 node, String name) {
		GClassJdk8u20 result = null;
		
		GUnit rootUnit = node.getRootUnit();
		String packageName = rootUnit.getPackageName();
		packageName = (packageName != null ? packageName : rootUnit.getApp().getPackageName());
		
		GUnitJdk8u20 unit = Factory.addUnit(node, packageName, name);
		result = (GClassJdk8u20)unit.getMainClass();
		result.setSuperClass(org.effortless.core.EnumString.class);
		
		GMethodJdk8u20 mg = (GMethodJdk8u20)result.addConstructor().addParameter(String.class, "name").addParameter("int", "ordinal").setPublic(true);
		mg.add(mg.callSuper(mg.var("name"), mg.var("ordinal")));
		
		mg = (GMethodJdk8u20)result.addConstructor().addParameter(String.class, "name").addParameter("int", "ordinal").addParameter(org.effortless.core.EnumGroupString.class, "parent").setPublic(true);
		mg.add(mg.callSuper(mg.var("name"), mg.var("ordinal"), mg.var("parent")));
		
		return result;
	}

	public static GAnnotationJdk8u20 getAnnotation(GNodeJdk8u20 gNode, String annotation) {
		GAnnotationJdk8u20 result = null;

		if (annotation != null) {
			annotation = alterType(gNode, annotation);
			
			JCTree.JCModifiers mods = _getMods(gNode);
			
			JCAnnotation nodeAnn = null;
			
			com.sun.tools.javac.util.List<JCAnnotation> annotations = mods.annotations;
			ListBuffer<JCAnnotation> newList = new ListBuffer<JCAnnotation>();
			newList.appendList(annotations);
			
			for (JCAnnotation item : newList) {
				if (item != null && checkSameType(annotation, item.annotationType.toString())) {
					nodeAnn = item;
					break;
				}
			}
			
			if (nodeAnn != null) {
				result = new GAnnotationJdk8u20(gNode, nodeAnn);
			}
		}
		
		return result;
	}

	public static GAnnotationJdk8u20[] getAnnotations(GNodeJdk8u20 gNode, String annotation) {
		GAnnotationJdk8u20[] result = null;

		if (annotation != null) {
			java.util.List list = new java.util.ArrayList();
			
			annotation = alterType(gNode, annotation);
			
			JCTree.JCModifiers mods = _getMods(gNode);
			
			JCAnnotation nodeAnn = null;
			
			com.sun.tools.javac.util.List<JCAnnotation> annotations = mods.annotations;
			ListBuffer<JCAnnotation> newList = new ListBuffer<JCAnnotation>();
			newList.appendList(annotations);
			
			for (JCAnnotation item : newList) {
				if (item != null && checkSameType(annotation, item.annotationType.toString())) {
					nodeAnn = item;
					if (nodeAnn != null) {
						GAnnotationJdk8u20 newItem = new GAnnotationJdk8u20(gNode, nodeAnn);
						list.add(newItem);
					}
				}
			}
			
			result = (GAnnotationJdk8u20[])list.toArray(new GAnnotationJdk8u20[0]);
		}
		
		return result;
	}

	public static ExpressionJdk8u20 enumValue(GNodeJdk8u20 gNode, String type, String enumValue) {
		ExpressionJdk8u20 result = null;
		type = alterType(gNode, type);
		JCExpression targetNode = seq(gNode, type + "." + enumValue);
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);
		return result;
	}

	public static void addBlock(GMethodJdk8u20 gNode, GCodeJdk8u20 block) {
		List<JCStatement> stats = gNode.getNode().getBody().stats;
		
		JCTree.JCStatement code = block.getBlockCode();

		ListBuffer<JCTree.JCStatement> list = new ListBuffer<JCTree.JCStatement>();
		list.addAll(stats);
		list.append(code);

		gNode.getNode().getBody().stats = list.toList();
	}

	public static void addBlock(GCodeJdk8u20 gNode, GCodeJdk8u20 block) {
		List<JCStatement> stats = ((JCBlock)(gNode.getBlockCode())).stats;
		
		JCTree.JCStatement code = block.getBlockCode();

		ListBuffer<JCTree.JCStatement> list = new ListBuffer<JCTree.JCStatement>();
		list.addAll(stats);
		list.append(code);

		((JCBlock)(gNode.getBlockCode())).stats = list.toList();
	}

	public static void addJavaEnumCte(GClassJdk8u20 gClassJdk8u20, String name,
			String parent, List children) {
		// TODO Auto-generated method stub
		
	}

	public static void addEnumStringCte(GClassJdk8u20 gNode, String name, String parent, List children) {
//	    public static final RoleGroup VALOR = new RoleGroup("VALOR", 1);
		String cteName = name;//name.trim().toUpperCase();
		int ordinal = 1;
		List fields = gNode.getFields();
		int length = (fields != null ? fields.size() : 0);
		for (int i = 0; i < length; i++) {
			GField field = (GField)fields.get(i);
			if (field != null && field.isType(gNode.getFullName()) && field.isPublic() && field.isStatic() && field.isFinal()) {
				ordinal += 1;
			}
		}
		gNode.addField(gNode, cteName, gNode.newObject(gNode, gNode.cte(name), gNode.cte(ordinal))).setPublic(true).setStatic(true).setFinal(true);
		// TODO Auto-generated method stub
		
	}

	public static void addEnumStringCte(GClassJdk8u20 gNode,
			String name, ExpressionJdk8u20 parent) {
//	    public static final RoleGroup VALOR = new RoleGroup("VALOR", 1);
		String cteName = name;//name.trim().toUpperCase();
		int ordinal = 1;
		List fields = gNode.getFields();
		int length = (fields != null ? fields.size() : 0);
		for (int i = 0; i < length; i++) {
			GField field = (GField)fields.get(i);
			if (field != null && field.isType(gNode.getFullName()) && field.isPublic() && field.isStatic() && field.isFinal()) {
				ordinal += 1;
			}
		}
		gNode.addField(gNode, cteName, gNode.newObject(gNode, gNode.cte(name), gNode.cte(ordinal), parent)).setPublic(true).setStatic(true).setFinal(true);
	}

	public static void addImport(GUnitJdk8u20 unit, String clazz) {
		if (true && !containsImport(unit, clazz)) {
			JCCompilationUnit cu = unit.getNode();
			
			int idx = clazz.indexOf("<");
			clazz = (idx >= 0 ? clazz.substring(0, idx) : clazz);
			
			TreeMaker tm = _getTm(unit);
			JCTree.JCImport eImport = tm.Import(generatePackage(unit, clazz), false);

			ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
			ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
			oldDefs.appendList(cu.defs);

			JCClassDecl first = null;
			for (JCTree item : oldDefs) {
				if (first == null) {
					try { first = (JCClassDecl)item; } catch (ClassCastException e) { first = null; }
					if (first != null) {
						newDefs.append(eImport);
					}
				}
				newDefs.append(item);
			}
			
			cu.defs = newDefs.toList();
		}
	}

	public static void removeImport(GUnitJdk8u20 unit, String clazz) {
		if (true && containsImport(unit, clazz)) {
			JCCompilationUnit cu = unit.getNode();
			
			int idx = clazz.indexOf("<");
			clazz = (idx >= 0 ? clazz.substring(0, idx) : clazz);
			
			ListBuffer<JCTree> newDefs = new ListBuffer<JCTree>();
			ListBuffer<JCTree> oldDefs = new ListBuffer<JCTree>();
			oldDefs.appendList(cu.defs);

			String importClass = "import " + clazz + ";\n";
			
			
			JCTree.JCImport first = null;
			for (JCTree item : oldDefs) {
				try { first = (JCTree.JCImport)item; } catch (ClassCastException e) { first = null; }
				String firstStr = (first != null ? first.toString() : null);
				if (!importClass.equals(firstStr)) {
					newDefs.append(item);
				}
			}
			
			cu.defs = newDefs.toList();
		}
	}

	protected static com.sun.tools.javac.util.List EMPTY_LIST = (new com.sun.tools.javac.util.ListBuffer()).toList();
	
	public static Expression cte_array(GNode node, String type, Expression[] arguments) {
		ExpressionJdk8u20 result = null;
		GNodeJdk8u20 gNode = (GNodeJdk8u20)node;		
		TreeMaker tm = _getTm(gNode);
		
		type = alterType(gNode, type);
		
		ListBuffer listArgs = new ListBuffer();
		if (arguments != null) {
			for (Expression _arg : arguments) {
				ExpressionJdk8u20 arg = (ExpressionJdk8u20)_arg;
				if (arg != null && arg.getNode() != null) {
					listArgs.add(arg.getNode());
				}
			}
		}

		JCExpression targetNode = tm.NewArray(toIdentExpression(gNode, type).getNode(), EMPTY_LIST, listArgs.toList());
		
		result = new ExpressionJdk8u20();
		result.setNode(targetNode);
		return result;
	}

}
