package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public class NormalVisitor extends Object implements Visitor {

	public NormalVisitor () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	@Override
	public boolean contains(Resource rs, AdditionResourceSet rd) {
		boolean result = false;
		
		int size = (rd.items != null ? rd.items.size() : 0);
		if (rs != null && size > 0) {
			result = false;
			for (int i = 0; i < size; i++) {
				ResourceDefinition item = rd.items.get(i);
				if (item.contains(rs, this)) {
					result = true;
					break;
				}
			}
		}
		
		return result;
	}

	@Override
	public boolean contains(Resource rs, AllResourceSet rd) {
		return (rs != null);
	}

	@Override
	public boolean contains(Resource rs, EntityResourceSet rd) {
		boolean result = false;
		
		String entity = (rd.entity != null ? rd.entity.trim() : "");
		String resEntity = getEntity(rs);
		if (entity.equals(resEntity)) {
			result = _belongsScope(rs, rd);
			if (result && rd.operations != null && rd.operations.trim().length() > 0) {
				result = _belongs(rs.getAction(), rd.operations);
			}
		}
		
		return result;
	}

	@Override
	public boolean contains(Resource rs, IncludeDeleteInnerResourceSet rd) {
		boolean result = false;
		
		String action = rs.getAction();
		action = (action != null ? action.trim().toLowerCase() : "");
		if ("delete".equals(action)) {
			result = _belongsScope(rs, rd);
			result = (result && _checkInner(rs));
		}
		
		return result;
	}

	@Override
	public boolean contains(Resource rs, ModuleResourceSet rd) {
		boolean result = false;
		result = _belongsScope(rs, rd);
		return result;
	}

	@Override
	public boolean contains(Resource rs, NoneResourceSet rd) {
		return false;
	}

	@Override
	public boolean contains(Resource rs, NotResourceSet rd) {
		// TODO Auto-generated method stub
//		<#if (data?has_content && data?children?size > 0)>
//		<#list data?children as element>
//			<#if (element?node_type = "element")>
//				<#import element?node_name?trim?lower_case + ".ftl" as lib>
//				<#local result = !lib.fCheckBelongs2Resource(infoSecurity, element, extra, cfg)>
//				<#break>
//			</#if>
//		</#list>
//	</#if>
		return false;
	}

	@Override
	public boolean contains(Resource rs, OperationResourceSet rd) {
		boolean result = false;
		if (_belongs(rs.getAction(), rd.operations)) {
			result = _belongsScope(rs, rd);
		}
		return result;
	}

	@Override
	public boolean contains(Resource rs, PropertyResourceSet rd) {
//		<#if (data.@id[0]?? && data.@id?trim?length > 0)>
//		<#local resPropertyName = data.@id?trim?lower_case>
//		<#if (libSecurityResources.fCheckProperty(infoSecurity, data, extra, cfg))>
//			<#local propertyName = libSecurityResources.fGetPropertyName(infoSecurity, data, extra, cfg)?trim?lower_case>
//			<#if (resPropertyName = propertyName)>
//				<#if (data.@entity[0]?? && data.@entity?trim?length > 0)>
//					<#local entityName = libSecurityResources.fGetEntityName(infoSecurity, data, extra, cfg)?trim?lower_case>
//					<#local resEntityName = data.@entity?trim?lower_case>
//					<#local result = (entityName = resEntityName)>
//				<#elseif (data.@unit[0]?? && data.@unit?trim?length > 0)>
//					<#local unitName = libSecurityResources.fGetUnitName(infoSecurity, data, extra, cfg)?trim?lower_case>
//					<#local resUnitName = data.@unit?trim?lower_case>
//					<#local result = (unitName = resUnitName)>
//				<#elseif (data.@module[0]?? && data.@module?trim?length > 0)>
//					<#local moduleName = libSecurityResources.fGetModuleName(infoSecurity, data, extra, cfg)?trim?lower_case>
//					<#local resModuleName = data.@module?trim?lower_case>
//					<#local result = (moduleName = resModuleName)>
//				<#else>
//					<#local result = true>
//				</#if>
//				<#if (result && data.@operation[0]?? && data.@operation?trim?length > 0)>
//					<#local operationName = "">
//					<#if (infoSecurity["op"]?? && infoSecurity["op"]?trim?length > 0)>
//						<#local operationName = infoSecurity["op"]?trim>
//					</#if>
//					<#if (operationName?trim?length > 0)>
//						<#local array = data.@operation?trim?lower_case?split(",")>
//						<#if (operationName?trim?lower_case = "save")>
//							<#local contains = false>
//							<#if (!contains && array?seq_contains("create"?trim))><#local contains = true></#if>
//							<#if (!contains && array?seq_contains("update"?trim))><#local contains = true></#if>
//							<#if (!contains && array?seq_contains("persist"?trim))><#local contains = true></#if>
//							<#local result = contains>
//						<#elseif (operationName?trim?lower_case = "load")>
//							<#local contains = false>
//							<#if (!contains && array?seq_contains("read"?trim))><#local contains = true></#if>
//							<#local result = contains>
//						</#if>
//					</#if>
//				</#if>
//			</#if>
//		</#if>
//	</#if>
		return false;
	}

	@Override
	public boolean contains(Resource rs, ReadOperationsResourceSet rd) {
		boolean result = false;
		
		result = _belongsScope(rs, rd);
		if (result && checkProcedure(rs)) {
			result = false;
		}

		return result;
	}
	
	@Override
	public boolean contains(Resource rs, ResourceResourceSet rd) {
		boolean result = false;
		if (rd.resourceSet != null && rs != null) {
			result = rd.resourceSet.contains(rs, this);
		}
		return result;
	}

	@Override
	public boolean contains(Resource rs, SubtractResourceSet rd) {
		boolean result = false;
		
		int size = (rd.items != null ? rd.items.size() : 0);
		if (rs != null && size > 1) {
			for (int i = 0; i < (size - 1); i++) {
				ResourceDefinition item = rd.items.get(i);
				if (item.contains(rs, this)) {
					result = true;
					break;
				}
			}
			result = (result && !(rd.items.get(size - 1).contains(rs, this)));
		}
		
		return result;
	}

	@Override
	public boolean contains(Resource rs, UnitResourceSet rd) {
		boolean result = false;
		result = _belongsScope(rs, rd);
		
		if (result && rd.operations != null && rd.operations.trim().length() > 0 && checkAction(rs)) {
			result = _belongs(rs.getAction(), rd.operations);
		}
		return result;
	}
	
	@Override
	public boolean contains(Resource rs, WriteOperationsResourceSet rd) {
		boolean result = false;

		String action = rs.getAction();
		if (action != null && action.length() > 0) {
			result = _belongsScope(rs, rd);
			if (result && checkFunction(rs)) {
				result = false;
			}
		}

		return result;
	}

	@Override
	public boolean contains(Resource rs, MenuResourceSet rd) {
		boolean result = false;
		
		if (checkView(rs)) {
			boolean checkMenu = checkMenuitem(rs);
			result = (checkMenu && _belongsScope(rs, rd));
		}
		
		return result;
	}

	
	
	
	protected String getEntity (Resource rs) {
		return rs.getClassName();
	}
	
	protected boolean _belongs(String action, String operations) {
		boolean result = false;
		result = (operations != null && action != null && operations.contains(action));
		return result;
	}

	
	protected boolean checkProcedure (Resource rs) {
		return false;//ActionType.PROCEDURE.equals(resource.getActionType())
	}

	protected boolean checkAction (Resource rs) {
		return false;//ResourceType.ACTION.equals(rs.getType())
	}

	protected boolean checkFunction (Resource rs) {
		return false;//ActionType.FUNCTION.equals(rs.getActionType())
	}

	
	protected boolean checkView (Resource rs) {
		return false;//if (ResourceType.VIEW.equals(resource.getType())) {
	}

	protected boolean checkMenuitem (Resource rs) {
		return false;//if (ResourceType.VIEW.equals(resource.getType())) {
	}

	
	protected boolean _checkInner (Resource resource) {
		boolean result = false;
		Class<?> clazz = null;//resource.getEntityClass();
		result = (clazz != null && clazz.getEnclosingClass() != null);
		return result;
	}

	
	protected boolean _belongsScope(Resource rs, ScopeResourceSet rd) {
		boolean result = false;
		
		String entity = (rd.entity != null ? rd.entity.trim() : "");
		String unit = (rd.unit != null ? rd.unit.trim() : "");
		String module = (rd.module != null ? rd.module.trim() : "");
		
		result = false;
		result = result || entity.equals(getEntity(rs));
		result = result || unit.equals(getUnit(rs));
		result = result || module.equals(getUnit(rs));
		result = result || (module.length() <= 0 && unit.length() <= 0 && entity.length() <= 0);
			
		if (result && rd.excludeEntities != null && rd.excludeEntities.trim().length() > 0) {
			if (_belongs(getEntity(rs), rd.excludeEntities.trim())) {
				result = false;
			}
		}
		
		if (result && rd.excludeActions != null && rd.excludeActions.trim().length() > 0) {
			result = !_belongs(rs.getAction(), rd.excludeActions);
		}
		
		if (result && rd.excludeAllViews) {
			result = !checkView(rs);
		}

		return result;
	}

	protected Object getUnit(Resource rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
