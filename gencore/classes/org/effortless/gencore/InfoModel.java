package org.effortless.gencore;

import java.util.ArrayList;
import java.util.List;

//import org.effortless.ann.Finder;
//import org.effortless.ann.InfoUi;
//import org.effortless.ann.NotNull;
//import org.effortless.ann.OwnerInnerEntity;
import org.effortless.core.Collections;
import org.effortless.core.StringUtils;
import org.effortless.orm.AbstractEnabledPersistEntity;
import org.effortless.orm.FileEntity;
//import org.effortless.jast.GAnnotation;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;

public class InfoModel extends Object {

	
	public static final String[] ARRAY_SINGLE_UNIQUE = {"code", "codigo", "cif", "nif", "dni", "passport"};
	public static final Object[][] ARRAY_SINGLE_UNIQUE_DEPENDS = {{"name", new String[] {"surname"}}, {"nombre", new String[] {"apellido"}}};
	
	public static List<GField> listNotNull (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			result = new ArrayList<GField>();
			List<GField> fields = clazz.getViewProperties();
			for (GField field : fields) {
				if (isNotNull(clazz, field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public static List<GField> listUnique (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			result = new ArrayList<GField>();
			List<GField> fields = clazz.getViewProperties();
			for (GField field : fields) {
				if (isUnique(clazz, field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public static List<GField> listNotNullUnique (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			List<GField> notNull = listNotNull(clazz);
			List<GField> unique = listUnique(clazz);
			result = new ArrayList<GField>();
			Collections.addAll(result, notNull);
			Collections.addAll(result, unique);
		}
		return result;
	}

	public static Boolean isUnique (GClass clazz, GField field) {
		return isSingleUnique(clazz, field);
	}
	
	public static Boolean isSingleUnique (GClass clazz, GField field) {
		Boolean result = false;
		if (clazz != null && field != null) {
			String fieldName = field.getName().toLowerCase();
			for (String it : ARRAY_SINGLE_UNIQUE) {
				if (fieldName.contains(it)) {
					result = true;
//					println "UNIQUE " + effortless.MySession.getRootContext()
//					println "$fieldName is unique on class ${clazz.name}"
					break;
				}
			}
			if (!result) {
				for (Object[] it : ARRAY_SINGLE_UNIQUE_DEPENDS) {
					if (fieldName.contains((String)it[0])) {
						result = !containsAnyField(clazz, (String[])it[1]);
						if (result) {
//							println "UNIQUE " + effortless.MySession.getRootContext()
//							println "$fieldName is unique on class ${clazz.name}"
							break;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static Boolean containsAnyField (GClass clazz, String[] names) {
		Boolean result = false;
		List<GField> fields = clazz.getViewProperties();
		for (String name : names) { 
			for (GField field : fields) {
				if (field.getName().contains(name)) {
					result = true;
					break;
				}
			}
			if (result) {
				break;
			}
		}
		return result;
	}
	
	public static final String[] ARRAY_NOT_NULL = {"code", "codigo", "nombre", "apellido", "name", "surname", "cif", "nif", "dni", "passport", "pasaporte"};
	
	public static Boolean isNotNull (GClass clazz, GField field) {
		Boolean result = false;
		if (clazz != null && field != null) {
			String fieldName = field.getName().toLowerCase();
			for (String it : ARRAY_NOT_NULL) {
				if (fieldName.contains(it)) {
					result = true;
//					println "$fieldName is not null on class ${clazz.name}"
					break;
				}
			}
		}
		result = (result && isNull(field) ? false : result);
		return result;
	}
	
	public static boolean isNull (GField field) {
		boolean result = false;
//		GAnnotation ann = field.getAnnotation(NotNull.class);
//		String value = StringUtils.forceNotNull((ann != null ? ann.getMemberString("enabled") : null));
//		result = (ann != null && (value.length() > 0 && "false".equals(value)));
		Boolean _notNull = (Boolean)field.getData("notNull");
		boolean notNull = (_notNull != null && _notNull.booleanValue());
		result = !notNull;
		return result;
	}
	
	

	public static boolean isSingleUnique(GField field) {
        boolean result = false;
        if (field != null) {
        	String fieldName = field.getName().toLowerCase();
                for (String it : ARRAY_SINGLE_UNIQUE) {
                        if (fieldName.contains(it)) {
                                result = true;
//                                println "UNIQUE " + effortless.MySession.getRootContext()
//                                println "$fieldName is unique on class ${clazz.name}"
                                break;
                        }
                }
                if (!result) {
                        for (Object[] it : ARRAY_SINGLE_UNIQUE_DEPENDS) {
                                if (fieldName.contains((String)it[0])) {
                                        result = !containsAnyField(field.getClazz(), (String[])it[1]);
                                        if (result) {
//                                                println "UNIQUE " + effortless.MySession.getRootContext()
//                                                println "$fieldName is unique on class ${clazz.name}"
                                                break;
                                        }
                                }
                        }
                }
        }
        return result;		
	}

	public static boolean isNotNull(GField field) {
        boolean result = false;
        if (field != null) {
                String fieldName = field.getName().toLowerCase();
                for (String it : ARRAY_NOT_NULL) {
                        if (fieldName.contains(it)) {
                                result = true;
//                                println "$fieldName is not null on class ${clazz.name}"
                                break;
                        }
                }
        }
        return result;
	}

    protected static final String[] COMMENT_NAMES = {"comment", "comentario", "remark", "observacion", "annotation", "anotacion"};
    	
//	public static boolean checkCommentField(String fieldName) {
//        boolean result = false;
//        if (fieldName != null) {
//                fieldName = fieldName.trim().toLowerCase();
//                for (String name : COMMENT_NAMES) {
//                        if (name != null && name.trim().toLowerCase().contains(fieldName)) {
//                                result = true;
//                                break;
//                        }
//                }
//        }
//        return result;
//	}
	
	public static boolean checkAnyValidCustomAction(GClass clazz) {
		boolean result = false;
		String[] names = getActionCustomNames(clazz);
		result = (names != null && names.length > 0);
		return result;
	}
	
	public static String[] getActionCustomNames(GClass clazz) {
		String[] result = null;
		if (clazz != null) {
			List<String> list = new ArrayList<String>();
//			List<MethodNode> methods = clazz.getAllDeclaredMethods();
			List<GMethod> methods = clazz.getMethods();
			for (GMethod method : methods) {
				if (true) {// || (method.checkSingleAction() && isVisible(method)) {
					list.add(method.getName());
				}
			}
			result = list.toArray(new String[0]);
		}
		return result;
	}

	public static List<GMethod> getCustomActions(GClass clazz) {
		List<GMethod> result = null;
		if (clazz != null) {
			result = new ArrayList<GMethod>();
			List<GMethod> methods = clazz.getMethods();
			for (GMethod method : methods) {
				if (method.checkSingleAction() && isVisible(method)) {
					result.add(method);
				}
			}
		}
		return result;
	}

	public static boolean isVisible(GMethod method) {
		boolean result = true;
		if (method != null) {
//			GAnnotation ann = method.getAnnotation(InfoUi.class);
//			String visibleValue = (ann != null ? ann.getMemberString("visible") : null);
//			result = !"false".equals(visibleValue);
			
			Boolean _visible = (Boolean)method.getData("infoUi.visible");
			boolean visible = (_visible != null && _visible.booleanValue());
			result = visible;
		}
		return result;
	}

	public static List<GField> getFinderProperties(GClass clazz) {
        List<GField> result = null;
        if (clazz.isType(AbstractEnabledPersistEntity.class)) {
        	GField enabledField = clazz.getField("enabled");
        	if (enabledField != null) {
        		result = (result != null ? result : new ArrayList<GField>());
        		result.add(enabledField);
        	}
        }
        java.util.List baseResult = listNotNullUnique(clazz);
        result = (result != null ? result : new ArrayList<GField>());
        if (baseResult != null) {
        	result.addAll(baseResult);
        }
        int length = (result != null ? result.size() : 0);
        if (length < 5) {
                List<GField> fields = clazz.getViewProperties();
                for (GField field : fields) {
                        if (!result.contains(field) && checkFilterField(field) && !checkExcludedFinder(field)) {
                                result.add(field);
                                if (result.size() >= 5) {
                                        break;
                                }
                        }
                }
        }
        return result;		
	}

	public static List<GField> listFileFields(GClass clazz) {
		List<GField> result = null;
		List<GField> fields = clazz.getViewProperties();
		result = new ArrayList<GField>();
		for (GField field : fields) {
			if (field.isType(FileEntity.class)) {
				result.add(field);
			}
		}
		return result;
	}

//si return type => se considera de consulta => NOLOG
//si NO return type => se considera de modificacion => LOG

	public static List<GField> getEditorProperties (GClass clazz) {
		List<GField> result = null;
		result = new ArrayList<GField>();
		List<GField> fields = clazz.getViewProperties();
		for (GField field : fields) {
			if (!result.contains(field) && !checkExcludedEditor(field)) {
				result.add(field);
			}
		}
		return result;
	}


	protected static boolean checkExcludedField(GField field) {
		boolean result = false;
		if (field != null) {
//			result = field.hasAnnotation(OwnerInnerEntity.class);
			result = field.hasDataClass("OwnerInnerEntity");
		}
		return result;
	}

	
	protected static boolean checkExcludedFinder(GField field) {
		boolean result = false;
		result = result || checkExcludedField(field);
		result = result || (field != null && (field.isType(FileEntity.class) || field.isType(java.io.File.class)));
		return result;
	}

	
	protected static boolean checkExcludedEditor(GField field) {
		boolean result = false;
		result = checkExcludedField(field);
		return result;
	}

	protected static boolean checkExcludedInfo(GField field) {
		boolean result = false;
		result = checkExcludedField(field);
		return result;
	}

	public static List<GField> getInfoViewProperties(GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
//			GAnnotation ann = clazz.getAnnotation(Finder.class);
//			String infoProperties = StringUtils.forceNotNull((ann != null ? ann.getMemberString("info") : null));
			String infoProperties = StringUtils.forceNotNull((String)clazz.getData("finder.info"));
			if (infoProperties.length() > 0) {
				result = new ArrayList<GField>();
				String[] array = infoProperties.split(",");
				for (String fName : array) {
					GField field = clazz.getField(fName);
					if (field != null && !checkExcludedInfo(field)) {
						result.add(field);
					}
				}
			}
			else {
				List<GField> finder = getFinderProperties(clazz);
				
				result = new ArrayList<GField>();
                List<GField> fields = clazz.getViewProperties();
                for (GField field : fields) {
                	if ((finder == null || !finder.contains(field)) && checkInfoField(field)) {
                		result.add(field);
                		if (result.size() >= 5) {
                			break;
                		}
                	}
                }
				
			}
		}
		return result;
	}
	
	public static boolean checkFilterField (GField field) {
		boolean result = false;
		result = true;
		result = result && !checkComment(field);
		result = result && !checkPassword(field);
		result = result && !checkPhoto(field);
		return result;
	}
	
	public static boolean checkInfoField (GField field) {
		boolean result = false;
		result = true;
		result = result && !checkPassword(field);
		result = result && !checkExcludedInfo(field);
		return result;
	}
	
	public static boolean checkComment (GField field) {
//		return checkKeywords(field, new String[] {"comment", "comentario"});
		return checkKeywords(field, COMMENT_NAMES);
	}
	
	public static boolean checkPassword (GField field) {
		return checkKeywords(field, new String[] {"password", "contrase", "secret"});
	}
	
	protected static boolean checkKeywords (GField field, String[] keywords) {
		boolean result = false;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		for (int i = 0; i < keywords.length; i++) {
			result = (!result && keywords[i].contains(lName) ? true : result);
		}
		return result;
	}
	
	public static boolean checkPhoto (GField field) {
		return checkKeywords(field, new String[] {"photo", "image", "foto", "icon"});
	}

	public static boolean checkCompleteApplication() {
		return true;
	}

	public static boolean checkCreateFileEntity() {
		return true;
	}

	public static boolean checkCreateLogEntity() {
		return true;
	}

	public static List<GField> listFirstTextField (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			result = new ArrayList<GField>();
			List fields = clazz.getProperties();
			int fieldsSize = (fields != null ? fields.size() : 0);
			for (int i = 0; i < fieldsSize; i++) {
				GField field = (GField)fields.get(i);
				if (field != null && field.isString()) {
					result.add(field);
					break;
				}
			}
		}
		return result;
	}

	public static List<GField> listFieldsByName(GClass clazz, String names) {
		List<GField> result = null;
		String[] arrayNames = (names != null ? names.split(",") : null);
		if (clazz != null && arrayNames != null) {
			result = new java.util.ArrayList<GField>();
			for (String name : arrayNames) {
				GField field = clazz.getField(name);
				if (field != null) {
					result.add(field);
				}
			}
			result = (result.size() > 0 ? result : null);
		}
		return result;
	}

}
