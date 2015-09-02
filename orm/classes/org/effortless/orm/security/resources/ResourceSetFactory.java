package org.effortless.orm.security.resources;

public class ResourceSetFactory {

	
	public static ResourceDefinition depends (String depends) {
		return depends(depends, false);
	}
	
	public static ResourceDefinition depends (String depends, boolean excludeViews) {
		AdditionResourceSet result = null;
		result = new AdditionResourceSet();
		String[] array = (depends != null ? depends.trim().split(",") : null);
		if (array != null) {
			for (String item : array) {
				result.add(new OperationResourceSet("read", item, excludeViews));
				result.add(new ReadOperationsResourceSet(item));
			}
		}
		return result;
	}

	/**
	 * Aplicable a ejemplos tipo Persona-Telefonos
	 * 
	 * 
elementos internos

Persona
  Crear persona -> Crear telefonos
  Leer persona -> Leer telefonos
  Eliminar persona -> Eliminar telefonos
  Editar persona -> Crear telefonos, modificar telefonos, eliminar telefonos

Telefonos
	 * 
	 * 
	 * @param className
	 * @param action
	 * @param role
	 * @param inners
	 * @return
	 */
	public static RoleResourceSet buildActionResource (String className, String action, Object role, String[] inners) {
		RoleResourceSet result = null;
		result = new RoleResourceSet(role);
		{
			EntityResourceSet rs = new EntityResourceSet();
			rs.setOperations(action);
			rs.setEntity(className);
			result.add(rs);
			
			int length = (inners != null ? inners.length : 0);
			if (length > 0) {
				if ("create".equals(action) || "read".equals(action) || "delete".equals(action)) {
					for (int i = 0; i < length; i++) {
						String innerClassName = inners[i];
						EntityResourceSet rsInner = new EntityResourceSet();
						rsInner.setOperations(action);
						rsInner.setEntity(innerClassName);
						result.add(rsInner);
					}
				}
				else if ("update".equals(action)) {
					String[] opActions = new String[] {"create", "update", "delete"};
					for (int i = 0; i < length; i++) {
						String innerClassName = inners[i];
						for (int j = 0; j < opActions.length; j++) {
							EntityResourceSet rsInner = new EntityResourceSet();
							rsInner.setOperations(opActions[j]);
							rsInner.setEntity(innerClassName);
							result.add(rsInner);
						}
					}
				}
			}
		}
		return result;
	}
	
	
	
