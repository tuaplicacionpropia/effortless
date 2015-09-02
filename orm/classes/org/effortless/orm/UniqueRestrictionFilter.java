package org.effortless.orm;

public abstract class UniqueRestrictionFilter<Type extends Object> extends AbstractFilter {

	public UniqueRestrictionFilter (String entityName, String deletedName, Boolean deletedValue, String idName, Object idValue, String[] propertyNames, Object[] params) {
		super();
		this.entityName = entityName;
		this.deletedName = deletedName;
		this.deletedValue = deletedValue;
		this.idName = idName;
		this.idValue = idValue;
		this.propertyNames = propertyNames;
		this.params = params;
		
		this.deletedName = (this.deletedName != null ? this.deletedName.trim() : "");
		setPaginated(Boolean.TRUE);
		setPageIndex(Integer.valueOf(0));
		setPageSize(Integer.valueOf(1));
		
//		setOrderBy("date DESC");
	}
	
	protected String entityName;
	protected String deletedName;
	protected Boolean deletedValue;
	protected String idName;
	protected Object idValue;
	protected String[] propertyNames;
	protected Object[] params;

	protected String _buildQuery (boolean count) {
		if (this.deletedName.length() > 0) {
			if (this.deletedValue != null && this.deletedValue.booleanValue() == false) {
				this.or().isNull(this.deletedName).eq(this.deletedName, Boolean.FALSE).end();
				//query += "(o." + deletedName + " IS NULL OR o." + deletedName + " = FALSE) AND ";
			}
			else if (deletedValue != null && deletedValue.booleanValue() == true) {
				this.eq(this.deletedName, Boolean.TRUE);
				//query += "(o." + deletedName + " = TRUE) AND ";
			}
		}
		
		if (this.idValue != null) {
			this.ne(this.idName, this.idValue);
			//query += "o." + this.idName + " <> ?1 AND ";
		}

		int paramsLength = (this.params != null ? this.params.length : 0);
		for (int i = 0; i < paramsLength; i++) {
			String pName = this.propertyNames[i];
			Object pValue = this.params[i];
			if (pValue != null) {
				this.eq(pName, pValue);
				//query += "o." + pName + " = ?" + String.valueOf(idx) + " ";
			}
			else {
				this.isNull(pName);
				//query += "o." + pName + " IS NULL ";
			}
		}
		
		return super._buildQuery(count);
	}
	
}
