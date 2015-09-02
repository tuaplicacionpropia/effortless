package org.effortless.orm.definition;

public class ListPropertyEntity extends Object implements PropertyEntity {
		
		public ListPropertyEntity () {
			super();
			initiate();
		}
		
		protected void initiate () {
			initiateOwnerColumnName();
			initiateItemColumnName();
			initiateTableName();
			initiatePropertyName();
			initiateType();
			initiateLoaderName();
			initiateInner();
		}
		
		protected boolean inner;
		
		protected void initiateInner () {
			this.inner = false;
		}
		
		public boolean getInner () {
			return this.inner;
		}
		
		public void setInner (boolean newValue) {
			this.inner = newValue;
		}
		
		protected EntityDefinition definition;
		
		protected void initiateDefinition () {
			this.definition = null;
		}
		
		public EntityDefinition getDefinition () {
			return this.definition;
		}
		
		public void setDefinition (EntityDefinition newValue) {
			this.definition = newValue;
		}
		
		protected String ownerColumnName;
		
		protected void initiateOwnerColumnName () {
			this.ownerColumnName = null;
		}
		
		public String getOwnerColumnName () {
			return this.ownerColumnName;
		}
		
		public void setOwnerColumnName (String newValue) {
			this.ownerColumnName = newValue;
		}
		
		protected String itemColumnName;
		
		protected void initiateItemColumnName () {
			this.itemColumnName = null;
		}
		
		public String getItemColumnName () {
			return this.itemColumnName;
		}
		
		public void setItemColumnName (String newValue) {
			this.itemColumnName = newValue;
		}
		
		protected String tableName;
		
		protected void initiateTableName () {
			this.tableName = null;
		}
		
		public String getTableName () {
			return this.tableName;
		}
		
		public void setTableName (String newValue) {
			this.tableName = newValue;
		}
	
		protected String propertyName;
		
		protected void initiatePropertyName () {
			this.propertyName = null;
		}
		
		public String getPropertyName() {
			return this.propertyName;
		}
		
		public void setPropertyName (String newValue) {
			this.propertyName = newValue;
		}

		protected Class<?> type;
		
		protected void initiateType () {
			this.type = null;
		}
		
		public Class<?> getType () {
			return this.type;
		}
		
		public void setType (Class<?> newValue) {
			this.type = newValue;
		}
		
		protected String loaderName;
		
		protected void initiateLoaderName () {
			this.loaderName = null;
		}
		
		public String getLoaderName () {
			return this.loaderName;
		}
		
		public void setLoaderName (String newValue) {
			this.loaderName = newValue;
		}


		protected java.util.Map attributes = null;
		
		@Override
		public Object getAttribute(String name) {
			return (this.attributes != null && name != null ? this.attributes.get(name) : null);
		}

		@Override
		public void setAttribute(String name, Object value) {
			if (name != null) {
				this.attributes = (this.attributes != null ? this.attributes : new java.util.HashMap());
				this.attributes.put(name, value);
			}
		}
		
}