	public static RoleResourceSet buildActionResource (String className, String action, Object role) {
		RoleResourceSet result = null;
		result = new RoleResourceSet(role);
		{
			EntityResourceSet rs = new EntityResourceSet();
			rs.setOperations(action);
			rs.setEntity(className);
			result.add(rs);
		}
		return result;
	}
	
//	public static ResourceDefinition build_Module_Administrator (String module, String exceptions, String depends) {
//		AdditionResourceSet result = null;
//		result = new AdditionResourceSet();
//		
//		result.subtract();
//			result.addition();
//				result.includeDeleteInner(module);
//				result.module(module, "delete,erase,deleteBind");
//			result.end();
//			String[] arrayExceptions = (exceptions != null ? exceptions.trim().split(",") : null);
//			if (arrayExceptions != null) {
//				result.addition();
//				for (String exception : arrayExceptions) {
//					result.operation(exception, module);
//				}
//				result.end();
//			}
//		result.end();
//		
//		result.add(new MenuResourceSet(module));
//		
//		String[] arrayDepends = (depends != null ? depends.trim().split(",") : null);
//		if (arrayDepends != null) {
//			for (String depend : arrayDepends) {
//				result.add(new OperationResourceSet("read", depend));
//				result.add(new ReadOperationsResourceSet(depend));
//			}
//		}
//		
//		return result;
//	}
	
//	
//	public static ResourceDefinition build_RES_ADMINISTRADOR_DE_USUARIOS_ACADEMICOS () {
////		return build_Module_Administrator("control_acceso", "imprimir.*,abrir,initLogin,forceLogout,getCurrent.*", "depends_profesorado,depends_personalnodocente,depends_cursos,depends_usuarios");
//		AdditionResourceSet result = null;
//		result = new AdditionResourceSet();
//		
//		result.subtract();
//			result.addition();
//				result.includeDeleteInner("control_acceso");
//				result.module("control_acceso", "delete,erase,deleteBind");
//			result.end();
//			result.addition();
//				result.operation("imprimir.*");
//				result.operation("abrir");
//				result.operation("initLogin", "control_acceso");
//				result.operation("forceLogout", "control_acceso");
//				result.operation("getCurrent.*", "control_acceso");
//			result.end();
//		result.end();
//		
//		result.menu("control_acceso");
//		
//		result.operation("read", "depends_profesorado");
//		result.readOperations("depends_profesorado");
//
//		result.operation("read", "depends_personalnodocente");
//		result.readOperations("depends_personalnodocente");
//
//		result.operation("read", "depends_cursos");
//		result.readOperations("depends_cursos");
//
//		result.operation("read", "depends_usuarios");
//		result.readOperations("depends_usuarios");
//		
//		return result;
//	}
//	
///*
// * 
//		<resource id="RES_ADMINISTRADOR_DE_USUARIOS_ACADEMICOS">
//			<subtract>
//				<addition>
//					<includeDeleteInner module="control_acceso" />
//					<module id="control_acceso" excludeOperations="delete,erase,deleteBind" />
//				</addition>
//				<addition>
//					<operation id="imprimir.*" />
//					<operation id="abrir" />
//					<operation id="initLogin" module="control_acceso" />
//					<operation id="forceLogout" module="control_acceso" />
//					<operation id="getCurrent.*" module="control_acceso" />
//				</addition>
// 			</subtract>
//		
//			<menu module="control_acceso" />
//			
//			<operation id="read" module="depends_profesorado" />
//			<readOperations module="depends_profesorado" />
//
//			<operation id="read" module="depends_personalnodocente" />
//			<readOperations module="depends_personalnodocente" />
//
//			<operation id="read" module="depends_cursos" />
//			<readOperations module="depends_cursos" />
//
//			<operation id="read" module="depends_usuarios" />
//			<readOperations module="depends_usuarios" />
//<!-- 
//			<operation id="read" module="autorun_cfg" />
//-->
//		</resource>
// * 
// * 	
// */
//
//	public ListResourceSet includeDeleteInner(String module) {
//		return _doAdd(new IncludeDeleteInnerResourceSet(module));
//	}
//	
//	public ListResourceSet subtract() {
//		return _doAdd(new SubtractResourceSet());
//	}
//
//	public ListResourceSet addition() {
//		return _doAdd(new AdditionResourceSet());
//	}
//
//	public ListResourceSet module(String module, String exclude) {
//		return _doAdd(new ModuleResourceSet(module, exclude));
//	}
//
//	public ListResourceSet end() {
//		int size = (this.list != null ? this.list.size() : 0);
//		if (size > 0) {
//			this.list.remove(size - 1);
//		}
//		return this;
//	}
//
//	public ListResourceSet operation(String operation) {
//		return _doAdd(new OperationResourceSet(operation));
//	}
//
//	public ListResourceSet operation(String operation, String module) {
//		return _doAdd(new OperationResourceSet(operation, module));
//	}
//
//	public ListResourceSet menu(String module) {
//		return _doAdd(new MenuResourceSet(module));
//	}
//
//	public ListResourceSet readOperations(String module) {
//		return _doAdd(new ReadOperationsResourceSet(module));
//	}
//
//	public ListResourceSet operation(String operation, String module, boolean excludeViews) {
//		return _doAdd(new OperationResourceSet(operation, module, excludeViews));
//	}
	
	
	
}
